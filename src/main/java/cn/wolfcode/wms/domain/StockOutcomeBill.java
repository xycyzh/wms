package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class StockOutcomeBill extends BaseAuditDomain {
    private String sn;
    /**
     * 业务时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date vdate;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 总数量
     */
    private BigDecimal totalNumber;
    /**
     * 仓库
     */
    private Depot depot;
    /**
     * 客户
     */
    private Client client;
    /**
     * 入库订单明细:一对多关联关系
     */
    private List<StockOutcomeBillItem> stockOutcomeBillItems = new ArrayList<>();
}