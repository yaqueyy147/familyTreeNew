
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
    <title>审核志愿者</title>
    <%@include file="common/commonCss.html"%>
</head>
<body>

<table id="tt" class="easyui-datagrid" style="width:100%;height:100%"
       url="datagrid24_getdata.php" toolbar="#tb"
       title="志愿申请列表" iconCls="icon-save"
       rownumbers="true" pagination="true">
    <thead>
        <tr>
            <th field="序号" width="80">序号</th>
            <th field="user_name" width="80">申请人</th>
            <th field="phone" width="80">联系电话</th>
            <th field="apply_desc" width="120">申请说明</th>
            <th field="applyTime" width="150">申请时间</th>
            <th field="is_volunteer" width="80">是否志愿者</th>
            <th field="操作" width="120">操作</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="volunteer" items="${volunteerApplyList}" varStatus="status">
        <tr>
            <td>${status.index + 1}
                <input type="hidden" id="volunteer-${volunteer.volunteerId}" name="volunteer-${volunteer.volunteerId}" value="${volunteer.volunteerId}" />
            </td>
            <td>${volunteer.user_name}</td>
            <td>${volunteer.phone}</td>
            <td>${volunteer.apply_desc}</td>
            <td>${volunteer.applyTime}</td>
            <td>
                <c:if test="${volunteer.is_volunteer == 1}">是</c:if>
                <c:if test="${volunteer.is_volunteer != 1}">否</c:if>
            </td>
            <td>
                <c:if test="${volunteer.is_volunteer != 0}">
                    已审核
                </c:if>
                <c:if test="${volunteer.is_volunteer == 0}">
                    <a href="javascript:void 0;" onclick="auditVolunteer('${volunteer.volunteerId}',1,'${volunteer.userId}')">同意</a>
                    <a href="javascript:void 0;" onclick="auditVolunteer('${volunteer.volunteerId}',0,'${volunteer.userId}')">不同意</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="auditDialog" class="easyui-dialog" title="审核意见" style="width:400px;height:200px;padding:10px;top: 25%;left: 30%;">
    <input type="hidden" id="applyManId" name="applyManId" />
    <input type="hidden" id="volunteerId" name="volunteerId" />
    <input type="hidden" id="auditState" name="auditState" />
    审核意见：
    <textarea cols="45" rows="5" id="auditDesc" name="auditDesc"></textarea>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/auditVolunteer.js"></script>
</body>
</html>
