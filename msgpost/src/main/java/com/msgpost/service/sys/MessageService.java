package com.msgpost.service.sys;

import java.util.List;

import com.msgpost.model.sys.Message;

import core.service.Service;
import net.sf.json.JSONArray;

/**
 * Message的业务逻辑层的接口
 * @Author: Bi Ran
 */
public interface MessageService extends Service<Message> {

	List<Message> queryMessageWithSubList(List<Message> resultList); 
	JSONArray queryMessageWithAuthorName(Integer limit); 
}
