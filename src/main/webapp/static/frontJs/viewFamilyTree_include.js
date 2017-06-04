/**
 * Created by suyx on 2016/12/21 0021.
 */
var targetFamily;
var targetFamilyPeople;
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
        bootcssVer:3
    });

    $("#die_time").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        language: 'zh-CN',
        autoclose:true,
        bootcssVer:3
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

        $(this).text("处理中，请稍后...");
        $(this).attr('disabled',"true");

        if($.trim($("#generation").val()).length <= 0){
            alert("请输入是第几代人");
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
            url: projectUrl + '/family/savePeople_include',
            dataType: 'json',
            data:testData,
            async:false,
            success:function (data) {
                alert(data.msg);
                var zNodes = initPeopleData(familyId);
                initFamilyTree(zNodes,setting);
                $("#addModal").modal('hide');
                $("#peopleForm")[0].reset();
                $("#savePeople").text("保 存");
                $("#savePeople").removeAttr("disabled");
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
        $("#superiorId").val(0);
        $("#peopleInfo").text("");
        $("#mateId").val("");
        $("#id").val(0);
    });

    // $("#generation").change(function(){
    //     var generation = $(this).val();
    //     initParent(generation-1);
    // });

    $("#generation").bind("propertychange input",function(){
        var generation = $(this).val();
        $("#superiorId").val(0);
        initParent(generation-1);
    });

    // $("#toInclude").click(function () {
    //     loadTargetFamily(familyId);
    //
    // });

    $("#toInclude").click(function () {
        if($(this).text().indexOf("已申请") >= 0){
            alert("已申请收录，不能再次申请！");
            return;
        }
        var params = {};
        params.primaryFamilyId = familyId;
        $.ajax({
            type:'post',
            url: projectUrl + '/family/familyMerge',
            dataType: 'json',
            data:params,
            async:true,
            success:function (data) {
                if(data.code >= 1){
                    alert("申请成功!");
                    $("#includeModal").modal('hide');
                    $("#includeForm")[0].reset();
                    $("#toInclude").text("已申请，待审核");
                }
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });

    $("#toPrint").click(function () {
        var beginGen = $("#beginGen").val();

        var endGen = $("#endGen").val();
        var maxGen = $("#maxGen").val();

        if(beginGen*1 < 1){
            $("#beginGen").val(1);
            alert("最小是能从第一代开始!");
            return;
        }

        if(beginGen*1 > endGen*1){
            alert("开始代不能大于结束代!");
            return;
        }

        if(endGen*1 > maxGen*1){
            $("#endGen").val(maxGen);
            alert("当前族谱最大只能到" + maxGen + "结束!");
            return;
        }
        $("#printGenForm").attr("action",projectUrl + "/family/print");
        $("#printGenForm").submit();
    });

    $('#printModal').on('shown.bs.modal', function (e) {
        $("#beginGen").val(1);
        $("#endGen").val(maxGeneration);
        $("#isAddIntroOk").prop("checked",true);
    });

    //修改父亲的时候同时修改上级id
    $("#fatherId").on({
        change:function () {
            var fatherType = $('#fatherId option:selected').attr("people-type");
            //如果选择的父亲是本族人，则修改上级id为选择的父亲Id
            if(fatherType == 1){
                $("#superiorId").val($(this).val());
            }else{
                $("#superiorId").val(0);
            }
        }
    });
    //修改母亲的时候同时修改上级id
    $("#motherId").on({
        change:function () {
            var motherType = $('#motherId option:selected').attr("people-type");
            var fatherType = $('#fatherId option:selected').attr("people-type");
            //如果选择的母亲是本族人，并且选中的父亲不是本族人，则修改上级id为选择的母亲Id
            if(motherType == 1 && fatherType != 1){
                $("#superiorId").val($(this).val());
            }else{//否则  以父亲ID作为上级ID
                $("#superiorId").val($("#fatherId").val());
            }
        }
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
    if(treeNode.peopleStatus == 5 && treeNode.isSupplement == 1){
        editStr += "<span style='color:#CC2222'>(补录添加待审核)</span>";
    }
    if(treeNode.peopleStatus == 51 && treeNode.isSupplement == 1){
        editStr += "<span style='color:#CC2222'>(补录修改待审核)</span>";
    }
    if(treeNode.peopleStatus == 52 && treeNode.isSupplement == 1){
        editStr += "<span style='color:#CC2222'>(补录删除待审核)</span>";
    }
    if($.trim(mateName).length > 0){
        var mates = mateName.split(",");
        editStr += "配偶:";
        for(var i=0;i<mates.length;i++){
            var mate = mates[i].split("--");

            var mateStatus = mate[2];
            var mateSupplement = mate[3];

            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 10px' onclick=\"editPeople('" + mate[1] + "','" + treeNode.level + "')\">" + mate[0];// + "</a>"
            if(mateStatus == 5 && mateSupplement == 1){
                editStr += "<span style='color:#CC2222'>(补录添加待审核)</span>";
            }
            if(mateStatus == 51 && mateSupplement == 1){
                editStr += "<span style='color:#CC2222'>(补录修改待审核)</span>";
            }
            if(mateStatus == 52 && mateSupplement == 1){
                editStr += "<span style='color:#CC2222'>(补录删除待审核)</span>";
            }
            editStr += "</a>";
            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 3px;color:#ff0000' onclick=\"deletePeople('" + mate[1] + "','" + mate[0] + "',2)\">删除</a>";
        }
    }

    // if(nodeLevel < 7){
    //     editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加子女</a>";
    // }
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加子女</a>";
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn2_" +treeNode.id+ "' onclick=\"addPeople(2,'"+ (nodeLevel + 1) +"','"+ parentId +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加配偶</a>";
    editStr += "<a style='display: inline-block;margin-left: 10px;color:#ff0000' id='diyBtn3_" +treeNode.id+ "' onclick=\"deletePeople('"+ treeNode.id +"','" + treeNode.name + "',1)\">删除</a>";

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
    $("#fatherId").change();
    $("#motherId").val(parentId);
    $("#motherId").change();
    $("#superiorId").val(parentId);
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
            var fatherHtml = "<option value='0'>无</option>";
            var motherHtml = "<option value='0'>无</option>";
            if(fathers.length > 0){
                for(var i=0;i<fathers.length;i++){
                    var ii = fathers[i];
                    fatherHtml += "<option value='" + ii.id + "' people-type='" + ii.peopleType + "'>" + ii.name + "(" + ii.id + ")</option>";
                }
            }
            $("#fatherId").html(fatherHtml);

            if(mothers.length > 0){
                for(var i=0;i<mothers.length;i++){
                    var ii = mothers[i];
                    motherHtml += "<option value='" + ii.id + "'people-type='" + ii.peopleType + "'>" + ii.name + "(" + ii.id + ")</option>";
                }
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
        data:{familyId : familyId,isIndex:0},
        success:function (data) {
            // var generation = 1;
            for(var i=0;i<data.length;i++) {
                var ii = data[i];
                // if(ii.generation > generation){
                //     generation = ii.generation;
                // }
                var node = {};
                node.id = ii.id;
                node.pId = ii.superiorId;
                node.name = ii.name;
                var mateList = ii.mateList;
                var mateName = "";
                for(var j=0;j<mateList.length;j++){
                    var jj = mateList[j];
                    mateName += "," + jj.name + "--" + jj.id + "--" + jj.peopleStatus + "--" + jj.isSupplement;
                }
                node.mateName = mateName.substring(1);
                node.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
                node.open = true;
                node.peopleStatus = ii.peopleStatus;
                node.isSupplement = ii.isSupplement;
                zNodes[i] = node;

            }
            // var generationHtml = "";
            // for(var i=1;i<=generation;i++){
            //     generationHtml += "<option value='" + i + "'>" + i + "</option>";
            // }
            // $("#generation").html(generationHtml);
            $(".loading").hide();
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
    // $("#fatherId").val(tPeople.fatherId);
    // $("#motherId").val(tPeople.motherId);
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

function loadTargetFamily(familyId){

    var peopleCC = $.fn.zTree.getZTreeObj("familyTree").getNodes();;

    if(peopleCC.length <= 0){
        alert("您的家族还没有族人，不能申请收录!");
        return;
    }

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
                    html += "<select class='form-control' id='targetFamily' name='targetFamily' onchange='selectTarget(this)'>";
                    for(var i=0;i<targetFamilyList.length;i++) {
                        var ii = targetFamilyList[i];

                        html += "<option value='" + ii.id + "' family-desc='" + ii.familyDesc + "'>" + ii.familyName + "(" + ii.id + ")" + "</option>";
                    }
                    html += "</select>";
                    $("#targetFamilyDiv").html(html);
                    $("#forInclude").show();
                }else{
                    $("#targetFamilyDiv").html("没有与您族谱相匹配的其他族谱，无法申请收录！");
                    $("#forInclude").hide();
                }
            }else if(data.code == -1){
                $("#targetFamilyDiv").html("您的家族还没有族人，无法申请收录！");
                $("#forInclude").hide();
            }
            else if(data.code == -2){
                $("#targetFamilyDiv").html("您至少需要有两代人才能找到与您族谱相匹配的其他族谱，无法申请收录！");
                $("#forInclude").hide();
            }
            $("#includeModal").modal("show");
            selectTarget($("#targetFamilyDiv select"));
        }
    });
}

function selectTarget(obj) {
    var familyId = $(obj).val();

    var familyDesc = $(obj).find("option:selected").attr("family-desc");
    $.ajax({
        type:'post',
        url:projectUrl + '/family/getPeopleList',
        dataType:'json',
        async:false,
        data:{familyId : familyId,orderBy:"order by generation asc",limit:"limit 0,2"},
        success:function (data) {
            var html = "<p>选择的目标族谱前两代人：";
            for(var i=0;i<data.length;i++){
                var ii = data[i];
                html += ii.name + ",";
            }
            html = html.substring(0,html.length - 1);
            html += "</p>";
            if($.trim(familyDesc).length > 0){
                html += "<p>选择的家族描述：" + familyDesc + "</p>";
            }

            $(obj).parent().append(html);
        }
    });
}

function deletePeople(peopleId,peopleName,peopleType) {
    if(confirm("确定要删除成员(" + peopleName + ")吗？")){
        $.ajax({
            type:'post',
            url:projectUrl + '/family/deletePeople_include',
            dataType:'json',
            async:false,
            data:{peopleId : peopleId, familyId:familyId,peopleType:peopleType},
            success:function (data) {
                if(data.code >= 1){
                    alert("删除完成!");
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