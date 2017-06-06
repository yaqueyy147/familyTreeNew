
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
    <title>世界何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
        <style>
            body{
                <%--background: url("<%=request.getContextPath()%>/static/images/bg-front.jpg") no-repeat;--%>
                <%--filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";--%>
                <%---moz-background-size:100% 100%;--%>
                <%--background-size:100% 100%;--%>
                width:100%;
            }
            #descDiv{
                margin-top: 20px;
                border:solid 1px #999999;
                word-break: break-all;
                height: auto;
                font-size: 16px;
            }
            #companyShow{
                margin-top: 20px;
            }
            .popover {
                word-break: break-all;
            }
            .modal-header{
                padding-top: 5px !important;
                padding-bottom: 5px !important;
            }
            .modal-body{
                padding:0px !important;
            }
            .modal-footer{
                padding-top: 5px !important;
                padding-bottom: 5px !important;
            }
        </style>
</head>
<body>
<%--<c:if test="${xxx != 2}">--%>
<%--<%@include file="common/header.jsp" %>--%>
<%--</c:if>--%>
<div class="container-fluid" style="width:90%;margin-top: 20px;margin-bottom: 10px;">

    <a class="btn btn-primary" href="#addIntroModal" data-toggle="modal" data-target="#addIntroModal" id="introBtn">
        <c:choose>
            <c:when test="${introduce == null}">
                添加公司介绍
            </c:when>
            <c:otherwise>
                修改公司介绍
            </c:otherwise>
        </c:choose>

    </a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    
    <%--<a class="btn btn-primary" href="#chargeModal" data-toggle="modal" data-target="#chargeModal">充 值</a>--%>
    <c:if test="${not empty tCompanySponsor.companyDesc}">
        <div id="descDiv">公司简介：${tCompanySponsor.companyDesc}</div>
    </c:if>
    <div id="companyShow">
        ${introduce.companyIntroduce}
        <%--<c:forEach var="companyDetail" items="${companyDetailList}">--%>

            <%--<div class="col-sm-6 col-md-2">--%>
                <%--<div class="thumbnail">--%>
                    <%--<a href="javascript:void(0)" ><img src="${companyDetail.publicityPhoto}" class="img-thumbnail" style="width: 100%"/></a>--%>
                    <%--&lt;%&ndash;<img data-src="holder.js/300x300" alt="...">&ndash;%&gt;--%>
                    <%--<div class="caption">--%>
                        <%--<p name="photoDesc" onmouseover="pPopover(this,1)" onmouseout="pPopover(this,2)" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden" data-container="body" data-toggle="popover" data-placement="bottom" data-content="${companyDetail.photoDesc}">--%>
                            <%--${companyDetail.photoDesc}--%>
                        <%--</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
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
            <div class="modal-body" style="max-height:300px; overflow: auto">
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
<!-- 充值列表 Modal -->
<div class="modal fade bs-example-modal-sm" id="chargeModal" tabindex="-1" role="dialog" aria-labelledby="chargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="chargeModalLabel">充值</h4>
            </div>
            <div class="modal-body">
                <input type="text" placeholder="输入充值金额" id="chargeMoney" name="chargeMoney" />
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">取消</button>
                &nbsp;&nbsp;
                <button class="btn btn-default" id="toCharge">确认充值</button>
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
                    <form class="form-horizontal" id="photoForm" method="post">
                        <input type="hidden" id="companyId" name="companyId" value="${tCompanySponsor.id}" >
                        <div class="form-group">
                            <label for="photoDesc" class="col-sm-2 control-label">图片介绍</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="photoDesc" name="photoDesc" placeholder="图片简介" type="text"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">添加图片</label>
                            <div class="col-sm-10">

                                <div id="progress_bar" style="display: none"></div>
                                <input id="publicityPhoto" name="publicityPhoto" type="hidden" />
                                <div class="row">
                                    <div class="col-xs-12 col-md-12" style="width: 150px;">
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

<!-- 添加公司简介 Modal -->
<div class="modal fade" id="addIntroModal" tabindex="-1" role="dialog" aria-labelledby="addIntroModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:1000px">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>--%>
                <h4 class="modal-title" id="addIntroModalLabel">添加公司简介</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="companyId4Intro" name="companyId" value="${tCompanySponsor.id}" >
                <input type="hidden" id="introduceId" name="id" value="${introduce.id}" >
                <div style="display: none" id="introduceA" name="introduceA">
                    ${introduce.companyIntroduce}
                </div>
                <%--<input type="hidden" id="introduceA" name="introduceA" value="${introduce.companyIntroduce}" >--%>
                <div>
                    <textarea style="width: 100%;height: 100%" class="ckeditor" name="companyDesc" id="companyDesc">
                        ${introduce.companyIntroduce}
                    </textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="saveIntro">提 交</button>
                <button class="btn btn-default" data-dismiss="modal" id="cancelIntro">取 消</button>
            </div>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/companyDetail.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    var winHeight = $(window.parent).height();
    var companyId = "${tCompanySponsor.id}";
    $(function () {
        $("body").attr("style","height:" + (winHeight - 50) + "px");
        var introModalHeight = winHeight - 150;
        $("#addIntroModal .modal-content").attr("style","height:" + introModalHeight + "px");
        $("#addIntroModal .modal-content .modal-body").attr("style","height:" + (introModalHeight - 90) + "px");
        var companyDescEditor=CKEDITOR.replace('companyDesc',{toolbar:'Full', skin : 'kama'});
        setTimeout(function() {
            $('#imgFile').uploadify({
                'swf': projectUrl + '/static/uploadify/uploadify.swf',
                'uploader': projectUrl + '/upload/uploadImg',
                'cancelImg': projectUrl + '/static/uploadify/cancel.png',
                'auto': true,
                "formData": {targetFile: '/upload/companyImg'},
                'queueID': 'progress_bar',
                'fileObjName': 'uploadFile',
                "buttonCursor": "hand",
                "buttonText": "选择图片",
                "buttonImage": projectUrl + "/static/images/defaultUpload.gif",
                "buttonClass": "img-thumbnail",
                "height": "140",
                'fileDesc': '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
                'fileExt': '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
                'onUploadSuccess': function (file, data, response) {
                    var result = eval('(' + data + ')');
                    var imgPath = result.filePath;
                    $("#result_img").attr('src', imgPath);
                    $("#result_img").show();
                    $("#imgFile").hide();
                    $("#publicityPhoto").attr('value', imgPath);
                    $("#show_img").mouseover(function () {
                        $("#result_img").attr('src', projectUrl + "/static/images/deleteImg.png");
                    });
                    $("#show_img").mouseout(function () {
                        $("#result_img").attr('src', imgPath);
                    });
                    $("#result_img").click(function () {
                        $("#result_img").hide();
                        $("#imgFile").show();
                        $("#publicityPhoto").removeAttr('value');
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
