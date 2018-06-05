package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Role extends BaseDomain {

    private String name;

    private String sn;

    private List<Permission> permissions = new ArrayList<>();

    private List<SystemMenu> menus = new ArrayList<>();
}