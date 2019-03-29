package com.snow.util;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.snow.entity.User;


/**
 *	登录检查拦截器，针对除了注册、登录功能之外的任何方法做拦截，判断其是否已经成功登录，
 *	若没有登录需返回错误信息，并将页面强制调转至登录页。
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	//最终：在请求结束时执行
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	//后置方法：在调用Controller之后执行
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	//前置方法：在调用Controller之前执行
	//该方法如果返回true，则Spring会继续调用
	//Controller，否则不会。
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object obj) throws Exception {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			//没有登录，不允许访问Controller，需要手动给页面返回一些提示信息，
			//由于页面上所有的回调函数接收的都是Result,做成的json对象，所以此时需要给页面返回一个json，封装对应的错误信息
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			Writer writer = response.getWriter();
			writer.write("{\"success\":false,\"message\":\"请登录\"}");
			writer.close();
			return false;
		} else {
			//成功登录，可以访问Controller
			return true;
		}
	}

}
