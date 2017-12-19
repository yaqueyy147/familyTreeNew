/**
 * Created by suyx on 2016/12/21 0021.
 */
var setting;
$(document).ready(function () {



    setting = {
        view: {
            showLine:false,
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

    $.when(initPeopleData(familyId)).done(function(data){
        initFamilyTree(data,setting);

        var totalWidth = 0;

        var topTree = $("#familyTree > li");
        $.each(topTree,function () {

            var liWidth = $(this).outerWidth();
            if(liWidth*1 < 1000){
                liWidth = liWidth*1 + 100;
            }
            totalWidth = totalWidth*1 + liWidth*1;

        });
        $("#familyTreeDiv").attr("style","width:" + (totalWidth) + "px");

    });
    // var zNodes = initPeopleData(familyId);
    // initFamilyTree(zNodes,setting);

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
    var dieAddr = treeNode.dieAddr;
    if($.trim(dieAddr).length > 0 && dieAddr.toLowerCase() != "null" && dieAddr.toLowerCase() != "undefined"){
        editStr += "<br/><span id='diyBtnMate_dieAddr_" +treeNode.id+ "' style='display: inline-block;margin-left: 10px' href='javascript:void 0;'>卒葬地点:" + dieAddr + "</span>";
    }
    if($.trim(mateName).length > 0){
        var mates = mateName.split(",");
        editStr += "<br/>配偶:";
        for(var i=0;i<mates.length;i++){
            var mate = mates[i].split("--");
            editStr += "<br/><span id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;' href='javascript:void 0;'>" + mate[0];
            if(mate.length > 1){
                var mateDieAddr = mate[1];
                if($.trim(mateDieAddr).length > 0  && mateDieAddr.toLowerCase() != "null" && mateDieAddr.toLowerCase() != "undefined"){
                    editStr += "(卒葬地点:" + mateDieAddr + ")";
                }

            }
            editStr += "</span>";
        }
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
    $(".loading").show();
    $.ajax({
        type:'post',
        url:projectUrl + '/consoles/peopleInfo4Print',
        dataType:'json',
        // async:false,
        data:{familyId : familyId, beginGen : beginGen, endGen : endGen},
        success:function (data) {
            // var generation = 1;
            // for(var i=0;i<data.length;i++) {
            //     var ii = data[i];
            //     // if(ii.generation > generation){
            //     //     generation = ii.generation;
            //     // }
            //     var node = {};
            //     node.id = ii.id;
            //     node.pId = ii.superiorId;
            //     node.name = ii.name;
            //     node.dieAddr = ii.dieAddr;
            //     var mateList = ii.mateList;
            //     var mateName = "";
            //     for(var j=0;j<mateList.length;j++){
            //         var jj = mateList[j];
            //         mateName += "," + jj.name + "--" + jj.dieAddr;
            //     }
            //     node.mateName = mateName.substring(1);
            //     node.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
            //     node.open = true;
            //     zNodes[i] = node;
            //
            // }
            // var generationHtml = "";
            // for(var i=1;i<=generation;i++){
            //     generationHtml += "<option value='" + i + "'>" + i + "</option>";
            // }
            // $("#generation").html(generationHtml);
            defer.resolve(data);
            $(".loading").hide();
        }

    });
    return defer.promise();

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
    initParent(generation);
    var params = {"peopleId":peopleId};
    var tPeople = getData("/family/getPeopleInfo",params).tPeople;
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
    $("#addModalLabel").text("修改族人【" + tPeople.name + "】信息");

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