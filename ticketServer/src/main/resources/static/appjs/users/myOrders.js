function myOrders() {
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
        el:"#myOrdersVue",
        data:{
            username:'您还没有登录',
            userlevel:0,

            listName:'即将到来',

            allOrders:[],
            future:[],
            past:[],
            notPay:[],
            overDue:[],
            hasPay:[],
            hasBack:[],

            showFuture:true,
            showAll:false,
            showNotPaid:false,

            allLen:false,
            futureLen:false,
            pastLen:false,
            overLen:false,
            notLen:false,
            payLen:false,
            backLen:false,
        },
        methods:{
            futureClick:function () {
                // $("#normalA").css("color","#31BBAC");
                // $("#pastA").css("color","#666");
                $("#future").attr("class","favoriteMenuTitleActive");
                $("#all").attr("class","favoriteMenuTitle");
                $("#notPaid").attr("class","favoriteMenuTitle");
                this.showFuture=true;
                this.showNotPaid=false;
                this.showAll=false;
            },
            allClick:function () {
                // $("#normalA").css("color","#31BBAC");
                // $("#pastA").css("color","#666");
                $("#future").attr("class","favoriteMenuTitle");
                $("#all").attr("class","favoriteMenuTitleActive");
                $("#notPaid").attr("class","favoriteMenuTitle");
                this.showFuture=false;
                this.showNotPaid=false;
                this.showAll=true;
            },
            notPaidClick:function () {
                // $("#normalA").css("color","#31BBAC");
                // $("#pastA").css("color","#666");
                $("#future").attr("class","favoriteMenuTitle");
                $("#all").attr("class","favoriteMenuTitle");
                $("#notPaid").attr("class","favoriteMenuTitleActive");
                this.showFuture=false;
                this.showNotPaid=true;
                this.showAll=false;
            },
            deleteOrder:function (id) {
                toastr.warning("请耐心等待该功能上线");
            }
        },
        mounted:function (){
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
            });
            this.$http.get("http://localhost:8080/order/getMyOrders").then(function (orderResponse) {
                console.log(orderResponse.data);
                var orders=orderResponse.data;
                this.allOrders=orders;
                if(orders.length>0){
                    this.allLen=true;
                    var nowTime=new Date();
                    nowTime=nowTime.getTime();
                    for(var i=0;i<orders.length;i++){
                        console.log(orders[i][0]);
                        if(orders[i][0].orderstatus==0){
                            this.notPay.push(orders[i]);
                        }else if(orders[i][0].orderstatus==1){
                            this.overDue.push(orders[i]);
                        }else if(orders[i][0].orderstatus==2){
                            this.hasPay.push(orders[i]);
                            if((orders[i][0].showtime-nowTime)>0){
                                this.future.push(orders[i]);
                            }else {
                                this.past.push(orders[i]);
                            }
                        }else{
                            this.hasBack.push(orders[i]);
                        }
                    }
                }
                if(this.allOrders.length>0){
                    this.allLen=true;
                }
                if(this.notPay.length>0){
                    this.notLen=true;
                }
                if(this.overDue.length>0){
                    this.overLen=true;
                }
                if(this.hasPay.length>0){
                    this.payLen=true;
                }
                if(this.hasBack.length>0){
                    this.backLen=true;
                }
                if(this.future.length>0){
                    this.futureLen=true;
                }
                if(this.past.length>0){
                    this.pastLen=true;
                }

            })
        }
    })

}

window.onload=function () {
    myOrders();
}