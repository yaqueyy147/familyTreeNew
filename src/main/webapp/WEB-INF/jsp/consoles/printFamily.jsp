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
</div>
<div id="printDialog" class="easyui-dialog" title="打印设置" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;">
    <form id="printGenForm" method="post" target="_blank" action="">
        <input id="printFamilyId" name="printFamilyId" type="hidden"/>
        <input id="maxGen" name="maxGen" type="hidden"/>
        <div>
            打印从第
            <input id="beginGen" name="beginGen" value="1" style="width: 50px;display: inline;" />
            代到第
            <input id="endGen" name="endGen" value="${maxGeneration}" style="width: 50px;display: inline;" />
            代的族人
        </div>
        <div class="form-group">
            是否添加家族说明/介绍：
            <label><input type="radio" id="isAddIntroOk" name="isAddIntro" value="1" checked />&nbsp;是</label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <label><input type="radio" id="isAddIntroNO" name="isAddIntro" value="0" />&nbsp;否</label>
        </div>
    </form>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/printFamily.js"></script>
<script>

</script>
</body>
</html>
