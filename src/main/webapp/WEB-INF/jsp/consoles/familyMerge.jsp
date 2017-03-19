
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
    <%@include file="common/commonCss.html"%>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:90%;">
    <div data-options="region:'north'" style="width:100%;height:5%">
        <button type="button" id="localBack">返回</button>
    </div>
    <div data-options="region:'west',split:true" title="申请收录的族谱" style="width:50%;position: relative">
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'north'" style="height:20%">
                <p style="margin-bottom: 1px;padding-bottom: 1px">家族名：${primaryFamily.familyName}</p>
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">家族属地：${primaryFamily.province}${primaryFamily.city}${primaryFamily.district}
                </p>
                <p style="margin-top: 1px;padding-top: 1px;">${primaryFamily.familyDesc}</p>

            </div>
            <div data-options="region:'south',split:true" style="height:80%;">
                <div class="ztree" id="primaryFamilyTree"></div>
            </div>

        </div>

    </div>
    <div data-options="region:'east',split:true" title="可选择收录的族谱" style="width:50%;">

        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'north'" style="height:20%">
                <p style="margin-bottom: 1px;padding-bottom: 1px">家族名：
                    <select id="targetFamily"></select>
                </p>
                <p id="targetFamilyAddr" style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;"></p>
                <p id="targetFamilyDesc" style="margin-top: 1px;padding-top: 1px;"></p>

            </div>
            <div data-options="region:'south',split:true" style="height:80%;">
                <div class="ztree" id="targetFamilyTree"></div>
            </div>

        </div>
    </div>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.html"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyMerge.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script>
    var familyId = "${primaryFamily.id}";
</script>
</body>
</html>
