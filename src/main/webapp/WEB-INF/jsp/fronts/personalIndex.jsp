
<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/22 0022
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>世界何氏族谱--个人族谱详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
    <style rel="stylesheet">
        body{
            width:100%;
            <%--background: url("<%=request.getContextPath()%>/static/images/bag2.jpg") no-repeat;--%>
            /*filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";*/
            /*-moz-background-size:100% 100%;*/
            /*background-size:100% 100%;*/
        }
        .caption p{
            font-size: 12px;
            margin:0px !important;
        }
        .familyImgFF{
            display: block;
            width: 100%;
            height: 155px !important;
        }
    </style>
</head>
<body>
<%--<%@include file="common/header.jsp" %>--%>
<div class="container-fluid" style="margin-top: 20px;margin-bottom: 10px;">
    <a class="btn btn-primary" href="#addFamilyModal" data-toggle="modal" data-target="#addFamilyModal">创建族谱</a>
        <div id="familyShow" class="row">
        <c:forEach var="family" items="${familyList}">

            <div class="col-sm-6 col-md-2">
                <div class="thumbnail">

                    <a href="javascript:void(0)" onclick="viewFamily('${family.id}','${family.visitStatus}','${family.visitPassword}')" style="float: none;width: 100%;">
                        <img class="familyImgFF" src="${family.photoUrl}" class="img-thumbnail"/></a>
                    <%--<img data-src="holder.js/300x300" alt="...">--%>
                    <div class="caption">
                        <h6><a href="javascript:void 0;" onclick="toEdit('${family.id}')">${family.familyFirstName}（${family.id}）</a></h6>
                        <%--<h6><a href="javascript:void 0;" onclick="toEdit('${family.id}')">世界何氏族谱（${family.id}）</a></h6>--%>
                        <p>家族人数：${family.zspeopleCount}&nbsp;/&nbsp;${family.peopleCount}人</p>
                        <p>状态：
                            <c:if test="${family.visitStatus == 0}">加密</c:if>
                            <c:if test="${family.visitStatus == 1}">开放</c:if>
                        </p>
                        <p style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden">${family.familyName}</p>
                        <p name="familyDesc" onmouseover="pPopover(this,1)" onmouseout="pPopover(this,2)" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden" data-container="body" data-toggle="popover" data-placement="right" data-content="${family.familyDesc}">
                            ${family.familyDesc}
                        </p>
                    </div>
                </div>
            </div>

        </c:forEach>
        </div>
</div>
<!-- 添加族谱 Modal -->
<div class="modal fade" id="addFamilyModal" tabindex="-1" role="dialog" aria-labelledby="addFamilyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addFamilyModalLabel">创建族谱</h4>
            </div>
            <div class="modal-body">
                <!-- 族谱信息页面 -->
                <div class="tab-content">
                    <form class="form-horizontal" id="familyForm" action="" method="post">
                        <input type="hidden" id="familyId" name="id" value="0" />
                        <div class="form-group">
                            <label for="familyFirstName" class="col-sm-2 control-label">家族姓氏</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="familyFirstName" name="familyFirstName" value="世界何氏族谱">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="familyName" class="col-sm-2 control-label">家谱/族谱名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="familyName" name="familyName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="familyName" class="col-sm-2 control-label">访问状态</label>
                            <div class="col-sm-8">
                                <label><input type="radio" name="visitStatus" value="1" checked />&nbsp;开放</label>
                                <label><input type="radio" name="visitStatus" value="0" />&nbsp;加密</label>
                            </div>
                        </div>
                        <div class="form-group" id="visitPasswordDiv" style="display: none">
                            <label for="familyName" class="col-sm-2 control-label">访问密码</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="visitPassword" name="visitPassword">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="familyName" class="col-sm-2 control-label">家族属地</label>
                            <div class="col-sm-10" data-toggle="distpicker">
                                <select id="province" name="province" data-province="---- 选择省 ----"></select>
                                <select id="city" name="city" data-city="---- 选择市 ----"></select>
                                <select id="district" name="district" data-district="---- 选择区 ----"></select>
                                <%--<select class="form-control" name="familyArea">--%>
                                    <%--<option value="1">大陆</option>--%>
                                    <%--<option value="2">香港</option>--%>
                                    <%--<option value="3">台湾</option>--%>
                                    <%--<option value="4">澳门</option>--%>
                                    <%--<option value="5">其他</option>--%>
                                <%--</select>--%>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="familyName" class="col-sm-2 control-label">家族简介</label>
                            <div class="col-sm-10">
                                <textarea cols="30" rows="5" class="form-control" id="familyDesc" name="familyDesc" style="resize: none"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">展示图片</label>
                            <div class="col-sm-10">
                                <div id="progress_bar" style="display: none"></div>
                                <input id="photoUrl" name="photoUrl" type="hidden" />
                                <div class="row">
                                    <div class="col-xs-10 col-md-6">
                                        <input type="file" name="imgFile" id="imgFile" />
                                        <a id="show_img"><img style="display: none;" id="result_img" class="img-thumbnail" height="128px" width="128px" /></a>
                                    </div>
                                </div>
                                <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="saveFamily">提 交</button>
                <button class="btn btn-default" id="closeModal" data-dismiss="modal">取 消</button>
            </div>
        </div>
    </div>
