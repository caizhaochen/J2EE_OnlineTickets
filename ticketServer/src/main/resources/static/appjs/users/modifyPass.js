

function modifyPass() {
    var vue = new Vue({
        el: "#modifyPassVue",
        data: {
            errorMsg: 'error！',
            originPass: '',
            email: '',
            name: '您还没有登录',
            level: '',
            origin: '',
            newPass: '',
            newConfirm: ''
            // password: '',
            // ischeck: '',
            // token: '',
            // activetime: ''
        },
        methods: {
            modifyPassSubmit: function () {
                const self = this;
                if (self.origin == "" || self.newPass == "" || self.newConfirm == "") {
                    // return "false";
                    self.errorMsg = "请填写完整信息！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if (self.origin != self.originPass) {
                    self.errorMsg = "原密码不正确！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if (self.newPass != self.newConfirm) {
                    self.errorMsg = "确认密码与新密码不吻合！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else {
                    var modifyPass = [self.email, self.newPass];
                    this.$http.get("http://localhost:8080/user/modifyPass/" + modifyPass).then(function (response) {
                        if (response.bodyText == "fail") {
                            self.errorMsg = "修改失败，请重试！";
                            showError();
                            setTimeout("hideError()", 5000);
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
                this.email = userInfo.email;
                this.name = userInfo.username;
                this.level = userInfo.level;
                this.originPass = userInfo.userpassword;
                // this.password=userInfo.userpassword;
                // this.ischeck=userInfo.ischeck;
                // this.token=userInfo.token;
                // this.activetime=userInfo.activetime;
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