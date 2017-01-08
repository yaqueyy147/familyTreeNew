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
    <title>注册</title>
    <%@include file="common/commonCss.html"%>
    <link href="<%=request.getContextPath()%>/static/css/fronts/regedit.css" rel="stylesheet" type="text/css" />
    <style>

        .bbtt {
            margin-bottom: 30px;
        }

    </style>
</head>
<body>
<div class="regedit-box">
    <div class="regedit-title">
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active">
                <a href="#personalRegedit" role="tab" data-toggle="tab">个人注册</a>
            </li>
            <li role="presentation">
                <a href="#companyRegedit" role="tab" data-toggle="tab">公司注册</a>
            </li>
        </ul>
    </div>

    <div class="regedit-content tab-content">
        <div role="tabpanel" class="form tab-pane active" id="personalRegedit">
            <form id="regeditForm" action="" method="post">
                <div class="form-group col-xs-8 form-actions col-xs-offset-2">
                    <input class="form-control" id="userName" name="userName" placeholder="用户名" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="nickName" name="nickName" placeholder="昵 称" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="password" name="password" placeholder="密 码" type="password" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="idCard" name="idCard" placeholder="身份证号" type="text" />
                </div>
                <div class="form-group col-xs-12 form-actions" style="margin-top: 15px">
                    <div data-toggle="distpicker">
                        <select name="province" data-province="---- 选择省 ----"></select>
                        <select name="city" data-city="---- 选择市 ----"></select>
                        <select name="district" data-district="---- 选择区 ----"></select>
                    </div>
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="detailAttr" name="detailAttr" placeholder="详细地址" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="phone" name="phone" placeholder="手机号码" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="wechart" name="wechart" placeholder="微 信" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="qqNum" name="qqNum" placeholder="QQ" type="text" />
                </div>
                <div class="form-group col-xs-9 form-actions col-xs-offset-4" style="margin-top: 15px">
                    <button class="btn btn-primary bbtt" id="regedit" type="button">注 册</button>
                    &nbsp;&nbsp;
                    <a class="btn btn-primary bbtt" href="/familyTree/index" type="button">取 消</a>
                </div>

            </form>
        </div>
        <div role="tabpanel" class="form tab-pane" id="companyRegedit">
            <form id="companyForm" action="" method="post">
                <div class="form-group col-xs-8 form-actions col-xs-offset-2">
                    <input class="form-control" id="companyLoginName" name="companyLoginName" placeholder="用户名(登录用)" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyName" name="companyName" placeholder="公司名称" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyLoginPassword" name="companyLoginPassword" placeholder="密 码" type="password" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyArea" name="companyArea" placeholder="公司地址" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyMobilePhone" name="companyMobilePhone" placeholder="手机号码" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyTelephone" name="companyTelephone" placeholder="固定电话" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyFax" name="companyFax" placeholder="传 真" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyWechart" name="companyWechart" placeholder="微 信" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="companyQq" name="companyQq" placeholder="QQ" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <textarea class="form-control" id="companyDesc" name="companyDesc" placeholder="公司简介" type="text"></textarea>
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <div class="col-xs-12 col-sm-12" style="height:140px">
                        <div id="progress_bar" style="display: none"></div>
                        <input id="companyPhoto" name="companyPhoto" type="hidden" />
                        <div class="row">
                            <div class="col-xs-12 col-md-12">
                                <a id="imgFile" />
                                <a id="show_img"><img style="display: none;" id="result_img" class="img-thumbnail" /></a>
                            </div>
                        </div>
                        <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                    </div>
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <div class="col-xs-12 col-sm-12" style="height:140px">
                        <div id="progress_bar" style="display: none"></div>
                        <input id="businessLicense" name="businessLicense" type="hidden" />
                        <div class="row">
                            <div class="col-xs-12 col-md-12">
                                <input type="file" name="imgFile1" id="imgFile1" />
                                <a id="show_img1"><img style="display: none;" id="result_img1" class="img-thumbnail" /></a>
                            </div>
                        </div>
                        <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                    </div>
                </div>
                <div class="form-group col-xs-9 form-actions col-xs-offset-4" style="margin-top: 15px">
                    <button class="btn btn-primary bbtt" id="companyRegeditbb" type="button">注 册</button>
                    &nbsp;&nbsp;
                    <a class="btn btn-primary bbtt" href="/familyTree/index" type="button">取 消</a>
                </div>

            </form>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/regedit.js"></script>
<script type="text/javascript">
    $(function () {
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
                $("#companyPhoto").attr('value',imgPath);
                $("#show_img").mouseover(function(){
                    $("#result_img").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img").mouseout(function(){
                    $("#result_img").attr('src',imgPath);
                });
                $("#result_img").click(function(){
                    $("#result_img").hide();
                    $("#imgFile").show();
                    $("#companyPhoto").removeAttr('value');
                    $("#show_img").unbind('mouseover');
                    $("#show_img").unbind('mouseout');

                });
            },
            onUploadError:function (file, errorCode, errorMsg, errorString) {
                alert("error-->" + errorString);
            }
        });
        $('#imgFile1').uploadify({
            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
            'uploader'      : projectUrl + '/upload/uploadImg',
            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
            'auto'          : true,
            "formData"      : {targetFile : '/static/upload/licenceImg'},
            'queueID'       : 'progress_bar',
            'fileObjName'   : 'uploadFile',
            "buttonCursor"  : "hand",
            "buttonText"    : "选择图片",
            'fileDesc'      : '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
            'fileExt'       : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
            'onUploadSuccess' : function(file, data, response) {
                var result = eval('(' + data + ')');
                var imgPath = result.filePath;
                $("#result_img1").attr('src',imgPath);
                $("#result_img1").show();
                $("#imgFile1").hide();
                $("#businessLicense").attr('value',imgPath);
                $("#show_img1").mouseover(function(){
                    $("#result_img1").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img1").mouseout(function(){
                    $("#result_img1").attr('src',imgPath);
                });
                $("#result_img1").click(function(){
                    $("#result_img1").hide();
                    $("#imgFile1").show();
                    $("#businessLicense").removeAttr('value');
                    $("#show_img1").unbind('mouseover');
                    $("#show_img1").unbind('mouseout');

                });
            },
            onUploadError:function (file, errorCode, errorMsg, errorString) {
                alert("error-->" + errorString);
            }
        });
    });

</script>
</body>
</html>
