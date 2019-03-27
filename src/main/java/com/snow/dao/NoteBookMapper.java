package com.snow.dao;

import java.util.List;

import com.snow.entity.NoteBook;

public interface NoteBookMapper {

	void save(NoteBook notebook);
	
	List<NoteBook> findNormal(String userId);
	
	List<NoteBook> findSpecial(String userId);
	
	void update(NoteBook notebook);
	
	void delete(String id);
}
