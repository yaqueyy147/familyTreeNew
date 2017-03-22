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
    <title>角色设置</title>
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<table id="roleList" class="easyui-datagrid" style="width:100%;height:100%"
       title="角色列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">

</table>
<div id="tb">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel">删除</a>
</div>
<div id="roleDialog" class="easyui-dialog" title="角色信息" style="width:100px;height:100px;padding:10px;top: 10%;left: 15%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="roleForm" method="post">
            <input type="hidden" id="roleId" name="id" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>角色名称:</td>
                    <td><input class="easyui-validatebox" type="text" id="roleName" name="roleName" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>角色说明:</td>
                    <td><input class="easyui-validatebox" id="roleDesc" name="roleDesc" data-options="multiline:true" /></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td>
                        <select id="state" name="state" class="easyui-combobox" style="width:100px">
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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/roleSetting.js"></script>
</body>
</html>
