// var addr = "http://localhost:8080";
// var url = "http://192.168.0.43:8082";
var addr = "http://smartmart.ge.com.cn";
var url = "http://smartmart.ge.com.cn";

$.postJSON = function (url, data, callback) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback
    });
};

$.addEvent = function (obj, type, handle) {
    //事件绑定，兼容ff，ie
    if (window.addEventListener) {
        obj.addEventListener(type, handle, false);
    } else if (window.attachEvent) {
        obj.attachEvent("on" + type, handle);
    } else {
        obj["on" + type] = handle;
    }
};

//解析服务器返回的数据
function parseData(data) {
    if (data.status == 200) {
        return data.data;
    } else if (data.status == 201) {
        alert("请进行登录!");
        window.location.href = "./index.html";
    } else {
        alert(data.msg);
    }
}

//校验cookie是否存在，不存在则跳转到首页
function checkToken() {
    var vtoken = getCookie("P-TOKEN");
    if (vtoken == null || vtoken == "" || vtoken == undefined) {
        window.location.href = "./index.html";
    }
}

//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

//获取cookie
function getCookie(cookieName) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (cookieName == arr[0]) {
            return arr[1];
        }
    }
    return "";
}

//获取url参数方法
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return  decodeURI(r[2]);
    return null;
}