/**
 * Created by suyx on 2016/12/18.
 */
$(function () {

    $("#companyRegeditbb").click(function () {

        var formData = $("#companyForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        $.ajax({
            type:'post',
            url: projectUrl + '/company/modifyCompanyInfo',
            dataType: 'json',
            data:testData,
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
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
    });

    $("#toModify-pwd").on({
        click:function () {
            var oldP = $("#oldPassword").val();
            var newP = $("#newPassword").val()
            var newPA = $("#newPasswordAffirm").val();
            if(newP != newPA){
                alert("两次新密码输入不一致！");
                return;
            }

            $.ajax({
                type:'post',
                url: projectUrl + '/sign/modifyPassword',
                dataType: 'json',
                data:{userId:userInfo.id,newPassword:newP,oldPassword:oldP},
                // async:false,
                success:function (data) {
                    if(data.code >= 1){
                        alert(data.msg);
                    }
                    if(data.code == 1){
                        $("#oldPassword").val("");
                        $("#newPassword").val("")
                        $("#newPasswordAffirm").val("");
                        $("#companyLoginPassword").val(newP);
                        $("#modifyModal").modal('hide');
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
    });

    $("#toModify-photo").click(function () {
        var photoPath = $("#photoUrl").val();
        $.ajax({
            type:'post',
            url: projectUrl + '/family/modifyPhoto',
            dataType: 'json',
            data:{photoPath:photoPath},
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                    $("#userPhotoBox img").attr("src",photoPath);
                    $("#companyphoto").val(photoPath);
                    $("#photoModal").modal('hide');
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
    });
    
    $("#companyMoney").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + "/company/moneyList",
            dataType:'json',
            data:{"companyId" : companyId, "type" : 2},
            success:function (data) {
            	var moneyHtml = "<tr><th>序号</th><th>充值金额</th><th>充值说明</th><th>充值时间</th><th>充值人</th></tr><tr>";
                if(data){
                    var moneyList = data.dataList;
                    
                    for(var i=0;i<moneyList.length;i++){
                        var ii = moneyList[i];
                        moneyHtml  += "<td>" + (i+1) + "</td>";
                        moneyHtml  += "<td>" + ii.payMoney + "</td>";
                        moneyHtml  += "<td>" + ii.payDesc + "</td>";
                        moneyHtml  += "<td>" + ii.payTime + "</td>";
                        moneyHtml  += "<td>" + ii.payMan + "</td>";
                    }
                    moneyHtml += "</tr>";
                    
                }
                $("#moneyTable").html(moneyHtml);
                $("#moneyModal").modal('show');
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


    $("#applyVolunteer").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + '/company/applySponsor',
            dataType: 'json',
            data:{},
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                    $("#applyVolunteer").replaceWith("<span>已申请，请等待审核！</span>");
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
    });

    $('#myCompanyTabLi a').on('shown.bs.tab', function (e) {
        var myCompanyUrl = projectUrl + "/company/detail?companyId=" + companyId + "&xxx=2";
        $("#myFamilyTab iframe").attr("src",myCompanyUrl);
    });
});
