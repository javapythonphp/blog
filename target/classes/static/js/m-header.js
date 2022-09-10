// JavaScript Document
$(document).ready(function(){
	$(".m-header .head-containner .header-button").click(function(e){
		$(this).addClass("button-bottom-border").siblings().removeClass("button-bottom-border")

        /*右侧控制*/
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

		}
        /*中心最大div高度控制*/
		setTimeout(function(){
			$len1 = $(".center-container").css("height");
			$(".m-center").css("min-height",$len1);
		},20)
	})
});