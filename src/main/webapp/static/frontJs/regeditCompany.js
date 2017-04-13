/**
 * Created by suyx on 2016/12/18.
 */
var checkCodePre;
$(function () {
    $("#province").val("");
    $("#province").change();

    checkCodePre = drawPic();
    $("#companyRegeditbb").click(function () {

        var companyLoginName = $("#companyLoginName").val();
        var companyLoginPassword = $("#companyLoginPassword").val();
        var companyLoginPasswordAffirm = $("#companyLoginPasswordAffirm").val();
        var companyName = $("#companyName").val();
        var companyMobilePhone = $("#companyMobilePhone").val();
        var companyTelephone = $("#companyTelephone").val();
        var businessLicense = $("#businessLicense").val();
        var checkCode = $("#checkCode").val();
        var province = $("#province").val();
        var city = $("#city").val();
        var district = $("#district").val();

        if($.trim(companyLoginName).length <= 0){
            alert("用户名不能为空！");
            return ;
        }
        if($.trim(companyLoginPassword).length <= 0){
            alert("密码不能为空！");
            return ;
        }
        if(companyLoginPassword != companyLoginPasswordAffirm){
            alert("密码输入不一致！");
            return ;
        }
        if($.trim(companyName).length <= 0){
            alert("公司名不能为空！");
            return ;
        }
        if($.trim(companyMobilePhone).length <= 0 && $.trim(companyTelephone).length <= 0){
            alert("请至少输入一个手机号码或者一个固定电话！");
            return ;
        }
        if($.trim(companyMobilePhone).length > 0 && $.trim(companyMobilePhone).length != 11){
            alert("请输入正确的11位手机号码！");
            return ;
        }
        if($.trim(province).length <= 0){
            alert("请选择省！");
            return ;
        }
        if($.trim(city).length <= 0){
            alert("请选择市！");
            return ;
        }
        if($.trim(district).length <= 0){
            alert("请选择区！");
            return ;
        }

        if($.trim(businessLicense).length <= 0){
            alert("请上传公司营业执照！");
            return ;
        }
        if(checkCodePre.toUpperCase() != checkCode.toUpperCase()){
            alert("验证码错误！");
            return;
        }

        $("#regeditForm").attr("action",projectUrl + "/sign/companyRegester");
        $("#regeditForm").submit();
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
