function home() {
    Vue.filter('currency',
        <!-- value 格式为13位unix时间戳 -->
        <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
        function (value) {
            var toFixedNum = Number(value).toFixed(3);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });

    var vue=new Vue({
        el:"#homeVue",
        data:{
            username:'您还没有登录',
            useremail:'',
            usersex:'',
            userbirth:'',
            userlevel:'',
            usercredit:'',
            userconsume:'',
        },
        methods:{

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                console.log(response);
                var userInfo=response.data;
                this.useremail=userInfo.email;
                this.username=userInfo.username;
                this.userbirth=userInfo.userbirth;
                this.usersex=userInfo.usersex;
                this.userlevel=userInfo.level;
                this.usercredit=userInfo.credit;
                this.userconsume=userInfo.userconsume;
            })
        }
    });
}

// function deleteUser() {
//     new Vue({
//         el:'#deleteVue',
//         data:{
//
//         },
//         methods:{
//             deleteuser:function () {
//                 alert("???")
//             }
//         }
//     })
// }


window.onload=function () {
    home();
    // deleteUser();
}
