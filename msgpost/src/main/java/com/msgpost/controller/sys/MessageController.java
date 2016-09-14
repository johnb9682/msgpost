package com.msgpost.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap; 
import java.util.List;
import java.util.ListIterator;
import java.util.Map; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
import com.msgpost.core.Constant;
import com.msgpost.core.JavaEEFrameworkBaseController;
import com.msgpost.model.sys.Message;
import com.msgpost.model.sys.User;
import com.msgpost.service.sys.MessageService;
import com.msgpost.service.sys.UserService;

import core.support.ExtJSBaseParameter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Message的控制层
 * @Author: Bi Ran
 */
@Controller
@RequestMapping("/message")
public class MessageController extends JavaEEFrameworkBaseController<Message> implements Constant {

	@Resource
	private MessageService messageService;
	
	@Resource
	private UserService userService;
	
	// 查询Message的表格，包括分页、搜索和排序
	@RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.GET })
	public String message(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		
		System.out.println("Message Controller");
		return "back/message/message";
	} 
	
	// 查询Message的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getmessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void getMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		Integer limit = 10; 
		String res_limit = request.getParameter("limit"); 
		if(res_limit != null && !res_limit.isEmpty()){ 
			limit = Integer.parseInt(res_limit);
		}  
		JSONArray jsonArray = messageService.queryMessageWithAuthorName(limit);  
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("data", jsonArray);
		writeJSON(response, result); 
	} 

	// 保存Message的实体Bean
	@RequestMapping(value = "/saveMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Message entity, HttpServletRequest request, HttpServletResponse response) throws IOException { 
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (CMD_EDIT.equals(parameter.getCmd())) {
			messageService.merge(entity);
			result.put("result", 2);
			writeJSON(response, result);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			messageService.persist(entity);
			result.put("result", 1);
			writeJSON(response, result);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作Message的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Enter operateMessage");
		String oper = request.getParameter("oper");
		String id = "0";//request.getParameter("id");
		if (oper.equals("del")) {
//			String[] ids = id.split(",");
//			deleteMessage(request, response, (Long) ConvertUtils.convert(ids, Long.class));
		} else {
			// ADD
			Map<String, Object> result = new HashMap<String, Object>();
			String message = request.getParameter("message"); 
			Long currentUserId = (Long) SecurityUtils.getSubject().getPrincipal(); 
			Integer authorid = currentUserId.intValue();
//			System.out.println(currentUserId);
//			System.out.println(message);
			Message msg= null;
			if (oper.equals("edit")) {
				msg = messageService.get(Integer.valueOf(id));
			} 
			if (StringUtils.isBlank(message)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "Message can't be empty");
				writeJSON(response, result);
			} else {
				Message entity = new Message(); 
				entity.setAuthorid(authorid);
				entity.setMessage(message);
				entity.setTime(new Date()); 
				if (oper.equals("edit")) {
//					entity.setId(Long.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除Message
	@RequestMapping("/deleteMessage")
	public void deleteMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Delete a Message"); 
		String res_id = request.getParameter("id"); 
		if(res_id != null && !res_id.isEmpty()){ 
			Integer id = Integer.parseInt(res_id);
			messageService.deleteByProperties("messageid", id); 
			response.sendRedirect(response.encodeRedirectURL("/msgpost/home#page/message"));
//			writeJSON(response, "{success:true}");
		}  
		else{
//			writeJSON(response, "{success:false}");
		} 
	} 
	
}
