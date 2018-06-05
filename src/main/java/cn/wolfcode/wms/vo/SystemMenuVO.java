package cn.wolfcode.wms.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemMenuVO {
    private Long id;
    private Long pId;
    private String name;
    private String action;
}
