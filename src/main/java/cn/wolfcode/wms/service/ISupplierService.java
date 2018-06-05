package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface ISupplierService {
    List<Supplier> selectAll();

    Supplier get(Long id);

    void saveOrUpdate(Supplier supplier);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