</div>
<!-- 族谱密码 Modal -->
<div class="modal fade" id="visitPasswordModal" tabindex="-1" role="dialog" aria-labelledby="visitPasswordModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">您需要输入密码才能访问该族谱</h4>
            </div>
            <div class="modal-body">
                <!-- 族人信息页面 -->
                <div class="tab-content">
                    <input type="password" class="form-control" id="password" name="password" placeholder="访问密码">
                    <input type="hidden" class="form-control" id="passwordPre" name="passwordPre">
                    <input type="hidden" class="form-control" id="visitFamilyId" name="visitFamilyId">
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="checkPassword">提 交</button>
                <button class="btn btn-default" id="closePassword" data-dismiss="modal">取 消</button>
            </div>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%--<%@include file="common/footer.jsp" %>--%>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/personalIndex.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/jquery.MD5.js"></script>
<script type="text/javascript">
    var winHeight = $(window).height();
    $(function () {
        $("body").attr("style","height:" + (winHeight - 50) + "px");
        setTimeout(function() {
            $('#imgFile').uploadify({
                'swf': projectUrl + '/static/uploadify/uploadify.swf',
                'uploader': projectUrl + '/upload/uploadImg',
                'cancelImg': projectUrl + '/static/uploadify/cancel.png',
                'auto': true,
                "formData": {targetFile: '/upload/familyImg'},
                'queueID': 'progress_bar',
                'fileObjName': 'uploadFile',
                "buttonCursor": "hand",
                "buttonText": "选择图片",
                'fileDesc': '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
                'fileExt': '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
                'onUploadSuccess': function (file, data, response) {
                    var result = eval('(' + data + ')');
                    var imgPath = result.filePath;
                    $("#result_img").attr('src', imgPath);
                    $("#result_img").show();
                    $("#imgFile").hide();
                    $("#photoUrl").attr('value', imgPath);
                    $("#show_img").mouseover(function () {
                        $("#result_img").attr('src', projectUrl + "/static/images/deleteImg.png");
                    });
                    $("#show_img").mouseout(function () {
                        $("#result_img").attr('src', imgPath);
                    });
                    $("#result_img").click(function () {
                        $("#result_img").hide();
                        $("#imgFile").show();
                        $("#photoUrl").removeAttr('value');
                        $("#show_img").unbind('mouseover');
                        $("#show_img").unbind('mouseout');

                    });
                },
                onUploadError: function (file, errorCode, errorMsg, errorString) {
                    alert("error-->" + errorString);
                }
            });
        },10);
    });
</script>
</body>
</html>
