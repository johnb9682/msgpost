package com.msgpost.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.msgpost.model.sys.User;

import core.controller.ExtJSBaseController;
import core.support.ExtJSBaseParameter;

/**
 * @Author: Bi Ran
 */
public abstract class JavaEEFrameworkBaseController<E extends ExtJSBaseParameter> extends ExtJSBaseController<E> implements Constant {

	public User getCurrentSysUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (User) request.getSession().getAttribute(SESSION_SYS_USER);
	}

}
