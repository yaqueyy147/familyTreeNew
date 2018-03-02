<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/20 0020
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>世界何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/fronts/viewFamilyTree.css" />
    <%@include file="common/commonCss.jsp"%>
    <style rel="stylesheet">
        <%--body{--%>
            <%--width:100%;--%>
            <%--height: 100%;--%>
            <%--background: url("<%=request.getContextPath()%>/static/images/bag2.jpg") no-repeat;--%>
            <%--filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";--%>
            <%---moz-background-size:100% 100%;--%>
            <%--background-size:100% 100%;--%>
        <%--}--%>

        .container{
            width:100% !important;
        }
        .swfupload {
            opacity: 0;
        }
        .loading{
            z-index: 8888;
            width: 100%;
            height: 100%;
            background-color: #999999;
            opacity: 0.5;
            text-align: center;
            position: fixed;
        }
        .loading div{
            z-index: 9999;
            width: 200px;
            height:200px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 10%;
            color: #ff0000;
            font-size: 16px;
        }
        #peopleForm input[type="text"]{
            height:20px;
        }
        .modal-header{
            padding-top:5px !important;
            padding-bottom: 5px !important;
        }
        .modal-dialog{
            margin-top: 5px !important;
        }
    </style>
</head>
<body>

<%--<%@include file="common/header.jsp" %>--%>

<div class="container" style="margin-top: 50px">
    <div class="loading">
        <div>加载中,请稍后...</div>
    </div>
    <input type="hidden" value="${familyId}" id="familyIdT" name="familyIdT" />
    <div style="text-align: center;font-size: 20px">
        <p style="border-bottom: solid 1px #999999">
            <span style="color: #a94442">${tFamily.familyName}</span>
            &nbsp;&nbsp;的族人&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="btn btn-primary btn-sm" href="javascript:void 0;" id="goBack">返回</a>
            &nbsp;&nbsp;
            <c:if test="${merge == null}">
                <a class="btn btn-primary btn-sm" href="javascript:void 0;" id="toInclude">申请收录</a>
            </c:if>
            <c:if test="${merge.state == 1}">
                <a class="btn btn-primary btn-sm" href="javascript:void 0;">已收录</a>
            </c:if>
            <c:if test="${merge.state == 3}">
                <%--<a class="btn btn-primary" href="javascript:void 0;">已驳回</a>--%>
                <a class="btn btn-primary btn-sm" href="javascript:void 0;" id="toInclude">申请收录</a>
            </c:if>
            <c:if test="${merge.state == 2 || merge.state == 0}">
                <a class="btn btn-primary btn-sm" href="javascript:void 0;">已申请，待审核</a>
            </c:if>
            <c:if test="${merge.state == 5}">
                <a class="btn btn-primary btn-sm" href="javascript:void 0;">补录中...</a>
            </c:if>
            &nbsp;&nbsp;
            <a class="btn btn-primary btn-sm" href="javascript:void 0;" id="addPeople">添加族人</a>
            <%--<a class="btn btn-primary btn-sm" href="javascript:void 0;" id="print" data-toggle="modal" data-target="#printModal">打印</a>--%>
            <c:if test="${merge.state == 3}">
                <span>驳回意见:${merge.rejectDesc}</span>
            </c:if>

        </p>
        <c:if test="${not empty tFamily.familyDesc}">
            <p style="font-size: 14px;text-align: left;border-bottom: solid 1px #999999">家族简介：${tFamily.familyDesc}</p>
        </c:if>
    </div>

    <div id="familyTree" class="ztree"></div>
