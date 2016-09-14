package com.msgpost.security;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.msgpost.dao.sys.UserDao; 
import com.msgpost.model.sys.User;

@Component
public class ShiroSecurityRealm extends AuthorizingRealm {

	@Resource
	private UserDao userDao;

	public ShiroSecurityRealm() {
		setName("ShiroSecurityRealm"); // This name must match the name in the SysUser class's getPrincipals() method
		setCredentialsMatcher(new Sha256CredentialsMatcher());
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("AuthenticationInfo");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userDao.getByProerties("username", token.getUsername()); 
		if (user != null) { 
			return new SimpleAuthenticationInfo(user.getUserid(), user.getPassword(), getName());
		} else { 
			return null;
		} 
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("AuthorizationInfo"); 
		return null;
	}

}
