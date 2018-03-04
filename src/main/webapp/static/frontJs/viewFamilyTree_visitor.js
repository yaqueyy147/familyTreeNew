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

    $.when(initPeopleData(familyId)).done(function(data){
        initFamilyTree(data,setting);
    });
    // var zNodes = initPeopleData(familyId);
    // initFamilyTree(zNodes,setting);
    // $.fn.zTree.init($("#familyTree"), setting, zNodes);

    $("#generation").bind("propertychange input",function(){
        var generation = $(this).val();
        if(generation <= 0){
            generation = 1;
            $(this).val(1);
        }
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
        var mates = mateName.split(",");
        editStr += "配偶:";
        for(var i=0;i<mates.length;i++){
            var mate = mates[i].split("--");
            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 5px' onclick=\"editPeople('" + mate[1] + "','" + treeNode.level + "')\">" + mate[0] + "</a>";
        }
    }

    aObj.after(editStr);
}

function initParent(generation){
    var defer = $.Deferred();
    $.ajax({
        type:'post',
        url:projectUrl + '/family/getParent',
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
        url:projectUrl + '/family/getPeopleList4Index',
        dataType:'json',
        // async:false,
        data:{familyId : familyId,isIndex:1},
        success:function (data) {

            // for(var i=0;i<data.length;i++) {
            //     var ii = data[i];
            //     var node = {};
            //     node.id = ii.id;
            //     node.pId = ii.superiorId;
            //     node.name = ii.name;
            //     var mateList = ii.mateList;
            //     var mateName = "";
            //     for(var j=0;j<mateList.length;j++){
            //         var jj = mateList[j];
            //         mateName += "," + jj.name + "--" + jj.id + "--" + jj.peopleStatus + "--" + jj.isSupplement;
            //     }
            //     node.mateName = mateName;
            //     node.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
            //     node.open = true;
            //     zNodes[i] = node;
            // }
            defer.resolve(data);
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
    // initParent(generation);
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
    $.when(initParent(generation)).done(function(data){
        $("#peopleForm").populateForm(tPeople);
    });

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

    // function zTreeOnClick(event, treeId, treeNode) {
    //     initParent(treeNode.level);
    //     var params = {"peopleId":treeNode.id};
    //     var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    //     tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    //     tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    //     $("#peopleForm").populateForm(tPeople);
    //     $("#addModalLabel").text("族人【" + tPeople.name + "】信息");
    //     $("#addModal").modal('show');
    //
    //     var imgPath = tPeople.photoUrl;
    //     $("#result_img").attr('src',imgPath);
    // }