</div>
<!-- 添加族人 Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addModalLabel">添加族人</h4>
            </div>
            <div class="modal-body">
                <form id="peopleForm" action="<%=request.getContextPath()%>/family/savePeople" method="post">
                    <table>
                        <tr style="border: solid 2px #9d9d9d;">
                            <td class="tdLg" style="text-align: left" colspan="9">
                                <input name="familyId" id="familyId" type="hidden" value="${familyId}" />

                                &nbsp;&nbsp;
                                第<input id="generation" name="generation" type="text" style="width:20px" value="0" />代
                                <%--第--%>
                                <%--<select id="generation" name="generation" style="width:35px">--%>
                                    <%--<option value="1">1</option>--%>
                                    <%--<option value="2">2</option>--%>
                                    <%--<option value="3">3</option>--%>
                                    <%--<option value="4">4</option>--%>
                                    <%--<option value="5">5</option>--%>
                                    <%--<option value="6">6</option>--%>
                                    <%--<option value="7">7</option>--%>
                                    <%--<option value="8">8</option>--%>
                                <%--</select>--%>
                                <%--代--%>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input name="id" id="id" type="hidden" value="" />
                                <input name="mateId" id="mateId" type="hidden"/>
                                <input name="peopleType" id="peopleType" type="hidden" value="1" />
                                <input name="peopleStatus" id="peopleStatus" type="hidden" value="5" />
                                <input name="superiorId" id="superiorId" type="hidden" value="" />
                                <span id="peopleInfo"></span>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder">
                            <td class="photoBox" rowspan="8" colspan="3" style="text-align: center" id="photo">
                                <div id="progress_bar" style="display: none"></div>
                                <input id="photoUrl" name="photoUrl" type="hidden" />
                                <input type="file" name="imgFile" id="imgFile" />
                                <a id="show_img"><img style="display: none;" id="result_img" height="128px" width="128px" /></a>
                            </td>
                            <td class="tdSm">
                                姓名：
                            </td>
                            <td colspan="3">
                                <input name="name" id="name" type="text" value="何"/>
                            </td>
                            <td class="tdSm">
                                特殊说明：
                            </td>
                            <td><input name="specialRemark" id="specialRemark" type="text" /></td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">
                                曾用名：
                            </td>
                            <td colspan="3">
                                <input name="usedName" id="usedName" type="text"/>
                            </td>
                            <td class="tdSm">
                                学历：
                            </td>
                            <td>
                                <input name="education" id="education" type="text" />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">性别：</td>
                            <td style="text-align: left">
                                <select name="sex" id="sex">
                                    <option value="1">男</option>
                                    <option value="0">女</option>
                                </select>
                            </td>
                            <td colspan="2">
                                <select name="mateType" id="mateType">
                                    <option value="0"></option>
                                    <option value="1">妻子</option>
                                    <option value="2">丈夫</option>
                                </select>
                            </td>
                            <td class="tdSm">
                                职业：
                            </td>
                            <td>
                                <input name="job" id="job" type="text" />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">辈分：</td>
                            <td class="tdXSm">
                                <input name="familyGeneration" id="familyGeneration" class="inputSm" type="text" value="0" />
                            </td>
                            <td class="tdXSm">字：</td>
                            <td class="tdXSm">
                                <input name="cName" id="cName" class="inputSm" type="text" />
                            </td>
                            <td class="tdSm">身份证：</td>
                            <td>
                                <input name="idCard" id="idCard" type="text" />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">排行：</td>
                            <td>
                                <input name="familyRank" id="familyRank" class="inputSm" type="text" value="0" />
                            </td>
                            <td class="tdXSm">号：</td>
                            <td>
                                <input name="artName" id="artName" class="inputSm" type="text" />
                            </td>
                            <td class="tdSm">电话：</td>
                            <td>
                                <input name="phone" id="phone" type="text" />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">字辈：</td>
                            <td>
                                <input name="generationActor" id="generationActor" class="inputSm" type="text" />
                            </td>
                            <td class="tdXSm">行：</td>
                            <td>
                                <input name="xing" id="xing" class="inputSm" type="text" />
                            </td>
                            <td class="tdSm">邮箱：</td>
                            <td>
                                <input name="email" id="email" type="text" />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">国籍：</td>
                            <td colspan="3">
                                <input name="nationality" id="nationality" type="text" />
                            </td>
                            <td class="tdSm">父亲：</td>
                            <td style="text-align: left">
                                <select name="fatherId" id="fatherId" style="max-width:135px" >

                                </select>
                                <%--<input name="fatherId" id="fatherId" type="text" value="0" />--%>
                            </td>
                        </tr>
                        <tr class="bottomBorder rightBorder">
                            <td class="tdSm">民族：</td>
                            <td colspan="3">
                                <input name="nation" id="nation" type="text" />
                            </td>
                            <td class="tdSm">母亲：</td>
                            <td style="text-align: left">
                                <select name="motherId" id="motherId" style="max-width:135px">

                                </select>
                                <%--<input name="motherId" id="motherId" type="text" value="0" />--%>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder leftBorder">
                            <td>出生时间：</td>
                            <td colspan="7">
                                <input name="birth_time" id="birth_time" class="form-datetime inputLg" type="text" />
                            </td>
                            <td rowspan="5" style="text-align: center">状态：<br/>
                                <label><input type="radio" name="state" checked value="1" />&nbsp;在世</label>
                                <br/>
                                <label><input type="radio" name="state" value="0" />&nbsp;已逝</label>
                            </td>
                        </tr>
                        <tr class="rightBorder leftBorder">
                            <td>出生地点：</td>
                            <td colspan="7">
                                <input name="birthAddr" id="birthAddr" class="inputLg" type="text" />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder">
                            <td>去世时间：</td>
                            <td colspan="7">
                                <input name="die_time" id="die_time" class="form-datetime inputLg" type="text" />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder">
                            <td>卒葬地点：</td>
                            <td colspan="7">
                                <input name="dieAddr" id="dieAddr" class="inputLg" type="text" />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder bottomBorder">
                            <td>居住地址：</td>
                            <td colspan="7">
                                <input name="liveAddr" id="liveAddr" class="inputLg" type="text" />
                            </td>
                        </tr>
                        <tr style="height: 10px;"><td colspan="9"></td></tr>
                        <tr>
                            <td colspan="9" style="text-align: right">
                                <input id="savePeople" type="button" class="btn btn-primary" value="保 存" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 收录族谱 Modal -->
