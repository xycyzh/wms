package cn.wolfcode.wms.shiro;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.service.IPermissionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public String getName() {
        return "MyRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object principal = token.getPrincipal();
        Employee employee = employeeService.queryUsername((String) principal);
        if (employee == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(employee, employee.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Employee employee = (Employee) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (employee.isAdmin()) {
            //所有权限
            info.addStringPermission("*:*");
        } else {
            Set<String> expressions = permissionService.queryExpressionByEmployeeId(employee.getId());
            info.addStringPermissions(expressions);
        }
        return info;
    }
}
