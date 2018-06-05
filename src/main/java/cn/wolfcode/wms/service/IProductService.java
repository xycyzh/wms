package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IProductService {
    List<Product> selectAll();

    Product get(Long id);

    void saveOrUpdate(Product product);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
