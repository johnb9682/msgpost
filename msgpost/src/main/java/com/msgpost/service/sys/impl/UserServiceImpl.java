package com.msgpost.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.msgpost.dao.sys.UserDao;
import com.msgpost.model.sys.User;
import com.msgpost.service.sys.UserService;

import core.service.BaseService;

/**
 * User的业务逻辑层的实现
 * @Author: Bi Ran
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

	private UserDao UserDao;

	@Resource
	public void setUserDao(UserDao UserDao) {
		this.UserDao = UserDao;
		this.dao = UserDao;
	}

	public List<User> queryUserWithSubList(List<User> resultList) {
		List<User> userList = new ArrayList<User>();
		for (User entity : resultList) {
			User user = new User();
			user.setUserid(entity.getUserid());
			user.setUsername(entity.getUsername()); 
			user.setPassword(entity.getPassword()); 
			user.setImgpath(entity.getImgpath());
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Object[] querySingleUserInfo(Integer userid) {
		return UserDao.querySingleUserInfo(userid);
	}

}
