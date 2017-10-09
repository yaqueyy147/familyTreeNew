/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#pointsDialog").dialog({
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
                    var postUrl = projectUrl + "/consoles/savePointsRelation";
                    var testData = $("#pointsForm").serializeArray();

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
                            if(data.code >= 1){
                                alert("保存成功！");
                                var params = {};
                                loadDataGrid(params);
                                $("#pointsForm").form('clear');
                                closeDialog("pointsDialog");
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
                    $("#pointsForm").form('clear');
                    closeDialog("pointsDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#pointsForm").form('clear');
        $("#pointsRelationId").val(0);
        $("#pointsDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#pointsForm").form('clear');
        var selectRows = $("#pointsRelationList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#pointsDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#pointsRelationList").datagrid('getSelections');
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
        $.messager.confirm('提示','确定要删除选择数据吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deletePointsRelation",
                    // async:false,
                    dataType:'json',
                    data:{ids:selectIds},
                    success:function (data) {
                        alert("删除成功!");
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
    $("#pointsForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/pointsRelationData",params).pointsDicList;
    $("#pointsRelationList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"对应Id",width:"80",hidden:true},
            {field:"pointsType",title:"积分类型",width:"150",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "录入族人积分";
                    } else{
                        return "充值积分";
                    }
                }},
            {field:"pointsNum",title:"数量(录入族人数或者充值数)",width:"80"},
            {field:"pointsValue",title:"对应积分数",width:"80"},
            {field:"state",title:"状态",width:"180",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "可用";
                    } else{
                        return "不可用";
                    }
                }},
            {field:"remark",title:"说明",width:"200",
                formatter: function(value,row,index){
                    return '<span title='+value+'>'+value+'</span>'
                }}
            // {field:"operate",title:"操作",width:"120"}
        ]],
        loadFilter:pagerFilter
    });
}

function loadDataToForm(data){

    $("#pointsRelationId").val(data.id);
    $("#pointsNum").val(data.pointsNum);
    $("#pointsValue").val(data.pointsValue);
    $("#remark").val(data.remark);
    $("#pointsType").combobox("setValue",data.pointsType);
    $("#state").combobox("setValue",data.state);

}