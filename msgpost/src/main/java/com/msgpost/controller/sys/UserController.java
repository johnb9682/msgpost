package com.msgpost.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
 
import com.msgpost.core.Constant;
import com.msgpost.core.JavaEEFrameworkBaseController;
import com.msgpost.model.sys.User;
import com.msgpost.service.sys.UserService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * User的控制层
 * @Author: Bi Ran
 */
@Controller
@RequestMapping("/user")
public class UserController extends JavaEEFrameworkBaseController<User> implements Constant {

	@Resource
	private UserService userService;
	
	String message = "Welcome to Spring MVC!";

	// 登录
	@RequestMapping("/login")
	public void login(User userModel, HttpServletRequest request, HttpServletResponse response) throws IOException { 
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.getByProerties("username", userModel.getUsername());
		if (user == null ) { // 用户名有误或已被禁用
			result.put("result", -1);
			writeJSON(response, result);
			return;
		} 
		System.out.println(userModel.getUsername());
		if (!user.getPassword().equals(new Sha256Hash(userModel.getPassword()).toHex())) { // 密码错误
			result.put("result", -2);
			writeJSON(response, result);
			return;
		} 
		//user.setLastLoginTime(new Date());
		userService.merge(user);
		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(userModel.getUsername(),userModel.getPassword(), userModel.isRememberMe()));//
		Session session = subject.getSession();
		session.setAttribute(SESSION_SYS_USER, user);
		//session.setAttribute("ROLE_KEY", sysUser.getRoles().iterator().next().getRoleKey());
		result.put("result", 1); 
		writeJSON(response, result);
	}
	
	// 注册
	@RequestMapping("/register")
	public void register(User userModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.getByProerties("username", userModel.getUsername());
		if (user != null) {
			result.put("result", -1);
			writeJSON(response, result);
			return;
		}
		User newuser = new User();
		newuser.setUsername(userModel.getUsername());  
		newuser.setImgpath("/static/assets/avatars/profile-pic.jpg");
		// sysUser.setPassword(MD5.crypt(sysUserModel.getPassword()));
		newuser.setPassword(new Sha256Hash(userModel.getPassword()).toHex());
//			sysUser.setStatus(false);
//			sysUser.setLastLoginTime(new Date());

//			Set<Role> roles = new HashSet<Role>();
//			Role role = roleService.getByProerties("roleKey", "ROLE_USER");
//			roles.add(role);
//			sysUser.setRoles(roles); 
		userService.persist(newuser);
		// sysUserService.saveSysuserAndRole(sysUser.getId(), 3);

		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(userModel.getUsername(),userModel.getPassword()));
		Session session = subject.getSession();
		session.setAttribute(SESSION_SYS_USER, newuser);
