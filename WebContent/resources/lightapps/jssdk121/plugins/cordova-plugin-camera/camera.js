cordova.define("cordova-plugin-camera.camera", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        chooseImage: function (addSuc, addFailed,arg0) {
            exec(addSuc, addFailed, "CDVCamera", "chooseImage",[arg0]);
        },
        chooseVideoAndPic: function (addSuc, addFailed) {
               exec(addSuc, addFailed, "CDVCamera", "chooseVideoAndPic",[]);
               }

    };
});