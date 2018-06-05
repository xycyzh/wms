<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $("[name=beginDate]").click(function () {
                WdatePicker({
                    maxDate: $("[name=endDate]").val(),
                    skin: 'whyGreen', dateFmt: 'yyyy-MM-dd HH:mm:ss'
                });
            });
            $("[name=endDate]").click(function () {
                WdatePicker({
                    minDate: $("[name=beginDate]").val(),
                    skin: 'whyGreen', dateFmt: 'yyyy-MM-dd HH:mm:ss'
                });
            });
            /*订单审核*/
            $(".btn_audit").click(function () {
                var url = $(this).data("url");
                createDialog("温馨提示", "确定要审核这条订单吗?", "warning", function () {
                    //发起ajax请求
                    $.ajax({
                        url: url,
                        type: "get",
                        success: function (data) {
                            if (data.success) {
                                createDialog("温馨提示", "审核成功", "face-smile", function () {
                                    window.location.href = "/orderBill/list.do";
                                }, false);
                            } else {
                                createDialog("温馨提示", "出错啦" + data.msg, "face-sad", true, false);
                            }
                        }
                    });
                }, true);
            });
        });
    </script>
    <title>叩丁狼教育PSS（演示版）-采购订单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/orderBill/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd HH:mm:ss" var="beginDate"/>
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd HH:mm:ss" var="endDate"/>
                        开始时间:<input type="text" class="ui_input_txt02 Wdate" name="beginDate"
                                    value="${beginDate}"/>~
                        结束时间:<input type="text" class="ui_input_txt02 Wdate" name="endDate" value="${endDate}"/>
                        供应商:<select class="ui_select01" name="supplierId">
                        <option value="-1">请选择</option>
                        <c:forEach items="${suppliers}" var="supplier">
                            <option value="${supplier.id}" ${supplier.id==qo.supplierId?'selected':''}>${supplier.name}</option>
                        </c:forEach>
                    </select>
                        审核状态:<select class="ui_select01" name="status">
                        <option value="-1">请选择</option>
                        <option value="0">未审核</option>
                        <option value="1">已审核</option>
                    </select>
                        <script type="text/javascript">
                            $("[name=status] option[value=${qo.status}]").prop("selected", true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_redirect"
                               data-url="/orderBill/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>订单编号</th>
                        <th>业务时间</th>
                        <th>供应商</th>
                        <th>采购总金额</th>
                        <th>采购总数量</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>审核状态</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.listData}" var="list">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb" value="${list.id}"/></td>
                            <td>${list.id}</td>
                            <td><fmt:formatDate value="${list.vdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${list.supplier.name}</td>
                            <td>${list.totalAmount}</td>
                            <td>${list.totalNumber}</td>
                            <td>${list.inputUser.name}</td>
                            <td>${list.auditor.name}</td>
                            <td>
                                <c:if test="${list.status==0}">
                                    <span style="color: red">未审核</span>
                                </c:if>
                                <c:if test="${list.status==1}">
                                    <span style="color: green">已审核</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${list.status==0}">
                                    <a href="javascript:;" class="btn_audit"
                                       data-url="/orderBill/audit.do?id=${list.id}">审核</a>
                                    <a href="javascript:;" class="btn_redirect"
                                       data-url="/orderBill/input.do?id=${list.id}">编辑</a>
                                    <a href="javascript:;" class="btn_delete"
                                       data-url="/orderBill/delete.do?id=${list.id}"
                                       data-redirect="/orderBill/list.do">删除</a>
                                </c:if>
                                <c:if test="${list.status==1}">
                                    <a href="javascript:;" class="btn_redirect"
                                       data-url="/orderBill/view.do?id=${list.id}">查看</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <jsp:include page="/WEB-INF/views/commons/common_page.jsp"/>
        </div>
    </div>
</form>
</body>
</html>

