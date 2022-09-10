// JavaScript Document
/*右边是否完全显示*/
var $otherFlag = false;
$(document).ready(function(){

	$(".right-button").click(function(){
		if($(".center-other").hasClass("trans")&&$otherFlag){
			$(this).removeClass("trans")
			$(".center-container").css("display","block");
			/*先延时*/
			setTimeout(function(){
				$(".center-other").removeClass("trans");
				$(".center-container").removeClass("trans");
			},20)
			/*最大div高度控制*/
			setTimeout(function(){
				$len1 = $(".center-container").css("height");
				$(".m-center").css("min-height",$len1);
			},40)
			/*取消文档流*/
			setTimeout(function () {
				$(".center-other").css("display","none");
				$otherFlag = false;
			},820)

		}else if(!$(".center-other").hasClass("trans")&&!$otherFlag){
			$(this).addClass("trans")
			$(".center-other").css("display","block");
			/*先延时*/
			setTimeout(function(){
				$(".center-other").addClass("trans");
				$(".center-container").addClass("trans");
				$(".center-other").css("display","flex");
			},20)
			/*最大div高度控制*/
			setTimeout(function(){
				$len2 = $(".center-other").css("height");
				$(".m-center").css("min-height",$len2);
			},40)
			setTimeout(function(){
				$(".center-container").css("display","none");
				$otherFlag = true;
			},820);

		}

	})

	$(".side-button").click(function(){
		var left = $(".m-side")[0].offsetLeft;
		if(left==-220){
			$(".m-side").addClass("side-trans");
			$(".side-button").addClass("button-trans");
			$(".m-side .m-head img").addClass("img-trans")
			$(".m-side .m-head p").addClass("p-trans")
			$(".m-side .m-list>img").addClass("weixin-trans");
			/*标题文字*/
			$(".m-header .head-containner p").addClass("header-title-trans")
			/*bkn*/
			$(".waifu").addClass("trans");
			setTimeout(function(){
				$(".m-side .m-list ul>li").addClass("li-trans");
			},600)
		}else{
			$(".m-side").removeClass("side-trans");
			$(".side-button").removeClass("button-trans")
			$(".m-header .head-containner p").removeClass("header-title-trans")
			$(".waifu").removeClass("trans");
			setTimeout(function(){
				$(".m-side .m-head img").removeClass("img-trans")
				$(".m-side .m-head p").removeClass("p-trans")
				$(".m-side .m-list ul>li").removeClass("li-trans")
				$(".m-side .m-list>img").removeClass("weixin-trans");
			},300)
		}

	});



})