/**
 * Created by suyx on 2016/12/22 0022.
 */
$(function () {
    // $("#imgFile").fileinput({
    //     uploadUrl: '#', // you must set a valid URL here else you will get an error
    //     allowedFileExtensions : ['jpg', 'png','gif'],
    //     overwriteInitial: false,
    //     maxFileSize: 1000,
    //     maxFilesNum: 1,
    //     //allowedFileTypes: ['image', 'video', 'flash'],
    //     slugCallback: function(filename) {
    //         return filename.replace('(', '_').replace(']', '_');
    //     }
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
        $("#familyForm").attr("action","/family/savePeople");
        var formData = new FormData($("#familyForm")[0]);
        $.ajax({
            type:'post',
            url:'/family/saveFamily',
            dataType:'json',
            data:formData,
            processData: false,
            contentType: false,
            success:function (data) {
                alert(data.msg);
            },
            error:function (data) {

            }
        });
    });

});