package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.UserContext;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/login")
    @ResponseBody
    public JSONResult login(Employee employee) {
        try {
            employeeService.login(employee);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("登录成功");
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/logout")
    public String logout() {
        UserContext.setUser(null);
        return "redirect:/login.html";
    }
}
