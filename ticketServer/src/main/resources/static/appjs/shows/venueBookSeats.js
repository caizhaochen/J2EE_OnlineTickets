Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};


var map = new Array();
// var sc;
function registSeat(lines,rows) {
    var x = parseInt(lines);
    var y = parseInt(rows);
    map = [];
    for(var i=0;i<x;i++){
        map[i]="";
        for(var j=0;j<y;j++){
            map[i]+="e";
        }
    }
    // $('#seat-maps').empty();
    // $("#legend").empty();
    firstSeatId = 1;
    firstSeatLabel = 1;
    sc =$('#seat-maps').seatCharts({
        map: map,
        naming: {
            top: false, //不显示顶部横坐标（行）
            left: false,
            getId: function (character, row, column) {
                return firstSeatId++;
            },
            getLabel: function (character, row, column) { //返回座位信息
                return firstSeatLabel++;
                // return row+""+column+"";
            }
        },
        legend: {
            node: $('#legend'),
            items: [
                ['e', 'available', '位置'],
                ['e', 'unavailable', '已被购买'],
                ['e', 'selected', '已选']
            ]
        },
        // click: function () {
        //     if (this.status() == 'available') { //若为可选座状态，添加座位
        //         map[this.settings.row] = map[this.settings.row].substring(0, this.settings.column) + "_" + map[this.settings.row].substring(this.settings.column + 1);
        //         return 'none';
        //     } else {
        //         map[this.settings.row] = map[this.settings.row].substring(0, this.settings.column) + "e" + map[this.settings.row].substring(this.settings.column + 1);
        //         return "available";
        //     }
        // }
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
    // sc.get([1,2,3]).status("unavailable");
    // sc.get(['1_2', '4_1', '7_1', '7_2']).status('none');
}

function venueBookSeats() {
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
        el: '#venueBookSeatsVue',
        data: {
            venueName:'您还没有登录！',
            errorMsg:'error',
            showname:'',
            showtime:'',
            showId:'',
            seats:[],
            selected:[],
            totalPrice:0,
        },
        methods:{
            fetchTickets:function () {
                if(this.selected.length==0){
                    this.errorMsg="请至少选择一张票！"
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    var quantity=this.selected.length;
                    var price=this.totalPrice;
                    var orderInfo=[quantity,price,this.selected];

                    this.$http.get("http://localhost:8080/order/venueRegisterOrder/"+orderInfo).then(function (response) {
                        console.log(response.data);
                        var responseData=response.data;
                        var result=responseData[0];
                        if(result=="fail"){
                            this.errorMsg=responseData[1];
                            showError();
                            setTimeout("hideError()",5000);
                        }
                        else if(result=="success"){
                            var orderId=responseData[1];
                            window.location.href="/order/venuePayOrder/"+orderId;
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


            this.$http.get("http://localhost:8080/chooseSeats").then(function (response) {
                console.log(response.data);
                var seatsInfo=response.data;
                this.seats=seatsInfo;
                var unavailable=new Array();
                unavailable=[];
                for (var i=0;i<seatsInfo.length;i++){
                    if(seatsInfo[i].seatisbooked==1||seatsInfo[i].seatisbooked==2){
                        unavailable[unavailable.length]=seatsInfo[i].seatnum;
                    }
                }
                this.$http.get("http://localhost:8080/show/getCurrentShow").then(function (showResponse) {
                    console.log(showResponse.data);
                    var showInfo=showResponse.data;
                    var line=showInfo.showline;
                    var row=showInfo.showrow;
                    vue.showname=showInfo.showname;
                    vue.showtime=showInfo.showtime;
                    // registSeat(line,row);
                    var x = parseInt(line);
                    var y = parseInt(row);
                    map = [];
                    for(var i=0;i<x;i++){
                        map[i]="";
                        for(var j=0;j<y;j++){
                            map[i]+="e";
                        }
                    }
                    firstSeatId = 1;
                    firstSeatLabel = 0;
                    sc =$('#seat-maps').seatCharts({
                        map: map,
                        naming: {
                            top: true, //不显示顶部横坐标（行）
                            left: true,
                            getId: function (character, row, column) {
                                return firstSeatId++;
                            },
                            getLabel: function (character, row, column) { //返回座位信息
                                return vue.seats[firstSeatLabel++].seatprice;
                            }
                        },
                        legend: {
                            node: $('#legend'),
                            items: [
                                ['e', 'available', '可选'],
                                ['e', 'unavailable', '已被购买'],
                                ['e', 'selected', '已选']
                            ]
                        },
                        click: function () {

                            if (this.status() == 'available') {

                                if (vue.selected.length<6){
                                    vue.selected.push(this.settings.id);
                                    vue.totalPrice=vue.totalPrice+vue.seats[parseInt(this.settings.id)-1].seatprice;
                                    console.log(vue.selected);
                                    return 'selected';
                                }
                                else {
                                    alert("最多只能选择6个座位！");
                                    return 'available';
                                }

                            } else if (this.status() == 'selected') {
                                vue.selected.remove(this.settings.id);
                                console.log(vue.selected);
                                vue.totalPrice=vue.totalPrice-vue.seats[parseInt(this.settings.id)-1].seatprice;
                                return 'available';
                            } else {
                                console.log(this.status());
                                return this.style();
                            }

                        },
                    });
                    sc.get(unavailable).status('unavailable');
                })

            })

        }
    })

}

function hideError() {
    var alertBox=document.getElementById("venueBookSeatsError");
    alertBox.style.display="none";
}

function showError() {
    var alertBox=document.getElementById("venueBookSeatsError");
    alertBox.style.display="block";

}

window.onload = function () {
    venueBookSeats();
}