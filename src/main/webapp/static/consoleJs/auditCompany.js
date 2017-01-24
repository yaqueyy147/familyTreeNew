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

    $("#moneyListDialog").dialog({
        "closed":true,
        "buttons":[
            {
                "text":"关闭",
                handler:function () {
                    closeDialog("moneyListDialog");
                }
            }
        ]
    });

    var params = {};
    $("#companyList").datagrid({loadFilter:companyFilter}).datagrid('loadData', getData("/consoles/companyList",params));

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

function showMoneyList(companyId){
    var params = {"companyId":companyId};
    $("#moneyTable").datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData("/company/moneyList",params));
    $("#moneyListDialog").dialog('open');
}

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });

            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

function companyFilter(data){
    if(data){
        for(var i=0;i<data.length;i++){
            data[i].company_photo = "<img src=\"" + projectUrl + data[i].company_photo + "\" width=\"100px\" height=\"50px\" />";
            data[i].business_license = "<img src=\"" + projectUrl + data[i].business_license + "\" width=\"100px\" height=\"50px\" />";
            data[i].totalMoney = "<a href=\"javascript:void 0\" onclick=\"showMoneyList('" + data[i].id + "')\">" + data[i].totalMoney + "</a>";
            data[i].phone = data[i].company_mobile_phone + "," + data[i].company_telephone;
            if(data[i].state != 0){
                data[i].operate = "已审核";
            }else{
                data[i].operate = "<a href=\"javascript:void 0;\" onclick=\"auditCompany('" + data[i].id + "',1,'')\">同意</a>";
                data[i].operate += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"auditCompany('" + data[i].id + "',0,'')\">不同意</a>";
            }


        }
    }
    if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });

            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}