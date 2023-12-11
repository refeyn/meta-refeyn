import contextlib
import multiprocessing
import os
import sys


@contextlib.contextmanager
def dummy_cm(*args):
    yield None


os.environ["LOCALAPPDATA"] = "/home/root/.local/share"
os.environ["PROGRAMDATA"] = "/etc/"
os.add_dll_directory = dummy_cm
sys.modules["hdf5plugin"] = sys

sys.Zstd = lambda clevel: {"compression": 32015, "compression_opts": (clevel,)}

import multiprocessing.connection as mc

mc.PipeConnection = mc.Connection

import logging
import pathlib
import sys

import lw8
import lw8.data
from iscat_utils.app import app as appModule
from iscat_utils.app import exception
from iscat_utils.app.app import createApplication
from iscat_utils.app.appData import AppData
from iscat_utils.gui import qml
from iscat_utils.gui.messageBox import errorMessageBox
from iscat_utils.users import users
from lw8 import NAME_APP, __version__
from lw8.backend import Backend
from lw8.devices.factories import liteFactory, twoFactory
from lw8.gui import commonQml, preferences
from lw8.gui.mainWindow import liteMainWindow
from lw8.utils.default_config import DEFAULT_SETTINGS
from lw8.utils.settings import SETTINGS_VERSION, AMPSettings, loading
from iscat import InstrumentType, MeasurementMode, OperatorMode
from PySide6 import QtCore, QtQml, QtWidgets

import liteController

import prctl, threading

def _name_hack(self):
    prctl.set_name(self.name)
    threading.Thread._bootstrap_original(self)

threading.Thread._bootstrap_original = threading.Thread._bootstrap
threading.Thread._bootstrap = _name_hack

def _get_name(self):
    return threading.Thread._name_original.fget(self)

def _set_name(self, v):
    prctl.set_name(v)
    return threading.Thread._name_original.fset(self, v)

threading.Thread._name_original = threading.Thread.name
threading.Thread.name = property(_get_name, _set_name)

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
    multiprocessing.set_start_method("spawn")
    app = createApplication(
        sys.argv, nameApp=NAME_APP, namePublisher="Refeyn Ltd", icon="amp-logo"
    )
    QtCore.QThread.currentThread().setObjectName("mainThread")
    appData = AppData(NAME_APP, __version__)

    _loggingExceptionHook = exception.LoggingExceptionHook(
        appName=lw8.NAME_APP,
        filePath=lw8.APPDATA_FOLDER_PATH / appData.ERROR_LOG_FILE_NAME,
    )
    lw8.exceptionHookHolder = exception.ExceptionHookHolder(
        appName=lw8.NAME_APP,
        version=lw8.__version__,
        email=lw8.APP_EMAIL,
        hooks=[_loggingExceptionHook],
    )
    lw8.rollbarExceptionHook = exception.RollbarExceptionHook(
        appVersion=lw8.__version__,
        access_token=lw8.ROLLBAR_POST_ACCESS_TOKENS[lw8.rollbarEnvironment],
        environment=lw8.rollbarEnvironment,
        version_sha=lw8.VERSION_SHA,
        project_root=lw8.REPO_ROOT_DIR_PATH,
        internal=lw8.IS_LICENCE_INTERNAL,
    )

    settings = AMPSettings(
        version=SETTINGS_VERSION,
        deviceSerials=None,
        photometer=DEFAULT_SETTINGS[InstrumentType.LITE_MP, OperatorMode.MANUAL],
    )
    backend = Backend(liteFactory.LitePhotometerFactory())
    backend.start(settings, print)
    #    preferences.initialisePreferences(appData.getFolderPath() / "preferences.json")
    lController = liteController.LiteController(backend)
    try:
        while True:
            runUi(lController)
    finally:
        backend.close()
    print("Bye")
