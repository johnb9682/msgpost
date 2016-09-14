package com.msgpost.controller; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView; 

import com.msgpost.core.Constant;
import com.msgpost.model.sys.User;
 
@Controller
public class HelloWorldController {
	String message = "Welcome to Spring MVC!";
 
	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	} 
	
	// 跳转到主页，获取菜单并授权
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(); 
		if (session.getAttribute(com.msgpost.core.Constant.SESSION_SYS_USER) == null) { 
			return new ModelAndView();
		} else {
			User user = (User) session.getAttribute(com.msgpost.core.Constant.SESSION_SYS_USER);  
			return new ModelAndView("back/index"); 
		}
	}

//	@RequestMapping("/home")
//	public ModelAndView showHomeMessage(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
//		System.out.println("home in controller");
// 
//		ModelAndView mv = new ModelAndView("back/index");
//		mv.addObject("message", message);
//		mv.addObject("name", name);
//		return mv;
//	} 
}
