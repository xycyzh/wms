package cn.wolfcode.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Product extends BaseDomain {

    private String name;

    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Long brand_id;

    private String brandName;

    //获取小图
    public String getSmallImagePath() {
        String imagePath = this.getImagePath();
        if (!StringUtils.isEmpty(imagePath)) {
            String prefix = imagePath.substring(0, imagePath.lastIndexOf("."));
            String suffix = imagePath.substring(imagePath.lastIndexOf("."));
            return prefix + "_small" + suffix;
        }
        return null;
    }

    /**
     * 将数据转成json字符串
     *
     * @return 返回json字符串
     */
    public String getProductJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("name", name);
        map.put("brandName", brandName);
        map.put("costPrice", costPrice);
        map.put("salePrice", salePrice);
        return JSON.toJSONString(map);
    }
}