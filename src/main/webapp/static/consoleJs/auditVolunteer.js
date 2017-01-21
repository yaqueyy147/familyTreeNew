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
                            if(data.code == 1){
                                var obj = $("#volunteer-" + volunteerId);
                                $(obj).parent().parent().parent().find("td:eq(5)").find("div").text("是");
                                $(obj).parent().parent().parent().find("td:eq(6)").find("div").html("已审核");
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
});
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