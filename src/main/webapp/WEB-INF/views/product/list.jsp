<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancyBox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/fancyBox/jquery.fancybox.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $('.fancybox-buttons').fancybox();
        });
    </script>
    <title>叩丁狼教育PSS（演示版）-商品管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/product/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        关键字
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        所属品牌
                        <select class="ui_select01" name="brandId">
                            <option value="-1">请选择</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}" ${brand.id==qo.brandId?'selected':''}>${brand.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_redirect"
                               data-url="/product/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>图片</th>
                        <th>名称</th>
                        <th>编码</th>
                        <th>品牌</th>
                        <th>成本价</th>
                        <th>销售价</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.listData}" var="list">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb" value="${list.id}"/></td>
                            <td>
                                <a class="fancybox-buttons" href="${list.imagePath}" title="${list.name}">
                                    <img src="${list.smallImagePath}"
                                         class="list_img_min"/></a>
                            </td>
                            <td>${list.name}</td>
                            <td>${list.sn}</td>
                            <td>${list.brandName}</td>
                            <td>${list.costPrice}</td>
                            <td>${list.salePrice}</td>
                            <td>
                                <a href="javascript:;" class="btn_redirect"
                                   data-url="/product/input.do?id=${list.id}">编辑</a>
                                <a href="javascript:;" class="btn_delete"
                                   data-url="/product/delete.do?id=${list.id}&imagePath=${list.imagePath}"
                                   data-redirect="/product/list.do">删除</a>
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

