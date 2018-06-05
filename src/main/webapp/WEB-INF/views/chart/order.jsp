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
        });
    </script>
    <title>叩丁狼教育PSS（演示版）-订货报表</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/chart/order.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd HH:mm:ss" var="beginDate"/>
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd HH:mm:ss" var="endDate"/>
                        业务时间:<input type="text" class="ui_input_txt02 Wdate" name="beginDate"
                                    value="${beginDate}"/>~
                        <input type="text" class="ui_input_txt02 Wdate" name="endDate" value="${endDate}"/>
                        供应商:<select class="ui_select01" name="supplierId">
                        <option value="-1">请选择</option>
                        <c:forEach items="${suppliers}" var="supplier">
                            <option value="${supplier.id}" ${supplier.id==qo.supplierId?'selected':''}>${supplier.name}</option>
                        </c:forEach>
                    </select>
                        货品品牌:<select class="ui_select01" name="brandId">
                        <option value="-1">请选择</option>
                        <c:forEach items="${brands}" var="brand">
                            <option value="${brand.id}" ${brand.id==qo.brandId?'selected':''}>${brand.name}</option>
                        </c:forEach>
                    </select>
                        分组:<select class="ui_select01" name="groupBy">
                        <c:forEach items="${groups}" var="group">
                            <option value="${group.key}" ${group.key==qo.groupBy?'selected':''}>${group.value}</option>
                        </c:forEach>
                    </select>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>分组类型</th>
                        <th>采购总数量</th>
                        <th>采购总金额</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${orderChart}" var="item">
                        <tr>
                            <td>${item.groupType}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.totalAmount}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>

