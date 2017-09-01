var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.saveReportMenu=_ctx + '/reportMenu/saveReportMenu.do';
URLS.checkReportMenu=_ctx + '/reportMenu/checkReportMenu.do';

$(function() {
	EL.body = $(document.body);
	EL.menuparent = $("#menuparent");
	EL.addReportMenuForm=$('#addReportMenuForm');
	EL.menuid_=$('#menuid_');
	EL.menuid=$('#menuid');
	EL.savebutton=$('#savebutton');
	EL.closebutton=$('#closebutton');
	EL.menuname_=$('#menuname_');
	EL.menuname=$('#menuname');
	
	EL.ordinal=$('#ordinal');
	
	//------------------------------------------------------
	if(menuparent!=null&&menuparent!='null'){
		$("#menuparent").val(menuparent);
	}
	
	
		//保存
		EL.savebutton.click(function(){
			EL.addReportMenuForm.form('submit',{
				url: URLS.saveReportMenu,
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						layer.msg("新增菜单信息保存成功",{icon:1},function(){
			                parent.location.reload(); // 父页面刷新
			                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			                parent.layer.close(index);
			            });
					} else {
						layer.msg("菜单编号或菜单名称已存在不能重复添加！", {
							msg : [ '#BB0000' ],
							time : 2000
						});
					}
				},
				error :function(){
					layer.msg("路径未知或请求未响应！", {
						msg : [ '#BB0000' ],
						time : 2000
					});
				}
			});
		})
		
		
   EL.menuid.blur(function(){
			var menuid = EL.menuid.val();
			if(menuid!="" && menuid!=null){
				var reg=new RegExp("^[0-9]*$");
				if(reg.test(menuid)){
					EL.menuid_.html('');
					$.ajax({
						type:"post",
						url:URLS.checkReportMenu,
						data:{'menuid' : menuid},
						async:false,
						dataType:"json",
						success:function(data){
							if(data=="0"){
								EL.menuid_.html('可用');
								 $('#menuname_').html('');
								var menuname = $('#menuname').val();
								if(menuname!="" && menuname!=null){
									$.ajax({
										type:"post",
										url:URLS.checkReportMenu,
										data:{'menuname' : menuname},
										async:false,
										dataType:"json",
										success:function(data){
											if(data=="0"){
												$('#menuname_').html('可用');
												EL.savebutton.prop("disabled",false);
											}
											if(data=="1"){
												$('#menuname_').html('重复');
												EL.savebutton.prop("disabled",true);
											}
										},
										error:function(){
											
										}
									});
								}else{
									$('#menuname_').html('');
								} 
							}
							if(data=="1"){
								EL.menuid_.html('重复');
								EL.savebutton.prop("disabled",true);
							}
						},
						error:function(){
							
						}
					});
				}else{
					EL.menuid_.html('输入数字');
					EL.savebutton.prop("disabled",true);
				}
			}else{
				EL.menuid_.html('');
			}
		})
EL.ordinal.blur(function(){
	var reg=new RegExp("^[0-9]*$");
	//排序号校验
	var ordinal = $('#ordinal').val();
	if(ordinal != "" && ordinal != null){
		if(reg.test(ordinal)){
			checkOrdinal(ordinal);
		}else{
			$('#ordinal_').html('输入数字');
			EL.savebutton.prop("disabled",true);
		}
	}
});
function checkOrdinal(ordinal){
	$.ajax({
		type:"post",
		url:URLS.checkReportMenu,
		data:{'ordinal' : ordinal},
		async:false,
		dataType:"json",
		success:function(data){
			if(data=="0"){
				$('#ordinal_').html('可用');
				EL.savebutton.prop("disabled",false);
			}
			if(data=="1"){
				$('#ordinal_').html('重复');
				EL.savebutton.prop("disabled",true);
			}
		},
		error:function(){
			
		}
	});
}
		EL.menuname.blur(function(){
			EL.menuname_.html('');
			var menuname = EL.menuname.val();
			if(menuname!="" && menuname!=null){
				$.ajax({
					type:"post",
					url:URLS.checkReportMenu,
					data:{'menuname' : menuname},
					async:false,
					dataType:"json",
					success:function(data){
						if(data=="0"){
							EL.menuname_.html('可用');
							//EL.savebutton.linkbutton('enable');
							EL.menuid_.html('');
							//$('#savebutton').linkbutton('enable');
							var menuid = EL.menuid.val();
							if(menuid!="" && menuid!=null){
								var reg=new RegExp("^[0-9]*$");
								if(reg.test(menuid)){
									EL.menuid_.html('');
									//EL.savebutton.linkbutton('enable');
									$.ajax({
										type:"post",
										url:URLS.checkReportMenu,
										data:{'menuid' : menuid},
										async:false,
										dataType:"json",
										success:function(data){
											if(data=="0"){
												EL.menuid_.html('可用');
												EL.savebutton.prop("disabled",false); 
												//EL.savebutton.linkbutton('enable');
											}
											if(data=="1"){
												EL.menuid_.html('重复');
												EL.savebutton.prop("disabled",true);
												//EL.savebutton.linkbutton('disable');
											}
										},
										error:function(){
											
										}
									});
								}else{
									EL.menuid_.html('输入数字');
									EL.savebutton.prop("disabled",true);
								}
							}else{
								EL.menuid_.html('');
							}
						}
						if(data=="1"){
							EL.menuname_.html('重复');
							EL.savebutton.prop("disabled",true);
						}
					},
					error:function(){
					}
				});
			}else{
				EL.menuname_.html('');
			}
		})
		
		
	//关闭窗口	
	EL.closebutton.click(function(){
	 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
        parent.layer.close(index);
	})
	//------------------------------------------------------------
		
});

