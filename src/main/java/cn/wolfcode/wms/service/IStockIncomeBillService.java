package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IStockIncomeBillService {
    List<StockIncomeBill> selectAll();

    StockIncomeBill get(Long id);

    void saveOrUpdate(StockIncomeBill stockIncomeBill);

    void delete(Long id);

    PageResult query(QueryObject qo);

    void audit(Long id);
}
