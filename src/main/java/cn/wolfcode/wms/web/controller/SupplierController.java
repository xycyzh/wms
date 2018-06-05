package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @RequiredPermission("供应商列表")
    @RequestMapping("/list")
    public String list(Model model, QueryObject qo) {
        model.addAttribute("pageResult", supplierService.query(qo));
        return "supplier/list";
    }

    @RequiredPermission("供应商编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("supplier", supplierService.get(id));
        }
        return "supplier/input";
    }

    @RequiredPermission("供应商保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Supplier supplier) {
        try {
            supplierService.saveOrUpdate(supplier);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("供应商删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            supplierService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }
}
