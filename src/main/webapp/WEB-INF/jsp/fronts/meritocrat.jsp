<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/18
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>何氏英才录</title>
    <link href="/static/css/fronts/index.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.html"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container-fluid" style="margin-top: 50px;width: 90%; margin-bottom: 50px">

</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/jquery.MD5.js"></script>
</body>
</html>
