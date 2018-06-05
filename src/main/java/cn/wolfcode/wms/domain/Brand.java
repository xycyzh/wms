package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Brand extends BaseDomain {
    private String name;

    private String sn;
}