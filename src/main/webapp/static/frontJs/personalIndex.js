/**
 * Created by suyx on 2016/12/22 0022.
 */
$(function () {
    $("#imgFile").fileinput({
        uploadUrl: '#', // you must set a valid URL here else you will get an error
        allowedFileExtensions : ['jpg', 'png','gif'],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 1,
        showUpload: false,
        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    });

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
        // enctype="multipart/form-data"
        // $("#familyForm").attr("action","/family/savePeople");
        var fileUrl = $("#imgFile").val();
        var formData;
        var postUrl = projectUrl + "/family/saveFamilyNoImg";
        if($.trim(fileUrl).length > 0){
            $("#familyForm").attr("enctype","multipart/form-data");
            formData = new FormData($("#familyForm")[0]);
            postUrl = projectUrl + "/family/saveFamilyWithImg";
        } else {
            var testData = $("#familyForm").serializeArray();
            formData = {};
            for (var item in testData) {
                formData["" + testData[item].name + ""] = testData[item].value;
            }
            postUrl = projectUrl + "/family/saveFamilyNoImg";
        }
        $.ajax({
            type:'post',
            url:postUrl,
            dataType:'json',
            data:formData,
            processData: false,
            contentType: false,
            success:function (data) {
                if(data.code == 1){
                    var tFamily = data.tFamily;
                    var familyImg = tFamily.photoUrl;
                    var img = "<a href=\"" + projectUrl + "/family/viewFamily?familyId="+tFamily.id+"\"><img src=\"" + familyImg + "\" width='100px' height='100px' /></a>";
                    $("#familyShow").append(img);
                    $("#addFamilyModal").modal('hide');
                }
                alert(data.msg);
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
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