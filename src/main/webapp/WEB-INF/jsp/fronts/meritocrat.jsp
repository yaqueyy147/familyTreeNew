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
    <title>世界何氏族谱--何氏英才录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/meritocrat.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
    <style>
        body,html{
            height: 97%;
        }
    </style>
</head>
<body>
<%@include file="common/header.jsp" %>
<nav class="navbar navbar-default" style="margin-top: 30px;width: 90%;margin-left: auto;margin-right: auto">
    <form class="navbar-form navbar-left searchForm" role="search">
        <div class="form-group">
            <input type="text" id="meritocratName" name="meritocratName" class="form-control" placeholder="英才姓名">
        </div>
        <div class="form-group addressSelect">
            <select id="province" name="province">
                <option value="">全部属地</option>
                <c:if test="${meritorcatArea != null}">
                    <c:forEach var="mAddr" items="${meritorcatArea}">
                        <option value="${mAddr.meritocrat_area}">${mAddr.meritocrat_area}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div class="form-group">
            <select id="meritocratAttrId" name="meritocratAttrId">
                <option value="">全部属性</option>
                <c:if test="${meritorcatAttr != null}">
                    <c:forEach var="mAttr" items="${meritorcatAttr}">
                        <option value="${mAttr.id}">${mAttr.meritocratAttr}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <button type="button" id="searchBtn" class="btn btn-default">查询</button>
    </form>
</nav>
<div class="container-fluid">

    <div class="table-responsive" id="meritocratDiv">
        <table class="table table-hover" id="meritocratTable" style="background: #ffffff">
            <tbody></tbody>
        </table>

    </div>
    <div id="pageChanger">

    </div>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/meritocrat.js"></script>
<script type="text/javascript">
    var winHeight = $(window).height();
    $(function () {
        var containerHeight = winHeight - 100;
        $(".container-fluid").attr("style","height" + containerHeight + "px");
        $("#meritocratDiv").attr("style","background: #ffffff;overflow:auto;height:" + (containerHeight - 50) + "px");
        $("#meritocratDiv table").attr("style","height:" + (containerHeight - 50) + "px");
    });
</script>
</body>
</html>
