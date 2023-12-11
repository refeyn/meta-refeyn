# -----------------------------------------------------------------------------------------------------
# Copyright (C) Refeyn Ltd - All Rights Reserved
# Unauthorized copying of this file, via any medium is strictly prohibited
# Proprietary and confidential
# URL: https://www.refeyn.com
# -----------------------------------------------------------------------------------------------------

from iscat_utils.gui.messageBox import errorMessageBox

import liteController


def runUi(
    lController_: liteController.LiteController,
) -> None:
    engine = QtQml.QQmlApplicationEngine()
    engine.addImportPath(qml.QML_IMPORT_PATH)
    engine.addImportPath(commonQml.QML_IMPORT_PATH)
    engine.setInitialProperties({"controller": lController_})
    print()
    print("Loading...")
    engine.load(pathlib.Path(__file__).parent / "MainInABox.qml")
    if not engine.rootObjects():
        errorMessageBox("Could not load (check logs)")
    else:
        app.exec()


if __name__ == "__main__":
    import logging
    import pathlib
    import sys

    import lw8
    import lw8.data
    from iscat_utils.app import app as appModule
    from iscat_utils.gui import qml
    from lw8.backend import Backend
    from lw8.devices.factories import liteFactory
    from lw8.gui import commonQml
    from lw8.utils.default_config import DEFAULT_SETTINGS
    from lw8.utils.settings import SETTINGS_VERSION, AMPSettings
    from PySide6 import QtQml

    logging.getLogger().setLevel(logging.INFO)

    app = appModule.createApplication(sys.argv)
    backend = Backend(liteFactory.LitePhotometerFactory())
    backend.start(
        AMPSettings(
            version=SETTINGS_VERSION,
            deviceSerials=None,
            photometer=DEFAULT_SETTINGS[
                lw8.InstrumentType.LITE_MP, lw8.OperatorMode.MANUAL
            ],
        ),
        None,
    )
    lController = liteController.LiteController(backend)
    try:
        while True:
            runUi(lController)
    finally:
        backend.close()
