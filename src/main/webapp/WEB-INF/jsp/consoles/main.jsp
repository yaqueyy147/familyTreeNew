<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/14 0014
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>何氏族谱管理系统</title>
    <%@ include file="common/commonCss.jsp" %>
    <style type="text/css">

        body,html{
            background: url("<%=request.getContextPath()%>/static/images/bg-front.jpg") no-repeat;
            filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
            -webkit-background-size:cover;
            -moz-background-size:cover;
            background-size:cover;
            background-attachment:fixed;
        }

        .easyui-tabs div iframe{
            width:100%;
            border: none;
        }
        #topRegion{
            background-color: #e3d4b7;
        }
        .userInfo{
            float: right;
            margin-top: 10px;
            margin-right: 20px;
        }
        #logoDiv{
            font-weight: bold;
            font-size: 22px;
            margin-left: 20px;
            margin-top: 10px;
            width:30%;
            float: left;
        }
    </style>
</head>
<body class="layout">
<div class="easyui-layout" style="width:100%;height:100%;">
    <div id="topRegion" data-options="region:'north'" style="height:50px;">
        <div id="logoDiv">寻根问祖</div>
        <div id="userInfoDiv" class="userInfo">
            <a href="javascript:void 0;" class="easyui-menubutton"
               data-options="menu:'#mm'">
                <c:if test="${empty consoleUserInfo.userName}">
                    ${consoleUserInfo.loginName}
                </c:if>
                <c:if test="${!empty consoleUserInfo.userName}">
                    ${consoleUserInfo.userName}
                </c:if>
            </a>
            <div id="mm" style="width:150px;">
                <div id="toEditUser">编辑用户信息</div>
                <div id="toModifyPassword">修改密码</div>
            </div>
            &nbsp;|&nbsp;
            <a href="<%=request.getContextPath()%>/consoles/logout">退出</a>
        </div>
    </div>

    <div data-options="region:'west',split:true" title="菜单" style="width:15%;">
        <ul id="menuTT" class="easyui-tree">
            <li>
                <span>族谱管理</span>
                <ul>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('volunteerList','注册用户审核','/consoles/volunteer')">注册用户审核</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('companyList','赞助商列表','/consoles/company')">赞助商列表</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('familyList','族谱列表','/consoles/family')">族谱列表</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('rankList','积分排行榜','/consoles/rank')">积分排行榜</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('meritorcat','何氏英才录','/consoles/meritorcat')">何氏英才录</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('meritorcatAttr','英才属性设置','/consoles/meritorcatAttr')">英才属性设置</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('merge','收录族谱','/consoles/merge')">收录族谱</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('pointsRelation','积分对应关系','/consoles/pointsRelation')">积分对应关系</a></span></li>
                </ul>
            </li>
            <c:if test="${consoleUserInfo.loginName == 'admin'}">
            <li><span>用户管理</span>
                <ul>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('userSetting','用户设置','/consoles/user')">用户设置</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('roleSetting','角色设置','/consoles/role')">角色设置</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('log','日志','/log/index')">日志</a></span></li>
                </ul>
            </li>
            </c:if>
        </ul>
    </div>
    <div data-options="region:'center'">
        <div id="tabTT" class="easyui-tabs" data-options="border:false,fit:true">
            <div title="首页" data-options="closable:false" style="padding:10px"></div>
            <%--<div id="volunteerList" title="志愿者审核" data-options="closable:true" selected>--%>
                <%--<iframe src="<%=request.getContextPath()%>/consoles/volunteerList"></iframe>--%>
            <%--</div>--%>
        </div>
    </div>
    <div data-options="region:'south',split:true"></div>
</div>

<div id="userDialog" class="easyui-dialog" title="用户信息" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="userInfoForm" method="post">
            <input type="hidden" id="userId" name="id" value="${consoleUserInfo.id}" />
            <input type="hidden" id="state" name="state" value="${consoleUserInfo.state}" />
            <table cellpadding="5">
                <tr>
                    <td>账号:</td>
                    <td><input class="easyui-validatebox" value="${consoleUserInfo.userName}" type="text" id="userName" name="userName" data-options="required:true" readonly /></td>
                    <td>昵称:</td>
                    <td><input class="easyui-validatebox" value="${consoleUserInfo.userNickName}" type="text" id="userNickName" name="userNickName" /></td>
                </tr>
                <tr id="passwordTr" style="display: none">
                    <td>密码:</td>
                    <td><input class="easyui-validatebox" value="${consoleUserInfo.userPassword}" type="password" id="userPassword" name="userPassword" value="123456" data-options="required:true" /></td>
                    <td>确认密码:</td>
                    <td><input class="easyui-validatebox" value="${consoleUserInfo.userPassword}" type="password" id="userPasswordAffirm" name="userPasswordAffirm" value="123456" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input class="easyui-validatebox" value="${consoleUserInfo.userPhone}" type="text" id="userPhone" name="userPhone"/>
                    </td>
                    <td>邮箱:</td>
                    <td>
                        <input class="easyui-validatebox" value="${consoleUserInfo.userEmail}" type="text" id="userEmail" name="userEmail" />
                    </td>
                </tr>
                <tr>
                    <td>QQ:</td>
                    <td>
                        <input class="easyui-validatebox" value="${consoleUserInfo.userQq}" type="text" id="userQq" name="userQq"/>
                    </td>
                    <td>微信:</td>
                    <td>
                        <input class="easyui-validatebox" value="${consoleUserInfo.userWechart}" type="text" id="userWechart" name="userWechart" />
                    </td>
                </tr>
                <tr>
                    <td>用户说明:</td>
                    <td>
                        <%--<input class="easyui-validatebox" id="userDesc" name="userDesc" data-options="multiline:true" style="height:60px" />--%>
                        <textarea class="text-area easyui-validatebox" id="userDesc" name="userDesc" rows="3" cols="14">
                            ${consoleUserInfo.userDesc}
                        </textarea>
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
                    <td>新原密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="oldPassword" name="oldPassword" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>新密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="newPassword" name="newPassword" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>确认新密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="newPasswordAffirm" name="newPasswordAffirm" data-options="required:true" /></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@ include file="common/commonJs.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/mainJs.js"></script>
</body>
</html>
