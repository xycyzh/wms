package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.SaleAccount;

import java.util.List;

public interface SaleAccountMapper {
    //销售流水只需要插入流水即可
    int insert(SaleAccount record);
}