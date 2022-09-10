// JavaScript Document
$(document).ready(function(){
	var len = $(".m-footer").offset().top-$(window).scrollTop();
	if(len<=900){
	        $(".footer-container .footer-about").addClass("footer-trans");
			$(".footer-container .footer-connect").addClass("footer-trans");
			$(".footer-container .footer-introduce").addClass("footer-trans");
	}
	$(window).scroll(function(){
		len = $(".m-footer").offset().top-$(window).scrollTop();
		if(len<=900){
			$(".footer-container .footer-about").addClass("footer-trans");
			$(".footer-container .footer-connect").addClass("footer-trans");
			$(".footer-container .footer-introduce").addClass("footer-trans");
		}else{
			$(".footer-container .footer-about").removeClass("footer-trans");
			$(".footer-container .footer-connect").removeClass("footer-trans");
			$(".footer-container .footer-introduce").removeClass("footer-trans");
		}
	})
});