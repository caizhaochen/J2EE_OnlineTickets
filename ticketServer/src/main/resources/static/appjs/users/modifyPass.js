

function modifyPass() {
    var vue = new Vue({
        el: "#modifyPassVue",
        data: {
            userEmailNoChar:'',
            originPass: '',
            email: '',
            name: '您还没有登录',
            level: '',
            origin: '',
            newPass: '',
            newConfirm: '',

            // password: '',
            // ischeck: '',
            // token: '',
            // activetime: ''
        },
        methods: {
            modifyPassSubmit: function () {
                const self = this;
                if (self.origin == "" || self.newPass == "" || self.newConfirm == "") {
                    toastr.error("请填写完整信息！");
                }
                else if (self.origin != self.originPass) {
                    toastr.error("原密码不正确！");
                }
                else if (self.newPass != self.newConfirm) {
                    toastr.error("确认密码与新密码不吻合！");
                }
                else {
                    var modifyPass = [self.email, self.newPass];
                    this.$http.get("http://localhost:8080/user/modifyPass/" + modifyPass).then(function (response) {
                        if (response.bodyText == "fail") {
                            toastr.error("修改失败，请重试！");
                        }
                        if (response.bodyText == "success") {
                            window.location.href="/";
                        }
                    })

                }

            }
        },
        mounted: function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo = response.data;
                var splitEmail=[];
                splitEmail=userInfo.email.split(".");
                var i=0;
                for(i=0;i<splitEmail.length;i++){
                    this.userEmailNoChar=this.userEmailNoChar+splitEmail[i];
                };
                console.log("userEmailNoChar"+this.userEmailNoChar);
                this.email = userInfo.email;
                this.name = userInfo.username;
                this.level = userInfo.level;
                this.originPass = userInfo.userpassword;
                // this.password=userInfo.userpassword;
                // this.ischeck=userInfo.ischeck;
                // this.token=userInfo.token;
                // this.activetime=userInfo.activetime;
                $("#userIcon").on("error",function () {
                    $(this).attr("src","/images/show1.jpg");
                })
            })
        }
    })

}


function hideError() {
    var alertBox = document.getElementById("modifyPassError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("modifyPassError");
    alertBox.style.display = "block";

}


window.onload = function () {
    modifyPass();
}