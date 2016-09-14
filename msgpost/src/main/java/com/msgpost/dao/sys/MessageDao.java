package com.msgpost.dao.sys;

import java.util.List;

import com.msgpost.model.sys.Message;

import core.dao.Dao;

/**
 * Message的数据持久层的接口
 * @Author: Bi Ran
 */
public interface MessageDao extends Dao<Message> { 
	List<Object[]> queryMessageWithAuthorName(Integer limit); 
	List<Object[]> queryMessageWithTargetAuthorName(String username, Integer limit);
}
