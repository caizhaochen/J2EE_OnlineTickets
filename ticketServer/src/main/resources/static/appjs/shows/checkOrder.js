function checkOrderVue() {
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
        el: "#checkOrderVue",
        data: {
            errorMsg: 'error!',
            successMsg: 'Success!',
            venueName:'您还没有登录',
            checkOrderId:'',
            showOrder:false,
            order:'',
            show:'',



        },
        methods: {
            getOrder:function () {
                if (this.checkOrderId==""){
                    this.errorMsg="请输入订单号！";
                    showError();
                    setTimeout("hideError()",5000);
                }
                else if(!IntCheck(this.checkOrderId)){
                    this.errorMsg="订单号必须是数字组合!";
                    showError();
                    setTimeout("hideError()",5000);
                }else {
                    this.$http.get("http://localhost:8080/order/getOrderShow/"+this.checkOrderId).then(function (response) {
                        var res=response.data;
                        if(res[0]=="fail"){
                            this.errorMsg=res[1];
                            showError();
                            setTimeout("hideError()",5000);
                        }else if(res[0]=="success"){
                            this.order=res[1];
                            this.show=res[2];
                            this.showOrder=true;
                        }
                    })
                }
            },
            checkOrder:function(){
                if (this.checkOrderId==""){
                    this.errorMsg="请输入订单号！";
                    showError();
                    setTimeout("hideError()",5000);
                }
                else if(!IntCheck(this.checkOrderId)){
                    this.errorMsg="订单号必须是数字组合!";
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    this.$http.get("http://localhost:8080/order/checkOrder/"+this.checkOrderId).then(function (showResponse) {
                        var checkRes=showResponse.data;
                        var result=checkRes[0];
                        if(result=="fail"){
                            this.errorMsg=checkRes[1];
                            showError();
                            setTimeout("hideError()",5000);
                            this.checkOrderId="";
                            this.showOrder=false;
                        }
                        else {
                            this.successMsg="检票成功！"
                            showSuccess();
                            setTimeout("hideSuccess()",5000);
                            this.checkOrderId="";
                            setTimeout("this.showOrder=false",3000);
                            this.showOrder=false;
                        }
                    })
                }

            }

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/venue/getVenueInfo").then(function (response) {
                console.log(response);
                var venueInfo=response.data;
                if (venueInfo==null){
                    this.errorMsg="请求超时，请重新登录";
                    showError();
                    setTimeout("hideError()",5000);
                }else if(venueInfo.ischecked==0){
                    window.location.href="/venueNotCheck"
                }
                else{
                    this.venueName=venueInfo.venuename;
                }
            });



        }
    })

}


function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag = r.test(num);
    return flag;
}

function hideError() {
    var alertBox = document.getElementById("checkOrderError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("checkOrderError");
    alertBox.style.display = "block";

}
function hideSuccess() {
    var alertBox = document.getElementById("checkOrderSuccess");
    alertBox.style.display = "none";
}

function showSuccess() {
    var alertBox = document.getElementById("checkOrderSuccess");
    alertBox.style.display = "block";

}

window.onload = function () {
    checkOrderVue();
}