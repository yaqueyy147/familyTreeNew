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
            url: projectUrl + '/sign/modifyCompanyInfo',
            dataType: 'json',
            data:testData,
            async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                }

            },
            error:function (data) {
                alert(JSON.stringify(data));
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
                async:false,
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
                    alert(JSON.stringify(data));
                }
            });
        }
    });

    $("#toModify-photo").click(function () {
        var photoPath = $("#photoUrl").val();
        $.ajax({
            type:'post',
            url: projectUrl + '/sign/modifyPhoto',
            dataType: 'json',
            data:{photoPath:photoPath},
            async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                    $("#userPhotoBox img").attr("src",photoPath);
                    $("#companyphoto").val(photoPath);
                    $("#photoModal").modal('hide');
                }
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });
    $("#toModify-license").click(function () {
        var photoPath = $("#photoUrl1").val();
        $.ajax({
            type:'post',
            url: projectUrl + '/sign/modifyPhoto',
            dataType: 'json',
            data:{photoPath:photoPath},
            async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                    $("#userPhotoBox img").attr("src",photoPath);
                    $("#companyphoto").val(photoPath);
                    $("#photoModal").modal('hide');
                }
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });

});
