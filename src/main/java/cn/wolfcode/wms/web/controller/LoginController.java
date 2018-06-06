package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.UserContext;
import cn.wolfcode.wms.vo.JSONResult;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    private IEmployeeService employeeService;

//    @RequestMapping("/login")
//    @ResponseBody
//    public JSONResult login(Employee employee) {
//        try {
//            employeeService.login(employee);
//        } catch (Exception e) {
//            return JSONResult.mark(e.getMessage());
//        }
//        return JSONResult.success("登录成功");
//    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Object loginFailure = request.getAttribute("shiroLoginFailure");
        String errorMsg = "";
        if (loginFailure != null) {
            if (loginFailure.equals(UnknownAccountException.class.getName())) {
                errorMsg = "用户不存在";
            } else if (loginFailure.equals(IncorrectCredentialsException.class.getName())) {
                errorMsg = "密码不正确";
            }
        }
        model.addAttribute("errorMsg", errorMsg);
        return "forward:/login.jsp";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

//    @RequestMapping("/logout")
//    public String logout() {
//        UserContext.setUser(null);
//        return "redirect:/login.html";
//    }
}
