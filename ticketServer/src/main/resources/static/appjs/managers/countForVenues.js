function countForVenues() {
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
            var toFixedNum = Number(value).toFixed(5);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });

    var vue=new Vue({
        el:"#countForVenuesVue",
        data:{
            errorMsg:'error',
            successMsg:'success',
            hasUnCount:false,
            unCountInfo:[],
            notPassReason:'',
            tempShowId:'',
        },
        methods:{
            payForVenue:function (showId,venueGet,adminGet) {
                var countInfo=[showId,venueGet,adminGet];
                this.$http.get("http://localhost:8080/order/countForVenue/"+countInfo).then(function (response) {
                    var res=response.data;
                    if(res[0]=="success"){
                        window.location.href="/pages/managers/balanceAccount.html";
                    }
                    else if(res[0]=="fail"){
                        this.errorMsg=res[1];
                        showError();
                        setTimeout("hideError()",5000);
                    }
                })
            }

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/order/getUnCountInfo").then(function (response) {
                this.unCountInfo=response.data;
                if(this.unCountInfo.length!=0){
                    this.hasUnCount=true;
                }
            })
        }
    })

}
function hideError() {
    var alertBox = document.getElementById("countForVenuesError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("countForVenuesError");
    alertBox.style.display = "block";

}

window.onload=function () {
    countForVenues();
}