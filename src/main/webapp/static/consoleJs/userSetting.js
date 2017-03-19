/**
 * Created by suyx on 2017/1/12.
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

    $("#toAdd").click(function () {
        $("#userInfoForm").form('clear');
        $("#userId").val(0);
        $("#passwordTr").removeAttr("style");
        $("#userDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#userInfoForm").form('clear');
        $("#passwordTr").attr("style","display:none");
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#userDialog").dialog('open');
    });

    $("#toModifyPassword").click(function () {
        $("#modifyPasswordForm").form('clear');
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        $("#userIdForModify").val(selectRows[0].id);
        $("#modifyPasswordDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.userName);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('Confirm','确定要删除用户(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: "/consoles/deleteUser",
                    async:false,
                    dataType:'json',
                    data:{ids:selectIds},
                    async:false,
                    success:function (data) {
                        alert(data.msg);
                        var params = {};
                        loadDataGrid(params);
                    },
                    error:function (data) {
                        alert(JSON.stringify(data));
                    }
                });
            }
        });
    });

    var params = {};
    loadDataGrid(params);

});

function closeDialog(dialogId){
    $("#userInfoForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/userList",params).dataList;
    dataList = formatDataList(dataList);
    $("#userList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"用户Id",width:"80",hidden:true},
            {field:"userName",title:"用户账号",width:"150"},
            {field:"userNickName",title:"用户昵称",width:"80"},
            {field:"userPassword",title:"密码",width:"80",hidden:true},
            {field:"userDesc",title:"用户说明",width:"80"},
            {field:"userContact",title:"联系方式",width:"200"},
            {field:"createMan",title:"创建人",width:"80"},
            {field:"createTime",title:"创建时间",width:"150"},
            {field:"stateDesc",title:"状态",width:"180"},
            {field:"state",title:"状态",width:"180",hidden:true}
            // {field:"operate",title:"操作",width:"120"}
        ]],
        loadFilter:pagerFilter
    });
}

function formatDataList(data){
    if(data){

        for(var i=0;i<data.length;i++){
            data[i].userCode = "<a href=\"javascript:void 0;\" onclick=\"editUser('"+ data[i].id +"')\">" + data[i].userCode +" </a>";
            data[i].createTime = new Date(data[i].createTime).Format("yyyy-MM-dd hh:mm:ss");
            if($.trim(data[i].userPhone).length <= 0){
                data[i].userPhone = "未设置电话";
            }
            if($.trim(data[i].userEmail).length <= 0){
                data[i].userEmail = "未设置邮箱";
            }
            if($.trim(data[i].userQq).length <= 0){
                data[i].userQq = "未设置qq";
            }
            if($.trim(data[i].userWechart).length <= 0){
                data[i].userWechart = "未设置微信";
            }
            data[i].userContact = data[i].userPhone + "," + data[i].userEmail + "," + data[i].userQq + "," + data[i].userWechart;

            if(data[i].state == 1){
                data[i].stateDesc = "可用";
            }else{
                data[i].stateDesc = "不可用";
            }

            // data[i].operate = "<a href=\"javascript:void 0;\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\" onclick=\"toEdit('" + data[i].id + "')\">编辑</a>";
            // data[i].operate += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"toDel('" + data[i].id + "')\">删除</a>";
        }
    }
    return data;
}

function loadDataToForm(data){

    var userContact = data.userContact;
    var contacts = userContact.split(",");

    var userPassword = data.userPassword;
    // userPassword
    $("#userId").val(data.id);
    $("#userName").val(data.userName);
    $("#userNickName").val(data.userNickName);
    $("#userPassword").val(data.userPassword);
    $("#userPasswordAffirm").val(data.userPassword);
    if(contacts[0] == "未设置电话"){
        $("#userPhone").val("");
    }else{
        $("#userPhone").val(contacts[0]);
    }
    if(contacts[1] == "未设置邮箱"){
        $("#userEmail").val("");
    }else{
        $("#userEmail").val(contacts[1]);
    }
    if(contacts[2] == "未设置qq"){
        $("#userQq").val("");
    }else{
        $("#userQq").val(contacts[2]);
    }
    if(contacts[3] == "未设置微信"){
        $("#userWechart").val("");
    }else{
        $("#userWechart").val(contacts[3]);
    }

    $("#userDesc").val(data.Desc);
    $("#state").combobox("setValue",data.state);

}