package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/depot")
public class DepotController {
    @Autowired
    private IDepotService depotService;

    @RequiredPermission("仓库列表")
    @RequestMapping("/list")
    public String list(Model model, QueryObject qo) {
        model.addAttribute("pageResult", depotService.query(qo));
        return "depot/list";
    }

    @RequiredPermission("仓库编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("depot", depotService.get(id));
        }
        return "depot/input";
    }

    @RequiredPermission("仓库保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Depot depot) {
        try {
            depotService.saveOrUpdate(depot);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("仓库删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            depotService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }
}
