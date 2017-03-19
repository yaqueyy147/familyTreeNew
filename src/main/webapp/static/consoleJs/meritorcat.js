/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#meritocratArea").combobox("loadData", ChineseProvince);

    $("#meritocratDialog").dialog({
        width: 600,
        height: 400,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    $('#meritocratArea').combobox('setValue',$('#meritocratArea').combobox('getText') )
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveMeritorcat";
                    var testData = $("#meritocratForm").serializeArray();

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
                                $("#meritocratForm").form('clear');
                                closeDialog("meritocratDialog");
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
                    $("#meritocratForm").form('clear');
                    closeDialog("meritocratDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#meritocratForm").form('clear');
        $("#meritocratId").val(0);
        $("#meritocratDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#meritocratForm").form('clear');
        var selectRows = $("#meritorcatList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#meritocratDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#meritorcatList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.meritocrat_name);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('Confirm','确定要删除用户(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: "/consoles/deleteMeritorcat",
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
    var dataList = getData("/consoles/meritorcatList",params).meritorcatList;
    // dataList = rankData.listPersonalPoints;
    $("#meritorcatList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"英才Id",width:"80",hidden:true},
            {field:"phone",title:"联系电话",width:"80",hidden:true},
            {field:"fax",title:"传真",width:"80",hidden:true},
            {field:"post_code",title:"邮编",width:"80",hidden:true},
            {field:"meritocrat_name",title:"英才姓名",width:"80"},
            {field:"meritocrat_area",title:"英才属地",width:"80"},
            {field:"meritocrat_attr",title:"英才属性",width:"80"},
            {field:"meritocrat_attr_id",title:"英才属性Id",width:"80",hidden:true},
            {field:"meritocrat_addr",title:"详细地址",width:"200",
                formatter: function(value,row,index){
                    return '<span title='+value+'>'+value+'</span>'
                }},
            {field:"meritocrat_desc",title:"英才简介",width:"300",
                formatter: function(value,row,index){
                    return '<span title='+value+'>'+value+'</span>'
                }}
        ]],
        loadFilter:pagerFilter
    });
}

function formatDataList(data){
    if(data){
        for(var i=0;i<data.length;i++){
            // data[i].meritocrat_desc =
        }
    }
    return data;
}

function loadDataToForm(data){
    $("#meritocratId").val(data.id);
    $("#meritocratName").val(data.meritocrat_name);
    $("#meritocratArea").combobox("setValue",data.meritocrat_area);
    $("#meritocratDesc").val(data.meritocrat_desc);
    $("#meritocratAddr").val(data.meritocrat_addr);
    $("#phone").val(data.phone);
    $("#fax").val(data.fax);
    $("#postCode").val(data.post_code);
    $("#meritocratAttrId").combobox("setValue",data.meritocrat_attr_id);

}