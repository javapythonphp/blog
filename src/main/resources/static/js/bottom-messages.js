$(function(){
    var $bottom = $(".footer-introduce:eq(0)").children("p");
    $bottom.eq(0).text("您的浏览器名:"+window.navigator.appName);
    $bottom.eq(1).text("您的浏览器版本:"+window.navigator.appVersion);
    $bottom.eq(2).text("您的操作系统:"+window.navigator.platform);

    var $bottom2 = $(".footer-bottom:eq(0)").children();
    $bottom2.eq(0).text("您的用户头:"+window.navigator.userAgent+" 来自主机"+location.host)

})
