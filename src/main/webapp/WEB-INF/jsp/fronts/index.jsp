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
    <title>何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/index.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<c:choose>
    <c:when test="${(userInfo.userType == 1 && userInfo.isVolunteer == 1) || (userInfo.userType == 2 && userInfo.state == 1)}">
        <div class="container-fluid" style="margin-top: 50px;width: 90%; margin-bottom: 50px">
            <nav class="navbar navbar-default">
                <form class="navbar-form navbar-left searchForm" role="search">
                    <div class="form-group">
                        <input type="text" id="familyName" name="familyName" class="form-control" placeholder="族谱名">
                    </div>
                    <div class="form-group addressSelect" data-toggle="distpicker">
                        <select id="province" name="province" data-province="---- 选择省 ----"></select>
                        <select id="city" name="city" data-city="---- 选择市 ----"></select>
                        <select id="district" name="district" data-district="---- 选择区 ----"></select>
                    </div>
                    <button type="button" id="searchBtn" class="btn btn-default">查询</button>
                </form>
            </nav>
            <div class="row" id="familyContent">
                <c:forEach var="family" items="${familyList}">

                    <div class="col-sm-3 col-md-2 familyDiv">
                        <div class="thumbnail">
                            <a href="javascript:void(0)" onclick="viewFamily('${family.id}','${family.visitStatus}','${family.visitPassword}')" style="float: none;width: 100%;">
                                <img class="familyImgFF" src="${family.photoUrl}" class="img-thumbnail"/></a>
                                <%--<img data-src="holder.js/300x300" alt="...">--%>
                            <div class="caption">
                                    <%--<h6>${family.familyFirstName}氏族谱（${family.id}）</h6>--%>
                                <h6>世界何氏族谱（${family.id}）</h6>
                                <p>家族人数：${family.peopleCount}人</p>
                                <p>状态：
                                    <c:if test="${family.visitStatus == 0}">加密</c:if>
                                    <c:if test="${family.visitStatus == 1}">开放</c:if>
                                        <%--<c:if test="${family.visitStatus == 2}">仅族人查看</c:if>--%>

                                </p>
                                <p>${family.familyName}</p>
                                <p name="familyDesc" onmouseover="pPopover(this,1)" onmouseout="pPopover(this,2)" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden" data-container="body" data-toggle="popover" data-placement="right" data-content="${family.familyDesc}">
                                        ${family.familyDesc}
                                </p>
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container-fluid" style="margin-top: 50px;width: 90%; margin-bottom: 50px">
            <h2 style="text-align: center">您的账号正在审核中，请耐心等待...</h2>
        </div>
    </c:otherwise>
</c:choose>

<!-- 族谱密码 Modal -->
<div class="modal fade" id="visitPasswordModal" tabindex="-1" role="dialog" aria-labelledby="visitPasswordModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">您需要输入密码才能访问该族谱</h4>
            </div>
            <div class="modal-body">
                <!-- 族人信息页面 -->
                <div class="tab-content">
                    <input type="password" class="form-control" id="password" name="password" placeholder="访问密码">
                    <input type="hidden" class="form-control" id="passwordPre" name="passwordPre">
                    <input type="hidden" class="form-control" id="visitFamilyId" name="visitFamilyId">
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="checkPassword">提 交</button>
                <button class="btn btn-default" id="closePassword" data-dismiss="modal">取 消</button>
            </div>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/jquery.MD5.js"></script>
</body>
</html>
