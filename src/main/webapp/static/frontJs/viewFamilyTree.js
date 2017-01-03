/**
 * Created by suyx on 2016/12/21 0021.
 */
$(function () {

    $("#addPeople").click(function () {
        addPeople(0,1,0);
    });

    var setting = {
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

        var formData = $("#peopleForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        $.ajax({
            type:'post',
            url: projectUrl + '/family/savePeople',
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
    });

    $("#generation").bind("propertychange input",function(){
        var generation = $(this).val();
        initParent(generation);
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
        editStr += "  配偶" + treeNode.mateName;
    }

    editStr += "<a id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "')\">添加子女</a>";
    editStr += "<a id='diyBtn2_" +treeNode.id+ "' onclick=\"addPeople(2,'"+ (nodeLevel + 1) +"','"+ parentId +"','" + treeNode.name + "')\">添加配偶</a>";
    aObj.after(editStr);
}

function addPeople(type,generation,parentId,name){
    var peopleInfo = "";
    var peopleType = 1;
    var modalTitle = "";
    var generationN = generation;
    if(type == 0){
        peopleInfo = familyFirstName + "氏家族的族人";
        modalTitle = "添加族人";
    } else if(type == 1){
        peopleInfo = name + "的子女";
        modalTitle = "添加子女";

        // generationN = Number(generationN) + 1;
    } else if(type == 2){
        peopleType = 0;
        peopleInfo = name + "的配偶";
        modalTitle = "添加配偶";
    }
    initParent(generation);
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