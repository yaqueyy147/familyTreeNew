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
    <title>世界何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
    <link href="<%=request.getContextPath()%>/static/css/fronts/companyInfo.css" rel="stylesheet" type="text/css" />
    <style>
        /*
        html,body {
            height: 100%;
        }
        */

        .bbtt {
            margin-bottom: 30px;
        }
        #ccDiv{
            width: 90%;
            /*margin-bottom: 50px;*/
            overflow: auto
        }
        .form-control{
            padding:0px !important;
        }

    </style>
</head>
<body>
<%@include file="common/header.jsp" %>
<div id="ccDiv" class="container-fluid">
    <ul class="nav nav-tabs" role="tablist" style="margin-top: 30px">
        <li role="presentation" class="active">
            <a href="#userDetailTab"  aria-controls="userDetailTab" role="tab" data-toggle="tab">个人信息</a>
        </li>
        <c:if test="${companyInfo.state == 1}">
        <li role="presentation" id="myCompanyTabLi">
            <a href="#myFamilyTab" aria-controls="myFamilyTab" role="tab" data-toggle="tab">我的公司</a>
        </li>
        </c:if>

    </ul>
    <div class="tab-content container-fluid">
        <div id="userDetailTab" class="tab-pane active" role="tabpanel">
            <div id="userDetail" class="container-fluid">
                <div class="leftInfo infoDetail col-lg-3 col-md-3 col-sm-3 col-xs-3">
                    <div class="col-sm-10 col-md-10 col-md-offset-1">
                        <div class="thumbnail">
                            <a href="javascript:void(0)" id="userPhotoBox">
                                <c:if test="${companyInfo.companyPhoto == null || companyInfo.companyPhoto == '' || companyInfo.companyPhoto == 'null'}">
                                    <img src="<%=request.getContextPath()%>/static/images/defautCompany.jpg" />
                                </c:if>
                                <c:if test="${companyInfo.companyPhoto != null && companyInfo.companyPhoto != '' && companyInfo.companyPhoto != 'null'}">
                                    <img src="${companyInfo.companyPhoto}" />
                                </c:if>
                            </a>
                            <%--<img data-src="holder.js/300x300" alt="...">--%>
                            <div class="caption">
                                <p id="modifyPhotoBox"><button type="button" class="btn btn-default" data-toggle="modal" data-target="#photoModal">修改头像</button></p>
                                <h3>${companyInfo.companyName}</h3>

                                <p>${companyInfo.companyArea}</p>
                                <p>
	                                <span style="font-size: 16px">
								        已充值：<a id="companyMoney" href="javascript:void 0;">${totalMoney}</a>元
								    </span>
								    &nbsp;&nbsp;
								    <a class="btn btn-primary btn-sm" href="#chargeModal" data-toggle="modal" data-target="#chargeModal">充 值</a>
    							</p>
                                <p>
	                                <span style="font-size: 16px">
                                        总积分：<a id="companyPoints" href="javascript:void 0;">${totalPoints}</a>
								    </span>
                                </p>
                                <p>
                                    <c:if test="${companyInfo.state != 3 && companyInfo.state != 1}">
                                        <button type="button" class="btn btn-primary" id="applyVolunteer">申请宣传/赞助</button>
                                    </c:if>
                                    <c:if test="${companyInfo.state == 3}">
                                        <span style="color: #ff8000">已申请，请等待审核！</span>
                                    </c:if>
                                    <c:if test="${companyInfo.state == 1}">
                                        <span style="color: #00ff00">可宣传/赞助！</span>
                                    </c:if>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyPwdModal">修改密码</button>

                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="regedit-content rightInfo infoDetail col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <div class="form active" id="personalRegedit">
                        <form id="companyForm" action="" method="post">
                            <input type="hidden" name="id" value="${companyInfo.id}" />
                            <input type="hidden" name="companyPhoto" id="companyPhoto" value="${companyInfo.companyPhoto}" />
                            <div class="form-group col-xs-6 has-error has-feedback">
                                <input class="form-control" id="companyLoginName" name="companyLoginName" value="${companyInfo.companyLoginName}" type="text" readonly />
                                <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
                            </div>
                            <div class="form-group col-xs-6 has-error form-actions">
                                <input class="form-control" id="companyName" name="companyName" value="${companyInfo.companyName}" placeholder="公司名称" type="text" />
                                <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
                            </div>
                            <div class="form-group col-xs-6 has-error form-actions" style="margin-top: 15px;display: none;">
                                <input class="form-control" id="companyLoginPassword" name="companyLoginPassword" value="${companyInfo.companyLoginPassword}" type="password" />
                                <span class="glyphicon glyphicon-asterisk form-control-feedback"></span>
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="companyArea" name="companyArea" value="${companyInfo.companyArea}" placeholder="公司地址" type="text" />
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="companyMobilePhone" name="companyMobilePhone" value="${companyInfo.companyMobilePhone}" placeholder="手机号码" type="text" />
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="companyTelephone" name="companyTelephone" value="${companyInfo.companyTelephone}" placeholder="固定电话" type="text" />
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="companyFax" name="companyFax" value="${companyInfo.companyFax}" placeholder="传 真" type="text" />
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="companyWechart" name="companyWechart" value="${companyInfo.companyWechart}" placeholder="微 信" type="text" />
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <input class="form-control" id="companyQq" name="companyQq" value="${companyInfo.companyQq}" placeholder="QQ" type="text" />
                            </div>
                            <div class="form-group col-xs-12 form-actions" style="margin-top: 15px">
                                <textarea class="form-control" id="companyDesc" name="companyDesc" value="${companyInfo.companyDesc}" placeholder="公司简介" type="text">${companyInfo.companyDesc}
                                </textarea>
                            </div>
                            <div class="form-group col-xs-6 form-actions" style="margin-top: 15px">
                                <div class="col-xs-12 col-sm-12" style="height:140px">
                                    <div id="progress_bar1" style="display: none"></div>
                                    <input id="businessLicense" name="businessLicense" value="${companyInfo.businessLicense}" type="hidden" />
                                    <div class="row">
                                        <div class="col-xs-12 col-md-12">
                                            <input type="file" name="imgFile1" id="imgFile1" style="display: none" />
                                            <a id="show_img1"><img style="display: none;" id="result_img1" height="128px" width="128px" src="${companyInfo.businessLicense}" /></a>
                                        </div>
                                    </div>
                                    上传公司营业执照<span class="glyphicon glyphicon-asterisk" style="color: #a94442"></span>
                                    <%--<input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true">--%>
                                </div>
                            </div>
                            <div class="form-group col-xs-9 form-actions col-xs-offset-4" style="margin-top: 50px">
                                <button class="btn btn-primary bbtt" id="companyRegeditbb" type="button">保存</button>
                            </div>

                        </form>
                    </div>

                </div>
            </div>
        </div>
        <div class="tab-pane" role="tabpanel" id="myFamilyTab">
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
                <%--<input type="text" placeholder="输入充值金额" id="chargeMoney" name="chargeMoney" />--%>
                    <p style="text-align: left;font-size: 18px">
                        充值功能开发中，目前您可以通过微信和支付宝转账的方式进行充值，扫描下方二维码即可进行充值。
                    </p>
                    <p style="text-align: left;color: #ff0000;font-size: 18px">
                        转账是请务必将您的登录账号和用户名进行备注，以便工作人员进行备案登记，充值才能生效，否则充值将不能生效。
                    </p>
                    <%--<input type="text" placeholder="输入充值金额" id="chargeMoney" name="chargeMoney" />--%>
                    <img src="<%=request.getContextPath()%>/static/images/wxPayQRCode.png" width="200px" height="250px" />
                    <img src="<%=request.getContextPath()%>/static/images/zfbPayQRCode.jpg" width="200px" height="250px" style="margin-left: 10px" />
            </div>

            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">取消</button>
                &nbsp;&nbsp;
                <button class="btn btn-default" id="toCharge">确认充值</button>
            </div>
        </div>
    </div>
