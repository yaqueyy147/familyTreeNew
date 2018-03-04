
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
       title="赞助商列表" toolbar="#tb" data-options="
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
<div id="tb">
    <span>公司名:</span>
    <input id="companyName4Search" name="companyName" style="line-height:26px;border:1px solid #ccc;height: 23px;">
    <span>公司属地:</span>
    <span data-toggle="distpicker">
        <select id="province4Search" name="province" style="line-height:26px;border:1px solid #ccc" data-province="---- 全部 ----"></select>
        <select id="city4Search" name="city" style="line-height:26px;border:1px solid #ccc" data-city="---- 全部 ----"></select>
        <select id="district4Search" name="district" style="line-height:26px;border:1px solid #ccc" data-district="---- 全部 ----"></select>
    </span>

    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="doSearch">查询</a>
</div>
<div id="moneyListDialog" class="easyui-dialog" title="充值详情" style="width:600px;height:400px;padding:10px;top: 15%;left: 20%;">
    <input type="hidden" id="companyId" value="" />
    <input type="hidden" id="companyName" value="" />
    <table id="moneyTable" class="easyui-datagrid" style="width:100%;height:100%"
           title="充值列表" toolbar="#tb2" data-options="
				rownumbers:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
        <thead>
            <!-- <th field="payMoney" width="90" editor="{type:'numberbox'}">充值金额</th>
            <th field="payDesc" width="150" editor="{type:'textbox'}">充值说明</th>
            <th field="payTime" width="150">充值时间</th>
            <th field="payMan" width="100">充值人</th> -->
        </thead>
    </table>
</div>
<div id="tb2">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addMoney">添加充值</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="affirmAdd">确认添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="cancelAdd">取消添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="deleteMoney">删除</a>
</div>

<div id="licenseDialog" class="easyui-dialog" title="营业执照展示" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;text-align: center">

</div>
<div id="familylistDialog" class="easyui-dialog" title="设置积分排名家族" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;text-align: center">
    <input type="hidden" id="companyid4set" />
    <div id="companyname4set"></div>
    <select id="rankfamily" name="rankfamily">
        <option value="">无</option>
        <c:forEach var="family" items="${familylist}" varStatus="status">
            <option value="${family.id}">${family.familyName}</option>
        </c:forEach>
    </select>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/auditCompany.js"></script>
</body>
</html>
