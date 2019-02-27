cordova.define("cordova-plugin-communication.communication", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        callPhone: function (addSuc, addFailed,arg0) {
            exec(addSuc, addFailed, "CDVCommunication", "callPhone",[arg0]);
        },

        sendMessage: function (addSuc, addFailed,arg0) {
            exec(addSuc, addFailed, "CDVCommunication", "sendMessage",[arg0]);

        },
               
       sendEmail: function (addSuc, addFailed,arg0) {
       exec(addSuc, addFailed, "CDVCommunication", "sendEmail",[arg0]);
       
       }
    };
});