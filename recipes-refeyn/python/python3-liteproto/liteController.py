# -----------------------------------------------------------------------------------------------------
# Copyright (C) Refeyn Ltd - All Rights Reserved
# Unauthorized copying of this file, via any medium is strictly prohibited
# Proprietary and confidential
# URL: https://www.refeyn.com
# -----------------------------------------------------------------------------------------------------
import enum
import logging
import os
import pathlib
import shutil
import tempfile
from typing import Optional

from iscat_utils.backend import coroUtils, operationsController
from iscat_utils.gui import qml
from iscat_utils.gui.qml import quickGraphicsView
from iscat_utils.qt import typedSignal
from lw8.backend import Backend
from lw8.engines.analysisEngine import AEResults
from lw8.engines.autoFocus import focusStabiliser
from lw8.engines.recordCodecs import abstractCodec, mpxWriterCodec
from lw8.gui.commonQml.controllers import history
from lw8.gui.widgets import liveHistogramWidget
from lw8.routines import common, findFocus
from PySide6 import QtCore, QtQml, QtQuick, QtWidgets

logger = logging.getLogger(__name__)


class MeasurementStage(enum.Enum):
    WARMING_UP = enum.auto()
    NOT_MEASURING = enum.auto()
    FINDING_FOCUS = enum.auto()
    ADD_SAMPLE = enum.auto()
    REFINING_FOCUS = enum.auto()
    RECORDING = enum.auto()
    SAVE_LOCATION = enum.auto()


