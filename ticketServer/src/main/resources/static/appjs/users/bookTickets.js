$(document).ready(function () {
    Vue.filter('onlyNumeric', {
        // model -> view
        // 在更新 `<input>` 元素之前格式化值
        read: function (val) {
            return val;
        },
        // view -> model
        // 在写回数据之前格式化值
        write: function (val, oldVal) {
            var number = +val.replace(/[^\d]/g, '')
            return isNaN(number) ? 1 : parseFloat(number.toFixed(2))
        }
    });

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
        el: "#showTickets",
        data: {
            username:'您还没有登录',
            userlevel:'',
            //总项目数
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

            arrayDateAll: [],

            // showName:'',

            arrayDataOrigin:[],
        },
        methods: {
            // searchShow:function (text) {
            //     var searchRegex = new RegExp(text, 'i');
            //     this.arrayDateAll=[];
            //     for(var i=0;i<this.arrayDataOrigin.length;i++){
            //         if(searchRegex.test(this.arrayDataOrigin[i][0].showname)){
            //             this.arrayDateAll.push(this.arrayDataOrigin[i]);
            //         }
            //     }
            //     this.showPage(1,null,true);
            // },
            //分页方法
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
                window.location.href="/bookSeats/"+showid;
            },
            randomBookShow:function (showid) {
                window.location.href="/randomSeats/"+showid;
            }

        },
        mounted: function () {
            this.$http.get("http://localhost:8080/show/getAvailableShows").then(function (response) {
                this.arrayData = response.data;
                this.arrayDateAll = response.data;
                this.arrayDataOrigin=response.data;
                this.totalCount = this.arrayData.length;
                this.pageCount = parseInt((this.totalCount - 1) / this.pagesize) + 1;
                console.log(this.arrayData);
                console.log(this.pageCount);
                this.showPage(vue.pageCurrent, null, true);
            });
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
            })
        }

    })

})