$(function () {
    //权限的左移右移
    $("#selectAll").click(function () {
        $(".all_permission option").appendTo($(".self_permission"));
    });
    $("#deselectAll").click(function () {
        $(".self_permission option").appendTo($(".all_permission"));
    });
    $("#select").click(function () {
        $(".all_permission option:selected").appendTo($(".self_permission"));
    });
    $("#deselect").click(function () {
        $(".self_permission option:selected").appendTo($(".all_permission"));
    });
    //菜单的左移右移
    $("#mselectAll").click(function () {
        $(".all_menus option").appendTo($(".self_menus"));
    });
    $("#mdeselectAll").click(function () {
        $(".self_menus option").appendTo($(".all_menus"));
    });
    $("#mselect").click(function () {
        $(".all_menus option:selected").appendTo($(".self_menus"));
    });
    $("#mdeselect").click(function () {
        $(".self_menus option:selected").appendTo($(".all_menus"));
    });
});

$(function () {
    //回显的时候把右边已存在的从左边删除
    //先拿到右边所有的id数组
    var ids = $(".self_permission option").map(function (index, option) {
        return $(option).val();
    });
    //再遍历左边
    $(".all_permission option").each(function (index, option) {
        //判断
        if ($.inArray($(option).val(), ids) > -1) {
            $(option).remove();
        }
    });

    var menusIds = $(".self_menus option").map(function (index, option) {
        return $(option).val();
    });
    //再遍历左边
    $(".all_menus option").each(function (index, option) {
        //判断
        if ($.inArray($(option).val(), menusIds) > -1) {
            $(option).remove();
        }
    });
});