class LiteController(QtCore.QObject):
    QtCore.QEnum(MeasurementStage)

    @typedSignal.TypedSignal
    def measurementStageChanged(self, value: int) -> None:
        ...

    @typedSignal.TypedSignal
    def recordingProgressChanged(self, value: float) -> None:
        ...

    @typedSignal.TypedSignal
    def analysisEventsChanged(self) -> None:
        ...

    @typedSignal.TypedSignal
    def analysisProgressChanged(self, value: float) -> None:
        ...

    @typedSignal.TypedSignal
    def _sampleManuallyAdded(self) -> None:
        ...

    @typedSignal.TypedSignal
    def _saveLocationProvided(self, location: pathlib.Path) -> None:
        ...

    def __init__(self, backend: Backend) -> None:
        super().__init__()
        self._backend = backend
        self._operationsController = operationsController.OperationsController(
            backend.getEngines().coroutineCoordinator, self
        )
        self._historyController = history.HistoryController(
            backend.getEngines().historyEngine, backend.getEngines().afEngine, self
        )
        self._warmedUp = False
        self._recordingProgress = 0.0
        self._recordingLength = 1
        self._analysisEvents = []
        self._analysisProgress = 0.0
        self._measurementStage = MeasurementStage.WARMING_UP

        self._warmUpTimer = QtCore.QTimer(self)
        self._warmUpTimer.setSingleShot(True)
        self._warmUpTimer.timeout.connect(self._onWarmUpFinished)
        self._warmUpTimer.start(1000)

        analysisEngine = backend.getEngines().analysisEngine
        analysisEngine.setDisabled(False)
        analysisEngine.previewUpdated.connect(self._onAnalysisPreviewUpdated)

    def _getOperationsController(self) -> operationsController.OperationsController:
        return self._operationsController

    operationsController = typedSignal.TypedProperty(
        _getOperationsController, constant=True
    )

    def _getHistoryController(self) -> history.HistoryController:
        return self._historyController

    historyController = typedSignal.TypedProperty(_getHistoryController, constant=True)

    def _setMeasurementStage(self, stage: MeasurementStage) -> None:
        logger.info("Stage %s", stage)
        self._measurementStage = stage
        self.measurementStageChanged.emit(stage.value)

    def _getMeasurementStage(self) -> int:
        return self._measurementStage.value

    measurementStage = typedSignal.TypedProperty(
        _getMeasurementStage, notifySignal=measurementStageChanged
    )

    def _getRecordingProgress(self) -> float:
        return self._recordingProgress

    def _setRecordingProgress(self, progress: float) -> None:
        self._recordingProgress = progress
        self.recordingProgressChanged.emit(progress)

    recordingProgress = typedSignal.TypedProperty(
        _getRecordingProgress, notifySignal=recordingProgressChanged
    )

    def _getAnalysisEvents(self) -> list:
        return self._analysisEvents

    def _setAnalysisEvents(self, events: list) -> None:
        self._analysisEvents = events
        self.analysisEventsChanged.emit()

    analysisEvents = typedSignal.TypedProperty(
        _getAnalysisEvents, notifySignal=analysisEventsChanged
    )

    def _getAnalysisProgress(self) -> float:
        return self._analysisProgress

    def _setAnalysisProgress(self, progress: float) -> None:
        self._analysisProgress = progress
        self.analysisProgressChanged.emit(progress)

    analysisProgress = typedSignal.TypedProperty(
        _getAnalysisProgress, notifySignal=analysisProgressChanged
    )

    @typedSignal.TypedSlot
    def startMeasurement(self) -> None:
        self._backend.getEngines().coroutineCoordinator.submitRoutine(
            self._measurement(), self._onFinishedMeasurement
        )

    @typedSignal.TypedSlot
    def sampleManuallyAdded(self) -> None:
        self._sampleManuallyAdded.emit()

    @typedSignal.TypedSlot
    def setSaveLocation(self, location: QtCore.QUrl) -> None:
        self._saveLocationProvided.emit(pathlib.Path(location.toLocalFile()))

    @typedSignal.TypedSlot
    def poweroff(self) -> None:
        os.system("poweroff")
        QtCore.QCoreApplication.instance().quit()

    # Internals

    @typedSignal.TypedSlot
    def _onWarmUpFinished(self) -> None:
        self._setMeasurementStage(MeasurementStage.NOT_MEASURING)

    @typedSignal.TypedSlot
    def _onFinishedMeasurement(self, result: Optional[BaseException]) -> None:
        self._operationsController.reportResult(result)

    async def _addSampleCallback(
        self, stabMethod: focusStabiliser.AbstractFocusStabiliserMethod
    ) -> None:
        self._setMeasurementStage(MeasurementStage.ADD_SAMPLE)
        await coroUtils.awaitable_signal(self._sampleManuallyAdded)
        self._setMeasurementStage(MeasurementStage.REFINING_FOCUS)

    @typedSignal.TypedSlot
    def _onProgressUpdated(self, frameId: int) -> None:
        self._setRecordingProgress(frameId / self._recordingLength)

    @typedSignal.TypedSlot
    def _onAnalysisPreviewUpdated(self, results: Optional[AEResults]) -> None:
        if results is None:
            self._setAnalysisEvents([])
            self._setAnalysisProgress(0)
        else:
            self._setAnalysisEvents([float(c) for c in results.events.get_contrasts()])
            self._setAnalysisProgress(len(results.frameIds) / self._recordingLength)

    async def _measurement(self) -> None:
        with tempfile.NamedTemporaryFile(
            prefix="amp-lite-recording-", suffix=".mp", delete=False
        ) as tmpf:
            fname = pathlib.Path(tmpf.name)
        try:
            self._setMeasurementStage(MeasurementStage.FINDING_FOCUS)
            await findFocus.bufferFreeFindFocus(
                self._backend,
                self._addSampleCallback,
                stabiliseFocusMethodHint=focusStabiliser.StabiliseFocusMethodHint.RING_FREE,
            )
            self._setMeasurementStage(MeasurementStage.RECORDING)
            self._recordingLength = round(
                self._backend.getPhotometer().acqCam.getSettings().binnedFrameRate
                * 60
            )
            await common.recordMovie(
                self._backend.getEngines().recordingEngine,
                nFrames=self._recordingLength,
                filepath=fname,
                progressCallback=self._onProgressUpdated,
                measurementDetails=abstractCodec.MeasurementDetails(
                    self._backend.getEngines().recordingEngine.getBlankMeasurementInfo()
                ),
                codec=lambda f: mpxWriterCodec.MPWriterCodec(f, compressed=False),
            )
            self._setMeasurementStage(MeasurementStage.SAVE_LOCATION)
            (saveLocation,) = await coroUtils.awaitable_signal(
                self._saveLocationProvided
            )
            shutil.move(str(fname), saveLocation)
        finally:
            if fname.exists():
                fname.unlink()
            self._setMeasurementStage(MeasurementStage.NOT_MEASURING)


# Cannot use QmlElement with Cython, due to the way Cython overrides globals()
QtQml.qmlRegisterUncreatableType(LiteController, "refeyn.lite", 1, 0, "LiteController", "")  # type: ignore[arg-type]


class QMLLiveHistogram(quickGraphicsView.QuickGraphicsView):
    def __init__(
        self,
        parent: Optional[QtQuick.QQuickItem] = None,
    ) -> None:
        super().__init__(parent)

        self._analysisEvents = []
        self._widget.xAutoRange = True

    def _createWidget(self) -> QtWidgets.QGraphicsWidget:
        return liveHistogramWidget.LiveHistogramWidget()

    @typedSignal.TypedSignal
    def analysisEventsChanged(self) -> None:
        ...

    def _getAnalysisEvents(self) -> list:
        return self._analysisEvents

    def _setAnalysisEvents(self, events: list) -> None:
        self._analysisEvents = events
        print(len(events))
        self._widget.setData(self._analysisEvents)
        self.analysisEventsChanged.emit()

    analysisEvents = typedSignal.TypedProperty(
        _getAnalysisEvents, _setAnalysisEvents, notifySignal=analysisEventsChanged
    )


QtQml.qmlRegisterType(QMLLiveHistogram, "refeyn.lite", 1, 0, "QMLLiveHistogram")
