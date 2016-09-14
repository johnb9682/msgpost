<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!-- ajax layout which only needs content area -->
<div class="page-header">
	<h1>
		Message
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="alert alert-block alert-success">
			<button type="button" class="close" data-dismiss="alert">
				<i class="ace-icon fa fa-times"></i>
			</button>
			<strong class="green">
				MsgPost
			</strong>
   			Post anything
		</div> 
		
		<div class="row"> 
			<div class="widget-box"> 

				<div class="widget-body">
					<div class="widget-main no-padding"> 
						
						<!-- /section:pages/dashboard.conversations -->
						<form id="messagePostForm" > <!-- role="form" action='${contextPath}/message/operateMessage?oper=add' method='post' -->
							<div class="form-actions"> 
								<div class="input-group">
									<textarea id="messageArea" placeholder="Input messages..." type="text" class="autosize-transition form-control limited" maxlength="255" name="message" /></textarea>
									<span class="input-group-btn">
										<button id="sendButton" class="btn btn-sm btn-info no-radius" type="submit">
											<i class="ace-icon fa fa-share"></i>
											Send
										</button>
									</span>
								</div> 
								<div class="row">
									<label class="block clearfix">
										<span class="block input-icon input-icon-right">
											<span id="loginTip" style="color:#A94442"></span>
										</span>
									</label>
									
								</div>
							</div>
						</form>   
					</div><!-- /.widget-main -->
				</div><!-- /.widget-body -->
			</div><!-- /.widget-box -->
			 
		</div><!-- /.row -->
		
		<div class="row"> 
			<div class="widget-box">
				<div class="widget-header">
					<h4 class="widget-title lighter smaller">
						<i class="ace-icon fa fa-comment blue"></i>
						Message
					</h4>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">
						<!-- #section:pages/dashboard.conversations -->
						<div class="dialogs" id="messagelist"> 
						</div>

						
					</div><!-- /.widget-main -->
				</div><!-- /.widget-body -->
			</div><!-- /.widget-box -->
			 
		</div><!-- /.row -->

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="${contextPath}/static/assets/js/excanvas.js"></script>
<![endif]-->
<script type="text/javascript">
		var scripts = [ null, 
                "${contextPath}/static/assets/js/jquery-ui.custom.js", 
                "${contextPath}/static/assets/js/custom/sendmessage.js", 
                "${contextPath}/static/assets/js/jquery.ui.touch-punch.js",  
                "${contextPath}/static/assets/js/jquery.sparkline.js",
        		"${contextPath}/static/assets/js/flot/jquery.flot.js",  
        		"${contextPath}/static/assets/js/flot/jquery.flot.resize.js", 
        		"${contextPath}/static/assets/js/activities-serverload.js", null ]
		
		function getContextPath() {
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var result = pathName.substr(0, index + 1);
			return result;
		}
		var messageList;  
		$.ajax({
			url : getContextPath() + "/message/getmessage",
			async : false,
			data : {
				limit : 20
			},
			type : 'POST',
			dataType : 'json',
			complete : function(response) {
				var returninfo = eval("(" + response.responseText + ")");
				messageList = returninfo.data;
			}
		}); 
		
		AppendMessage(messageList);
		function AppendMessage(messageList) { 
			for (var i in messageList) {    
			
				var var_time = messageList[i].time;
				var var_name = messageList[i].author;
				var var_message = messageList[i].message;
				var var_messageid = messageList[i].messageid;
				var var_imgpath = messageList[i].imgpath;
				
				var element_div_h = document.createElement("DIV");
					var element_div_h_attr1 = document.createAttribute("class");    
						element_div_h_attr1.value = "itemdiv dialogdiv";
					element_div_h.setAttributeNode(element_div_h_attr1);   
				var element_div_profileimg = document.createElement("DIV");
					var element_div_profileimg_attr1 = document.createAttribute("class");    
						element_div_profileimg_attr1.value = "user";
					element_div_profileimg.setAttributeNode(element_div_profileimg_attr1);  
					var element_img_img = document.createElement("IMG");
						var element_img_img_attr1 = document.createAttribute("alt");    
							element_img_img_attr1.value = "Jim's Avatar"; 
						var element_img_img_attr2 = document.createAttribute("src");    
							element_img_img_attr2.value = getContextPath() + var_imgpath;//"${contextPath}/static/assets/avatars/avatar4.png"; 
						element_img_img.setAttributeNode(element_img_img_attr1);  
						element_img_img.setAttributeNode(element_img_img_attr2); 
					element_div_profileimg.appendChild(element_img_img);  
				var element_div_body = document.createElement("DIV"); 
					var element_div_body_attr1 = document.createAttribute("class");    
						element_div_body_attr1.value = "body";
					element_div_body.setAttributeNode(element_div_body_attr1);   
					//time
					var element_div_body_time = document.createElement("DIV"); 
						var element_div_body_time_attr1 = document.createAttribute("class");    
							element_div_body_time_attr1.value = "time";
						element_div_body_time.setAttributeNode(element_div_body_time_attr1);  
						var element_i_body_time_class = document.createElement("I"); 
							var element_i_body_time_class_attr1 = document.createAttribute("class");    
								element_i_body_time_class_attr1.value = "ace-icon fa fa-clock-o";
							element_i_body_time_class.setAttributeNode(element_i_body_time_class_attr1);  
						var element_span_body_time_span = document.createElement("SPAN"); 
							var element_span_body_time_span_attr1 = document.createAttribute("class");    
								element_span_body_time_span_attr1.value = "grey";
							element_span_body_time_span.setAttributeNode(element_span_body_time_span_attr1);  
							//var t = document.createTextNode("3分钟前");
							var t = document.createTextNode(var_time);
							element_span_body_time_span.appendChild(t);
						element_div_body_time.appendChild(element_i_body_time_class); 
						element_div_body_time.appendChild(element_span_body_time_span); 
					element_div_body.appendChild(element_div_body_time);  
					//name 
					var element_div_body_name = document.createElement("DIV"); 
						var element_div_body_name_attr1 = document.createAttribute("class");    
							element_div_body_name_attr1.value = "name";
						element_div_body_name.setAttributeNode(element_div_body_name_attr1);  
						var element_a_body_name_href = document.createElement("A"); 
							var element_a_body_name_href_attr1 = document.createAttribute("href");    
								element_a_body_name_href_attr1.value = "#";
							element_a_body_name_href.setAttributeNode(element_a_body_name_href_attr1);   
							//var name = document.createTextNode("Jim");
							var name = document.createTextNode(var_name);
							element_a_body_name_href.appendChild(name);
						element_div_body_name.appendChild(element_a_body_name_href);  
					element_div_body.appendChild(element_div_body_name);  
					//message <div class="text">IE8不支持CSS3中的圆角属性。</div> 
					var element_div_body_message = document.createElement("DIV"); 
						var element_div_body_message_attr1 = document.createAttribute("class");    
							element_div_body_message_attr1.value = "text";
						element_div_body_message.setAttributeNode(element_div_body_message_attr1);     
						//var message = document.createTextNode("IE8不支持CSS3中的圆角属性。");
						var message = document.createTextNode(var_message);
						element_div_body_message.appendChild(message); 
					element_div_body.appendChild(element_div_body_message);   
					
					var element_div_body_tool = document.createElement("DIV"); 
						var element_div_body_tool_attr1 = document.createAttribute("class");    
							element_div_body_tool_attr1.value = "tools";
						element_div_body_tool.setAttributeNode(element_div_body_tool_attr1);   
						/*
						var element_div_body_tool_href = document.createElement("A"); 
							var element_div_body_tool_href_attr1 = document.createAttribute("class");    
								element_div_body_tool_href_attr1.value = "btn btn-minier btn-info";
							element_div_body_tool_href.setAttributeNode(element_div_body_tool_href_attr1);   
							var element_div_body_tool_href_attr2 = document.createAttribute("href");    
								element_div_body_tool_href_attr2.value = "#";
							element_div_body_tool_href.setAttributeNode(element_div_body_tool_href_attr2);   
							var element_i_body_tool_i = document.createElement("I"); 
								var element_i_body_tool_i_attr1 = document.createAttribute("class");    
									element_i_body_tool_i_attr1.value = "icon-only ace-icon fa fa-share";
								element_i_body_tool_i.setAttributeNode(element_i_body_tool_i_attr1);    
							element_div_body_tool_href.appendChild(element_i_body_tool_i); 	
							*/
						var element_div_body_tool_href2 = document.createElement("A"); 
							var element_div_body_tool_href2_attr1 = document.createAttribute("class");    
								element_div_body_tool_href2_attr1.value = "btn btn-minier btn-info";
							element_div_body_tool_href2.setAttributeNode(element_div_body_tool_href2_attr1);   
							var element_div_body_tool_href2_attr2 = document.createAttribute("href");    
								element_div_body_tool_href2_attr2.value = getContextPath() + "/message/deleteMessage?id="+var_messageid;
							element_div_body_tool_href2.setAttributeNode(element_div_body_tool_href2_attr2);   
							var element_i_body_tool_i2 = document.createElement("I"); 
								var element_i_body_tool_i2_attr1 = document.createAttribute("class");    
									element_i_body_tool_i2_attr1.value = "icon-only ace-icon fa fa-trash";
								element_i_body_tool_i2.setAttributeNode(element_i_body_tool_i2_attr1);    
							element_div_body_tool_href2.appendChild(element_i_body_tool_i2); 		
							
						//element_div_body_tool.appendChild(element_div_body_tool_href); 
						element_div_body_tool.appendChild(element_div_body_tool_href2);
					element_div_body.appendChild(element_div_body_tool); 
					
					
					
			    element_div_h.appendChild(element_div_profileimg); 
			    element_div_h.appendChild(element_div_body); 	
			    var node = document.getElementById("messagelist"); 
			    node.appendChild(element_div_h); 
			}
    	} 
		
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page 
        	
        });
		
</script>
