package com.anshishagua.object;

import com.anshishagua.service.UserRoleService;
import com.anshishagua.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午5:06
 */

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getByUserName(username);
        UserRole userRole = userRoleService.getByUserId(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(userRole.getRole());

        return info;
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char []) authenticationToken.getCredentials());

        //从数据库中根据用户名查找用户
        User user = userService.getByUserName(username);

        if (user == null) {
            throw new UnknownAccountException(String.format("用户名%s不存在", username));
        }

        if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码错误");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());

        return info;
    }
}