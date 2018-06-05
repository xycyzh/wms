$(function () {
    /*表单提交事件,一定要在ajaxForm之前执行*/
    $("#editForm").submit(function () {
        var trs = $("#edit_table_body tr");
        trs.each(function (index, option) {
            $(option).find("[tag=pid]").prop("name", "stockIncomeBillItems[" + index + "].product.id");
            $(option).find("[tag=costPrice]").prop("name", "stockIncomeBillItems[" + index + "].costPrice");
            $(option).find("[tag=number]").prop("name", "stockIncomeBillItems[" + index + "].number");
            $(option).find("[tag=remark]").prop("name", "stockIncomeBillItems[" + index + "].remark");
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
                    window.location.href = "/stockIncomeBill/list.do"
                }
            });
        } else {
            dialog.button({
                name: "确定"
            });
        }
    });
});


/*添加明细*/
$(function () {
    $(".appendRow").click(function () {
        //复制tr
        var newTr = $("#edit_table_body tr:first").clone();
        //清空tr
        newTr.find("input").val("");
        newTr.find("span").text("");
        newTr.appendTo("#edit_table_body");
    });
});

/*统一点击事件*/
$(function () {
    $("#edit_table_body").on("click", ".removeItem", function () {
        //当前tr
        var tr = $(this).closest("tr");
        //删除明细,如果是最后一个就清空,如果不是最后一个,就删除
        var trs = $("#edit_table_body tr");
        if (trs.size() > 1) {
            //删除
            tr.remove();
        } else {
            tr.find("input").val("");
            tr.find("span").text("");
        }
    }).on("change", "[tag=costPrice],[tag=number]", function () {
        //当前tr
        var tr = $(this).closest("tr");
        var costPrice = parseFloat(tr.find("[tag=costPrice]").val()) || 0;
        var number = parseFloat(tr.find("[tag=number]").val()) || 0;
        var totalAmount = costPrice * number;
        tr.find("[tag=amount]").text(totalAmount.toFixed(2));
    }).on("click", ".searchproduct", function () {
        var tr = $(this).closest("tr");
        $.dialog.open("/product/selectList.do", {
            title: '商品信息',
            width: 1000,
            height: 500,
            close: function () {
                var json = $.dialog.data("json");
                //设置值
                tr.find("[tag=pid]").val(json.id);
                tr.find("[tag=name]").val(json.name);
                tr.find("[tag=brand]").text(json.brandName);
                tr.find("[tag=costPrice]").val(json.costPrice);
                tr.find("[tag=number]").val(1);
                tr.find("[tag=amount]").text(json.costPrice.toFixed(2));
            }
        });
    });
});