
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
    <title>族谱</title>
    <%@include file="common/commonCss.html"%>
</head>
<body>

<table id="familyList" class="easyui-datagrid" style="width:100%;height:100%"
       title="族谱列表"data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    <thead>
        <tr>
            <th field="familyName" width="80">族谱名称</th>
            <th field="familyFirstName" width="80">族谱姓氏</th>
            <th field="peopleCount" width="80">族谱人数</th>
            <th field="createMan" width="80">创建人</th>
            <th field="createTime" width="120">创建时间</th>
            <th field="familyArea" width="150">族谱属地</th>
        </tr>
    </thead>

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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyList.js"></script>
</body>
</html>
