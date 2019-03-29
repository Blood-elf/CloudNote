/***
 * 加载普通笔记
 */
function getNormalNoteList(cn_notebook_id){
	$.post(
			base_path+"/note/find.do",
			{"noteBookId":cn_notebook_id},
			function(result){
				if(result.success){
					var list = result.data;
					$(list).each(function(){
						//根据笔记创建li，并插入到笔记列表下
						var li = '<li class="online"><a class="unchecked"><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '
							+this.cn_note_title
							+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button></a><div class="note_menu" tabindex="-1"><dl><dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt></dl></div></li>';
						$("#second_side_right ul").append(li);						
						//绑定数据
						$("#second_side_right ul li:last").data("note",this);						
					});
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 查询普通笔记内容
 */
function getNoteDetail(li){
	//获取绑定的笔记数据	
	var note = li.data("note");
	//显示标题
	$("#input_note_title").val(note.cn_note_title);
	//显示内容
	if(note.cn_note_body == null){
		um.setContent("");
	}
	else{
		um.setContent(note.cn_note_body);
	}
}

/***
 * 创建普通笔记
 */
function createNormalNote(){
	console.info("创建普通笔记");
	//获取选中的笔记本
	var li = $("#first_side_right .checked").parent();
	var notebook = li.data("notebook");
	//获取新增的笔记本标题
	var title = $("#input_note").val();
	if(!title) {
		return;
	}
	$.post(
			base_path+"/note/add.do",
			{
				"noteBookId":notebook.cn_notebook_id,
				"noteTitle":title
			},
			function(result){
				if(result.success){
					var note = result.data;
					var li = '<li class="online"><a class="unchecked"><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '
						+note.cn_note_title+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button></a><div class="note_menu" tabindex="-1"><dl><dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt></dl></div></li>';					
					$("#second_side_right ul").append(li);
					//绑定数据
					$("#second_side_right ul li:last").data("note",note);
					//关闭弹出框
					$(".cancle").trigger("click");
					$("#second_side_right ul li:last").trigger("click");
				}
				else{
					alert(result.message);
				}
			}
	);
	
}

/***
 * 更新普通笔记
 */
function updateNormalNote(){
	//获取选中的笔记
	var li = $("#second_side_right .checked").parent();
	var note = li.data("note");
	//获取编辑后的笔记标题
	var title = $("#input_note_title").val();
	//获取编辑后的笔记内容
	var body = um.getContent();
	
	//如果标题为空，程序结束
	if(!title) return;
	//如果内容未边，程序结束
	if(title==note.cn_note_title && body==note.cn_note_body) return;
	
	//更新笔记
	note.cn_note_title = title;
	note.cn_note_body = body;
	
	$.post(
			base_path+"/note/update.do",
			note,
			function(result){
				if(result.success){
					$("#second_side_right .checked").html('<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '
							+title
							+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>');
					li.data("note",note);
					$("footer strong").text("更新成功").parent().fadeIn(200).fadeOut(3000);
				}
				else{
					alert(result.message);
				}
			}
	);
	
}

/***
 * 删除普通笔记
 */
function deleteNormalNote(){
	//获取选中的笔记
	var li = $("#second_side_right .checked").parent();
	var note = li.data("note");
	//执行删除
	$.post(
			base_path+"/note/delete.do",
			note,
			function(result){
				if(result.success){
					//删除笔记节点
					li.remove();
					//关闭弹出框
					$(".cancle").trigger("click");
					//笔记内容清空
					$("#input_note_title").val("");
					um.setContent("");					
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 移动笔记
 */
function moveNote(notebook_id){
	//获取当前选中的笔记
	var li = $("#second_side_right .checked").parent();
	var note = li.data("note");
	if(notebook_id==note.cn_notebook_id) 
		return;
	
	//更新笔记的笔记本id
	note.cn_notebook_id = notebook_id;
	$.post(
			base_path+"/note/move.do",
			note,
			function(result){
				if(result.success){
					//将笔记从当前的笔记本中移除
					li.remove();
					//关闭弹出框
					$(".cancle").trigger("click");
					//笔记内容清空
					$("#input_note_title").val("");
					um.setContent("");					
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 分享笔记
 */
function createShareNote(){
	//获取当前选中的笔记
	var li = $("#second_side_right .checked").parent();
	var note = li.data("note");
	
	$.post(
			base_path+"/note/share.do",
			note,
			function(result){
				if(result.success){
					$("footer div strong").text("分享成功").parent().fadeIn(100).fadeOut(3000);
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 查询回收站笔记列表
 */
function getRecycleNoteList(){
	alert("查询回收站笔记列表");
}

/***
 * 查看回收站笔记内容
 */
function getRecycleNoteDetail() {
	console.log("查看回收站笔记内容");
}

/***
 * 删除回收站笔记
 */
function deleteRecycleNote(){
	alert("删除回收站笔记");
}

/***
 * 搜索分享笔记列表
 */
function getShareNoteList(condition,currentPage){
	$.post(
			base_path+"/note/search.do",
			{
				"condition":condition,
				"currentPage":currentPage
			},
			function(result){
				if(result.success){
					var list = result.data;
					$(list).each(function(){
						var li = '<li class="online"><a href="#"><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+this.cn_share_title+'<button type="button" class="btn btn-default btn-xs btn_position btn_like"><i class="fa fa-star-o"></i></button><div class="time"></div></a></li>';
						$("#sixth_side_right ul").append(li);
						$("#sixth_side_right ul li:last").data("share",this);
					});					
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 查询分享笔记内容
 */
function getShareNoteDetail(){
	//获取选中的搜索笔记
	var li = $("#sixth_side_right .checked").parent();
	var share = li.data("share");
	//预览
	$("#noput_note_title").text(share.cn_share_title);
	$("#noput_note_body").html(share.cn_share_body);
}

/***
 * 收藏分享笔记
 */
function likeShareNote(shareId,dom){
	//获取选中的搜索笔记
	var li = $("#sixth_side_right .checked").parent();
	var share = li.data("share");
	//收藏
	$.post(
		base_path+"/note/like.do",
		share,
		function(result) {
			if(result.success) {
				$(".cancle").trigger("click");
				$("#sixth_side_right .checked button").fadeOut(600);
			} else {
				alert(result.message);
			}
		}
	);	
}

/***
 * 加载收藏笔记
 */
function getLikeNoteList(likeNoteId){
	alert("加载收藏笔记");
}

/***
 * 查看收藏笔记内容
 */
function getLikeNoteDetail(noteId) {
	console.log("查看收藏笔记内容");
}

/***
 * 删除收藏笔记
 */
function deleteLikeNote(noteId,dom){
	alert("删除收藏笔记");
}

/***
 * 加载本用户参加活动笔记列表
 */
function getNoteActivityList(noteBookId){
	alert("加载本用户参加活动笔记列表");
}

/***
 * 查询参加活动的笔记内容
 */
function getActivityNoteDetail(noteId) {
	console.log("查询参加活动的笔记内容");
}