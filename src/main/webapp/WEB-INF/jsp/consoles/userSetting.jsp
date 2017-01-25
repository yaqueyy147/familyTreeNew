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
    <title>用户设置</title>
    <%@include file="common/commonCss.html"%>
</head>
<body>
<table id="userList" class="easyui-datagrid" style="width:100%;height:100%"
       title="用户列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10"></table>
<div id="tb">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:alert('edit')">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:alert('del')">删除</a>
</div>
<div id="userDialog" class="easyui-dialog" title="用户信息" style="width:400px;height:200px;padding:10px;top: 25%;left: 30%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="userInfoForm" method="post">
            <input type="hidden" name="id" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>账号:</td>
                    <td><input class="easyui-textbox" type="text" name="userName" data-options="required:true" /></td>
                    <td>昵称:</td>
                    <td><input class="easyui-textbox" type="text" name="userNickName" /></td>
                </tr>
                <tr>
                    <td>密码:</td>
                    <td><input class="easyui-textbox" type="password" name="userPassword" value="123456" data-options="required:true" /></td>
                    <td>确认密码:</td>
                    <td><input class="easyui-textbox" type="password" name="userPasswordAffirm" value="123456" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="userPhone"/>
                    </td>
                    <td>邮箱:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="userEmail" />
                    </td>
                </tr>
                <tr>
                    <td>QQ:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="userQq"/>
                    </td>
                    <td>微信:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="userWechart" />
                    </td>
                </tr>
                <tr>
                    <td>用户说明:</td>
                    <td><input class="easyui-textbox" name="userDesc" data-options="multiline:true" style="height:60px" /></td>
                    <td>状态:</td>
                    <td>
                        <select name="state">
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
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/userSetting.js"></script>
</body>
</html>
