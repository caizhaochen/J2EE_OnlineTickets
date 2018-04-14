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


    var vue=new Vue({
        el:"#backOrderVue",
        data:{
            errorMsg:'error',
            username:'您还没有登录',
            userlevel:'',
            orderId:'',
            orderTime:'',
            orderPrice:'',
            orderQuantity:'',
            seats:'',
            originPrice:'',
            backPrice:'',

        },
        methods:{
            back:function () {
                this.$http.get("http://localhost:8080/order/back/"+this.orderId).then(function (response) {
                    if(response.bodyText=="success"){
                        window.location.href="/order/backSuccess"
                    }
                })
            },
        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
            });
            this.$http.get("http://localhost:8080/order/getBackOrderInfo").then(function (orderResponse) {
                var backInfo=orderResponse.data;
                console.log(orderResponse);
                var orderInfo=backInfo[0];
                var percent=backInfo[1];
                this.orderId=orderInfo.orderid;
                this.orderTime=orderInfo.ordertime;
                this.orderQuantity=orderInfo.quantity;
                this.seats=orderInfo.orderseats;
                this.orderPrice=orderInfo.totalprice;
                this.backPrice=orderInfo.totalprice*percent;
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