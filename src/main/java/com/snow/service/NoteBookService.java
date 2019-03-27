package com.snow.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.snow.dao.NoteBookMapper;
import com.snow.entity.NoteBook;
import com.snow.util.SystemConstant;

@Service
public class NoteBookService implements SystemConstant{
	
	@Resource
	private NoteBookMapper noteBookMapper;
	
	public List<NoteBook> findNormalNoteBook(String userId){
		if(userId == null) 
			throw new RuntimeException("参数为空");
		
		return noteBookMapper.findNormal(userId);
	}
	
	public List<NoteBook> findSpecialNoteBook(String userId){
		if(userId == null)
			throw new RuntimeException("参数为空");
		
			return noteBookMapper.findSpecial(userId);
	}
	
	public NoteBook add(String userId,String noteBookName) {
		if(userId == null || noteBookName == null)
			throw new RuntimeException("参数为空");
		
		NoteBook n = new NoteBook();
		n.setCn_notebook_id(UUID.randomUUID().toString());
		n.setCn_user_id(userId);
		n.setCn_notebook_type_id(NOTEBOOK_TYPE_ID_NORMAL);
		n.setCn_notebook_name(noteBookName);
		n.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		
		noteBookMapper.save(n);
		
		return n;
	}
	
	public void update(NoteBook notebook) {
		if(notebook == null) 
			throw new RuntimeException("参数为空");
		
		noteBookMapper.update(notebook);
	}
	
	public void delete(String id) {
		if(id == null)
			throw new RuntimeException("参数为空");
		
		noteBookMapper.delete(id);
	}
}
