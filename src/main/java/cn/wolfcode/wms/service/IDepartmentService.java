package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IDepartmentService {
    List<Department> selectAll();

    Department get(Long id);

    void saveOrUpdate(Department department);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
