/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#roleDialog").dialog({
        width: 400,
        height: 300,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveRole";
                    var testData = $("#roleForm").serializeArray();

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
                                var params = {};
                                loadDataGrid(params);
                                $("#roleForm").form('clear');
                                closeDialog("roleDialog");
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
                    $("#roleForm").form('clear');
                    closeDialog("roleDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#roleForm").form('clear');
        $("#roleId").val(0);
        $("#roleDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#roleForm").form('clear');
        var selectRows = $("#roleList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#roleDialog").dialog('open');
    });

    $("#toDel").click(function () {

        var selectRows = $("#roleList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.roleName);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除角色(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteRole",
                    // async:false,
                    dataType:'json',
                    data:{ids:selectIds},
                    success:function (data) {
                        alert(data.msg);
                        var params = {};
                        loadDataGrid(params);
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
        });
    });

    var params = {};
    loadDataGrid(params);

});

function closeDialog(dialogId){
    $("#roleForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/roleList",params).dataList;
    dataList = formatDataList(dataList);
    $("#roleList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"角色Id",width:"80",hidden:true},
            {field:"roleName",title:"角色名称",width:"150"},
            {field:"roleDesc",title:"角色说明",width:"80"},
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
            if(data[i].state == 1){
                data[i].stateDesc = "可用";
            }else{
                data[i].stateDesc = "不可用";
            }
        }
    }
    return data;
}

function loadDataToForm(data){

    $("#roleId").val(data.id);
    $("#roleName").val(data.roleName);
    $("#roleDesc").val(data.roleDesc);
    $("#state").combobox("setValue",data.state);
}