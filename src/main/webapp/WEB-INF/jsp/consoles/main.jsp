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
    <%@ include file="../common/commonCss.html" %>
</head>
<body class="layout">
<div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'north'" style="height:50px">这是顶部</div>

    <div data-options="region:'west',split:true" title="菜单" style="width:15%;"></div>
    <div data-options="region:'center'">
        <div class="easyui-tabs" data-options="border:false,fit:true">
            <div title="About" data-options="closable:true" style="padding:10px">
                <p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
                <ul>
                    <li>easyui is a collection of user-interface plugin based on jQuery.</li>
                    <li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
                    <li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
                    <li>complete framework for HTML5 web page.</li>
                    <li>easyui save your time and scales while developing your products.</li>
                    <li>easyui is very easy but powerful.</li>
                </ul>
            </div>
        </div>
    </div>
    <div data-options="region:'south',split:true"></div>
</div>
<%@ include file="../common/commonJs.html" %>
</body>
</html>
