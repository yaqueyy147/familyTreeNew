<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/18
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>注册</title>
    <link href="/static/css/login.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.html"%>
    <style>
        body{
            background: #ffd306;
        }
        .bbtt {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="login-box">
    <div class="login-title text-center">注&nbsp;&nbsp;&nbsp;&nbsp;册</div>
    <div class="login-content">
        <div class="form">
            <form id="regeditForm" action="" method="post">
                <div class="form-group col-xs-8 form-actions col-xs-offset-2">
                    <input class="form-control" id="userName" name="userName" placeholder="用户名" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="nickName" name="nickName" placeholder="昵 称" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="password" name="password" placeholder="密 码" type="password" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="idCard" name="idCard" placeholder="身份证号" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="phone" name="phone" placeholder="手机号码" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="wechart" name="wechart" placeholder="微 信" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="qqNum" name="qqNum" placeholder="QQ" type="text" />
                </div>
                <div class="form-group col-xs-9 form-actions col-xs-offset-4" style="margin-top: 15px">
                    <button class="btn btn-primary bbtt" id="regedit" type="button">注 册</button>
                    &nbsp;&nbsp;
                    <a class="btn btn-primary bbtt" href="/familyTree/index" type="button">取 消</a>
                </div>

            </form>
        </div>
    </div>
</div>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="/static/frontJs/regedit.js"></script>
</body>
</html>
