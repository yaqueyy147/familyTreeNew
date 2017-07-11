
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
    <style rel="stylesheet">
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
<div class="easyui-layout" style="width:100%;height:100%;">

    <%--<div data-options="region:'center'" style="width:100%;height:5%">--%>
        <%--<button type="button" id="localBack" class="btn btn-primary">返回</button>--%>
    <%--</div>--%>
    <div data-options="region:'center',split:true" title="申请收录的族谱" style="width:50%;position: relative">
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div id="primaryDesc" data-options="region:'north'" style="height:20%">
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">
                    家族名：${primaryFamily.familyName}
                    <span style="margin-left: 20px">家族人数：&nbsp;<span id="peopleCount">0</span>&nbsp;人</span>
                    <span style="margin-left: 20px">家族代数：&nbsp;<span id="familyGenNum">${maxGen}</span>&nbsp;代</span>
                    <button type="button" id="localBack" class="easyui-linkbutton" style="margin-left: 20px">返回</button>
                    <c:choose>
                        <c:when test="${merge.mergeState == 2 || merge.mergeState == 0}">
                            <button type="button" id="acceptIn" class="easyui-linkbutton" style="margin-left: 20px">同意开放补录</button>
                            <button type="button" id="reject" class="easyui-linkbutton" style="margin-left: 20px">驳回</button>
                        </c:when>
                        <c:when test="${merge.mergeState == 5}">
                            <span id="supplementDesc" class="easyui-linkbutton" style="margin-left: 20px">补录中...</span>
                            
                            <button type="button" id="batchAcceptIn" class="easyui-linkbutton" style="margin-left: 20px" onclick="batchAudit(this,1)">批量通过审核</button>
                            <button type="button" id="batchRefuseIn" class="easyui-linkbutton" style="margin-left: 20px" onclick="batchAudit(this,7)">批量不通过审核</button>
                            
                            <button type="button" id="completeIn" class="easyui-linkbutton" style="margin-left: 20px">完成收录,关闭补录</button>
                        </c:when>
                        <c:when test="${merge.mergeState == 1}">
                            <span class="easyui-linkbutton" style="margin-left: 20px">收录完成</span>
                        </c:when>
                    </c:choose>

                </p>
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">家族属地：${primaryFamily.province}${primaryFamily.city}${primaryFamily.district}
                </p>
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">家族简介:${primaryFamily.familyDesc}</p>

            </div>
            <div data-options="region:'south',split:true" style="height:80%;">
                <div class="ztree" id="primaryFamilyTree"></div>
            </div>

        </div>

    </div>
    <%--<div data-options="region:'east',split:true" title="可选择收录的族谱" style="width:50%;">--%>

        <%--<div class="easyui-layout" style="width:100%;height:100%;">--%>
            <%--<div data-options="region:'north'" style="height:20%">--%>
                <%--<p style="margin-bottom: 1px;padding-bottom: 1px">家族名：--%>
                    <%--<select id="targetFamily"></select>--%>
                <%--</p>--%>
                <%--<p id="targetFamilyAddr" style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;"></p>--%>
                <%--<p id="targetFamilyDesc" style="margin-top: 1px;padding-top: 1px;"></p>--%>

            <%--</div>--%>
            <%--<div data-options="region:'south',split:true" style="height:80%;">--%>
                <%--<div class="ztree" id="targetFamilyTree"></div>--%>
            <%--</div>--%>

        <%--</div>--%>
    <%--</div>--%>
    <%--<div data-options="region:'south'" style="width:100%;height:5%">--%>
        <%--<button type="button" id="confirmInclude" class="btn btn-primary">收录完成</button>--%>
    <%--</div>--%>
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
                                第<input id="generation" name="generation" type="text" style="width:20px" value="0" disabled />代
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
                                <input name="id" id="id" type="hidden" value="0" />
                                <input name="mateId" id="mateId" type="hidden"/>
                                <input name="peopleType" id="peopleType" type="hidden" value="1" />
                                <span id="peopleInfo"></span>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder">
                            <td class="photoBox" rowspan="8" colspan="3" style="text-align: center" id="photo">
                                <img id="result_img" height="128px" width="128px" />
                            </td>
                            <td class="tdSm">
                                姓名：
                            </td>
                            <td colspan="3">
                                <input name="name" id="name" type="text" value="何" disabled/>
                            </td>
                            <td class="tdSm">
                                特殊说明：
                            </td>
                            <td><input name="specialRemark" id="specialRemark" type="text" disabled /></td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">
                                曾用名：
                            </td>
                            <td colspan="3">
                                <input name="usedName" id="usedName" type="text" disabled/>
                            </td>
                            <td class="tdSm">
                                学历：
                            </td>
                            <td>
                                <input name="education" id="education" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">性别：</td>
                            <td style="text-align: left">
                                <select name="sex" id="sex" disabled>
                                    <option value="1">男</option>
                                    <option value="0">女</option>
                                </select>
                            </td>
                            <td colspan="2">
                                <select name="mateType" id="mateType" disabled>
                                    <option value="0"></option>
                                    <option value="1">妻子</option>
                                    <option value="2">丈夫</option>
                                </select>
                            </td>
                            <td class="tdSm">
                                职业：
                            </td>
                            <td>
                                <input name="job" id="job" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">辈分：</td>
                            <td class="tdXSm">
                                <input name="familyGeneration" id="familyGeneration" class="inputSm" type="text" value="0" disabled />
                            </td>
                            <td class="tdXSm">字：</td>
                            <td class="tdXSm">
                                <input name="cName" id="cName" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdSm">身份证：</td>
                            <td>
                                <input name="idCard" id="idCard" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">排行：</td>
                            <td>
                                <input name="familyRank" id="familyRank" class="inputSm" type="text" value="0" disabled />
                            </td>
                            <td class="tdXSm">号：</td>
                            <td>
                                <input name="artName" id="artName" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdSm">电话：</td>
                            <td>
                                <input name="phone" id="phone" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">字辈：</td>
                            <td>
                                <input name="generationActor" id="generationActor" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdXSm">行：</td>
                            <td>
                                <input name="xing" id="xing" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdSm">邮箱：</td>
                            <td>
                                <input name="email" id="email" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">国籍：</td>
                            <td colspan="3">
                                <input name="nationality" id="nationality" type="text" disabled />
                            </td>
                            <td class="tdSm">父亲：</td>
                            <td style="text-align: left">
                                <select name="fatherId" id="fatherId" disabled style="max-width:23px">

                                </select>
                                <%--<input name="fatherId" id="fatherId" type="text" value="0" />--%>
                            </td>
                        </tr>
                        <tr class="bottomBorder rightBorder">
                            <td class="tdSm">民族：</td>
                            <td colspan="3">
                                <input name="nation" id="nation" type="text" disabled />
                            </td>
                            <td class="tdSm">母亲：</td>
                            <td style="text-align: left">
                                <select name="motherId" id="motherId" disabled style="max-width:23px">

                                </select>
                                <%--<input name="motherId" id="motherId" type="text" value="0" />--%>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder leftBorder">
                            <td>出生时间：</td>
                            <td colspan="7">
                                <input name="birth_time" id="birth_time" class="form-datetime inputLg" type="text" disabled />
                            </td>
                            <td rowspan="5" style="text-align: center">状态：<br/>
                                <label><input type="radio" name="state" checked value="1" disabled />&nbsp;在世</label>
                                <br/>
                                <label><input type="radio" name="state" value="0" disabled />&nbsp;已逝</label>
                            </td>
                        </tr>
                        <tr class="rightBorder leftBorder">
                            <td>出生地点：</td>
                            <td colspan="7">
                                <input name="birthAddr" id="birthAddr" class="inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder">
                            <td>去世时间：</td>
                            <td colspan="7">
                                <input name="die_time" id="die_time" class="form-datetime inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder">
                            <td>卒葬地点：</td>
                            <td colspan="7">
                                <input name="dieAddr" id="dieAddr" class="inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder bottomBorder">
                            <td>居住地址：</td>
                            <td colspan="7">
                                <input name="liveAddr" id="liveAddr" class="inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <%--<tr style="height: 15px;"><td colspan="9"></td></tr>--%>
                        <%--<tr>--%>
                            <%--<td colspan="9" style="text-align: right">--%>
                                <%--<input id="savePeople" type="button" class="btn btn-primary" value="保 存" />--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="rejectDialog" class="easyui-dialog" title="驳回" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="rejectForm" method="post">
            <input class="easyui-validatebox" type="hidden" id="mergeId" name="id" value="${mergeId}" />
            <table cellpadding="5">
                <tr>
                    <td>驳回说明:</td>
                    <td>
                        <%--<input class="easyui-validatebox" id="userDesc" name="userDesc" data-options="multiline:true" style="height:60px" />--%>
                        <textarea class="text-area easyui-validatebox" id="rejectDesc" name="rejectDesc" rows="3" cols="50"></textarea>
                    </td>
                </tr>

            </table>
        </form>
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

    });
</script>
</body>
</html>
