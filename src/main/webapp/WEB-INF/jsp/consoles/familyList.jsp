<%@ page import="java.util.Date" %>
<%@ page import="com.witkey.familyTree.util.CommonUtil" %>
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
    <title>族谱</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>

<table id="familyList" class="easyui-datagrid" style="width:100%;height:98%"
       title="族谱列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
</table>
<div id="tb">
    <span>族谱名:</span>
    <input id="familyName4Search" name="familyName" style="line-height:26px;border:1px solid #ccc;height: 23px;">
    <span>族谱属地:</span>
    <span data-toggle="distpicker">
        <select id="province4Search" name="province" style="line-height:26px;border:1px solid #ccc" data-province="---- 选择省 ----"></select>
        <select id="city4Search" name="city" style="line-height:26px;border:1px solid #ccc" data-city="---- 选择市 ----"></select>
        <select id="district4Search" name="district" style="line-height:26px;border:1px solid #ccc" data-district="---- 选择区 ----"></select>
    </span>

    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="doSearch">查询</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
</div>

<div id="familyDialog" class="easyui-dialog" title="族谱信息" style="width:400px;height:200px;padding:10px;top: 0;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="familyForm" method="post">
            <input type="hidden" id="familyId" name="id" value="0" />
            <input type="hidden" id="createMan" name="createMan" value="" />
            <input type="hidden" id="createTime4Modify" name="createTime4Modify" value="" />
            <input type="hidden" id="familyArea" name="familyArea" value="0" />
            <table cellpadding="5" style="width: 100%">
                <tr>
                    <td>家族姓氏:</td>
                    <td><input type="text" id="familyFirstName" name="familyFirstName" value="何" /></td>
                    <td>家族名称:</td>
                    <td><input type="text" id="familyName" name="familyName" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>访问状态:</td>
                    <td>
                        <label><input type="radio" name="visitStatus" value="1" checked="checked" />&nbsp;开放</label>
                        <label><input type="radio" name="visitStatus" value="0" />&nbsp;加密</label>
                    </td>
                    <td id="visitPwdTitle" style="display: none">访问密码:</td>
                    <td id="visitPwd" style="display: none"><input type="password" id="visitPassword" name="visitPassword" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>家族所在地:</td>
                    <td data-toggle="distpicker" colspan="3">
                        <select id="province" name="province" data-province="---- 选择省 ----"></select>
                        <select id="city" name="city" data-city="---- 选择市 ----"></select>
                        <select id="district" name="district" data-district="---- 选择区 ----"></select>
                    </td>
                </tr>
                <tr>
                    <td>家族简介:</td>
                    <td colspan="3">
                        <%--<input class="easyui-validatebox" id="userDesc" name="userDesc" data-options="multiline:true" style="height:60px" />--%>
                        <textarea class="text-area" id="familyDesc" name="familyDesc" rows="3" cols="40"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td>
                        <select id="familyState" name="state" style="width:100px">
                            <option value="1" selected>可用</option>
                            <option value="0">不可用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>展示图片:</td>
                    <td colspan="3">
                        <div id="progress_bar" style="display: none"></div>
                        <input id="photoUrl" name="photoUrl" type="hidden" />
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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyList.js"></script>
<script>
    $(function () {
        $('#imgFile').uploadify({
            'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
            'uploader'      : projectUrl + '/upload/uploadImg',
            'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
            'auto'          : true,
            "formData"      : {targetFile : '/static/upload/familyImg'},
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
