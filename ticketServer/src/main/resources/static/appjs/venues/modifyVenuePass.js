

function modifyVenuePass() {
    var vue = new Vue({
        el: "#modifyVenuePassVue",
        data: {
            errorMsg: 'error！',
            originPass: '',
            venueID: '',
            venueName: '您还没有登录',
            origin: '',
            newPass: '',
            newConfirm: ''
        },
        methods: {
            modifyVenuePassSubmit: function () {
                const self = this;
                if (self.origin == "" || self.newPass == "" || self.newConfirm == "") {
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
                    var modifyPass = [self.venueID, self.newPass];
                    this.$http.get("http://localhost:8080/venue/modifyPass/" + modifyPass).then(function (response) {
                        if (response.bodyText == "fail") {
                            self.errorMsg = "修改失败，请重试！";
                            showError();
                            setTimeout("hideError()", 5000);
                        }
                        if (response.bodyText == "success") {
                            window.location.href="/venues";
                        }
                    })

                }

            }
        },
        mounted: function () {
            this.$http.get("http://localhost:8080/venue/getVenueInfo").then(function (response) {
                console.log(response);
                var venueInfo=response.data;
                if (venueInfo==null){
                    this.errorMsg="请求超时，请重新登录";
                    showError();
                    setTimeout("hideError()",5000);
                }else if(venueInfo.ischecked==0 || venueInfo.ischecked==2){
                    // window.location.href="/venueNotCheck"
                    hideNav();
                    this.errorMsg=venueInfo.checkInfo;
                    showError();
                    this.venueID=venueInfo.venueid;
                    this.venueName=venueInfo.venuename;
                    this.originPass=venueInfo.venuepassword;
                }
                else{
                    this.venueID=venueInfo.venueid;
                    this.venueName=venueInfo.venuename;
                    this.originPass=venueInfo.venuepassword;
                }

            })
        }
    })

}

function hideNav() {
    var bar=document.getElementsByClassName("barFun");
    for (var i=0;i<bar.length;i++){
        bar[i].style.display="none";
    }
}

function hideError() {
    var alertBox = document.getElementById("modifyVenuePassError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("modifyVenuePassError");
    alertBox.style.display = "block";

}


window.onload = function () {
    modifyVenuePass();
}