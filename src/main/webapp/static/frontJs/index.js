/**
 * Created by suyx on 2017/1/5 0005.
 */
$(function () {

    $("#province").val("");
    $("#province").change();
    // $("p[name='familyDesc']").mouseover(function () {
    //    $(this).popover('show');
    // });
    //
    // $("p[name='familyDesc']").mouseout(function () {
    //     $(this).popover('hide');
    // });

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
        if(password != passwordPre){
            alert("密码输入有误!");
            return;
        }
        location.href = projectUrl + "/family/viewFamily_visitor?familyId=" + familyId;
    });

    $("#searchBtn").click(function () {
        $(".loading").show();
        var params = {};
        params.familyName = $("#familyName").val();
        params.province = $("#province").val();
        params.city = $("#city").val();
        params.district = $("#district").val();
        params.searchname = $("#searchname").val();

        $.ajax({
            type:'post',
            url: projectUrl + '/family/queryFamily',
            dataType: 'json',
            data:params,
            // async:false,
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
                        }
                        // else if(visitState == 2){
                        //     visitDesc = "仅族人查看";
                        // }
                        familyContent += "<div class='col-sm-3 col-md-2 familyDiv'>";
                        familyContent += "<div class='thumbnail'>";
                        familyContent += "<a href='javascript:void(0)' onclick=\"viewFamily('" + ii.id + "','" + ii.visitStatus + "','" + ii.visitPassword + "')\" style=\"float: none;width: 100%;\">";
                        familyContent += "<img class=\"familyImgFF\" src='" + ii.photoUrl + "' class='img-thumbnail'/></a>";
                        familyContent += "<div class='caption'>";
                        familyContent += "<h6>" + ii.familyFirstName + "（" + ii.id + "）</h6>";
                        // familyContent += "<h6>世界何氏族谱（" + ii.id + "）</h6>";
                        familyContent += "<p>家族人数：" + ii.peopleCount + "人</p>";
                        familyContent += "<p>状态：" + visitDesc + "</p>";
                        familyContent += "<p>" + ii.familyName + "</p>";
                        familyContent += "<p name='familyDesc' onmouseover='pPopover(this,1)' onmouseout='pPopover(this,2)' style='text-overflow: ellipsis;white-space: nowrap;overflow: hidden' data-container='body' data-toggle='popover' data-placement='right' data-content='" + ii.familyDesc +"'>";
                        familyContent += ii.familyDesc;
                        familyContent += "</p></div></div></div>";
                    }
                }

                // var listPersonalPoints = data.listPersonalPoints;
                // var personalPointsContent = "";
                // if(listPersonalPoints.length > 0){
                //     for(var i=0;i<listPersonalPoints.length;i++){
                //         var ii = listPersonalPoints[i];
                //
                //         personalPointsContent += "<tr>";
                //         personalPointsContent += "<td>" + (i + 1) + "</td>";
                //         personalPointsContent += "<td>" + ii.user_name + "</td>";
                //         personalPointsContent += "<td>" + ii.points + "</td>";
                //         personalPointsContent += "</tr>";
                //     }
                // }
                //
                // var listCompanyPoints = data.listCompanyPoints;
                // var companyPointsContent = "";
                // if(listCompanyPoints.length > 0){
                //     for(var i=0;i<listCompanyPoints.length;i++){
                //         var ii = listCompanyPoints[i];
                //
                //         companyPointsContent += "<tr>";
                //         companyPointsContent += "<td>" + (i + 1) + "</td>";
                //         companyPointsContent += "<td style=\"word-break: break-all;max-width: 100px;\">" + ii.company_name + "</td>";
                //         companyPointsContent += "<td>" + ii.points + "</td>";
                //         companyPointsContent += "</tr>";
                //     }
                // }
                // $("#personalPoints").html(personalPointsContent);
                // $("#companyPoints").html(companyPointsContent);
                $("#familyContent").html(familyContent);
                $(".loading").hide();
            },
            error:function (data) {
                var responseText = data.responseText;
                if(responseText.indexOf("登出跳转页面") >= 0){
                    ajaxErrorToLogin();
                }else{
                    alert(JSON.stringify(data));
                }
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
