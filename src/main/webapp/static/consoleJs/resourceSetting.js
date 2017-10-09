/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#resourceDialog").dialog({
        width: 450,
        height: 300,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveResource";
                    var testData = $("#resourceForm").serializeArray();

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
                                $("#resourceForm").form('clear');

                                // var params = {userId:parent.userId};
                                // parent.loadMenuTree(params);

                                closeDialog("resourceDialog");

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
                    $("#resourceForm").form('clear');
                    closeDialog("resourceDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#resourceForm").form('clear');
        // var selectRows = $("#roleList").datagrid('getSelections');
        $("#resourceId").val(0);
        $("#parentSourceId").val(0);
        $("#sourceLevel").val(0);
        $("#sourceType").val(0);
        $("#parentSourceId").combobox("loadData", getResourceList(params));
        $("#resourceDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#resourceForm").form('clear');
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
        $("#parentSourceId").combobox("loadData", getResourceList(params));
        $("#resourceDialog").dialog('open');
    });

    $("#toDel").click(function () {

        var selectRows = $("#resourceList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.sourceName);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除资源(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteResource",
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
    $("#resourceForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getResourceList(params);
    // dataList = formatDataList(dataList);
    $("#resourceList").treegrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        idField:"id",
        treeField: 'sourceName',
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"资源Id",width:"80",hidden:true},
            {field:"sourceName",title:"资源名称",width:"200"},
            {field:"sourceUrl",title:"资源链接",width:"200"},
            {field:"resourceState",title:"状态",width:"80",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "可用";
                    }
                    return '不可用';
                }}
        ]]
        // ,
        // loadFilter:pagerFilter4TreeGrid
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

    $("#resourceId").val(data.id);
    $("#sourceName").val(data.sourceName);
    $("#sourceDesc").val(data.sourceDesc);
    $("#sourceUrl").val(data.sourceUrl);
    $("#parentSourceId").val(data._parentId);
    $("#state").combobox("setValue",data.state);
}

function getResourceList(params){
    var dataList = getData("/consoles/resourceList",params).resourceList;
    return dataList;
}