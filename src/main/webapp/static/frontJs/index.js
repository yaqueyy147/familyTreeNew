/**
 * Created by suyx on 2017/1/5 0005.
 */
$(function () {

    $("#province").val("");
    $("#province").change();

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

    $("#searchBtn").click(function () {
        var params = {};
        params.familyName = $("#familyName").val();
        params.province = $("#province").val();
        params.city = $("#city").val();
        params.district = $("#district").val();

        $.ajax({
            type:'post',
            url: projectUrl + '/familyTree/queryFamily',
            dataType: 'json',
            data:params,
            async:false,
            success:function (data) {
                var familyList = data.familyList;
                var familyContent = "";
                if(familyList.length > 0){
                    for(var i=0;i<familyList.length;i++){
                        var ii = familyList[i];
                        var visitState = ii.visitStatus;
                        var visitDesc = "加密";
                        if(visitState == 1){
                            visitDesc = "开放";
                        }else if(visitState == 2){
                            visitDesc = "仅族人查看";
                        }
                        familyContent += "<div class='col-sm-3 col-md-2'>";
                        familyContent += "<div class='thumbnail'>";
                        familyContent += "<a href='javascript:void(0)' onclick=\"viewFamily('" + ii.id + "','" + ii.visitStatus + "','" + ii.visitPassword + "')\">";
                        familyContent += "<img src='" + ii.photoUrl + "' class='img-thumbnail'/></a>";
                        familyContent += "<div class='caption'>";
                        familyContent += "<h6>" + ii.familyFirstName + "氏族谱（" + ii.id + "）</h6>";
                        familyContent += "<p>状态：" + visitDesc + "</p>";
                        familyContent += "<p>" + ii.familyName + "</p>";
                        familyContent += "<p name='familyDesc' style='text-overflow: ellipsis;white-space: nowrap;overflow: hidden' data-container='body' data-toggle='popover' data-placement='right' data-content='" + ii.familyDesc +"'>";
                        familyContent += ii.familyDesc;
                        familyContent += "</p></div></div></div>";
                    }
                }
                $("#familyContent").html(familyContent);
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });

    });

});

function viewFamily(familyId,visitStatus,visitPassword) {
    if(visitStatus == 1){
        location.href = projectUrl + "/family/viewFamily_visitor?familyId=" + familyId;
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