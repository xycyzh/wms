package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.SaleAccount;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.service.ISaleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleAccountServiceImpl implements ISaleAccountService {
    @Autowired
    private SaleAccountMapper saleAccountMapper;

    @Override
    public void save(SaleAccount record) {
        saleAccountMapper.insert(record);
    }
}
