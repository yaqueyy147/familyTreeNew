/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    var params = {};
    $("#familyList").datagrid({loadFilter:familyFilter}).datagrid('loadData', getData("/consoles/familyList",params));
});

function closeDialog(dialogId){
    $("#volunteerId").val("");
    $("#auditState").val("");
    $("#applyManId").val("");
    $("#auditDesc").val("");
    $("#" + dialogId).dialog("close");
}

function familyFilter(result){
    var data = result.dataList;
    if(data){

        for(var i=0;i<data.length;i++){
            data[i].familyName = "<a href=\"" + projectUrl + "/consoles/familyTree?familyId=" + data[i].id + "\">" + data[i].familyName +" </a>";// onclick=\"familyDetail('" + data[i].id + "')\"
            data[i].createTime = new Date(data[i].createTime).Format("yyyy-MM-dd hh:mm:ss");
            data[i].familyArea = convertFamilyArea(data[i].familyArea);
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

function convertFamilyArea(area) {
    var familyArea = "无";
    switch(area){
        case 0:
            familyArea = "无";
            break;
        case 1:
            familyArea = "大陆";
            break;
        case 2:
            familyArea = "香港";
            break;
        case 3:
            familyArea = "台湾";
            break;
        case 4:
            familyArea = "澳门";
            break;
        case 5:
            familyArea = "无";
            break;
        default:
            break;
    }
    return familyArea;
}