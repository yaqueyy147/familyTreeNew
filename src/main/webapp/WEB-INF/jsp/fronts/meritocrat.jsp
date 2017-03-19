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
    <link href="/static/css/fronts/meritocrat.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.html"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container-fluid" style="margin-top: 30px;width: 90%; margin-bottom: 30px;position: relative">
    <nav class="navbar navbar-default">
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
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/meritocrat.js"></script>
<script>
    var winHeight = $(document).height();
    $(function () {
        $("#meritocratDiv").attr("style","height:" + (winHeight - 150));
    });
</script>
</body>
</html>
