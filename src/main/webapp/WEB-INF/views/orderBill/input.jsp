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
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.source.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/system/orderBill.js"></script>
</head>
<body>
<form name="editForm" action="/orderBill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${orderBill.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">采购订单编号</td>
                    <td class="ui_text_lt">
                        <input name="sn" value="${orderBill.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <select name="supplier.id" class="ui_select03">
                            <c:forEach items="${suppliers}" var="supplier">
                                <option value="${supplier.id}" ${supplier.id==orderBill.supplier.id?'selected':''}>${supplier.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <fmt:formatDate value="${orderBill.vdate}" var="date" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <input name="vdate" value="${date}"
                               onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               onClick="WdatePicker()"
                               class="ui_input_txt02 Wdate"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
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
                                <th width="60">操作</th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:choose>
                                <c:when test="${orderBill.id==null}">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <input type="hidden" name="" tag="pid"/>
                                        </td>
                                        <td><span tag="brand"></span></td>
                                        <td><input tag="costPrice" name=""
                                                   class="ui_input_txt02" type="number"/></td>
                                        <td><input tag="number" name=""
                                                   class="ui_input_txt02" type="number"/></td>
                                        <td><span tag="amount"></span></td>
                                        <td><input tag="remark" name=""
                                                   class="ui_input_txt02"/></td>
                                        <td>
                                            <a href="javascript:;" class="removeItem">删除明细</a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${orderBill.orderBillItems}" var="orderBillItem">
                                        <tr>
                                            <td></td>
                                            <td>
                                                <input disabled="true" readonly="true"
                                                       value="${orderBillItem.product.name}" class="ui_input_txt02"
                                                       tag="name"/>
                                                <img src="/images/common/search.png" class="searchproduct"/>
                                                <input type="hidden" value="${orderBillItem.product.id}"
                                                       name="${orderBillItem.product.id}" tag="pid"/>
                                            </td>
                                            <td><span tag="brand">${orderBillItem.product.brandName}</span></td>
                                            <td><input tag="costPrice" name=""
                                                       class="ui_input_txt02" value="${orderBillItem.costPrice}"
                                                       type="number"/></td>
                                            <td><input tag="number" name="" value="${orderBillItem.number}"
                                                       class="ui_input_txt02" type="number"/></td>
                                            <td><span tag="amount">${orderBillItem.amount}</span></td>
                                            <td><input tag="remark" name=""
                                                       class="ui_input_txt02" value="${orderBillItem.remark}"/></td>
                                            <td>
                                                <a href="javascript:;" class="removeItem">删除明细</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>


                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="submit" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>