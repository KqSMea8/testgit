cordova.define("cordova-plugin-video.video", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        getVideo: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVVideo", "chooseVideo",[]);
        }
   };
});