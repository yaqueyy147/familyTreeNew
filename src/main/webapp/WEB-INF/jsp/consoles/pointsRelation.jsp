<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/25 0025
  Time: 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>积分对应关系设置</title>
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<table id="pointsRelationList" class="easyui-datagrid" style="width:100%;height:100%"
       title="积分对应关系列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10"></table>
<div id="tb">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
</div>
<div id="pointsDialog" class="easyui-dialog" title="用户信息" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="pointsForm" method="post">
            <input class="easyui-validatebox" type="hidden" id="pointsRelationId" name="id" value="0" />
            <table cellpadding="5">
                <tr>
                    <td>积分类型:</td>
                    <td colspan="3">
                        <select id="pointsType" name="pointsType" class="easyui-combobox" style="width:150px" data-options="required:true">
                            <option value="1">修订族谱积分</option>
                            <option value="2">充值积分</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>数量(录入族人数量或者充值金额):</td>
                    <td><input class="easyui-validatebox" type="text" id="pointsNum" name="pointsNum" data-options="required:true" /></td>
                    <td>对应积分数:</td>
                    <td><input class="easyui-validatebox" type="text" id="pointsValue" name="pointsValue" data-options="required:true" /></td>
                </tr>

                <tr>
                    <td>状态:</td>
                    <td>
                        <select id="state" name="state" class="easyui-combobox" style="width:150px">
                            <option value="1">可用</option>
                            <option value="0">不可用</option>
                        </select>
                    </td>
                    <td>积分说明:</td>
                    <td>
                        <%--<input class="easyui-validatebox" id="userDesc" name="userDesc" data-options="multiline:true" style="height:60px" />--%>
                        <textarea class="text-area easyui-validatebox" id="remark" name="remark" rows="3" cols="14"></textarea>
                    </td>

                </tr>

            </table>
        </form>
    </div>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/pointsRelation.js"></script>
</body>
</html>
