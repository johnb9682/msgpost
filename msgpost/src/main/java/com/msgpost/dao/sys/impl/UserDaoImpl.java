package com.msgpost.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.msgpost.dao.sys.UserDao;
import com.msgpost.model.sys.User;

import core.dao.BaseDao;

/**
 * User的数据持久层的实现类
 * @Author: Bi Ran
 */
@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	/*
	 * SELECT * FROM msgpost.user
	 * WHERE msgpost.user.userid = 1;
	 * @see com.msgpost.dao.sys.UserDao#querySingleUserInfo(java.lang.Integer)
	 */
	public Object[] querySingleUserInfo(Integer userid) {
		Query query = this.getSession().createSQLQuery("SELECT * FROM msgpost.user WHERE msgpost.user.userid = " + userid);
		List<Object[]> list = query.list();
		return list.get(0);    
	}

}
