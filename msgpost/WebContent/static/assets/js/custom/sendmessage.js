function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
//msgpost_9
jQuery(function($) { 
	
	// 按回车键触发登录事件
	/*
	$(document).keydown(function(event) {
		var key = window.event ? event.keyCode : event.which; 
		if (key == 13) {
			alert("Send message?");
//			$('#messagepost').submit();
			$.ajax({
				dataType : "json",
				url : getContextPath() + "/message/operateMessage",
				type : "post",
				data : {
					oper : 'add',
					message : $('#messageArea').val()
				},
				complete : function(xmlRequest) {
					var returninfo = eval("(" + xmlRequest.responseText + ")");
					alert(returninfo.result);
					if (returninfo.result == 1) {  
						document.getElementById("messageArea").value = "";
						document.location.href = getContextPath() + "/home#page/message"; 
					} else {
						$("#loginTip").html("服务器错误");
					}
				} 
			});
		}
	});
	 */

	// 验证登录表单
	$("#sendButton").bind("click", function() {
//		alert($('#messageArea').val());
		$.ajax({
			dataType : "json",
			url : getContextPath() + "/message/operateMessage",
			type : "post",
			data : {
				oper : 'add',
				message : $('#messageArea').val()
			},
			complete : function(xmlRequest) {
				var returninfo = eval("(" + xmlRequest.responseText + ")");
				if (returninfo.result == 1) {
					document.getElementById("messageArea").value = "";
					document.location.href = getContextPath() + "/home#page/message";
				} else {
					$("#loginTip").html("服务器错误");
				}
			} 
		});
//		$('#messagePostForm').submit();
	});
	 
	$('[data-rel=tooltip]').tooltip({
		container : 'body'
	});
	 
});
