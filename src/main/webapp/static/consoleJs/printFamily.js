/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#province4Search").val("");
    $("#province4Search").change();

    $("#printDialog").dialog({
        "closed":true,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    $("#printGenForm").attr("action",projectUrl + "/consoles/print");
                    $("#printGenForm").submit();
                }
            },
            {
                "text":"取消",
                handler:function () {
                    $("#printDialog").dialog("close");
                }
            }
        ]
    });

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

    $("#doSearch").click(function () {
        var params = {};
        params.familyName = $("#familyName4Search").val();
        params.province = $("#province4Search").val();
        params.city = $("#city4Search").val();
        params.district = $("#district4Search").val();
        loadFamilyList(params);
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
        singleSelect:true,
        nowrap: true,
        columns:[[
            {field:"id",title:"族谱Id",width:"80",hidden:true},
            // {field:"familyName",title:"族谱名称",width:"200",
            //     formatter: function(value,row,index){
            //         return "<a href=\"" + projectUrl + "/consoles/familyTree?familyId=" + row.id + "\" title='" + value + "'>" + value +" </a>";
            //     }},
            {field:"familyName",title:"族谱名称",width:"200"},
            {field:"familyFirstName",title:"族谱姓氏",width:"150"},
            {field:"peopleCount",title:"族谱人数",width:"80"},
            {field:"createMan",title:"创建人",width:"80"},
            {field:"createTime",title:"创建时间",width:"180"},
            {field:"familyAddr",title:"族谱所在地",width:"300",
                formatter: function(value,row,index){
                    return '<span title='+ row.province + row.city + row.district + '>'+row.province + row.city + row.district+'</span>'
                }},
            {field:"operate",title:"操作",width:"80",
                formatter: function(value,row,index){
                    return "<a href=\"javascript:void 0;\" onclick=\"toPrint(this,'" + row.id + "')\">打印</a>";
                }}
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

function toPrint(obj,familyId){

    $.ajax({
        type:'post',
        url: projectUrl + '/consoles/getMaxGen',
        dataType: 'json',
        data:{familyId:familyId},
        async:true,
        success:function (data) {
            if(data.maxGen >= 1){
                $("#printDialog").dialog("open");
                $("#printFamilyId").val(familyId);
                $("#endGen").val(data.maxGen);
                $("#maxGen").val(data.maxGen);
            }else{
                alert("该族谱没有可打印的内容!");
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