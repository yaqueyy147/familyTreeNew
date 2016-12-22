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
    <link rel="stylesheet" type="text/css" href="/static/css/fronts/viewFamilyTree.css" />
    <%@include file="common/commonCss.html"%>
</head>
<body>
<%@include file="common/header.jsp" %>
<div class="container" style="margin-top: 50px">
    <a href="#addModal" data-toggle="modal" data-target="#addModal">添加族人</a>
    <!-- 添加族人 Modal -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
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
                            <form id="peopleForm" action="" method="post">
                            <table>
                                <tr style="border: solid 2px #9d9d9d;">
                                    <td class="tdLg" style="text-align: left" colspan="9">编号:</td>
                                </tr>
                                <tr style="height: 15px;"><td colspan="9"></td></tr>
                                <tr class="topBorder rightBorder">
                                    <td class="photoBox" rowspan="8" colspan="3">

                                    </td>
                                    <td class="tdSm">
                                        姓名：
                                    </td>
                                    <td colspan="3">
                                        <input name="name" id="name" type="text"/>
                                    </td>
                                    <td class="tdSm">
                                        特殊说明：
                                    </td>
                                    <td><input name="specialRemark" id="specialRemark" type="text" /></td>
                                </tr>
                                <tr class="rightBorder">
                                    <td class="tdSm">
                                        曾用名：
                                    </td>
                                    <td colspan="3">
                                        <input name="usedName" id="usedName" type="text"/>
                                    </td>
                                    <td class="tdSm">
                                        学历：
                                    </td>
                                    <td>
                                        <input name="education" id="education" type="text" />
                                    </td>
                                </tr>
                                <tr class="rightBorder">
                                    <td class="tdSm">性别：</td>
                                    <td>
                                        <select name="sex" id="sex">
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
                                        <input name="job" id="job" type="text" />
                                    </td>
                                </tr>
                                <tr class="rightBorder">
                                    <td class="tdSm">辈分：</td>
                                    <td class="tdXSm">
                                        <input name="familyGeneration" id="familyGeneration" class="inputSm" type="text" />
                                    </td>
                                    <td class="tdXSm">字：</td>
                                    <td class="tdXSm">
                                        <input name="cName" id="cName" class="inputSm" type="text" />
                                    </td>
                                    <td class="tdSm">身份证：</td>
                                    <td>
                                        <input name="idCard" id="idCard" type="text" />
                                    </td>
                                </tr>
                                <tr class="rightBorder">
                                    <td class="tdSm">排行：</td>
                                    <td>
                                        <input name="familyRank" id="familyRank" class="inputSm" type="text" />
                                    </td>
                                    <td class="tdXSm">号：</td>
                                    <td>
                                        <input name="artName" id="artName" class="inputSm" type="text" />
                                    </td>
                                    <td class="tdSm">电话：</td>
                                    <td>
                                        <input name="phone" id="phone" type="text" />
                                    </td>
                                </tr>
                                <tr class="rightBorder">
                                    <td class="tdSm">字辈：</td>
                                    <td>
                                        <input name="generationActor" id="generationActor" class="inputSm" type="text" />
                                    </td>
                                    <td class="tdXSm">行：</td>
                                    <td>
                                        <input name="xing" id="xing" class="inputSm" type="text" />
                                    </td>
                                    <td class="tdSm">邮箱：</td>
                                    <td>
                                        <input name="email" id="email" type="text" />
                                    </td>
                                </tr>
                                <tr class="rightBorder">
                                    <td class="tdSm">国籍：</td>
                                    <td colspan="3">
                                        <input name="nationality" id="nationality" type="text" />
                                    </td>
                                    <td class="tdSm">父亲：</td>
                                    <td>
                                        <input name="" type="text" />
                                    </td>
                                </tr>
                                <tr class="bottomBorder rightBorder">
                                    <td class="tdSm">民族：</td>
                                    <td colspan="3">
                                        <input name="nation" id="nation" type="text" />
                                    </td>
                                    <td class="tdSm">母亲：</td>
                                    <td><input type="text" /></td>
                                </tr>
                                <tr style="height: 15px;"><td colspan="9"></td></tr>
                                <tr class="topBorder rightBorder leftBorder">
                                    <td>出生时间：</td>
                                    <td colspan="7">
                                        <input name="birthTime" id="birthTime" class="form-datetime inputLg" type="text" />
                                    </td>
                                    <td rowspan="5" style="text-align: center">状态：<br/>
                                        <label><input type="radio" name="state" />&nbsp;在世</label>
                                        <br/>
                                        <label><input type="radio" name="state" />&nbsp;已逝</label>
                                    </td>
                                </tr>
                                <tr class="rightBorder leftBorder">
                                    <td>出生地点：</td>
                                    <td colspan="7">
                                        <input name="birthAddr" id="birthAddr" class="inputLg" type="text" />
                                    </td>
                                </tr>
                                <tr class="leftBorder rightBorder">
                                    <td>去世时间：</td>
                                    <td colspan="7">
                                        <input name="dieTime" id="dieTime" class="form-datetime inputLg" type="text" />
                                    </td>
                                </tr>
                                <tr class="leftBorder rightBorder">
                                    <td>卒葬地点：</td>
                                    <td colspan="7">
                                        <input name="dieAddr" id="dieAddr" class="inputLg" type="text" />
                                    </td>
                                </tr>
                                <tr class="leftBorder rightBorder bottomBorder">
                                    <td>居住地址：</td>
                                    <td colspan="7">
                                        <input name="liveAddr" id="liveAddr" class="inputLg" type="text" />
                                    </td>
                                </tr>
                                <tr style="height: 15px;"><td colspan="9"></td></tr>
                                <tr>
                                    <td colspan="9" style="text-align: right">
                                        <input id="savePeople" type="button" class="btn btn-primary" value="保 存" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                        </div>

                        <div role="tabpanel" class="tab-pane" id="mateInfo">
                            <form id="mateForm" action="" method="post">
                                <table>
                                    <tr style="border: solid 2px #9d9d9d;">
                                        <td class="tdLg" style="text-align: left" colspan="9">编号:</td>
                                    </tr>
                                    <tr style="height: 15px;"><td colspan="9"></td></tr>
                                    <tr class="topBorder rightBorder">
                                        <td class="photoBox" rowspan="8" colspan="3">

                                        </td>
                                        <td class="tdSm">
                                            姓名：
                                        </td>
                                        <td colspan="3">
                                            <input name="name" id="matename" type="text"/>
                                        </td>
                                        <td class="tdSm">
                                            特殊说明：
                                        </td>
                                        <td><input name="specialRemark" id="matespecialRemark" type="text" /></td>
                                    </tr>
                                    <tr class="rightBorder">
                                        <td class="tdSm">
                                            曾用名：
                                        </td>
                                        <td colspan="3">
                                            <input name="usedName" id="mateusedName" type="text"/>
                                        </td>
                                        <td class="tdSm">
                                            学历：
                                        </td>
                                        <td>
                                            <input name="education" id="mateeducation" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="rightBorder">
                                        <td class="tdSm">性别：</td>
                                        <td>
                                            <select name="sex" id="matesex">
                                                <option value="1">男</option>
                                                <option value="0">女</option>
                                            </select>
                                        </td>
                                        <td colspan="2">
                                            <select name="mateType" id="matemateType" disabled>
                                                <option value="0"></option>
                                                <option value="1">妻子</option>
                                                <option value="2">丈夫</option>
                                            </select>
                                        </td>
                                        <td class="tdSm">
                                            职业：
                                        </td>
                                        <td>
                                            <input name="job" id="matejob" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="rightBorder">
                                        <td class="tdSm">辈分：</td>
                                        <td class="tdXSm">
                                            <input name="familyGeneration" id="matefamilyGeneration" class="inputSm" type="text" />
                                        </td>
                                        <td class="tdXSm">字：</td>
                                        <td class="tdXSm">
                                            <input name="cName" id="matecName" class="inputSm" type="text" />
                                        </td>
                                        <td class="tdSm">身份证：</td>
                                        <td>
                                            <input name="idCard" id="mateidCard" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="rightBorder">
                                        <td class="tdSm">排行：</td>
                                        <td>
                                            <input name="familyRank" id="matefamilyRank" class="inputSm" type="text" />
                                        </td>
                                        <td class="tdXSm">号：</td>
                                        <td>
                                            <input name="artName" id="mateartName" class="inputSm" type="text" />
                                        </td>
                                        <td class="tdSm">电话：</td>
                                        <td>
                                            <input name="phone" id="matephone" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="rightBorder">
                                        <td class="tdSm">字辈：</td>
                                        <td>
                                            <input name="generationActor" id="mategenerationActor" class="inputSm" type="text" />
                                        </td>
                                        <td class="tdXSm">行：</td>
                                        <td>
                                            <input name="xing" id="matexing" class="inputSm" type="text" />
                                        </td>
                                        <td class="tdSm">邮箱：</td>
                                        <td>
                                            <input name="email" id="mateemail" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="rightBorder">
                                        <td class="tdSm">国籍：</td>
                                        <td colspan="3">
                                            <input name="nationality" id="matenationality" type="text" />
                                        </td>
                                        <td class="tdSm">父亲：</td>
                                        <td>
                                            <input name="" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="bottomBorder rightBorder">
                                        <td class="tdSm">民族：</td>
                                        <td colspan="3">
                                            <input name="nation" id="matenation" type="text" />
                                        </td>
                                        <td class="tdSm">母亲：</td>
                                        <td><input type="text" /></td>
                                    </tr>
                                    <tr style="height: 15px;"><td colspan="9"></td></tr>
                                    <tr class="topBorder rightBorder leftBorder">
                                        <td>出生时间：</td>
                                        <td colspan="7">
                                            <input name="birthTime" id="matebirthTime" class="form-datetime inputLg" type="text" />
                                        </td>
                                        <td rowspan="5" style="text-align: center">状态：<br/>
                                            <label><input type="radio" name="state" />&nbsp;在世</label>
                                            <br/>
                                            <label><input type="radio" name="state" />&nbsp;已逝</label>
                                        </td>
                                    </tr>
                                    <tr class="rightBorder leftBorder">
                                        <td>出生地点：</td>
                                        <td colspan="7">
                                            <input name="birthAddr" id="matebirthAddr" class="inputLg" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="leftBorder rightBorder">
                                        <td>去世时间：</td>
                                        <td colspan="7">
                                            <input name="dieTime" id="matedieTime" class="form-datetime inputLg" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="leftBorder rightBorder">
                                        <td>卒葬地点：</td>
                                        <td colspan="7">
                                            <input name="dieAddr" id="matedieAddr" class="inputLg" type="text" />
                                        </td>
                                    </tr>
                                    <tr class="leftBorder rightBorder bottomBorder">
                                        <td>居住地址：</td>
                                        <td colspan="7">
                                            <input name="liveAddr" id="mateliveAddr" class="inputLg" type="text" />
                                        </td>
                                    </tr>
                                    <tr style="height: 15px;"><td colspan="9"></td></tr>
                                    <tr>
                                        <td colspan="9" style="text-align: right">
                                            <input id="saveMate" type="button" class="btn btn-primary" value="保 存" />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="history">
                            <textarea cols="30" rows="10" style="width: 100%"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.html"%>
<script type="text/javascript" src="/static/frontJs/viewFamilyTree.js"></script>
</body>
</html>
