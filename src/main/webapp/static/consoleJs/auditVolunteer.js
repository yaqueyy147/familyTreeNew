/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    $("#auditDialog").dialog({
        "closed":true,
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


    var params = {};
    loadVolunteerData(params);

});

function loadVolunteerData(params) {
    var dataList = formatVolunteerData(getData("/consoles/volunteerList",params).dataList);
    $("#volunteerList").datagrid({
        data:dataList,
        columns:[[
            {field:"user_name",title:"申请人",width:"80"},
            {field:"phone",title:"联系电话",width:"80"},
            {field:"apply_desc",title:"申请说明",width:"120"},
            {field:"applyTime",title:"申请时间",width:"150"},
            {field:"is_volunteer",title:"是否志愿者",width:"80"},
            {field:"operate",title:"操作",width:"120"}
        ]],
        loadFilter:pagerFilter
    });
}

function auditVolunteer(volunteerId,state,applyManId){

    $("#volunteerId").val(volunteerId);
    $("#auditState").val(state);
    $("#applyManId").val(applyManId);
    $("#auditDialog").dialog("open");
}
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