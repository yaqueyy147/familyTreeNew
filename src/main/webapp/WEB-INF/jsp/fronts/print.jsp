<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/20 0020
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>世界何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
    <style rel="stylesheet">
        <%--body{--%>
            <%--width:100%;--%>
            <%--height: 100%;--%>
            <%--background: url("<%=request.getContextPath()%>/static/images/bag2.jpg") no-repeat;--%>
            <%--filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";--%>
            <%---moz-background-size:100% 100%;--%>
            <%--background-size:100% 100%;--%>
        <%--}--%>
        .container{
            width:100% !important;
        }
        .swfupload {
            opacity: 0;
        }
        /*.ztree,.ztree ul,.ztree ul li,.ztree li{*/
            /*display: inline;*/
        /*}*/
        .ztree ul {
            padding-top: 20px !important;
            position: relative;
            transition: all 0.5s;
            -webkit-transition: all 0.5s;
            -moz-transition: all 0.5s;
        }
        .ztree li {
            float: left;
            text-align: center;
            list-style-type: none;
            position: relative;
            padding: 20px 5px 0 5px;
            transition: all 0.5s;
            -webkit-transition: all 0.5s;
            -moz-transition: all 0.5s;
        }
        /*We will use ::before and ::after to draw the connectors*/
        .ztree li::before, .ztree li::after {
            content: '';
            position: absolute;
            top: 0;
            right: 50%;
            border-top: 1px solid #ccc;
            width: 50%;
            height: 20px;
        }
        .ztree li::after {
            right: auto;
            left: 50%;
            border-left: 1px solid #ccc;
        }
        /*We need to remove left-right connectors from elements without
        any siblings*/
        .ztree li:only-child::after, .ztree li:only-child::before {
            display: none;
        }
        /*Remove space from the top of single children*/
        .ztree li:only-child {
            padding-top: 0;
        }
        /*Remove left connector from first child and
        right connector from last child*/
        .ztree li:first-child::before, .ztree li:last-child::after {
            border: 0 none;
        }
        /*Adding back the vertical connector to the last nodes*/
        .ztree li:last-child::before {
            border-right: 1px solid #ccc;
            border-radius: 0 5px 0 0;
            -webkit-border-radius: 0 5px 0 0;
            -moz-border-radius: 0 5px 0 0;
        }
        .ztree li:first-child::after {
            border-radius: 5px 0 0 0;
            -webkit-border-radius: 5px 0 0 0;
            -moz-border-radius: 5px 0 0 0;
        }
        /*Time to add downward connectors from parents*/
        .ztree ul::before {
            content: '';
            position: absolute;
            top: 0;
            left: 50%;
            border-left: 1px solid #ccc;
            width: 0;
            height: 20px;
        }
        .ztree li a {
            max-width: 100px;
            white-space: normal;
            min-height: 30px !important;
            height: auto;
            /*border: 1px solid #ccc;*/
            padding: 5px 10px;
            text-decoration: none;
            color: #666;
            font-family: arial, verdana, tahoma;
            font-size: 11px;
            display: inline-block;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            transition: all 0.5s;
            -webkit-transition: all 0.5s;
            -moz-transition: all 0.5s;
        }
        .ztree li span.button.switch {
            display:none;
        }
        .ztree li a.curSelectedNode{
            padding-top: 5px !important;
            background-color: inherit;
            border:none;
        }
        /*Time for some hover effects*/
        /*We will apply the hover effect the the lineage of the element also*/
        /*.tree li a:hover, .tree li a:hover+ul li a {*/
            /*background: #c8e4f8;*/
            /*color: #000;*/
            /*border: 1px solid #94a0b4;*/
        /*}*/
        /*!*Connector styles on hover*!*/
        /*.tree li a:hover+ul li::after, .tree li a:hover+ul li::before, .tree li a:hover+ul::before, .tree li a:hover+ul ul::before {*/
            /*border-color: #94a0b4;*/
        /*}*/
        .loading{
            z-index: 8888;
            width: 100%;
            height: 100%;
            background-color: #999999;
            opacity: 0.5;
            text-align: center;
            position: fixed;
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
<body style="padding-bottom: 100px">
<div class="loading">
    <div>加载中,请稍后...</div>
</div>
<%--<%@include file="common/header.jsp" %>--%>
    <input type="hidden" value="${familyId}" id="familyIdT" name="familyIdT" />
    <input type="hidden" value="${beginGen}" id="beginGen" name="beginGen" />
    <input type="hidden" value="${endGen}" id="endGen" name="endGen" />
    <div style="text-align: center;font-size: 20px">
        <p style="border-bottom: solid 1px #999999">
            <span style="color: #a94442">${tFamily.familyName}</span>

        </p>
        <c:if test="${not empty tFamily.familyDesc && isAddIntro == 1}">
            <p style="font-size: 14px;text-align: left;border-bottom: solid 1px #999999">家族简介：${tFamily.familyDesc}</p>
        </c:if>
    </div>

    <div id="familyTreeDiv" style="width: 120000px">
        <ul id="familyTree" class="ztree"></ul>
    </div>
<nav class="navbar navbar-default navbar-fixed-bottom">
    <div class="container" style="text-align: center">
        <button class="btn btn-sm btn-primary" id="okPrint">打印</button>
        <button class="btn btn-sm btn-primary" id="cancel">取消打印/关闭</button>
    </div>
</nav>
<%@ include file="common/springUrl.jsp"%>
<%--<%@include file="common/footer.jsp" %>--%>
<%@include file="common/commonJS.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/print.js"></script>
<script type="text/javascript">
    var familyId = "${familyId}";
    var beginGen = "${beginGen}";
    var endGen = "${endGen}";
    var familyFirstName = "${tFamily.familyFirstName}";
    $(document).ready(function () {

        var treeLi = $(".ztree li");
        var totalWidth = 0;

        var topTree = $("#familyTree > li");
        $.each(topTree,function () {

            var liWidth = $(this).outerWidth();
            if(liWidth*1 < 1000){
                liWidth = liWidth*1 + 100;
            }
            totalWidth = totalWidth*1 + liWidth*1;

//            liCss.width = liWidth + "px";
//            $(this).append("<style>.ztree li::before{width:" + liWidth + "px;right:" + liWidth + "px;}</style>");
//            $(this).append("<style>.ztree li::after{width:" + liWidth + "px;right:" + liWidth + "px;}</style>");
//            $(".ztree li::after").css(liCss);
        });
        $("#familyTreeDiv").attr("style","width:" + (totalWidth) + "px");

//        window.print();

        $("#okPrint").click(function () {
            window.print();
        });
        $("#cancel").click(function () {
            window.close();
        });

    });

</script>
</body>
</html>
