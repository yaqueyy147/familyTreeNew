<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/4
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登出跳转页面</title>
</head>
<body>
<script language="JavaScript" type="text/javascript">
    var type = "${type}";
    var url = "";
    if(type == 1) {//后台跳转
        url = "<%=request.getContextPath()%>/consoles/login?loginCode=-2";
    }else{
        url = "<%=request.getContextPath()%>/sign?loginCode=-2";
    }
    if (window != top){
        top.location.href = url;
    }
    location.href = url;
</script>
</body>
</html>
