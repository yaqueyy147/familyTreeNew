/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    var params = {};
    var dataList = getData("/consoles/familyList",params).dataList;
    dataList = formatDataList(dataList);
    // $("#familyList").datagrid({loadFilter:familyFilter}).datagrid('loadData', getData("/consoles/familyList",params).dataList);
    $("#familyList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        columns:[[
            {field:"id",title:"族谱Id",width:"80",hidden:true},
            {field:"familyName",title:"族谱名称",width:"150"},
            {field:"familyFirstName",title:"族谱姓氏",width:"150"},
            {field:"peopleCount",title:"族谱人数",width:"80"},
            {field:"createMan",title:"创建人",width:"80"},
            {field:"createTime",title:"创建时间",width:"180"},
            {field:"familyArea",title:"族谱属地",width:"80"}
        ]],
        loadFilter:pagerFilter
    });
});

function closeDialog(dialogId){
    $("#volunteerId").val("");
    $("#auditState").val("");
    $("#applyManId").val("");
    $("#auditDesc").val("");
    $("#" + dialogId).dialog("close");
}

function formatDataList(data){
    if(data){

        for(var i=0;i<data.length;i++){
            data[i].familyName = "<a href=\"" + projectUrl + "/consoles/familyTree?familyId=" + data[i].id + "\">" + data[i].familyName +" </a>";// onclick=\"familyDetail('" + data[i].id + "')\"
            data[i].createTime = new Date(data[i].createTime).Format("yyyy-MM-dd hh:mm:ss");
            data[i].familyArea = convertFamilyArea(data[i].familyArea);
        }
    }
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