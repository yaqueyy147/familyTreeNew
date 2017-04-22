/**
 * Created by suyx on 2016/12/21 0021.
 */
var setting;
var includeDesc;
$(function () {

    setting = {
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
                async:false,
                success:function (data) {
                    alert(data.msg);
                    // var zNodes = initPeopleData(familyId);
                    // initFamilyTree(zNodes,setting);
                    $("#addModal").modal('hide');
                    $("#peopleForm")[0].reset();
                },
                error:function (data) {
                    alert(JSON.stringify(data));
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
        $("#id").val(0);
    });

    //同意收录
    $("#acceptIn").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + '/consoles/confirmInclude',
            dataType: 'json',
            data:{familyId:familyId},
            async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert("操作成功!");
                }
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });

    var primarySetting = {
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
        // },
        // callback:{
        //     onClick:zTreeOnClick
        // }
    };

    var zNodes = initPeopleData(familyId);
    initFamilyTree("primaryFamilyTree",primarySetting,zNodes);

    // initTargetFamily();
    $("#localBack").click(function () {
        history.back();
    });

});

function initFamilyTree(treeId,setting,zNodes) {
    $.fn.zTree.init($("#" + treeId), setting, zNodes);
}

/**
 * 设置族谱树的数据
 * @param familyId
 * @returns {Array}
 */
function initPeopleData(familyId){
    var zNodes = [];
    $.ajax({
        type:'post',
        url:projectUrl + '/family/getPeopleList',
        dataType:'json',
        async:false,
        data:{familyId : familyId},
        success:function (data) {

            var genNum = 0;
            for(var i=0;i<data.length;i++) {
                var ii = data[i];
                var node = {};
                node.id = ii.id;
                node.pId = ii.fatherId;
                node.name = ii.name;
                var mateList = ii.mateList;
                var mateName = "";
                for(var j=0;j<mateList.length;j++){
                    var jj = mateList[j];
                    mateName += "  " + jj.name;
                }
                node.mateName = mateName;
                node.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
                node.open = true;
                zNodes[i] = node;
                if(genNum < ii.generation){
                    genNum = ii.generation;
                }
            }

            // var peopleHtml = "<p style=\"margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;\">家族人数：&nbsp;" + data.length + "&nbsp;人</p>";
            // peopleHtml += "<p style=\"margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;\">家族代数：&nbsp;" + genNum + "&nbsp;代</p>";
            // $("#primaryDesc").append(peopleHtml);
            $("#peopleCount").text(data.length);
            $("#familyGenNum").text(genNum);
        }
    });
    return zNodes;

}

function initTargetFamily() {
    $.ajax({
        type:'post',
        url:projectUrl + '/family/familyMatch',
        dataType:'json',
        async:true,
        data:{familyId : familyId},
        success:function (data) {
            if(data.code == 1){
                var targetFamilyList = data.resultFamilyList;
                if(targetFamilyList && targetFamilyList.length > 0){
                    var html = "<label for='targetFamily'>请选择收录的目标族谱</label>";
                    html += "";
                    for(var i=0;i<targetFamilyList.length;i++) {
                        var ii = targetFamilyList[i];
                        html += "<option value='" + ii.id + "' family-desc='" + ii.familyDesc + "' family-addr='" + ii.province + ii.city + ii.district + "'>" + ii.familyName + "(" + ii.id + ")" + "</option>";
                    }
                    $("#targetFamily").html(html);
                }
            }
            selectTarget($("#targetFamily"));
        }
    });
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
    var targetFamilyId = $("#targetFamily").val();
    if($.trim(targetFamilyId).length <= 0){
        alert("请选择一个目标家族！");
        return;
    }
    initParent(targetFamilyId,generation);
    var params = {"peopleId":peopleId};
    var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    $("#peopleForm").populateForm(tPeople);
    $("#addModalLabel").text("修改族人【" + tPeople.name + "】信息");
    $("#familyId").val(targetFamilyId);
    includeDesc = "将【" + tPeople.name + "】收录并入家族【" + $("#targetFamily").find("option:selected").text() + "】中";
    $("#generation").parent().append("&nbsp;&nbsp;" + includeDesc);
    $("#addModal").modal('show');
}

function initParent(familyId,generation){
    $.ajax({
        type:'post',
        url:projectUrl + '/family/getParent',
        dataType:'json',
        async:false,
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


        },
        error:function (data) {
            alert(JSON.stringify(data));

        }
    });
}