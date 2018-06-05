$(function () {
    $('#pwd').keypress(enterLogin).keydown(enterLogin);
});

function enterLogin(e) { // 传入 event
    var e = e || window.event;
    if (e.keyCode == 13) {
        login();
    }
}

$(function () {
    $("#login_sub").click(function () {
        login();
    });
});

function login() {
    var dialog = $.dialog({
        title: "温馨提示",
        icon: "warning"
    });
    $("#submitForm").ajaxSubmit(function (data) {
        dialog.content(data.msg);
        if (data.success) {
            console.log(1);
            dialog.button({
                name: "确定",
                callback: function () {
                    window.location.href = "/main.do"
                }
            });
        } else {
            console.log(2);
            dialog.button({
                name: "确定"
            });
        }
    });
}
