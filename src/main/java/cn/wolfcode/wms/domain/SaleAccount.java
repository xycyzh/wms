package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class SaleAccount extends BaseDomain {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date vdate;
    /**
     * 销售数量
     */
    private BigDecimal number;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 成本总额
     */
    private BigDecimal costAmount;
    /**
     * 销售价
     */
    private BigDecimal salePrice;
    /**
     * 销售总额
     */
    private BigDecimal saleAmount;
    /**
     * 商品id
     */
    private Long product_id;
    /**
     * 销售人员id
     */
    private Long saleman_id;
    /**
     * 客户id
     */
    private Long client_id;
}
