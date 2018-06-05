package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.mapper.OrderBillItemMapper;
import cn.wolfcode.wms.mapper.OrderBillMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService {
    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderBillItemMapper orderBillItemMapper;

    @Override
    public List<OrderBill> selectAll() {
        return orderBillMapper.selectAll();
    }

    @Override
    public OrderBill get(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(OrderBill orderBill) {
        if (orderBill.getId() != null) {
            update(orderBill);
        } else {
            save(orderBill);
        }
    }

    /**
     * 更新采购订单
     *
     * @param orderBill 订单对象
     */
    private void update(OrderBill orderBill) {
        //删除旧的明细
        orderBillItemMapper.deleteByOrderId(orderBill.getId());
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //计算明细的小计
        for (OrderBillItem orderBillItem : orderBill.getOrderBillItems()) {
            BigDecimal amount = orderBillItem.getNumber().multiply(orderBillItem.getCostPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderBillItem.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(orderBillItem.getNumber());
            //为明细设置订单id
            orderBillItem.setBill_id(orderBill.getId());
            //保存明细
            orderBillItemMapper.insert(orderBillItem);
        }
        //设置订单的总金额和总数目
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);
        orderBillMapper.updateByPrimaryKey(orderBill);
    }

    private void save(OrderBill orderBill) {
        //保存业务逻辑
        //1.获取当前用户
        Employee user = UserContext.getUser();
        orderBill.setInputUser(user);
        orderBill.setInputTime(new Date());
        //2.将当前订单状态设置为未审核
        orderBill.setStatus(OrderBill.STATUS_NORMAL);
        //3遍历订单明细
        //总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        //总数目
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (OrderBillItem orderBillItem : orderBill.getOrderBillItems()) {
            //计算明细的小计
            BigDecimal amount = orderBillItem.getNumber().multiply(orderBillItem.getCostPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderBillItem.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(orderBillItem.getNumber());
        }
        //4.为订单对象设置总金额和总数目
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);
        //5.保存订单
        orderBillMapper.insert(orderBill);
        //6.为订单明细设置订单id
        for (OrderBillItem orderBillItem : orderBill.getOrderBillItems()) {
            orderBillItem.setBill_id(orderBill.getId());
            //7.保存明细
            orderBillItemMapper.insert(orderBillItem);
        }
    }

    @Override
    public void delete(Long id) {
        //删除明细
        orderBillItemMapper.deleteByOrderId(id);
        orderBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = orderBillMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<OrderBill> list = orderBillMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void audit(Long id) {
        //订单审核
        Employee user = UserContext.getUser();
        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);
        if (orderBill != null && OrderBill.STATUS_NORMAL == orderBill.getStatus()) {
            //只有订单处于非审核状态才可以审核
            orderBill.setAuditor(user);
            orderBill.setAuditTime(new Date());
            orderBill.setStatus(OrderBill.STATUS_AUDITED);
            orderBillMapper.audit(orderBill);
        }
    }
}
