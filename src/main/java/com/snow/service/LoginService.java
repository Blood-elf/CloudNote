package com.snow.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.snow.dao.NoteBookMapper;
import com.snow.dao.UserMapper;
import com.snow.entity.NoteBook;
import com.snow.entity.User;
import com.snow.util.Md5Util;
import com.snow.util.SystemConstant;


@Service
public class LoginService implements SystemConstant{
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private NoteBookMapper noteBookMapper;	
	
	public boolean updatePassword(String oldPassword,String newPassword,User user) {
		//参数安全校验
		if(oldPassword==null || newPassword==null || user==null) {
			throw new RuntimeException("参数不能为空");
		}
		
		//验证原密码是否正确
		String md5OldPassword = Md5Util.md5(oldPassword);
		if(!md5OldPassword.contentEquals(user.getCn_user_password())) {
			//密码不正确
			return false;
		}
		
		//修改密码
		String md5NewPassword = Md5Util.md5(newPassword);
		user.setCn_user_password(md5NewPassword);
		userMapper.update(user);
		
		return true;
	}
	
	/**
	 * 用户登陆
	 * @param userName  登陆用户名
	 * @param password	登陆用户密码
	 * @return
	 */
	public Map<String, Object> checkUser(String userName, String password) {
		
		if(userName == null || password == null)
			throw new RuntimeException("参数不能为空.");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userMapper.findByName(userName);
		if(user == null) {
			//用户名不存在
			map.put("flag", NAME_ERROR);
		} else {
			//用户名存在，继续验证密码
			String md5Password = Md5Util.md5(password);
			if(md5Password.equals(user.getCn_user_password())) {
				//验证通过
				map.put("flag", SUCCESS);
				map.put("user", user);
			} else { 
				//密码错误
				map.put("flag", PASSWORD_ERROR);
			}
		}
		
		return map;
	}
	
	/**
	 * 添加一个用户，以实现注册。
	 */
	public Map<String, Object> addUser(User user) {
		//方法参数的有效性验证，是基本的规范，可以保证方法的独立性和严谨性。
		//在发现参数为空时，这是错误的调用情况，我们向外抛出异常，该异常将来我们采用统一的方式统一处理。
		if(user == null)
			throw new RuntimeException("参数不能为空.");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//验证用户名是否重复
		User u = userMapper.findByName(user.getCn_user_name());
		if(u == null) {
			//用户名不重复，创建用户
			user.setCn_user_id(UUID.randomUUID().toString());
			user.setCn_user_password(Md5Util.md5(user.getCn_user_password()));
			userMapper.save(user);
			
			//给用户预置4个特殊笔记本
			initNoteBook(user.getCn_user_id());			
			
			//返回正确的提示信息
			map.put("pass", true);
		} else {
			//用户名重复，返回错误的提示信息
			map.put("pass", false);
			map.put("error", "用户名已存在");
		}
		
		return map;
	}

	/**
	 * 给用户预置4个特殊笔记本
	 */
	private void initNoteBook(String userId) {
		//预置收藏夹
		NoteBook n1 = new NoteBook();
		n1.setCn_notebook_id(UUID.randomUUID().toString());
		n1.setCn_user_id(userId);
		n1.setCn_notebook_type_id(NOTEBOOK_TYPE_ID_FAVORITES);
		n1.setCn_notebook_name(NOTEBOOK_TYPE_NAME_FAVORITES);
		n1.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		noteBookMapper.save(n1);
		
		//预置回收站
		NoteBook n2 = new NoteBook();
		n2.setCn_notebook_id(UUID.randomUUID().toString());
		n2.setCn_user_id(userId);
		n2.setCn_notebook_type_id(NOTEBOOK_TYPE_ID_RECYCLE);
		n2.setCn_notebook_name(NOTEBOOK_TYPE_NAME_RECYCLE);
		n2.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		noteBookMapper.save(n2);
		
		//预置活动
		NoteBook n3 = new NoteBook();
		n3.setCn_notebook_id(UUID.randomUUID().toString());
		n3.setCn_user_id(userId);
		n3.setCn_notebook_type_id(NOTEBOOK_TYPE_ID_ACTION);
		n3.setCn_notebook_name(NOTEBOOK_TYPE_NAME_ACTION);
		n3.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		noteBookMapper.save(n3);
		
		//预置默认笔记本
		NoteBook n4 = new NoteBook();
		n4.setCn_notebook_id(UUID.randomUUID().toString());
		n4.setCn_user_id(userId);
		n4.setCn_notebook_type_id(NOTEBOOK_TYPE_ID_PUSH);
		n4.setCn_notebook_name(NOTEBOOK_TYPE_NAME_PUSH);
		n4.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		noteBookMapper.save(n4);
	}	
	
}
