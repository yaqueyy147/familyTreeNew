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
    <title>何氏族谱--企业注册</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/regedit.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<div class="login-box">
    <div class="login-title text-center">注&nbsp;&nbsp;&nbsp;&nbsp;册</div>
    <div class="login-content">
        <div class="form">
            <form id="regeditForm" action="" method="post">
            <div class="form-group col-xs-6 has-error has-feedback">
                <input class="form-control" id="companyLoginName" name="companyLoginName" placeholder="用户名(登录用)" type="text" />
            <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
            </div>
            <div class="form-group col-xs-6 has-error form-actions">
                <input class="form-control" id="companyName" name="companyName" placeholder="公司名称" type="text" />
                <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
            </div>
            <div class="form-group col-xs-6 has-error form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyLoginPassword" name="companyLoginPassword" placeholder="密 码" type="password" />
                <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
            </div>
            <div class="form-group col-xs-6 has-error form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyLoginPasswordAffirm" name="companyLoginPasswordAffirm" placeholder="确认密码" type="password" />
                <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyMobilePhone" name="companyMobilePhone" placeholder="手机号码" type="text" />
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyTelephone" name="companyTelephone" placeholder="固定电话" type="text" />
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyFax" name="companyFax" placeholder="传 真" type="text" />
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyWechart" name="companyWechart" placeholder="微 信" type="text" />
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyQq" name="companyQq" placeholder="QQ" type="text" />
            </div>
            <div class="form-group col-xs-12 form-actions" style="margin-top: 15px">
                <input class="form-control" id="companyArea" name="companyArea" placeholder="公司地址" type="text" />
            </div>
            <div class="form-group col-xs-12 form-actions" style="margin-top: 15px">
                <textarea class="form-control" id="companyDesc" name="companyDesc" placeholder="公司简介" type="text"></textarea>
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <div class="col-xs-12 col-sm-12" style="max-height:140px">
                    <div id="progress_bar" style="display: none"></div>
                    <input id="companyPhoto" name="companyPhoto" type="hidden" />
                    <div class="row">
                        <div class="col-xs-12 col-md-12">
                            <input type="file" name="imgFile1" id="imgFile1" />
                            <a id="show_img1"><img style="display: none;" id="result_img1"  height="128px" width="128px" /></a>
                        </div>
                    </div>
                上传公司展示图片
                <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                </div>
            </div>
            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                <div class="col-xs-12 col-sm-12" style="max-height:140px">
                <div id="progress_bar" style="display: none"></div>
                    <input id="businessLicense" name="businessLicense" type="hidden" />
                    <div class="row">
                        <div class="col-xs-12 col-md-12">
                            <input type="file" name="imgFile2" id="imgFile2" />
                            <a id="show_img2"><img style="display: none;" id="result_img2" height="128px" width="128px" /></a>
                        </div>
                    </div>
                    上传公司营业执照<span class="glyphicon glyphicon-asterisk" style="color: #a94442"></span>
                    <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                </div>
            </div>
            <div class="form-group col-xs-9 form-actions col-xs-offset-4" style="margin-top: 30px">
                <button class="btn btn-primary bbtt" style="margin-bottom: 20px;" id="companyRegeditbb" type="button">注 册</button>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/regeditCompany.js"></script>
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
            "formData"      : {targetFile : '/upload/companyImg'},
            'queueID'       : 'progress_bar',
            'fileObjName'   : 'uploadFile',
            "buttonCursor"  : "hand",
            "buttonText"    : "选择图片",
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
                $("#imgFile1").hide();
                $("#companyPhoto").attr('value',imgPath);
                $("#show_img1").mouseover(function(){
                    $("#result_img1").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img1").mouseout(function(){
                    $("#result_img1").attr('src',imgPath);
                });
                $("#result_img1").click(function(){
                    $("#result_img1").hide();
                    $("#imgFile1").show();
                    $("#companyPhoto").removeAttr('value');
                    $("#show_img1").unbind('mouseover');
                    $("#show_img1").unbind('mouseout');

                });
            },
            onUploadError:function (file, errorCode, errorMsg, errorString) {
                alert("error-->" + errorString);
            }
        });
        $('#imgFile2').uploadify({
            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
            'uploader'      : projectUrl + '/upload/uploadImg',
            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
            'auto'          : true,
            "formData"      : {targetFile : '/upload/licenceImg'},
            'queueID'       : 'progress_bar',
            'fileObjName'   : 'uploadFile',
            "buttonCursor"  : "hand",
            "buttonText"    : "选择图片",
//            "buttonImage"   : projectUrl + "/static/images/defaultUpload.gif",
            "buttonClass"   : "img-thumbnail",
//            "height"         : "140",
            'fileDesc'      : '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
            'fileExt'       : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
            'onUploadSuccess' : function(file, data, response) {
                var result = eval('(' + data + ')');
                var imgPath = result.filePath;
                $("#result_img2").attr('src',imgPath);
                $("#result_img2").show();
                $("#imgFile2").hide();
                $("#businessLicense").attr('value',imgPath);
                $("#show_img2").mouseover(function(){
                    $("#result_img2").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img2").mouseout(function(){
                    $("#result_img2").attr('src',imgPath);
                });
                $("#result_img2").click(function(){
                    $("#result_img2").hide();
                    $("#imgFile2").show();
                    $("#businessLicense").removeAttr('value');
                    $("#show_img2").unbind('mouseover');
                    $("#show_img2").unbind('mouseout');

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
