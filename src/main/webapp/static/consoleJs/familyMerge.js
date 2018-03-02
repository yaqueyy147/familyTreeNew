/**
 * Created by suyx on 2016/12/21 0021.
 */
var setting;
var includeDesc;
var primarySetting;
$(function () {

    $("#rejectDialog").dialog({
        width: 500,
        height: 300,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/rejectInclude";
                    formData.mergeId = $("#mergeId").val();
                    formData.rejectDesc = $("#rejectDesc").val();
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        // async:false,
                        dataType:'json',
                        data:formData,
                        // async:false,
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){
                                var params = {};
                                var opp = "<span class=\"easyui-linkbutton\" style=\"margin-left: 20px\">已驳回</span>";
                                // opp += "&nbsp;&nbsp;<button type=\"button\" id=\"completeIn\" class=\"easyui-linkbutton\" style=\"margin-left: 20px\">完成收录</button>";
                                $("#reject").replaceWith(opp);
                                $("#primaryDesc p button").remove("#acceptIn");
                                closeDialog("rejectDialog");

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
            },
            {
                "text":"取消",
                handler:function () {
                    $("#rejectForm").form('clear');
                    closeDialog("userDialog");
                }
            }
        ]
    });

    $("#reject").click(function () {
        $("#rejectDialog").dialog('open');
    });

    setting = {
        view: {
            addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        }
        // ,
        // check: {
        //     enable: true,
        //     chkStyle: "checkbox",
        //     chkboxType: { "Y": "ps", "N": "ps" }
        // }
    };


    //时间空间初始化
    $("#birth_time").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        language: 'zh-CN',
        autoclose:true,
    });

    $("#die_time").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        language: 'zh-CN',
        autoclose:true
    });
    $.fn.serializeObject = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    $("#targetFamily").change(function () {
        selectTarget($(this));
    });

    $("#savePeople").click(function () {
        var formData = $("#peopleForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        if(confirm("确定" + includeDesc + "吗？")){
            $.ajax({
                type:'post',
                url: projectUrl + '/consoles/savePeople',
                dataType: 'json',
                data:testData,
                // async:false,
                success:function (data) {
                    alert(data.msg);
                    // var zNodes = initPeopleData(familyId);
                    // initFamilyTree(zNodes,setting);
                    $("#addModal").modal('hide');
                    $("#peopleForm")[0].reset();
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

    $("#confirmInclude").click(function () {
        var primaryObj = $.fn.zTree.getZTreeObj("primaryFamilyTree");
        var primaryFamily = primaryObj.transformToArray(primaryObj.getNodes());
        alert(JSON.stringify(primaryFamily[0]) + "--->1");
        $("#localBack").parent().append(primaryFamily);
        // var targetFamily = $.fn.zTree.getZTreeObj("targetFamilyTree").getNodes();
        // targetFamily = targetFamily.transformToArray(targetFamily.getNodes());
        // $.ajax({
        //     type:'post',
        //     url: projectUrl + '/consoles/confirmInclude',
        //     dataType: 'json',
        //     data:{familyId:familyId},
        //     async:false,
        //     success:function (data) {
        //         if(data.code >= 1){
        //             alert("收录完成!");
        //         }
        //
        //     },
        //     error:function (data) {
        //         alert(JSON.stringify(data));
        //     }
        // });
    });

    $('#addModal').on('hidden.bs.modal', function (e) {
        $("#peopleForm")[0].reset();
        var ss = "<option value='0'>无</option>";
        $("#fatherId").html(ss);
        $("#motherId").html(ss);
        $("#peopleType").val(1);
        $("#peopleInfo").text("");
        $("#mateId").val("");
        $("#superiorId").val("");
        $("#id").val("");
    });

    //同意收录，同意开启
    $("#acceptIn").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + '/consoles/confirmInclude',
            dataType: 'json',
            data:{familyId:familyId},
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert("操作成功!");
                    var opp = "<span class=\"easyui-linkbutton\" style=\"margin-left: 20px\">补录中...</span>";
                    // opp += "&nbsp;&nbsp;<button type=\"button\" id=\"completeIn\" class=\"easyui-linkbutton\" style=\"margin-left: 20px\">完成收录</button>";
                    $("#acceptIn").replaceWith(opp);
                    $("#primaryDesc p button").remove("#reject");
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
    });

    $("#completeIn").click(function() {
    	$.ajax({
            type:'post',
            url: projectUrl + '/consoles/completeIn',
            dataType: 'json',
            data:{familyId:familyId},
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert("操作成功!");
                    $("#supplementDesc").text("收录完成");
                    $("#batchAcceptIn").remove();
                    $("#batchRefuseIn").remove();
                    $("#completeIn").remove();
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
	});
    
    primarySetting = {
        view: {
            addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        }
        ,
        check: {
            enable: true,
            chkStyle: "checkbox",
            // chkboxType: { "Y": "ps", "N": "ps" }
        }
        // ,
        // callback:{
        //     onClick:zTreeOnClick
        // }
    };

    $.when(initPeopleData(familyId)).done(function(data){
        initFamilyTree("primaryFamilyTree",primarySetting,data);
    });

    // var zNodes = initPeopleData(familyId);
    // initFamilyTree("primaryFamilyTree",primarySetting,zNodes);

    // initTargetFamily();
    $("#localBack").click(function () {
        history.back();
    });

});

function closeDialog(dialogId){
    $("#" + dialogId).dialog("close");
}

function initFamilyTree(treeId,setting,zNodes) {
    $.fn.zTree.init($("#" + treeId), setting, zNodes);
}

function addDiyDom(treeId, treeNode) {
    var IDMark_A = "_a";
    var aObj = $("#" + treeNode.tId + IDMark_A);

    var nodeLevel = treeNode.level;
    var parentId = 0;

    var peopleStatus = treeNode.peopleStatus;

    if(nodeLevel > 0){
        parentId = treeNode.pId;
    }
    var mateName = treeNode.mateName;
    var editStr = "";

    if(peopleStatus == 5){
        editStr += "<div style='display: inline-block'>";
        editStr += "-<a id='diyBtnInclude_" +treeNode.id+ "_ok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + treeNode.id + "',1,'" + treeNode.tId +　"',1)\">同意添加收录</a>";
        editStr += "-<a id='diyBtnInclude_" +treeNode.id+ "_nok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + treeNode.id + "',7,'" + treeNode.tId +　"',1)\">不同意添加收录</a>";
        editStr += "</div>";
    }
    if(peopleStatus == 51){
        editStr += "<div style='display: inline-block'>";
        editStr += "-<a id='diyBtnInclude_" +treeNode.id+ "_ok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + treeNode.id + "',1,'" + treeNode.tId +　"',2)\">同意修改收录</a>";
        editStr += "-<a id='diyBtnInclude_" +treeNode.id+ "_nok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + treeNode.id + "',7,'" + treeNode.tId +　"',2)\">不同意修改收录</a>";
        editStr += "</div>";
    }
    if(peopleStatus == 52){
        editStr += "<div style='display: inline-block'>";
        editStr += "-<a id='diyBtnInclude_" +treeNode.id+ "_ok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + treeNode.id + "',9,'" + treeNode.tId +　"',3)\">同意删除收录</a>";
        editStr += "-<a id='diyBtnInclude_" +treeNode.id+ "_nok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + treeNode.id + "',7,'" + treeNode.tId +　"',3)\">不同意删除收录</a>";
        editStr += "</div>";
    }

    if($.trim(mateName).length > 0){
        var mates = mateName.split(",");
        editStr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配偶:";
        for(var i=0;i<mates.length;i++){
            var mate = mates[i].split("--");
            var mateStatus = mate[2];
            var mateSupplement = mate[3];
            var mateid = mate[1];
            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;'>" + mate[0] + "</a>";// onclick=\"editPeople('" + mate[1] + "','" + treeNode.level + "')\"
            if(mateStatus == 5 && mateSupplement == 1){
                editStr += "<div style='display: inline-block'>";
                editStr += "-<a id='diyBtnInclude_mate_" +treeNode.id+ "_ok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + mateid + "',1,'" + treeNode.tId +　"',1)\">同意添加收录</a>";
                editStr += "-<a id='diyBtnInclude_mate_" +treeNode.id+ "_nok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + mateid + "',7,'" + treeNode.tId +　"',1)\">不同意添加收录</a>";
                editStr += "</div>";
            }
            if(mateStatus == 51 && mateSupplement == 1){
                editStr += "<div style='display: inline-block'>";
                editStr += "-<a id='diyBtnInclude_mate_" +treeNode.id+ "_ok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + mateid + "',1,'" + treeNode.tId +　"',2)\">同意修改收录</a>";
                editStr += "-<a id='diyBtnInclude_mate_" +treeNode.id+ "_nok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + mateid + "',7,'" + treeNode.tId +　"',2)\">不同意修改收录</a>";
                editStr += "</div>";
            }
            if(mateStatus == 52 && mateSupplement == 1){
                editStr += "<div style='display: inline-block'>";
                editStr += "-<a id='diyBtnInclude_mate_" +treeNode.id+ "_ok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + mateid + "',9,'" + treeNode.tId +　"',3)\">同意删除收录</a>";
                editStr += "-<a id='diyBtnInclude_mate_" +treeNode.id+ "_nok' style='display: inline-block;color: #CC2222' onclick=\"affirmInclude(this,'" + mateid + "',7,'" + treeNode.tId +　"',3)\">不同意删除收录</a>";
                editStr += "</div>";
            }
        }
    }


    if(peopleStatus == 7){
    	editStr += "&nbsp;&nbsp;<span style='color:#FF7F00'>补录审核未通过</span>";
    }

    aObj.after(editStr);
}

/**
 * 设置族谱树的数据
 * @param familyId
 * @returns {Array}
 */
function initPeopleData(familyId){
    var defer = $.Deferred();
    var zNodes = [];
    $.ajax({
        type:'post',
        url:projectUrl + '/consoles/getPeopleList4Merge',
        dataType:'json',
        // async:false,
        data:{familyId : familyId,isIndex:0},
        success:function (data) {

            // var genNum = 0;
            // for(var i=0;i<data.length;i++) {
            //     var ii = data[i];
            //     var node = {};
            //     node.id = ii.id;
            //     node.pId = ii.superiorId;
            //     node.name = ii.name;
            //     node.createId = ii.createId;
            //     var mateList = ii.mateList;
            //     var mateName = "";
            //     for(var j=0;j<mateList.length;j++){
            //         var jj = mateList[j];
            //         mateName += "," + jj.name + "--" + jj.id + "--" + jj.peopleStatus + "--" + jj.isSupplement;
            //     }
            //     node.mateName = mateName;
            //     node.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
            //     node.open = true;
            //     node.peopleStatus = ii.peopleStatus;
            //     if(ii.peopleStatus != 5 && ii.peopleStatus != 51 && ii.peopleStatus != 52){
            //         node.nocheck = true;
            //     }
            //     node.isSupplement = ii.isSupplement;
            //     zNodes[i] = node;
            //     if(genNum < ii.generation){
            //         genNum = ii.generation;
            //     }
            // }

            // var peopleHtml = "<p style=\"margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;\">家族人数：&nbsp;" + data.length + "&nbsp;人</p>";
            // peopleHtml += "<p style=\"margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;\">家族代数：&nbsp;" + genNum + "&nbsp;代</p>";
            // $("#primaryDesc").append(peopleHtml);
            defer.resolve(data);
            $("#peopleCount").text(data.length);
            // $("#familyGenNum").text(genNum);
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
    return defer.promise();

}

function selectTarget(obj) {
    var familyId = $(obj).val();
    var familyDesc = $(obj).find("option:selected").attr("family-desc");
    var familyAddr = $(obj).find("option:selected").attr("family-addr");

    var zNodes = initPeopleData(familyId);

    initFamilyTree("targetFamilyTree",setting,zNodes);
    if($.trim(familyAddr).length > 0){
        $("#targetFamilyAddr").text("家族属地：" + familyAddr);
    }

    if($.trim(familyDesc).length > 0){
        $("#targetFamilyDesc").text("家族描述：" + familyDesc);
    }
}

function zTreeOnClick(event, treeId, treeNode) {
    editPeople(treeNode.id,treeNode.level);
    // initParent(treeNode.level);
    // var params = {"peopleId":treeNode.id};
    // var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    // tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    // tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    // $("#peopleForm").populateForm(tPeople);
    // $("#addModalLabel").text("修改族人【" + tPeople.name + "】信息");
    // $("#addModal").modal('show');

}

function editPeople(peopleId,generation){
    // var targetFamilyId = $("#targetFamily").val();
    // if($.trim(targetFamilyId).length <= 0){
    //     alert("请选择一个目标家族！");
    //     return;
    // }
    initParent(familyId,generation);
    var params = {"peopleId":peopleId};
    var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    if(tPeople.birthTime){
        tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    }else{
        tPeople.birth_time = "";
    }
    if(tPeople.dieTime){
        tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    }else{
        tPeople.die_time = "";
    }
    $("#peopleForm").populateForm(tPeople);
    // $("#addModalLabel").text("修改族人【" + tPeople.name + "】信息");
    // $("#familyId").val(targetFamilyId);
    // includeDesc = "将【" + tPeople.name + "】收录并入家族【" + $("#targetFamily").find("option:selected").text() + "】中";
    // $("#generation").parent().append("&nbsp;&nbsp;" + includeDesc);

    var imgPath = tPeople.photoUrl;
    $("#result_img").attr('src',imgPath);
    $("#result_img").show();
    $("#imgFile").hide();
    $("#photoUrl").attr('value',imgPath);
    $("#show_img").mouseover(function(){
        $("#result_img").attr('src',projectUrl + "/static/images/deleteImg.png");
    });
    $("#show_img").mouseout(function(){
        $("#result_img").attr('src',imgPath);
    });

    $("#result_img").click(function(){
        $("#result_img").hide();
        $("#imgFile").show();
        $("#photoUrl").removeAttr('value');
        $("#show_img").unbind('mouseover');
        $("#show_img").unbind('mouseout');

    });

    $("#addModal").modal('show');
}

function initParent(familyId,generation){
    var defer = $.Deferred();
    $.ajax({
        type:'post',
        url:projectUrl + '/consoles/getParent',
        dataType:'json',
        // async:false,
        data:{familyId : familyId,generation:generation},
        success:function (data) {
            var fathers = data.fatherList;
            var mothers = data.motherList;
            var fatherHtml = "";
            var motherHtml = "";
            if(fathers.length > 0){
                for(var i=0;i<fathers.length;i++){
                    var ii = fathers[i];
                    fatherHtml += "<option value='" + ii.id + "'>" + ii.name + "(" + ii.id + ")</option>";
                }
            }else{
                fatherHtml += "<option value='0'>无</option>";
            }
            $("#fatherId").html(fatherHtml);

            if(mothers.length > 0){
                for(var i=0;i<mothers.length;i++){
                    var ii = mothers[i];
                    motherHtml += "<option value='" + ii.id + "'>" + ii.name + "(" + ii.id + ")</option>";
                }
            }else{
                motherHtml += "<option value='0'>无</option>";
            }
            $("#motherId").html(motherHtml);

            defer.resolve(data);
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
    return defer.promise();
}

function affirmInclude(obj, peopleId, auditStatus, tId, includeType) {
	var treeObj = $.fn.zTree.getZTreeObj("primaryFamilyTree");
	var node = treeObj.getNodeByTId(tId);
	var createId = node.createId;

	var parentNode = node.getParentNode();
    if(parentNode.peopleStatus != 1){
        alert("该成员父级还没有通过审核生效，不能审核该成员！");
        return;
    }

    $.when(doAudit(peopleId + ":" + createId, auditStatus, includeType)).done(function(data){
        if(data == 1){
            $(obj).parent().remove();
            node.nocheck = true;
            node.peopleStatus = auditStatus;
            treeObj.updateNode(node);
        }
    })

	// if(doAudit(peopleId + ":" + createId, auditStatus, includeType)){
	//
	// }
}

function batchAudit(obj, auditStatus){
	var treeObj = $.fn.zTree.getZTreeObj("primaryFamilyTree");
	var chkNodes = treeObj.getCheckedNodes(true);
	if(chkNodes.length <= 0){
		alert("请至少选择一个人!");
		return;
	}
	var ids = "";
	var names = "";
    var auditStatuss = "";
	for(var i=0;i<chkNodes.length;i++){
		var ii = chkNodes[i];
		ids += "," + ii.id + ":" + ii.createId;
		names += "," + ii.name;
		var peopleStatus = ii.peopleStatus;
		if(auditStatus == 1){
            if(peopleStatus == 5 || peopleStatus == 51){
                auditStatuss += "," + 1;
            }
            if(peopleStatus == 52){
                auditStatuss += "," + 9;
            }
        }else{
            auditStatuss += "," + 7;
        }


	}
	ids = ids.substring(1);
	names = names.substring(1);
    auditStatuss = auditStatuss.substring(1);
	$.messager.confirm('提示','确定要批量审核这些(' + names + ')人吗？',function(r){
	    if (r){

            $.when(doAudit(ids, auditStatuss)).done(function(data){
                if(data == 1){
                    $.when(initPeopleData(familyId)).done(function(data){
                        initFamilyTree("primaryFamilyTree",primarySetting,data);
                    });
                }
            })

	    	// if(doAudit(ids, auditStatuss)){
             //
             //    // var zNodes = initPeopleData(familyId);
	    	//     // initFamilyTree("primaryFamilyTree",primarySetting,zNodes);
	    	// }
	    }
	});

}

function doAudit(peopleId, auditStatus, includeType){
    var defer = $.Deferred();
	var auditB = 0;
	$.ajax({
        type:'post',
        url:projectUrl + '/consoles/auditIncludePeople',
        dataType:'json',
        // async:false,
        data:{peopleIds : peopleId,auditStatus:auditStatus,includeType:includeType},
        success:function (data) {
            if(data.code >= 1){
            	auditB = 1;
                defer.resolve(auditB);
            	alert("审核完成");
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
    return defer.promise();
}

function reject(mergeId) {
    $("#rejectDialog").dialog('open');
}