
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

    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/datetime-picker/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/fonts/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/fronts/viewFamilyTree.css" />
    <%@include file="common/commonCss.jsp"%>
    <style rel="stylesheet">
        #peopleForm input[type="text"]{
            height:20px;
        }
        .modal-header{
            padding-top:5px !important;
            padding-bottom: 5px !important;
        }
        .modal-dialog{
            margin-top: 5px !important;
        }
        .loading{
            z-index: 8888;
            width: 100%;
            height: 100%;
            background-color: #999999;
            opacity: 0.5;
            text-align: center;
            position: fixed;
            display: none;
        }
        .loading div{
            z-index: 9999;
            width: 200px;
            height:200px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 10%;
            color: #ff0000;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div class="loading">
        <div>加载中,请稍后...</div>
    </div>
    <%--<div data-options="region:'center'" style="width:100%;height:5%">--%>
        <%--<button type="button" id="localBack" class="btn btn-primary">返回</button>--%>
    <%--</div>--%>
    <div data-options="region:'center',split:true" title="合并的主族谱" style="width:50%;position: relative">
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div id="primaryDesc" data-options="region:'north'" style="height:20%">
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">
                    家族名：${mianFamily.familyName}
                    <span style="margin-left: 20px">家族人数：&nbsp;<span id="peopleCount">0</span>&nbsp;人</span>
                    <span style="margin-left: 20px">家族代数：&nbsp;<span id="familyGenNum">${maxGen}</span>&nbsp;代</span>
                    <button type="button" id="tojoint" class="easyui-linkbutton" style="margin-left: 20px">确定合并</button>
                </p>
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">家族属地：${mianFamily.province}${mianFamily.city}${mianFamily.district}
                </p>
                <p style="margin-bottom: 1px;padding-bottom: 1px;margin-top: 1px;padding-top: 1px;">家族简介:${mianFamily.familyDesc}</p>

            </div>
            <div data-options="region:'south',split:true" style="height:80%;">
                <div class="ztree" id="mainFamilyTree"></div>
            </div>

        </div>

    </div>
    <div data-options="region:'east',split:true" title="可选择合并的族谱" style="width:50%;">

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
    <%--<div data-options="region:'south'" style="width:100%;height:5%">--%>
        <%--<button type="button" id="confirmInclude" class="btn btn-primary">收录完成</button>--%>
    <%--</div>--%>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/datetime-picker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/datetime-picker/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/commonUtilJs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/familyJoint.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script>
    var mianFamilyId = "${mianFamilyId}";
    $(function () {

    });
</script>
</body>
</html>
