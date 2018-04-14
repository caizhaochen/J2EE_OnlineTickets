function postEventVue() {
    var vue = new Vue({
        el: "#postEventVue",
        data: {
            errorMsg: 'error!',
            venueName:'您还没有登录',
            lines: 5,
            rows: 6,
            typeNumber: 1,
            // numbers:0,
            priceName: 'priceName',
            seatL: 'seatL',
            seatR: 'seatR',
            price: 'price',

        },
        methods: {
            // priceStart:function () {
            //     this.numbers=$("#typeNum").val();
            // }
            generateSeats: function (lines, rows) {
                if (this.lines == "" || this.rows == "") {
                    this.errorMsg = "请完整填写座位规模！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if (!IntCheck(this.lines) || !IntCheck(this.rows)) {
                    this.errorMsg = "座位规模必须为整数！"
                    showError();
                    setTimeout("hideError()", 5000);
                } else {
                    var map = [];
                    var x = lines;
                    var y = rows;
                    for (var i = 0; i < x; i++) {
                        map[i] = "";
                        for (var j = 0; j < y; j++) {
                            map[i] += "a";
                        }
                    }

                    $('#seatMaps').empty();
                    firstSeatId = 1;
                    firstSeatLabel = 1;
                    $('#seatMaps').seatCharts({
                        map: map,
                        naming: {
                            top: false, //不显示顶部横坐标（行）
                            left: false,
                            getId: function (character, row, column) {
                                console.log(row);
                                return firstSeatId++;
                            },
                            getLabel: function (character, row, column) { //返回座位信息
                                return firstSeatLabel++;
                                // return row+""+column+"";
                            }
                        },

                        click: function () {

                            if (this.status() == 'available') {
                                //do some custom stuff
                                console.log(this.data());
                                alert(this.settings.id);
                                return 'selected';
                            } else if (this.status() == 'selected') {
                                //do some custom stuff
                                console.log("selected")
                                return 'available';
                            } else {
                                //i.e. alert that the seat's not available
                                console.log(this.status());
                                return this.style();
                            }

                        },
                    });
                }

            },

            showRegisterSubmit: function () {
                var isBlank = "false";
                var isInt="true";
                var isLsmThanR="true"
                var showName = $("#showName").val();
                var showType = $("#showType").val();
                var showTime = $("#showDate").val();
                var showInfo = $("#showInformation").val();
                var showLine = $("#showLine").val();
                var showRow = $("#showRow").val();
                var priceNum = $("#typeNum").val();
                var priceType =[];
                for (var i = 1; i <= priceNum; i++) {
                    var typeName = $("#priceName" + i).val();
                    var typeSeatL = $("#seatL" + i).val();
                    var typeSeatR = $("#seatR" + i).val();
                    var typePrice = $("#price" + i).val();
                    console.log(typeSeatL,typeSeatR);
                    if (typeName == "" || typeSeatL == "" || typeSeatR == "" || typePrice == "") {
                        isBlank = "true";
                    }else if (!IntCheck(typeSeatL)||!IntCheck(typeSeatR)){
                        isInt="false";
                    }else if(parseInt(typeSeatL)>parseInt(typeSeatR)){

                        isLsmThanR="false";
                    }

                    priceType[i-1] = [typeName, typeSeatL, typeSeatR, typePrice];
                    console.log(priceType);
                }
                if (showName == "" || showTime == "" || showInfo == "" || showLine == "" || showRow == "") {
                    isBlank = "true";
                }
                if (!IntCheck(this.lines) || !IntCheck(this.rows)) {
                    this.errorMsg = "座位规模必须为整数！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if(isInt=="false"){
                    this.errorMsg = "座位号必须为整数！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if(isLsmThanR=="false"){
                    this.errorMsg = "座位区间初始不得大于结束！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if(isBlank=="true"){
                    this.errorMsg = "请确认信息填写完整！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else {
                    var showRegister=[showName,showType,showTime,showInfo,showLine,showRow,priceNum,priceType];
                    console.log(showRegister);
                    this.$http.get("http://localhost:8080/show/register/"+showRegister).then(function (response) {
                        var result=response.bodyText;
                        if (result=="noVenue"){
                            window.location.href="/venues";
                        }
                        else if(result=="false"){
                            this.errorMsg = "发布失败，请重试！"
                            showError();
                            setTimeout("hideError()", 5000);
                        }else {
                            showSuccess();
                            setTimeout("returnPostHistory()",3000);
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
                    // this.venueID=venueInfo.venueid;
                    this.venueName=venueInfo.venuename;
                    // this.venuePhone=venueInfo.venuephone;
                    // this.venueLocation=venueInfo.location;
                    // this.venueInfo=venueInfo.information;
                    // this.venueLine=venueInfo.venueline;
                    // this.venueRow=venueInfo.venuerow;
                }

            });
            // this.generateSeats(this.lines,this.rows);
            laydate.render({
                elem: '#showDate' //指定元素
                , type: 'datetime'
                , min: 10
            });
        }
    })

}

function returnPostHistory() {
    window.location.href="/pages/venues/postHistory.html";
}

function showSuccess() {
    var alertBox = document.getElementById("showRegisterSuccess");
    alertBox.style.display = "block";
}

function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag = r.test(num);
    return flag;
}

function hideError() {
    var alertBox = document.getElementById("showRegisterError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("showRegisterError");
    alertBox.style.display = "block";

}

window.onload = function () {
    postEventVue();
}