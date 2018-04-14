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
                var orderInfo=orderResponse.data;
                console.log(orderResponse);
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

window.onload = function () {
    paySuccess();
}

