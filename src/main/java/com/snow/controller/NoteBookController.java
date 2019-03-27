package com.snow.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snow.entity.NoteBook;
import com.snow.entity.User;
import com.snow.service.NoteBookService;

@Controller
@RequestMapping("/notebook")
public class NoteBookController extends BaseController{
	
	@Resource
	private NoteBookService noteBookService;
	
	@RequestMapping("/findnormal.do")
	@ResponseBody
	public Result findNormalNoteBook(HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<NoteBook> notebooks = noteBookService.findNormalNoteBook(user.getCn_user_id());
		return new Result(notebooks);
	}
	
	@RequestMapping("/findspecial.do")
	@ResponseBody
	public Result findSpecialNoteBook(HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<NoteBook> specialbookList = noteBookService.findSpecialNoteBook(user.getCn_user_id());
		return new Result(specialbookList);
	}
	
	@RequestMapping("/addNoteBook.do")
	@ResponseBody
	public Result addNoteBook(String noteBookName,HttpSession session) {
		User user = (User)session.getAttribute("user");
		NoteBook n = noteBookService.add(user.getCn_user_id(),noteBookName);
		return new Result(n);
	}
	
	@RequestMapping("/updateNoteBook.do")
	@ResponseBody
	public Result updateNoteBook(NoteBook notebook) {
		noteBookService.update(notebook);
		return new Result();
	}
	
	@RequestMapping("/deleteNoteBook.do")
	@ResponseBody
	public Result deleteNoteBook(String id) {
		noteBookService.delete(id);
		return new Result();
	}
}
