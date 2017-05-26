/**
 * Created by suyx on 2016/12/21 0021.
 */
$(function () {

    $("#addPeople").click(function () {
        addPeople(0,1,0,'',0);
    });

    var setting = {
        view: {
            showLine:true,
            addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback:{
            onClick:zTreeOnClick
        }
    };

    var zNodes = initPeopleData(familyId);
    initFamilyTree(zNodes,setting);
    // $.fn.zTree.init($("#familyTree"), setting, zNodes);

    $("#generation").bind("propertychange input",function(){
        var generation = $(this).val();
        initParent(generation-1);
    });

});

function initFamilyTree(zNodes,setting) {
    $.fn.zTree.init($("#familyTree"), setting, zNodes);
}

function addDiyDom(treeId, treeNode) {
    var IDMark_A = "_a";
    var aObj = $("#" + treeNode.tId + IDMark_A);

    var nodeLevel = treeNode.level;
    var parentId = 0;
    if(nodeLevel > 0){
        parentId = treeNode.pId;
    }
    var mateName = treeNode.mateName;
    var editStr = "";
    if($.trim(mateName).length > 0){
        editStr += "  配偶:" + treeNode.mateName;
    }

    aObj.after(editStr);
}

function initParent(generation){
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

/**
 * 设置族谱树的数据
 * @param familyId
 * @returns {Array}
 */
function initPeopleData(familyId){
    var zNodes = [];
    $(".loading").show();
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
                node.pId = ii.superiorId;
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
            }
            $(".loading").hide();
        }
    });
    return zNodes;

}

function zTreeOnClick(event, treeId, treeNode) {
    initParent(treeNode.level);
    var params = {"peopleId":treeNode.id};
    var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    $("#peopleForm").populateForm(tPeople);
    $("#addModalLabel").text("族人【" + tPeople.name + "】信息");
    $("#addModal").modal('show');

    var imgPath = tPeople.photoUrl;
    $("#result_img").attr('src',imgPath);
}