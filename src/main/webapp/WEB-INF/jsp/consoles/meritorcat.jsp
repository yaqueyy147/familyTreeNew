
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
    <title>英才录</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />
    <%@include file="common/commonCss.jsp"%>
    <style>
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
    </style>
</head>
<body>
<div class="loading">
    <div>加载中,请稍后...</div>
</div>
    <table id="meritorcatList" class="easyui-datagrid" style="width:100%;height:98%"
           title="何氏英才录" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    </table>
    <div id="tb">
        <span>英才名:</span>
        <input id="meritocratName4Search" name="meritocratName4Search" style="line-height:26px;border:1px solid #ccc;height: 23px;">
        <span>英才所在地:</span>
        <span data-toggle="distpicker">
            <select id="province" name="province">
                <option value="">全部属地</option>
                <c:if test="${meritorcatArea != null}">
                    <c:forEach var="mAddr" items="${meritorcatArea}">
                        <option value="${mAddr.meritocrat_area}">${mAddr.meritocrat_area}</option>
                    </c:forEach>
                </c:if>
            </select>
        </span>
        <span>英才属性:</span>
        <span data-toggle="distpicker">
            <select id="meritocratAttrId4Search" name="meritocratAttrId">
                    <option value="">全部属性</option>
                    <c:if test="${meritorcatAttr != null}">
                        <c:forEach var="mAttr" items="${meritorcatAttr}">
                            <option value="${mAttr.id}">${mAttr.meritocratAttr}</option>
                        </c:forEach>
                    </c:if>
                </select>
        </span>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="doSearch">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
    </div>
    <div id="meritocratDialog" class="easyui-dialog" title="英才信息" style="padding:10px;top: 0;left: 20%;">
        <div style="padding:10px 40px 20px 40px">
            <form id="meritocratForm" method="post">
                <input type="hidden" id="meritocratId" name="id" value="0" />
                <table cellpadding="5">
                    <tr>
                        <td>英才姓名:</td>
                        <td><input class="easyui-validatebox" type="text" id="meritocratName" name="meritocratName" data-options="required:true" /></td>
                        <td>英才属地:</td>
                        <td>
                            <input id="meritocratArea" class="easyui-combobox" name="meritocratArea"
                                   data-options="valueField:'id',textField:'text'">
                            <%--<select id="meritocratArea" name="meritocratArea" class="easyui-combobox" style="width:150px" data-options="valueField:'id', textField:'text'">--%>
                                <%--<option value="">无</option>--%>
                            <%--</select>--%>
                        </td>
                    </tr>
                    <tr>
                        <td>英才类型:</td>
                        <td>
                            <select id="meritocratAttrId" name="meritocratAttrId" class="easyui-combobox" style="width:150px">
                                <c:if test="${meritorcatAttr != null}">
                                    <c:forEach var="mAttr" items="${meritorcatAttr}">
                                        <option value="${mAttr.id}">${mAttr.meritocratAttr}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </td>
                        <td>邮编:</td>
                        <td><input class="easyui-validatebox" type="text" id="postCode" name="postCode" /></td>
                    </tr>
                    <tr>
                        <td>联系电话:</td>
                        <td><input class="easyui-validatebox" type="text" id="phone" name="phone" /></td>
                        <td>传真:</td>
                        <td><input class="easyui-validatebox" type="text" id="fax" name="fax" /></td>
                    </tr>
                    <tr>
                        <td>详细地址:</td>
                        <td colspan="3">
                            <input class="easyui-validatebox" type="text" id="meritocratAddr" name="meritocratAddr" style="width: 380px" />
                        </td>
                    </tr>
                    <tr>
                        <td>英才简介:</td>
                        <td colspan="3">
                            <textarea class="text-area easyui-validatebox" id="meritocratDesc" name="meritocratDesc" rows="5" cols="40"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>英才头像:</td>
                        <td colspan="3">
                            <div id="progress_bar" style="display: none"></div>
                            <input id="photo" name="photo" type="hidden" class="easyui-validatebox" />
                            <div class="row">
                                <div style="width: 150px">
                                    <input type="file" name="imgFile" id="imgFile" />
                                    <a id="show_img"><img style="display: none;width: 150px;height: 150px;" id="result_img" /></a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/meritorcat.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>>
    <script>
        $(function () {
            setTimeout(function() {
                $('#imgFile').uploadify({
                    'swf': projectUrl + '/static/uploadify/uploadify.swf',
                    'uploader': projectUrl + '/upload/uploadImg',
                    'cancelImg': projectUrl + '/static/uploadify/cancel.png',
                    'auto': true,
                    "formData": {targetFile: '/upload/familyImg'},
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
                        $("#photo").attr('value', imgPath);
                        $("#show_img").mouseover(function () {
                            $("#result_img").attr('src', projectUrl + "/static/images/deleteImg.png");
                        });
                        $("#show_img").mouseout(function () {
                            $("#result_img").attr('src', imgPath);
                        });
                        $("#result_img").click(function () {
                            $("#result_img").hide();
                            $("#imgFile").show();
                            $("#photo").removeAttr('value');
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
