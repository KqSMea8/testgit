cordova.define("cordova-plugin-pay.pay", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        pay: function (addSuc, addFailed, arg1, arg2,arg3,arg4) {
            exec(addSuc, addFailed, "CDVPay", "pay",[arg1, arg2,arg3, arg4]);
        }
    };
});