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
        url:projectUrl + '/consoles/getPeopleListJoint',
        dataType:'json',
        // async:false,
        data:{familyId : familyId,isIndex:0},
        success:function (data) {
            defer.resolve(data);
            $("#peopleCount").text(data.length);
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
    $(".loading").show();
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