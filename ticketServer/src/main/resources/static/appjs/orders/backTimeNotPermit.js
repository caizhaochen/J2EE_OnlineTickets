function paySuccess() {

    var vue=new Vue({
        el:"#backTimeNotPermitVue",
        data:{
            username:'您还没有登录',
            userlevel:'',

        },
        methods:{

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo=response.data;
                this.username=userInfo.username;
                this.userlevel=userInfo.level;
            });

        }
    })

}

window.onload = function () {
    paySuccess();
}

