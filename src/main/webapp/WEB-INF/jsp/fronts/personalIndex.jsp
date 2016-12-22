<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/22 0022
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
    <%@include file="common/commonCss.html"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container" style="margin-top: 50px">
    <a href="#addFamilyModal" data-toggle="modal" data-target="#addFamilyModal">创建族谱</a>
    <!-- 添加族人 Modal -->
    <div class="modal fade" id="addFamilyModal" tabindex="-1" role="dialog" aria-labelledby="addFamilyModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="addFamilyModalLabel">创建族谱</h4>
                </div>
                <div class="modal-body">
                    <!-- 族人信息页面 -->
                    <div class="tab-content">
                        <form enctype="multipart/form-data" class="form-horizontal" id="familyForm" action="" method="post">
                            <div class="form-group">
                                <label for="familyFirstName" class="col-sm-2 control-label">家族姓氏</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="familyFirstName" name="familyFirstName">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="familyName" class="col-sm-2 control-label">族谱名称</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="familyName" name="familyName">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="familyName" class="col-sm-2 control-label">访问状态</label>
                                <div class="col-sm-8">
                                    <label><input type="radio" name="visitStatus" value="1" checked />&nbsp;开放</label>
                                    <label><input type="radio" name="visitStatus" value="0" />&nbsp;加密</label>
                                    <label><input type="radio" name="visitStatus" value="2" />&nbsp;仅家族人访问</label>
                                </div>
                            </div>
                            <div class="form-group" id="visitPasswordDiv" style="display: none">
                                <label for="familyName" class="col-sm-2 control-label">访问密码</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="visitPassword" name="visitPassword">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="familyName" class="col-sm-2 control-label">家族简介</label>
                                <div class="col-sm-10">
                                    <textarea cols="30" rows="10" class="form-control" id="familyDesc" name="familyDesc"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="familyName" class="col-sm-2 control-label">展示图片</label>
                                <div class="col-sm-10">
                                    <input id="imgFile" name="imgFile" type="file" multiple class="file" data-overwrite-initial="true" data-min-file-count="1">
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary" id="saveFamily">提 交</button>
                    <button class="btn btn-default" id="closeModal" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="/static/frontJs/personalIndex.js"></script>
</body>
</html>
