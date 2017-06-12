/**
 * Created by suyx on 2017/1/16.
 */
var setting;
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
                            var responseText = data.responseText;
                            if(responseText.indexOf("登出跳转页面") >= 0){
                                ajaxErrorToLogin();
                            }else{
                                alert(JSON.stringify(data));
                            }

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
                            var responseText = data.responseText;
                            if(responseText.indexOf("登出跳转页面") >= 0){
                                ajaxErrorToLogin();
                            }else{
                                alert(JSON.stringify(data));
                            }
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

    setting = {
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback:{
        onClick:zTreeOnClick
    }

    };
    var params = {userId:userId};
    loadMenuTree(params);

});

function loadMenuTree(params){
    var dataList = getData("/consoles/menuTree",params).menuList;
    $.fn.zTree.init($("#menuTree"), setting, dataList);
}

function loadTab(tabId,tabTitle,tabUrl) {
    if(tabUrl == "/"){
        return ;
    }
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
function zTreeOnClick(event, treeId, treeNode) {
    loadTab(treeNode.id,treeNode.source_name,treeNode.source_url);

}