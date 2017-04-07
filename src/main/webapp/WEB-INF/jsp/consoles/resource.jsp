<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/25 0025
  Time: 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>资源设置</title>
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<table id="resourceList" class="easyui-treegrid" style="width:100%;height:100%"
       title="资源列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true">

</table>
<div id="tb">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel">删除</a>
</div>
<div id="resourceDialog" class="easyui-dialog" title="资源信息" style="width:100px;height:100px;padding:10px;top: 10%;left: 15%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="resourceForm" method="post">
            <input type="hidden" id="resourceId" name="id" value="0" />
            <input type="hidden" id="sourceLevel" name="sourceLevel" value="0" />
            <input type="hidden" id="sourceType" name="sourceType" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>资源名称:</td>
                    <td><input class="easyui-validatebox" type="text" id="sourceName" name="sourceName" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>上级资源:</td>
                    <td><input class="easyui-combobox" id="parentSourceId" name="parentSourceId" data-options="required:true,valueField:'id',textField:'sourceName'" value="0" /></td>
                </tr>
                <tr>
                    <td>资源链接:</td>
                    <td><input class="easyui-validatebox" id="sourceUrl" name="sourceUrl" value="/" /></td>
                </tr>
                <tr>
                    <td>资源说明:</td>
                    <td><input class="easyui-validatebox" id="sourceDesc" name="sourceDesc"/></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td>
                        <select id="state" name="state" class="easyui-combobox" style="width:150px">
                            <option value="1">可用</option>
                            <option value="0">不可用</option>
                        </select>
                    </td>
                </tr>

            </table>
        </form>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/resourceSetting.js"></script>
</body>
</html>
