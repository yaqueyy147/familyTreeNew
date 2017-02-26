/**
 * Created by suyx on 2017/1/16.
 */
$(function () {

    $("#userDialog").dialog({
        width: 600,
        height: 400,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveUserBase";
                    var testData = $("#userInfoForm").serializeArray();

                    for (var item in testData) {
                        formData["" + testData[item].name + ""] = testData[item].value;
                    }
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        async:false,
                        dataType:'json',
                        data:formData,
                        async:false,
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){
                                var params = {};
                                loadDataGrid(params);
                                $("#userInfoForm").form('clear');
                                closeDialog("userDialog");
                            }
                        },
                        error:function (data) {
                            alert(JSON.stringify(data));
                        }
                    });
                }
            },
            {
                "text":"取消",
                handler:function () {
                    $("#userInfoForm").form('clear');
                    closeDialog("userDialog");
                }
            }
        ]
    });

    $("#modifyPasswordDialog").dialog({
        width: 400,
        height: 200,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var oldPassword = $("#oldPassword").val();
                    var newPassword = $("#newPassword").val();
                    var newPasswordAffirm = $("#newPasswordAffirm").val();
                    if(newPassword != newPasswordAffirm){
                        alert("密码输入不一致!");
                        return;
                    }

                    var postUrl = projectUrl + "/consoles/modifyPassword";
                    var params = {};
                    params.userId = $("#userIdForModify").val();
                    params.newPassword = newPassword;
                    params.oldPassword = oldPassword;
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        async:false,
                        dataType:'json',
                        data:params,
                        async:false,
                        success:function (data) {
                            if(data.code >= 1){
                                alert(data.msg);
                                var params = {};
                                loadDataGrid(params);
                                $("#modifyPasswordForm").form('clear');
                                closeDialog("modifyPasswordDialog");
                            } else if(data.code == -2){
                                alert(data.msg);
                            }
                        },
                        error:function (data) {
                            alert(JSON.stringify(data));
                        }
                    });
                }
            },
            {
                "text":"取消",
                handler:function () {
                    $("#modifyPasswordForm").form('clear');
                    closeDialog("modifyPasswordDialog");
                }
            }
        ]
    });

    $("#toEditUser").click(function () {
        $("#userDialog").dialog('open');
    });

    $("#toModifyPassword").click(function () {
        $("#modifyPasswordDialog").dialog('open');
    });

});
function loadTab(tabId,tabTitle,tabUrl) {
    if ($('#tabTT').tabs('exists', tabTitle)){
        $('#tabTT').tabs('select', tabTitle);
    }else{
        $("#tabTT").tabs('add',{
            title:tabTitle,
            content:"<iframe src=\"" + projectUrl + tabUrl + "\" width='100%' height='100%'></iframe>",
            closable:true
        });
    }

}