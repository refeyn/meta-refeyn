import QtQuick
import QtQuick.Controls
import QtQuick.Layouts
import QtQuick.Window
import refeyn.theme
import refeyn.common
import refeyn.lite
import Palettes
import Controls
import ExtendedControls

Pane {
    id: root

    property string abortButtonText
    required property LiteController controller
    property bool nextButtonEnabled: true
    property string nextButtonText
    property bool showHistogram: false
    property string title: "hai %1".arg(controller.measurementStage)

    anchors.fill: parent
    palette: DarkPalette

    states: [
        State {
            name: "warming_up"
            when: controller.measurementStage == LiteController.WARMING_UP

            PropertyChanges {
                abortButtonText: "Shutdown"
                nextButtonEnabled: false
                nextButtonText: "Start measurement"
                target: root
                title: "Liteᴹᴾ is warming up..."
            }
            StateChangeScript {
                script: print("YO!")
            }
        },
        State {
            name: "ready"
            when: controller.measurementStage == LiteController.NOT_MEASURING

            PropertyChanges {
                abortButtonText: "Shutdown"
                nextButtonText: "Start measurement"
                target: root
                title: "Liteᴹᴾ is ready for use"
            }
            StateChangeScript {
                script: print("YO!")
            }
        },
        State {
            name: "finding_focus"
            when: controller.measurementStage == LiteController.FINDING_FOCUS

            PropertyChanges {
                abortButtonText: "Cancel"
                nextButtonEnabled: false
                nextButtonText: "Sample added"
                target: root
                title: "Finding focus..."
            }
            StateChangeScript {
                script: print("YO!")
            }
        },
        State {
            name: "add_sample"
            when: controller.measurementStage == LiteController.ADD_SAMPLE

            PropertyChanges {
                abortButtonText: "Cancel"
                nextButtonText: "Sample added"
                target: root
                title: "Focus found, please add your sample"
            }
            StateChangeScript {
                script: print("YO!")
            }
        },
        State {
            name: "refining_focus"
            when: controller.measurementStage == LiteController.REFINING_FOCUS

            PropertyChanges {
                abortButtonText: "Cancel"
                nextButtonEnabled: false
                nextButtonText: "Save measurement"
                target: root
                title: "Refining focus..."
            }
            StateChangeScript {
                script: print("YO!")
            }
        },
        State {
            name: "recording"
            when: controller.measurementStage == LiteController.RECORDING

            PropertyChanges {
                abortButtonText: "Cancel"
                nextButtonEnabled: false
                nextButtonText: "Save measurement"
                target: root
                title: "Recording measurement..."
            }
            StateChangeScript {
                script: print("YO!")
            }
        },
        State {
            name: "saving"
            when: controller.measurementStage == LiteController.SAVE_LOCATION

            PropertyChanges {
                abortButtonText: "Discard"
                nextButtonText: "Save measurement"
                target: root
                title: "Save recorded data"
            }
            StateChangeScript {
                script: print("YO!")
            }
        }
    ]

    ErrorDialog {
        text: controller.operationsController.error
        visible: controller.operationsController.error !== ""

        onClosed: controller.operationsController.resetError()
    }
    Dialog {
        id: shutdownDialog
        anchors.centerIn: Overlay.overlay
        modal: true
        standardButtons: Dialog.Ok | Dialog.Cancel
        title: "Are you sure you want to shutdown?"

        onAccepted: root.controller.poweroff()
    }
    ImageProvider {
        id: imageProvider
        autoscale: root.state != "recording"
        data: root.state == "recording" ? controller.historyController.ratiometricFrame : controller.historyController.nativeFrame
        vmax: root.state == "recording" ? 0.01 : 1
        vmin: root.state == "recording" ? -0.01 : 0
    }
    ColumnLayout {
        anchors.fill: parent

        Label {
            font.pixelSize: 18
            text: root.title
        }
        Label {
            Layout.fillHeight: true
            Layout.fillWidth: true
            text: "To begin, apply oil to the objective, and add a coverslip, secured by the magnets.\n\nThen, align an unused gasket well with the center of the objective, and start the measurement process."
            verticalAlignment: Text.AlignVCenter
            visible: root.state == "ready"
            wrapMode: Text.Wrap
        }
        Row {
            visible: root.state == "recording"

            RadioButton {
                id: ratiometricSelected
                checked: true
                text: qsTr("Ratiometric")
            }
            RadioButton {
                text: qsTr("Histogram")
            }
        }
        ImageView {
            Layout.fillHeight: true
            Layout.fillWidth: true
            image: imageProvider.image
            visible: root.state == "finding_focus" || root.state == "add_sample" || root.state == "refining_focus" || root.state == "recording" && ratiometricSelected.checked
        }
        QMLLiveHistogram {
            Layout.fillHeight: true
            Layout.fillWidth: true
            analysisEvents: controller.analysisEvents
            visible: root.state == "recording" && !ratiometricSelected.checked
        }
        CheckBox {
            text: "Auto-detect sample addition"
            visible: root.state == "add_sample"
        }
        ProgressBar {
            Layout.fillHeight: root.state == "warming_up"
            Layout.fillWidth: true
            Layout.preferredHeight: 40
            from: 0
            indeterminate: root.state != "recording"
            to: 1
            value: root.controller.recordingProgress
            visible: root.state == "warming_up" || root.state == "finding_focus" || root.state == "refining_focus" || root.state == "recording"

            Rectangle {
                color: parent.palette.highlight
                height: 6
                implicitHeight: 6
                visible: root.state == "recording"
                width: parent.width * root.controller.analysisProgress
                y: (parent.height - height) / 2
                z: 2
            }
        }
        RowLayout {
            Item {
                Layout.fillWidth: true
            }
            Button {
                text: root.abortButtonText

                onClicked: {
                    if (root.state == "ready" || root.state == "warming_up") {
                        shutdownDialog.open();
                    } else {
                        controller.operationsController.cancelOperation();
                    }
                }
            }
            PrimaryButton {
                enabled: root.nextButtonEnabled
                text: root.nextButtonText

                onClicked: {
                    if (root.state == "ready") {
                        root.controller.startMeasurement();
                    } else if (root.state == "add_sample") {
                        root.controller.sampleManuallyAdded();
                    } else {
                        root.controller.setSaveLocation("C:/Users/matthew.joyce/lite.mp");
                    }
                }
            }
        }
    }
}
