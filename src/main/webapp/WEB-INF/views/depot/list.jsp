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

    <title>PSS-仓库管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/depot/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                </div>
                <div id="box_bottom">
                    <input type="button" value="新增" class="ui_input_btn01 btn_redirect"
                           data-url="/depot/input.do"/>
                </div>
            </div>
        </div>
    </div>
    <div class="ui_content">
        <div class="ui_tb">
            <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                <tr>
                    <th width="30"><input type="checkbox" id="all"/></th>
                    <th>编号</th>
                    <th>仓库名称</th>
                    <th>仓库地址</th>
                    <th>操作</th>
                </tr>
                <tbody>
                <c:forEach items="${pageResult.listData}" var="list">
                    <tr>
                        <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                        <td>${list.id}</td>
                        <td>${list.name}</td>
                        <td>${list.location}</td>
                        <td>
                            <a href="javascript:;" class="btn_redirect"
                               data-url="/depot/input.do?id=${list.id}">编辑</a>
                            <a href="javascript:;" class="btn_delete"
                               data-url="/depot/delete.do?id=${list.id}" data-redirect="/depot/list.do">删除</a>
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