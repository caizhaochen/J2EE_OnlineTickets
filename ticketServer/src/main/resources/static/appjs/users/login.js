$(document).ready(function() {

    var vue=new Vue({
        el:"#loginVUE",
        data:{
            errorMsg:"error!",
            login:{
                email:'',
                password:''
            }
        },
        methods:{
            loginSubmit:function () {
                const self=this;
                var login=this.login;

                if(login.email==""){
                    toastr.error("请填写邮箱！");
                }
                else if (login.password==""){
                    toastr.error("请填写密码！");
                }
                else{
                    var loginInfo=[login.email,login.password];
                    this.$http.get("http://localhost:8080/user/login/"+loginInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="noUser"){
                            toastr.error("该邮箱账户不存在！请注册或正确填写！");
                        }else if(res=="errorPassword"){
                            toastr.error("密码不正确!");

                        }else if(res=="notPermit"){
                            toastr.error("该号已被注销，请换一个邮箱注册登录！");
                        } else if(res=="success"){
                            window.location.href="/tickets/home"
                        }
                    })
                }

            }
        }
    })


})