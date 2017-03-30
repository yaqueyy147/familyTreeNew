
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
    <title>英才录</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
    <table id="logList" class="easyui-datagrid" style="width:100%;height:98%"
           title="日志" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    </table>
    <div id="tb">
        <%--<span>操作人:</span>--%>
        <%--<input id="operateMan" name="operateMan" style="line-height:26px;border:1px solid #ccc;height: 23px;">--%>
        <span>操作时间:</span>
        <span>
            <input class="easyui-datetimebox" id="beginTime" name="beginTime" style="line-height:26px;border:1px solid #ccc;height: 23px;width:150px">
            &nbsp;~&nbsp;
            <input class="easyui-datetimebox" id="endTime" name="endTime"  style="line-height:26px;border:1px solid #ccc;height: 23px;width:150px">
        </span>
        <span>操作类型:</span>
        <span>
            <select id="operateType" name="operateType">
                <option value="">全部</option>
                <option value="1">新增</option>
                <option value="2">修改</option>
                <option value="3">删除</option>

            </select>
        </span>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="doSearch">查询</a>
    </div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/log.js"></script>

</body>
</html>
