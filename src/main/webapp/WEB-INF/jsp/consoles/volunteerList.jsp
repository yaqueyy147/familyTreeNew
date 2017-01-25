
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
    <title>审核志愿者</title>
    <%@include file="common/commonCss.html"%>
</head>
<body>

<table id="volunteerList" class="easyui-datagrid" style="width:100%;height:98%;"
       title="志愿者列表" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10,
                scrollbarSize:18">
</table>
<div id="auditDialog" class="easyui-dialog" title="审核意见" style="width:400px;height:200px;padding:10px;top: 25%;left: 30%;">
    <input type="hidden" id="applyManId" name="applyManId" />
    <input type="hidden" id="volunteerId" name="volunteerId" />
    <input type="hidden" id="auditState" name="auditState" />
    审核意见：
    <textarea cols="45" rows="5" id="auditDesc" name="auditDesc"></textarea>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/auditVolunteer.js"></script>
</body>
</html>
