package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ApplicationContext ctx;

    @Override
    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Permission permission) {
        if (permission.getId() != null) {
            permissionMapper.updateByPrimaryKey(permission);
        } else {
            permissionMapper.insert(permission);
        }
    }

    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = permissionMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<Permission> list = permissionMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void load() {
        //查询出所有的权限
        List<Permission> permissionList = permissionMapper.selectAll();
        Set<String> permissions = new HashSet<>();
        for (Permission permission : permissionList) {
            permissions.add(permission.getExpression());
        }
        //获取所有的controller
        Collection<Object> values = ctx.getBeansWithAnnotation(Controller.class).values();
        for (Object value : values) {
            //获取每个controller中的方法
            Method[] methods = value.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequiredPermission.class)) {
                    //获取权限表达式
                    String expression = value.getClass().getName() + ":" + method.getName();
                    if (!permissions.contains(expression)) {
                        String name = method.getAnnotation(RequiredPermission.class).value();
                        Permission permission = new Permission();
                        permission.setExpression(expression);
                        permission.setName(name);
                        permissionMapper.insert(permission);
                    }
                }
            }
        }
    }

    @Override
    public Set<String> queryExpressionByEmployeeId(Long employeeId) {
        return permissionMapper.queryExpressionByEmployeeId(employeeId);
    }
}
