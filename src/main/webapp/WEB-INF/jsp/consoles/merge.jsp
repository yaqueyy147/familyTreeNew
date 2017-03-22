
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
    <title>族谱收录</title>
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
    <table id="mergeList" class="easyui-datagrid" style="width:100%;height:98%"
           title="申请收录列表" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    </table>
    <div id="rejectDialog" class="easyui-dialog" title="驳回" style="width:400px;height:200px;padding:10px;top: 20%;left: 20%;">
        <div style="padding:10px 40px 20px 40px">
            <form id="rejectForm" method="post">
                <input class="easyui-validatebox" type="hidden" id="mergeId" name="id" value="0" />
                <table cellpadding="5">
                    <tr>
                        <td>驳回说明:</td>
                        <td>
                            <%--<input class="easyui-validatebox" id="userDesc" name="userDesc" data-options="multiline:true" style="height:60px" />--%>
                            <textarea class="text-area easyui-validatebox" id="rejectDesc" name="rejectDesc" rows="3" cols="24"></textarea>
                        </td>
                    </tr>

                </table>
            </form>
        </div>
    </div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/merge.js"></script>

</body>
</html>
