package com.snow.dao;

import java.util.List;

import com.snow.entity.Share;

public interface ShareMapper {
	
	void save(Share share);

	Share findByNoteId(String noteId);
	
	void update(Share share);
	
	/**
	 * 分页查询
	 * @param condition 搜索条件
	 * @param begin	分页起使行
	 * @param pageSize	每页显示最大行
	 * @return	
	 */
	List<Share> findByPage(String condition,int begin,int pageSize);	
	
}
