package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class StockOutcomeBillQueryObject extends BaseAuditQueryObject {
    /**
     * 仓库
     */
    private Long depotId = -1L;
    /**
     * 客户
     */
    private Long clientId = -1L;
}
