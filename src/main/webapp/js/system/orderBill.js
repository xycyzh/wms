$(function () {
    /*表单提交事件,重新给name赋值,设置索引*/
    $("#editForm").submit(function () {
        var trs = $("#edit_table_body tr");
        for (var i = 0; i < trs.size(); i++) {
            var tr = $(trs[i]);
            tr.find("input[tag=pid]").prop("name", "orderBillItems[" + i + "].product.id");
            tr.find("input[tag=costPrice]").prop("name", "orderBillItems[" + i + "].costPrice");
            tr.find("input[tag=number]").prop("name", "orderBillItems[" + i + "].number");
            tr.find("input[tag=remark]").prop("name", "orderBillItems[" + i + "].remark");
        }
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
                    window.location.href = "/orderBill/list.do"
                }
            });
        } else {
            dialog.button({
                name: "确定"
            });
        }
    });
});

/*克隆事件*/
$(function () {
    $(".appendRow").click(function () {
        var cloneTr = $("#edit_table_body tr:first").clone();
        cloneTr.find("input").val("");
        cloneTr.find("span").text("");
        cloneTr.appendTo("#edit_table_body");
    });
});

$(function () {
    //从jquery1.7之后开始,支持统一事件绑定
    $("#edit_table_body").on("click", ".searchproduct", function () {
        /*放大镜功能*/
        //找到当前行的tr
        var tr = $(this).closest("tr");
        $.dialog.open("/product/selectList.do", {
            title: '商品信息',
            width: 1000,
            height: 500,
            close: function () {
                //子窗口向父窗口传递的数据
                var json = $.dialog.data("json");
                if (json) {
                    tr.find("input[tag=name]").val(json["name"]);
                    tr.find("input[tag=pid]").val(json["id"]);
                    tr.find("span[tag=brand]").text(json["brandName"]);
                    tr.find("input[tag=costPrice]").val(json["costPrice"]);
                    tr.find("input[tag=number]").val(1);
                    tr.find("span[tag=amount]").html(parseFloat(json["costPrice"]).toFixed(2));
                }
            }
        });
    }).on("change", "input[tag=costPrice],input[tag=number]", function () {
        //change事件
        //找到当前的tr
        var tr = $(this).closest("tr");
        var costPrice = tr.find("input[tag=costPrice]").val() || 0;
        var number = tr.find("input[tag=number]").val() || 0;
        tr.find("span[tag=amount]").text(parseFloat(costPrice * number).toFixed(2));
    }).on("click", ".removeItem", function () {
        //删除明细
        var trs = $("#edit_table_body tr");
        if (trs.size() > 1) {
            //说明明细数量大于1,这时候可以删除
            $(this).closest("tr").remove();
        } else {
            //就剩一条数据,就清空
            var tr = $(this).closest("tr");
            tr.find("input").val("");
            tr.find("span").text("");
            tr.find("span[tag=amount]").text("");
        }
    });
});