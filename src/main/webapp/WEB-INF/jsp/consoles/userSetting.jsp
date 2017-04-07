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
    <%@include file="common/commonCss.jsp"%>
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
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toSetAuth" >权限设置</a>
</div>
<div id="userDialog" class="easyui-dialog" title="用户信息" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="userInfoForm" method="post">
            <input class="easyui-validatebox" type="hidden" id="userId" name="id" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>登录账号:</td>
                    <td><input class="easyui-validatebox" type="text" id="loginName" name="loginName" data-options="required:true" /></td>
                    <td>用户名/真实姓名:</td>
                    <td><input class="easyui-validatebox" type="text" id="userName" name="userName" /></td>
                </tr>
                <tr id="passwordTr">
                    <td>密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="password" name="password" value="123456" data-options="required:true" /></td>
                    <td>确认密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="passwordAffirm" name="passwordAffirm" value="123456" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="phone" name="phone"/>
                    </td>
                    <td>QQ:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="qqNum" name="qqNum"/>
                    </td>
                </tr>
                <tr>
                    <td>微信:</td>
                    <td>
                        <input class="easyui-validatebox" type="text" id="wechart" name="wechart" />
                    </td>
                    <td>状态:</td>
                    <td>
                        <select id="state" name="state" class="easyui-combobox" style="width:150px">
                            <option value="1">可用</option>
                            <option value="9">不可用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>是否可登录前台:</td>
                    <td>
                        <select id="isFront" name="isFront" class="easyui-combobox" style="width:150px">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                    <td>是否可登录后台:</td>
                    <td>
                        <select id="isConsole" name="isConsole" class="easyui-combobox" style="width:150px">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>

                </tr>
                <tr>
                    <td>是否可修族谱:</td>
                    <td>
                        <select id="isVolunteer" name="isVolunteer" class="easyui-combobox" style="width:150px">
                            <option value="1">是</option>
                            <option value="0">否</option>
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

<div id="resourceDialog" class="easyui-dialog" title="权限设置" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;">
    <input type="hidden" id="userId4Tree" />
    <div id="resourceTree" class="ztree" style="padding:10px 40px 20px 40px" >

    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/userSetting.js"></script>
</body>
</html>
