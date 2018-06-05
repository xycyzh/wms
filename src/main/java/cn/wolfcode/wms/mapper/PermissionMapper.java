package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    int queryForCount(QueryObject qo);

    List<Permission> queryForList(QueryObject qo);

    List<Permission> queryPermissionByRoleId(Long roleId);

    Set<String> queryExpressionByEmployeeId(Long employeeId);
}