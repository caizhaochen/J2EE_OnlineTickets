function RQcheck(RQ) {
    var date = RQ;
    var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);

    if (result == null)
        return false;
    var d = new Date(result[1], result[3] - 1, result[4]);
    return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);

}

function register() {
    var vue=new Vue({
        el:"#registerVUE",
        data:{
            errorMsg:'error！',
            register:{
                email:'',
                name:'',
                // birth:'',
                code:'',
                // gender:'男',
                password:'',
                confirmPass:'',
                waitTime:60
            }
        },
        methods:{
            getCodeFunction:function () {
                var time;
                if (this.register.email == ""){
                    toastr.error("请先输入你的邮箱！");
                }else {
                    $("#codeGetButton").attr("disabled",true);
                    $("#codeGetButton").val("正在发送...");
                    $("#codeGetButton").css("background-color","#999999");
                    this.$http.post("http://localhost:8080/user/getCheckCode/"+this.register.email).then(
                        function(response)  {
                            console.log(response);
                            toastr.success("发送成功！请在30分钟内填写验证码！");

                            console.log("start wait 60s");
                            time=setInterval(
                                function () {
                                    console.log("在倒计时方法中的waitTime-"+vue.waitTime);
                                    if (vue.waitTime==0){
                                        $("#codeGetButton").attr("disabled",false);
                                        $("#codeGetButton").val("获取验证码");
                                        $("#codeGetButton").css("background-color","#c4e3f3");
                                        console.log("倒计时方法准备return");
                                        clearInterval(time);
                                        return;
                                    }
                                    else {
                                        console.log("still wait "+vue.waitTime);
                                        $("#codeGetButton").attr("disabled",true);
                                        $("#codeGetButton").val(vue.waitTime+"s后重新获取");
                                        $("#codeGetButton").css("background-color","#999999");
                                        vue.waitTime--;
                                    }
                                },1000);

                        }),
                        function () {
                            toastr.error("网络或者服务器错误，请重试！")
                        }

                }
                this.waitTime=60;
                return;
            },
            registerSubmit:function () {
                const selfThis=this;
                var self =(this.register);
                // var myBirth=$('#birthText').val();
                if(self.email==""||self.name==""||self.code==""||self.password==""||self.confirmPass==""){
                    toastr.error("请确认信息填写完整!")
                }
                else{
                    if(self.password!=self.confirmPass){
                        toastr.error("请确认两次输入的密码一样！")
                    }
                    else{
                        var userInfo=[self.email,self.name,self.code,self.password];
                        this.$http.get("http://localhost:8080/user/register/"+userInfo).then(function (response) {
                            var registerRes=response.data;
                            if (registerRes[0]=="fail"){
                                toastr.error(registerRes[1]);
                            }
                            if(registerRes[0]=="success"){
                                toastr.success("注册成功，即将进入！");
                                // setTimeout("window.location.href=\"/userLogin\"",3000);
                                var loginInfo=[userInfo[0],userInfo[3]];
                                this.$http.get("http://localhost:8080/user/login/"+loginInfo).then(function (response) {
                                    var res=response.bodyText;
                                    if(res=="noUser"){
                                        toastr.error("该邮箱账户不存在！请注册或正确填写！");
                                    }else if(res=="errorPassword"){
                                        toastr.error("密码不正确!");

                                    }else if(res=="notPermit"){
                                        toastr.error("该号已被注销，请换一个邮箱注册登录！");
                                    } else if(res=="success"){
                                        window.location.href="/showTickets"
                                    }
                                })
                            }

                        })
                    }

                }

            }
        }
    })

}

function showSuccess() {
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "positionClass": "toast-top",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr.success("注册成功，即将跳转到登录界面！");
}

function hideError() {
    var alertBox=document.getElementById("registerError");
    alertBox.style.display="none";
}

function showError() {
    var alertBox=document.getElementById("registerError");
    alertBox.style.display="block";

}
window.onload=function () {
    register();
}
// $(document).ready(function() {
//
//     register();
// })