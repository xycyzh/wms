package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

public interface IProductStockService {
    /**
     * 入库审核
     *
     * @param incomeBill 入库对象
     */
    void income(StockIncomeBill incomeBill);

    /**
     * 出库审核
     *
     * @param outcomeBill 出库对象
     */
    void outcome(StockOutcomeBill outcomeBill);

    /**
     * 高级查询+分页
     *
     * @param qo 查询对象
     * @return 返回查询结果
     */
    PageResult query(QueryObject qo);
}
