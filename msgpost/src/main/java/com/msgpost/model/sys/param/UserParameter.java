package com.msgpost.model.sys.param; 

import core.support.ExtJSBaseParameter;

/**
 * User的参数类
 * @Author: Bi Ran
 */
public class UserParameter extends ExtJSBaseParameter {

	private String $eq_username; 
	private boolean rememberMe; // 下次自动登录 

	public String get$eq_username() {
		return $eq_username;
	}

	public void set$eq_username(String $eq_username) {
		this.$eq_username = $eq_username;
	} 
	
	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}

