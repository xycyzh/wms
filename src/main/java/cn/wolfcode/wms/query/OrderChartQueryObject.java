package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class OrderChartQueryObject extends QueryObject {
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
    private Long supplierId = -1L;
    private Long brandId = -1L;
    private String groupBy = "e.name";
    //订货人员 货品名称 供应商 品牌 订货日期(月) 订货日期(日)
    public final static Map<String, String> map = new LinkedHashMap<>();

    static {
        map.put("e.name", "订货人员");
        map.put("p.name", "货品名称");
        map.put("s.name", "供应商");
        map.put("p.brandName", "品牌");
        map.put("DATE_FORMAT(bill.vdate,'%Y-%m')", "订货日期(月)");
        map.put("DATE_FORMAT(bill.vdate,'%Y-%m-%d')", "订货日期(日)");
    }
}
