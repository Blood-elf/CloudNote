package com.snow.test;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.snow.dao.NoteBookMapper;
import com.snow.dao.UserMapper;
import com.snow.entity.NoteBook;
import com.snow.entity.User;
import com.snow.service.LoginService;


public class TestCase {
	
	/**
	 * UserMapper.findByName
	 */
	@Test
	public void test1() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config/applicationContext.xml");
		UserMapper mapper = ctx.getBean(UserMapper.class);
		User user = mapper.findByName("zhouj");
		System.out.println(user.getCn_user_id()
				+ " " + user.getCn_user_name() + " " 
				+ user.getCn_user_password());
	}
	
	/**
	 * UserMapper.save
	 */
	@Test
	public void test2() {
		User user = new User();
		user.setCn_user_id(
				UUID.randomUUID().toString());
		user.setCn_user_name("liubei");
		user.setCn_user_password("123");
		user.setCn_user_desc("刘备的账号");
		
		ApplicationContext ctx = 
			new ClassPathXmlApplicationContext(
					"spring-config/applicationContext.xml");
		UserMapper mapper = 
			ctx.getBean(UserMapper.class);
		mapper.save(user);
	}
	
	/**
	 * LoginService.addUser
	 */
	@Test
	public void test3() {
		ApplicationContext ctx = 
			new ClassPathXmlApplicationContext(
					"spring-config/applicationContext.xml");
		LoginService ser = ctx.getBean(LoginService.class);
		User user = new User();
		user.setCn_user_name("zhangfei");
		user.setCn_user_desc("张飞");
		user.setCn_user_password("111111");
		Map<String, Object> map = ser.addUser(user);
		System.out.println(map.get("pass"));
	}
	
	/**
	 * NoteBookMapper.save
	 */
	@Test
	public void test4() {
		ApplicationContext ctx = 
			new ClassPathXmlApplicationContext(
					"spring-config/applicationContext.xml");
		NoteBookMapper mapper = 
			ctx.getBean(NoteBookMapper.class);
		NoteBook n = new NoteBook();
		n.setCn_notebook_id(
				UUID.randomUUID().toString());
		n.setCn_user_id(
				"bd7167a5-9a04-4729-9901-744fa2365cfe");
		n.setCn_notebook_type_id("5");
		n.setCn_notebook_name("Spring");
		n.setCn_notebook_createtime(
				new Timestamp(System.currentTimeMillis()));
		mapper.save(n);
	}

}