<div class="modal fade" id="includeModal" tabindex="-1" role="dialog" aria-labelledby="includeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="includeModalLabel">收录族谱</h4>
            </div>
            <div class="modal-body">
                <form id="includeForm" method="post">
                    <div class="form-group" id="targetFamilyDiv">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="forInclude">提 交</button>
                <button class="btn btn-default" id="closeModal" data-dismiss="modal">取 消</button>
            </div>
        </div>
    </div>
</div>
<!-- 选择打印代数 Modal -->
<div class="modal fade" id="printModal" tabindex="-1" role="dialog" aria-labelledby="printModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="printModalLabel">打印族谱,选择打印代数</h4>
            </div>
            <div class="modal-body">
                <form id="printGenForm" method="post" target="_blank" action="">
                    <input id="printFamilyId" name="printFamilyId" value="${familyId}" type="hidden"/>
                    <input id="maxGen" name="maxGen" value="${maxGeneration}" type="hidden"/>
                    <div class="form-group">
                        打印从第
                        <input class="form-control input-sm" id="beginGen" name="beginGen" value="1" style="width: 50px;display: inline;" />
                        代到第
                        <input class="form-control input-sm" id="endGen" name="endGen" value="${maxGeneration}" style="width: 50px;display: inline;" />
                        代的族人
                    </div>
                    <div class="form-group">
                        是否添加家族说明/介绍：
                        <label><input type="radio" id="isAddIntroOk" name="isAddIntro" value="1" checked />&nbsp;是</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <label><input type="radio" id="isAddIntroNO" name="isAddIntro" value="0" />&nbsp;否</label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="toPrint">提 交</button>
                <button class="btn btn-default" id="closePrintModal" data-dismiss="modal">取 消</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="common/springUrl.jsp"%>
<%--<%@include file="common/footer.jsp" %>--%>
<%@include file="common/commonJS.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/viewFamilyTree_include.js"></script>
<script type="text/javascript">
    var familyId = "${familyId}";
    var maxGeneration = "${maxGeneration}";
    var familyFirstName = "${tFamily.familyFirstName}";
    $(function () {
        $("#goBack").click(function () {
            window.history.back();
        });
        setTimeout(function() {
            $('#imgFile').uploadify({
                'swf': projectUrl + '/static/uploadify/uploadify.swf?ver=' + Math.random(),
                'uploader': projectUrl + '/upload/uploadImg?ver=' + Math.random(),
                'cancelImg': projectUrl + '/static/uploadify/cancel.png',
                'auto': true,
                "formData": {targetFile: '/upload/personImg'},
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
        }, 10);
    });
</script>
</body>
</html>
