function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag=r.test(num);
    return flag;
}

function venueRegister() {
    var vue=new Vue({
        el:"#venueRegisterVUE",
        data:{
            errorMsg:'error！',
            venueRegister:{
                name:'',
                phone:'',
                location:'',
                information:'',
                line:'',
                row:'',
                password:'',
                confirmPass:''
            },
            id:''
        },
        methods:{
            venueRegisterSubmit:function () {
                const selfThis=this;
                var self =(this.venueRegister);
                if(self.name==""||self.phone==""||self.location==""||self.information==""||self.line==""||self.row==""||self.password==""||self.confirmPass==""){
                    // return "false";
                    selfThis.errorMsg="请填写完整信息！"
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    if(!IntCheck(self.line)||!IntCheck(self.row)){
                        selfThis.errorMsg="行数和列数都必须为正整数！"
                        showError();
                        setTimeout("hideError()",5000);
                    }
                    else if(self.password!=self.confirmPass){
                        selfThis.errorMsg="请确认两次输入的密码一样！"
                        showError();
                        setTimeout("hideError()",5000);
                    }
                    else{
                        var venueInfo=[self.name,self.phone,self.location,self.information,self.line,self.row,self.password];
                        this.$http.get("http://localhost:8080/venue/register/"+venueInfo).then(function (response) {
                            console.log(response);
                            if(response.bodyText=="exists"){
                                selfThis.errorMsg="该场馆名字已被注册，请登录或用其他名字注册";
                                showError();
                                setTimeout("hideError()",5000);
                            }
                            else if(response.bodyText=="fail"){
                                selfThis.errorMsg="注册失败，请重试！";
                                showError();
                                setTimeout("hideError()",5000);
                            }
                            else {
                            // if(response.bodyText.indexOf("success:")>0){
                                console.log(response.bodyText);
                                var res=response.bodyText.split(":");
                                selfThis.id=res[1];
                                $("#venueRegisterModal").modal();
                                // window.location.href="/venues/registerSuccess"
                            }

                        })
                    }

                }

            }
        }
    })

}


function hideError() {
    var alertBox=document.getElementById("venueRegisterError");
    alertBox.style.display="none";
}

function showError() {
    var alertBox=document.getElementById("venueRegisterError");
    alertBox.style.display="block";

}
window.onload=function () {
    venueRegister();
}
