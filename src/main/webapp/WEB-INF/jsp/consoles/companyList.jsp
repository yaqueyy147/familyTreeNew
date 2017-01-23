
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
    <%@include file="common/commonCss.html"%>
</head>
<body>

<table id="companyList" class="easyui-datagrid" style="width:100%;height:100%"
       title="赞助商列表"data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    <thead>
        <tr>
            <th field="company_name" width="80">公司名称</th>
            <th field="totalMoney" width="80">公司赞助总额</th>
            <th field="phone" width="80">联系方式</th>
            <th field="company_area" width="120">公司地址</th>
            <th field="company_desc" width="150">公司简介</th>
            <th field="company_photo" width="120">公司展示图</th>
            <th field="business_license" width="120">公司营业执照图</th>
            <th field="operate" width="120">操作</th>
        </tr>
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
<div id="auditDialog" class="easyui-dialog" title="审核意见" style="width:400px;height:200px;padding:10px;top: 25%;left: 30%;">
    <input type="hidden" id="applyManId" name="applyManId" />
    <input type="hidden" id="volunteerId" name="volunteerId" />
    <input type="hidden" id="auditState" name="auditState" />
    审核意见：
    <textarea cols="45" rows="5" id="auditDesc" name="auditDesc"></textarea>
</div>
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
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/auditCompany.js"></script>
</body>
</html>
