/**
 * Created by suyx on 2017/1/16.
 */
var setting;
$(function () {

    $("#userDialog").dialog({
        width: 800,
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
                        // async:false,
                        dataType:'json',
                        data:formData,
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){
                                $("#userDialog").dialog('close');
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
                    $("#userDialog").dialog('close');
                }
            }
        ]
    });

    $("#modifyPasswordDialog").dialog({
        width: 400,
        height: 250,
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
                        // async:false,
                        dataType:'json',
                        data:params,
                        success:function (data) {
                            if(data.code >= 1){
                                alert("修改成功，以后登录请使用新密码！");
                                $("#modifyPasswordForm").form('clear');
                                $("#modifyPasswordDialog").dialog('close');
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
                    $("#modifyPasswordDialog").dialog('close');
                }
            }
        ]
    });

    $("#toEditUser").click(function () {
        // alert(userId);
        $.ajax({
            type:'post',
            url: projectUrl + "/consoles/userList",
            // async:false,
            dataType:'json',
            data:{id:userId},
            success:function (data) {
                var userList = data.dataList;
                if(userList != null && userList.length > 0){
                    var userInfo = userList[0];
                    $("#userId").val(userInfo.id);
                    $("#state").val(userInfo.state);
                    $("#isFront").val(userInfo.isFront);
                    $("#isConsole").val(userInfo.isConsole);
                    $("#isVolunteer").val(userInfo.isVolunteer);
                    $("#password").val(userInfo.password);
                    $("#userFrom").val(userInfo.userFrom);
                    $("#idCard").val(userInfo.idCard);
                    $("#userPhoto").val(userInfo.userPhoto);
                    $("#idCardPhoto").val(userInfo.idCardPhoto);
                    $("#loginName").val(userInfo.loginName);
                    $("#userName").val(userInfo.userName);
                    $("#phone").val(userInfo.phone);
                    $("#qqNum").val(userInfo.qqNum);
                    $("#wechart").val(userInfo.wechart);
                    $("#province").val(userInfo.province);
                    $("#province").change();
                    $("#city").val(userInfo.city);
                    $("#city").change();
                    $("#district").val(userInfo.district);
                    $("#district").change();
                    $("#userDialog").dialog('open');
                }else{
                    alert("用户信息读取错误，请重新登录尝试！");
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