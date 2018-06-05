<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $(":input").prop("readOnly", true);
        });
    </script>
</head>
<body>
<input type="hidden" name="id" value="${stockIncomeBill.id}"/>
<div id="container">
    <div id="nav_links">
        <span style="color: #1A5CC6;">入库订单查看</span>
        <div id="page_close">
            <a>
                <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
            </a>
        </div>
    </div>
    <div class="ui_content">
        <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
            <tr>
                <td class="ui_text_rt" width="140">入库订单编号</td>
                <td class="ui_text_lt">
                    <input name="sn" value="${stockIncomeBill.sn}" class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">仓库</td>
                <td class="ui_text_lt">
                    <input type="text" value="${stockIncomeBill.depot.name}" class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">业务时间</td>
                <td class="ui_text_lt">
                    <fmt:formatDate value="${stockIncomeBill.vdate}" var="date" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <input name="vdate" value="${date}" class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">单据明细</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                        <thead>
                        <tr>
                            <th width="10"></th>
                            <th width="200">货品</th>
                            <th width="120">品牌</th>
                            <th width="80">价格</th>
                            <th width="80">数量</th>
                            <th width="80">金额小计</th>
                            <th width="150">备注</th>
                        </tr>
                        </thead>
                        <tbody id="edit_table_body">
                        <c:forEach items="${stockIncomeBill.stockIncomeBillItems}" var="stockIncomeBillItem">
                            <tr>
                                <td></td>
                                <td>
                                    <input disabled="true" readonly="true"
                                           value="${stockIncomeBillItem.product.name}" class="ui_input_txt02"
                                           tag="name"/>
                                    <img src="/images/common/search.png" class="searchproduct"/>
                                    <input type="hidden" value="${stockIncomeBillItem.product.id}"
                                           name="${stockIncomeBillItem.product.id}" tag="pid"/>
                                </td>
                                <td><span tag="brand">${stockIncomeBillItem.product.brandName}</span></td>
                                <td><input tag="costPrice" name=""
                                           class="ui_input_txt02" value="${stockIncomeBillItem.costPrice}"
                                           type="number"/></td>
                                <td><input tag="number" name="" value="${stockIncomeBillItem.number}"
                                           class="ui_input_txt02" type="number"/></td>
                                <td><span tag="amount">${stockIncomeBillItem.amount}</span></td>
                                <td><input tag="remark" name=""
                                           class="ui_input_txt02" value="${stockIncomeBillItem.remark}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td class="ui_text_lt">
                    &nbsp;<input type="button" value="返回列表" onclick="window.history.back()" class="ui_input_btn01"/>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>