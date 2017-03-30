/**
 * Created by suyx on 2016/12/21 0021.
 */
var setting;
$(function () {

    $("#addPeople").click(function () {
        addPeople(0,1,0,'',0);
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
        },
        callback:{
            onClick:zTreeOnClick
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

    $("#savePeople").click(function () {
        if($.trim($("#generation").val()).length <= 0){
            alert("请选择是第几代人");
            return;
        }
        if($("#generation").val() > 1){
            if($("#fatherId").val() == 0){
                alert("请选择一个父亲，如果还没有父级，请先添加父级族人或者第一代族人！");
                return;
            }
        }
        var formData = $("#peopleForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        $.ajax({
            type:'post',
            url: projectUrl + '/consoles/savePeople',
            dataType: 'json',
            data:testData,
            async:false,
            success:function (data) {
                alert(data.msg);
                var zNodes = initPeopleData(familyId);
                initFamilyTree(zNodes,setting);
                $("#addModal").modal('hide');
                $("#peopleForm")[0].reset();
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });

    var zNodes = initPeopleData(familyId);
    initFamilyTree(zNodes,setting);
    // $.fn.zTree.init($("#familyTree"), setting, zNodes);

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

    $("#generation").change(function(){
        var generation = $(this).val();
        initParent(generation-1);
    });

    $("#localBack").click(function () {
        history.back();
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
            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 10px' onclick=\"editPeople('" + mate[1] + "','" + treeNode.level + "')\">" + mate[0] + "</a>";
        }
    }

    // editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加子女</a>";
    if(nodeLevel < 7){
        editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加子女</a>";
    }
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn2_" +treeNode.id+ "' onclick=\"addPeople(2,'"+ (nodeLevel + 1) +"','"+ parentId +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加配偶</a>";
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn2_" +treeNode.id+ "' onclick=\"deletePeople('"+ treeNode.id +"','" + treeNode.name + "')\">删除</a>";
    aObj.after(editStr);
}

function addPeople(type,generation,parentId,name,peopleId){
    var peopleInfo = "";
    var peopleType = 1;
    var modalTitle = "";
    var generationN = generation;//当前要添加的成员的代数
    var parentGen = generation;//当前要添加的成员的父母代数
    if(type == 0){
        peopleInfo = familyFirstName + "氏家族的族人";
        modalTitle = "添加族人";
        parentGen = Number(parentGen) - 1;
    } else if(type == 1){
        peopleInfo = name + "的子女";
        modalTitle = "添加子女";
        generationN = Number(generationN) + 1;
    } else if(type == 2){
        peopleType = 0;
        peopleInfo = name + "的配偶";
        modalTitle = "添加配偶";
        $("#mateId").val(peopleId);
        parentGen = Number(parentGen) - 1;
    }
    initParent(parentGen);
    $("#generation").val(generationN);
    $("#peopleType").val(peopleType);
    $("#peopleInfo").text(peopleInfo);
    $("#fatherId").val(parentId);
    $("#addModalLabel").text(modalTitle);
    $("#addModal").modal('show');

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
                    mateName += "," + jj.name + "--" + jj.id;
                }
                node.mateName = mateName.substring(1);
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
    var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    $("#peopleForm").populateForm(tPeople);
    $("#addModalLabel").text("修改族人【" + tPeople.name + "】信息");
    $("#addModal").modal('show');
}

function deletePeople(peopleId,peopleName) {
    if(confirm("确定要删除成员(" + peopleName + ")吗？")){
        $.ajax({
            type:'post',
            url:projectUrl + '/family/deletePeople',
            dataType:'json',
            async:false,
            data:{peopleId : peopleId},
            success:function (data) {
                if(data.code >= 1){
                    alert("删除完成!");
                    alert(familyId);
                    var zNodes = initPeopleData(familyId);
                    initFamilyTree(zNodes,setting);
                }
                if(data.code == -1){
                    alert("该成员含有下一代，不能删除！如需删除，请先删除其后代！");
                }
            }
        });
    }
}