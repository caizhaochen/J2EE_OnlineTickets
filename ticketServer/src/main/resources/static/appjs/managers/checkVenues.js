function checkVenues() {
    var vue=new Vue({
        el:"#checkVenuesVue",
        data:{
            errorMsg:'error',
            successMsg:'success',
            hasCheck:false,
            uncheckInfo:[],
            notPassReason:'',
            tempVenueId:'',
        },
        methods:{
            refuse:function (venueid) {
                this.tempVenueId=venueid;
            },
            pass:function (venueid) {
                var checkVar=[venueid,1,"您已通过审核"];
                this.$http.get("http://localhost:8080/venue/checkVenue/"+checkVar).then(function (response) {
                    var res=response.bodyText;
                    if(res=="success"){
                        window.location.href="/pages/managers/checkVenue.html";
                    }
                    else {
                        this.errorMsg="发生错误"
                        showError();
                        setTimeout("hideError()",5000);
                    }
                })
            },
            denyVenue:function () {
                if(this.notPassReason==""){
                    this.errorMsg="请输入不通过的理由！"
                    showError();
                    setTimeout("hideError()",5000);
                }
                else{
                    this.notPassReason="您的信息未通过审核，原因："+this.notPassReason;
                    var checkVar=[this.tempVenueId,2,this.notPassReason];
                    this.$http.get("http://localhost:8080/venue/checkVenue/"+checkVar).then(function (response) {
                        var res=response.bodyText;
                        if(res=="success"){
                            window.location.href="/pages/managers/checkVenue.html";
                        }
                        else {
                            this.errorMsg="发生错误"
                            showError();
                            setTimeout("hideError()",5000);
                        }
                    })
                }
                $("#notPassModal").modal('hide');
            }
        },
        mounted:function () {
            this.$http.get("http://localhost:8080/venue/getUnCheckedVenues").then(function (response) {
                this.uncheckInfo=response.data;
                if(this.uncheckInfo.length!=0){
                    this.hasCheck=true;
                }
                console.log(this.uncheckInfo);
            })
        }
    })

}
function hideError() {
    var alertBox = document.getElementById("checkVenuesError");
    alertBox.style.display = "none";
}

function showError() {
    var alertBox = document.getElementById("checkVenuesError");
    alertBox.style.display = "block";

}

window.onload=function () {
    checkVenues();
}