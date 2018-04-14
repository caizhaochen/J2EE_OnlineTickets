function adminHome() {
    var vue=new Vue({
        el:"#adminHomeVue",
        data:{
            errorMsg:'',
            checkNum:0,
            uncheckVenue:[],
            hasCheck:false,
        },
        mounted:function () {
            this.$http.get("http://localhost:8080/venue/getUnCheckedVenues").then(function (response) {
                this.uncheckVenue=response.data;
                if(this.uncheckVenue.length!=0){
                    this.hasCheck=true;
                    this.checkNum=this.uncheckVenue.length;
                }
            })
        }
    })
}

window.onload=function () {
    adminHome();
}