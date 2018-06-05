package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 库存表
 */
@Setter
@Getter
public class ProductStock extends BaseDomain {
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存量
     */
    private BigDecimal storeNumber;
    /**
     * 库存总额
     */
    private BigDecimal amount;
    /**
     * 关联product表
     */
    private Product product;
    /**
     * 关联仓库表
     */
    private Depot depot;
}
