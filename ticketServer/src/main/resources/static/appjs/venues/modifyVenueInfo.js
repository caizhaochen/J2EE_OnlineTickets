function venueModifyInfo() {
    var vue=new Vue({
        el:"#modifyVenueVue",
        data:{
            errorMsg:'',
            successMsg:'',
            venueID:'',
            venueName:'您还没有登录',
            venuePhone:'',
            venueLocation:'',
            venueInfo:'',
            venueLine:'',
            venueRow:''
        },
        methods:{
            venueModifyInfoSubmit:function () {
                const self=this;
                if(self.venueID==""||self.venueName==""||self.venuePhone==""||self.venueLocation==""||self.venueInfo==""||self.venueLine==""||self.venueRow==""){
                    self.errorMsg = "请填写完整信息！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else if (!IntCheck(self.venueLine)||!IntCheck(self.venueRow)) {
                    self.errorMsg = "行数和列数都必须为正整数！"
                    showError();
                    setTimeout("hideError()", 5000);
                }
                else{
                    var modifyInfo=[self.venueID,self.venueName,self.venuePhone,self.venueLocation,self.venueInfo,self.venueLine,self.venueRow];
                    this.$http.get("http://localhost:8080/venue/modify/"+modifyInfo).then(function (response) {
                        if(response.bodyText=="fail"){
                            self.errorMsg = "修改失败请重试！"
                            showError();
                            setTimeout("hideError()", 5000);
                        }else if(response.bodyText=="same"){
                            self.errorMsg = "您的信息并没有发生改变，不需要审核！"
                            showError();
                            setTimeout("hideError()", 5000);
                        }else if(response.bodyText=="success"){
                            window.location.href="/venues/modifySuccess";
                        }

                    })
                }
            }
        },
        mounted:function () {
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
                    this.venuePhone=venueInfo.venuephone;
                    this.venueLocation=venueInfo.location;
                    this.venueInfo=venueInfo.information;
                    this.venueLine=venueInfo.venueline;
                    this.venueRow=venueInfo.venuerow;
                }
                else{
                    this.venueID=venueInfo.venueid;
                    this.venueName=venueInfo.venuename;
                    this.venuePhone=venueInfo.venuephone;
                    this.venueLocation=venueInfo.location;
                    this.venueInfo=venueInfo.information;
                    this.venueLine=venueInfo.venueline;
                    this.venueRow=venueInfo.venuerow;
                }

            })
        }
    });
}

function hideError() {
    var alertBox = document.getElementById("modifyVenueError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("modifyVenueError");
    alertBox.style.display = "block";

}
function hideSuccess() {
    var alertBox = document.getElementById("modifyVenueSuccess");
    alertBox.style.display = "none";
}

function showSuccess() {
    var alertBox = document.getElementById("modifyVenueSuccess");
    alertBox.style.display = "block";

}

function IntCheck(num) {
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    var flag=r.test(num);
    return flag;
}

function hideNav() {
    var bar=document.getElementsByClassName("barFun");
    for (var i=0;i<bar.length;i++){
        bar[i].style.display="none";
    }
}

window.onload=function () {
    venueModifyInfo();
}
