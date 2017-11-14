/**
 * Created by suyx on 2017/1/5 0005.
 */
$(function () {

    $("#province").val("");
    $("#searchBtn").click(function () {

        meritocratTablePageChange(1);
    });

    var params = {};
    meritocratTablePageChange(1);

});

function meritocratTablePageChange(pageNo) {

    var params = {};
    params.pageNo = pageNo;
    params.meritocrat_name = $("#meritocratName").val();
    params.meritocrat_area = $("#province").val();
    params.meritocrat_attr_id = $("#meritocratAttrId").val();
    params.tableId = "meritocratTable";

    $.ajax({
        type:'post',
        url: projectUrl + '/family/meritocratList',
        dataType: 'json',
        data:params,
        // async:false,
        success:function (data) {
            var meritocratList = data.meritocratList;
            var meritocrat = "";
            if(meritocratList.length > 0){
                for(var i=0;i<meritocratList.length;i++){
                    var ii = meritocratList[i];
                    meritocrat += "<tr>";
                    meritocrat += "<td style='width: 100px'><img src='" + ii.photo + "' class='img-thumbnail' style=\"width: 100%;\"/></td>"
                    meritocrat += "<td style='position: relative'><p></p><span style='font-weight: bold'>" + ii.meritocrat_name + "：</span>";
                    meritocrat += ii.meritocrat_desc + "</p><div style='position: absolute;bottom: 0%'>";
                    if($.trim(ii.post_code).length > 0){
                        meritocrat += "邮编：" + ii.post_code + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    if($.trim(ii.phone).length > 0) {
                        meritocrat += "联系电话：" + ii.phone + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    if($.trim(ii.fax).length > 0) {
                        meritocrat += "传真：" + ii.fax + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    if($.trim(ii.meritocrat_addr).length > 0){
                        meritocrat += "详细地址：" + ii.meritocrat_addr;
                    }
                    meritocrat += "</div></td>";
                    meritocrat += "</tr>";
                }
            }
            $("#meritocratTable tbody").html(meritocrat);
            $("#pageChanger").html(data.pageChanger);
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