/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function () {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function () {
        var back = document.getElementById("back");
        back.addEventListener("click", this.onBack, false);
    },
    onBack: function () {
//        window.location.href = "../demo/home.html";
        history.back(-1);
    }
};

//网络状态
function onNetworkType() {
    jmportal.device.getNetworkType(function (data) {
        alert(data);
    }, function (data) {
        alert("networkType error");
    });
};
//用户注册（浙江为例）
function onRegisterUser() {
    jmportal.login.registerUser(function (data) {
                               alert(data);
                               }, function (data) {
                               alert("RegisterUser error");
                               });
};
//找回密码（浙江为例）
function onModifyPassword() {
    jmportal.login.modifyPassword(function (data) {
                               alert(data);
                               }, function (data) {
                               alert("ModifyPassword error");
                               });
};
//获得用户信息
function onGetUserInfo() {
    jmportal.login.getUserInfo(function (data) {
                               if(data == '未登录'){
                               alert("当前未登录,无用户信息");
                               }else{
                                alert(data);
                               }
                               }, function (data) {
                           alert("GetUserInfo error");
                           });
};
//登录
function onLoginApp() {
    jmportal.login.loginApp(function (data) {
                            alert(data);
                            }, function (data) {
                            alert("LoginApp error");
                            });
    
};
//注销
function onLogout() {
    jmportal.login.logout(function (data) {
                           alert(data);
                           }, function (data) {
                           alert("Logout error");
                           });
};
//登录到qq
function onLoginQQ() {
    jmportal.login.loginQQ(function (data) {
                           if (typeof data === 'string' && data.constructor == String) {
                           alert(data);
                           } else {
                           var userName = data.userName;//用户昵称
                           var userIcon = data.userIcon;//用户头像的url
                           var userId = data.userId;//用户id
                           alert("用户昵称:" + userName + "\n用户头像url:" + userIcon + "\n用户ID:" + userId);
                           }
                           ;
                           }, function (data) {
                           alert("login QQ error");
                           });
};
//注销qq
function onLogoutQQ() {
    jmportal.login.logoutQQ(function (data) {
                            
                            }, function (data) {
                            alert("logout QQ error");
                            });
};
//登录到腾讯微博
function onLoginTencentWeibo() {
    jmportal.login.loginTencentWeibo(function (data) {
                                     if (typeof data === 'string' && data.constructor == String) {
                                     alert(data);
                                     } else {
                                     var userName = data.userName;//用户昵称
                                     var userIcon = data.userIcon;//用户头像的url
                                     var userId = data.userId;//用户id
                                     alert("用户昵称:" + userName + "\n用户头像url:" + userIcon + "\n用户ID:" + userId);
                                     }
                                     ;
                                     }, function (data) {
                                     alert("login TencentWeibo error");
                                     });
};
//注销腾讯微博
function onLogoutTencentWeibo() {
    jmportal.login.logoutTencentWeibo(function (data) {
                                      
                                      }, function (data) {
                                      alert("logout TencentWeibo error");
                                      });
};
//登录到新浪微博
function onLoginSinaWeibo() {
    jmportal.login.loginSinaWeibo(function (data) {
                                  if (typeof data === 'string' && data.constructor == String) {
                                  alert(data);
                                  } else {
                                  var userName = data.userName;//用户昵称
                                  var userIcon = data.userIcon;//用户头像的url
                                  var userId = data.userId;//用户id
                                  alert("用户昵称:" + userName + "\n用户头像url:" + userIcon + "\n用户ID:" + userId);
                                  }
                                  ;
                                  }, function (data) {
                                  alert("login SinaWeibo error");
                                  });
};
//注销新浪微博
function onLogoutSinaWeibo() {
    jmportal.login.logoutSinaWeibo(function (data) {
                                   
                                   }, function (data) {
                                   alert("logout SinaWeibo error");
                                   });
};

//获得设备唯一标示
function onUUID() {
    jmportal.device.getUUID(function (data) {
        alert(data);
    }, function (data) {
        alert("UUID error");
    });
};



