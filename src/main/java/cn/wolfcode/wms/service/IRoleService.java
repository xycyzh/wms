package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IRoleService {
    List<Role> selectAll();

    Role get(Long id);

    void saveOrUpdate(Role role, Long[] permissionIds, Long[] menuIds);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
