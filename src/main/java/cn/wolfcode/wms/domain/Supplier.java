package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Supplier extends BaseDomain {

    private String name;

    private String phone;

    private String address;
}