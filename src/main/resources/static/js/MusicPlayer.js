/*click无法外联，不知道为什么*/
var preBound;
var Boundflag = false;
var currentSongIndex = 0;
var SongList= document.getElementsByClassName("song");
var len = SongList.length;
var AudioSong = document.getElementById("audio");
var MusicPicture = document.getElementById("musicPicture");
/*滚动条*/
var progressBar = document.getElementsByClassName("progressBar")[0];
var currentBar = document.getElementsByClassName("currentBar")[0];
/*播放时间*/
var curtxt=document.getElementById("currenttime");
var Alltxt=document.getElementById("duration");
/*当前播放对象*/
var currentObject;
/*window.onload = function(){
    SongList = document.getElementsByClassName("song");
    AudioSong = document.getElementById("audio");
    MusicPicture = document.getElementById("musicPicture");
    progressBar = document.getElementsByClassName("progressBar")[0];
    currentBar = document.getElementsByClassName("currentBar")[0];
    curtxt=document.getElementById("currenttime");
    Alltxt=document.getElementById("duration");

}*/

function changeMusic(t){
    currentSongIndex = $(t).index();
    currentObject = SongList[currentSongIndex];
    document.getElementById("playPause").innerHTML = "<i class=\"pause icon large\"></i>";
    if(t==preBound) {
        if (!Boundflag) {
            $(t).children().addClass("bounder");
            $(t).siblings().children().remove("bounder");
            Boundflag = true;
            setTimeout(function () {
                $(t).children().removeClass("bounder");
                Boundflag = false;
            }, 2200)
        }
    }
    else{
        $(t).siblings().children().removeClass("bounder");
        $(t).children().addClass("bounder");
        Boundflag = true;
        setTimeout(function () {
            $(t).children().removeClass("bounder");
            Boundflag = false;
        }, 2200)
        preBound = t;
    }
    $("#audio").attr("src",$(t).children().data("value"));
    $(t).addClass("back").siblings().removeClass("back");
    MusicPicture.setAttribute("src",$(t).children().data("pic"))
    if(AudioSong.paused){
        AudioSong.play();
    }
}

function PreSong(t){
    currentObject = t;
    toLargeBoreder(t)
    currentSongIndex = currentSongIndex==0?SongList.length-1:currentSongIndex-1;
    updateMusic(currentSongIndex)
}
function NextSong(t){
    currentObject = t;
    toLargeBoreder(t)
    currentSongIndex = currentSongIndex==SongList.length-1?0:currentSongIndex+1;
    updateMusic(currentSongIndex)
}
function updateMusic(currentIndex){
    $("#audio").attr("src",$(SongList[currentIndex]).data("value"));
    MusicPicture.setAttribute("src",$(SongList[currentIndex]).data("pic"));
    $(SongList[currentIndex]).parent().addClass("back").siblings().removeClass("back");
    document.querySelector("#playPause").innerHTML = "<i class=\"pause icon large\"></i>";
}

function PlayOrPause(t){
    toLargeBoreder(t)
    if(AudioSong.paused){
        AudioSong.play();
        t.innerHTML = "<i class=\"pause icon large\"></i>";
    }else{
        AudioSong.pause();
        t.innerHTML = "<i class=\"play icon large\"></i>";
    }
}
function toLargeBoreder(t){
    t.style['transform'] = "scale(1.2,1.2)"
    t.style['border-color'] = "red"
    setTimeout(function(){
        t.style['transform'] = "scale(1,1)"
        t.style['border-color'] = "black"
    },500)
}


function onover(t){
    $(t).css({"font-size":"22px","color":"aqua","cursor":"Pointer"});
}

function onout(t){
    $(t).css({"font-size":"18px","color":"#fff"});
}

function scrollChange(e){
    let off = getLeft(e);
    let realoffLeft = window.event.pageX-off+655;
    AudioSong.currentTime = AudioSong.duration*realoffLeft/295;
    currentBar.style['width'] = realoffLeft+"px";
}

function getLeft(e){
       let offset=e.offsetLeft;
       if(e.offsetParent!=null) offset+=getLeft(e.offsetParent);
       return offset;
}

function curtime(txt,music)
{
    if(music.currentTime<10)
    {
        txt.innerHTML="0:0"+Math.floor(music.currentTime);
    }
    else if(music.currentTime<60)
    {
        txt.innerHTML="0:"+Math.floor(music.currentTime);
    }
    else
    {
        var minet=parseInt(music.currentTime/60);
        var sec=music.currentTime-minet*60;
        if(sec<10)
        {
            txt.innerHTML="0"+minet+":"+"0"+parseInt(sec);
        }
        else
        {
            txt.innerHTML="0"+minet+":"+parseInt(sec);
        }
    }
}
/*歌曲全部时间*/
function AllTime(txt,music){
    txt.innerHTML = parseInt(music.duration/60)+":"+parseInt(music.duration%60);
}


function AfterEnded(){
    NextSong(currentObject);
}

setInterval(function(){
    currentBar.style['width'] = 295*AudioSong.currentTime/AudioSong.duration+"px";
    AllTime(Alltxt,AudioSong);
    curtime(curtxt,AudioSong);
},50)