//计算距离
function onGetDistance(arg0) {
    
    jmportal.device.getDistance(function (data) {
                            alert("距离:"+data);
                            }, function (data) {
                            alert("UUID error");
                            },arg0);
};
//获得坐标
function onLocation() {
    jmportal.device.getLocation(function (data) {
//        document.write()
        if (typeof data === 'string' && data.constructor == String) {
            alert(data);
        } else {
            var cityName = data.cityName;
            var region = data.region;
            var detailAddress = data.detailAddress;
            var longitude = data.longitude;
            var latitude = data.latitude;
            alert("经度:" + longitude + "\n纬度:" + latitude + "\n城市名:" + cityName + "\n区域:" + region + "\n详细地址:" + detailAddress);
            }
        ;
    }, function (data) {
        alert("location error");
    });
};

function setCookie(key,value){
    document.cookie=key+ "=" +escape(value);
    jmportal.device.setCookieValue(function (data) {
                                       }, function (data) {
                                       },key,value);
    

}

function getCookie(key){

    if(document.cookie.length>0){

        c_start=document.cookie.indexOf(key + "=");
        if(c_start!=-1){
            c_start=c_start + key.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if(c_end==-1) c_end=document.cookie.length;
            var result = unescape(document.cookie.substring(c_start,c_end));
            return result;
        }
    }
        jmportal.device.getCookieValue(function (data) {
                                       }, function (data) {
                                       },key);
        return "";
}

function removeAllCookieValue()
{
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;)
            document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()
            }
        jmportal.device.removeAllCookieValue(function (data) {
                                        }, function (data) {
                                        });

}
//选取图片
var picPath = new Array();
function onChooseImage(arg0) {
    
    jmportal.camera.chooseImage(function (data) {
        picPath = data;
        alert(data)
//        if (document.getElementsByClassName("images")) {
//            var img = document.getElementsByClassName("images");
//            for (var i = img.length - 1; i >= 0; i--) {
//                document.getElementById("cameraBody").removeChild(img[i]);
//            }
//        }
//        ;
        for (var i = 0; i < data.length; i++) {
            var img = document.createElement("img");
            img.className = "images";                                
            img.src = data[i];
                                
            document.getElementById("cameraBody").appendChild(img);
            var link = document.createElement("link");
            link.setAttribute("rel", "stylesheet");
            link.setAttribute("type", "text/css");
            link.setAttribute("href", "../css/camera.css");
            document.getElementById("cameraBody").appendChild(link);
        }
    }, function (data) {
        alert("choose image error");
    },arg0);
};
//选取视频
var videopath;
function onChooseVideo() {
    jmportal.video.getVideo(function (data) {
                            var videoStr= " <div><p class='btnInfo'>拍设或从手机中选视频接口</p><button class='sbtn' onclick=onChooseVideo()>chooseVideo</button></div> <div align='center'><video src='"+data+"'  width='100' height='200'"+ "controls='controls'>"+
                            "<source src='/i/movie.ogg' type='video/ogg'>"+"<source src='/i/movie.mp4' type='video/mp4'></video></div>"
                               document.getElementById("videoBody").innerHTML = videoStr;
                            videopath = data;
//                                  document.getElementById("cameraBody").appendChild(videoStr);
                            }, function (data) {
                            alert("getVideo error");
                            });
};
function onChooseVideoAndPic() {
    
    jmportal.camera.chooseVideoAndPic(function (data) {
                                picPath = data;
                                
                                //        if (document.getElementsByClassName("images")) {
                                //            var img = document.getElementsByClassName("images");
                                //            for (var i = img.length - 1; i >= 0; i--) {
                                //                document.getElementById("cameraBody").removeChild(img[i]);
                                //            }
                                //        }
                                //        ;
                                for (var i = 0; i < data.length; i++) {
                                var img = document.createElement("img");
                                img.className = "images";
                                img.src = data[i];
                                
                                document.getElementById("cameraBody").appendChild(img);
                                var link = document.createElement("link");
                                link.setAttribute("rel", "stylesheet");
                                link.setAttribute("type", "text/css");
                                link.setAttribute("href", "../css/camera.css");
                                document.getElementById("cameraBody").appendChild(link);
                                }
                                }, function (data) {
                                alert("choose image error");
                                });

    
}

//音频
var audiopath = new Array();

