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
    <title>世界何氏族谱--个人信息</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
    <link href="<%=request.getContextPath()%>/static/css/fronts/personalInfo.css" rel="stylesheet" type="text/css" />
    <style>
        /*html,body {*/
            /*height: 98%;*/
        /*}*/
        <%--body{--%>
            <%--background: url("<%=request.getContextPath()%>/static/images/bg-front.jpg") no-repeat;--%>
            <%--filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";--%>
            <%---moz-background-size:100% 100%;--%>
            <%--background-size:100% 100%;--%>
        <%--}--%>
        .bbtt {
            margin-bottom: 30px;
        }

    </style>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container-fluid" style="width: 90%; "><!--margin-bottom: 50px-->
    <ul class="nav nav-tabs" role="tablist" style="margin-top: 30px">
        <li role="presentation" id="userDetailTabLi" class="active">
            <a href="#userDetailTab"  aria-controls="userDetailTab" role="tab" data-toggle="tab">个人信息</a>
        </li>
        <c:if test="${tUserFront.isVolunteer == 1}">
            <li role="presentation" id="myFamilyTabLi">
                <a href="#myFamilyTab" aria-controls="myFamilyTab" role="tab" data-toggle="tab">我的族谱</a>
            </li>
            <li role="presentation" id="includeFamilyTabLi">
                <a href="#includeFamilyTab" aria-controls="includeFamilyTab" role="tab" data-toggle="tab">族谱补录</a>
            </li>
        </c:if>


    </ul>
    <div class="tab-content container-fluid">
        <div id="userDetailTab" class="tab-pane active container-fluid" role="tabpanel">
            <div id="userDetail" class=" container-fluid">
                <div class="leftInfo infoDetail col-lg-3 col-md-3 col-sm-3 col-xs-3">
                    <div class="col-sm-10 col-md-10 col-md-offset-1">
                        <div class="thumbnail">
                            <a href="javascript:void(0)" id="userPhotoBox">
                                <c:if test="${tUserFront.userPhoto == null || tUserFront.userPhoto == '' || tUserFront.userPhoto == 'null'}">
                                    <img src="<%=request.getContextPath()%>/static/images/defaultMan.png" height="150px" width="150px" />
                                </c:if>
                                <c:if test="${tUserFront.userPhoto != null && tUserFront.userPhoto != '' && tUserFront.userPhoto != 'null'}">
                                    <img src="${tUserFront.userPhoto}"  height="150px" width="150px" /><%--<!--<%=request.getContextPath()%>-->--%>
                                </c:if>
                            </a>
                            <%--<img data-src="holder.js/300x300" alt="...">--%>
                            <div class="caption">
                                <p id="modifyPhotoBox"><button type="button" class="btn btn-default" data-toggle="modal" data-target="#photoModal">修改头像</button></p>
                                <h3>${tUserFront.userName}</h3>

                                <p>${tUserFront.province}${tUserFront.city}${tUserFront.district}</p>
                                
                                <p>
	                                <span style="font-size: 16px">
								        已充值：<a id="userMoney" href="javascript:void 0;">${totalMoney}</a>元
								    </span>
								    &nbsp;&nbsp;
								    <a class="btn btn-primary btn-sm" href="#chargeModal" data-toggle="modal" data-target="#chargeModal">充 值</a>
    							</p>

                                <p>
	                                <span style="font-size: 16px">
                                        总积分：<a id="userPoints" href="javascript:void 0;">${totalPoints}</a>
								    </span>
                                </p>
                                
                                <p>
                                    <c:if test="${tUserFront.isVolunteer != 3 && tUserFront.isVolunteer != 1}">
                                        <button type="button" class="btn btn-primary" id="applyVolunteer">申请修订族谱</button>
                                    </c:if>
                                    <c:if test="${tUserFront.isVolunteer == 3}">
                                        <span style="color: #ff8000">请等待审核！</span>
                                    </c:if>
                                    <c:if test="${tUserFront.isVolunteer == 1}">
                                        <span style="color: #00ff00">可录入族谱！</span>
                                    </c:if>
                                    &nbsp;
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyModal">修改密码</button>

                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="regedit-content rightInfo infoDetail col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <div class="form active" id="personalRegedit">
                        <form id="personalForm" action="" method="post">
                            <input type="hidden" name="id" value="${tUserFront.id}" />
                            <input type="hidden" name="userPhoto" id="userPhoto" value="${tUserFront.userPhoto}" />

                            <div class="form-group col-xs-8 form-actions">
                                <input class="form-control" id="loginName" name="loginName" value="${tUserFront.loginName}" placeholder="登录名" type="text" readonly />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="userName" name="userName" value="${tUserFront.userName}" placeholder="真实姓名" type="text" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px;display: none">
                                <input class="form-control" id="password" name="password" value="${tUserFront.password}" type="password" />
                            </div>
                            <div class="form-group col-xs-8 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="idCard" name="idCard" value="${tUserFront.idCard}" placeholder="身份证号" type="text" />
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <div class="col-xs-12 col-sm-12" style="max-height:140px">
                                    <div id="progress_bar1" style="display: none"></div>
                                    <input id="idCardPhoto" name="idCardPhoto" type="hidden" />
                                    <div class="row">
                                        <div class="col-xs-12 col-md-12" style="position: relative">
                                            <input type="file" name="imgFile1" id="imgFile1" />
                                            <a id="show_img1">
                                                <img style="display: none;" id="result_img1"  height="128px" width="128px" />
                                                <span id="result_img1_wm" style="display: none;position: absolute; top: 50px; left: 0;">本图片仅用于何氏族谱网</span>
                                            </a>
                                        </div>
                                    </div>
                                    <%--上传身份证照片--%>
                                </div>
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
            <iframe src=""></iframe>
        </div>
        <div class="tab-pane" role="tabpanel" id="includeFamilyTab">
            <iframe src=""></iframe>
        </div>
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
                <table id="moneyTable" class="table table-striped">
                    <tr>
                        <td>序号</td>
                        <td>充值金额</td>
                        <td>充值说明</td>
                        <td>充值时间</td>
                        <td>充值人</td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" id="closeMoneyModal" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 充值 Modal -->
