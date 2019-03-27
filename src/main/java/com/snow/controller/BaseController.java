package com.snow.controller;

import java.sql.Timestamp;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.snow.util.TimestampEditor;


/**
 * Controller的父类，用来封装Controller的公共代码。
 *
 */
public class BaseController implements WebBindingInitializer {

	@Override
	//在请求开始阶段，注册类型转换器
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		//注册一个自定义的类型转换器；
		//给java.sql.Timestamp指定转换器为TimestampEditor;
		binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
	}

}
