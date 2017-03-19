/**
 * Created by suyx on 2016/12/21 0021.
 */
var setting;
$(function () {

    setting = {
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
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

    $("#confirmInclude").click(function () {
        //申请收录的族谱勾选的族人
        var primaryTree = $.fn.zTree.getZTreeObj("primaryFamilyTree");
        var selectPrimaryPeople = primaryTree.getCheckedNodes(true);

        //选择作为收录用的族谱勾选的族人
        var targetTree = $.fn.zTree.getZTreeObj("targetFamilyTree");
        var selectTargetPeople = targetTree.getCheckedNodes(true);
    });

    var zNodes = initPeopleData(familyId);
    initFamilyTree("primaryFamilyTree",zNodes);

    initTargetFamily();
    $("#localBack").click(function () {
        history.back();
    });

});

function initFamilyTree(treeId,zNodes) {
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
                if(ii.fatherId == 0){
                    node.open = true;
                }
                zNodes[i] = node;
            }

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

    initFamilyTree("targetFamilyTree",zNodes);
    $("#targetFamilyAddr").text("家族属地：" + familyAddr);

    if($.trim(familyDesc).length > 0){
        $("#targetFamilyDesc").text("家族描述：" + familyDesc);
    }
}