
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
    <title>个人中心</title>
    <%@include file="common/commonCss.html"%>
    <style rel="stylesheet">
        body{
            width:100%;
            height: 100%;
            background: url("<%=request.getContextPath()%>/static/images/bag2.jpg") no-repeat;
            filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
            -moz-background-size:100% 100%;
            background-size:100% 100%;
        }
    </style>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container" style="margin-top: 50px;">
    <a class="btn btn-primary" href="#addPhotoModal" data-toggle="modal" data-target="#addPhotoModal">添加照片</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    已赞助：
    <a id="companyMoney" href="javascript:void 0;">${totalMoney}</a>元
    &nbsp;&nbsp;
    <a class="btn btn-primary" href="#addPhotoModal" data-toggle="modal" data-target="#addPhotoModal">充 值</a>
    <div id="companyShow" class="row">
        <c:forEach var="companyDetail" items="${companyDetailList}">

            <div class="col-sm-6 col-md-2">
                <div class="thumbnail">
                    <a href="javascript:void(0)" ><img src="<%=request.getContextPath()%>${companyDetail.publicityPhoto}" class="img-thumbnail"/></a>
                    <%--<img data-src="holder.js/300x300" alt="...">--%>
                    <div class="caption">
                        <p name="photoDesc" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden" data-container="body" data-toggle="popover" data-placement="right" data-content="${companyDetail.photoDesc}">
                            ${companyDetail.photoDesc}
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<!-- 充值列表 Modal -->
<div class="modal fade" id="moneyModal" tabindex="-1" role="dialog" aria-labelledby="moneyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="moneyModalLabel">公司充值列表</h4>
            </div>
            <div class="modal-body">
                <table id="moneyTable" class="table">
                    <tr>
                        <th>序号</th>
                        <th>充值金额</th>
                        <th>充值说明</th>
                        <th>充值时间</th>
                        <th>充值人</th>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" id="closeMoneyModal" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 添加宣传图片 Modal -->
<div class="modal fade" id="addPhotoModal" tabindex="-1" role="dialog" aria-labelledby="addPhotoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addPhotoModalLabel">添加公司宣传照片</h4>
            </div>
            <div class="modal-body">
                <div class="tab-content">
                    <form class="form-horizontal" id="photoForm" action="" method="post">
                        <input type="hidden" id="companyId" name="companyId" value="${tCompanySponsor.id}" >
                        <div class="form-group">
                            <label for="photoDesc" class="col-sm-2 control-label">图片介绍</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="photoDesc" name="photoDesc" placeholder="图片简介" type="text"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-10">
                                <div id="progress_bar" style="display: none"></div>
                                <input id="publicityPhoto" name="publicityPhoto" type="hidden" />
                                <div class="row">
                                    <div class="col-xs-12 col-md-12">
                                        <input type="file" name="imgFile" id="imgFile" />
                                        <a id="show_img"><img style="display: none;" id="result_img" class="img-responsive" /></a>
                                    </div>
                                </div>
                                <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="savePhoto">提 交</button>
                <button class="btn btn-default" id="closeModal" data-dismiss="modal">取 消</button>
            </div>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/companyDetail.js"></script>
<script type="text/javascript">
    var companyId = "${tCompanySponsor.id}";
    $('#imgFile').uploadify({
        'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
        'uploader'      : projectUrl + '/upload/uploadImg',
        'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
        'auto'          : true,
        "formData"      : {targetFile : '/static/upload/companyImg'},
        'queueID'       : 'progress_bar',
        'fileObjName'   : 'uploadFile',
        "buttonCursor"  : "hand",
        "buttonText"    : "选择图片",
        "buttonImage"   : projectUrl + "/static/images/defaultUpload.gif",
        "buttonClass"   : "img-thumbnail",
        "height"         : "140",
        'fileDesc'      : '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
        'fileExt'       : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
        'onUploadSuccess' : function(file, data, response) {
            var result = eval('(' + data + ')');
            var imgPath = result.filePath;
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
        },
        onUploadError:function (file, errorCode, errorMsg, errorString) {
            alert("error-->" + errorString);
        }
    });
</script>
</body>
</html>