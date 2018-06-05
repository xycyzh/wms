package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.OrderBillItem;

import java.util.List;

public interface OrderBillItemMapper {

    int insert(OrderBillItem record);

    OrderBillItem selectByOrderId(Long id);

    void deleteByOrderId(Long orderBillId);
}