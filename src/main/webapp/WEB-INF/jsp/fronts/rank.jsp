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
    <title>世界何氏族谱--积分排行榜</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/rank.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container-fluid">
    <div class="personalRank">
        <span>个人积分排名:</span>
        <table class="table">
            <thead>
                <tr>
                    <td>序号</td>
                    <td>用户名</td>
                    <td>积分</td>
                </tr>
            </thead>
            <tbody>
                <c:if test="${listPersonalPoints != null}">
                    <c:forEach var="personalPoints" items="${listPersonalPoints}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${personalPoints.user_name}</td>
                            <td>${personalPoints.totalPoints}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>
    <div class="companyRank">
        <span>赞助商积分排名:</span>
        <table class="table">
            <thead>
            <tr>
                <td>序号</td>
                <td>公司名</td>
                <td>积分</td>
            </tr>
            </thead>
            <tbody>
            <c:if test="${listCompanyPoints != null}">
                <c:forEach var="companyPoints" items="${listCompanyPoints}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td><a href="<%=request.getContextPath()%>/company/detail?companyId=${companyPoints.company_id}&xxx=1">${companyPoints.company_name}</a></td>
                        <td>${companyPoints.totalPoints}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script>
    var winHeight = $(document).height();
    $(function () {
        $(".personalRank").attr("style","height:" + (winHeight - 20 - 30 - 50) + " !important");
        $(".companyRank").attr("style","height:" + (winHeight - 20 - 30 - 50) + " !important");
    });
</script>
</body>
</html>
