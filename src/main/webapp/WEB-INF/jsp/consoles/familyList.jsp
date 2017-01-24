
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
    <%@include file="common/commonCss.html"%>
</head>
<body>

<table id="familyList" class="easyui-datagrid" style="width:100%;height:100%"
       title="族谱列表"data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    <thead>
        <tr>
            <th field="id" width="80" hidden>族谱Id</th>
            <th field="familyName" width="150">族谱名称</th>
            <th field="familyFirstName" width="150">族谱姓氏</th>
            <th field="peopleCount" width="80">族谱人数</th>
            <th field="createMan" width="80">创建人</th>
            <th field="createTime" width="180">创建时间</th>
            <th field="familyArea" width="80">族谱属地</th>
        </tr>
    </thead>

</table>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyList.js"></script>
</body>
</html>
