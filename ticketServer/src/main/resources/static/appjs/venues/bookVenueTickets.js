function showSceneTickets() {
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
        el: "#showSceneTickets",
        data: {
            errorMsg: 'error!',
            venueName:'您还没有登录',

            totalCount: 200,

            //分页数
            pageCount: 20,

            //当前页面
            pageCurrent: 1,

            //分页大小
            pagesize: 2,

            //显示分页按钮数
            showPages: 11,

            //开始显示的分页按钮
            showPagesStart: 1,

            //结束显示的分页按钮
            showPageEnd: 5,

            //分页数据
            arrayData: [],

            arrayDateAll: []


        },
        methods: {
            showPage: function (pageIndex, $event, forceRefresh) {
                if (pageIndex > 0) {
                    if (pageIndex > this.pageCount) {
                        pageIndex = this.pageCount;
                    }
                    //判断数据是否需要更新
                    var currentPageCount = Math.ceil(this.totalCount / this.pagesize);
                    if (currentPageCount != this.pageCount) {
                        pageIndex = 1;
                        this.pageCount = currentPageCount;
                    }
                    else if (this.pageCurrent == pageIndex && currentPageCount == this.pageCount && typeof (forceRefresh) == "undefined") {
                        console.log("not refresh");
                        return;
                    }

                    //处理分页点中样式
                    var buttons = $("#pager").find("span");
                    for (var i = 0; i < buttons.length; i++) {
                        if (buttons.eq(i).html() != pageIndex) {
                            buttons.eq(i).removeClass("active");
                        }
                        else {
                            buttons.eq(i).addClass("active");
                        }
                    }

                    var newPageInfo = [];
                    for (var i = 0; i < this.pagesize; i++) {
                        var index = i + (pageIndex - 1) * this.pagesize;
                        if (index > this.totalCount - 1) break;
                        newPageInfo[i] = this.arrayDateAll[index];
                    }
                    this.pageCurrent = pageIndex;
                    this.arrayData = newPageInfo;

                    //计算分页按钮数据
                    if (this.pageCount > this.showPages) {
                        if (pageIndex <= (this.showPages - 1) / 2) {
                            this.showPagesStart = 1;
                            this.showPageEnd = this.showPages - 1;
                            console.log("showPage1")
                        }
                        else if (pageIndex >= this.pageCount - (this.showPages - 3) / 2) {
                            this.showPagesStart = this.pageCount - this.showPages + 2;
                            this.showPageEnd = this.pageCount;
                            console.log("showPage2")
                        }
                        else {
                            console.log("showPage3")
                            this.showPagesStart = pageIndex - (this.showPages - 3) / 2;
                            this.showPageEnd = pageIndex + (this.showPages - 3) / 2;
                            // this.showPagesStart = pageIndex - (this.showPages - 3) / 2;
                            // this.showPageEnd = pageIndex + (this.showPages - 3) / 2;
                        }
                    }
                    console.log("showPagesStart:" + this.showPagesStart + ",showPageEnd:" + this.showPageEnd + ",pageIndex:" + pageIndex);
                }

            },
            bookShow:function (showid) {
                window.location.href="/venueBookSeats/"+showid;
            },
            randomBookShow:function (showid) {
                window.location.href="/venueRandomSeats/"+showid;
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

            this.$http.get("http://localhost:8080/show/getFutureShowByVenue").then(function (response) {
                this.arrayData = response.data;
                this.arrayDateAll = response.data;
                this.totalCount = this.arrayData.length;
                this.pageCount = parseInt((this.totalCount - 1) / this.pagesize) + 1;
                console.log(this.arrayData);
                console.log(this.pageCount);
                this.showPage(vue.pageCurrent, null, true);
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
    var alertBox = document.getElementById("sceneTicketError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("sceneTicketError");
    alertBox.style.display = "block";

}

window.onload = function () {
    showSceneTickets();
}