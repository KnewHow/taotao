var TT = TAOTAO = {
	checkLogin : function(){
		var token = $.cookie("TT_TOKEN");
		if(!token){
			return ;
		}
		$.ajax({
			url : "http://sso.taotao.com:81/service/user/" + token,
			dataType : "jsonp",
			type : "GET",
			success : function(_data){
				if(_data){
					var html =_data.username+"，欢迎来到淘淘！<a href=\"http://sso.taotao.com:81/service/user/logout\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});