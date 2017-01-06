/**
 * Created by suyx on 2016/12/20 0020.
 */
var checkCodePre;
$(function () {
    if(loginCode == -1){
        $("#loginFail").text("登录失败：用户名或密码错误!");
    }
    if(loginCode == -2){
        $("#loginFail").text("未登录或登录已失效!");
    }
    checkCodePre = drawPic();

    $("#signIn").click(function () {
        var checkCode = $("#checkCode").val();
        if(checkCodePre.toUpperCase() != checkCode.toUpperCase()){
            alert("验证码错误！");
            return;
        }
        $("#signInForm").attr("action",projectUrl + "/sign/signIn");
        $("#signInForm").submit();
    });

    document.getElementById("canvas").onclick = function(e){
        e.preventDefault();
        checkCodePre = drawPic();
    };
});