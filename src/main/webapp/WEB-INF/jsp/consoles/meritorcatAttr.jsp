
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
    <title>英才属性</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
    <table id="meritorcatAttrList" class="easyui-datagrid" style="width:100%;height:98%"
           title="何氏英才录" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    </table>
    <div id="tb">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
    </div>
    <div id="meritocratAttrDialog" class="easyui-dialog" title="英才信息" style="padding:10px;top: 30%;left: 20%;">
        <div style="padding:10px 40px 20px 40px">
            <form id="meritocratAttrForm" method="post">
                <input type="hidden" id="meritocratAttrId" name="id" value="0" />
                <table cellpadding="5">
                    <tr>
                        <td>属性名:</td>
                        <td><input class="easyui-validatebox" type="text" id="meritocratAttr" name="meritocratAttr" data-options="required:true" /></td>
                        <td>状态:</td>
                        <td>
                            <select id="state" name="state" class="easyui-combobox" style="width:100px">
                                <option value="1">可用</option>
                                <option value="0">不可用</option>
                            </select>
                        </td>
                    </tr>

                </table>
            </form>
        </div>
    </div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/meritorcatAttr.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>>

</body>
</html>
