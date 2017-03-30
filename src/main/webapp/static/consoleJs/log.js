/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $(".easyui-datetimebox").datetimebox({
        // timeSeparator:'-'
    });

    $("#doSearch").click(function () {
        var beginTime = $("#beginTime").val();
        var endTime = $("#endTime").val();
        if($.trim(beginTime).length > 0){
            beginTime = new Date(beginTime).Format("yyyy-MM-dd hh:mm:ss");
        }
        if($.trim(endTime).length > 0){
            endTime = new Date(endTime).Format("yyyy-MM-dd hh:mm:ss");
        }
        var params = {};
        params.operateType = $("#operateType").val();
        // params.operateMan = $("#operateMan").val();
        params.beginTime = beginTime;
        params.endTime = endTime;
        loadDataGrid(params);
    });

    var params = {};
    loadDataGrid(params);
});

function loadDataGrid(params) {
    var dataList = getData("/log/logList",params).logList;
    // dataList = rankData.listPersonalPoints;
    $("#logList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"日志Id",width:"80",hidden:true},

            {field:"operateType",title:"操作类型",width:"80",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "新增";
                    }
                    if(value == 2){
                        return "修改";
                    }
                    if(value == 3){
                        return "删除";
                    }
                    return '其他';
                }},
            {field:"operateMan",title:"操作人",width:"80"},
            {field:"operateTime",title:"操作时间",width:"150"},
            {field:"operateContent",title:"操作内容",width:"350",
                formatter: function(value,row,index){
                    return '<span title='+value+'>'+value+'</span>'
                }},
            {field:"operateContentOld",title:"修改前内容",width:"350",
                formatter: function(value,row,index){
                    if($.trim(value).length <= 0){
                        return "";
                    }
                    return '<span title='+value+'>'+value+'</span>'
                }}
        ]],
        loadFilter:pagerFilter
    });
}

