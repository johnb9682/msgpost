package com.msgpost.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.msgpost.dao.sys.MessageDao;
import com.msgpost.model.sys.Message;

import core.dao.BaseDao;

/**
 * Message的数据持久层的实现类
 * @Author: Bi Ran
 */
@Repository
public class MessageDaoImpl extends BaseDao<Message> implements MessageDao {

	public MessageDaoImpl() {
		super(Message.class);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.msgpost.dao.sys.MessageDao#queryMessageWithAuthorName(int)
	 * SELECT msgpost.message.messageid, msgpost.message.message, msgpost.message.time, msgpost.user.username 
	 * FROM msgpost.message 
	 * left join msgpost.user 
	 * on msgpost.message.authorid = msgpost.user.userid 
	 * order by msgpost.message.time desc
	 * limit 1
	 */
	public List<Object[]> queryMessageWithAuthorName(Integer limit) {
		Query query = this.getSession().createSQLQuery(
				"SELECT msgpost.message.messageid, msgpost.message.message, msgpost.message.time, msgpost.user.username, msgpost.user.imgpath " +
				"FROM msgpost.message " + 
				"left join msgpost.user " + 
				"on msgpost.message.authorid = msgpost.user.userid " + 
				"order by msgpost.message.time desc " +
				"limit " + limit);
		List<Object[]> list = query.list();   
		return list;
	} 
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.msgpost.dao.sys.MessageDao#queryMessageWithTargetAuthorName(java.lang.String)
	 * SELECT msgpost.message.messageid, msgpost.message.message, msgpost.message.time, msgpost.user.username 
	 * FROM msgpost.message 
	 * left join msgpost.user 
	 * on msgpost.message.authorid = msgpost.user.userid 
	 * where msgpost.user.username = 'johnb9682'
	 * order by msgpost.message.time desc
	 * limit 2;
	 */
	public List<Object[]> queryMessageWithTargetAuthorName(String username, Integer limit) {
		Query query = this.getSession().createSQLQuery(
				"SELECT msgpost.message.messageid, msgpost.message.message, msgpost.message.time, msgpost.user.username "
				+ "FROM msgpost.message "
				+ "left join msgpost.user "
				+ "on msgpost.message.authorid = msgpost.user.userid "
				+ "where msgpost.user.username = " 
				+ username
				+ " order by msgpost.message.time desc "
				+ "limit " + limit);
		List<Object[]> list = query.list();   
		return list;
	} 

}
