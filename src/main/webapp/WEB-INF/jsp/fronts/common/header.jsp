<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .userInfo{
        float: right;
        margin-right: 0px;
    }
</style>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="min-height: 0;">
    <div class="container">
        <div class="userInfo">
            <a href="<%=request.getContextPath()%>/family/index">首页</a>&nbsp;|&nbsp;
            <c:choose>
                <c:when test="${not empty userInfo}">
                    <c:choose>
                        <c:when test="${(userInfo.userType == 1 && userInfo.isVolunteer == 1) || (userInfo.userType == 2 && userInfo.state == 1)}">
                            <a href="<%=request.getContextPath()%>/family/meritocrat">何氏英才录</a>&nbsp;|&nbsp;
                            <a href="<%=request.getContextPath()%>/family/pointsRanking">积分排行榜</a>&nbsp;|&nbsp;
                        </c:when>
                    </c:choose>
                    <c:if test="${userInfo.isVolunteer == 1}">
                        <a href="<%=request.getContextPath()%>/family/personalInfo?xxx=2">创建族谱</a>&nbsp;|&nbsp;
                        <a href="<%=request.getContextPath()%>/family/personalInfo?xxx=3">补录族谱</a>&nbsp;|&nbsp;
                    </c:if>
                    【欢迎您，

                    <c:if test="${userInfo.userType == 1}">
                    <a href="<%=request.getContextPath()%>/family/personalInfo?xxx=1">
                        ${userInfo.userName}
                    </c:if>
                    <c:if test="${userInfo.userType == 2}">
                    <a href="<%=request.getContextPath()%>/company/info">
                        ${userInfo.companyName}
                    </c:if>
                    </a>
                    &nbsp;|&nbsp;
                    <a href="<%=request.getContextPath()%>/sign/logout">退出</a>
                    】
                </c:when>
                <c:otherwise>
                    【<a href="<%=request.getContextPath()%>/sign/">登录</a>
                    &nbsp;|&nbsp;
                    <a href="<%=request.getContextPath()%>/sign/regeditPersonal">个人用户注册</a>
                    &nbsp;|&nbsp;
                    <a href="<%=request.getContextPath()%>/sign/regeditCompany">企业用户注册</a>】
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</nav>

