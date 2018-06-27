function paySuccess() {

    var vue=new Vue({
        el:"#paySuccessVue",
        data:{
            username:'您还没有登录',
            userlevel:'',
            orderPrice:'',
            credit:'',

        },
        methods:{

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
            });
            this.$http.get("http://localhost:8080/order/getPayInfo").then(function (orderResponse) {
                var orderInfo=orderResponse.data[0];
                console.log(orderInfo);
                // this.orderId=orderInfo.orderid;
                // this.orderTime=orderInfo.ordertime;
                // this.originPrice=orderInfo.originprice;
                // this.orderQuantity=orderInfo.quantity;
                // this.seats=orderInfo.orderseats;
                // this.orderDeadline=orderInfo.orderdeadline;
                this.orderPrice=orderInfo.totalprice;
                this.credit=Math.floor(this.orderPrice)
            })

        }
    })

}
function resetTime(time){
    var timer=null;
    var t=time;
    var m=0;
    var s=0;
    m=Math.floor(t/60%60);
    // m<10&&(m='0'+m);
    s=Math.floor(t%60);
    function countDown(){

        s--;
        // s<10&&(s='0'+s);
        if(m==0&&s<0){
            return;
        }
        if(s<0){
            s=59;
            m--;
        }

        console.log(m+"分钟"+s+"秒");
        var minute = document.getElementById("minute");
        minute.innerHTML=m.toString();
        var second = document.getElementById("second");
        second.innerHTML=s.toString();

    }
    timer=setInterval(countDown,1000);
}

window.onload = function () {
    paySuccess();
    resetTime(899);
}

