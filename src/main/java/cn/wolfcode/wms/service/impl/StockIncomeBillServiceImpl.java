package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.domain.StockIncomeBillItem;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService {
    @Autowired
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Autowired
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;

    @Override
    public List<StockIncomeBill> selectAll() {
        return stockIncomeBillMapper.selectAll();
    }

    @Override
    public StockIncomeBill get(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(StockIncomeBill stockIncomeBill) {
        if (stockIncomeBill.getId() != null) {
            update(stockIncomeBill);
        } else {
            save(stockIncomeBill);
        }
    }

    /**
     * 更新入库订单
     *
     * @param stockIncomeBill 入库订单对象
     */
    private void update(StockIncomeBill stockIncomeBill) {
        //查询当前订单状态
        StockIncomeBill incomeBill = stockIncomeBillMapper.selectByPrimaryKey(stockIncomeBill.getId());
        if (incomeBill != null && incomeBill.getStatus() == StockIncomeBill.STATUS_NORMAL) {
            //先删除旧明细
            stockIncomeBillItemMapper.deleteByStockIncomeBillId(stockIncomeBill.getId());
            //只有当前订单处于未审核状态,才可以审核订单
            //计算订单的总金额和总数目
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //计算订单明细的🐥
            for (StockIncomeBillItem billItem : stockIncomeBill.getStockIncomeBillItems()) {
                //计算出明细小计
                BigDecimal amount = billItem.getCostPrice().multiply(billItem.getNumber());
                //给明细设置小计
                billItem.setAmount(amount);
                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(billItem.getNumber());
                //给明细设置订单id
                billItem.setBill_id(stockIncomeBill.getId());
                //保存明细
                stockIncomeBillItemMapper.insert(billItem);
            }
            //给当前订单设置总金额和总数目
            stockIncomeBill.setTotalAmount(totalAmount);
            stockIncomeBill.setTotalNumber(totalNumber);
            //更新当前订单
            stockIncomeBillMapper.updateByPrimaryKey(stockIncomeBill);
        } else {
            throw new LogicException("当前订单已审核");
        }
    }

    /**
     * 保存入库订单
     *
     * @param stockIncomeBill 入库订单对象
     */
    private void save(StockIncomeBill stockIncomeBill) {
        //1.录入当前用户和事件
        Employee user = UserContext.getUser();
        stockIncomeBill.setInputUser(user);
        stockIncomeBill.setInputTime(new Date());
        //2.将当前订单状态设置为未审核
        stockIncomeBill.setStatus(StockIncomeBill.STATUS_NORMAL);
        //3.设置订单总金额和总数目
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //4.计算明细小计
        for (StockIncomeBillItem billItem : stockIncomeBill.getStockIncomeBillItems()) {
            BigDecimal amount = billItem.getCostPrice().multiply(billItem.getNumber());
            //为当前明细设置小计
            billItem.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(billItem.getNumber());
        }
        //5.给当前订单设置总金额和总数目
        stockIncomeBill.setTotalAmount(totalAmount);
        stockIncomeBill.setTotalNumber(totalNumber);
        //6.保存当前订单
        stockIncomeBillMapper.insert(stockIncomeBill);
        //7.给订单明细设置订单id
        for (StockIncomeBillItem billItem : stockIncomeBill.getStockIncomeBillItems()) {
            billItem.setBill_id(stockIncomeBill.getId());
            //8.保存订单明细
            stockIncomeBillItemMapper.insert(billItem);
        }
    }

    @Override
    public void delete(Long id) {
        //删除明细
        StockIncomeBill incomeBill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if (incomeBill.getStatus() == StockIncomeBill.STATUS_NORMAL) {
            stockIncomeBillItemMapper.deleteByStockIncomeBillId(id);
            stockIncomeBillMapper.deleteByPrimaryKey(id);
        } else {
            throw new LogicException("该订单已审核");
        }
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = stockIncomeBillMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<StockIncomeBill> list = stockIncomeBillMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    /**
     * 入库订单审核
     *
     * @param id 订单id
     */
    @Override
    public void audit(Long id) {
        //1.查询当前订单状态
        StockIncomeBill incomeBill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if (incomeBill.getStatus() == StockIncomeBill.STATUS_NORMAL) {
            //插入审核人和审核时间
            incomeBill.setStatus(StockIncomeBill.STATUS_AUDITED);
            incomeBill.setAuditor(UserContext.getUser());
            incomeBill.setAuditTime(new Date());
            //更新审核
            stockIncomeBillMapper.audit(incomeBill);
            //审核后更新库存
            //1.根据商品的id和仓库的id来定位查询在一个仓库是否当前商品有库存,如果有库存的话就更新,如果没有库存的话,就新增
            productStockService.income(incomeBill);
        } else {
            throw new LogicException("该订单已审核");
        }
    }
}
