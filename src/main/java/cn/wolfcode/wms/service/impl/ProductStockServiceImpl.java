package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.ISaleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class ProductStockServiceImpl implements IProductStockService {
    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private ISaleAccountService saleAccountService;

    @Override
    public void income(StockIncomeBill incomeBill) {
        for (StockIncomeBillItem billItem : incomeBill.getStockIncomeBillItems()) {
            ProductStock productStock = productStockMapper.
                    selectByProductIdAndDepotId(billItem.getProduct().getId(), incomeBill.getDepot().getId());
            if (productStock == null) {
                ProductStock stock = new ProductStock();
                BigDecimal costPrice = billItem.getCostPrice();
                stock.setPrice(costPrice);
                BigDecimal number = billItem.getNumber();
                stock.setStoreNumber(number);
                stock.setAmount(costPrice.multiply(number).setScale(2, BigDecimal.ROUND_HALF_UP));
                stock.setProduct(billItem.getProduct());
                stock.setDepot(incomeBill.getDepot());
                //插入库存
                productStockMapper.insert(stock);
            } else {
                //更新库存数据
                productStock.setStoreNumber(productStock.getStoreNumber().add(billItem.getNumber()));
                //更新总额
                productStock.setAmount(productStock.getAmount().add(billItem.getAmount()));
                //更新价格
                productStock.setPrice(productStock.getAmount().divide(productStock.getStoreNumber(), 2, BigDecimal.ROUND_HALF_UP));
                productStockMapper.updateByPrimaryKey(productStock);
            }
        }
    }

    @Override
    public void outcome(StockOutcomeBill outcomeBill) {
        for (StockOutcomeBillItem billItem : outcomeBill.getStockOutcomeBillItems()) {
            ProductStock productStock = productStockMapper.selectByProductIdAndDepotId(billItem.getProduct().getId(), outcomeBill.getDepot().getId());
            if (productStock == null) {
                //货品不足
                throw new LogicException(billItem.getProduct().getName() + "在" + outcomeBill.getDepot().getName() + "中没有商品");
            }
            //有货品,但是仓库的货品数量不足,也就是数量小于出库的数量
            if (productStock.getStoreNumber().compareTo(billItem.getNumber()) < 0) {
                throw new LogicException(outcomeBill.getDepot().getName() + "中商品" + billItem.getProduct().getName() + "货源不足");
            }
            //有货品,而且货品数量充足
            //减少库存数量
            productStock.setStoreNumber(productStock.getStoreNumber().subtract(billItem.getNumber()));
            //减少库存的总额
            productStock.setAmount(productStock.getStoreNumber().multiply(billItem.getSalePrice()));
            productStockMapper.updateByPrimaryKey(productStock);
            //记录销售流水账
            SaleAccount saleAccount = new SaleAccount();
            saleAccount.setClient_id(outcomeBill.getClient().getId());
            //设置成本价
            saleAccount.setCostPrice(productStock.getPrice());
            saleAccount.setNumber(billItem.getNumber());
            saleAccount.setCostAmount(saleAccount.getCostPrice().multiply(saleAccount.getNumber()).setScale(2, RoundingMode.HALF_UP));
            saleAccount.setSalePrice(billItem.getSalePrice());
            saleAccount.setSaleAmount(billItem.getAmount());
            saleAccount.setProduct_id(billItem.getProduct().getId());
            saleAccount.setVdate(outcomeBill.getVdate());
            saleAccount.setSaleman_id(outcomeBill.getInputUser().getId());
            saleAccountService.save(saleAccount);
        }
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = productStockMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<ProductStock> list = productStockMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }
}
