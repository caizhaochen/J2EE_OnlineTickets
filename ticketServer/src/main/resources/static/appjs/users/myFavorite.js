window.onload=function () {
    myFavorite();
}

function myFavorite() {
    Vue.filter('time',
        <!-- value 格式为13位unix时间戳 -->
        <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
        function (value) {
            var date = new Date(value);
            Y = date.getFullYear(),
                m = date.getMonth() + 1,
                d = date.getDate(),
                H = date.getHours(),
                i = date.getMinutes(),
                s = date.getSeconds();
            if (m < 10) {
                m = '0' + m;
            }
            if (d < 10) {
                d = '0' + d;
            }
            if (H < 10) {
                H = '0' + H;
            }
            if (i < 10) {
                i = '0' + i;
            }
            if (s < 10) {
                s = '0' + s;
            }
            <!-- 获取时间格式 2017-01-03 10:13:48 -->
            var t = Y + '-' + m + '-' + d + ' ' + H + ':' + i + ':' + s;
            <!-- 获取时间格式 2017-01-03 -->
            // var t = Y + '-' + m + '-' + d;
            return t;
        });

    var vue=new Vue({
        el:"#favoriteVue",
        data:{
            username:'您还没有登录',
            userlevel:0,
            userEmail:'',

            showNormal:true,
            showPasts:false,

            hasNormal:false,
            hasPast:false,

            normal:[],
            past:[],

        },
        methods:{
            normalClick:function () {
                $("#normalA").css("color","#31BBAC");
                $("#pastA").css("color","#666");
                $("#normalB").attr("class","favoriteMenuTitleActive");
                $("#pastB").attr("class","favoriteMenuTitle");
                this.showNormal=true;
                this.showPasts=false;
            },
            pastClick:function () {
                $("#normalA").css("color","#666");
                $("#pastA").css("color","#31BBAC");
                $("#normalB").attr("class","favoriteMenuTitle");
                $("#pastB").attr("class","favoriteMenuTitleActive");
                this.showNormal=false;
                this.showPasts=true;
            },
            getDetail:function (showid) {
                window.location.href="/show/viewShowDetail/"+showid;
            },
            cancelFavorite:function (showid) {
                // alert("cancel---"+showid);
                var cancelFavorite=[this.userEmail,showid];
                this.$http.get("http://localhost:8080/user/cancelFavorite/"+cancelFavorite).then(function (response) {
                    for(var i=0;i<this.normal.length;i++){
                        if((this.normal)[i].showid==showid){
                            (this.normal).splice(i,1);
                        }
                    }
                    for(var i=0;i<this.past.length;i++){
                        if((this.past)[i].showid==showid){
                            (this.past).splice(i,1);
                        }
                    }
                    toastr.success("取消收藏成功！")
                })
            }
        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
                this.userEmail=userInfo.email;
            });

            $("#favoriteImg").on("error",function () {
                $(this).attr("src","/images/showIndex.jpg");
            });
            this.$http.get("http://localhost:8080/user/getFavoriteShow").then(function (response) {
                console.log(response);
                var nowDate = new Date();
                var year= nowDate.getFullYear();
                var month = nowDate.getMonth()+1;
                var today = nowDate.getDate();
                var hours = nowDate.getHours();
                var minutes = nowDate.getMinutes();
                var seconds = nowDate.getSeconds();

                if(month >= 1 && month <=9){
                    month = "0" + month;
                }
                if(today >= 1 && today <=9){
                    today = "0" + today;
                }
                var currentdate = year + "-" + month + "-" + today + " " + hours + ":" +minutes + ":" +seconds;
                var currentDateLong = new Date(currentdate.replace(new RegExp("-","gm"),"/")).getTime();

                var showInfos=response.data;
                if (showInfos.length!=0){
                    for(var i=0;i<showInfos.length;i++){
                        if (showInfos[i].showtime>currentDateLong){
                            (this.normal).push(showInfos[i]);
                        }
                        else {
                            (this.past).push(showInfos[i]);
                        }
                    }
                    if ((this.normal).length!=0){
                        this.hasNormal=true;
                    }
                    if((this.past).length!=0){
                        this.hasPast=true;
                    }
                    console.log("this.normal"+this.normal[0]);
                }
            })
        }
    })
}