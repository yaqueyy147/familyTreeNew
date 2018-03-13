/**
 * Created by suyx on 2016/12/21 0021.
 */
var setting;
var includeDesc;
var mainSetting;
$(function () {

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
        ,
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "s" }
        }
    };

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

    $("#tojoint").click(function() {
    	var maintreeobj = $.fn.zTree.getZTreeObj("mainFamilyTree");
    	var mainchknodes = maintreeobj.getCheckedNodes(true);
        // if(mainchknodes.length <= 0){
        //     $.messager.alert('提示','请在主族谱选择一个族人作为合并的根!');
        //     return;
        // }else if(mainchknodes.length > 1){
        //     $.messager.alert('提示','只能选择其中一个族人!');
        //     return;
        // }
        var maincknode = "";
        if(mainchknodes.length > 0){
            maincknode = mainchknodes[0];
        }
    	var targettreeobj = $.fn.zTree.getZTreeObj("targetFamilyTree");
    	var targetchknodes = targettreeobj.getCheckedNodes(true);
        if(targetchknodes.length <= 0){
            $.messager.alert('提示','请在目标族谱选择族人用来合并!');
            return;
        }
        $.messager.confirm('提示', '确定要合并选择的族人吗', function(r){
            if (r){
                $(".loading").show();
                var targetpeopleids = "";
                for(var i=0;i<targetchknodes.length;i++){
                    var ii = targetchknodes[i];
                    targetpeopleids += "," + ii.id;
                }
                targetpeopleids = targetpeopleids.substring(1);
                $.ajax({
                    type:'post',
                    url:projectUrl + '/consoles/affirmjoint',
                    dataType:'json',
                    // async:false,
                    data:{familyId : mianFamilyId,mainpeopleid:maincknode.id,mainpeoplegeneration:maincknode.generation,targetpeopleids:targetpeopleids},
                    success:function (data) {
                        if(data.code){
                            $.messager.alert('提示','合并完成!');
                            $.when(initPeopleData(mianFamilyId)).done(function(data){
                                initFamilyTree("mainFamilyTree",mainSetting,data);
                                $(".loading").hide()
                            });
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
        });

	});

    $("#todelete").click(function () {
        var maintreeobj = $.fn.zTree.getZTreeObj("mainFamilyTree");
        var mainchknodes = maintreeobj.getCheckedNodes(true);
        if(mainchknodes.length <= 0){
            $.messager.alert('提示','您还没有选中需要删除的族人!');
            return;
        }

        $.messager.confirm('提示', '确定要删除选择的族人吗', function(r){
            if (r){
                $(".loading").show();
                var peopleids = "";
                for(var i=0;i<mainchknodes.length;i++){
                    var ii = mainchknodes[i];
                    peopleids += "," + ii.id;
                }
                peopleids = peopleids.substring(1);
                $.ajax({
                    type:'post',
                    url:projectUrl + '/consoles/deletepeople',
                    dataType:'json',
                    // async:false,
                    data:{familyId : mianFamilyId,peopleids:peopleids},
                    success:function (data) {
                        if(data.code){
                            $.messager.alert('提示','删除完成!');
                            $.when(initPeopleData(mianFamilyId)).done(function(data){
                                initFamilyTree("mainFamilyTree",mainSetting,data);
                                $(".loading").hide()
                            });
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
        });

    });

    mainSetting = {
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
            chkboxType: { "Y": "", "N": "" }
        }
    };

    $.when(initPeopleData(mianFamilyId)).done(function(data){
        $("#peopleCount").text(data.length);
        initFamilyTree("mainFamilyTree",mainSetting,data);
    });

    initTargetFamily();

    $("#localBack").click(function () {
        history.back();
    });

});

function closeDialog(dialogId){
    $("#" + dialogId).dialog("close");
}

function initFamilyTree(treeId,setting,zNodes) {
    $.fn.zTree.init($("#" + treeId), setting, zNodes);
    $(".loading").hide();
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

    if($.trim(mateName).length > 0){
        var mates = mateName.split(",");
        editStr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配偶:";
        for(var i=0;i<mates.length;i++){
            var mate = mates[i].split("--");
            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;'>" + mate[0] + "</a>";
            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 3px;color:#ff0000' onclick=\"deletePeople('" + mate[1] + "','" + mate[0] + "',2,'"+ treeNode.id +"')\">删除</a>";
        }
    }
    if("mainFamilyTree" == treeId){
        editStr += "<a style='display: inline-block;margin-left: 10px;color:#ff0000' id='diyBtn2_" +treeNode.id+ "' onclick=\"deletePeople('"+ treeNode.id +"','" + treeNode.name + "',1,null)\">删除</a>";
    }

    aObj.after(editStr);
}

/**
 * 设置族谱树的数据
 * @param familyId
 * @returns {Array}
 */
function initPeopleData(familyId){
    $(".loading").show();
    var defer = $.Deferred();
    var zNodes = [];
    $.ajax({
        type:'post',
        url:projectUrl + '/consoles/getPeopleListJoint',
        dataType:'json',
        // async:false,
        data:{familyId : familyId,isIndex:0},
        success:function (data) {
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

function selectTarget(obj) {
    var familyId = $(obj).val();
    var familyDesc = $(obj).find("option:selected").attr("family-desc");
    var familyAddr = $(obj).find("option:selected").attr("family-addr");
    $.when(initPeopleData(familyId)).done(function(data){
        initFamilyTree("targetFamilyTree",setting,data);
        $(".loading").hide();
    });

    if($.trim(familyAddr).length > 0){
        $("#targetFamilyAddr").text("家族属地：" + familyAddr);
    }

    if($.trim(familyDesc).length > 0){
        $("#targetFamilyDesc").text("家族描述：" + familyDesc);
    }
}

function initTargetFamily(){

    $.ajax({
        type:'post',
        url:projectUrl + '/consoles/targetfamily',
        dataType:'json',
        // async:false,
        data:{familyId:mianFamilyId},
        success:function (data) {
            if(data){
                var targetoptions = "";
                for(var i=0;i<data.length;i++){
                    var ii = data[i];
                    var familyaddr = ii.province + ii.city + ii.district;
                    targetoptions += "<option value='" + ii.id + "' family-desc='" + ii.familyDesc + "' family-addr='" + familyaddr + "'>" + ii.familyName + "</option>";
                }
                $("#targetFamily").html(targetoptions);
                $("#targetFamily").change();
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

function deletePeople(peopleId,peopleName,peopleType,cNodeId) {
    if(confirm("删除成员将会同时删除其所有的子孙，确定要删除成员(" + peopleName + ")吗？")){
        $(".loading").show();
        var treeObj = $.fn.zTree.getZTreeObj("mainFamilyTree");
        $.ajax({
            type:'post',
            url:projectUrl + '/consoles/deletePeople',
            dataType:'json',
            // async:false,
            data:{peopleId : peopleId, familyId:mianFamilyId,peopleType:peopleType},
            success:function (data) {
                if(data.code >= 1){
                    alert("删除完成!");
                    // var zNodes = initPeopleData(familyId);
                    // initFamilyTree(zNodes,setting);
                    if(peopleType == 1){//删除本族人,移除当前节点
                        var cNode = treeObj.getNodeByParam("id", peopleId, null);
                        treeObj.removeNode(cNode);
                    }else{//删除配偶,修改当前节点
                        var cNode = treeObj.getNodeByParam("id", cNodeId, null);
                        // var nodeIndex =  treeObj.getNodeIndex(cNode);
                        var nodesCount = treeObj.getNodesByParam("pId", cNode.pId, parentNode).length;
                        var targetNode = cNode.getPreNode();
                        var moveType = "next";
                        if(cNode.isFirstNode){
                            targetNode = cNode.getNextNode();
                            moveType = "prev";
                        }

                        var parentNode = cNode.getParentNode();
                        treeObj.removeNode(cNode);
                        var mateStr = cNode.mateName;
                        var mates = mateStr.split(",");

                        for(var i=0;i<mates.length;i++){
                            var mate = mates[i].split("--");
                            var mateId = mate[1];
                            var mateName = mate[0];
                            if(peopleId == mateId){
                                mates.splice(i, 1);
                                break;
                            }
                        }

                        cNode["mateName"] = mates.toString();
                        treeObj.addNodes(parentNode,cNode);
                        if(nodesCount > 1){
                            //获取添加后的当前节点
                            cNode = treeObj.getNodeByParam("id", cNode.id, null);
                            //根据之前设置的规则移动当前节点
                            treeObj.moveNode(targetNode, cNode, moveType);
                        }
                    }
                }
                if(data.code == -1){
                    alert("该成员含有下一代，不能删除！如需删除，请先删除其后代！");
                }
                $(".loading").hide();
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
}