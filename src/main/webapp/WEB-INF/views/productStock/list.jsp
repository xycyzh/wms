<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>叩丁狼教育PSS（演示版）-及时库存报表</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/productStock/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        货品名称或编号:
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        所在仓库:
                        <select class="ui_select01" name="depotId">
                            <option value="-1">请选择</option>
                            <c:forEach items="${depots}" var="depot">
                                <option value="${depot.id}" ${depot.id==qo.depotId?'selected':''}>${depot.name}</option>
                            </c:forEach>
                        </select>
                        货品品牌:
                        <select class="ui_select01" name="brandId">
                            <option value="-1">请选择</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}" ${brand.id==qo.brandId?'selected':''}>${brand.name}</option>
                            </c:forEach>
                        </select>
                        库存阈值:<input type="text" class="ui_input_txt04" name="limitNumber" value="${qo.limitNumber}"/>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>仓库</th>
                        <th>商品编码</th>
                        <th>商品名称</th>
                        <th>品牌</th>
                        <th>库存数量</th>
                        <th>成本</th>
                        <th>库存汇总</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.listData}" var="list">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb" value=""/></td>
                            <td>${list.depot.name}</td>
                            <td>${list.product.sn}</td>
                            <td>${list.product.name}</td>
                            <td>${list.product.brandName}</td>
                            <td>${list.storeNumber}</td>
                            <td>${list.price}</td>
                            <td>${list.amount}</td>
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

