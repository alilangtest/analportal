var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.updateReportMenu=_ctx + '/queryReportMenu/updateReportMenu.do';
URLS.checkReportMenu=_ctx + '/queryReportMenu/checkReportMenu.do';
	//保存
	function saveUpdateReportMenu(){
		$('#updateReportMenuForm').form('submit',{
			url:URLS.updateReportMenu,
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
					layer.msg("修改菜单信息保存成功",{icon:1},function(){
		                parent.location.reload(); // 父页面刷新
		                closeReportMenu();
		            });
					
				} else {
					layer.msg('修改菜单失败！', {
						msg : [ '#BB0000' ],
						time : 1500
					});
				}
			},
			error :function(){
				layer.msg('路径未知或请求未响应！', {
					msg : [ '#BB0000' ],
					time : 1500
				});
			}
		});
	}
$(function(){
	EL.ordinal=$('#ordinal');
	EL.savebutton=$('#savebutton');
	EL.ordinal.blur(function(){
		var reg=new RegExp("^[0-9]*$");
		//排序号校验
		var ordinal = $('#ordinal').val();
		if(ordinal == $('#ordinal_hidden').val()){
			$('#ordinal_').html('');
			EL.savebutton.prop("disabled",false);
			return;
		}
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
});
	function closeReportMenu(){
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
	        parent.layer.close(index);
	}
	
	//------------------------------------------------------------

