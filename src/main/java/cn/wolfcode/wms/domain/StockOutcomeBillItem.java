package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class StockOutcomeBillItem extends BaseDomain {
    /**
     * 出库订单:销售价
     */
    private BigDecimal salePrice;

    private BigDecimal number;

    private BigDecimal amount;

    private String remark;

    private Product product;

    private Long bill_id;
}