/**
 * Created by suyx on 2016/12/18.
 */
$(function () {

    $("#regedit").click(function () {

        var formData = $("#personalForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        $.ajax({
            type:'post',
            url: projectUrl + '/sign/modifyPersonalInfo',
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
                data:{userId:userId,newPassword:newP,oldPassword:oldP},
                async:false,
                success:function (data) {
                    if(data.code >= 1){
                        alert(data.msg);
                    }
                    if(data.code == 1){
                        $("#oldPassword").val("");
                        $("#newPassword").val("")
                        $("#newPasswordAffirm").val("");
                        $("#password").val(newP);
                        $("#modifyModal").modal('hide');
                    }

                },
                error:function (data) {
                    alert(JSON.stringify(data));
                }
            });
        }
    });

    $("#applyVolunteer").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + '/sign/applyVolunteer',
            dataType: 'json',
            data:{},
            async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                    $("#applyVolunteer").replaceWith("<span>已申请修订族谱，请等待审核！</span>");
                }
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
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
                    $("#userPhotoBox img").attr("src",projectUrl + photoPath);
                    $("#userphoto").val(photoPath);
                    $("#photoModal").modal('hide');
                }
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });

});
