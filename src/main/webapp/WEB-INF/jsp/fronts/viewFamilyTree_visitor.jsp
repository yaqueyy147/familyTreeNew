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
    <title>世界何氏族谱</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/fronts/viewFamilyTree.css" />
    <%@include file="common/commonCss.jsp"%>
    <style rel="stylesheet">
        body{
            height: 90%;
            width: 100%;
            background: url("<%=request.getContextPath()%>/static/images/bg-front.jpg") no-repeat;
            filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
            -webkit-background-size:cover;
            -moz-background-size:cover;
            background-size:cover;
            background-attachment:fixed;
            overflow: auto;
        }
        #peopleForm input{
            border: 0px;
        }
        .treeContainer.container{
            width:90% !important;
        }
        .rankDiv{
            height: 50%;
            float: left;
            margin-top: 20px;
            margin-bottom: 50px;
            overflow: auto;
            border-left: solid 1px #999999;
            border-right: solid 1px #999999;
        }
        #familyTree{
            margin-top: 20px;
            overflow: auto;
            float: left;
            background-color: #ffffff;
        }
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
    </style>
</head>
<body>

<%@include file="common/header.jsp" %>
<div class="loading">
    <div>加载中,请稍后...</div>
</div>
<div class="container treeContainer" style="margin-top: 50px">

    <%--<a class="btn btn-primary" href="javascript:void 0;" id="goBack">返回</a>--%>
    <div style="text-align: center;font-size: 20px;">
        <p style="border-bottom: solid 1px #999999">
            <span style="color: #fbf069">${tFamily.familyName}</span>
            &nbsp;&nbsp;的族人
            &nbsp;&nbsp;
            <a class="btn btn-primary btn-sm" href="javascript:void 0;" id="goBack">返回</a>
        </p>
        <c:if test="${not empty tFamily.familyDesc}">
        <p id="familyDesc" style="font-size: 14px;text-align: left;border-bottom: solid 1px #999999" >家族简介：${tFamily.familyDesc}</p>
        </c:if>
    </div>
    <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 rankDiv" >
        <div class="personalRank">
            <span>个人积分排名:</span>
            <table class="table">
                <thead>
                <tr>
                    <td>序号</td>
                    <td>用户名</td>
                    <td>积分</td>
                </tr>
                </thead>
                <tbody id="personalPoints">
                <c:if test="${listPersonalPoints != null}">
                    <c:forEach var="personalPoints" items="${listPersonalPoints}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${personalPoints.user_name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty personalPoints.totalPoints}">
                                        ${personalPoints.totalPoints}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>

    </div>
    <div id="familyTree" class="ztree container-fluid col-xs-8 col-sm-8 col-md-8 col-lg-8" ></div>
    <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 rankDiv" >
        <div class="companyRank">
            <span>赞助商积分排名:</span>
            <table class="table">
                <thead>
                <tr>
                    <td>序号</td>
                    <td>公司名</td>
                    <td>积分</td>
                </tr>
                </thead>
                <tbody id="companyPoints">
                <c:if test="${listCompanyPoints != null}">
                    <c:forEach var="companyPoints" items="${listCompanyPoints}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td style="word-break: break-all;max-width: 100px;"><a href="<%=request.getContextPath()%>/company/detail?companyId=${companyPoints.company_id}&xxx=1">${companyPoints.company_name}</a></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty companyPoints.totalPoints}">
                                        ${companyPoints.totalPoints}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addModalLabel">添加族人</h4>
            </div>
            <div class="modal-body">
                <form id="peopleForm" action="<%=request.getContextPath()%>/family/savePeople" method="post">
                    <table>
                        <tr style="border: solid 2px #9d9d9d;">
                            <td class="tdLg" style="text-align: left" colspan="9">
                                <input name="familyId" id="familyId" type="hidden" value="${familyId}" />

                                &nbsp;&nbsp;
                                第<input id="generation" name="generation" type="text" style="width:20px" value="0" disabled />代
                                <%--第--%>
                                <%--<select id="generation" name="generation" style="width:35px">--%>
                                <%--<option value="1">1</option>--%>
                                <%--<option value="2">2</option>--%>
                                <%--<option value="3">3</option>--%>
                                <%--<option value="4">4</option>--%>
                                <%--<option value="5">5</option>--%>
                                <%--<option value="6">6</option>--%>
                                <%--<option value="7">7</option>--%>
                                <%--<option value="8">8</option>--%>
                                <%--</select>--%>
                                <%--代--%>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input name="id" id="id" type="hidden" value="0" />
                                <input name="mateId" id="mateId" type="hidden"/>
                                <input name="peopleType" id="peopleType" type="hidden" value="1" />
                                <span id="peopleInfo"></span>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder">
                            <td class="photoBox" rowspan="8" colspan="3" style="text-align: center" id="photo">
                                <img id="result_img" height="128px" width="128px" />
                            </td>
                            <td class="tdSm">
                                姓名：
                            </td>
                            <td colspan="3">
                                <input name="name" id="name" type="text" value="何" disabled/>
                            </td>
                            <td class="tdSm">
                                特殊说明：
                            </td>
                            <td><input name="specialRemark" id="specialRemark" type="text" disabled /></td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">
                                曾用名：
                            </td>
                            <td colspan="3">
                                <input name="usedName" id="usedName" type="text" disabled/>
                            </td>
                            <td class="tdSm">
                                学历：
                            </td>
                            <td>
                                <input name="education" id="education" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">性别：</td>
                            <td style="text-align: left">
                                <select name="sex" id="sex" disabled>
                                    <option value="1">男</option>
                                    <option value="0">女</option>
                                </select>
                            </td>
                            <td colspan="2">
                                <select name="mateType" id="mateType" disabled>
                                    <option value="0"></option>
                                    <option value="1">妻子</option>
                                    <option value="2">丈夫</option>
                                </select>
                            </td>
                            <td class="tdSm">
                                职业：
                            </td>
                            <td>
                                <input name="job" id="job" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">辈分：</td>
                            <td class="tdXSm">
                                <input name="familyGeneration" id="familyGeneration" class="inputSm" type="text" value="0" disabled />
                            </td>
                            <td class="tdXSm">字：</td>
                            <td class="tdXSm">
                                <input name="cName" id="cName" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdSm">身份证：</td>
                            <td>
                                <input name="idCard" id="idCard" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">排行：</td>
                            <td>
                                <input name="familyRank" id="familyRank" class="inputSm" type="text" value="0" disabled />
                            </td>
                            <td class="tdXSm">号：</td>
                            <td>
                                <input name="artName" id="artName" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdSm">电话：</td>
                            <td>
                                <input name="phone" id="phone" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">字辈：</td>
                            <td>
                                <input name="generationActor" id="generationActor" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdXSm">行：</td>
                            <td>
                                <input name="xing" id="xing" class="inputSm" type="text" disabled />
                            </td>
                            <td class="tdSm">邮箱：</td>
                            <td>
                                <input name="email" id="email" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="rightBorder">
                            <td class="tdSm">国籍：</td>
                            <td colspan="3">
                                <input name="nationality" id="nationality" type="text" disabled />
                            </td>
                            <td class="tdSm">父亲：</td>
                            <td style="text-align: left">
                                <select name="fatherId" id="fatherId" disabled style="max-width:23px">

                                </select>
                                <%--<input name="fatherId" id="fatherId" type="text" value="0" />--%>
                            </td>
                        </tr>
                        <tr class="bottomBorder rightBorder">
                            <td class="tdSm">民族：</td>
                            <td colspan="3">
                                <input name="nation" id="nation" type="text" disabled />
                            </td>
                            <td class="tdSm">母亲：</td>
                            <td style="text-align: left">
                                <select name="motherId" id="motherId" disabled style="max-width:23px">

                                </select>
                                <%--<input name="motherId" id="motherId" type="text" value="0" />--%>
                            </td>
                        </tr>
                        <tr style="height: 15px;"><td colspan="9"></td></tr>
                        <tr class="topBorder rightBorder leftBorder">
                            <td>出生时间：</td>
                            <td colspan="7">
                                <input name="birth_time" id="birth_time" class="form-datetime inputLg" type="text" disabled />
                            </td>
                            <td rowspan="5" style="text-align: center">状态：<br/>
                                <label><input type="radio" name="state" checked value="1" disabled />&nbsp;在世</label>
                                <br/>
                                <label><input type="radio" name="state" value="0" disabled />&nbsp;已逝</label>
                            </td>
                        </tr>
                        <tr class="rightBorder leftBorder">
                            <td>出生地点：</td>
                            <td colspan="7">
                                <input name="birthAddr" id="birthAddr" class="inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder">
                            <td>去世时间：</td>
                            <td colspan="7">
                                <input name="die_time" id="die_time" class="form-datetime inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder">
                            <td>卒葬地点：</td>
                            <td colspan="7">
                                <input name="dieAddr" id="dieAddr" class="inputLg" type="text" disabled />
                            </td>
                        </tr>
                        <tr class="leftBorder rightBorder bottomBorder">
                            <td>居住地址：</td>
                            <td colspan="7">
                                <input name="liveAddr" id="liveAddr" class="inputLg" type="text" disabled />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/viewFamilyTree_visitor.js"></script>
<script type="text/javascript">
    var winHeight = $(window).height();
    var familyId = "${familyId}";
    var familyFirstName = "${tFamily.familyFirstName}";
    $(function () {
        var treeContainerHeight = $(".treeContainer").height();
        var descHeight = $("#familyDesc").height();
        if(!descHeight){
            descHeight = 0;
        }
        $("#familyTree").attr("style","height:" + (winHeight - descHeight - 105) + "px");
        $(".rankDiv").attr("style","height:" + (winHeight - descHeight - 105) + "px")
        $("#goBack").click(function () {
            window.history.back();
        });
    });
</script>
</body>
</html>
