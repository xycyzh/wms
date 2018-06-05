package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBillItem;

import java.util.List;

public interface StockOutcomeBillItemMapper {

    int insert(StockOutcomeBillItem record);

    List<StockOutcomeBillItem> selectByStockOutcomeBillId(Long stockOutcomeBillId);

    /*根据订单id来删除明细*/
    void deleteByStockOutcomeBillId(Long stockOutcomeBillId);
}