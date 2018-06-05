package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IBrandService {
    List<Brand> selectAll();

    Brand get(Long id);

    void saveOrUpdate(Brand brand);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
