<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/18
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人信息</title>
    <%@include file="common/commonCss.html"%>
    <link href="<%=request.getContextPath()%>/static/css/fronts/personalInfo.css" rel="stylesheet" type="text/css" />
    <style>
        html,body {
            height: 100%;
        }
        body{
            background: url("/static/images/bag.jpg") no-repeat;
            filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
            -moz-background-size:100% 100%;
            background-size:100% 100%;
        }
        .bbtt {
            margin-bottom: 30px;
        }

    </style>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container">
<ul class="nav nav-tabs" style="margin-top: 30px">
    <li role="presentation" class="active"><a href="#">个人信息</a></li>
    <li role="presentation"><a href="#">我的族谱</a></li>

</ul>
<div id="userDetail" class="tab-content infoBox">
    <div class="leftInfo infoDetail">
        <div class="col-sm-10 col-md-10 col-md-offset-1">
            <div class="thumbnail">
                <a href="javascript:void(0)">
                    <c:if test="${userInfo.userPhoto == null}">
                        <img src="<%=request.getContextPath()%>/static/images/defaultMan.png"  class="img-thumbnail" />
                    </c:if>
                    <c:if test="${userInfo.userPhoto != null}">
                        <img src="${userInfo.userPhoto}"  class="img-thumbnail" />
                    </c:if>
                </a>
                <%--<img data-src="holder.js/300x300" alt="...">--%>
                <div class="caption">
                    <h3>${userInfo.userName}</h3>

                    <p>${userInfo.province}${userInfo.city}${userInfo.district}</p>
                    <p>
                        <button type="button" class="btn btn-primary">申请志愿者</button>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="regedit-content tab-content rightInfo infoDetail">
        <div role="tabpanel" class="form tab-pane active" id="personalRegedit">
            <form id="pwesonalForm" action="" method="post">
                <div class="form-group col-xs-8 form-actions">
                    <input class="form-control" id="userName" name="userName" value="${userInfo.userName}" type="text" readonly />
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                    <input class="form-control" id="nickName" name="nickName" value="${userInfo.nickName}" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px;display: none">
                    <input class="form-control" id="password" name="password" value="${userInfo.password}" type="password" />
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                    <input class="form-control" id="idCard" name="idCard" value="${userInfo.idCard}" type="text" />
                </div>
                <div class="form-group col-xs-12 form-actions" style="margin-top: 15px">
                    <div data-toggle="distpicker">
                        <select id="province" name="province" data-province="---- 选择省 ----"></select>
                        <select id="city" name="city" data-city="---- 选择市 ----"></select>
                        <select id="district" name="district" data-district="---- 选择区 ----"></select>
                    </div>
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                    <input class="form-control" id="detailAttr" name="detailAttr" value="${userInfo.detailAddr}" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                    <input class="form-control" id="phone" name="phone" value="${userInfo.phone}" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                    <input class="form-control" id="wechart" name="wechart" value="${userInfo.wechart}" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                    <input class="form-control" id="qqNum" name="qqNum" value="${userInfo.qqNum}" type="text" />
                </div>
                <div class="form-group col-xs-9 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <button class="btn btn-primary bbtt" id="regedit" type="button">保存</button>
                </div>

            </form>
        </div>

    </div>
</div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/personalInfo.js"></script>
<script type="text/javascript">
    var userInfo = ${userInfo};
    $(function () {

        $("#province").val(userInfo.province);
        $("#province").change();
        $("#city").val(userInfo.city);
        $("#city").change();
        $("#district").val(userInfo.district);
        $("#district").change();

//        $('#imgFile').uploadify({
//            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
//            'uploader'      : projectUrl + '/upload/uploadImg',
//            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
//            'auto'          : true,
//            "formData"      : {targetFile : '/static/upload/companyImg'},
//            'queueID'       : 'progress_bar',
//            'fileObjName'   : 'uploadFile',
//            "buttonCursor"  : "hand",
//            "buttonText"    : "选择图片",
//            "buttonImage"   : projectUrl + "/static/images/defaultUpload.gif",
//            "buttonClass"   : "img-thumbnail",
//            "height"         : "140",
//            'fileDesc'      : '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
//            'fileExt'       : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
//            'onUploadSuccess' : function(file, data, response) {
//                var result = eval('(' + data + ')');
//                var imgPath = result.filePath;
//                $("#result_img").attr('src',imgPath);
//                $("#result_img").show();
//                $("#imgFile").hide();
//                $("#companyPhoto").attr('value',imgPath);
//                $("#show_img").mouseover(function(){
//                    $("#result_img").attr('src',projectUrl + "/static/images/deleteImg.png");
//                });
//                $("#show_img").mouseout(function(){
//                    $("#result_img").attr('src',imgPath);
//                });
//                $("#result_img").click(function(){
//                    $("#result_img").hide();
//                    $("#imgFile").show();
//                    $("#companyPhoto").removeAttr('value');
//                    $("#show_img").unbind('mouseover');
//                    $("#show_img").unbind('mouseout');
//
//                });
//            },
//            onUploadError:function (file, errorCode, errorMsg, errorString) {
//                alert("error-->" + errorString);
//            }
//        });

    });

</script>
</body>
</html>
