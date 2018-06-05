package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.RoleMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Role role, Long[] permissionIds, Long[] menuIds) {
        if (role.getId() != null) {
            //删除角色和权限的旧关系
            roleMapper.deleteRolePermissionRelation(role.getId());
            //删除角色和菜单的旧关系
            roleMapper.deleteRoleSystemMenuRelation(role.getId());
            roleMapper.updateByPrimaryKey(role);
        } else {
            roleMapper.insert(role);
        }
        //插入新关系
        if (permissionIds != null) {
            for (Long id : permissionIds) {
                roleMapper.insertRolePermissionRelation(role.getId(), id);
            }
        }
        if (menuIds != null) {
            for (Long menuId : menuIds) {
                roleMapper.insertRoleSystemMenuRelation(role.getId(), menuId);
            }
        }
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteRolePermissionRelation(id);
        roleMapper.deleteRoleSystemMenuRelation(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = roleMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<Role> list = roleMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }
}
