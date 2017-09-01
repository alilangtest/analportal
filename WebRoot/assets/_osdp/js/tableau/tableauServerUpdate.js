function saveTableauConfig(){
	var flag=true;
	var tableauserverip = $("#tableauserverip").val().trim();
	var postgreurl = $("#postgreurl").val().trim();
	var postgreuname = $("#postgreuname").val().trim();
	var postgrepwd = $("#postgrepwd").val().trim();
	if(tableauserverip=='' || tableauserverip==null){
		layer.msg("请填写tableau服务器IP！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return;
	}
	if(postgreurl=='' || postgreurl==null){
		layer.msg("请填写postgreurl！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(postgreuname=='' || postgreuname==null){
		layer.msg("请填写postgre用户名！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(postgrepwd=='' || postgrepwd==null){
		layer.msg("请填写postgre密码！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		flag=false;
		return
	}
	if(flag){
		$('#savePcTableauConfig').form('submit',{
			url : _ctx+'/tableauserver/saveOrUpdate.do',
			success: function(result){
				var result = eval('('+result+')');
				if(result.flag){
					layer.msg('tableau服务器配置信息添加成功', {
						msg : [ '#BB0000' ],
						shift : 5,
						time : 1500
					});
					parent.window.location.reload();
				}else{
					layer.msg('tableau服务器配置信息添加失败', {
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