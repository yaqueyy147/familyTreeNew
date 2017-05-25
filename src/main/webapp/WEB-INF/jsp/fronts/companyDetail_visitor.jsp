
<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/22 0022
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>世界何氏族谱--赞助商详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%@include file="common/commonCss.jsp"%>
    <c:if test="${xxx != 2}">
        <style>
            body{
                background: url("<%=request.getContextPath()%>/static/images/bg-front.jpg") no-repeat;
                filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
                -moz-background-size:100% 100%;
                background-size:100% 100%;
            }
            #descDiv{
                border-bottom:solid 1px #999999;
                word-break: break-all;
                height: auto;
                font-size: 14px;
            }
            #companyShow{
                margin-top: 20px;
            }
            #companyMoney{

            }
            .popover {
                word-break: break-all;
            }
        </style>

    </c:if>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container-fluid" style="margin-top: 50px;width: 90%; margin-bottom: 50px">
    <%--<a class="btn btn-primary" href="#addPhotoModal" data-toggle="modal" data-target="#addPhotoModal">添加照片</a>--%>
    <span style="font-size: 18px;border-bottom: solid 1px #999999;display: block">
        <span style="color:#EEEE00;font-weight: bold;">${tCompanySponsor.companyName}</span>&nbsp;&nbsp;
        <%--已赞助：--%>
        <%--<a id="companyMoney" href="javascript:void 0;">${totalMoney}</a>元--%>
        总积分：
        <a id="companyPoints" href="javascript:void 0;">${totalPoints}</a>
        &nbsp;&nbsp;
        <a class="btn btn-primary" href="javascript:void 0;" id="goBack">返回</a>
    </span>
    &nbsp;&nbsp;
    <%--<a class="btn btn-primary" href="#chargeModal" data-toggle="modal" data-target="#chargeModal">充 值</a>--%>
    <c:if test="${not empty tCompanySponsor.companyDesc}">
        <div id="descDiv">${tCompanySponsor.companyDesc}</div>
    </c:if>
    <div id="companyShow" class="row">
        ${introduce.companyIntroduce}
        <%--<c:forEach var="companyDetail" items="${companyDetailList}">--%>

            <%--<div class="col-sm-6 col-md-2">--%>
                <%--<div class="thumbnail">--%>
                    <%--<a href="javascript:void(0)" ><img src="${companyDetail.publicityPhoto}" class="img-thumbnail" style="width: 100%;"/></a>--%>
                    <%--&lt;%&ndash;<img data-src="holder.js/300x300" alt="...">&ndash;%&gt;--%>
                    <%--<div class="caption">--%>
                        <%--<p name="photoDesc" onmouseover="pPopover(this,1)" onmouseout="pPopover(this,2)" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden" data-container="body" data-toggle="popover" data-placement="bottom" data-content="${companyDetail.photoDesc}">--%>
                            <%--${companyDetail.photoDesc}--%>
                        <%--</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
    </div>
</div>
<!-- 充值列表 Modal -->
<div class="modal fade" id="moneyModal" tabindex="-1" role="dialog" aria-labelledby="moneyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="moneyModalLabel">公司充值列表</h4>
            </div>
            <div class="modal-body" style="max-height:300px; overflow: auto">
                <table id="moneyTable" class="table">
                    <tr>
                        <th>序号</th>
                        <th>充值金额</th>
                        <th>充值说明</th>
                        <th>充值时间</th>
                        <th>充值人</th>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" id="closeMoneyModal" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/companyDetail.js"></script>
<script type="text/javascript">
    var winHeight = $(document).height();
    var companyId = "${tCompanySponsor.id}";
    $(function () {
        $("#goBack").click(function () {
            window.history.back();
        });
        $("body").attr("style","height:" + (winHeight - 50) + "px");

    });
</script>
</body>
</html>
