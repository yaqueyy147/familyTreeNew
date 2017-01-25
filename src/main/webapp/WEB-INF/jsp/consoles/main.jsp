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
    <title>族谱管理系统</title>
    <%@ include file="common/commonCss.html" %>
    <style type="text/css">

        .easyui-tabs div iframe{
            width:100%;
            height:100%;
            border: none;
        }
        #topRegion{
            background-color: #e3d4b7;
        }
    </style>
</head>
<body class="layout">
<div class="easyui-layout" style="width:100%;height:100%;">
    <div id="topRegion" data-options="region:'north'" style="height:70px;">这是顶部</div>

    <div data-options="region:'west',split:true" title="菜单" style="width:15%;">
        <ul id="menuTT" class="easyui-tree">
            <li>
                <span>族谱管理</span>
                <ul>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('volunteerList','志愿者审核','/consoles/volunteer')">志愿者审核</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('companyList','赞助商列表','/consoles/company')">赞助商列表</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('familyList','族谱列表','/consoles/family')">族谱列表</a></span></li>
                </ul>
            </li>
            <li><span>用户管理</span>
                <ul>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('userSetting','用户设置','/consoles/user')">用户设置</a></span></li>
                    <li><span><a href="javascript:void 0;" onclick="loadTab('roleSetting','角色设置','/consoles/role')">角色设置</a></span></li>
                </ul>
            </li>
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
<%@include file="common/springUrl.jsp"%>
<%@ include file="common/commonJs.html" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/mainJs.js"></script>
</body>
</html>
