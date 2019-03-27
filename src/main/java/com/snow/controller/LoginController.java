package com.snow.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.snow.entity.User;
import com.snow.service.LoginService;
import com.snow.util.SystemConstant;


/**
 *	登录模块
 */
@Controller
@RequestMapping("/login")
public class LoginController implements SystemConstant{
	
	@Resource
	private LoginService loginService;
	
	/**
	 * SpringMVC在接收页面传入的参数时，实际上它并不知道参数要传给谁，它只是采用了傻瓜式的处理，即遍历方法中所有的参数，挨个判断
	 * 方法参数是否与传入参数相匹配（名称一致），若匹配则给该方法参数注入值，否则不注入。如果有多个参数都可以匹配，则注入多次。
	 */
	@RequestMapping("/register.do")
	@ResponseBody
	public Result register(User user) {
		System.out.println("into register");
		//创建用户
		Map<String,Object> map = loginService.addUser(user);
		System.out.println("out register");
		return new Result(map);
	}
	
	@RequestMapping("/checkLogin.do")
	@ResponseBody
	public Result checkLogin(String userName, String password, HttpSession session) {
		Map<String, Object> map = loginService.checkUser(userName, password);
		Object flag = map.get("flag");
		if (flag.equals(SUCCESS)) {
			// 登录成功，将用户信息存入session
			session.setAttribute("user", map.get("user"));
		}
		return new Result(map);
	}
	
	@RequestMapping("/logout.do")
	@ResponseBody
	public Result logout(HttpSession session) {
		System.out.println("into logout");
		//注销session
		session.invalidate();
		System.out.println("out logout");
		return new Result();
	}
	
	@RequestMapping("/updatePassword.do")
	@ResponseBody
	public Result updatePassword(String oldPassword,String newPassword,HttpSession session) {
		User user = (User)session.getAttribute("user");
		boolean flag = loginService.updatePassword(oldPassword, newPassword, user);
		return new Result(flag);
	}

}
