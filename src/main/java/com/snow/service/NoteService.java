package com.snow.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.snow.dao.NoteBookMapper;
import com.snow.dao.NoteMapper;
import com.snow.dao.ShareMapper;
import com.snow.entity.Note;
import com.snow.entity.NoteBook;
import com.snow.entity.Share;
import com.snow.util.SystemConstant;


@Service
public class NoteService implements SystemConstant{

	@Resource
	private NoteMapper noteMapper;
	@Resource
	private NoteBookMapper noteBookMapper;
	@Resource
	private ShareMapper shareMapper;
	
	public List<Note> findNotes(String noteBookId) {
		if(noteBookId==null)
			throw new RuntimeException("参数为空");
		return noteMapper.findByNoteBookId(noteBookId);
	}
	
	
	public Note addNote(String noteBookId,String noteTitle,String userId) {
		
		if(noteBookId == null || noteTitle == null || userId == null) {
			throw new RuntimeException("参数为空");
		}	
		
		Note note = new Note();
		note.setCn_note_id(UUID.randomUUID().toString());
		note.setCn_notebook_id(noteBookId);
		note.setCn_user_id(userId);
		note.setCn_note_title(noteTitle);
		note.setCn_note_create_time(System.currentTimeMillis());
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		
		noteMapper.save(note);	
		
		return note;
	}
	
	public void updateNote(Note note) {
		if(note == null)
			throw new RuntimeException("参数为空");
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		noteMapper.update(note);
	}
	
	public void deleteNote(Note note) {
		if(note == null)
			throw new RuntimeException("参数为空");
		//查询出回收站笔记本
		NoteBook recycle = noteBookMapper.findSpecialByType(note.getCn_user_id(), NOTEBOOK_TYPE_ID_RECYCLE);	
		
		//将笔记的笔记本更新为回收站
		note.setCn_notebook_id(recycle.getCn_notebook_id());
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		
		noteMapper.update(note);
	}
	
	public void move(Note note) {
		if(note == null)
			throw new RuntimeException("参数为空");
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		noteMapper.update(note);
	}
	
	/**
	 * 分享笔记，将笔记复制到分享表里
	 * @param note
	 */
	public void addShare(Note note) {
		if(note == null)
			throw new RuntimeException("参数为空");
		
		Share s = shareMapper.findByNoteId(note.getCn_note_id());
		if(s == null) {
			//如果该笔记没有分享过，则新增分享笔记
			Share share = new Share();
			share.setCn_share_id(UUID.randomUUID().toString());
			share.setCn_share_title(note.getCn_note_title());
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_note_id(note.getCn_note_id());
			shareMapper.save(share);			
		}
		else {
			//如果该笔记分享过，则修改已分享的笔记
			s.setCn_share_title(note.getCn_note_title());
			s.setCn_share_body(note.getCn_note_body());
			shareMapper.update(s);			
		}
	}
	
	/**
	 * 搜索分享笔记
	 * @param condition 搜索条件
	 * @param currentPage 当前页码
	 * @param pageSize 每页显示最大行
	 * @return
	 */	
	public List<Share> findShares(String condition, int currentPage, int pageSize){
		//begin=(currentPage-1)*pageSize+1
		//由于mysql行数从1开始计算，所以需要在原来公式基础上-1		
		int begin = (currentPage-1)*pageSize;
		return shareMapper.findByPage(condition, begin, pageSize);
	}

	/**
	 * 增加一个收藏笔记
	 * @param share 用户选中的被分享的笔记
	 * @param userId 用户的ID
	 */
	public void addFavoritesNote(Share share, String userId) {
		if(share==null)
			throw new RuntimeException("参数为空");
		
		Note n = new Note();
		n.setCn_note_id(UUID.randomUUID().toString());
		NoteBook favorates = noteBookMapper.findSpecialByType(userId, NOTEBOOK_TYPE_ID_FAVORITES);
		n.setCn_notebook_id(favorates.getCn_notebook_id());
		n.setCn_user_id(userId);
		n.setCn_note_title(share.getCn_share_title());
		n.setCn_note_body(share.getCn_share_body());
		n.setCn_note_create_time(System.currentTimeMillis());
		n.setCn_note_last_modify_time(System.currentTimeMillis());
		
		noteMapper.save(n);		
	}
}
