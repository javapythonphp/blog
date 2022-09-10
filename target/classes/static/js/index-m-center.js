// JavaScript Document
$(document).ready(function(){
	/*博客窗口滚动*/
	var contents = document.getElementsByClassName("index-content");
		for(var i=0;i<contents.length;i++){
			if(contents[i].offsetTop-$(window).scrollTop()<850){
                 contents[i].setAttribute("class","index-content index-content-trans")
			}
		}
    $(window).scroll(function(){		
		for(var i=0;i<contents.length;i++){
			if(contents[i].offsetTop-$(window).scrollTop()<850){
                 contents[i].setAttribute("class","index-content index-content-trans")
			}else{
			     contents[i].setAttribute("class","index-content")
		    }
		}
	})

})

function backImgBig(t){
	$(t).children(".content-backImg").children("img").addClass("trans");
}

function backImgSmall(t){
	$(t).children(".content-backImg").children("img").removeClass("trans");
}