cordova.define("cordova-plugin-upload.upload", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        //上传接口
        onSubmit: function (addSuc, addFailed,arg0,arg1,arg2,arg3,arg4) {
            exec(addSuc, addFailed, "CDVUpload", "onSubmit",[arg0,arg1,arg2,arg3,arg4]);
        },
        };
});