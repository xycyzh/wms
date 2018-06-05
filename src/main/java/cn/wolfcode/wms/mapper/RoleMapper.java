package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    int queryForCount(QueryObject qo);

    List<Role> queryForList(QueryObject qo);

    void deleteRolePermissionRelation(Long roleId);

    void insertRolePermissionRelation(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    List<Role> queryRoleByEmployeeId(Long employeeId);

    void deleteRoleSystemMenuRelation(Long roleId);

    void insertRoleSystemMenuRelation(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
}