package com.snow.util;

/**
 *	系统常量接口
 */
public interface SystemConstant {

	//笔记本类型ID
	String NOTEBOOK_TYPE_ID_FAVORITES = "1"; //收藏
	String NOTEBOOK_TYPE_ID_RECYCLE = "2"; //回收
	String NOTEBOOK_TYPE_ID_ACTION = "3"; //活动
	String NOTEBOOK_TYPE_ID_PUSH = "4"; //推送(默认)
	String NOTEBOOK_TYPE_ID_NORMAL = "5"; //普通
	
	//笔记本类型名
	String NOTEBOOK_TYPE_NAME_FAVORITES = "收藏"; //收藏
	String NOTEBOOK_TYPE_NAME_RECYCLE = "回收站";
	String NOTEBOOK_TYPE_NAME_ACTION = "活动";
	String NOTEBOOK_TYPE_NAME_PUSH = "推送";
	String NOTEBOOK_TYPE_NAME_NORMAL = "普通";
	
	//登录常量
	int SUCCESS = 0;
	int NAME_ERROR = 1;
	int PASSWORD_ERROR = 2;
	
}
