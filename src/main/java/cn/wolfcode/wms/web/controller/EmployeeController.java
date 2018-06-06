package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.vo.JSONResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("cn.wolfcode.wms.web.controller.EmployeeController:list")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo) {
        model.addAttribute("pageResult", employeeService.query(qo));
        model.addAttribute("departments", departmentService.selectAll());
        return "employee/list";
    }

    @RequiredPermission("员工编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("employee", employeeService.get(id));
        }
        model.addAttribute("departments", departmentService.selectAll());
        model.addAttribute("roles", roleService.selectAll());
        return "employee/input";
    }

    @RequiredPermission("员工保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Employee employee, Long[] roleIds) {
        try {
            employeeService.saveOrUpdate(employee, roleIds);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("员工删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            employeeService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }

    @RequiredPermission("员工删除")
    @RequestMapping("/batchDelete")
    @ResponseBody
    public JSONResult batchDelete(@RequestParam("ids[]") Long[] ids) {
        try {
            employeeService.batchDelete(ids);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("批量删除成功");
    }
}
