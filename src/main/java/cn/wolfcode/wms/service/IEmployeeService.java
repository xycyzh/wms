package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IEmployeeService {
    List<Employee> selectAll();

    Employee get(Long id);

    void saveOrUpdate(Employee employee, Long[] roleIds);

    void delete(Long id);

    PageResult query(QueryObject qo);

    void login(Employee employee);

    void batchDelete(Long[] ids);
}
