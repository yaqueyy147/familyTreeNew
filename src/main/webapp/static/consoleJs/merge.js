/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#rejectDialog").dialog({
        width: 500,
        height: 300,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/rejectInclude";
                    formData.mergeId = $("#mergeId").val();
                    formData.rejectDesc = $("#rejectDesc").val();
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
                                $("#rejectForm").form('clear');
                                closeDialog("rejectDialog");
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
                    $("#rejectForm").form('clear');
                    closeDialog("userDialog");
                }
            }
        ]
    });
    var params = {};
    loadDataGrid(params);
});

function closeDialog(dialogId){
    $("#rejectForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/mergePrimary",params).primaryList;
    // dataList = rankData.listPersonalPoints;
    $("#mergeList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"族谱Id",width:"80",hidden:true},
            {field:"mergeId",title:"申请收录Id",width:"80",hidden:true},
            {field:"family_name",title:"族谱名称",width:"150"},
            {field:"apply_man",title:"申请人",width:"100"},
            {field:"mergeState",title:"状态",width:"80",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "已收录";
                    } else if(value == 3){
                        return "已驳回";
                    } else{
                        return "待审核";
                    }
                }},
            {field:"operation",title:"操作",width:"150",align:"center",
                formatter: function(value,row,index){
                    if(row.mergeState == 1){
                        return "已收录";
                    } else if(row.mergeState == 3){
                        return "已驳回";
                    }else{
                        var operationHtml = "<a href='/consoles/familyMerge?familyId=" + row.id + "'>收录</a>&nbsp;&nbsp;";
                        operationHtml += "<a href='javascript:void 0' onclick=\"reject('" + row.mergeId + "')\">驳回</a>&nbsp;&nbsp;";
                        return operationHtml;
                    }

                }}
        ]],
        loadFilter:pagerFilter
    });
}

function reject(mergeId) {
    $("#mergeId").val(mergeId);
    $("#rejectDialog").dialog('open');
}

function formatDataList(data){
    if(data){
        for(var i=0;i<data.length;i++){
            // data[i].meritocrat_desc =
        }
    }
    return data;
}
