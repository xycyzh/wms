package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @RequiredPermission("部门列表")
    @RequestMapping("/list")
    public String list(Model model, QueryObject qo) {
        model.addAttribute("pageResult", departmentService.query(qo));
        return "department/list";
    }

    @RequiredPermission("部门编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("department", departmentService.get(id));
        }
        return "department/input";
    }

    @RequiredPermission("部门保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Department department) {
        try {
            departmentService.saveOrUpdate(department);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("部门删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            departmentService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }
}
