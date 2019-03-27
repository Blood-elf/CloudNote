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
function getNoteDetail(){
	console.log("查询普通笔记内容");
}

/***
 * 创建普通笔记
 */
function createNormalNote(){
	alert("创建普通笔记");
}

/***
 * 更新普通笔记
 */
function updateNormalNote(){
	alert("更新普通笔记");
}

/***
 * 删除普通笔记
 */
function deleteNormalNote(){
	alert("删除普通笔记");
}

/***
 * 移动笔记
 */
function moveNote(){
	alert("移动笔记");
}

/***
 * 分享笔记
 */
function createShareNote(){
	$("footer div strong").text("分享成功").parent().fadeIn(100);
	setTimeout(function(){
		$("footer div").fadeOut(500);
	}, 1500);
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
function getShareNoteList(){
	alert("搜索分享笔记列表");
}

/***
 * 查询分享笔记内容
 */
function getShareNoteDetail(){
	alert("查询分享笔记内容");
}

/***
 * 收藏分享笔记
 */
function likeShareNote(shareId,dom){
	alert("收藏分享笔记");
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