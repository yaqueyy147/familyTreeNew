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
    <title>族谱</title>
    <link href="/static/css/login.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.html"%>
    <style>
        body{
            background: #ffd306;
        }

    </style>
</head>
<body>
这是 首页
${tUserFront.userName}
<%@include file="common/commonJS.html"%>
</body>
</html>
