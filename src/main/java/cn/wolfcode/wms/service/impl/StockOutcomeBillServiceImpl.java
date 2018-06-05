package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBillItem;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {
    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;

    @Override
    public List<StockOutcomeBill> selectAll() {
        return stockOutcomeBillMapper.selectAll();
    }

    @Override
    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(StockOutcomeBill stockOutcomeBill) {
        if (stockOutcomeBill.getId() != null) {
            update(stockOutcomeBill);
        } else {
            save(stockOutcomeBill);
        }
    }

    /**
     * 更新入库订单
     *
     * @param stockOutcomeBill 入库订单对象
     */
    private void update(StockOutcomeBill stockOutcomeBill) {
        //查询当前订单状态
        StockOutcomeBill outcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(stockOutcomeBill.getId());
        if (outcomeBill != null && outcomeBill.getStatus() == StockOutcomeBill.STATUS_NORMAL) {
            //先删除旧明细
            stockOutcomeBillItemMapper.deleteByStockOutcomeBillId(stockOutcomeBill.getId());
            //只有当前订单处于未审核状态,才可以审核订单
            //计算订单的总金额和总数目
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //计算订单明细的🐥
            for (StockOutcomeBillItem billItem : stockOutcomeBill.getStockOutcomeBillItems()) {
                //计算出明细小计
                BigDecimal amount = billItem.getSalePrice().multiply(billItem.getNumber());
                //给明细设置小计
                billItem.setAmount(amount);
                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(billItem.getNumber());
                //给明细设置订单id
                billItem.setBill_id(stockOutcomeBill.getId());
                //保存明细
                stockOutcomeBillItemMapper.insert(billItem);
            }
            //给当前订单设置总金额和总数目
            stockOutcomeBill.setTotalAmount(totalAmount);
            stockOutcomeBill.setTotalNumber(totalNumber);
            //更新当前订单
            stockOutcomeBillMapper.updateByPrimaryKey(stockOutcomeBill);
        } else {
            throw new LogicException("当前订单已审核");
        }
    }

    /**
     * 保存入库订单
     *
     * @param stockOutcomeBill 入库订单对象
     */
    private void save(StockOutcomeBill stockOutcomeBill) {
        //1.录入当前用户和时间
        Employee user = UserContext.getUser();
        stockOutcomeBill.setInputUser(user);
        stockOutcomeBill.setInputTime(new Date());
        //2.将当前订单状态设置为未审核
        stockOutcomeBill.setStatus(StockOutcomeBill.STATUS_NORMAL);
        //3.设置订单总金额和总数目
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //4.计算明细小计
        for (StockOutcomeBillItem billItem : stockOutcomeBill.getStockOutcomeBillItems()) {
            BigDecimal amount = billItem.getSalePrice().multiply(billItem.getNumber());
            //为当前明细设置小计
            billItem.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(billItem.getNumber());
        }
        //5.给当前订单设置总金额和总数目
        stockOutcomeBill.setTotalAmount(totalAmount);
        stockOutcomeBill.setTotalNumber(totalNumber);
        //6.保存当前订单
        stockOutcomeBillMapper.insert(stockOutcomeBill);
        //7.给订单明细设置订单id
        for (StockOutcomeBillItem billItem : stockOutcomeBill.getStockOutcomeBillItems()) {
            billItem.setBill_id(stockOutcomeBill.getId());
            //8.保存订单明细
            stockOutcomeBillItemMapper.insert(billItem);
        }
    }

    @Override
    public void delete(Long id) {
        //删除明细
        StockOutcomeBill outcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if (outcomeBill.getStatus() == StockOutcomeBill.STATUS_NORMAL) {
            stockOutcomeBillItemMapper.deleteByStockOutcomeBillId(id);
            stockOutcomeBillMapper.deleteByPrimaryKey(id);
        } else {
            throw new LogicException("该订单已审核");
        }
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = stockOutcomeBillMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<StockOutcomeBill> list = stockOutcomeBillMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    /**
     * 出库订单审核
     *
     * @param id 订单di
     */
    @Override
    public void audit(Long id) {
        //1.查询当前订单状态
        StockOutcomeBill outcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if (outcomeBill.getStatus() == StockOutcomeBill.STATUS_NORMAL) {
            //插入审核人和审核时间
            outcomeBill.setStatus(StockOutcomeBill.STATUS_AUDITED);
            outcomeBill.setAuditor(UserContext.getUser());
            outcomeBill.setAuditTime(new Date());
            //更新审核
            stockOutcomeBillMapper.audit(outcomeBill);
            //审核后更新库存
            productStockService.outcome(outcomeBill);
        } else {
            throw new LogicException("该订单已审核");
        }
    }
}
