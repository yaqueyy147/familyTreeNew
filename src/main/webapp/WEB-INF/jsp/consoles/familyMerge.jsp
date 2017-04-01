
<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2017/1/11
  Time: 下午9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>族谱收录</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/datetime-picker/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/fonts/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/fronts/viewFamilyTree.css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:90%;">
    <div data-options="region:'north'" style="width:100%;height:5%">
        <button type="button" id="localBack" class="btn btn-primary">返回</button>
    </div>
    <div data-options="region:'west',split:true" title="申请收录的族谱" style="width:50%;position: relative">
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'north'" style="height:20%">
                <p style="margin-bottom: 1px;padding-bottom: 1px">家族名：${primaryFamily.familyName}</p>
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">家族属地：${primaryFamily.province}${primaryFamily.city}${primaryFamily.district}
                </p>
                <p style="margin-top: 1px;padding-top: 1px;">${primaryFamily.familyDesc}</p>

            </div>
            <div data-options="region:'south',split:true" style="height:80%;">
                <div class="ztree" id="primaryFamilyTree"></div>
            </div>

        </div>

    </div>
    <div data-options="region:'east',split:true" title="可选择收录的族谱" style="width:50%;">

        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'north'" style="height:20%">
                <p style="margin-bottom: 1px;padding-bottom: 1px">家族名：
                    <select id="targetFamily"></select>
                </p>
                <p id="targetFamilyAddr" style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;"></p>
                <p id="targetFamilyDesc" style="margin-top: 1px;padding-top: 1px;"></p>

            </div>
            <div data-options="region:'south',split:true" style="height:80%;">
                <div class="ztree" id="targetFamilyTree"></div>
            </div>

        </div>
    </div>
    <div data-options="region:'south'" style="width:100%;height:5%">
        <button type="button" id="confirmInclude" class="btn btn-primary">收录完成</button>
    </div>
</div>
<!-- 编辑族人 Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addModalLabel">编辑族人</h4>
            </div>
            <div class="modal-body">
                <form id="peopleForm" action="/family/savePeople" method="post">
                    <table>
                        <tr style="border: solid 2px #9d9d9d;">
                            <td class="tdLg" style="text-align: left" colspan="9">
                                <input name="familyId" id="familyId" type="hidden" value="${familyId}" />

                                &nbsp;&nbsp;
                                第
                                <select id="generation" name="generation" style="width:35px">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                </select>
                                代
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input name="id" id="id" type="hidden" value="0" />
                                <input name="mateId" id="mateId" type="hidden"/>
                                <input name="peopleType" id="peopleType" type="hidden" value="1" />
                                <input name="userCC" id="userCC" type="hidden" value="1" />
                                <span id="peopleInfo"></span>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder">
                            <td class="photoBox" rowspan="8" colspan="3" style="text-align: center" id="photo">
                                <div id="progress_bar" style="display: none"></div>
                                <input id="photoUrl" name="photoUrl" type="hidden" />
                                <input type="file" name="imgFile" id="imgFile" />
                                <a id="show_img"><img style="display: none;" id="result_img" class="img-responsive" /></a>
                            </td>
                            <td class="tdSm">
                                姓名：
                            </td>
                            <td colspan="3">
                                <input name="name" id="name" type="text"/>
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
                                <select name="fatherId" id="fatherId">

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
                                <select name="motherId" id="motherId">

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
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
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
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/datetime-picker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/datetime-picker/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/commonUtilJs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyMerge.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script>
    var familyId = "${primaryFamily.id}";
    $(function () {
        $('#imgFile').uploadify({
            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
            'uploader'      : projectUrl + '/upload/uploadImg',
            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
            'auto'          : true,
            "formData"      : {targetFile : '/upload/personImg'},
            'queueID'       : 'progress_bar',
            'fileObjName'   : 'uploadFile',
            "buttonCursor"  : "hand",
            "buttonText"    : "选择图片",
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
