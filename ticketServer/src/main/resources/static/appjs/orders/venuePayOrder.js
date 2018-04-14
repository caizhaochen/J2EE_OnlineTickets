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
        <!-- value 格式为13位unix时间戳 -->
        <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
        function (value) {
            var toFixedNum = Number(value).toFixed(3);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });


    var vue = new Vue({
        el: "#venuePayOrderVue",
        data: {
            errorMsg: 'error',
            // successMsg:'收款成功！',
            venueName: '您还没有登录',
            orderId: '',
            orderTime: '',
            orderPrice: '',
            orderQuantity: '',
            seats: '',
            orderDeadline: '',
            originPrice: '',
            vipPrice: '',
            vipId: '',
            userId: '',
        },
        methods: {
            searchVip: function () {
                if (this.vipId == "") {
                    this.errorMsg = "请输入会员邮箱！";
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else {
                    this.$http.get("http://localhost:8080/order/getVipInfo/" + this.vipId).then(function (response) {

                        var vipInfo = response.data;
                        if (vipInfo[0] == "fail") {
                            this.errorMsg = vipInfo[1];
                            showError();
                            setTimeout("hideError()", 5000);
                        }
                        else {
                            this.userId = this.vipId;
                            this.vipPrice = this.originPrice * vipInfo[1];
                            var vprice = document.getElementById("showVipPrice");
                            vprice.style.display = "block";
                        }
                    })
                }
            },
            venuePay: function () {
                // var id=this.accountId;
                // var pass=this.accountPass;
                // if(id==""||pass==""){
                //     this.errorMsg="请填写完整账户账号和密码";
                //     showError();
                //     setTimeout("showError()",5000);
                // }
                // else {
                this.userId = this.vipId;
                if (this.vipId == "") {
                    this.userId = "--";
                }
                console.log(this.userId);

                this.$http.get("http://localhost:8080/order/venuePay/" + this.userId).then(function (payResponse) {
                    console.log(payResponse.bodyText);
                    var payRes = payResponse.bodyText;
                    if (payRes == "success") {
                        // this.successMsg="收款成功！三秒后返回现场购票界面";
                        showSuccess();
                        setTimeout("returnScene()", 3000);
                        // window.location.href="/pages/venues/sceneTickets.html";
                    } else if (payRes == "overDue") {
                        this.errorMsg = "未在规定时间内支付，订单已失效！"
                        showError();
                        setTimeout("hideError()", 5000);
                    } else if (payRes == "noUser") {
                        this.errorMsg = "该会员不存在！"
                        showError();
                        setTimeout("hideError()", 5000);
                    }
                })
            }
            // }
        },
        mounted: function () {
            this.$http.get("http://localhost:8080/venue/getVenueInfo").then(function (response) {
                console.log(response);
                var venueInfo = response.data;
                if (venueInfo == null) {
                    this.errorMsg = "请求超时，请重新登录";
                    showError();
                    setTimeout("hideError()", 5000);
                } else if (venueInfo.ischecked == 0) {
                    window.location.href = "/venueNotCheck"
                }
                else {
                    this.venueName = venueInfo.venuename;

                }

            });

            this.$http.get("http://localhost:8080/order/getPayInfo").then(function (orderResponse) {
                var orderInfo = orderResponse.data;
                console.log(orderResponse);
                this.orderId = orderInfo.orderid;
                this.orderTime = orderInfo.ordertime;
                this.originPrice = orderInfo.originprice;
                this.orderQuantity = orderInfo.quantity;
                this.seats = orderInfo.orderseats;
                this.orderDeadline = orderInfo.orderdeadline;
                this.orderPrice = orderInfo.totalprice;
            })
        }
    })

}

window.onload = function () {
    payOrder();
}

function hideError() {
    var alertBox = document.getElementById("venuePayOrderError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("venuePayOrderError");
    alertBox.style.display = "block";

}

function showSuccess() {
    var alertBox = document.getElementById("venuePayOrderSuccess");
    alertBox.style.display = "block";

}

function returnScene() {
    window.location.href = "/pages/venues/sceneTickets.html";

}