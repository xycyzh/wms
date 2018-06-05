package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

//菜单
@Setter
@Getter
public class SystemMenu extends BaseDomain {
    private String name;
    private String url;
    private String sn;
    private SystemMenu parent;
}
