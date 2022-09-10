// JavaScript Document.

$(document).ready(function(){
	
	$(".m-center .center-box").on("mouseover",function(){
		    $img = $(this).find(".imgSide");
		    $cover = $(this).find(".img-cover");
            $img.addClass("center-img-trans");
		    $cover.addClass("img-cover-trans");
	});
	$(".m-center .center-box").on("mouseleave",function(){
            $img = $(this).find(".imgSide");
		    $cover = $(this).find(".img-cover");
            $img.removeClass("center-img-trans");
		    $cover.removeClass("img-cover-trans");
	});
});