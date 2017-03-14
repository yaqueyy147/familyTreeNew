<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/20 0020
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>族谱展示</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/fronts/viewFamilyTree.css" />
    <%@include file="common/commonCss.html"%>
    <style rel="stylesheet">
        body{
            width:100%;
            height: 100%;
            background: url("<%=request.getContextPath()%>/static/images/bag2.jpg") no-repeat;
            filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
            -moz-background-size:100% 100%;
            background-size:100% 100%;
        }
    </style>
</head>
<body>

<%@include file="common/header.jsp" %>

<div class="container" style="margin-top: 50px">
    <a class="btn btn-primary" href="javascript:void 0;" id="goBack">返回</a>
    <div id="familyTree" class="ztree"></div>
</div>

<%@ include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/viewFamilyTree_visitor.js"></script>
<script type="text/javascript">
    var familyId = "${familyId}";
    var familyFirstName = "${tFamily.familyFirstName}";
    $(function () {
        $("#goBack").click(function () {
            window.history.back();
        });
    });
</script>
</body>
</html>
