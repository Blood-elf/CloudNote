package com.snow.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snow.entity.Note;
import com.snow.service.NoteService;


@Component
@RequestMapping("/note")
public class NoteController extends BaseController {

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/find.do")
	@ResponseBody
	public Result find(String noteBookId) {
		List<Note> list = noteService.findNotes(noteBookId);
		return new Result(list);
	}
	
}
