function home() {
    Vue.filter('currency',
        <!-- value 格式为13位unix时间戳 -->
        <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
        function (value) {
            var toFixedNum = Number(value).toFixed(3);
            var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        });

    var vue=new Vue({
        el:"#homeVue",
        data:{
            username:'您还没有登录',
            useremail:'',
            usersex:'',
            userbirth:'',
            userlevel:'',
            usercredit:0,
            userconsume:0,
            userEmailNoChar:'',
            image:'',
            preInfo:true,
            preSafe:false,
            originPass: '',
            origin: '',
            newPass: '',
            newConfirm: '',
        },
        methods:{
            infoClick:function () {
                $("#privateInfo").attr("class","favoriteMenuTitleActive");
                $("#privateSafe").attr("class","favoriteMenuTitle");
                this.preInfo=true;
                this.preSafe=false;
            },
            safeClick:function () {
                $("#privateInfo").attr("class","favoriteMenuTitle");
                $("#privateSafe").attr("class","favoriteMenuTitleActive");
                this.preSafe=true;
                this.preInfo=false;
            },
            selectImage:function(e){
                console.log("选择了上传图片");
                // this.image=e.target.files[0];
                var file = e.target.files[0];
                if (file!=null){
                    var imgSize=file.size/1024;
                    if (imgSize>1000){
                        toastr.error("请保证图片的大小不超过1M！");
                        return;
                    }else {
                        // var file = e.target.files[0];
                        var reader = new FileReader();
                        reader.readAsDataURL(file);
                        this.image=reader.result;
                        console.log("this.image:"+this.image);
                        console.log("reader.result:"+reader.result);
                        reader.onloadend = function (){
                            // 图片的 base64 格式, 可以直接当成 img 的 src 属性值
                            var dataURL = reader.result;
                            document.getElementById('userIcon').src=dataURL;
                            console.log("dataURL:"+dataURL);
                            vue.image=dataURL;
                            this.image=dataURL;
                            // console.log("this.image"+this.image)
                            // console.log("vue.image"+vue.image);
                            vue.$http.post("http://localhost:8080/user/uploadUserIcon",{image:this.image,userEmail:vue.userEmailNoChar},{emulateJSON: true})
                                .then(function(response){
                                        console.log(response);
                                        toastr.success("上传头像成功！");
                                    },
                                    function(error){
                                        console.log(error);
                                        toastr.error("上传头像失败!");
                                    });
                        };
                    }
                }

            },
            saveModify:function () {
                var birth=$("#birth").val();
                if(this.username==""){
                    toastr.error("昵称不得为空！");
                }
                else if (birth==""){
                    toastr.error("请选择生日！");
                }
                else {
                    var modifyInfo = [this.useremail, this.username, birth, this.usersex];
                    // var modifyInfo = [self.email, self.name, self.birth, self.gender, self.password, self.level, self.ischeck, self.token, self.activetime];
                    console.log(modifyInfo);
                    this.$http.get("http://localhost:8080/user/modifyInfo/"+modifyInfo).then(function (response) {
                        if(response.bodyText=="fail"){
                            toastr.error("修改失败，请重试！");
                        }
                        if(response.bodyText=="success"){
                            toastr.success("修改成功！");
                        }

                    });
                }

            },
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
                    var modifyPass = [self.useremail, self.newPass];
                    this.$http.get("http://localhost:8080/user/modifyPass/" + modifyPass).then(function (response) {
                        if (response.bodyText == "fail") {
                            toastr.error("修改失败，请重试！");
                        }
                        if (response.bodyText == "success") {
                            window.location.href="/";
                        }
                    })

                }

            },

        },
        mounted:function () {
            this.$http.get("http://localhost:8080/tickets/getUserInfo").then(function (response) {
                console.log(response);
                var userInfo=response.data;
                var splitEmail=[];
                splitEmail=userInfo.email.split(".");
                var i=0;
                for(i=0;i<splitEmail.length;i++){
                    this.userEmailNoChar=this.userEmailNoChar+splitEmail[i];
                };
                console.log("userEmailNoChar"+this.userEmailNoChar);
                this.useremail=userInfo.email;
                this.username=userInfo.username;
                this.userbirth=userInfo.userbirth;
                this.usersex=userInfo.usersex;
                this.userlevel=userInfo.level;
                this.usercredit=userInfo.credit;
                this.userconsume=userInfo.userconsume;
                this.originPass = userInfo.userpassword;

                $(".personIcon").on("error",function () {
                    $(this).attr("src","/images/show1.jpg");
                })
            })
        }
    });
}

// function deleteUser() {
//     new Vue({
//         el:'#deleteVue',
//         data:{
//
//         },
//         methods:{
//             deleteuser:function () {
//                 alert("???")
//             }
//         }
//     })
// }


window.onload=function () {
    home();
    // deleteUser();
}
