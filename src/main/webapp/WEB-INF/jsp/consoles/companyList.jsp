
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
    <title>赞助商</title>
    <%@include file="common/commonCss.jsp"%>
</head>
<body>

<table id="companyList" class="easyui-datagrid" style="width:100%;height:100%"
       title="赞助商列表"data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    <thead>
    </thead>
    <%--<tbody>--%>
    <%--<c:forEach var="company" items="${companyList}" varStatus="status">--%>
        <%--<tr>--%>
            <%--<td>${status.index + 1}--%>
                <%--<input type="hidden" id="volunteer-${company.id}" name="volunteer-${company.id}" value="${company.id}" />--%>
            <%--</td>--%>
            <%--<td>${company.company_name}</td>--%>
            <%--<td><a href="javascript:void 0" onclick="showMoneyList('${company.id}')">${company.totalMoney}</a></td>--%>
            <%--<td>${company.company_mobile_phone},${company.company_telephone}</td>--%>
            <%--<td>${company.company_area}</td>--%>
            <%--<td>${company.company_desc}</td>--%>
            <%--<td><img src="<%=request.getContextPath()%>${company.company_photo}" width="100px" height="50px" /></td>--%>
            <%--<td><img src="<%=request.getContextPath()%>${company.business_license}"  width="100px" height="50px" /></td>--%>
            <%--<td>--%>
                <%--<c:if test="${company.state != 0}">--%>
                    <%--已审核--%>
                <%--</c:if>--%>
                <%--<c:if test="${company.state == 0}">--%>
                    <%--<a href="javascript:void 0;" onclick="auditCompany('${company.id}',1,'${volunteer.userId}')">同意</a>--%>
                    <%--<a href="javascript:void 0;" onclick="auditCompany('${company.id}',0,'${volunteer.userId}')">不同意</a>--%>
                <%--</c:if>--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</tbody>--%>
</table>

<div id="moneyListDialog" class="easyui-dialog" title="充值详情" style="width:600px;height:400px;padding:10px;top: 15%;left: 20%;">
    <table id="moneyTable" class="easyui-datagrid" style="width:100%;height:100%"
           title="充值列表" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
        <thead>
            <th field="payMoney" width="90">充值金额</th>
            <th field="payDesc" width="150">充值说明</th>
            <th field="payTime" width="150">充值时间</th>
            <th field="payMan" width="100">充值人</th>
        </thead>
    </table>
</div>
<div id="licenseDialog" class="easyui-dialog" title="营业执照展示" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;text-align: center">

</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/auditCompany.js"></script>
</body>
</html>
