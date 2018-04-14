function deleteuser() {
   $.get("http://localhost:8080/user/deleteUser").then(function (response) {
        if(response=="success"){
            window.location.href="/";
        }
        else {
            alert("注销失败，请重试！")
        }
    })
}