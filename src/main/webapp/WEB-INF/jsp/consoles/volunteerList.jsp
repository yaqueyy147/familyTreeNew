
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
    <title>审核注册用户</title>
    <%@include file="common/commonCss.jsp"%>
</head>
<body>

<table id="volunteerList" class="easyui-datagrid" style="width:100%;height:98%;"
       title="注册用户列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10,
                scrollbarSize:18">
</table>
<div id="tb">
    <span>登录账号:</span>
    <input id="loginName4Search" name="loginName" style="line-height:26px;border:1px solid #ccc;height: 23px;">
    <span>用户名:</span>
    <input id="userName4Search" name="userName" style="line-height:26px;border:1px solid #ccc;height: 23px;">
    <span>用户属地:</span>
    <span data-toggle="distpicker">
        <select id="province4Search" name="province" style="line-height:26px;border:1px solid #ccc" data-province="---- 全部 ----"></select>
        <select id="city4Search" name="city" style="line-height:26px;border:1px solid #ccc" data-city="---- 全部 ----"></select>
        <select id="district4Search" name="district" style="line-height:26px;border:1px solid #ccc" data-district="---- 全部 ----"></select>
    </span>
    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="doSearch">查询</a>
</div>
<div id="auditDialog" class="easyui-dialog" title="审核意见" style="width:400px;height:200px;padding:10px;top: 25%;left: 30%;">
    <input type="hidden" id="applyManId" name="applyManId" />
    <input type="hidden" id="volunteerId" name="volunteerId" />
    <input type="hidden" id="auditState" name="auditState" />
    审核意见：
    <textarea cols="45" rows="5" id="auditDesc" name="auditDesc"></textarea>
</div>

<div id="idCardDialog" class="easyui-dialog" title="身份证照展示" style="width:400px;height:200px;padding:10px;top: 10%;left: 10%;text-align: center">

</div>

<div id="moneyListDialog" class="easyui-dialog" title="充值详情" style="width:600px;height:400px;padding:10px;top: 15%;left: 20%;">
    <input type="hidden" id="userId" value="" />
    <input type="hidden" id="userName" value="" />
    <table id="moneyTable" class="easyui-datagrid" style="width:100%;height:100%"
           title="充值列表" toolbar="#tb2" data-options="
				rownumbers:true,
				singleSelect:true,
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
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/auditVolunteer.js"></script>
</body>
</html>
