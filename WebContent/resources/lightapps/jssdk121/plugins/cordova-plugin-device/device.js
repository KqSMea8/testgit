cordova.define("cordova-plugin-device.device", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        //获取设备的uuid
        getUUID: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVUUID", "getUUID",[]);
        },
        //获取网络状态
        getNetworkType: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVNetworkType", "getNetworkType",[]);
        },
        //获取地理位置
        getLocation: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLocation", "getLocation",[]);
        },
       //计算地理位置
       getDistance: function (addSuc, addFailed,arg0) {
       exec(addSuc, addFailed, "CDVLocation", "getDistance",[arg0]);
       },
       //设置cookie
       setCookieValue: function (addSuc, addFailed,arg0,arg1) {
       exec(addSuc, addFailed, "CDVCookie", "setCookie",[arg0,arg1]);
       },
       //取cookie
       getCookieValue: function (addSuc, addFailed,arg0) {
       exec(addSuc, addFailed, "CDVCookie", "getCookie",[arg0]);
       },
       //清除cookie
       removeAllCookieValue: function (addSuc, addFailed) {
       exec(addSuc, addFailed, "CDVCookie", "removeallCookie",[]);
       }

    };
});