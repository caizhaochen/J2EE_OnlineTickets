function creditShop() {
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

    Vue.filter('currency',
        <!-- value 格式为13位unix时间戳 -->
        <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
        function (value) {
            var toFixedNum = Number(value).toFixed(3);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });

    var vue=new Vue({
        el:"#creditShopVue",
        data:{
            errorMsg:'error',
            successMsg:'success',
            username:'您还没有登录',
            userlevel:0,
            userEmail:'',
            userCredit:0,
            showShop:true,
            showCoupon:false,
            hasCoupons:false,
            myCoupons:[],


        },
        methods:{
            showCreditShop:function () {
                $("#dealCredit").css("background-color","#5bc0de");
                $("#myCoupon").css("background-color","#e7e7e7");
                this.showShop=true;
                this.showCoupon=false;
            },
            showMyCoupon:function () {
                $("#dealCredit").css("background-color","#e7e7e7");
                $("#myCoupon").css("background-color","#5bc0de");
                this.$http.get("http://localhost:8080/user/getMyCoupon").then(function (response) {
                    this.myCoupons=response.data;
                    if(this.myCoupons.length>0){
                        this.hasCoupons=true;
                    }
                    this.showShop=false;
                    this.showCoupon=true;
                })
            },
            getCoupon:function (money) {
                this.$http.get("http://localhost:8080/user/getCoupon/"+money).then(function (response) {
                    var getInfo=response.data;
                    if(getInfo[0]=="success"){
                        toastr.success("获取成功！");
                    }else if(getInfo[0]=="fail"){
                        this.errorMsg=getInfo[1];
                        toastr.error(this.errorMsg);
                    }
                })
            }
        },
        mounted:function (){
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {

                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
                this.userEmail=userInfo.email;
                this.userCredit=userInfo.credit;
            });

        }
    });
}

window.onload=function () {
    $("#dealCredit").css("background-color","#5bc0de");
    $("#myCoupon").css("background-color","#e7e7e7");
    creditShop();
}