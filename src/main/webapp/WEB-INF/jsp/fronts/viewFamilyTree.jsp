<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/20 0020
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>族谱展示</title>
    <%@include file="common/commonCss.html"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container" style="margin-top: 50px">
    <a href="#addModal" data-toggle="modal" data-target="#addModal">添加族人</a>
    <!-- 添加族人 Modal -->
    <div class="modal fade bs-example-modal-lg" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="addModalLabel">添加族人</h4>
                </div>
                <div class="modal-body">
                    <!--族人信息标签-->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#baseInfo" role="tab" data-toggle="tab">基本信息</a></li>
                        <li role="presentation"><a href="#mateInfo" role="tab" data-toggle="tab">配偶信息</a></li>
                        <li role="presentation"><a href="#history" role="tab" data-toggle="tab">家族往事</a></li>
                    </ul>
                    <!-- 族人信息页面 -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="baseInfo">
                            <table>
                                <tr>
                                    <td colspan="9">编号:</td>
                                </tr>
                                <tr>
                                    <td rowspan="8" colspan="3"></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                            </table>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="mateInfo">配偶信息</div>
                        <div role="tabpanel" class="tab-pane" id="history">
                            <textarea cols="30" rows="10" style="width: 100%"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary">保 存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>

                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>
</body>
</html>
