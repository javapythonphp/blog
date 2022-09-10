// JavaScript Document
var items = document.getElementsByClassName('m-item');	
  for (var i = 0,l = items.length; i < l; i++) {
	items[i].style.left = (50 - 35 * Math.cos( - 0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%";
	items[i].style.top = (50 + 35 * Math.sin( - 0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%"
}

$(document).ready(function(){
	/*元素是否完全显示*/
	let $flag = false;
	$("body").click(function(){
		if($(".m-ring").hasClass("m-open")&&$flag){

			setTimeout(function(){
				$(".m-ring").removeClass("m-open");
			},50)

			setTimeout(function(){
				$(".m-GalDown").css("display","none");
				$flag = false
			},500);
		}
	});

	$("body").on("contextmenu",function(e){
		var left = e.pageX-150;
		var top = e.pageY-150;
		  if(e.clientX<220&& $(".m-side").hasClass("side-trans")){
			  
		  }else{
			  e.preventDefault();
			  e.stopPropagation();
			if(!$(".m-ring").hasClass("m-open")&&!$flag){

				$(".m-GalDown").css({
					top: top + 'px',
					left: left + 'px',
					display: 'block'
				})

                setTimeout(function(){
					$(".m-ring").addClass("m-open")
				},50)

				setTimeout(function(){
					$flag = true;
				},500)
			}else if($(".m-ring").hasClass("m-open")&&$flag){

				$(".m-GalDown").css({
					top: top + 'px',
					left: left + 'px',
				})

				setTimeout(function(){
					$(".m-ring").removeClass("m-open");
				},50)

				setTimeout(function(){
					$(".m-GalDown").css("display","none");
					$flag = false
				},500);
			}
		  }
	})

	
});