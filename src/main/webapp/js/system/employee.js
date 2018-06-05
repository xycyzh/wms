$(function () {
    $("#selectAll").click(function () {
        $(".all_role option").appendTo($(".self_role"));
    });
    $("#deselectAll").click(function () {
        $(".self_role option").appendTo($(".all_role"));
    });
    $("#select").click(function () {
        $(".all_role option:selected").appendTo($(".self_role"));
    });
    $("#deselect").click(function () {
        $(".self_role option:selected").appendTo($(".all_role"));
    });
});

$(function () {
    //回显的时候把右边已存在的从左边删除
    //先拿到右边所有的id数组
    var ids = $(".self_role option").map(function (index, option) {
        return $(option).val();
    });
    //再遍历左边
    $(".all_role option").each(function (index, option) {
        //判断
        if ($.inArray($(option).val(), ids) > -1) {
            $(option).remove();
        }
    });
});