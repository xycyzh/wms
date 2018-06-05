package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class SaleChartQueryObject extends QueryObject {
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Long clientId = -1L;
    private Long brandId = -1L;
    private String groupBy = "e.name";
    //销售人员 货品名称 客户 品牌 订货日期(月) 订货日期(日)
    public final static Map<String, String> map = new LinkedHashMap<>();

    static {
        map.put("e.name", "销售人员");
        map.put("p.name", "货品名称");
        map.put("c.name", "客户");
        map.put("p.brandName", "品牌");
        map.put("DATE_FORMAT(sa.vdate,'%Y-%m')", "订货日期(月)");
        map.put("DATE_FORMAT(sa.vdate,'%Y-%m-%d')", "订货日期(日)");
    }
}
