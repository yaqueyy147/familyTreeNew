
<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2017/1/7
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>世界何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<%@include file="common/header.jsp" %>

<div class="container" style="margin-top: 50px;">
    <a href="#addFamilyModal" data-toggle="modal" data-target="#addFamilyModal">创建族谱</a>
    <div id="familyShow">
        <div class="row">
            <c:forEach var="company" items="${listCompany}">

                <div class="col-sm-6 col-md-2">
                    <div class="thumbnail">
                        <a href="<%=request.getContextPath()%>/company/detail?companyId=${company.id}" ><img src="${company.companyPhoto}" class="img-thumbnail"/></a>
                            <%--<img data-src="holder.js/300x300" alt="...">--%>
                        <div class="caption">
                            <h3>${company.companyName}</h3>
                            <p name="companyDesc" onmouseover="pPopover(this,1)" onmouseout="pPopover(this,2)" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden" data-container="body" data-toggle="popover" data-placement="right" data-content="${company.companyDesc}">
                                    ${company.companyDesc}
                            </p>
                            <p name="companyMoney">
                                    共充值: ￥&nbsp;${company.totalMoney}&nbsp;元
                            </p>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </div>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/companyIndex.js"></script>
</body>
</html>
