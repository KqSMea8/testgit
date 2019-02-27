cordova.define("cordova-plugin-voice.voice", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        //开始录音
        startVoice: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVVoice", "startVoice",[]);
        },
        //播放录音
        playVoice: function (addSuc, addFailed, arg1) {
            exec(addSuc, addFailed, "CDVVoice", "playVoice", [arg1]);
        },
       //停播放音
       stopPlayVoice: function (addSuc, addFailed) {
       exec(addSuc, addFailed, "CDVVoice", "stopPlayVoice", []);
       }
    };
});