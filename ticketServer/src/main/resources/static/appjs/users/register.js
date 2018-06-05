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
                gender:'男',
                password:'',
                confirmPass:''
            }
        },
        methods:{
            registerSubmit:function () {
                const selfThis=this;
                var self =(this.register);
                var myBirth=$('#birthText').val();
                if(self.email==""||self.name==""||myBirth==""||self.gender==""||self.password==""||self.confirmPass==""){
                    // return "false";
                    selfThis.errorMsg="请填写完整信息！"
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    console.log(myBirth);
                    // if(!RQcheck(self.birth)){
                    //     selfThis.errorMsg="请检查日期以及日期格式是否正确！"
                    //     showError();
                    //     setTimeout("hideError()",5000);
                    // }
                    // else
                    if(self.password!=self.confirmPass){
                        // selfThis.errorMsg="请确认两次输入的密码一样！"
                        // showError();
                        // setTimeout("hideError()",5000);
                        toastr.error("请确认两次输入的密码一样！")
                    }
                    else{
                        var userInfo=[self.email,self.name,myBirth,self.gender,self.password];
                        this.$http.get("http://localhost:8080/user/register/"+userInfo).then(function (response) {
                            if(response.bodyText=="exists"){
                                selfThis.errorMsg="该邮箱已被注册，请登录或用其他邮箱注册";
                                // showError();
                                // setTimeout("hideError()",5000);
                                toastr.error("该邮箱已被注册，请登录或用其他邮箱注册")
                            }
                            if(response.bodyText=="fail"){
                                selfThis.errorMsg="注册失败，请重试！";
                                // showError();
                                toastr.error("注册失败，请重试！")
                            }
                            if(response.bodyText=="success"){
                                toastr.success("注册成功，即将跳转到登录界面！");
                                setTimeout("window.location.href=\"/userLogin\"",3000);
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