package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Depot extends BaseDomain {
    private String name;

    private String location;
}