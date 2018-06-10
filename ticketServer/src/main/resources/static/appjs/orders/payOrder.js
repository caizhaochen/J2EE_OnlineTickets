function payOrder() {
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
        function (value) {
            var toFixedNum = Number(value).toFixed(3);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });


    var vue=new Vue({
        el:"#payOrderVue",
        data:{
            errorMsg:'error',
            username:'您还没有登录',
            userlevel:'',
            orderId:'',
            showId:'',
            orderTime:'',
            orderPrice:'',
            orderQuantity:'',
            seats:'',
            orderDeadline:'',
            originPrice:'',
            coupons:[],
            accountId:'',
            accountPass:'',
            hasCoupon:false,
            reducePrice:0,
            showName:'',
            showTime:'',
            venueName:'',
            venueLocation:''
        },
        methods:{
            pay:function () {
                var id=this.accountId;
                var pass=this.accountPass;
                if(id==""||pass==""){
                    // this.errorMsg="请填写完整账户账号和密码";
                    // showError();
                    // setTimeout("showError()",5000);
                    toastr.error("请填写完整账户账号和密码");
                }
                else {
                    var payInfo=[id,pass,this.reducePrice];
                    this.$http.get("http://localhost:8080/order/pay/"+payInfo).then(function (payResponse) {
                        console.log(payResponse.bodyText);
                        var payRes=payResponse.bodyText;
                        if(payRes=="noUser"){
                            toastr.error("不存在该银行账户");
                        }
                        else if(payRes=="errorPassword"){
                            toastr.error("密码错误");
                        }
                        else if(payRes=="overDue"){
                            toastr.error("您未在规定时间内支付，订单已失效");
                        }
                        else if(payRes=="notEnough"){
                            toastr.error("账户余额不足");
                        }
                        else{
                            window.location.href="/order/paySuccess";
                        }
                    })
                }
            }
        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
            });
            this.$http.get("http://localhost:8080/order/getPayInfo").then(function (orderResponse) {
                console.log(orderResponse);

                var orderInfo=orderResponse.data[0];
                this.orderId=orderInfo.orderid;
                this.showId=orderInfo.showid;
                this.orderTime=orderInfo.ordertime;
                this.originPrice=orderInfo.originprice;
                this.orderQuantity=orderInfo.quantity;
                this.seats=orderInfo.orderseats;
                this.orderDeadline=orderInfo.orderdeadline;
                this.orderPrice=orderInfo.totalprice;

                this.showName=orderResponse.data[1];
                this.showTime=orderResponse.data[2];
                this.venueName=orderResponse.data[3];
                this.venueLocation=orderResponse.data[4];

            });
            this.$http.get("http://localhost:8080/coupon/getCoupons").then(function (couponResponse) {
                var couponsInfo=couponResponse.data;
                console.log(couponsInfo);
                if(couponsInfo.length>0){
                    this.hasCoupon=true;
                }
                this.coupons=couponsInfo;
            });
            $("#payShowImage").on("error",function () {
                $(this).attr("src","/images/showIndex.jpg");
            })
        }
    })

}

window.onload = function () {
    payOrder();
}

function hideError() {
    var alertBox = document.getElementById("payOrderError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("payOrderError");
    alertBox.style.display = "block";

}