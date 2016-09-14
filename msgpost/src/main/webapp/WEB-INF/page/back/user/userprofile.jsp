<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery.gritter.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/select2.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/bootstrap-editable.css" />

<!-- ajax layout which only needs content area -->
<div class="page-header">
	<h1>
		Personal Information
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="clearfix">
			<div class="pull-left alert alert-success no-margin">
				<button type="button" class="close" data-dismiss="alert">
					<i class="ace-icon fa fa-times"></i>
				</button>

				<i class="ace-icon fa fa-umbrella bigger-120 blue"></i>
				Click your profile image ... &nbsp;&nbsp;
			</div> 
		</div>

		<div class="hr dotted"></div>
 

		<div >
			<div id="user-profile-3" class="user-profile row">
				<div class="col-sm-offset-1 col-sm-10"> 

					<div class="space"></div>

					<form class="form-horizontal">
						<div class="tabbable">
							<ul class="nav nav-tabs padding-16">
								<li class="active">
									<a data-toggle="tab" href="#edit-basic">
										<i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
										Basic Information
									</a>
								</li>

								<li>
									<a data-toggle="tab" href="#edit-password">
										<i class="blue ace-icon fa fa-key bigger-125"></i>
										Change Password
									</a>
								</li>
							</ul>

							<div class="tab-content profile-edit-tab-content">
								<div id="edit-basic" class="tab-pane in active">
								
									<h4 class="header blue bolder smaller">Profile Image</h4> 

									<div class="space-4"></div> 
									
									<div class="center"> 
										<div>
											<!-- #section:pages/profile.picture -->
											<span class="profile-picture">
												<img id="avatar" class="editable img-responsive" alt="Alex's Avatar" src="${contextPath}${user.imgpath}" />
											</span>
					
											<!-- /section:pages/profile.picture -->
											<div class="space-4"></div> 
										</div>   
									</div>
									
									<h4 class="header blue bolder smaller">Basic Information</h4>

									<div class="row"> 
									
									<div class="space"></div>
									
										<div class="vspace-12-sm"></div>

										<div class="col-xs-12 col-sm-8">
											<div class="form-group">
												<label class="col-sm-4 control-label no-padding-right" for="form-field-userName">Username</label>

												<div class="col-sm-8">
													<input class="col-xs-12 col-sm-10" type="text" id="form-field-userName" placeholder="姓名" value="${user.username}" /> 
												</div>
											</div>
											
											<div class="space-4"></div>
											 											
										</div>
									</div>

									<div class="space"></div>
									

								</div>

								<div id="edit-password" class="tab-pane left">
									<div class="space-10"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-pass1"  >New Password</label>

										<div class="col-sm-9">
											<input type="password" id="form-field-pass1" value ="" />
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-pass2">Confirm New Password</label>

										<div class="col-sm-9">
											<input type="password" id="form-field-pass2" value ="" />
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button id="submitButton" class="btn btn-info" type="button">
									<i class="ace-icon fa fa-check bigger-110"></i>
									Save
								</button>
								&nbsp; &nbsp;
								<button class="btn" type="reset">
									<i class="ace-icon fa fa-undo bigger-110"></i>
									Reset
								</button>
							</div>
						</div>
					</form>
				</div><!-- /.span -->
			</div><!-- /.user-profile -->
		</div>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="${contextPath}/static/assets/js/excanvas.js"></script>
