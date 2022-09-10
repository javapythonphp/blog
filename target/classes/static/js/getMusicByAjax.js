/*var data1Obj
var data2Obj
var songListAjax = document.getElementsByClassName("songList")[0]
$.ajax({
    async:false,
    type:"post",
    url:"https://api.imjad.cn/cloudmusic/?type=playlist&id=2882628380",
    success:function(data0){
        var list = data0.playlist.trackIds;

        list.forEach(item=>{*/
            /*---------------------------------------------------*/
         /*   $.ajax({
                async:false,
                type:"get",
                url:"https://music.jeeas.cn/v1/music/detail?id="+item.id+"&from=music",
                success:function(data1){
                    data1Obj = JSON.parse(data1);*/
                    /*---------------------------------------------------*/
                   /* $.ajax({
                        async:false,
                        type:"get",
                        url:"https://music.jeeas.cn/v1/music/url?id="+item.id+"&from=music",
                        success:function(data2){
                            data2Obj = JSON.parse(data2);

                            var newDiv1 = document.createElement("div");
                            newDiv1.setAttribute("class","song");
                            newDiv1.setAttribute("onmouseover","onover(this)");
                            newDiv1.setAttribute("onmouseout","onout(this)");
                            newDiv1.setAttribute("data-value",data2Obj.data[0].url);
                            newDiv1.setAttribute("data-pic",data1Obj.songs[0].al.picUrl);
                            newDiv1.innerHTML = data1Obj.songs[0].name+"-"+data1Obj.songs[0].ar[0].name;

                            var divOuter = document.createElement("div");
                            divOuter.setAttribute.classList.add("song-out");
                            divOuter.setAttribute.setAttribute("onclick","changeMusic(this)");

                            divOuter.appendChild(newDiv1);
                            songListAjax.appendChild(divOuter);


                        }
                    });*/
                    /*---------------------------------------------------*/
              /*  }
            });*/
            /*---------------------------------------------------*/

       /* });
    }
});
*/