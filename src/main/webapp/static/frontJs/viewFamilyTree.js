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
        $(this).text("处理中，请稍后...");
        $(this).attr('disabled',"true");
        var formData = $("#peopleForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        var treeObj = $.fn.zTree.getZTreeObj("familyTree");
        var parentNode = treeObj.getNodeByParam("id", testData.superiorId, null);
        $.ajax({
            type:'post',
            url: projectUrl + '/family/savePeople',
            dataType: 'json',
            data:testData,
            // async:false,
            success:function (data) {
                alert(data.msg);
                // var zNodes = initPeopleData(familyId);
                // initFamilyTree(zNodes,setting);
                var tPeople = data.tPeople;
                if(tPeople.peopleType == 1){//操作本族人，直接新增节点

                    //如果id存在并且大于0，则为修改族人
                    if($.trim(testData.id).length > 0 && testData.id > 0){
                        //获取原节点
                        var cNode = treeObj.getNodeByParam("id", testData.id, null);
                        //修改节点名
                        cNode["name"] = tPeople.name;
                        //更新节点信息
                        treeObj.updateNode(cNode);
                    }else{//新增
                        //创建一个新节点
                        var newNodes = {name:testData.name,pId:testData.superiorId};
                        newNodes.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
                        newNodes.open = true;
                        newNodes.peopleStatus = tPeople.peopleStatus;
                        newNodes.isSupplement = tPeople.isSupplement;
                        newNodes.id = tPeople.id;
                        //将新节点添加到当前节点集的最后面
                        treeObj.addNodes(parentNode, newNodes);
                    }

                }else{//操作配偶，修改当前节点
                    //获取原节点
                    var cNode = treeObj.getNodeByParam("id", testData.mateId, null);
                    //获取节点位置
                    // var nodeIndex =  treeObj.getNodeIndex(cNode);
                    //获要移动到的目标节点为当前节点前一节点，供最后移动节点用
                    var targetNode = cNode.getPreNode();
                    //当前子节点集的节点数
                    var nodesCount = treeObj.getNodesByParam("pId", testData.superiorId, parentNode).length;
                    //设置移动类型
                    var moveType = "next";
                    //如果当前节点的位置在最前
                    if(cNode.isFirstNode){
                        //设置要移动到的目标节点为当前节点后一节点
                        targetNode = cNode.getNextNode();
                        //设置移动类型
                        moveType = "prev";
                    }
                    //先删除原有节点
                    treeObj.removeNode(cNode);
                    //获取节点的配偶信息
                    var mateStr = cNode.mateName;
                    //修改配偶，设置配偶信息
                    if($.trim(testData.id).length > 0 && testData.id > 0){

                        var mates = mateStr.split(",");
                        //修改配偶信息
                        for(var i=0;i<mates.length;i++){
                            var mate = mates[i].split("--");
                            var mateId = mate[1];
                            var mateName = mate[0];
                            if(tPeople.id == mateId){
                                mateStr = mateStr.replace(mateName + "--" + mateId,tPeople.name + "--" + tPeople.id);
                                break;
                            }
                        }

                    }else{//新增配偶
                        var newMate = tPeople.name + "--" + tPeople.id + "--" + tPeople.peopleStatus + "--" + tPeople.isSupplement;

                        if($.trim(mateStr).length > 0 && mateStr != "undefined" && mateStr != "null"){
                            mateStr += "," + newMate;
                        }else{
                            mateStr = newMate;
                        }

                    }
                    //修改原节点的配偶信息
                    cNode["mateName"] = mateStr;
                    //将节点添加到树
                    treeObj.addNodes(parentNode,cNode);
                    if(nodesCount > 1){
                        //获取添加后的当前节点
                        cNode = treeObj.getNodeByParam("id", cNode.id, null);
                        //根据之前设置的规则移动当前节点
                        treeObj.moveNode(targetNode, cNode, moveType);
                    }

                }
                $("#addModal").modal('hide');
                $("#peopleForm")[0].reset();
                $("#savePeople").text("保 存");
                $("#savePeople").removeAttr("disabled");
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

    });


    $.when(initPeopleData(familyId)).done(function(data){
        initFamilyTree(data,setting);
    });
    // var zNodes = initPeopleData(familyId);

    // $.fn.zTree.init($("#familyTree"), setting, zNodes);

    $('#addModal').on('hidden.bs.modal', function (e) {
        $("#peopleForm")[0].reset();
        var ss = "<option value=''>无</option>";
        $("#fatherId").html(ss);
        $("#motherId").html(ss);
        $("#peopleType").val(1);
        $("#superiorId").val("");
        $("#peopleInfo").text("");
        $("#mateId").val("");
        $("#id").val("");
    });

    // $("#generation").change(function(){
    //     var generation = $(this).val();
    //     initParent(generation-1);
    // });

    $("#generation").bind("propertychange input",function(){
        var generation = $(this).val();
        if(generation <= 0){
            generation = 1;
            $(this).val(1);
        }
        $("#superiorId").val("");
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
                var responseText = data.responseText;
                if(responseText.indexOf("登出跳转页面") >= 0){
                    ajaxErrorToLogin();
                }else{
                    alert(JSON.stringify(data));
                }
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
                $("#superiorId").val("");
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
    if($.trim(mateName).length > 0){
        var mates = mateName.split(",");
        editStr += "配偶:";
        for(var i=0;i<mates.length;i++){
            var mate = mates[i].split("--");

            var mateStatus = mate[2];
            var mateSupplement = mate[3];

            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 10px' onclick=\"editPeople('" + mate[1] + "','" + treeNode.level + "','" + treeNode.id + "')\">" + mate[0] + "</a>";

            editStr += "<a id='diyBtnMate" + (i+1) + "_" +treeNode.id+ "' style='display: inline-block;margin-left: 3px;color:#ff0000' onclick=\"deletePeople('" + mate[1] + "','" + mate[0] + "',2,'" + treeNode.id + "')\">删除</a>";
        }
    }

    // if(nodeLevel < 7){
    //     editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加子女</a>";
    // }
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn1_" +treeNode.id+ "' onclick=\"addPeople(1,'"+ (nodeLevel + 1) +"','"+ treeNode.id +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加子女</a>";
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn2_" +treeNode.id+ "' onclick=\"addPeople(2,'"+ (nodeLevel + 1) +"','"+ parentId +"','" + treeNode.name + "','"+ treeNode.id +"')\">添加配偶</a>";
    editStr += "<a style='display: inline-block;margin-left: 10px' id='diyBtn3_" +treeNode.id+ "' onclick=\"deletePeople('"+ treeNode.id +"','" + treeNode.name + "',1,null)\">删除</a>";


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
    // initParent(parentGen);

    $.when(initParent(parentGen)).done(function(data){
        $("#fatherId").val(parentId);
        $("#fatherId").change();
        $("#motherId").val(parentId);
        $("#motherId").change();
        $("#superiorId").val(parentId);
    });

    $("#generation").val(generationN);
    $("#peopleType").val(peopleType);
    $("#peopleInfo").text(peopleInfo);

    $("#addModalLabel").text(modalTitle);
    $("#addModal").modal('show');

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
    // $(".loading").show();
    $.ajax({
        type:'post',
        url:projectUrl + '/family/getPeopleList',
        dataType:'json',
        // async:false,
        data:{familyId : familyId,isIndex:0},
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
            //     var mateList = ii.mateList;
            //     var mateName = "";
            //     for(var j=0;j<mateList.length;j++){
            //         var jj = mateList[j];
            //         mateName += "," + jj.name + "--" + jj.id + "--" + jj.peopleStatus + "--" + jj.isSupplement;
            //     }
            //     node.mateName = mateName.substring(1);
            //     node.icon = projectUrl + "/static/jquery/ztree/icon/head2.ico";
            //     node.open = true;
            //     node.peopleStatus = ii.peopleStatus;
            //     node.isSupplement = ii.isSupplement;
            //     zNodes[i] = node;
            //
            // }
            // var generationHtml = "";
            // for(var i=1;i<=generation;i++){
            //     generationHtml += "<option value='" + i + "'>" + i + "</option>";
            // }
            // $("#generation").html(generationHtml);

            // alert(JSON.stringify(data));
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
    editPeople(treeNode.id,treeNode.level,null);
    // initParent(treeNode.level);
    // var params = {"peopleId":treeNode.id};
    // var tPeople = getData("/consoles/getPeopleInfo",params).tPeople;
    // tPeople.birth_time = new Date(tPeople.birthTime).Format("yyyy-MM-dd hh:mm:ss");
    // tPeople.die_time =  new Date(tPeople.dieTime).Format("yyyy-MM-dd hh:mm:ss");
    // $("#peopleForm").populateForm(tPeople);
    // $("#addModalLabel").text("修改族人【" + tPeople.name + "】信息");
    // $("#addModal").modal('show');

}

function editPeople(peopleId,generation,mateId){
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

    // initParent(generation);
    $.when(initParent(generation)).done(function(data){
        $("#peopleForm").populateForm(tPeople);
        if($.trim(mateId).length > 0 && mateId > 0){
            $("#mateId").val(mateId);
        }
        $("#addModalLabel").text("族人【" + tPeople.name + "】信息");

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
    });

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

function selectTarget(obj) {
    var familyId = $(obj).val();

    var familyDesc = $(obj).find("option:selected").attr("family-desc");
    $.ajax({
        type:'post',
        url:projectUrl + '/family/getPeopleList',
        dataType:'json',
        // async:false,
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
    //删除成员将会同事删除其所有的子孙，
    if(confirm("确定要删除成员(" + peopleName + ")吗？")){
        $(".loading").show();
        var treeObj = $.fn.zTree.getZTreeObj("familyTree");
        $.ajax({
            type:'post',
            url:projectUrl + '/family/deletePeople',
            dataType:'json',
            // async:false,
            data:{peopleId : peopleId, familyId:familyId,peopleType:peopleType},
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