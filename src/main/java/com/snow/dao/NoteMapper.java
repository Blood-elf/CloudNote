package com.snow.dao;

import java.util.List;

import com.snow.entity.Note;
import com.snow.entity.Share;


public interface NoteMapper {
	
	List<Note> findByNoteBookId(String noteBookId);

	void save(Note note);
	
	void update(Note note);
	
}
