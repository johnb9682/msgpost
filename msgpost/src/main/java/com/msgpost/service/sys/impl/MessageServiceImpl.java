package com.msgpost.service.sys.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.stereotype.Service;

import com.msgpost.dao.sys.MessageDao;
import com.msgpost.model.sys.Message;
import com.msgpost.service.sys.MessageService;

import core.service.BaseService;
import core.support.DateTimeSerializer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Message的业务逻辑层的实现
 * @Author: Bi Ran
 */
@Service
public class MessageServiceImpl extends BaseService<Message> implements MessageService {

	private MessageDao messageDao; 

	@Resource
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
		this.dao = messageDao;
	}

	public List<Message> queryMessageWithSubList(List<Message> resultList) {
		List<Message> MessageList = new ArrayList<Message>();
		for (Message entity : resultList) {
			Message Message = new Message();
			Message.setMessageid(entity.getMessageid());
			Message.setMessage(entity.getMessage()); 
			Message.setAuthorid(entity.getAuthorid()); 
			MessageList.add(Message);
		}
		return MessageList;
	}

	@Override
	public JSONArray queryMessageWithAuthorName(Integer limit) {
		 
		List<Object[]> list = messageDao.queryMessageWithAuthorName(limit); 
		JSONArray jsonArray = new JSONArray();   
		for(ListIterator<Object[]> iter = list.listIterator(); iter.hasNext();){ 
			JSONObject obj = new JSONObject();  
			Object[] element = iter.next();    
			
			obj.put("messageid", element[0]);  
			obj.put("message", element[1]);    
			obj.put("time", DateTimeSerializer.DateToStringSerialize((Date) element[2])); 
			obj.put("author", element[3]);   
			obj.put("imgpath", element[4]);   
		    jsonArray.add(obj);    
		}   
		return jsonArray;
	} 

}
