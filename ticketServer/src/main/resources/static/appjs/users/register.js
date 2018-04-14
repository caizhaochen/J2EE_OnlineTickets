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
                birth:'',
                gender:'男',
                password:'',
                confirmPass:''
            }
        },
        methods:{
            registerSubmit:function () {
                const selfThis=this;
                var self =(this.register);
                if(self.email==""||self.name==""||self.birth==""||self.gender==""||self.password==""||self.confirmPass==""){
                    // return "false";
                    selfThis.errorMsg="请填写完整信息！"
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    if(!RQcheck(self.birth)){
                        selfThis.errorMsg="请检查日期以及日期格式是否正确！"
                        showError();
                        setTimeout("hideError()",5000);
                    }
                    else if(self.password!=self.confirmPass){
                        selfThis.errorMsg="请确认两次输入的密码一样！"
                        showError();
                        setTimeout("hideError()",5000);
                    }
                    else{
                        var userInfo=[self.email,self.name,self.birth,self.gender,self.password];
                        this.$http.get("http://localhost:8080/user/register/"+userInfo).then(function (response) {
                            if(response.bodyText=="exists"){
                                selfThis.errorMsg="该邮箱已被注册，请登录或用其他邮箱注册";
                                showError();
                                // setTimeout("hideError()",5000);
                            }
                            if(response.bodyText=="fail"){
                                selfThis.errorMsg="注册失败，请重试！";
                                showError();
                            }
                            if(response.bodyText=="success"){
                                window.location.href="/registerSuccess"
                            }

                        })
                    }

                }

            }
        }
    })

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