/**
 * Created by suyx on 2016/12/20 0020.
 */
$(function () {

    if(loginCode == -1){
        $("#loginFail").text("登录失败：用户名或密码错误!");
    }

    $("#signIn").click(function () {

        $("#signInForm").attr("action","/sign/signIn");
        $("#signInForm").submit();
    });
});