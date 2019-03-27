package com.snow.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.snow.dao.NoteMapper;
import com.snow.entity.Note;


@Service
public class NoteService {

	@Resource
	private NoteMapper noteMapper;
	
	public List<Note> findNotes(String noteBookId) {
		if(noteBookId==null)
			throw new RuntimeException("参数为空");
		return noteMapper.findByNoteBookId(noteBookId);
	}
	
}
