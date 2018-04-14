$(document).ready(function() {

    var vue=new Vue({
        el:"#venueLoginVUE",
        data:{
            errorMsg:"error!",
            venueLogin:{
                id:'',
                password:''
            }
        },
        methods:{
            venueLoginSubmit:function () {
                const self=this;
                var login=this.venueLogin;

                if(login.id==""||login.password==""){
                    self.errorMsg="请填写完整登录信息！";
                    showError();
                    setTimeout("hideError()",5000);
                }
                else if(!IntCheck(login.id)){
                    self.errorMsg="ID必须为数字";
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    var loginInfo=[login.id,login.password];
                    this.$http.get("http://localhost:8080/venue/login/"+loginInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="noUser"){
                            self.errorMsg="该场馆ID不存在！请注册或正确填写！";
                            showError();
                            setTimeout("hideError()",5000);
                        }else if(res=="errorPassword"){
                            self.errorMsg="密码不正确！";
                            showError();
                            setTimeout("hideError()",5000);
                        }else if(res=="notCheck" || res=="notPass"){
                            // self.errorMsg="您还没有审核通过！请等待管理员审核！如在申请48小时之后您的号还没有审核成功说明您申请失败，请重新注册！"
                            // showError();
                            // setTimeout("hideError()",5000);
                            // window.location.href="/pages/venues/modifyVenue.html";
                            window.location.href="/tickets/venueHome"

                        } else if(res=="success"){
                            window.location.href="/tickets/venueHome"
                        }else {
                            self.errorMsg="发生未知错误，请重试！"
                            showError();
                            setTimeout("hideError()",5000);
                        }
                    })
                }

            }
        }
    })


})

function hideError() {
    var alertBox=document.getElementById("venueLoginError");
    alertBox.style.display="none";
}

function showError() {
    var alertBox=document.getElementById("venueLoginError");
    alertBox.style.display="block";
}

function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag=r.test(num);
    return flag;
}