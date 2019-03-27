/***
 * 加载普通笔记本
 */
function loadNormalNoteBook(){
	console.log("into loadNormalNoteBook");
	$.post(
			base_path+"/notebook/findnormal.do",
			{},
			function(result){
				if(result.success){
					//遍历笔记本，追加到笔记本的ul下
					var list = result.data;
					$(list).each(function(){
						console.info("notebook's name:"+this.cn_notebook_name);
						var li = '<li class="online"><a class="unchecked"><i class="fa fa-book" title="笔记本" rel="tooltip-bottom"></i> '
							+this.cn_notebook_name
							+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button></a></li>';
						$("#first_side_right ul").append(li);	
						
						//将笔记本数据绑定到对应的li上，
						//便于修改、删除时取得笔记本的ID。
						$("#first_side_right ul li:last").data("notebook",this);						
					});
				}
				else{
					alert(result.message);
				}
			}
	);
	console.log("out loadNormalNoteBook");
}

/***
 * 加载特殊笔记本
 */
function loadSpecialNoteBook(){
	$.post(
			base_path+"/notebook/findspecial.do",
			{},
			function(result){
				if(result.success){
					var list = result.data;
					$(list).each(function(){
						if(this.cn_notebook_type_id=='1'){
							//收藏
							$("#like_button").data("notebook",this);
						}
						else if(this.cn_notebook_type_id=='2'){
							//回收站
							$("#rollback_button").data("notebook",this);
						}
						else if(this.cn_notebook_type_id=='3'){
							//活动
							$("#action_button").data("notebook",this);
						}
						else{
							//推送(默认)
							$("#first_side_right ul li:first").data("notebook",this);
						}
					});
					//打印出绑定的特殊笔记本
					console.log($("#like_button").data("notebook").cn_notebook_name);
					console.log($("#rollback_button").data("notebook").cn_notebook_name);
					console.log($("#action_button").data("notebook").cn_notebook_name);
					console.log($("#first_side_right ul li:first").data("notebook").cn_notebook_name);					
				}
				else{
					alert(result.message);
				}
			}
	);
}

/****
 * 添加笔记本
 */
function addNoteBook(name){
	$.post(
			base_path+"/notebook/addNoteBook.do",
			{"noteBookName":name},
			function(result){
				if(result.success){
					var notebook = result.data;
					var li = '<li class="online"><a class="unchecked"><i class="fa fa-book" title="笔记本" rel="tooltip-bottom"></i> '
						+notebook.cn_notebook_name
						+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button></a></li>';
					$("#first_side_right ul").append(li);
					//将笔记本数据绑定到对应的li上，便于修改、删除时取得笔记本的ID。
					$("#first_side_right ul li:last").data("notebook",notebook);
					//关闭弹出框
					$(".cancle").trigger("click");					
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 重命名笔记本
 */
function updateNoteBook(new_name,li){
	var notebook = li.data("notebook");
	notebook.cn_notebook_name = new_name;
	
	$.post(
			base_path+"/notebook/updateNoteBook.do",
			notebook,
			function(result){
				if(result.success){
					//更新li中的显示名
					var txt = '<i class="fa fa-book" title="笔记本" rel="tooltip-bottom"></i> '
						+new_name
						+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button>';
					$("#first_side_right .checked").html(txt);
					//重新绑定数据
					li.data("notebook",notebook);
					//关闭弹出框
					$(".cancle").trigger("click");
				}
				else{
					alert(result.message);
				}
			}
	);
}

/***
 * 删除笔记本
 */
function deleteNoteBook(li){
	var notebook = li.data("notebook");
	// TODO 验证该笔记本下是否包含笔记
	
	$.post(
			base_path+"/notebook/deleteNoteBook.do",
			{
				"id":notebook.cn_notebook_id
			},
			function(result){
				if(result.success){
					//将要删除的笔记本li从ul中移除
					li.remove();
					//关掉弹出框
					$(".cancle").trigger("click");
				}
				else{
					alert(result.message);
				}
			}
	)
}

/**
 * 将笔记本列表放置到select组件中
 */
function setNoteBookToSelect(){
	console.log("将笔记本列表放置到select组件中");
}