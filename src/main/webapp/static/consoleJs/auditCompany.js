/**
 * Created by suyx on 2017/1/12.
 */
var editIndex = undefined;
$(function () {
    $("#province4Search").val("");
    $("#province4Search").change();
    $("#moneyListDialog").dialog({
        "closed":true,
        modal:true,
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
        modal:true,
        resizable:true,
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

    $("#familylistDialog").dialog({
        width: 660,
        height: 200,
        "closed":true,
        modal:true,
        resizable:true,
        "buttons":[
            {
                "text":"关闭",
                handler:function () {
                    $("#companyid4set").val("");
                    closeDialog("familylistDialog");
                }
            },
            {
                "text":"确定",
                handler:function () {
                    var companyid = $("#companyid4set").val();
                    var familyid = $("#rankfamily").val();
                    var familyname = $("#rankfamily").find("option:selected").text();
                    $.ajax({
                        type:'post',
                        url: projectUrl + "/consoles/setrankfamily",
                        dataType:'json',
                        data:{companyid:companyid,familyid:familyid,familyname:familyname},
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){

                                var params = {};
                                params.companyName = $("#companyName4Search").val();
                                params.province = $("#province4Search").val();
                                params.city = $("#city4Search").val();
                                params.district = $("#district4Search").val();
                                loadCompanyData(params);
                                editIndex = undefined;
                            }
                        },
                        error:function (data) {
                            var responseText = data.responseText;
                            if(responseText.indexOf("登出跳转页面") >= 0){
                                ajaxErrorToLogin();
                            }else{
                                alert(JSON.stringify(data));
                            }
                        }
                    });
                    $("#companyid4set").val("");
                    closeDialog("familylistDialog");
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
        if (endEditing()){
            $('#moneyTable').datagrid('insertRow', {index: 0,row:{}});
            var rows = $('#moneyTable').datagrid('getRows');
            editIndex = 0;
            $('#moneyTable').datagrid('selectRow', editIndex);
            $('#moneyTable').datagrid('beginEdit',editIndex);
        }
    });

    $("#affirmAdd").click(function () {
    	if(editIndex != undefined){
    		$("#moneyTable").datagrid('endEdit',editIndex);
            var moneyInfo = $("#moneyTable").datagrid('getSelected');
            var companyId = $("#companyId").val();
            var companyName = $("#companyName").val();
            moneyInfo.companyId = companyId;
            moneyInfo.companyName = companyName;
            moneyInfo.type = 2;
            $.ajax({
            	type:'post',
                url: projectUrl + "/consoles/addMoney",
                dataType:'json',
                data:moneyInfo,
                success:function (data) {

                    alert(data.msg);
                    if(data.code >= 1){
                    	showMoneyList(companyId,companyName);
                    	
                    	var params = {};
                        params.companyName = $("#companyName4Search").val();
                        params.province = $("#province4Search").val();
                        params.city = $("#city4Search").val();
                        params.district = $("#district4Search").val();
                        loadCompanyData(params);
                        editIndex = undefined;
                    }
                },
                error:function (data) {
                	$('#moneyTable').datagrid('selectRow', editIndex);
                    $('#moneyTable').datagrid('beginEdit',editIndex);
                    var responseText = data.responseText;
                    if(responseText.indexOf("登出跳转页面") >= 0){
                        ajaxErrorToLogin();
                    }else{
                        alert(JSON.stringify(data));
                    }
                }
            });
    	}
        
    });

    $("#cancelAdd").click(function () {
        if (editIndex == undefined){return;}
        $('#moneyTable').datagrid('cancelEdit', editIndex)
            .datagrid('deleteRow', editIndex);
        editIndex = undefined;

    });

    $("#deleteMoney").click(function () {
        var companyId = $("#companyId").val();
        var companyName = $("#companyName").val();
        var selectRows = $("#moneyTable").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var totalMoney = 0;
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            totalMoney = parseInt(totalMoney)*1 + parseInt(ii.payMoney)*1;
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除这些充值记录(' + selectIds + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteMoney",
                    // async:false,
                    dataType:'json',
                    data:{moneyIds:selectIds,type:2,companyId:companyId,totalMoney:totalMoney},
                    // async:false,
                    success:function (data) {
                        alert(data.msg);
                        var params = {};
                        showMoneyList(companyId,companyName);

                        var params = {};
                        params.companyName = $("#companyName4Search").val();
                        params.province = $("#province4Search").val();
                        params.city = $("#city4Search").val();
                        params.district = $("#district4Search").val();
                        loadCompanyData(params);
                    },
                    error:function (data) {
                        var responseText = data.responseText;
                        if(responseText.indexOf("登出跳转页面") >= 0){
                            ajaxErrorToLogin();
                        }else{
                            alert(JSON.stringify(data));
                        }
                    }
                });
            }
        });
    });

    var params = {type:2};
    loadCompanyData(params);

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

                    return "<a href=\"javascript:void 0\" onclick=\"showMoneyList('" + row.id + "','" + row.company_name + "')\">" + value + "</a>";
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
            {field:"rankfamilyname",title:"积分排名家族",width:"120",
                formatter: function(value,row,index){
                    var html = value;
                    html += "&nbsp;&nbsp;";
                    if($.trim(value).length > 0){
                        html += "<a href=\"javascript:void 0;\" onclick=\"setrankfamily('" + row.id + "','" + row.company_name + "')\">修改</a>";
                    }else{
                        html += "<a href=\"javascript:void 0;\" onclick=\"setrankfamily('" + row.id + "','" + row.company_name + "')\">设置</a>";
                    }

                    return html;
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

function showMoneyList(companyId,companyName){
    var params = {"companyId":companyId,"type":2};
    var moneyList = getData("/consoles/moneyList",params).dataList;
    $("#moneyTable").datagrid({
    	data:moneyList,
    	columns:[[
            {field:"ck",checkbox:"true"},
    		{field:"payMoney",title:"充值金额",width:"90",
    			editor:{type:'numberbox'},
    		},
    		{field:"payDesc",title:"充值说明",width:"150",
    			editor:{type:'textbox'},
    			formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        return '<span title='+ value + '>'+value+'</span>';
                    }
                    return '';
                }
    		},
    		{field:"payTime",title:"充值时间",width:"150",
    			formatter: function(value,row,index){
                    if($.trim(value).length <= 0){
                        return "";
                    }
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }
    		},
    		{field:"payMan",title:"充值人",width:"100"}
    	]],
    	loadFilter:pagerFilter
    });
    $("#companyId").val(companyId);
    $("#companyName").val(companyName);
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
            var responseText = data.responseText;
            if(responseText.indexOf("登出跳转页面") >= 0){
                ajaxErrorToLogin();
            }else{
                alert(JSON.stringify(data));
            }
        }
    });
}

function viewLicense(licenseUrl){
    var licenseImg = "<img src=\"" + licenseUrl + "\" style=\"width:100%;height:100%\" />";
    // licenseImg += "<span id=\"result_img1_wm\" style=\"display: none;position: absolute; top: 300px; left: 0;\">本图片仅用于注册何氏族谱网</span>";
    $("#licenseDialog").html(licenseImg);
    $("#licenseDialog").dialog("open");
}

function endEditing(){
    // alert(editIndex);
    if (editIndex == undefined){
        return true;
    }else {
        return false;
    }
}

function setrankfamily(companyid,companyname) {
    $("#companyid4set").val(companyid);
    $("#companyname4set").text(companyname);
    $("#familylistDialog").dialog("open");
}