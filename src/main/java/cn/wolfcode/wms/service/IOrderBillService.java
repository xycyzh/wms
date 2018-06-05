package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IOrderBillService {
    List<OrderBill> selectAll();

    OrderBill get(Long id);

    void saveOrUpdate(OrderBill orderBill);

    void delete(Long id);

    PageResult query(QueryObject qo);

    void audit(Long id);
}
