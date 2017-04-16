/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    var params = {};
    var rankData = getData("/consoles/pointsRanking",params);
    var personalRankDataList = rankData.listPersonalPoints;
    $("#personalRankList").datagrid({
        data:personalRankDataList,
        loadMsg:"加载中...",
        columns:[[
            {field:"user_id",title:"用户Id",width:"80",hidden:true},
            {field:"user_name",title:"用户名",width:"150"},
            {field:"totalPoints",title:"积分",width:"150"}
        ]]
    });

    var companyRankDataList = rankData.listCompanyPoints;
    $("#companyRankList").datagrid({
        data:companyRankDataList,
        loadMsg:"加载中...",
        columns:[[
            {field:"company_id",title:"赞助商Id",width:"80",hidden:true},
            {field:"company_name",title:"赞助商名",width:"150"},
            {field:"totalPoints",title:"积分",width:"150"}
        ]]
    });
});

function formatDataList(data){
    if(data){
        alert(JSON.stringify(data));
        for(var i=0;i<data.length;i++){
            data[i].index = (i+1);
        }
    }
    return data;
}
