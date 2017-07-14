/**
 * Created by suyx on 2016/12/22 0022.
 */
$(function () {
    // $("p[name='photoDesc']").mouseover(function () {
    //     $(this).popover('show');
    // });
    //
    // $("p[name='photoDesc']").mouseout(function () {
    //     $(this).popover('hide');
    // });

    $("#savePhoto").click(function () {
        var formData = {};
        var postUrl = projectUrl + "/company/savePublicity";
        var testData = $("#photoForm").serializeArray();

        for (var item in testData) {
            formData["" + testData[item].name + ""] = testData[item].value;
        }
        $.ajax({
            type:'post',
            url:postUrl,
            dataType:'json',
            data:formData,
            success:function (data) {
                if(data.code == 1){
                    var tCompanyPhoto = data.tCompanyPhoto;

                    var imgHtml = "<div class=\"col-sm-6 col-md-2\"><div class=\"thumbnail\">";
                    imgHtml += "<a href=\"javascript:void(0)\">";
                    imgHtml += "<img src=\"" + tCompanyPhoto.publicityPhoto + "\" class=\"img-thumbnail\"/></a>";
                    imgHtml += "<div class=\"caption\">";
                    imgHtml += "<p name=\"photoDesc\" onmouseover='pPopover(this,1)' onmouseout='pPopover(this,2)' style=\"text-overflow: ellipsis;white-space: nowrap;overflow: hidden\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"right\" data-content=\"" + tCompanyPhoto.photoDesc + "\">" + tCompanyPhoto.photoDesc + "</p>";
                    imgHtml += "</div></div></div>";
                    $("#companyShow").append(imgHtml);
                    $("#addPhotoModal").modal('hide');
                }
                alert(data.msg);
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

    $("#saveIntro").click(function () {
        var companyDesc = CKEDITOR.instances.companyDesc.getData();
        var id = $("#introduceId").val();
        if($.trim(id).length <= 0){
            id = 0;
        }
        $.ajax({
            type:'post',
            url:projectUrl + "/company/saveIntro",
            dataType:'json',
            data:{companyId:companyId, companyIntroduce: companyDesc, id:id},
            success:function (data) {
                if(data.code >= 1){
                    $("#introduceId").val(data.tCompanyIntroduce.id);
                    $("#introBtn").text("修改公司介绍");
                    $("#companyDesc").val(data.tCompanyIntroduce.companyIntroduce);
                    $("#companyShow").html(data.tCompanyIntroduce.companyIntroduce);

                }
                alert(data.msg);
                $("#addIntroModal").modal('hide');
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

    $("#cancelIntro").on({
        click:function () {
            // var primaryIntro = $("#introduceA").html();
            $.ajax({
                type:'post',
                url:projectUrl + "/company/getIntro",
                dataType:'json',
                data:{companyId:companyId},
                // async:false,
                success:function (data) {
                    if(data){
                        CKEDITOR.instances.companyDesc.setData(data.companyIntroduce);
                        // $("#companyDesc").val(data.companyIntroduce);
                        $("#addIntroModal").modal('hide');
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

    $("#companyMoney").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + "/company/moneyList",
            dataType:'json',
            data:{"companyId" : companyId},
            success:function (data) {
                if(data){
                    var moneyList = data.dataList;
                    var moneyHtml = "<tr><th>序号</th><th>充值金额</th><th>充值说明</th><th>充值时间</th><th>充值人</th></tr><tr>";
                    for(var i=0;i<moneyList.length;i++){
                        var ii = moneyList[i];
                        moneyHtml  += "<td>" + (i+1) + "</td>";
                        moneyHtml  += "<td>" + ii.payMoney + "</td>";
                        moneyHtml  += "<td>" + ii.payDesc + "</td>";
                        moneyHtml  += "<td>" + ii.payTime + "</td>";
                        moneyHtml  += "<td>" + ii.payMan + "</td>";
                    }
                    moneyHtml += "</tr>";
                    $("#moneyTable").html(moneyHtml);
                    $("#moneyModal").modal('show');
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

});