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

            isAll:true,
            isFuture:true,
            isPast:false,
            isOverDue:false,
            isNotPay:false,
            isHasPay:false,
            isHasBack:false,

            allLen:false,
            futureLen:false,
            pastLen:false,
            overLen:false,
            notLen:false,
            payLen:false,
            backLen:false,
        },
        methods:{
            showFuture:function () {
                this.listName='即将到来';
                this.isHasPay=false;
                this.isOverDue=false;
                this.isNotPay=false;
                this.isHasBack=false;
                this.isFuture=true;
                this.isPast=false;
            },
            showPast:function () {
                this.listName='已过去';
                this.isHasPay=false;
                this.isOverDue=false;
                this.isNotPay=false;
                this.isHasBack=false;
                this.isFuture=false;
                this.isPast=true;
            },
            showPay:function () {
                this.listName='已付款';
                this.isHasPay=true;
                this.isOverDue=false;
                this.isNotPay=false;
                this.isHasBack=false;
                this.isFuture=false;
                this.isPast=false;
            },
            showNot:function () {
                this.listName='未付款';
                this.isHasPay=false;
                this.isOverDue=false;
                this.isNotPay=true;
                this.isHasBack=false;
                this.isFuture=false;
                this.isPast=false;
            },
            showOver:function () {
                this.listName='已失效';
                this.isHasPay=false;
                this.isOverDue=true;
                this.isNotPay=false;
                this.isHasBack=false;
                this.isFuture=false;
                this.isPast=false;
            },
            showBack:function () {
                this.listName='已撤回';
                this.isHasPay=false;
                this.isOverDue=false;
                this.isNotPay=false;
                this.isHasBack=true;
                this.isFuture=false;
                this.isPast=false;
            },
            deleteOrder:function (id) {
                toastr.error("目前还不支持删除");
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