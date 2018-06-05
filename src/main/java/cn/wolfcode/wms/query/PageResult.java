package cn.wolfcode.wms.query;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class PageResult {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalCount;
    private Integer prevPage;
    private Integer nextPage;
    private List<?> listData;

    public PageResult(Integer currentPage, Integer pageSize, Integer totalCount, List<?> listData) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.listData = listData;
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.prevPage = currentPage - 1 > 0 ? currentPage - 1 : 1;
        this.nextPage = currentPage + 1 < totalPage ? currentPage + 1 : totalPage;
    }

    public PageResult(Integer pageSize) {
        this(1, pageSize, 0, Collections.EMPTY_LIST);
    }
}