//			session.setAttribute("ROLE_KEY", sysUser.getRoles().iterator().next().getRoleKey()); 
		result.put("result", 1);
		writeJSON(response, result);
	}
	
	// 登出
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SecurityUtils.getSubject().logout();
		response.sendRedirect("/msgpost/login.jsp");
	} 
	
	@RequestMapping("/checkusername")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		System.out.println("check username");
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("valid", true); 
		writeJSON(response, result);
	}
	
	@RequestMapping("/userprofile")
	public ModelAndView userProfile(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		System.out.println("User Profile"); 
		User user = userService.get(((User) request.getSession().getAttribute(SESSION_SYS_USER)).getUserid());
		System.out.println(user.getImgpath());
		//SysUser sysUserWithAvatar = sysUserService.getSysUserWithAvatar(sysuser);
		return new ModelAndView("back/user/userprofile", "user", user);//, "sysuser", sysUserWithAvatar);
	} 
	
	// 更改密码
	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Reset Password");
		String password = request.getParameter("password");
		Long id = ((User) request.getSession().getAttribute(SESSION_SYS_USER)).getUserid();
		// sysUserService.updateByProperties("id", id, "password", MD5.crypt(password));
		userService.updateByProperties("userid", id, "password", new Sha256Hash(password).toHex());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		writeJSON(response, result);
	}
	
	// 保存个人资料
	@RequestMapping(value = "/saveUserProfile", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveSysUserProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = ((User) request.getSession().getAttribute(SESSION_SYS_USER)).getUserid();
		User user = userService.get(userId);
		User entity = new User();  
		if (null != request.getParameter("username")) {
			entity.setUsername(request.getParameter("username"));   
		} else{
			entity.setUsername(user.getUsername());   
		}
		entity.setUserid(user.getUserid()); 
		entity.setPassword(user.getPassword());
		entity.setImgpath(user.getImgpath()); 
		userService.merge(entity);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		writeJSON(response, result);
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	// 上传个人资料的头像
	@RequestMapping(value = "/uploadAttachement", method = RequestMethod.POST)
	public void uploadAttachement(@RequestParam(value = "avatar", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Upload Attachement");
		RequestContext requestContext = new RequestContext(request);
		JSONObject json = new JSONObject();
		if (!file.isEmpty()) {
			System.out.println("File exist");
			if (file.getSize() > 2097152) {
				System.out.println("File Too big");
				json.put("message", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					String originalFilename = file.getOriginalFilename();
					String fileName = sdf.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/img/" + DateFormatUtils.format(new Date(), "yyyyMM")));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					System.out.println(filePath);
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					String destinationFilePath = "/static/upload/img/" + DateFormatUtils.format(new Date(), "yyyyMM") + "/" + fileName;
					System.out.println(destinationFilePath);
					Long userId = ((User) request.getSession().getAttribute(SESSION_SYS_USER)).getUserid();
//					attachmentService.deleteByProperties(new String[] { "type", "typeId" }, new Object[] { (short) 1, sysUserId });
//					Attachment attachment = new Attachment();
//					attachment.setFileName(originalFilename);
//					attachment.setFilePath(destinationFilePath);
//					attachment.setType((short) 1);
//					attachment.setTypeId(sysUserId);
//					attachmentService.persist(attachment);
					User user = userService.get(userId);
					User entity = new User();   
					entity.setUsername(user.getUsername());    
					entity.setUserid(user.getUserid()); 
					entity.setPassword(user.getPassword());
					entity.setImgpath(destinationFilePath); 
					userService.merge(entity);
					json.put("status", "OK");
					json.put("url", request.getContextPath() + destinationFilePath);
					json.put("message", "g_uploadSuccess");//requestContext.getMessage("g_uploadSuccess"));
				} catch (Exception e) {
					e.printStackTrace();
					json.put("message", "g_uploadFailure");//requestContext.getMessage("g_uploadFailure"));
				}
			}
		} else {
			json.put("message", requestContext.getMessage("g_uploadNotExists"));
		}
		writeJSON(response, json.toString());
	}
	
	// 查询User的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getuser", method = { RequestMethod.POST, RequestMethod.GET })
	public void getUser(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	}
	
	// 查询User的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getsingleuserprofile", method = { RequestMethod.POST, RequestMethod.GET })
	public void getSingleUserProfile(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	}

	// 保存User的实体Bean
	@RequestMapping(value = "/saveuser", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(User entity, HttpServletRequest request, HttpServletResponse response) throws IOException { 
	}

	// 操作User的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateuser", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateUser(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	}

	// 删除User
	@RequestMapping("/deleteuser")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException { 
	}

	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name, 
			@RequestParam(value = "userid", required = false, defaultValue = "2") String userid) {
		
		System.out.println("in controller: /sys/user/hello"); 
		
		User user = new User();  
		Object[] element = userService.querySingleUserInfo(Integer.valueOf(userid));      
		
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", element[1]);
		return mv;
	} 
	
	@RequestMapping("/callUnauthorized")
	public ModelAndView callUnauthorized(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name, 
			@RequestParam(value = "userid", required = false, defaultValue = "2") String userid) {
		
		System.out.println("callUnauthorized in controller: /sys/user/hello"); 
		
		User user = new User();  
		Object[] element = userService.querySingleUserInfo(Integer.valueOf(userid));      
		
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", element[1]);
		return mv;
	} 
	
}
