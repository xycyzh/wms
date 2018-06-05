package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee extends BaseDomain {

    private String name;

    private String password;

    private String email;

    private Integer age;

    private boolean admin;

    private Department dept;

    private List<Role> roles = new ArrayList<>();

    public String getRoleNames() {
        if (isAdmin()) {
            return "[超级管理员]";
        }
        List<Role> roles = this.getRoles();
        if (roles.size() == 0) {
            return "[暂未分配角色]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (Role role : roles) {
            sb.append(role.getName()).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        return sb.toString();
    }
}