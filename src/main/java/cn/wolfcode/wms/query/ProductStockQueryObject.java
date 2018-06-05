package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductStockQueryObject extends QueryObject {
    //关键字:货品名称或编号
    private String keyword;
    private Long depotId = -1L;
    private Long brandId = -1L;
    //库存阈值
    private BigDecimal limitNumber;

    public void setKeyword(String keyword) {
        this.keyword = str2null(keyword);
    }
}
