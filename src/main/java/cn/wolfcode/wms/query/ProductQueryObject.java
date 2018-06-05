package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductQueryObject extends QueryObject {
    //关键字
    private String keyword;
    //商品品牌
    private Long brandId = -1L;
}
