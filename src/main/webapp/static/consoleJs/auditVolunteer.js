/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    $("#auditDialog").dialog({
        "closed":true,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var volunteerId = $("#volunteerId").val();
                    var auditState = $("#auditState").val();
                    var auditDesc = $("#auditDesc").val();
                    var applyManId = $("#applyManId").val();
                    $.ajax({
                        type:'post',
                        url: projectUrl + "/consoles/auditVolunteer",
                        dataType:'json',
                        data:{volunteerId:volunteerId,auditState:auditState,auditDesc:auditDesc,applyManId:applyManId},
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){
                                loadVolunteerData(params);
                                closeDialog("auditDialog");
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
                    closeDialog("auditDialog");
                }
            }
        ]
    });

    $("#idCardDialog").dialog({
        width: 500,
        height: 500,
        "closed":true,
        resizable:true,
        modal:true,
        "buttons":[
            {
                "text":"关闭",
                handler:function () {
                    $("#idCardDialog").html("");
                    $("#idCardDialog").dialog("close");
                }
            }
        ]
    });

    var params = {};
    loadVolunteerData(params);

});

function loadVolunteerData(params) {
    // var dataList = formatVolunteerData(getData("/consoles/volunteerList",params).dataList);
    var dataList = getData("/consoles/userFrontList",params).dataList;
    $("#volunteerList").datagrid({
        data:dataList,
        // columns:[[
        //     {field:"user_name",title:"申请人",width:"80"},
        //     {field:"phone",title:"联系电话",width:"80"},
        //     {field:"apply_desc",title:"申请说明",width:"120"},
        //     {field:"applyTime",title:"申请时间",width:"150"},
        //     {field:"is_volunteer",title:"是否志愿者",width:"80"},
        //     {field:"operate",title:"操作",width:"120"}
        // ]],
        columns:[[
            {field:"loginName",title:"申请人账号",width:"80"},
            {field:"userName",title:"姓名",width:"80"},
            {field:"idCard",title:"身份证号",width:"150"},
            {field:"phone",title:"联系电话",width:"120"},
            {field:"idCardPhoto",title:"身份证图片",width:"120",
                formatter: function(value,row,index){
                    var imgHtml = "<img src=\"" + value + "\" width=\"100px\" height=\"50px\" onclick=\"viewIdCard('" + value + "')\" />";
                    // imgHtml += "<span id=\"result_img1_wm\" style=\"position: absolute; top: 25px; left: 0;\">本图片仅用于注册何氏族谱网</span></div>";
                    return imgHtml;
                }},
            {field:"createTime",title:"注册时间",width:"150",
                formatter: function(value,row,index){
                    if($.trim(value).length <= 0){
                        return "";
                    }
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }},
            {field:"isVolunteer",title:"是否可修族谱",width:"80",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "是";
                    }
                    return '否';
                }},
            {field:"operate",title:"操作",width:"120",
                formatter: function(value,row,index){
                    var opHtml = "";
                    if(row.isVolunteer == 3 || row.isVolunteer == 0){
                        opHtml = "<a href=\"javascript:void 0;\" onclick=\"auditVolunteer('" + row.id + "',1)\">同意</a>";
                        opHtml += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"auditVolunteer('" + row.id + "',2)\">不同意</a>";
                        // return opHtml;
                    }else if(row.isVolunteer == 1 || row.isVolunteer == 2){
                        opHtml = "<span>已审核</span>";
                        opHtml += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"auditVolunteer('" + row.id + "',9)\">冻结账号</a>";
                    }else if(row.isVolunteer == 9){
                        opHtml = "<span>账号已冻结</span>";
                    }

                    return opHtml;
                }}
        ]],
        loadFilter:pagerFilter
    });
}

function auditVolunteer(userId,state){
    var params = {};
    $.ajax({
        type:'post',
        url: projectUrl + "/consoles/auditVolunteer",
        dataType:'json',
        data:{applyManId:userId,auditState:state},
        success:function (data) {

            alert(data.msg);
            if(data.code >= 1){
                loadVolunteerData(params);
                // closeDialog("auditDialog");
            }
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}

// function auditVolunteer(volunteerId,state,applyManId){
//
//     $("#volunteerId").val(volunteerId);
//     $("#auditState").val(state);
//     $("#applyManId").val(applyManId);
//     $("#auditDialog").dialog("open");
// }
function closeDialog(dialogId){
    $("#volunteerId").val("");
    $("#auditState").val("");
    $("#applyManId").val("");
    $("#auditDesc").val("");
    $("#" + dialogId).dialog("close");
}

function formatVolunteerData(data){
    if(data){
        for(var i=0;i<data.length;i++){

            if(data[i].is_volunteer == 1){
                data[i].is_volunteer = "是";
                data[i].operate = "已审核";
            }else if(data[i].is_volunteer == 2){
                data[i].is_volunteer = "否";
                data[i].operate = "已审核";
            }else{
                data[i].operate = "<a href=\"javascript:void 0;\" onclick=\"auditVolunteer('" + data[i].volunteerId + "',1,'" + data[i].userId + "')\">同意</a>";
                data[i].operate += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"auditVolunteer('" + data[i].volunteerId + "',0,'" + data[i].userId + "')\">不同意</a>";
            }
        }
    }
    return data;
}

function viewIdCard(idCardUrl){
    var idCard = "<img src=\"" + idCardUrl + "\" style=\"width:100%;height:100%\"/>";
    idCard += "<span id=\"result_img1_wm\" style=\"position: absolute; top: 200px; left: 130px;color:#ff0000;font-size: 18px\">本图片仅用于注册何氏族谱网</span>";
    $("#idCardDialog").html(idCard);
    $("#idCardDialog").dialog("open");
}