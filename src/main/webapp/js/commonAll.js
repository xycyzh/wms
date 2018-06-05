/** table鼠标悬停换色* */
$(function () {
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({background: "#CDDAEB"});
        $(this).children('td').each(function (index, ele) {
            $(ele).css({color: "#1D1E21"});
        });
    }).mouseout(function () {
        $(this).css({background: "#FFF"});
        $(this).children('td').each(function (index, ele) {
            $(ele).css({color: "#909090"});
        });
    });
});
/*统一跳转*/
$(function () {
    $(".btn_redirect").click(function () {
        var url = $(this).data("url");
        window.location.href = url;
    });
});
/*统一删除*/
$(function () {
    $(".btn_delete").click(function () {
        var url = $(this).data("url");
        var redirect = $(this).data("redirect");
        createDialog("温馨提示", "确定要删除这条数据吗?", "warning", function () {
            //发起ajax请求
            $.ajax({
                url: url,
                type: "get",
                success: function (data) {
                    if (data.success) {
                        createDialog("温馨提示", "删除成功", "face-smile", function () {
                            window.location.href = redirect;
                        }, false);
                    } else {
                        createDialog("温馨提示", "出错啦" + data.msg, "face-sad", true, false);
                    }
                }
            });
        }, true);
    });
});
/*批量删除*/
$(function () {
    $(".btn_batchDelete").click(function () {
        var url = $(this).data("url");
        var dialog = $.dialog({
            title: "温馨提示",
            icon: "warning"
        });
        var checkboxs = $(".acb:checked");
        if (checkboxs.size() === 0) {
            //表明没有选中
            dialog.content("请选择要删除的选项");
            dialog.button({
                name: "确定"
            });
        } else {
            dialog.content("确定要删除吗?");
            dialog.button({
                name: "确定",
                callback: function () {
                    var ids = $.map(checkboxs, function (param) {
                        return $(param).val();
                    });
                    $.ajax({
                        url: url,
                        type: "post",
                        data: {ids: ids},
                        success: function (data) {
                            var dialog = $.dialog({
                                title: "温馨提示",
                                icon: "warning",
                                content: data.msg
                            });
                            if (data.success) {
                                dialog.button({
                                    name: "确定",
                                    callback: function () {
                                        window.location.reload();
                                    }
                                });
                            } else {
                                dialog.button({
                                    name: "确定"
                                });
                            }
                        }
                    });
                }
            });
        }
    });
});

/*批量删除的全选和全不选*/
$(function () {
    $("#all").click(function () {
        $(".acb").prop("checked", this.checked);
    });
});

function createDialog(title, content, icon, callback, cancel) {
    $.dialog({
        title: title,
        content: content,
        icon: icon,
        ok: callback,
        cancel: cancel
    });
}

