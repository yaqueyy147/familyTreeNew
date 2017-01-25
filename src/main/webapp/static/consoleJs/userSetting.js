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
                        success:function (data) {

                            alert(data.msg);
                            if(data.code == 1){

                                $("#userInfoForm")[0].reset();
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
                    $("#userInfoForm")[0].reset();
                    closeDialog("userDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#userDialog").dialog('open');
    });

    var params = {};
    loadDataGrid(params);

});

function closeDialog(dialogId){
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/userList",params).dataList;
    dataList = formatDataList(dataList);
    $("#userList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        columns:[[
            {field:"id",title:"用户Id",width:"80",hidden:true},
            {field:"userName",title:"用户账号",width:"150"},
            {field:"userNickName",title:"用户昵称",width:"80"},
            {field:"userDesc",title:"用户说明",width:"80"},
            {field:"userContact",title:"联系方式",width:"200"},
            {field:"createMan",title:"创建人",width:"80"},
            {field:"createTime",title:"创建时间",width:"150"},
            {field:"state",title:"状态",width:"180"},
            {field:"operate",title:"操作",width:"120"}
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
                data[i].state = "可用";
            }else{
                data[i].state = "不可用";
            }

            data[i].operate = "<a href=\"javascript:void 0;\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\" onclick=\"toEdit('" + data[i].id + "')\">编辑</a>";
            data[i].operate += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"toDel('" + data[i].id + "')\">删除</a>";
        }
    }
    return data;
}

function toEdit(userId){

    var row = $('#userList').datagrid('getSelected');

    if(row){
        alert(JSON.stringify(row));
    }

    $("#userDialog").dialog('open');
}