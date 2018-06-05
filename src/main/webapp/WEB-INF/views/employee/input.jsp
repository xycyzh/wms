<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/system/employee.js"></script>
    <script type="text/javascript">
        $(function () {
//            $("#editForm").validate({
//                rules: {
//                    name: {
//                        required: true,
//                        minlength: 4
//                    },
//                    password: {
//                        required: true,
//                        minlength: 6
//                    },
//                    repassword: {
//                        required: true,
//                        equalTo: "#password"
//                    },
//                    email: {
//                        required: true,
//                        email: true
//                    },
//                    age: {
//                        required: true,
//                        range: [18, 60]
//                    }
//                },
//                messages: {
//                    name: {
//                        required: "请输入用户名",
//                        minlength: "用户名长度不能低于{0}位"
//                    },
//                    password: {
//                        required: "请输入密码",
//                        minlength: "密码长度不能小于{0}位"
//                    },
//                    repassword: {
//                        required: "请输入密码",
//                        equalTo: "两次密码输入不一致"
//                    },
//                    email: {
//                        required: "邮箱不能为空",
//                        email: "邮箱地址格式不正确"
//                    },
//                    age: {
//                        required: "年龄不能为空",
//                        range: "年龄范围必须在{0}和{1}之间"
//                    }
//                }
//            });
            //增加提交表单事件,让右边的所有选项被选中
            $("#editForm").submit(function () {
                $(".self_role option").each(function (index, option) {
                    $(option).prop("selected", true);
                });
            });
            $("#editForm").ajaxForm(function (data) {
                console.log(data);
                var dialog = $.dialog({
                    title: "温馨提示",
                    content: data.msg,
                    icon: "warning"
                });
                if (data.success) {
                    dialog.button({
                        name: "确定",
                        callback: function () {
                            window.location.href = "/employee/list.do"
                        }
                    });
                } else {
                    dialog.button({
                        name: "确定"
                    });
                }
            });
        });
    </script>
</head>
<body>
<form name="editForm" action="/employee/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${employee.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">用户编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">用户名</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${employee.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <c:if test="${employee.id==null}">
                    <tr>
                        <td class="ui_text_rt" width="140">密码</td>
                        <td class="ui_text_lt">
                            <input type="password" name="password" id="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="ui_text_rt" width="140">验证密码</td>
                        <td class="ui_text_lt">
                            <input name="repassword" type="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">Email</td>
                    <td class="ui_text_lt">
                        <input name="email" value="${employee.email}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">年龄</td>
                    <td class="ui_text_lt">
                        <input name="age" value="${employee.age}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所属部门</td>
                    <td class="ui_text_lt">
                        <select name="dept.id" class="ui_select01">
                            <c:forEach items="${departments}" var="department">
                                <option value="${department.id}" ${department.id==employee.dept.id?'selected':''}>${department.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">超级管理员</td>
                    <td class="ui_text_lt">
                        <input type="checkbox" name="admin" class="ui_checkbox01" ${employee.admin?'checked':''}>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">角色</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_role">
                                        <c:forEach items="${roles}" var="role">
                                            <option value="${role.id}">${role.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="select" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAll" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselect" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAll" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <select multiple="true" name="roleIds" class="ui_multiselect01 self_role">
                                        <c:forEach items="${employee.roles}" var="role">
                                            <option value="${role.id}">${role.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
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