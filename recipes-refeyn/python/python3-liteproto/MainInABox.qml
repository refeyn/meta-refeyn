import QtQuick
import QtQuick.Controls
import refeyn.lite

Window {
    id: window

    required property LiteController controller

    contentOrientation: Qt.LandscapeOrientation
    minimumHeight: 300
    minimumWidth: 500
    visible: true

    Main {
        controller: window.controller
        height: parent.width
        width: parent.height

        transform: Rotation {
            angle: Screen.angleBetween(window.contentOrientation, Screen.orientation)
            origin.x: Screen.width / 2
            origin.y: Screen.width / 2
        }
    }
}
