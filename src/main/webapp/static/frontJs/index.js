/**
 * Created by suyx on 2017/1/5 0005.
 */
$(function () {
   $("p[name='familyDesc']").mouseover(function () {
       $(this).popover('show');
   });

    $("p[name='familyDesc']").mouseout(function () {
        $(this).popover('hide');
    });

    $(".panel-heading").click(function () {
        var contentDiv = $(this).next();

        if($(contentDiv).is(":hidden")){
            $(contentDiv).show(300);
            $(this).find("i").removeClass("fa-chevron-down");
            $(this).find("i").addClass("fa-chevron-up");
        } else{
            $(contentDiv).hide(300);
            $(this).find("i").removeClass("fa-chevron-up");
            $(this).find("i").addClass("fa-chevron-down");
        }
    });

    $("#checkPassword").click(function () {
        var familyId = $("#visitFamilyId").val();
        var passwordPre = $("#passwordPre").val();
        var password = $("#password").val();
        if($.md5(password) != passwordPre){
            alert("密码输入有误!");
            return;
        }
        location.href = projectUrl + "/family/viewFamily?familyId=" + familyId;
    });

});

function viewFamily(familyId,visitStatus,visitPassword) {
    if(visitStatus == 1){
        location.href = projectUrl + "/family/viewFamily?familyId=" + familyId;
        return;
    }else if(visitStatus == 0){
        $("#passwordPre").val(visitPassword);
        $("#visitFamilyId").val(familyId);
        $("#visitPasswordModal").modal('show');

        return;
    }else{
        alert("您无没有权限访问这个族谱!");
        return;
    }
}