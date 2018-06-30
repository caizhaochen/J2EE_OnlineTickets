function showDetailVue() {
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

    var vue = new Vue({
        el: '#showDetailVue',
        data:{
            username:'您还没有登录',
            userlevel:0,
            userEmail:'',
            item:'',

            notLove:true,
            loved:false,

        },
        methods:{

            bookShow:function (showid) {
                window.location.href="/bookSeats/"+showid;
            },
            addFavorite:function (showId) {
                var favoriteInfo=[this.userEmail,showId];
                this.$http.get("http://localhost:8080/user/addFavorite/"+favoriteInfo).then(function (response) {
                    // toastr.success("收藏成功")
                    if (this.loved==true){
                        this.notLove=true;
                        this.loved=false;
                    }else{
                        this.loved=true;
                        this.notLove=false;
                    }
                })
            }
        },

        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                console.log(response.data)
                this.username=userInfo.username;
                this.userlevel=userInfo.userlevel;
                this.userEmail=userInfo.email;
            });
            this.$http.get("http://localhost:8080/show/viewShowDetail").then(function (response) {
                console.log(response.data)
                this.item=response.data;
            });
            this.$http.get("http://localhost:8080/show/getDetailShowIsFav").then(function (response) {
                console.log(response);
                if (response.bodyText=="true"){
                    this.notLove=false;
                    this.loved=true;
                }else {

                    this.notLove=true;
                    this.loved=false;
                }
            });

        }
        }
    )
}

window.onload=function () {
    showDetailVue();
}