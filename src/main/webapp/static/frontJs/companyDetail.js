/**
 * Created by suyx on 2016/12/22 0022.
 */
$(function () {
    $("p[name='photoDesc']").mouseover(function () {
        $(this).popover('show');
    });

    $("p[name='photoDesc']").mouseout(function () {
        $(this).popover('hide');
    });

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
                    imgHtml += "<p name=\"photoDesc\" style=\"text-overflow: ellipsis;white-space: nowrap;overflow: hidden\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"right\" data-content=\"" + tCompanyPhoto.photoDesc + "\">" + tCompanyPhoto.photoDesc + "</p>";
                    imgHtml += "</div></div></div>";
                    $("#companyShow").append(imgHtml);
                    $("#addPhotoModal").modal('hide');
                }
                alert(data.msg);
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });
    });

    $("#companyMoney").click(function () {
        $.ajax({
            type:'post',
            url: projectUrl + "/company/moneyList",
            dataType:'json',
            data:{"companyId" : companyId},
            success:function (data) {
                if(data){
                    var moneyList = data.moneyList;
                    var moneyHtml = "<th>序号</th><th>充值金额</th><th>充值说明</th><th>充值时间</th><th>充值人</th>";
                    for(var i=0;i<moneyList.length;i++){
                        var ii = moneyList[i];
                        moneyHtml  += "<td>" + (i+1) + "</td>";
                        moneyHtml  += "<td>" + ii.payMoney + "</td>";
                        moneyHtml  += "<td>" + ii.payDesc + "</td>";
                        moneyHtml  += "<td>" + ii.payTime + "</td>";
                        moneyHtml  += "<td>" + ii.payMan + "</td>";
                    }

                    $("#moneyTable").html(moneyHtml);
                    $("#moneyModal").modal('show');
                }
                alert(data.msg);
            },
            error:function (data) {
                alert(JSON.stringify(data));
            }
        });

    });

});