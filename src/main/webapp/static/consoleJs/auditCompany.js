/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    $("#province4Search").val("");
    $("#province4Search").change();
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
    $("#licenseDialog").dialog({
        width: 660,
        height: 500,
        "closed":true,
        "buttons":[
            {
                "text":"关闭",
                handler:function () {
                    $("#licenseDialog").html("");
                    closeDialog("licenseDialog");
                }
            }
        ]
    });

    $("#doSearch").click(function () {
        var params = {};
        params.companyName = $("#companyName4Search").val();
        params.province = $("#province4Search").val();
        params.city = $("#city4Search").val();
        params.district = $("#district4Search").val();
        loadCompanyData(params);
    });


    $("#addMoney").click(function () {
        $('#moneyTable').datagrid('appendRow', {});
        var rows = $('#moneyTable').datagrid('getRows');
        $('#moneyTable').datagrid('beginEdit',rows.length - 1 );
    });

    var params = {};
    loadCompanyData(params)

});

function closeDialog(dialogId){
    $("#" + dialogId).dialog("close");
}

function loadCompanyData(params) {
    // var dataList = formatVolunteerData(getData("/consoles/volunteerList",params).dataList);
    var dataList = getData("/consoles/companyList",params).dataList;
    $("#companyList").datagrid({
        data:dataList,
        columns:[[
            {field:"company_name",title:"公司名",width:"150",
                formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        return '<span title='+ value + '>'+value+'</span>';
                    }
                    return '';
                }},
            {field:"totalMoney",title:"赞助金额",width:"80",
                formatter: function(value,row,index){

                    return "<a href=\"javascript:void 0\" onclick=\"showMoneyList('" + row.id + "')\">" + value + "</a>";
                }},
            {field:"company_mobile_phone",title:"联系电话",width:"100"},
            {field:"company_addr",title:"公司地址",width:"200",
                formatter: function(value,row,index){
                    var companyAddr = "";
                    if($.trim(row.province).length > 0){
                        companyAddr += row.province;
                    }
                    if($.trim(row.city).length > 0){
                        companyAddr += row.city;
                    }
                    if($.trim(row.district).length > 0){
                        companyAddr += row.district;
                    }
                    if($.trim(row.detail_addr).length > 0){
                        companyAddr += row.detail_addr;
                    }
                    if($.trim(companyAddr).length > 0){
                        return '<span title='+ companyAddr + '>'+companyAddr+'</span>';
                    }
                    return '';
                }},
            {field:"company_desc",title:"公司简介",width:"200",
                formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        return '<span title='+ value + '>'+value+'</span>';
                    }
                    return '';
                }},
            {field:"business_license",title:"营业执照",width:"120",
                formatter: function(value,row,index){
                    return "<img src=\"" + value + "\" width=\"100px\" height=\"50px\" onclick=\"viewLicense('" + value + "')\" />";
                }},
            {field:"state",title:"状态",width:"80",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "已通过审核";
                    }else if(value == 2){
                        return "未通过审核";
                    }else if(value == 3) {
                        return "待审核";
                    }else if(value == 9) {
                        return "已冻结";
                    }

                }},
            {field:"operate",title:"操作",width:"150",
                formatter: function(value,row,index){

                    var opHtml = "";
                    if(row.state == 3){
                        opHtml = "<a href=\"javascript:void 0;\" onclick=\"auditCompany('" + row.id + "',1)\">同意</a>";
                        opHtml += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"auditCompany('" + row.id + "',2)\">不同意</a>";
                        // return opHtml;
                    }else if(row.state == 1){
                        opHtml = "<span>已通过审核</span>";
                        opHtml += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"auditCompany('" + row.id + "',9)\">冻结账号</a>";
                    }else if(row.state == 2){
                        opHtml = "<span>未通过审核</span>";
                    }
                    else if(row.state == 9){
                        opHtml = "<span>账号已冻结</span>";
                    }

                    return opHtml;
                }}
        ]],
        loadFilter:pagerFilter
    });
}

function showMoneyList(companyId){
    var params = {"companyId":companyId};
    $("#moneyTable").datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData("/company/moneyList",params).dataList);
    $("#moneyListDialog").dialog('open');
}

function auditCompany(companyId,state) {
    var params = {};
    $.ajax({
        type:'post',
        url: projectUrl + "/consoles/auditCompany",
        dataType:'json',
        data:{companyId:companyId,auditState:state},
        success:function (data) {

            alert(data.msg);
            if(data.code >= 1){
                loadCompanyData(params);
                // closeDialog("auditDialog");
            }
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}

function viewLicense(licenseUrl){
    var licenseImg = "<img src=\"" + licenseUrl + "\" width=\"600px\" height=\"600px\" />";
    // licenseImg += "<span id=\"result_img1_wm\" style=\"display: none;position: absolute; top: 300px; left: 0;\">本图片仅用于注册何氏族谱网</span>";
    $("#licenseDialog").html(licenseImg);
    $("#licenseDialog").dialog("open");
}
