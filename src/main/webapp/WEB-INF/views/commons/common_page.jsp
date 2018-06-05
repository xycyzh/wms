<jsp:useBean id="pageResult" scope="request" type="cn.wolfcode.wms.query.PageResult"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    $(function () {
        $(".btn_page").click(function () {
            var page = $(this).data("page") || 1;
            $("input[name=currentPage]").val(page);
            $("#searchForm").submit();
        });

        $(":input[name=pageSize]").change(function () {
            $("input[name=currentPage]").val(1);
            $("#searchForm").submit();
        });

        $(":input[name=pageSize] option").each(function (index, option) {
            if ($(option).val() == "${pageResult.pageSize}") {
                $(option).prop("selected", true);
            }
        });
    });
</script>
<div class="ui_tb_h30">
    <div class="ui_flt" style="height: 30px; line-height: 30px;">
        共有
        <span class="ui_txt_bold04">${pageResult.totalCount}</span>
        条记录，当前第
        <span class="ui_txt_bold04">${pageResult.currentPage}/${pageResult.totalPage}</span>
        页
    </div>
    <div class="ui_frt">
        <input type="button" value="首页" class="ui_input_btn01 btn_page" data-page="1"/>
        <input type="button" value="上一页" class="ui_input_btn01 btn_page" data-page="${pageResult.prevPage}"/>
        <input type="button" value="下一页" class="ui_input_btn01 btn_page" data-page="${pageResult.nextPage}"/>
        <input type="button" value="尾页" class="ui_input_btn01 btn_page" data-page="${pageResult.totalPage}"/>

        <select list="{5,10,20,50}" name="pageSize" class="ui_select02">
            <option ${pageResult.pageSize==5?'selected':''}>5</option>
            <option ${pageResult.pageSize==10?'selected':''}>10</option>
            <option ${pageResult.pageSize==20?'selected':''}>20</option>
            <option ${pageResult.pageSize==50?'selected':''}>50</option>
        </select>
        转到第<input type="number" max="${pageResult.totalPage}" min="1" name="currentPage"
                  value="${pageResult.currentPage}" class="ui_input_txt01"/>页
        <input type="button" class="ui_input_btn01 btn_page" value="跳转"/>
    </div>
</div>