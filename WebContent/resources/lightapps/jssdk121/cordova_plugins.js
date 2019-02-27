cordova.define('cordova/plugin_list', function (require, exports, module) {
    module.exports = [
                      {
                      "file": "plugins/cordova-plugin-pay/pay.js",
                      "id": "cordova-plugin-pay.pay",
                      "clobbers": [
                                   "jmportal.pay"
                                   ]
                      },
        {
            "file": "plugins/cordova-plugin-device/device.js",
            "id": "cordova-plugin-device.device",
            "clobbers": [
                "jmportal.device"
            ]
        },
        {
            "file": "plugins/cordova-plugin-camera/camera.js",
            "id": "cordova-plugin-camera.camera",
            "clobbers": [
                "jmportal.camera"
            ]
        },
        {
            "file": "plugins/cordova-plugin-voice/voice.js",
            "id": "cordova-plugin-voice.voice",
            "clobbers": [
                "jmportal.voice"
            ]
        },
        {
            "file": "plugins/cordova-plugin-qrcode/qrcode.js",
            "id": "cordova-plugin-qrcode.qrcode",
            "clobbers": [
                "jmportal.QRCode"
            ]
        },
        {
            "file": "plugins/cordova-plugin-share/share.js",
            "id": "cordova-plugin-share.share",
            "clobbers": [
                "jmportal.share"
            ]
        },
        {
            "file": "plugins/cordova-plugin-login/login.js",
            "id": "cordova-plugin-login.login",
            "clobbers": [
                "jmportal.login"
            ]
        },
        {
            "file": "plugins/cordova-plugin-storage/storage.js",
            "id": "cordova-plugin-storage.storage",
            "clobbers": [
                "jmportal.storage"
            ]
        },
        {
            "file": "plugins/cordova-plugin-showOrHiddenNav/showOrHiddenNav.js",
            "id": "cordova-plugin-showOrHiddenNav.showOrHiddenNav",
            "clobbers": [
            "jmportal.showOrHiddenNav"
            ]
        },
      {
      "file": "plugins/cordova-plugin-video/video.js",
      "id": "cordova-plugin-video.video",
      "clobbers": [
                   "jmportal.video"
                   ]
      },
      {
      "file": "plugins/cordova-plugin-upload/upload.js",
      "id": "cordova-plugin-upload.upload",
      "clobbers": [
                   "jmportal.upload"
                   ]
      },
      {
      "file": "plugins/cordova-plugin-communication/communication.js",
      "id": "cordova-plugin-communication.communication",
      "clobbers": [
                   "jmportal.communication"
                   ]
      }
    ];
    module.exports.metadata =
        // TOP OF METADATA
    {
        "cordova-plugin-camera": "1.2.0"
    }
    // BOTTOM OF METADATA
});