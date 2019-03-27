package com.snow.dao;

import com.snow.entity.User;

public interface UserMapper {

	User findByName(String userName);
	
	void save(User user);
	
	void update(User user);
	
}
