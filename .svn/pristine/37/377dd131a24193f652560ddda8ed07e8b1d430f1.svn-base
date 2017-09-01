var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS|| {};

URLS.showlist = _ctx + '/tableauserver/pageQuery.do';//liebiao展示
URLS.add = _ctx + '/tableauserver/tableauServerAdd.html';
URLS.update = _ctx + '/tableauserver/tableauServerUpdate.html';
URLS.view = _ctx + '/tableauserver/tableauServerView.html';
URLS.removes = _ctx + '/tableauserver/tableauServerRemove.do';

$(function() {
	// 页面控件
	EL.oGrid = $('#oGrid');
	EL.searchs = $('#searchs');

	// 初始加载 无查询条件
	EL.oGrid.datagrid({
		url : URLS.showlist,
		nowrap : false,
		queryParams : {
			filters : __.encode({
				'postgreuname' : ''
			})
		},
		striped : true,
		border : true,
		loadMsg : '数据加载中,请稍后……',
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		idField : 'serverid',
		singleSelect : false,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		contentListToolbar : true,
		collapsible : true,
		fitColumns : true,
		checkOnSelect : true,
		selectOnCheck : true,
		border : true,
		fit : true,// 自动大小
		columns : [ [ {
			field : '-ck',
			checkbox : true
		}, {
			field : 'SERVERID',
			title : '操作',
			hidden : true,
		}, {
			field : 'TABLEAUSERVERIP',
			title : 'postgre服务器IP',
			align : 'center',
			width : 200
		}, {
			field : 'POSTGREURL',
			title : 'postgreURL',
			width : 200,
			align : 'center'
		}, {
			field : 'POSTGREUNAME',
			title : 'postgre用户名',
			width : 200,
			align : 'center'
		}, {
			field : 'POSTGREPWD',
			title : 'postgre密码',
			width : 200,
			align : 'center'
		} ] ],
	/*	toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : toAdd
		} , {
			text : '修改',
			iconCls : 'icon-add',
			handler : toUpdate
		}, {
			text : '查看',
			iconCls : 'icon-add',
			handler : toView
		}, {
			text : '删除',
			iconCls : 'icon-add',
			handler : toRemoves
		} ]*/
	});

});
/*
 * 查询
 */
function searchs(){
	var postgreuname = $('#postgreuname1').val();
	EL.oGrid.datagrid('reload',{
		filters : __.encode({
			'postgreuname' : postgreuname
		})
	});
}
/*
 * 清空查询框
 */
function empty(){
	$('#postgreuname1').val('');
	searchs();
}
/*
 * 去新增用户
 */
/*
 * 去新增用户
 */
function toAdd() {
	//EL.form[0].reset();
	// iframe窗
	layer.open({
	      type: 2,
	      title: '新增',
	      shadeClose: true,
	      shade: 0.3,
	      maxmin: true, //开启最大化最小化按钮
	      area: ['650px', '240px'],
	      content: URLS.add
	 });
}
/*
 * 去修改用户
 */
function toUpdate() {
	var rows = EL.oGrid.datagrid('getSelections');
	if (rows.length == 1){		
		var row = EL.oGrid.datagrid('getSelected');
		var serverid =row.SERVERID;
		var tableauserverip =row.TABLEAUSERVERIP;
		var postgreurl =row.POSTGREURL;
		var postgreuname =row.POSTGREUNAME;
		var postgrepwd =row.POSTGREPWD;
		// iframe窗
		layer.open({
		      type: 2,
		      title: '修改',
		      shadeClose: true,
		      shade: 0.3,
		      maxmin: true, //开启最大化最小化按钮
		      area: ['650px', '240px'],
		      content: URLS.update,
		      success: function(layero, index){
		    	    var body = layer.getChildFrame('body', index);
		    	    body.contents().find("#serverid").val(serverid);
		    	    body.contents().find("#tableauserverip").val(tableauserverip);
		    	    body.contents().find("#postgreurl").val(postgreurl);
		    	    body.contents().find("#postgreuname").val(postgreuname);
		    	    body.contents().find("#postgrepwd").val(postgrepwd);
		    	  }
		 });
	}else{
		layer.msg('请选择一条数据', {
			msg : [ '#BB0000' ],
			shift : 5,
			time : 1500
		});
	}
}
/*
 * 去查看用户
 */
function toView() {
	var rows = EL.oGrid.datagrid('getSelections');
	if (rows.length == 1){		
		var row = EL.oGrid.datagrid('getSelected');
		var serverid =row.SERVERID;
		var tableauserverip =row.TABLEAUSERVERIP;
		var postgreurl =row.POSTGREURL;
		var postgreuname =row.POSTGREUNAME;
		var postgrepwd =row.POSTGREPWD;
		// iframe窗
		layer.open({
		      type: 2,
		      title: '查看',
		      shadeClose: true,
		      shade: 0.3,
		      maxmin: true, //开启最大化最小化按钮
		      area: ['650px', '240px'],
		      shadeClose: true, //开启遮罩关闭
		      content: URLS.view,
		      success: function(layero, index){
		    	    var body = layer.getChildFrame('body', index);
//		    	    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
//		    	    console.log(body.html()); //得到iframe页的body内容
//		    	    body.find('input').val(serverid);
		    	    body.contents().find("#serverid").val(serverid);
		    	    body.contents().find("#tableauserverip").val(tableauserverip);
		    	    body.contents().find("#postgreurl").val(postgreurl);
		    	    body.contents().find("#postgreuname").val(postgreuname);
		    	    body.contents().find("#postgrepwd").val(postgrepwd);
//		    	    document.getElementById("serverid").value=serverid;
		    	  }
		 });
	}else{
		layer.msg('请选择一条数据', {
			msg : [ '#BB0000' ],
			shift : 5,
			time : 1500
		});
	}
}
function toRemoves(){
	var rows = EL.oGrid.datagrid('getChecked');
	if (rows.length > 0){
		var ids="";
		for(var i=0;i<rows.length;i++){
			ids += rows[i].SERVERID+",";
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length-1);
		}
		layer.confirm("确认删除 "+rows.length+" 个消息？", function(){
		    //删除成功
			var postUrl = URLS.removes;
			$.post(postUrl,{"ids":ids},function(result){
				if (result.flag){
					EL.oGrid.datagrid('reload');	// reload the user data
					EL.oGrid.datagrid('clearChecked'); //clear selected options
					EL.oGrid.datagrid('clearSelections'); //clear selected options
					layer.msg('删除成功', {
						msg : [ '#BB0000' ],
						shift : 5,
						time : 1500
					});
				} else {
					layer.msg('删除失败', {
						msg : [ '#BB0000' ],
						shift : 5,
						time : 1500
					});
				}
			},'json');
			EL.oGrid.datagrid('refreshRow');
		});
	}else{
		layer.msg('请选择要删除数据', {
			msg : [ '#BB0000' ],
			shift : 5,
			time : 1500
		});
	}
}