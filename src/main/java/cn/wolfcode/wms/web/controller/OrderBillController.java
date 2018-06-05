package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orderBill")
public class OrderBillController {
    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private ISupplierService supplierService;

    @RequiredPermission("采购订单列表")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") OrderBillQueryObject qo) {
        model.addAttribute("pageResult", orderBillService.query(qo));
        model.addAttribute("suppliers", supplierService.selectAll());
        return "orderBill/list";
    }

    @RequiredPermission("采购订单编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("orderBill", orderBillService.get(id));
        }
        model.addAttribute("suppliers", supplierService.selectAll());
        return "orderBill/input";
    }

    @RequiredPermission("采购订单查看")
    @RequestMapping("/view")
    public String view(Model model, Long id) {
        if (id != null) {
            model.addAttribute("orderBill", orderBillService.get(id));
        }
        model.addAttribute("suppliers", supplierService.selectAll());
        return "orderBill/view";
    }

    @RequiredPermission("采购订单保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(OrderBill orderBill) {
        try {
            orderBillService.saveOrUpdate(orderBill);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("采购订单删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            orderBillService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }

    @RequiredPermission("采购订单审核")
    @RequestMapping("/audit")
    @ResponseBody
    public JSONResult audit(Long id) {
        try {
            orderBillService.audit(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("审核成功");
    }
}
