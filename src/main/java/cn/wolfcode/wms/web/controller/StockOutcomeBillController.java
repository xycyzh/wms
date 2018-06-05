package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stockOutcomeBill")
public class StockOutcomeBillController {
    @Autowired
    private IStockOutcomeBillService stockOutcomeBillService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private IClientService clientService;

    @RequiredPermission("出库订单列表")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StockOutcomeBillQueryObject qo) {
        model.addAttribute("pageResult", stockOutcomeBillService.query(qo));
        model.addAttribute("depots", depotService.selectAll());
        model.addAttribute("clients", clientService.selectAll());
        return "stockOutcomeBill/list";
    }

    @RequiredPermission("出库订单编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("stockOutcomeBill", stockOutcomeBillService.get(id));
        }
        model.addAttribute("depots", depotService.selectAll());
        model.addAttribute("clients", clientService.selectAll());
        return "stockOutcomeBill/input";
    }

    @RequiredPermission("出库订单查看")
    @RequestMapping("/view")
    public String view(Model model, Long id) {
        if (id != null) {
            model.addAttribute("stockOutcomeBill", stockOutcomeBillService.get(id));
        }
        model.addAttribute("depots", depotService.selectAll());
        model.addAttribute("clients", clientService.selectAll());
        return "stockOutcomeBill/view";
    }

    @RequiredPermission("出库订单保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(StockOutcomeBill StockOutcomeBill) {
        try {
            stockOutcomeBillService.saveOrUpdate(StockOutcomeBill);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("出库订单删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            stockOutcomeBillService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }

    @RequiredPermission("出库订单审核")
    @RequestMapping("/audit")
    @ResponseBody
    public JSONResult audit(Long id) {
        try {
            stockOutcomeBillService.audit(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("审核成功");
    }
}
