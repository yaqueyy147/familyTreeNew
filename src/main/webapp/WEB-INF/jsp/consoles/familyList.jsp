
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
    <%@include file="common/commonCss.jsp"%>
</head>
<body>

<table id="familyList" class="easyui-datagrid" style="width:100%;height:98%"
       title="族谱列表" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
</table>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyList.js"></script>
</body>
</html>
