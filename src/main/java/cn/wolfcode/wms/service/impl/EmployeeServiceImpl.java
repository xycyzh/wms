package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.MD5;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Employee employee, Long[] roleIds) {
        if (employee.getId() != null) {
            employeeMapper.deleteEmployeeRoleRelation(employee.getId());
            employeeMapper.updateByPrimaryKey(employee);
        } else {
            employee.setPassword(MD5.encode(employee.getPassword()));
            employeeMapper.insert(employee);
        }
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                employeeMapper.insertEmployeeRoleRelation(employee.getId(), roleId);
            }
        }
    }

    @Override
    public void delete(Long id) {
        employeeMapper.deleteEmployeeRoleRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = employeeMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<Employee> list = employeeMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void login(Employee employee) {
        //查询数据库
        Employee user = employeeMapper.queryUsername(employee.getName());
        if (user == null) {
            throw new LogicException("用户名或者密码错误");
        }
        String encode = MD5.encode(employee.getPassword());
        if (!user.getPassword().equals(encode)) {
            throw new LogicException("用户名或者密码错误");
        }
        //说明用户登录成功
        //将用户信息保存到session中
        UserContext.setUser(user);
        //获取到用户的所有的权限
        Set<String> expressions = permissionService.queryExpressionByEmployeeId(user.getId());
        UserContext.setExpressions(expressions);
    }

    @Override
    public void batchDelete(Long[] ids) {
        employeeMapper.batchDeleteEmployeeRoleRelation(ids);
        employeeMapper.batchDelete(ids);
    }
}
