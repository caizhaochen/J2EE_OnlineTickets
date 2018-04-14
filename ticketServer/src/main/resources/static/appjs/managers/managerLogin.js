$(document).ready(function () {

    var vue = new Vue({
        el: "#managerLoginVUE",
        data: {
            errorMsg: "error!",
            name: '',
            password: ''

        },
        methods: {
            managerLoginSubmit: function () {
                const self = this;

                if (self.name == "" || self.password == "") {
                    self.errorMsg = "请填写完整登录信息！";
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if(!(self.name=="admin")){
                    self.errorMsg = "用户名不正确";
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if(!(self.password=="czc489622czc")){
                    self.errorMsg = "用户名不正确";
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else {
                    window.location.href = "/pages/managers/managerHome.html";

                }

            }
        }
    })


})

function hideError() {
    var alertBox = document.getElementById("managerLoginError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("managerLoginError");
    alertBox.style.display = "block";

}