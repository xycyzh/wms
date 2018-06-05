package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import cn.wolfcode.wms.vo.SystemMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/systemMenu")
public class SystemMenuController {
    @Autowired
    private ISystemMenuService systemMenuService;

    @RequiredPermission("菜单列表")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") SystemMenuQueryObject qo) {
        model.addAttribute("pageResult", systemMenuService.query(qo));
        if (qo.getParentId() != null) {
            model.addAttribute("parentList", systemMenuService.queryParentList(qo.getParentId()));
        }
        return "systemMenu/list";
    }

    @RequiredPermission("菜单编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id, Long parentId) {
        if (parentId != null) {
            SystemMenu menu = systemMenuService.get(parentId);
            model.addAttribute("parentName", menu.getName());
            model.addAttribute("parentId", menu.getId());
        } else {
            model.addAttribute("parentName", "根目录");
        }
        if (id != null) {
            model.addAttribute("systemMenu", systemMenuService.get(id));
        }
        return "systemMenu/input";
    }

    @RequiredPermission("菜单保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(SystemMenu systemMenu) {
        try {
            systemMenuService.saveOrUpdate(systemMenu);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("菜单删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            systemMenuService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }

    @RequestMapping("loadMenuByParentSn")
    @ResponseBody
    public List<SystemMenuVO> loadMenuByParentSn(String parentSn) {
        return systemMenuService.loadMenuByParentSn(parentSn);
    }
}
