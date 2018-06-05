package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class StockIncomeBillQueryObject extends BaseAuditQueryObject {
    /**
     * 仓库
     */
    private Long depotId = -1L;
}
