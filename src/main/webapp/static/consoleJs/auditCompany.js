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
    $("#moneyTable").datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData(companyId));
    $("#moneyListDialog").dialog('open');
    // $.ajax({
    //     type:'post',
    //     url: projectUrl + "/company/moneyList",
    //     dataType:'json',
    //     async:false,
    //     data:{"companyId" : companyId},
    //     success:function (data) {
    //         if(data){
    //             var moneyList = data.moneyList;
    //             var moneyHtml = "<tr>";
    //             for(var i=0;i<moneyList.length;i++){
    //                 var ii = moneyList[i];
    //                 // moneyHtml  += "<td>" + (i+1) + "</td>";
    //                 moneyHtml  += "<td>" + ii.payMoney + "</td>";
    //                 moneyHtml  += "<td>" + ii.payDesc + "</td>";
    //                 moneyHtml  += "<td>" + ii.payTime + "</td>";
    //                 moneyHtml  += "<td>" + ii.payMan + "</td>";
    //             }
    //             moneyHtml += "</tr>";
    //             // $("#moneyBody").html(moneyHtml);
    //             $("#moneyTable").datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    //             $("#moneyListDialog").dialog('open');
    //         }
    //     },
    //     error:function (data) {
    //         alert(JSON.stringify(data));
    //     }
    // });
}

function getData(companyId){
    var result;
    $.ajax({
        type:'post',
        url: projectUrl + "/company/moneyList",
        dataType:'json',
        async:false,
        data:{"companyId" : companyId},
        success:function (data) {
            result = data.moneyList;

        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
    return result;
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