/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#meritocratArea").combobox("loadData", ChineseProvince);

    $("#meritocratDialog").dialog({
        width: 650,
        height: 550,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    if($.trim($("#meritocratName").val()).length <= 0){
                        alert("请输入英才姓名");
                        return;
                    }
                    if($.trim($("#meritocratArea").val()).length <= 0){
                        alert("请选择英才属地");
                        return;
                    }
                    if($.trim($("#meritocratAttrId").val()).length <= 0){
                        alert("请选择英才类型");
                        return;
                    }

                    $('#meritocratArea').combobox('setValue',$('#meritocratArea').combobox('getText') )
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveMeritorcat";
                    var testData = $("#meritocratForm").serializeArray();

                    for (var item in testData) {
                        formData["" + testData[item].name + ""] = testData[item].value;
                    }
                    $(".loading").show();
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
                                $("#meritocratForm").form('clear');
                                closeDialog("meritocratDialog");
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
                    $("#meritocratForm").form('clear');
                    closeDialog("meritocratDialog");
                }
            }
        ]
    });

    $("#doSearch").click(function () {
        $(".loading").show();
        var params = {};
        params.meritocrat_name = $("#meritocratName4Search").val();
        params.meritocrat_area = $("#province").val();
        params.meritocrat_attr_id = $("#meritocratAttrId4Search").val();
        loadDataGrid(params);
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
        $.messager.confirm('提示','确定要删除英才(' + selectNames + ')  吗?',function(r){
            if (r){
                $(".loading").show();
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteMeritorcat",
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
    $("#meritocratForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    // var dataList = getData("/consoles/meritorcatList",params);
    // dataList = rankData.listPersonalPoints;
    var defer = $.Deferred();
    $.ajax({
        type:'post',
        url: projectUrl + "/consoles/meritorcatList",
        dataType:'json',
        data:params,
        success:function (data) {
            $("#meritorcatList").datagrid({
                data:data,
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
                    {field:"photo",title:"头像",width:"80",hidden:true},
                    {field:"meritocrat_name",title:"英才姓名",width:"120"},
                    {field:"meritocrat_area",title:"英才属地",width:"80"},
                    {field:"meritocrat_attr",title:"英才属性",width:"120"},
                    {field:"meritocrat_attr_id",title:"英才属性Id",width:"80",hidden:true},
                    {field:"meritocrat_addr",title:"详细地址",width:"200",
                        formatter: function(value,row,index){
                            return '<span title='+value+'>'+value+'</span>'
                        }},
                    {field:"meritocrat_desc",title:"英才简介",width:"400",
                        formatter: function(value,row,index){
                            return '<span title='+value+'>'+value+'</span>'
                        }}
                ]],
                loadFilter:pagerFilter
            });
            defer.resolve(data);
            $(".loading").hide();
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
    return defer.promise();
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

    var imgPath = data.photo;
    $("#result_img").attr('src',imgPath);
    $("#result_img").show();
    $("#imgFile").hide();
    $("#photo").attr('value',imgPath);
    $("#show_img").mouseover(function(){
        $("#result_img").attr('src',projectUrl + "/static/images/deleteImg.png");
    });
    $("#show_img").mouseout(function(){
        $("#result_img").attr('src',imgPath);
    });

    $("#result_img").click(function(){
        $("#result_img").hide();
        $("#imgFile").show();
        $("#photo").removeAttr('value');
        $("#show_img").unbind('mouseover');
        $("#show_img").unbind('mouseout');

    });
}