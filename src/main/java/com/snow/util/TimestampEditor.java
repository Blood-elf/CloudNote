package com.snow.util;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;


/**
 *	日期类型转换器，可以将页面传入的日期转换
 *	为java.sql.Timestamp
 */
public class TimestampEditor extends PropertyEditorSupport {
	
	//text参数就是页面传入的日期字符串
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text == null) {
			//如果传入的日期为null，则将对应的值设置为null
			setValue(null);
		} else {
			setValue(new Timestamp(Long.valueOf(text)));
		}
	}

}
