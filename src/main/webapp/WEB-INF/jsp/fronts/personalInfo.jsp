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
    <%@include file="common/commonCss.jsp"%>
    <link href="<%=request.getContextPath()%>/static/css/fronts/personalInfo.css" rel="stylesheet" type="text/css" />
    <style>
        html,body {
            height: 100%;
        }
        body{
            background: url("<%=request.getContextPath()%>/static/images/bag.jpg") no-repeat;
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
    <ul class="nav nav-tabs" role="tablist" style="margin-top: 30px">
        <li role="presentation" class="active">
            <a href="#userDetailTab"  aria-controls="userDetailTab" role="tab" data-toggle="tab">个人信息</a>
        </li>
        <li role="presentation">
            <a href="#myFamilyTab" aria-controls="myFamilyTab" role="tab" data-toggle="tab">我的族谱</a>
        </li>

    </ul>
    <div class="tab-content">
        <div id="userDetailTab" class="tab-pane active" role="tabpanel">
            <div id="userDetail">
                <div class="leftInfo infoDetail">
                    <div class="col-sm-10 col-md-10 col-md-offset-1">
                        <div class="thumbnail">
                            <a href="javascript:void(0)" id="userPhotoBox">
                                <c:if test="${tUserFront.userPhoto == null || tUserFront.userPhoto == '' || tUserFront.userPhoto == 'null'}">
                                    <img src="<%=request.getContextPath()%>/static/images/defaultMan.png" />
                                </c:if>
                                <c:if test="${tUserFront.userPhoto != null && tUserFront.userPhoto != '' && tUserFront.userPhoto != 'null'}">
                                    <img src="<%=request.getContextPath()%>${tUserFront.userPhoto}" />
                                </c:if>
                            </a>
                            <%--<img data-src="holder.js/300x300" alt="...">--%>
                            <div class="caption">
                                <p id="modifyPhotoBox"><button type="button" class="btn btn-default" data-toggle="modal" data-target="#photoModal">修改头像</button></p>
                                <h3>${tUserFront.userName}</h3>

                                <p>${tUserFront.province}${tUserFront.city}${tUserFront.district}</p>
                                <p>
                                    <c:if test="${tUserFront.isVolunteer != 3}">
                                        <button type="button" class="btn btn-primary" id="applyVolunteer">申请志愿者</button>
                                    </c:if>
                                    <c:if test="${tUserFront.isVolunteer == 3}">
                                        <span>已申请志愿者，请等待审核！</span>
                                    </c:if>
                                    <c:if test="${tUserFront.isVolunteer == 1}">
                                        <span>录入族谱志愿者！</span>
                                    </c:if>
                                    &nbsp;
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyModal">修改密码</button>

                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="regedit-content rightInfo infoDetail">
                    <div class="form active" id="personalRegedit">
                        <form id="personalForm" action="" method="post">
                            <input type="hidden" name="id" value="${tUserFront.id}" />
                            <input type="hidden" name="userPhoto" id="userPhoto" value="${tUserFront.userPhoto}" />

                            <div class="form-group col-xs-8 form-actions">
                                <input class="form-control" id="userName" name="userName" value="${tUserFront.userName}" placeholder="用户名" type="text" readonly />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="nickName" name="nickName" value="${tUserFront.nickName}" placeholder="昵 称" type="text" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px;display: none">
                                <input class="form-control" id="password" name="password" value="${tUserFront.password}" type="password" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="idCard" name="idCard" value="${tUserFront.idCard}" placeholder="身份证号" type="text" />
                            </div>
                            <div class="form-group col-xs-12 form-actions" style="margin-top: 15px">
                                <div data-toggle="distpicker">
                                    <select id="province" name="province" data-province="---- 选择省 ----"></select>
                                    <select id="city" name="city" data-city="---- 选择市 ----"></select>
                                    <select id="district" name="district" data-district="---- 选择区 ----"></select>
                                </div>
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="detailAddr" name="detailAddr" value="${tUserFront.detailAddr}" placeholder="详细地址" type="text" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="phone" name="phone" value="${tUserFront.phone}" placeholder="手机号码" type="text" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="wechart" name="wechart" value="${tUserFront.wechart}" placeholder="微 信" type="text" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="qqNum" name="qqNum" value="${tUserFront.qqNum}" placeholder="QQ" type="text" />
                            </div>
                            <div class="form-group col-xs-9 form-actions col-xs-offset-2" style="margin-top: 15px">
                                <button class="btn btn-primary bbtt" id="regedit" type="button">保存</button>
                            </div>

                        </form>
                    </div>

                </div>
            </div>
        </div>
        <div class="tab-pane" role="tabpanel" id="myFamilyTab">
            <iframe src="<%=request.getContextPath()%>/family/personalIndex"></iframe>
        </div>
    </div>
</div>
<!--修改密码-->
<div class="modal fade bs-example-modal-sm" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modifyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modifyModalLabel">修改密码</h4>
            </div>
            <div class="modal-body">
                <form id="peopleForm" action="" method="post">
                    <div class="form-group">
                        <label for="oldPassword">原密码</label>
                        <input type="password" class="form-control" id="oldPassword" placeholder="原密码">
                    </div>
                    <div class="form-group">
                        <label for="newPassword">新密码</label>
                        <input type="password" class="form-control" id="newPassword" placeholder="新密码">
                    </div>
                    <div class="form-group">
                        <label for="newPasswordAffirm">确认新密码</label>
                        <input type="password" class="form-control" id="newPasswordAffirm" placeholder="确认新密码">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="toModify-pwd">确认修改</button>
            </div>
        </div>
    </div>
</div>
<!--修改头像-->
<div class="modal fade bs-example-modal-sm" id="photoModal" tabindex="-1" role="dialog" aria-labelledby="photoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="photoModalLabel">修改头像</h4>
            </div>
            <div class="modal-body" >
                <div class="photoBox" style="margin-left: auto;margin-right: auto;width: 150px;">
                    <div id="progress_bar" style="display: none"></div>
                    <input id="photoUrl" name="photoUrl" type="hidden" />
                    <input type="file" name="imgFile" id="imgFile" />
                    <a id="show_img"><img style="display: none;" id="result_img" class="img-responsive" /></a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="toModify-photo">确认修改</button>
            </div>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/personalInfo.js"></script>
<script type="text/javascript">
    var userInfo = "${tUserFront}";
    var winHeigth = $(document).height();
    $(function () {
        $("#myFamilyTab").attr("style","height:" + (winHeigth - 70 - 20 - 10));
        $("#myFamilyTab iframe").attr("style","height:" + (winHeigth - 70 - 20 - 10));
        $("#province").val(userInfo.province);
        $("#province").change();
        $("#city").val(userInfo.city);
        $("#city").change();
        $("#district").val(userInfo.district);
        $("#district").change();

        $('#imgFile').uploadify({
            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
            'uploader'      : projectUrl + '/upload/uploadImg',
            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
            'auto'          : true,
            "formData"      : {targetFile : '/static/upload/userImg'},
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

    });

</script>
</body>
</html>
