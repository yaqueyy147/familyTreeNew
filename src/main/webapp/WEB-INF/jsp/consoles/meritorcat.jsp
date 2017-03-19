
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
    <%@include file="common/commonCss.html"%>
</head>
<body>
    <table id="meritorcatList" class="easyui-datagrid" style="width:100%;height:98%"
           title="何氏英才录" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    </table>
    <div id="tb">
        <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
    </div>
    <div id="meritocratDialog" class="easyui-dialog" title="英才信息" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
        <div style="padding:10px 40px 20px 40px">
            <form id="meritocratForm" method="post">
                <input type="hidden" id="meritocratId" name="id" value="0" />
                <table cellpadding="5">
                    <tr>
                        <td>英才姓名:</td>
                        <td><input class="easyui-validatebox" type="text" id="meritocratName" name="meritocratName" data-options="required:true" /></td>
                        <td>英才属地:</td>
                        <td>
                            <input id="meritocratArea" class="easyui-combobox" name="meritocratArea"
                                   data-options="valueField:'id',textField:'text'">
                            <%--<select id="meritocratArea" name="meritocratArea" class="easyui-combobox" style="width:150px" data-options="valueField:'id', textField:'text'">--%>
                                <%--<option value="">无</option>--%>
                            <%--</select>--%>
                        </td>
                    </tr>
                    <tr>
                        <td>英才类型:</td>
                        <td>
                            <select id="meritocratAttrId" name="meritocratAttrId" class="easyui-combobox" style="width:150px">
                                <c:if test="${meritorcatAttr != null}">
                                    <c:forEach var="mAttr" items="${meritorcatAttr}">
                                        <option value="${mAttr.id}">${mAttr.meritocratAttr}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </td>
                        <td>邮编:</td>
                        <td><input class="easyui-validatebox" type="text" id="postCode" name="postCode" /></td>
                    </tr>
                    <tr>
                        <td>联系电话:</td>
                        <td><input class="easyui-validatebox" type="text" id="phone" name="phone" /></td>
                        <td>传真:</td>
                        <td><input class="easyui-validatebox" type="text" id="fax" name="fax" /></td>
                    </tr>
                    <tr>
                        <td>详细地址:</td>
                        <td colspan="3">
                            <input class="easyui-validatebox" type="text" id="meritocratAddr" name="meritocratAddr" style="width: 380px" />
                        </td>
                    </tr>
                    <tr>
                        <td>英才简介:</td>
                        <td colspan="3">
                            <textarea class="text-area easyui-validatebox" id="meritocratDesc" name="meritocratDesc" rows="5" cols="40"></textarea>
                        </td>
                    </tr>

                </table>
            </form>
        </div>
    </div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/meritorcat.js"></script>

</body>
</html>