<![endif]-->
<script type="text/javascript">
	var scripts = [null,"${contextPath}/static/assets/js/jquery-ui.custom.js",
	               "${contextPath}/static/assets/js/jquery.ui.touch-punch.js",
	               "${contextPath}/static/assets/js/jquery.gritter.js",
	               "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
	               "${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js",
	               "${contextPath}/static/assets/js/select2.js",
	               "${contextPath}/static/assets/js/x-editable/bootstrap-editable.js",
	               "${contextPath}/static/assets/js/x-editable/ace-editable.js", null]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	    //inline scripts related to this page
	    jQuery(function($) {

	        //editables on first profile page
	        $.fn.editable.defaults.mode = 'inline';
	        $.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
	        $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>' +
	            '<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';

	        //select2 editable 实现网页文本即时编辑
	        /*
	        $('#userName').editable({
                type: 'text',
                pk: ${id},
                name: 'userName',
                url: "${contextPath}" + "/sys/sysuser/updateSysUserField"
            });  
	        */ 
	        
	     // *** editable avatar *** //
	        try { 
	        	//ie8 throws some harmless exceptions, so let's catch'em
	            //first let's add a fake appendChild method for Image element for browsers that have a problem with this
	            //because editable plugin calls appendChild, and it causes errors on IE at unpredicted points
	            try {
	                document.createElement('IMG').appendChild(document.createElement('B'));
	            } catch (e) {
	                Image.prototype.appendChild = function(el) {}
	            }

	            var last_gritter;
	            $('#avatar').editable({
	                type: 'image',
	                name: 'avatar',
	                value: null,
	                image: {
	                    //specify ace file input plugin's options here
	                    btn_choose: 'Click to change image',
	                    droppable: true,
	                    maxSize: 2097152, //~100Kb
	                    //and a few extra ones here
	                    name: 'avatar', //put the field name here as well, will be used inside the custom plugin
	                    on_error: function(error_type) { //on_error function will be called when the selected file has a problem
	                        if (last_gritter) $.gritter.remove(last_gritter);
	                        if (error_type == 1) { //file format error
	                            last_gritter = $.gritter.add({
	                                title: '文件格式不正确!',
	                                text: '请选择  jpg|gif|png 格式的图片!',
	                                class_name: 'gritter-error gritter-center'
	                            });
	                        } else if (error_type == 2) { //file size rror
	                            last_gritter = $.gritter.add({
	                                title: '文件太大!',
	                                text: '图片大小不能超过2mb!',
	                                class_name: 'gritter-error gritter-center'
	                            });
	                        } else { //other error
	                        }
	                    },
	                    on_success: function() {
	                        $.gritter.removeAll();
	                    }
	                },
	                url: function(params) {
	                    //for a working upload example you can replace the contents of this function with 
	                    //examples/profile-avatar-update.js

	                    //this is similar to the file-upload.html example
						//replace the code inside profile page where it says ***UPDATE AVATAR HERE*** with the code below
						// ***UPDATE AVATAR HERE*** //
						var submit_url = "${contextPath}" + "/user/uploadAttachement";//please modify submit_url accordingly
						var deferred = null;
						var avatar = '#avatar';
						
						//if value is empty (""), it means no valid files were selected
						//but it may still be submitted by x-editable plugin
						//because "" (empty string) is different from previous non-empty value whatever it was
						//so we return just here to prevent problems
						var value = $(avatar).next().find('input[type=hidden]:eq(0)').val();
						/////////////////////////////////
						alert(value);
						if(!value || value.length == 0) {
							/* deleted
							deferred = new $.Deferred();
							deferred.resolve();
							return deferred.promise();
							*/
							alert("Deferred Staff");
						}
						
						var $form = $(avatar).next().find('.editableform:eq(0)')
						var file_input = $form.find('input[type=file]:eq(0)');
						var pk = $(avatar).attr('data-pk');//primary key to be sent to server
						
						var ie_timeout = null
						////////////////////////////////
						alert(file_input);
						////////////////////////////////
						alert(pk);
						
						if( "FormData" in window ) {
							alert("FormData in window");
							var formData_object = new FormData();//create empty FormData object
							
							//serialize our form (which excludes file inputs)
							$.each($form.serializeArray(), function(i, item) {
								//add them one by one to our FormData 
								formData_object.append(item.name, item.value);							
							});
							//and then add files
							$form.find('input[type=file]').each(function(){
								var field_name = $(this).attr('name');
								var files = $(this).data('ace_input_files');
								if(files && files.length > 0) {
									formData_object.append(field_name, files[0]);
								}
							});
						
							//append primary key to our formData
							formData_object.append('pk', pk);
						
							deferred = $.ajax({
										url: submit_url,
									   type: 'POST',
								processData: false,//important
								contentType: false,//important
								   dataType: 'json',//server response type
									   data: formData_object
							})
						}
						else {
							alert("FormData not in window");
							deferred = new $.Deferred();
						
							var temporary_iframe_id = 'temporary-iframe-'+(new Date()).getTime()+'-'+(parseInt(Math.random()*1000));
							var temp_iframe = 
									$('<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'" \
									frameborder="0" width="0" height="0" src="about:blank"\
									style="position:absolute; z-index:-1; visibility: hidden;"></iframe>')
									.insertAfter($form);
									
							$form.append('<input type="hidden" name="temporary-iframe-id" value="'+temporary_iframe_id+'" />');
							
							//append primary key (pk) to our form
							$('<input type="hidden" name="pk" />').val(pk).appendTo($form);
							
							temp_iframe.data('deferrer' , deferred);
							//we save the deferred object to the iframe and in our server side response
							//we use "temporary-iframe-id" to access iframe and its deferred object
						
							$form.attr({
									  action: submit_url,
									  method: 'POST',
									 enctype: 'multipart/form-data',
									  target: temporary_iframe_id //important
							});
						
							$form.get(0).submit();
						
							//if we don't receive any response after 30 seconds, declare it as failed!
							ie_timeout = setTimeout(function(){
								ie_timeout = null;
								temp_iframe.attr('src', 'about:blank').remove();
								deferred.reject({'status':'fail', 'message':'Timeout!'});
							} , 30000);
						}
						
						
						//deferred callbacks, triggered by both ajax and iframe solution
						deferred.done(function(result) {//success
							var res = result;//the `result` is formatted by your server side response and is arbitrary
							if(res.status == 'OK') $(avatar).get(0).src = res.url;
							else alert(res.message);
						}).fail(function(result) {//failure
							alert("There was an error");
						}).always(function() {//called on both success and failure
							if(ie_timeout) clearTimeout(ie_timeout)
							ie_timeout = null;	
						});
						
						return deferred.promise();
	                    // ***END OF UPDATE AVATAR HERE*** //
	                },
	                success: function(response, newValue) {
	                	
	                }
	            })
	        } catch (e) {
	        	
	        }

	        /**
			//let's display edit mode by default?
			var blank_image = true;//somehow you determine if image is initially blank or not, or you just want to display file input at first
			if(blank_image) {
				$('#avatar').editable('show').on('hidden', function(e, reason) {
					if(reason == 'onblur') {
						$('#avatar').editable('show');
						return;
					}
					$('#avatar').off('hidden');
				})
			}
			*/
 

	        ///////////////////////////////////////////
	        $('#user-profile-3').find('input[type=file]').ace_file_input({
                style: 'well',
                btn_choose: 'Change avatar',
                btn_change: null,
                no_icon: 'ace-icon fa fa-picture-o',
                thumbnail: 'large',
                droppable: true,
                allowExt: ['jpg', 'jpeg', 'png', 'gif'],
                allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
            }).end().find('button[type=reset]').on(ace.click_event, function() {
                $('#user-profile-3 input[type=file]').ace_file_input('reset_input');
            }).end().find('.date-picker').datepicker().next().on(ace.click_event, function() {
                $(this).prev().focus();
            })

	        $('#user-profile-3').find('input[type=file]').ace_file_input('show_file_list', [{
	            type: 'image',
	            name: $('#avatar').attr('src')
	        }]);
	        
			////////////////////
			$("a[href='#edit-password']").on('shown.bs.tab', function (e) {
			});
			$('#submitButton').on('click', function() {
				if($('#form-field-pass1').val() != '' && ($('#form-field-pass1').val().length < 6 || $('#form-field-pass1').val().length > 14)){
					$.gritter.add({
		                title: 'System Message',
		                text: 'Password length should in between 6 to 14 characters',
		                class_name: 'gritter-warning gritter-center'
		            });
				}else if($('#form-field-pass1').val() != '' && $('#form-field-pass1').val() != $('#form-field-pass2').val()){
					$.gritter.add({
		                title: 'System Message',
		                text: 'New password different from confirm password，Please type in again',
		                class_name: 'gritter-warning gritter-center'
		            });
				}else if($('#form-field-pass1').val() != '' && $('#form-field-pass1').val() == $('#form-field-pass2').val()){
					$.ajax({
						dataType : "json",
						url : "${contextPath}" + "/user/resetPassword",
						type : "post",
						data : {
							password : $('#form-field-pass1').val()
						},
						complete : function(xmlRequest) {
							
							document.getElementById("form-field-pass1").value = "";
							document.getElementById("form-field-pass2").value = ""; 
							$.gritter.add({
				                title: 'System Message',
				                text: 'Password Successfully Changed',
				                class_name: 'gritter-success gritter-center'
				            });
						}
					});
				}else{
					$.ajax({
						dataType : "json",
						url : "${contextPath}" + "/user/saveUserProfile",
						type : "post",
						data : {  
							username : $('#form-field-userName').val(), 
						},
						complete : function(xmlRequest) {
							$.gritter.add({
				                title: 'System Message',
				                text: 'Basic Information Successfully Changed',
				                class_name: 'gritter-success gritter-center'
				            });
						}
					});					
				}
			}); 

	        /////////////////////////////////////
	        $(document).one('ajaxloadstart.page', function(e) {
	            //in ajax mode, remove remaining elements before leaving page
	            try {
	                $('.editable').editable('destroy');
	            } catch (e) {}
	            $('[class*=select2]').remove();
	        });
	    });

	});
</script>
