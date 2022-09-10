var imgs = document.getElementsByClassName("m-background");
var len = imgs.length;
var isFirst = true;
var recent;
var pre = 0;
var flow_musicArray ;
function change(){
    recent = Math.floor(Math.random()*len)
	imgs[pre].style['opacity'] = "0";
	imgs[recent].style['display'] = "block";
	setTimeout(function(){

			imgs[recent].style['opacity'] = "1";
			imgs[recent].style['transform'] = "scale(1.2)";

	},200)

	setTimeout(function(){
		imgs[pre].style['transform'] = "scale(1)";
		imgs[pre].style['display'] = "none";
		pre = recent;
	},4000)

}


window.onload = function(){

	SongList = document.getElementsByClassName("song");
	AudioSong = document.getElementById("audio");
	MusicPicture = document.getElementById("musicPicture");
	progressBar = document.getElementsByClassName("progressBar")[0];
	currentBar = document.getElementsByClassName("currentBar")[0];
	curtxt=document.getElementById("currenttime");
	Alltxt=document.getElementById("duration");



	if(document.getElementsByClassName("load-animation").length!=0){
		document.getElementsByClassName("load-animation").item(0).classList.add("page-cover-trans");
		setTimeout(function(){
			document.getElementsByClassName("load-animation").item(0).style['display'] = 'none';

		},2000)
	}

	if(isIndex==1) {
		setTimeout(function () {
			document.getElementsByClassName("side-button")[0].dispatchEvent(new Event("click"));
			document.getElementsByClassName("right-button")[0].dispatchEvent(new Event("click"));
		}, 1000)
	}else{
		setTimeout(function () {
			document.getElementsByClassName("right-button")[0].dispatchEvent(new Event("click"));
		}, 1000)
	}



	/*document.getElementById("img").style['opacity'] = "0";
	setTimeout(function(){
		document.getElementById("img").style['display'] = "none";
	},5000)
	change();
	setInterval(function(){
		change();
	},12000)*/
}



