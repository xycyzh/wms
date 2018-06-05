package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockIncomeBillItem;

import java.util.List;

public interface StockIncomeBillItemMapper {

    int insert(StockIncomeBillItem record);

    List<StockIncomeBillItem> selectByStockIncomeBillId(Long stockIncomeBillId);
    /*根据订单id来删除明细*/
    void deleteByStockIncomeBillId(Long stockIncomeBillId);
}