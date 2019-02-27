var u = navigator.userAgent, app = navigator.appVersion;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

if(isiOS) {
    require.config({
        paths: {
            "cordava": ["cordova_ios"]
        }
    })
} else {
    require.config({
        paths: {
            "cordava": ["cordova_android"]
        }
    })
}
require(["cordava"],function($){

})
