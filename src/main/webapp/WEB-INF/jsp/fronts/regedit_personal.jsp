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
    <title>何氏族谱--个人注册</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/regedit.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
    <style>
        .form-group{
            padding-left: 0px !important;
            padding-right: 10px !important;
        }
    </style>
</head>
<body>
<div class="login-box">
    <div class="login-title text-center">注&nbsp;&nbsp;&nbsp;&nbsp;册</div>
    <div class="login-content">
        <div class="form">
            <form id="regeditForm" action="" method="post">
            <div class="form-group col-xs-5 form-actions col-xs-offset-1">
                <input class="form-control" id="loginName" name="loginName" placeholder="登录账号" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions">
                <input class="form-control" id="userName" name="userName" placeholder="真实姓名/昵称" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions col-xs-offset-1" style="margin-top: 15px">
                <input class="form-control" id="password" name="password" placeholder="密 码" type="password" />
            </div>
            <div class="form-group col-xs-5 form-actions" style="margin-top: 15px">
                <input class="form-control" id="passwordAffirm" name="passwordAffirm" placeholder="确认密码" type="password" />
            </div>
            <div class="form-group col-xs-5 form-actions col-xs-offset-1" style="margin-top: 15px">
                <input class="form-control" id="idCard" name="idCard" placeholder="身份证号" type="text" />
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <div class="col-xs-12 col-sm-12" style="max-height:140px">
                    <div id="progress_bar" style="display: none"></div>
                    <input id="idCardPhoto" name="idCardPhoto" type="hidden" />
                    <div class="row">
                        <div class="col-xs-12 col-md-12" style="position: relative">
                            <input type="file" name="imgFile1" id="imgFile1" />
                            <a id="show_img1">
                                <img style="display: none;" id="result_img1"  height="128px" width="128px" />
                                <span id="result_img1_wm" style="display: none;position: absolute; top: 50px; left: 0;">本图片仅用于注册何氏族谱网</span>
                            </a>
                        </div>
                    </div>
                    <%--上传身份证照片--%>
                </div>
            </div>
            <div class="form-group col-xs-11 form-actions col-xs-offset-1" style="margin-top: 15px">
                <div data-toggle="distpicker">
                    <select name="province" data-province="---- 选择省 ----"></select>
                    <select name="city" data-city="---- 选择市 ----"></select>
                    <select name="district" data-district="---- 选择区 ----"></select>
                </div>
            </div>
            <div class="form-group col-xs-10 form-actions col-xs-offset-1" style="margin-top: 15px">
                <input class="form-control" id="detailAddr" name="detailAddr" placeholder="详细地址" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions col-xs-offset-1" style="margin-top: 15px">
                <input class="form-control" id="phone" name="phone" placeholder="手机号码" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions" style="margin-top: 15px">
                <input class="form-control" id="wechart" name="wechart" placeholder="微 信" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions col-xs-offset-1" style="margin-top: 15px">
                <input class="form-control" id="qqNum" name="qqNum" placeholder="QQ号" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions" style="margin-top: 15px">
                <div style="width: 100%;height: 34px;"></div>
            </div>
            <div class="form-group col-xs-5 form-actions col-xs-offset-1" style="margin-top: 15px">
                <input class="form-control" id="checkCode" name="checkCode" placeholder="验证码" type="text" />
            </div>
            <div class="form-group col-xs-5 form-actions" style="margin-top: 15px">
                <canvas id="canvas"  width="150" height="34"></canvas>
            </div>
            <div class="form-group col-xs-9 form-actions col-xs-offset-4" style="margin-top: 15px">
                <button class="btn btn-primary bbtt" style="margin-bottom: 20px;" id="regedit" type="button">注 册</button>
                &nbsp;&nbsp;
                <a class="btn btn-primary bbtt" style="margin-bottom: 20px;" href="<%=request.getContextPath()%>/familyTree/index" type="button">取 消</a>
            </div>

            </form>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/checkCode_2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/regeditPersonal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript">
    $(function () {
        var regCode = "${regCode}";
        if(regCode == -2){
            alert("该账号已被注册!");
        }
        $('#imgFile1').uploadify({
            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
            'uploader'      : projectUrl + '/upload/uploadImg',
            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
            'auto'          : true,
            "formData"      : {targetFile : '/upload/userImg'},
            'queueID'       : 'progress_bar',
            'fileObjName'   : 'uploadFile',
            "buttonCursor"  : "hand",
            "buttonText"    : "上传身份证照片",
//            "buttonImage"   : projectUrl + "/static/images/defaultUpload.gif",
            "buttonClass"   : "img-thumbnail",
//            "height"         : "140",
            'fileDesc'      : '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
            'fileExt'       : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
            'onUploadSuccess' : function(file, data, response) {
                var result = eval('(' + data + ')');
                var imgPath = result.filePath;
                $("#result_img1").attr('src',imgPath);
                $("#result_img1").show();
                $("#result_img1_wm").show();
                $("#imgFile1").hide();
                $("#idCardPhoto").attr('value',imgPath);
                $("#show_img1").mouseover(function(){
                    $("#result_img1_wm").hide();
                    $("#result_img1").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img1").mouseout(function(){
                    $("#result_img1").attr('src',imgPath);
                    $("#result_img1_wm").show();
                });
                $("#result_img1").click(function(){
                    $("#result_img1").hide();
                    $("#result_img1_wm").hide();
                    $("#imgFile1").show();
                    $("#idCardPhoto").removeAttr('value');
                    $("#show_img1").unbind('mouseover');
                    $("#show_img1").unbind('mouseout');

                });
            },
            onUploadError:function (file, errorCode, errorMsg, errorString) {
                alert("error-->" + errorString);
            }
        });
//        $('#imgFile1').uploadify({
//            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
//            'uploader'      : projectUrl + '/upload/uploadImg',
//            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
//            'auto'          : true,
//            "formData"      : {targetFile : '/static/upload/licenceImg'},
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
//                $("#result_img1").attr('src',imgPath);
//                $("#result_img1").show();
//                $("#imgFile1").hide();
//                $("#businessLicense").attr('value',imgPath);
//                $("#show_img1").mouseover(function(){
//                    $("#result_img1").attr('src',projectUrl + "/static/images/deleteImg.png");
//                });
//                $("#show_img1").mouseout(function(){
//                    $("#result_img1").attr('src',imgPath);
//                });
//                $("#result_img1").click(function(){
//                    $("#result_img1").hide();
//                    $("#imgFile1").show();
//                    $("#businessLicense").removeAttr('value');
//                    $("#show_img1").unbind('mouseover');
//                    $("#show_img1").unbind('mouseout');
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
