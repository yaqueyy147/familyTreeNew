/**
 * Created by suyx on 2016/12/18.
 */
var checkCodePre;
$(function () {
    checkCodePre = drawPic();
    $("#regedit").click(function () {

        var userName = $("#userName").val();
        var password = $("#password").val();
        var companyName = $("#companyName").val();
        var userType = $("input[name='userType']:checked").val();

        if($.trim(userName).length <= 0){
            alert("用户名不能为空！");
            return ;
        }
        if($.trim(password).length <= 0){
            alert("密码不能为空！");
            return ;
        }
        if(userType == 2){
            if($.trim(companyName).length <= 0){
                alert("公司名不能为空！");
                return ;
            }
        }

        $("#regeditForm").attr("action",projectUrl + "/sign/regesterNew");
        $("#regeditForm").submit();
    });

    $("#companyRegeditbb").click(function () {
        $("#companyForm").attr("action",projectUrl + "/sign/companyRegester");
        $("#companyForm").submit();
    });

    $("input[name='userType']").click(function () {
        var sign = $(this).val();
        if(sign == 1){
            $("#companyDiv").hide();
        }
        if(sign == 2){
            $("#companyDiv").show();
        }
    });

    document.getElementById("canvas").onclick = function(e){
        e.preventDefault();
        checkCodePre = drawPic();
    };
});
