package com.snow.controller;

import java.io.Serializable;

/**
 *	用来封装Controller向页面传出的数据
 */
public class Result implements Serializable {

	private static final long serialVersionUID = -7366892359056569333L;

	/**
	 * 程序是否执行成功，默认为true。 目前都是返回true，后续我们会采用统一的方式处理异常，届时会改变这个属性的值为false，
	 * 同时给message设置相关的错误提示。
	 */
	private boolean success = true;
	
	/**
	 * 当程序执行报错时，用来封装错误信息
	 */
	private String message;
	
	/**
	 * 用来封装业务数据
	 */
	private Object data;
	
	public Result() {
		//通过无参构造器创建对象时，属性值为：
		//success=true;
		//message=null;
		//data=null;
	}
	
	public Result(Object data) {
		this.data = data;
		//通过该构造器创建对象时，属性值为：
		//success=true;
		//message=null;
		//data=传入的data参数值;
	}
	
	public Result(boolean success, String message ,Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
