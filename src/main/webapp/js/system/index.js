//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "." + myDay;
}

/**
 * 隐藏或者显示侧边栏
 *
 **/
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) {	// flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({width: '280px'});
        $('#top_nav').css({width: '77%', left: '304px'});
        $('#main').css({left: '280px'});
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({width: '60px'});
            $('#top_nav').css({width: '100%', left: '60px', 'padding-left': '28px'});
            $('#main').css({left: '60px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({width: '280px'});
            $('#top_nav').css({width: '77%', left: '304px', 'padding-left': '0px'});
            $('#main').css({left: '280px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
        }
    }
}

$(function () {
    loadDate();
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });
});
/*系统左边的菜单按钮切换*/
$(function () {
    $("#TabPage2 li").click(function () {
        //移除掉所有的样式
        $("#TabPage2 li").each(function (index, param) {
            $(param).removeClass("selected");
            $(param).find("img").prop("src", "/images/common/" + (index + 1) + ".jpg");
        });
        //当前选中的索引
        var index = $(this).index();
        $(this).addClass("selected");
        $(this).find("img").prop("src", "/images/common/" + (index + 1) + "_hover.jpg");
        $("#nav_module").find("img").prop("src", "/images/common/module_" + (index + 1) + ".png");
        initZTree(zNodes[$(this).data("rootmenu")]);
    });
});

/*系统菜单树zTree*/
//统一设置
var setting = {
    async: {
        autoParam: ["sn=parentSn"],
        enable: true,
        url: "/systemMenu/loadMenuByParentSn.do"
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: function (event, treeId, treeNode) {
            if (treeNode.action) {
                $("#rightMain").prop("src", treeNode.action);
            }
            var parentNode = treeNode.getParentNode();
            if (parentNode) {
                $("#here_area").html(parentNode.name + "&nbsp;>&nbsp;" + treeNode.name);
            } else {
                $("#here_area").html(treeNode.name);
            }
        }
    }
};

var zNodes = {
    "business": {id: 1, pId: 0, name: "业务模块", isParent: true, sn: "business"},
    "systemManage": {id: 2, pId: 0, name: "系统模块", isParent: true, sn: "system"},
    "charts": {id: 3, pId: 0, name: "报表模块", isParent: true, sn: "charts"}
};

$(function () {
    initZTree(zNodes["business"]);
});

function initZTree(node) {
    $.fn.zTree.init($("#dleft_tab1"), setting, node);
}

