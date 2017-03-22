
<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2017/1/11
  Time: 下午9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>积分排名</title>
    <%@include file="common/commonCss.jsp"%>
    <style>
        .personalRank,.companyRank{
            float: left;
            width: 45%;
            height: 100%;
            margin-left: 20px;
        }
    </style>
</head>
<body>
<div class="personalRank">
    <table id="personalRankList" class="easyui-datagrid" style="width:100%;height:98%"
           title="个人积分列表" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:false,
				pageSize:10">
    </table>
</div>
<div class="companyRank">
    <table id="companyRankList" class="easyui-datagrid" style="width:100%;height:98%"
           title="赞助商积分列表" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:false,
				pageSize:10">
    </table>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/pointsList.js"></script>
<script>
    var winHeight = $(document).height();

</script>
</body>
</html>
