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
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toModifyPassword" >修改密码</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
</div>
<div id="userDialog" class="easyui-dialog" title="用户信息" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="userInfoForm" method="post">
            <input class="easyui-validatebox" type="hidden" id="userId" name="id" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>账号:</td>
                    <td><input class="easyui-validatebox" type="text" id="userName" name="userName" data-options="required:true" /></td>
                    <td>昵称:</td>
                    <td><input class="easyui-validatebox" type="text" id="userNickName" name="userNickName" /></td>
                </tr>
                <tr id="passwordTr">
                    <td>密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="userPassword" name="userPassword" value="123456" data-options="required:true" /></td>
                    <td>确认密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="userPasswordAffirm" name="userPasswordAffirm" value="123456" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="userPhone" name="userPhone"/>
                    </td>
                    <td>邮箱:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="userEmail" name="userEmail" />
                    </td>
                </tr>
                <tr>
                    <td>QQ:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="userQq" name="userQq"/>
                    </td>
                    <td>微信:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="userWechart" name="userWechart" />
                    </td>
                </tr>
                <tr>
                    <td>用户说明:</td>
                    <td>
                        <%--<input class="easyui-validatebox" id="userDesc" name="userDesc" data-options="multiline:true" style="height:60px" />--%>
                        <textarea class="text-area easyui-validatebox" id="userDesc" name="userDesc" rows="3" cols="14"></textarea>
                    </td>
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
<div id="modifyPasswordDialog" class="easyui-dialog" title="修改密码" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="modifyPasswordForm" method="post">
            <input type="hidden" id="userIdForModify" name="id" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>新密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="newPassword" name="newPassword" value="123456" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>确认新密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="newPasswordAffirm" name="newPasswordAffirm" value="123456" data-options="required:true" /></td>
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
