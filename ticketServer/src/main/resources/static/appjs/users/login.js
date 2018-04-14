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

                if(login.email==""||login.password==""){
                    self.errorMsg="请填写完整登录信息！";
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    var loginInfo=[login.email,login.password];
                    this.$http.get("http://localhost:8080/user/login/"+loginInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="noUser"){
                            self.errorMsg="该邮箱不存在！请注册或正确填写！";
                            showError();
                            setTimeout("hideError()",5000);
                        }else if(res=="errorPassword"){
                            self.errorMsg="密码不正确！";
                            showError();
                            setTimeout("hideError()",5000);
                        }else if(res=="notCheck"){
                            self.errorMsg="您还没有激活！请先去邮箱激活！"
                            showError();
                            setTimeout("hideError()",5000);
                        }else if(res=="notPermit"){
                            self.errorMsg="该号已被注销，请换一个邮箱注册登录！"
                            showError();
                            setTimeout("hideError()",5000);
                        } else if(res=="success"){
                            window.location.href="/tickets/home"
                        }
                    })
                }

            }
        }
    })


})

function hideError() {
    var alertBox=document.getElementById("loginError");
    alertBox.style.display="none";
}

function showError() {
    var alertBox=document.getElementById("loginError");
    alertBox.style.display="block";

}