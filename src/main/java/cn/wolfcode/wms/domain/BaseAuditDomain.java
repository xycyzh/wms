package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/*审核相关的基类*/
@Setter
@Getter
public class BaseAuditDomain extends BaseDomain {
    /**
     * 未审核
     */
    public static final int STATUS_NORMAL = 0;
    /**
     * 已审核
     */
    public static final int STATUS_AUDITED = 1;
    /**
     * 审核状态
     */
    private Integer status = STATUS_NORMAL;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 录入时间
     */
    private Date inputTime;
    /**
     * 录入人
     */
    private Employee inputUser;
    /**
     * 审核人
     */
    private Employee auditor;
    /**
     * 测试
     */
    private Employee employee;
}
