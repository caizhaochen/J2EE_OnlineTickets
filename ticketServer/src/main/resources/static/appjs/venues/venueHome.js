function venueHome() {
    var vue=new Vue({
        el:"#venueHomeVue",
        data:{
            errorMsg:'Warning!',
            venueID:'',
            venueName:'您还没有登录',
            venuePhone:'',
            venueLocation:'',
            venueInfo:'',
            venueLine:'',
            venueRow:''
        },
        methods:{

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/venue/getVenueInfo").then(function (response) {
                console.log(response);
                var venueInfo=response.data;
                if (venueInfo==null){
                    window.location.href="/venues"
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
                else if(venueInfo.ischecked==0 || venueInfo.ischecked==2){
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

function hideNav() {
    var bar=document.getElementsByClassName("barFun");
    for (var i=0;i<bar.length;i++){
        bar[i].style.display="none";
    }
}

function showError() {
    var alertBox = document.getElementById("venueHomePassError");
    alertBox.style.display = "block";

}

window.onload=function () {
    venueHome();
};
