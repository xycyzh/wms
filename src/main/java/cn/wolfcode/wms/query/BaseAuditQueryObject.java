package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class BaseAuditQueryObject extends QueryObject {
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
    /**
     * 审核状态
     */
    private Integer status = -1;
    /**
     * 重新设置结束时间
     *
     * @param endDate 结束时间
     */
//    public void setEndDate(Date endDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(endDate);
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        this.endDate = calendar.getTime();
//    }
}
