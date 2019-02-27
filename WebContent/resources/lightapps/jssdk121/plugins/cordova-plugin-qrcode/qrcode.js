cordova.define("cordova-plugin-qrcode.qrcode", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        //开始录音
        getQRCode: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVQRCode", "getQRCode",[]);
        }
    };
});