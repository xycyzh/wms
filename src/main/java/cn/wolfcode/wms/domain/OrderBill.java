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
public class OrderBill extends BaseAuditDomain {
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
     * 供应商
     */
    private Supplier supplier;
    /**
     * 订单明细:一对多关联关系
     */
    private List<OrderBillItem> orderBillItems = new ArrayList<>();
}