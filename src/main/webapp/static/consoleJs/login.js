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
        var loginName = $("#loginName").val();
        var password = $("#userPassword").val();
        var checkCode = $("#checkCode").val();

        if($.trim(loginName).length <= 0){
            alert("用户名不能为空！");
            return ;
        }
        if($.trim(password).length <= 0){
            alert("密码不能为空！");
            return ;
        }

        if(checkCodePre.toUpperCase() != checkCode.toUpperCase()){
            alert("验证码错误！");
            $("#canvas").click();
            return;
        }
        $("#signInForm").attr("action",projectUrl + "/consoles/loginIn");
        $("#signInForm").submit();
    });

    document.getElementById("canvas").onclick = function(e){
        e.preventDefault();
        checkCodePre = drawPic();
    };
});