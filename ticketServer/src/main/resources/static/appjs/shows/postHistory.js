function postEventVue() {
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
        el: "#postHistoryVue",
        data: {
            errorMsg: 'error!',
            venueName:'您还没有登录',
            allShows:[],
            showLen:false,


        },
        methods: {



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

            this.$http.get("http://localhost:8080/show/getAllShow").then(function (showResponse) {
                var showInfos=showResponse.data;
                this.allShows=showInfos;
                if(this.allShows.length==0){
                    this.showLen=false;
                }
                else{
                    this.showLen=true;
                }
            })

        }
    })

}


function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag = r.test(num);
    return flag;
}

function hideError() {
    var alertBox = document.getElementById("postHistoryError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("postHistoryError");
    alertBox.style.display = "block";

}

window.onload = function () {
    postEventVue();
}