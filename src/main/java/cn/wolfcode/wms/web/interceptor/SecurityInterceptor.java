package cn.wolfcode.wms.web.interceptor;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session中的用户和用户的权限
        Employee user = UserContext.getUser();
        Set<String> expressions = UserContext.getExpressions();
        //是否是超级管理员
        if (user.isAdmin()) {
            return true;
        }
        //判断方法是否需要权限访问
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        if (!method.isAnnotationPresent(RequiredPermission.class)) {
            return true;
        }
        //判断用户是否有权限来访问
        //拼接权限表达式
        String expression = method.getDeclaringClass().getName() + ":" + method.getName();
        if (expressions.contains(expression)) {
            return true;
        }
        throw new SecurityException("对不起,你没有权限访问此页面");
    }
}
