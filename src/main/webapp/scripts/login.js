/**
 * 页面初始化后，绑定函数。
 */
$(function(){
	//注册
	$("#regist_button").click(function(){
		register();
	});
	
	//登录
	$("#login").click(function(){
		login();
	});
	
	//登出
	$("#logout").click(function(){
		logout();
	});
	
	//修改密码
	$("#changePassword").click(function(){
		changepwd();
	})
	
});

//注册
function register() {
	
	console.log("%c%s","font-size:30px;color:red;text-fill-color:red;-webkit-text-stroke: 1px black;","into register");	
	
	//获取表单中的数据
	var user_name = $("#regist_username").val();
	var nick_name = $("#nickname").val();
	var regist_password = $("#regist_password").val();
	var final_password = $("#final_password").val();
	
	//客户端验证
	//验证用户名，需满足3-20位字母、数字、下划线组合
	var regx = /^\w{3,20}$/;
	if(regx.test(user_name)){
		//用户名验证通过
		$("#warning_1").hide();
	}else{
		//用户名验证失败
		$("#warning_1 span").text("3-20位字母、数字、下划线的组合");
		$("#warning_1").show();
		return;
	}
	
	//验证密码，不能小于6位
	if(regist_password.length >= 6){
		//用户名验证通过
		$("#warning_2").hide();
	}else{
		//用户名验证失败
		$("#warning_2 span").text("密码长度不能小于6位");
		$("#warning_2").show();
		return;
	}
	
	//验证确认密码，需要与密码一致
	if(regist_password == final_password){
		//用户名验证通过
		$("#warning_3").hide();
	}else{
		//用户名验证失败
		$("#warning_3 span").text("2次输入密码不一致");
		$("#warning_3").show();
		return;
	}
	
	//发送注册请求
	console.log("%c%s","font-size:30px;color:green;text-fill-color:red;-webkit-text-stroke: 1px black;","准备发送异步请求");
	//注册成功，跳转到登录页
	$.post(
			base_path+"/login/register.do",
			{"cn_user_name":user_name,
				"cn_user_password":regist_password,
				"cn_user_desc":nick_name},
			function(result) {
				console.log("%c%s","font-size:30px;color:yellow;text-fill-color:red;-webkit-text-stroke: 1px black;","进入回调函数");
				//{"success":true,"message":null,"data":{"pass":false,"error":"aaa"}}
				if(result.success) {
					//程序执行成功
					var map = result.data;
					if(map.pass) {
						alert("注册成功");
						//注册成功，跳转到登录页
						//用js来模拟按钮的单击事件
						$("#back").trigger("click");
					} else {
						//注册失败，给予提示
						alert(map.error);
					}
				} else {
					//程序执行失败
					alert(result.message);
				}
			}
		);
	
}

//登陆
function login() {
	
	console.log("%c%s","font-size:30px;color:red;text-fill-color:red;-webkit-text-stroke: 1px black;","into login");	
	var user_name = $("#count").val();
	var password = $("#password").val();
	
	//客户端验证
	//验证用户名，满足3-20位字母、数字、下划线的组合
	var reg = /^\w{3,20}$/;
	if(!reg.test(user_name)){
		alert("用户名不能为空!");
		return;
	}
	//验证密码，长度不小于6位
	if(password.length<6){
		alert("密码长度不能小于6位");
		return;
	}
	
	console.log("%c%s","font-size:30px;color:red;text-fill-color:red;-webkit-text-stroke: 1px black;","准备发送异步请求");	
	$.post(
			base_path+"/login/checkLogin.do",
			{"userName":user_name,"password":password},
			function(result) {
				console.log("%s%c","font-size:30px;color:red;text-fill-color:red;-webkit-text-stroke: 1px black;","进入回调函数");
				if(result.success){
					//程序执行成功
					//判断是否登陆成功
					var map = result.data;
					if(map.flag==0){
						//登录成功
						alert("登陆成功");
						//将用户信息存入cookie
						addCookie("user_id",map.user.cn_user_id,12);
						addCookie("user_name",map.user.cn_user_name,12);						
						location.href = "edit.html";
					}else if(map.flag==1){
						//用户名错误
						alert("用户名错误");
					}else if(map.flag==2){
						//密码错误
						alert("密码错误");
					}
				}else{
					//程序执行报错
					alert(result.message);
				}
			}
	);
}

/**
 * 退出登录
 */
function logout(){
	console.log("%s%c","font-size:30px;color:blue;text-fill-color:blue;-webkit-text-stroke:1px black;","into logout");
	$.post(
			base_path+"/login/logout.do",
			{},
			function(result){
				console.log("%s%c","font-size:30px;color:blue;text-fill-color:blue;-webkit-text-stroke:1px black;","进入回调函数");
				if(result.success){
					//跳转至登陆页
					location.href = "login.html";
				}
				else{
					alert(result.message);
				}
			}
	);
}

/**
 * 修改密码
 */
function changepwd(){
	
	var last_password = $("#last_password").val();
	var new_password = $("#new_password").val();
	var final_password = $("#final_password").val();
	
	//验证原始密码格式
	if(last_password.length<6){
		$("#warning_1 span").text("原始密码长度不能小于6位");
		$("#warning_1").show();
		return;
	}
	else{
		$("#warning_1").hide();
	}
	
	//验证新密码格式
	if(new_password.length<6){
		$("#warning_2 span").text("新密码长度不能小于6位");
		$("#warning_2").show();
		return;
	}
	else{
		$("#warning_2").hide();
	}
	
	//验证确认新密码
	if(final_password!=new_password){
		$("#warning_3 span").text("2次输入的新密码不一致");
		$("#warning_3").show();
		return;
	}
	else{
		$("#warning_3").hide();
	}
	
	console.log("准备发送异步请求");
	$.post(
			base_path+"/login/updatePassword.do",
			{"oldPassword":last_password,
				"newPassword":new_password},
			function(result) {
				console.log("进入回调函数");
				if(result.success) {
					//判断密码是否修改成功
					if(result.data) {
						//密码修改成功，注销
						logout();
					} else {
						//密码修改失败，原密码错误
						$("#warning_1 span").text("原密码错误");
						$("#warning_1").show();
					}
				} else {
					alert(result.message);
				}
			}
		);
	
}


