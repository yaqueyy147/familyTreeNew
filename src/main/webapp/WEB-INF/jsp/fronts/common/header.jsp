<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .userInfo{
        float: right;
        margin-right: 0px;
    }
</style>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="userInfo">
            首页&nbsp;|&nbsp;
            <c:if test="${not empty tUserFront}">
                欢迎您，
                <a href="/family/personalIndex">
                ${tUserFront.userName}
                </a>
                &nbsp;|&nbsp;
                <a href="/sign/logout">退出</a>

            </c:if>
            <c:if test="${empty tUserFront}">
                【<a href="/sign/">登录</a>&nbsp;|&nbsp;<a href="/sign/regedit">注册</a>】
            </c:if>

        </div>
    </div>
</nav>
