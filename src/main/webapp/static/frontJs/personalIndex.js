/**
 * Created by suyx on 2016/12/22 0022.
 */
$(function () {
    // $("p[name='familyDesc']").mouseover(function () {
    //     $(this).popover('show');
    // });
    //
    // $("p[name='familyDesc']").mouseout(function () {
    //     $(this).popover('hide');
    // });

    $("input[name='visitStatus']").click(function () {
        var status = $(this).val();
        if(status == 0){
            $("#visitPasswordDiv").show();
        }else{
            $("#visitPassword").val("");
            $("#visitPasswordDiv").hide();
        }
    });

    $("#saveFamily").click(function () {
        $(this).text("处理中，请稍后...");
        $(this).attr('disabled',"true");
        if($.trim($("#familyName")).length <= 0){
            alert("请输入家族名称！");
            return;
        }
        if($.trim($("#province")).length <= 0){
            alert("请选择家族所在省！");
            return;
        }
        if($.trim($("#city")).length <= 0){
            alert("请选择家族所在市！");
            return;
        }
        if($.trim($("#district")).length <= 0){
            alert("请选择家族所在区县！");
            return;
        }

        var formData = {};
        var postUrl = projectUrl + "/family/saveFamily";
        var testData = $("#familyForm").serializeArray();

        for (var item in testData) {
            formData["" + testData[item].name + ""] = testData[item].value;
        }
        $.ajax({
            type:'post',
            url:postUrl,
            dataType:'json',
            data:formData,
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    var tFamily = data.tFamily;
                    var familyImg = tFamily.photoUrl;

                    var visitStatus = tFamily.visitStatus;
                    var statusDesc = "开放";
                    if(visitStatus == 0){
                        statusDesc = "加密";
                    } else if(visitStatus == 1){
                        statusDesc = "开放";
                    }
                    var imgHtml = "<div class=\"col-sm-6 col-md-2\"><div class=\"thumbnail\">";
                    imgHtml += "<a href=\"javascript:void(0)\" onclick=\"viewFamily('" + tFamily.id + "','" + visitStatus + "','" + tFamily.visitPassword + "')\" style='float: none;width: 100%'>";
                    imgHtml += "<img class=\"familyImgFF\" src=\"" + familyImg + "\" class=\"img-thumbnail\"/></a>";
                    imgHtml += "<div class=\"caption\">";
                    // imgHtml += "<h6><a href=\"javascript:void 0;\" onclick=\"toEdit('" + tFamily.id + "')\">世界何氏族谱（" + tFamily.id + "）</h6>";
                    imgHtml += "<h6><a href=\"javascript:void 0;\" onclick=\"toEdit('" + tFamily.id + "')\">" + tFamily.familyFirstName + "（" + tFamily.id + "）</h6>";
                    imgHtml += "<p>家族人数：0人</p>";
                    imgHtml += "<p>状态：" + statusDesc + "</p>";
                    imgHtml += "<p>" + tFamily.familyName + "</p>";
                    imgHtml += "<p name=\"familyDesc\" onmouseover='pPopover(this,1)' onmouseout='pPopover(this,2)' style=\"text-overflow: ellipsis;white-space: nowrap;overflow: hidden\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"right\" data-content=\"" + tFamily.familyDesc + "\">" + tFamily.familyDesc + "</p>";
                    imgHtml += "</div></div></div>";
                    $("#familyShow").append(imgHtml);
                    $("#addFamilyModal").modal('hide');
                    $("#saveFamily").text("提 交");
                    $("#saveFamily").removeAttr("disabled");
                }
                alert(data.msg);
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

    $("#checkPassword").click(function () {
        var familyId = $("#visitFamilyId").val();
        var passwordPre = $("#passwordPre").val();
        var password = $("#password").val();
        if(password != passwordPre){
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

function toEdit(familyId) {
    $.ajax({
        type:'post',
        url: projectUrl + "/family/getFamilyFromId",
        dataType:'json',
        // async:false,
        data:{familyId:familyId},
        success:function (data) {
            var family = data.tFamily;
            if(family){
                $("#familyId").val(familyId);
                $("#familyName").val(family.familyName);

                var visitStatus = family.visitStatus;
                $("input[type='radio'][value='" + visitStatus + "']").prop("checked","checked");
                $("input[type='radio'][value='" + visitStatus + "']").click();

                $("#visitPassword").val(family.visitPassword);
                $("#province").val(family.province);
                $("#province").change();
                $("#city").val(family.city);
                $("#city").change();
                $("#district").val(family.district);
                $("#familyDesc").val(family.familyDesc);
                $("#familyState").val(family.state);
                var imgPath = family.photoUrl;
                $("#result_img").attr('src',imgPath);
                $("#result_img").show();
                $("#imgFile").hide();
                $("#photoUrl").attr('value',imgPath);
                $("#show_img").mouseover(function(){
                    $("#result_img").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img").mouseout(function(){
                    $("#result_img").attr('src',imgPath);
                });

                $("#result_img").click(function(){
                    $("#result_img").hide();
                    $("#imgFile").show();
                    $("#photoUrl").removeAttr('value');
                    $("#show_img").unbind('mouseover');
                    $("#show_img").unbind('mouseout');

                });
                $("#addFamilyModal").modal('show');
            }
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
}