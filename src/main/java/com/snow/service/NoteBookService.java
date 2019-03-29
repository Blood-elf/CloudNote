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
	
	/**
	 * 查询出用户可用的笔记本，包括默认笔记本，以及普通笔记本。
	 * @param userId  用户ID
	 * @return
	 */
	public List<NoteBook> findEnableNoteBook(String userId){
		if(userId == null)
			throw new RuntimeException("参数为空");
		
		//查询出所有的普通笔记本
		List<NoteBook> list = noteBookMapper.findNormal(userId);
		
		//查询出默认笔记本
		NoteBook push = noteBookMapper.findSpecialByType(userId, NOTEBOOK_TYPE_ID_PUSH);
		push.setCn_notebook_name("默认笔记本");
		list.add(0, push);
		
		return list;
	}
}
