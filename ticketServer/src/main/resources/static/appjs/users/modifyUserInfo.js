function RQcheck(RQ) {
    var date = RQ;
    var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);

    if (result == null)
        return false;
    var d = new Date(result[1], result[3] - 1, result[4]);
    return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);

}

function modifyUser() {
    var vue = new Vue({
        el: "#modifyUserVue",
        data: {
            errorMsg: 'error！',
            successMsg:'success!',
            email: '',
            name: '您还没有登录',
            birth: '',
            gender: '男',
            level: ''
            // password: '',
            // ischeck: '',
            // token: '',
            // activetime: ''
        },
        methods: {
            modifySubmit: function () {
                const self = this;
                if (self.name == "" || self.birth == "" || self.gender == "") {
                    // return "false";
                    self.errorMsg = "请填写完整信息！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else {
                    if (!RQcheck(self.birth)) {
                        self.errorMsg = "请检查日期以及日期格式是否正确！"
                        showError();
                        setTimeout("hideError()", 5000);
                    }
                    else {
                        var modifyInfo = [self.email, self.name, self.birth, self.gender];
                        // var modifyInfo = [self.email, self.name, self.birth, self.gender, self.password, self.level, self.ischeck, self.token, self.activetime];
                        console.log(modifyInfo);
                        this.$http.get("http://localhost:8080/user/modifyInfo/"+modifyInfo).then(function (response) {
                            if(response.bodyText=="fail"){
                                self.errorMsg="修改失败，请重试！";
                                showError();
                                setTimeout("hideError()",5000);
                            }
                            if(response.bodyText=="success"){
                                // window.location.href="/registerSuccess"
                                self.successMsg="修改成功！"
                                showSuccess();
                                setTimeout("hideSuccess()",5000);
                            }

                        })
                    }

                }

            }
        },
        mounted: function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                var userInfo = response.data;
                this.email = userInfo.email;
                this.name = userInfo.username;
                this.birth= userInfo.userbirth;
                this.gender = userInfo.usersex;
                this.level = userInfo.level;
                // this.password=userInfo.userpassword;
                // this.ischeck=userInfo.ischeck;
                // this.token=userInfo.token;
                // this.activetime=userInfo.activetime;
            })
        }
    })

}


function hideError() {
    var alertBox = document.getElementById("modifyError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("modifyError");
    alertBox.style.display = "block";

}
function hideSuccess() {
    var alertBox = document.getElementById("modifySuccess");
    alertBox.style.display = "none";
}

function showSuccess() {
    var alertBox = document.getElementById("modifySuccess");
    alertBox.style.display = "block";

}

window.onload = function () {
    modifyUser()
}