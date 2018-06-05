package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    List<Permission> selectAll();

    Permission get(Long id);

    void saveOrUpdate(Permission permission);

    void delete(Long id);

    PageResult query(QueryObject qo);

    void load();

    Set<String> queryExpressionByEmployeeId(Long employeeId);
}
