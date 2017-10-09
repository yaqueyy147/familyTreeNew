/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#province4Search").val("");
    $("#province4Search").change();

    $("input[name='visitStatus']").click(function () {
        var status = $(this).val();
        if(status == 0){
            $("#visitPwdTitle").show();
            $("#visitPwd").show();
        }else{
            $("#visitPwdTitle").hide();
            $("#visitPwd").hide();
            $("#visitPassword").val("");
        }
    });

    $("#familyDialog").dialog({
        width: 800,
        height: 550,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    if($.trim($("#familyName")).length <= 0){
                        alert("请输入家族名称！");
                        return;
                    }
                    if($.trim($("#province")).length <= 0){
                        alert("请选择家族所在省！");
                        return;
                    }
                    if($.trim($("#city")).length <= 0){
                        alert("请选择家族所在市！");
                        return;
                    }
                    if($.trim($("#district")).length <= 0){
                        alert("请选择家族所在区县！");
                        return;
                    }
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveFamily";
                    var testData = $("#familyForm").serializeArray();
                    for (var item in testData) {
                        formData["" + testData[item].name + ""] = testData[item].value;
                    }
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        dataType:'json',
                        data:formData,
                        // async:false,
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){
                                var params = {};
                                loadFamilyList(params);
                                $("#familyForm").form('clear');
                                closeDialog("familyDialog");
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
                    $("#userInfoForm").form('clear');
                    closeDialog("familyDialog");
                }
            }
        ]
    });

    $("#doSearch").click(function () {
        var params = {};
        params.familyName = $("#familyName4Search").val();
        params.province = $("#province4Search").val();
        params.city = $("#city4Search").val();
        params.district = $("#district4Search").val();
        loadFamilyList(params);
    });

    $("#toAdd").click(function () {
        $("#familyForm")[0].reset();
        $("#familyId").val(0);
        $("#createMan").val("");
        $("#familyArea").val(0);

        $("#result_img").hide();
        $("#imgFile").show();
        $("#photoUrl").removeAttr('value');
        $("#show_img").unbind('mouseover');
        $("#show_img").unbind('mouseout');
        $("#province").val("");
        $("#province").change();

        $("#familyDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#familyForm").form('clear');
        var selectRows = $("#familyList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#familyDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#familyList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        var peopleCount = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.familyName);
            if(ii.peopleCount > 0){
                peopleCount.push(ii.familyName + "(" + ii.id + ")");
            }

        }
        if(peopleCount.length > 0){
            alert("家族(" + peopleCount + ")中含有成员，不能删除！如需删除，请先删除其成员！");
            return;
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除族谱(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteFamily",
                    // async:false,
                    dataType:'json',
                    data:{ids:selectIds},
                    success:function (data) {
                        alert(data.msg);
                        var params = {};
                        loadFamilyList(params);
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
    loadFamilyList(params);

});

function loadFamilyList(params){
    var dataList = getData("/consoles/familyList",params).dataList;
    dataList = formatDataList(dataList);
    // $("#familyList").datagrid({loadFilter:familyFilter}).datagrid('loadData', getData("/consoles/familyList",params).dataList);
    $("#familyList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"族谱Id",width:"80",hidden:true},
            {field:"familyName",title:"族谱名称",width:"200",
                formatter: function(value,row,index){
                    return "<a href=\"" + projectUrl + "/consoles/familyTree?familyId=" + row.id + "\" title='" + value + "'>" + value +" </a>";
                }},
            {field:"familyFirstName",title:"族谱姓氏",width:"150"},
            {field:"peopleCount",title:"族谱人数",width:"80"},
            {field:"createMan",title:"创建人",width:"80"},
            {field:"createTime",title:"创建时间",width:"180"},
            {field:"familyAddr",title:"族谱所在地",width:"300",
                formatter: function(value,row,index){
                    return '<span title='+ row.province + row.city + row.district + '>'+row.province + row.city + row.district+'</span>'
                }},
            {field:"familyArea",title:"族谱属地",width:"80",hidden:true},
            {field:"province",title:"族谱所在省",width:"80",hidden:true},
            {field:"city",title:"族谱所在市",width:"80",hidden:true},
            {field:"district",title:"族谱所在区",width:"80",hidden:true},
            {field:"visitStatus",title:"访问状态",width:"80",hidden:true}
        ]],
        loadFilter:pagerFilter
    });
}

function closeDialog(dialogId){
    $("#familyForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function formatDataList(data){
    if(data){

        for(var i=0;i<data.length;i++){
            // data[i].familyName = "<a href=\"" + projectUrl + "/consoles/familyTree?familyId=" + data[i].id + "\">" + data[i].familyName +" </a>";// onclick=\"familyDetail('" + data[i].id + "')\"
            data[i].createTime = new Date(data[i].createTime).Format("yyyy-MM-dd hh:mm:ss");
        }
    }
    return data;
}

function loadDataToForm(data) {

    $("#familyId").val(data.id);
    $("#createMan").val(data.createMan);
    var createTime = data.createTime;
    if($.trim(createTime).length <= 0){
        createTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
    }
    $("#createTime4Modify").val(createTime);
    $("#familyFirstName").val(data.familyFirstName);
    $("#familyName").val(data.familyName);
    $("#visitPassword").val(data.visitPassword);
    $("#province").val(data.province);
    $("#province").change();
    $("#city").val(data.city);
    $("#city").change();
    $("#district").val(data.district);
    $("#district").change();
    $("#familyDesc").val(data.familyDesc);
    $("#familyState").val(data.state);
    $("#familyArea").val(0);

    var visitStatus = data.visitStatus;
    $("input:radio[name='visitStatus'][value = " + visitStatus + "]").prop("checked","checked");
    $("input:radio[name='visitStatus'][value = " + visitStatus + "]").click();

    var imgPath = data.photoUrl;
    $("#result_img").attr('src',imgPath);
    $("#result_img").show();
    $("#imgFile").hide();
    $("#photoUrl").attr('value',imgPath);
    $("#show_img").mouseover(function(){
        $("#result_img").attr('src',"/ImgFile/images/deleteImg.png");
    });
    $("#show_img").mouseout(function(){
        $("#result_img").attr('src',imgPath);
    });

    $("#result_img").click(function(){
        $("#result_img").hide();
        $("#imgFile").show();
        $("#photoUrl").removeAttr('value');
        $("#show_img").unbind('mouseover');
        $("#show_img").unbind('mouseout');

    });
}