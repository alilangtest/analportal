$(function(){
	//加载值域
	loadSelectByDomianid();
});
function loadSelectByDomianid(){
	$.getJSON(_ctx + '/tableauuser/querySystemDomainByDomainid.do',
		{},
		function(objJson){
			var hasUserHtml = "";
			var selects = $('#optype1').val();
			var codeid;
			// 动态拼写
			for ( var j = 0; j < objJson.length; j++) {
				var per = objJson[j];
				if(selects == per.codename){
					codeid = per.codeid;
				}
				hasUserHtml += "<option value=\"" + per.codeid + "\">"
						+ per.codename + "</option>";
			}
			$('#optype').html(hasUserHtml);
			$("#optype").val(codeid);
	});
}
//保存tableau服务器配置信息
function saveTableauConfig(){
	var flag=true;
	var username=$('#username').val().trim();
	var password=$('#password').val().trim();
	var projectname=$('#projectname').val().trim();
	var tableauip=$('#tableauip').val().trim();
	var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\:(\d+)$/;
	if(username=='' || username==null){
		layer.msg("请填写tableau用户名！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(password=='' || password==null){
		layer.msg("请填写tableau密码！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(projectname=='' || projectname==null){
		layer.msg("请填写项目名称！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(tableauip=='' || tableauip==null){
		layer.msg("请填写tableau服务器IP！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(!reg.test(tableauip)){
		layer.msg("ip地址格式不正确，请修改!", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(flag){
		$('#savePcTableauConfig').form('submit',{
			url : _ctx+'/tableauuser/saveOrUpdate.do',
			success: function(result){
				var result = eval('('+result+')');
				if(result.flag){
					layer.msg('tableau服务器配置信息修改成功', {
						msg : [ '#BB0000' ],
						shift : 5,
						time : 1500
					});
					parent.window.location.reload();
				}else{
					layer.msg('tableau服务器配置信息修改失败', {
						msg : [ '#BB0000' ],
						shift : 5,
						time : 1500
					});
				}
				
			},error :function(){
				layer.msg('服务器错误', {
					msg : [ '#BB0000' ],
					shift : 5,
					time : 1500
				});
			}
		});
	}
}

function closeTableauConfig(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
    parent.layer.close(index);
}