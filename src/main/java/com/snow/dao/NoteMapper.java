package com.snow.dao;

import java.util.List;

import com.snow.entity.Note;


public interface NoteMapper {
	
	List<Note> findByNoteBookId(String noteBookId);

}