function onStartVoice() {
    var index = audiopath.length;
    jmportal.voice.startVoice(function (data) {
        audiopath[index] = data;
    }, function (data) {
        alert("start voice error");
    });
};

function onPlayVoice() {
    var index = audiopath.length;
    var audio = audiopath[index - 1];
    if(audio==null) {
        alert("请先使用startVoice接口录制一段声音");
    }
    jmportal.voice.playVoice(function (data){
                             alert(data);
    }, function (data) {
        alert("play voice error");
    },audio);//传入要播放的音频文件的路径
};
function onStopPlayVoice() {
    if(audiopath==null) {
        alert("请先使用startVoice接口录制一段声音并播放");
    }
    jmportal.voice.stopPlayVoice({
                             }, function (data) {
                             alert("stopPlayVoice voice error");
                             });//传入要播放的音频文件的路径
};




//二维码
function onGetQRCode() {
    jmportal.QRCode.getQRCode(function (data) {
        alert(data);
    }, function (data) {
        alert("QRCode error");
    });
};
//分享
function onShare() {
    //自定义分享内容
    var titleStr = "测试内容的标题";//标题，如果没有赋空值
    var contentStr = "这是测试的分享内容";//内容，如果没有赋空值
    var shareUrlStr = "http://www.baidu.com";//分享的链接，如果没有赋空值
    var imageStr = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";//图片的url地址,如果没有赋空值
    var dic = {"titleStr":titleStr,"contentStr":contentStr,"shareUrlStr":shareUrlStr,"imageStr":imageStr};
    jmportal.share.onMenuShare(function (data) {
        alert(data);
    }, function (data) {
        alert("share error");
    },dic);//传入分享的参数
};
//保存数据
function onSetItem() {
    var key = document.getElementById("key").value;//传入要保存数据的key
    var value = document.getElementById("value").value;//传入要保存数据的value
    jmportal.storage.setItem(function (data) {
        alert(data);
    }, function (data) {
        alert("setItem error");
    },key,value);

};
//支付
function onPay() {
    var key = document.getElementById("order_id").innerHTML;//传入订单号
    var value = document.getElementById("good_name").innerHTML;//传入商品名
    var key1 = document.getElementById("all_price").innerHTML;//传入价格
    var value1 = document.getElementById("time").innerHTML;//传入时间
//    alert(key);
    jmportal.pay.pay(function (data) {
                             alert(data);
                             }, function (data) {
                             alert("pay error");
                             },key,value,key1,value1);
};
//读取数据
function onGetItem() {
    var key = document.getElementById("keyForRead").value;//传入需要读取数据的key值
    jmportal.storage.getItem(function (data) {
        alert(data);
    }, function (data) {
        alert("getItem error");
    },key);
};
//删除数据
function onRemoveItem() {
    var  key = document.getElementById("keyForDelete").value;//传入要移除的数据的key值
    jmportal.storage.removeItem(function (data) {
        alert(data);
    }, function (data) {
        alert("removeItem error");
    },key);
};
//页面控制
function onShowOrHiddenNav(arg0,arg1,arg2,arg3) {
    jmportal.showOrHiddenNav.showOrHiddenNav(function (data) {
                                alert(data);
                                }, function (data) {
                                alert("showNav error");
                                },arg0,arg1,arg2,arg3);
};
//提交
function onSubmit(arg0) {
    
    
    var content = document.getElementById("jmpinfo").value;
    jmportal.upload.onSubmit(function (data) {
             alert(data);
            picPath = null;
            audiopath = null;
             }, function (data) {
             alert("upload error");
             },arg0,content,videopath,audiopath,picPath);
};
//打电话
function onCallPhone(arg0) {
    jmportal.communication.callPhone(function (data) {
                             alert(data);
                             }, function (data) {
                             alert("upload error");
                             },arg0);
};//发短信
function onSendMessage(arg0) {
    jmportal.communication.sendMessage(function (data) {
                             alert(data);
                             }, function (data) {
                             alert("upload error");
                             },arg0);
};//发邮件
function onSendEmail(arg0) {
    jmportal.communication.sendEmail(function (data) {
                             alert(data);
                             }, function (data) {
                             alert("upload error");
                             },arg0);
};






app.initialize();