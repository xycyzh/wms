package cn.wolfcode.wms.util;

import cn.wolfcode.wms.domain.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Set;

public class UserContext {
    private static final String USER_IN_SESSION = "user_in_session";
    private static final String EXPRESSION_IN_SESSION = "expression_in_session";

    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public static void setUser(Employee employee) {
        if (employee == null) {
            getSession().invalidate();
        } else {
            getSession().setAttribute(USER_IN_SESSION, employee);
        }
    }

    public static Employee getUser() {
        return (Employee) getSession().getAttribute(USER_IN_SESSION);
    }

    public static void setExpressions(Set<String> expressions) {
        getSession().setAttribute(EXPRESSION_IN_SESSION, expressions);
    }

    public static Set<String> getExpressions() {
        return (Set<String>) getSession().getAttribute(EXPRESSION_IN_SESSION);
    }
}
