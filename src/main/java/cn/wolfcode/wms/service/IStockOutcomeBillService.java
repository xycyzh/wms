package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IStockOutcomeBillService {
    List<StockOutcomeBill> selectAll();

    StockOutcomeBill get(Long id);

    void saveOrUpdate(StockOutcomeBill stockOutcomeBill);

    void delete(Long id);

    PageResult query(QueryObject qo);

    void audit(Long id);
}
