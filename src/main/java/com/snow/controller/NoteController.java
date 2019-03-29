package com.snow.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snow.entity.Note;
import com.snow.entity.Share;
import com.snow.entity.User;
import com.snow.service.NoteService;
import com.snow.util.SystemConstant;


@Component
@RequestMapping("/note")
public class NoteController extends BaseController implements SystemConstant{

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/find.do")
	@ResponseBody
	public Result find(String noteBookId) {
		List<Note> list = noteService.findNotes(noteBookId);
		return new Result(list);
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Result add(String noteBookId,String noteTitle,HttpSession session) {
		User user = (User)session.getAttribute("user");
		Note note = noteService.addNote(noteBookId, noteTitle, user.getCn_user_id());
		return new Result(note);
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public Result update(Note note) {
		noteService.updateNote(note);
		return new Result();
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Result delete(Note note) {
		noteService.deleteNote(note);
		return new Result();
	}
	
	@RequestMapping("/move.do")
	@ResponseBody
	public Result move(Note note) {
		noteService.move(note);
		return new Result();
	}
	
	@RequestMapping("/share.do")
	@ResponseBody
	public Result share(Note note) {
		noteService.addShare(note);
		return new Result();
	}
	
	@RequestMapping("/search.do")
	@ResponseBody
	public Result search(String condition, Integer currentPage) {
		List<Share> list = noteService.findShares(condition, currentPage, PAGE_SIZE);
		return new Result(list);
	}	
	
	@RequestMapping("/like.do")
	@ResponseBody
	public Result like(Share share, HttpSession session) {
		User user = (User)session.getAttribute("user");
		noteService.addFavoritesNote(share, user.getCn_user_id());
		return new Result();
	}
}
