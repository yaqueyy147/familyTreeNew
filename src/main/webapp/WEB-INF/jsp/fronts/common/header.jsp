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
            首页&nbsp;|&nbsp;
            <c:if test="${not empty userInfo}">
                【欢迎您，
                <a href="/family/personalInfo">
                ${userInfo.userName}
                </a>
                &nbsp;|&nbsp;
                <a href="/sign/logout">退出</a>
                】
            </c:if>
            <c:if test="${empty userInfo}">
                【<a href="/sign/">登录</a>&nbsp;|&nbsp;<a href="/sign/regedit">注册</a>】
            </c:if>

        </div>
    </div>
</nav>
