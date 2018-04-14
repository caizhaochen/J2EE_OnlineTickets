function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag = r.test(num);
    return flag;
}

function randomSeats() {
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
        el: '#randomSeats',
        data: {
            username: '您还没有登录',
            userlevel: 0,
            errorMsg: 'error',
            showname: '',
            showtime: '',
            showId: '',
            restTickets:'',
            seatsNum:'',

        },
        methods: {
            fetchRandomTickets: function () {
                if (!IntCheck(this.seatsNum)) {
                    this.errorMsg = "票张数必须为正整数！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if(this.seatsNum>20){
                    this.errorMsg = "随机购票每单不得超过20张"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else {
                    var quantity = this.seatsNum;

                    this.$http.get("http://localhost:8080/order/randomSeatOrder/" + quantity).then(function (response) {
                        console.log(response.data);
                        var responseData = response.data;
                        var result = responseData[0];
                        if (result == "fail") {
                            this.errorMsg = responseData[1];
                            showError();
                            setTimeout("hideError()", 5000);
                        }
                        else if (result == "success") {
                            var orderId = responseData[1];
                            window.location.href = "/order/payOrder/" + orderId;
                        }
                    })
                }

            }
        },
        mounted: function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo = response.data;
                this.username = userInfo.username;
                this.userlevel = userInfo.level;
            })

            this.$http.get("http://localhost:8080/show/getCurrentShow").then(function (showResponse) {
                console.log(showResponse.data);
                var showInfo = showResponse.data;
                this.showname = showInfo.showname;
                this.showtime = showInfo.showtime;
                this.restTickets=showInfo.restseats;
            })

        }
    })

}

function hideError() {
    var alertBox = document.getElementById("randomSeatsError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("randomSeatsError");
    alertBox.style.display = "block";

}

window.onload = function () {
    randomSeats();
}