<div class="modal fade" id="chargeModal" tabindex="-1" role="dialog" aria-labelledby="chargeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="chargeModalLabel">充值</h4>
            </div>
            <div class="modal-body" style="text-align: center">
                <p style="text-align: left;font-size: 18px">
                    充值功能开发中，目前您可以通过微信和支付宝转账的方式进行充值，扫描下方二维码即可进行充值。
                </p>
                <p style="text-align: left;color: #ff0000;font-size: 18px">
                    转账是请务必将您的“登录账号或者用户名以及联系方式”进行备注，以便工作人员进行备案登记，充值才能生效，否则充值将不能生效。
                    如：“登录账号：xxx,用户名：xxx,联系方式:15*********,说明：***”
                </p>
                <p style="text-align: left;font-size: 18px">
                    如有疑问，请联系微信:574611479--(何志业)
                </p>
                <%--<input type="text" placeholder="输入充值金额" id="chargeMoney" name="chargeMoney" />--%>
                <img src="<%=request.getContextPath()%>/static/images/wxPayQRCode.png" width="200px" height="250px" />
                <img src="<%=request.getContextPath()%>/static/images/zfbPayQRCode.jpg" width="200px" height="250px" style="margin-left: 10px" />
            </div>
            <div class="modal-footer">
                <%--<button class="btn btn-default" data-dismiss="modal">取消</button>--%>
                <%--&nbsp;&nbsp;--%>
                <button class="btn btn-default" id="toCharge" data-dismiss="modal">确认充值</button>
            </div>
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
    var isVolunteer = "${tUserFront.isVolunteer}";
    var userIdCard = "${tUserFront.idCardPhoto}";
    var xxx = "${xxx}";
    var userId = "${tUserFront.id}";
    var winHeigth = $(window).height();
    $(document).ready(function () {
        if(isVolunteer == 1 && xxx == 2){
//            $("#userDetailTabLi").removeClass("active");
//            $("#myFamilyTabLi").addClass("active");
            $("#myFamilyTabLi a").tab("show");
        }
        if(isVolunteer == 1 && xxx == 3){
//            $("#userDetailTabLi").removeClass("active");
//            $("#myFamilyTabLi").addClass("active");
            $("#includeFamilyTabLi a").tab("show");
        }

        $("#myFamilyTab").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#myFamilyTab iframe").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#includeFamilyTab").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#includeFamilyTab iframe").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#userDetail").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#province").val("${tUserFront.province}");
        $("#province").change();
        $("#city").val("${tUserFront.city}");
        $("#city").change();
        $("#district").val("${tUserFront.district}");
        $("#district").change();

        setTimeout(function() {
            $('#imgFile').uploadify({
                'swf': projectUrl + '/static/uploadify/uploadify.swf',
                'uploader': projectUrl + '/upload/uploadImg',
                'cancelImg': projectUrl + '/static/uploadify/cancel.png',
                'auto': true,
                "formData": {targetFile: '/upload/userImg'},
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

        setTimeout(function() {
            $('#imgFile1').uploadify({
                'swf': projectUrl + '/static/uploadify/uploadify.swf',
                'uploader': projectUrl + '/upload/uploadImg',
                'cancelImg': projectUrl + '/static/uploadify/cancel.png',
                'auto': true,
                "formData": {targetFile: '/upload/userImg'},
                'queueID': 'progress_bar1',
                'fileObjName': 'uploadFile',
                "buttonCursor": "hand",
                "buttonText": "上传身份证照片",
//            "buttonImage"   : projectUrl + "/static/images/defaultUpload.gif",
                "buttonClass": "img-thumbnail",
//            "height"         : "140",
                'fileDesc': '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
                'fileExt': '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
                'onUploadSuccess': function (file, data, response) {
                    var result = eval('(' + data + ')');
                    var imgPath = result.filePath;
                    $("#result_img1").attr('src', imgPath);
                    $("#result_img1").show();
                    $("#result_img1_wm").show();
                    $("#imgFile1").hide();
                    $("#idCardPhoto").attr('value', imgPath);
                    $("#show_img1").mouseover(function () {
                        $("#result_img1_wm").hide();
                        $("#result_img1").attr('src', projectUrl + "/static/images/deleteImg.png");
                    });
                    $("#show_img1").mouseout(function () {
                        $("#result_img1").attr('src', imgPath);
                        $("#result_img1_wm").show();
                    });
                    $("#result_img1").click(function () {
                        $("#result_img1").hide();
                        $("#result_img1_wm").hide();
                        $("#imgFile1").show();
                        $("#idCardPhoto").removeAttr('value');
                        $("#show_img1").unbind('mouseover');
                        $("#show_img1").unbind('mouseout');

                    });
                },
                onUploadError: function (file, errorCode, errorMsg, errorString) {
                    alert("error-->" + errorString);
                }
            });

            if(userIdCard){
                $("#result_img1").show();
                $("#imgFile1").hide();
                $("#result_img1_wm").show();
                $("#idCardPhoto").attr('value', userIdCard);
                $("#show_img1").mouseover(function(){
                    $("#result_img1_wm").hide();
                    $("#result_img1").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img1").mouseout(function(){
                    $("#result_img1").attr('src',projectUrl + userIdCard);
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
            }

        },10);
    });

</script>
</body>
</html>
