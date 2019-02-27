cordova.define("cordova-plugin-share.share", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        onMenuShare: function (addSuc, addFailed, arg1) {
            exec(addSuc, addFailed, "CDVShare", "onMenuShare", [arg1]);
        }
    };
});