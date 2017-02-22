/**
 * Created by suyx on 2016/12/18.
 */
$(function () {

    $("#regedit").click(function () {

        var formData = $("#pwesonalForm").serializeArray();
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


});
