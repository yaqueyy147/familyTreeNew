/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#meritocratAttrDialog").dialog({
        width: 550,
        height: 250,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveMeritorcatAttr";
                    var testData = $("#meritocratAttrForm").serializeArray();

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
                                $("#meritocratAttrForm").form('clear');
                                closeDialog("meritocratAttrDialog");
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
                    $("#meritocratAttrForm").form('clear');
                    closeDialog("meritocratAttrDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#meritocratAttrForm").form('clear');
        $("#meritocratAttrId").val(0);
        $("#meritocratAttrDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#meritocratAttrForm").form('clear');
        var selectRows = $("#meritorcatAttrList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#meritocratAttrDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#meritorcatAttrList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.meritocratAttr);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除属性(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteMeritorcatAttr",
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
    $("#meritocratAttrForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/meritorcatAttrList",params).meritorcatAttrList;
    // dataList = rankData.listPersonalPoints;
    $("#meritorcatAttrList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"英才属性Id",width:"80",hidden:true},
            {field:"meritocratAttr",title:"属性名称",width:"80"},
            {field:"state",title:"英才属性",width:"80",
                formatter: function(value,row,index){
                    if(value == 0){
                        return '不可用';
                    }
                    return '可用';
                }}
        ]],
        loadFilter:pagerFilter
    });
}


function loadDataToForm(data){
    $("#meritocratAttrId").val(data.id);
    $("#meritocratAttr").val(data.meritocratAttr);
    $("#state").combobox("setValue",data.state);
}