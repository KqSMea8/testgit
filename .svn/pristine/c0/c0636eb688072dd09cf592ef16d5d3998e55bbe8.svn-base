cordova.define("cordova-plugin-login.login", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
       //注册
       registerUser: function (addSuc, addFailed) {
       exec(addSuc, addFailed, "CDVLogin", "registerUser",[]);
       },
       //修改密码
       modifyPassword: function (addSuc, addFailed) {
       exec(addSuc, addFailed, "CDVLogin", "modifyPassword",[]);
       },
       //注销
       logout: function (addSuc, addFailed) {
       exec(addSuc, addFailed, "CDVLogin", "logout",[]);
       },
        //获得原生登录信息
        getUserInfo: function (addSuc, addFailed) {
        exec(addSuc, addFailed, "CDVLogin", "getUserInfo",[]);
        },
        //登录到原生
        loginApp: function (addSuc, addFailed) {
        exec(addSuc, addFailed, "CDVLogin", "loginApp",[]);
        },
        //登录到QQ
        loginQQ: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLogin", "loginQQ",[]);
        },
        //注销QQ登录
        logoutQQ: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLogin", "logoutQQ",[]);
        },
        //登录到腾讯微博
        loginTencentWeibo: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLogin", "loginTencentWeibo",[]);
        },
        //注销腾讯微博登录
        logoutTencentWeibo: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLogin", "logoutTencentWeibo",[]);
        },
        //登录到新浪微博
        loginSinaWeibo: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLogin", "loginSinaWeibo",[]);
        },
        //注销新浪微博登录
        logoutSinaWeibo: function (addSuc, addFailed) {
            exec(addSuc, addFailed, "CDVLogin", "logoutSinaWeibo",[]);
        }
    };
});