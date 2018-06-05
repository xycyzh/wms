package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
public class OrderBillQueryObject extends BaseAuditQueryObject {
    /**
     * 供应商
     */
    private Long supplierId = -1L;
}