</div>

<!--修改密码-->
<div class="modal fade bs-example-modal-sm" id="modifyPwdModal" tabindex="-1" role="dialog" aria-labelledby="modifyPwdModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modifyPwdModalLabel">修改密码</h4>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/companyInfo.js"></script>
<script type="text/javascript">
    var userInfo = "${companyInfo}";
    var companyId = "${companyInfo.id}";
    var winHeigth = $(window).height();
    var businessLicense = "${companyInfo.businessLicense}";
    $(document).ready(function () {
//        containerDiv
        $("#myFamilyTab").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#myFamilyTab iframe").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");
        $("#userDetail").attr("style","height:" + (winHeigth - 70 - 20 - 10 - 22 - 60) + "px");

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
                "formData": {targetFile: '/static/upload/companyImg'},
                'queueID': 'progress_bar1',
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
                    $("#result_img1").attr('src', imgPath);
                    $("#result_img1").show();
                    $("#imgFile1").hide();
                    $("#businessLicense").attr('value', imgPath);
                    $("#show_img1").mouseover(function () {
                        $("#result_img1").attr('src', projectUrl + "/static/images/deleteImg.png");
                    });
                    $("#show_img1").mouseout(function () {
                        $("#result_img1").attr('src', imgPath);
                    });
                    $("#result_img1").click(function () {
                        $("#result_img1").hide();
                        $("#imgFile1").show();
                        $("#businessLicense").removeAttr('value');
                        $("#show_img1").unbind('mouseover');
                        $("#show_img1").unbind('mouseout');

                    });
                },
                onUploadError: function (file, errorCode, errorMsg, errorString) {
                    alert("error-->" + errorString);
                }
            });

            if(businessLicense){
                $("#result_img1").show();
                $("#imgFile1").hide();
                $("#show_img1").mouseover(function(){
                    $("#result_img1").attr('src',projectUrl + "/static/images/deleteImg.png");
                });
                $("#show_img1").mouseout(function(){
                    $("#result_img1").attr('src',projectUrl + businessLicense);
                });
                $("#result_img1").click(function(){
                    $("#result_img1").hide();
                    $("#imgFile1").show();
                    $("#businessLicense").removeAttr('value');
                    $("#show_img1").unbind('mouseover');
                    $("#show_img1").unbind('mouseout');

                });
            }
        },10);


    });

</script>
</body>
</html>
