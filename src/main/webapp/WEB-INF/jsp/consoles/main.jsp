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
    </style>
</head>
<body class="layout">
<div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'north'" style="height:50px">这是顶部</div>

    <div data-options="region:'west',split:true" title="菜单" style="width:15%;">
        <ul id="tt" class="easyui-tree">
            <li>
                <span>Folder</span>
                <ul>
                    <li><span>志愿者审核</span></li>
                    <li><span>File 2</span></li>
                    <li><span>File 3</span></li>
                </ul>
            </li>
            <li><span>File21</span></li>
        </ul>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-tabs" data-options="border:false,fit:true">
            <div title="About" data-options="closable:false" style="padding:10px"></div>
            <div title="志愿者审核" data-options="closable:true">
                <iframe src="<%=request.getContextPath()%>/consoles/volunteerList"></iframe>
            </div>
        </div>
    </div>
    <div data-options="region:'south',split:true"></div>
</div>
<%@ include file="common/commonJs.html" %>
</body>
</html>
