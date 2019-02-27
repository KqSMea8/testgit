cordova.define("cordova-plugin-storage.storage", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
//      保存数据
        setItem: function (addSuc, addFailed, arg1, arg2) {
            exec(addSuc, addFailed, "CDVStorage", "setItem", [arg1, arg2]);
        },
//      读取数据
        getItem: function (addSuc, addFailed, arg1) {
            exec(addSuc, addFailed, "CDVStorage", "getItem", [arg1]);
        },
//      删除数据
        removeItem: function (addSuc, addFailed, arg1) {
            exec(addSuc, addFailed, "CDVStorage", "removeItem", [arg1]);
        }
    };
});