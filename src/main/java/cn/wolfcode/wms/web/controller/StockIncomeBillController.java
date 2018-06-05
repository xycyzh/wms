package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.StockIncomeBillQueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stockIncomeBill")
public class StockIncomeBillController {
    @Autowired
    private IStockIncomeBillService stockIncomeBillService;
    @Autowired
    private IDepotService depotService;

    @RequiredPermission("入库订单列表")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StockIncomeBillQueryObject qo) {
        model.addAttribute("pageResult", stockIncomeBillService.query(qo));
        model.addAttribute("depots", depotService.selectAll());
        return "stockIncomeBill/list";
    }

    @RequiredPermission("入库订单编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("stockIncomeBill", stockIncomeBillService.get(id));
        }
        model.addAttribute("depots", depotService.selectAll());
        return "stockIncomeBill/input";
    }

    @RequiredPermission("入库订单查看")
    @RequestMapping("/view")
    public String view(Model model, Long id) {
        if (id != null) {
            model.addAttribute("stockIncomeBill", stockIncomeBillService.get(id));
        }
        model.addAttribute("depots", depotService.selectAll());
        return "stockIncomeBill/view";
    }

    @RequiredPermission("入库订单保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(StockIncomeBill stockIncomeBill) {
        try {
            stockIncomeBillService.saveOrUpdate(stockIncomeBill);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("入库订单删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            stockIncomeBillService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }

    @RequiredPermission("入库订单审核")
    @RequestMapping("/audit")
    @ResponseBody
    public JSONResult audit(Long id) {
        try {
            stockIncomeBillService.audit(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("审核成功");
    }
}
