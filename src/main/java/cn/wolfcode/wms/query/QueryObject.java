package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {
    private Integer currentPage = 1;
    private Integer pageSize = 5;

    public int getStartIndex() {
        return (currentPage - 1) * pageSize;
    }

    /**
     * 将空字符串转换成null
     *
     * @param str 字符串
     * @return 返回空字符串
     */
    public String str2null(String str) {
        if (str == null || "".equals(str.trim())) {
            return null;
        }
        return str;
    }
}
