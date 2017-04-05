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
    <title>何氏族谱--登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/login.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>
<div class="login-box">
    <div class="login-title text-center">登&nbsp;&nbsp;&nbsp;&nbsp;录</div>
    <div class="login-content">
        <div class="form">
            <form id="signInForm" action="" method="post">
                <div id="loginFail" class="form-group col-xs-8 form-actions col-xs-offset-2" style="color: #ff0000">
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2">
                    <input class="form-control" id="loginName" name="loginName" placeholder="用户名" type="text" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">
                    <input class="form-control" id="password" name="password" placeholder="密 码" type="password" />
                </div>
                <div class="form-group col-xs-8 form-actions col-xs-offset-2" style="margin-top: 15px">

                    <div class="col-xs-7">
                        <input class="form-control" id="checkCode" name="checkCode" placeholder="验证码" type="text" />
                    </div>
                    <label for="checkCode" class="col-xs-4 control-label">
                        <canvas id="canvas"  width="100" height="34"></canvas>
                    </label>
                </div>
                <div class="form-group col-xs-4 form-actions col-xs-offset-5" style="margin-top: 15px">
                    <button class="btn btn-primary" id="signIn" type="button">登 录</button>
                </div>
                <div class="form-group col-xs-8 link col-xs-offset-4" style="text-align: right" >
                    <p>还没注册？免费注册
                        <small>
                            <a href="<%=request.getContextPath()%>/sign/regeditPersonal">个人注册</a>
                        </small>
                        &nbsp;|&nbsp;
                        <small>
                            <a href="<%=request.getContextPath()%>/sign/regeditCompany">企业注册</a>
                        </small>
                    </p>
                    <p style="margin-top: 0;">
                        <a href="<%=request.getContextPath()%>/familyTree/index"><small>直接进入!</small></a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/checkCode_2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/login.js"></script>

<script>
    var loginCode = "${loginCode}";
</script>
</body>
</html>
