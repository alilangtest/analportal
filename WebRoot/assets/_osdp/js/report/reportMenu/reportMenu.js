var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.reportMenuGrid=_ctx + '/reportMenu/listReportMenu.do';
URLS.viewReportMenu=_ctx + '/reportMenu/viewReportMenu.html';
URLS.updateReportMenu=_ctx + '/reportMenu/updateReportMenu.html';
URLS.addReportMenu=_ctx + '/reportMenu/addReportMenu.html';
URLS.relateRole=_ctx + '/reportMenu/relateRole.html';
URLS.getReportMenu=_ctx + '/reportMenu/getReportMenu.do';
URLS.deleteReportMenu=_ctx + '/reportMenu/deleteReportMenu.do';
EL.userid=userid;
$(function() {
	EL.body = $(document.body);
	EL.reportMenuGrid = $('#reportMenuGrid');
	EL.viewReportMenu=$("#viewReportMenu");
	EL.updateReportMenu=$("#updateReportMenu");
	EL.addReportMenu=$("#addReportMenu");
	EL.deleteReportMenu=$("#deleteReportMenu");
	EL.relateRole=$("#relateRole");
	EL.gridWrap = $("#grid-wrap");
	/*EL.btn2 = $('#btn2');
	EL.btn3 = $('#btn3');
	EL.inputword=$('#qusdesp');*/
	// 加载问题列表
	__.mask();
	
		EL.reportMenuGrid.treegrid({    
			height:EL.body.height()-$('#userquery').height(),
		    url:URLS.reportMenuGrid,    
		    idField:'menuid',
		    animate: true,
		    treeField:'menuname',
		    fit:true,
		    fitColumns: true,
		    columns:[[
		              /* {field:'ck',checkbox:true}, */   
			          {field:'menuid',title:'菜单编号',width:100,sortable:true},
					  {field:'menuname',title:'菜单名称',width:250,sortable:true},
					  /* {field:'menuparent',title:'上级菜单',width:250}, */
					  {field:'menudesp',title:'菜单描述',width:300},
					  {field:'ordinal',title:'排序号',width:100,sortable:true},
					  {field:'rem',title:'备注',width:250}, 
					  {
							field : '_operation',
							title : '操作',
							fixed : true,
							width : 250,
							halign : 'center',
							align : 'center',
							formatter : function(value, row) {
								var html = '';
								html += '<div data-record="' + $.encodeHTML($.encode(row)) + '">';
								html += ' <a data-invoke="edit" class="x-link">编辑</a>';
								var menuparent = row.menuparent;
								if(menuparent != null && menuparent != ""){
									html += ' <a class="x-link x-disable">创建下级</a>';
								}else{
									html += ' <a data-invoke="childran" class="x-link">创建下级</a>';
								}
								if ($.isEmpty(row.children)) {
									
									html += ' <a data-invoke="remove" class="x-link">删除</a>';
								} else {
									
									html += ' <a class="x-link x-disable">删除</a>';
								}
								html += ' <a data-invoke="viewReportMenu" class="x-link">查看</a>';
								html += ' <a data-invoke="relateRole" class="x-link">授权</a>';
								html += '</div>';
								return html;
							}
						}
		    		]],
		    
	   		 onLoadSuccess:function(data){
	   			__.unmask();
	   			//EL.reportMenuGrid.treegrid('clearSelections'); //clear selected options
	   	    }
		});
	
		EL.gridWrap.on('click', '[data-invoke]', function(e) {
			e.stopPropagation();
			var el = $(this), //
			invoke = el.attr('data-invoke'), //
			record = $.decode(el.parent().attr('data-record')), //
			id = record.id, name = record.name, namePath = record.namePath;
			//编辑
			if ('edit' === invoke) {
				var rows = EL.reportMenuGrid.treegrid("getSelections");
				if(rows.length == 1){
					var row = EL.reportMenuGrid.treegrid("getSelected");
					url=URLS.updateReportMenu+"?menuid="+row.menuid;
					layer.open({
					      type: 2,
					      title: '修改',
					     // shadeClose: false,
					     // shade: false,
					      maxmin: true, //开启最大化最小化按钮
					      area: ['730px', '300px'],
					      content: url,
					      btns: 0/*,
					      btn: ['保存','关闭']*/
					 });
				} else {
					layer.msg('请选择一条数据', {
						msg : [ '#BB0000' ],
						time : 1500
					});
				}
			}else if ('childran' === invoke) {
				var rows = EL.reportMenuGrid.treegrid("getSelections");
				if(rows.length > 1) {
					layer.msg('请选择一条数据！', {
						msg : [ '#BB0000' ],
						time : 1500
					});
				} else if(rows.length == 1){
					var row = EL.reportMenuGrid.treegrid("getSelected");
					var menuparent = row.menuparent;
					if(menuparent !=null && menuparent!=""){
						layer.msg('不能创建三级菜单！', {
							msg : [ '#BB0000' ],
							time : 1500
						});
					}else{
						url=URLS.addReportMenu+"?menuid="+row.menuid;
						//top.getDlg(url,"addReportMenuFrame","新增菜单",730,332,true);
						layer.open({
						      type: 2,
						      title: '新增',
						     // shadeClose: false,
						     // shade: false,
						      maxmin: true, //开启最大化最小化按钮
						      area: ['730px', '300px'],
						      content: url,
						      btns: 0/*,
						      btn: ['保存','关闭']*/	
					  });
					}
				} else {
					url=URLS.addReportMenu;
					//top.getDlg(url,"addReportMenuFrame","新增菜单",730,332,true);
					layer.open({
					      type: 2,
					      title: '新增',
					     // shadeClose: false,
					     // shade: false,
					      maxmin: true, //开启最大化最小化按钮
					      area: ['730px', '300px'],
					      content: url,
					 });
				}
			}else if('remove' === invoke){
				var rows = EL.reportMenuGrid.treegrid("getSelections");
				if (rows.length > 0){
					var ids="";
					for(var i=0;i<rows.length;i++){
						if(i<rows.length-1){
							ids =ids+rows[i].menuid+",";
						} else{
							ids +=rows[i].menuid;
						}
					}
					layer.confirm("确认删除 "+rows.length+" 个菜单？", function(r){
					//$.messager.confirm("删除菜单","确认删除 "+rows.length+" 个菜单？",function(r){
						if (r){
							var postUrl=URLS.getReportMenu;
							$.post(postUrl,{"ids":ids,"rd":Math.random()},function(result){
								if (result.success){
									var postUrl=URLS.deleteReportMenu;
									$.post(postUrl,{"ids":ids,"rd":Math.random()},function(result){
										if (result.success){
											EL.reportMenuGrid.treegrid('reload');	
											EL.reportMenuGrid.treegrid('clearSelections'); 
											layer.msg(result.msg, {
												msg : [ '#BB0000' ],
												shift : 5,
												time : 1500
											});
										} else {
											layer.msg(result.msg, {
												msg : [ '#BB0000' ],
												shift : 5,
												time : 1500
											});
										}
									},'json');
								} else{
									layer.msg(result.msg, {
										msg : [ '#BB0000' ],
										shift : 5,
										time : 1500
									});
								}
							},'json');
							
							layer.closeAll('dialog');
						}
					})
				} else{
					layer.msg('请选择至少选择一个菜单', {
						msg : [ '#BB0000' ],
						time : 1500
					});
				}
			}else if('viewReportMenu' === invoke){
				var rows = EL.reportMenuGrid.treegrid("getSelections");
				var rowss= EL.reportMenuGrid.treegrid("getRows");
				
				
				if(rows.length == 1){
					var row = EL.reportMenuGrid.treegrid('getSelected');
					var prow = EL.reportMenuGrid.treegrid('getParent',row.menuid);
					layer.open({
					      type: 2,
					      title: '查看',
					    //  shadeClose: false,
					     // shade: false,
					      maxmin: true, //开启最大化最小化按钮
					      area: ['750px', '400px'],
					      content: URLS.viewReportMenu,
					      btns: 1,
					      btn: ['关闭'],
					      success: function(layero, index){
					    	  var body = layer.getChildFrame('body', index);
					    	    body.contents().find("#menuid").val(row.menuid);
					    	    body.contents().find("#menuname").val(row.menuname);
					    	    body.contents().find("#menudesp").text(row.menudesp);
					    	    body.contents().find("#rem").text(row.rem);	
					    	    body.contents().find("#ordinal").val(row.ordinal);
					    	    body.contents().find("#menuParentName").val(prow.menuname);
					      }
					 });
				} else {
					layer.msg('请选择一条数据', {
						msg : [ '#BB0000' ],
						time : 1500
					});
				}
			}else if('relateRole' === invoke){
				var rows = EL.reportMenuGrid.treegrid('getSelections');
				if(rows.length == 1){
					var parentid="";
					var row = EL.reportMenuGrid.treegrid('getSelected');
					var Parent=EL.reportMenuGrid.treegrid('getParent',row.menuid);
					if(Parent!=null){
						parentid=Parent.menuid;
					}
					url=URLS.relateRole+"?menuid="+row.menuid+"&parentid="+parentid;
					//top.getDlg(url,"relateRoleFrame","分配角色",850,600,true);		
					
					layer.open({
					      type: 2,
					      title: '分配角色',
					     // shadeClose: false,
					     // shade: false,
					      maxmin: true, //开启最大化最小化按钮
					      area: ['800px', '500px'],
					      content:url,
					      btns: 0/*,
					      btn: ['保存','关闭']*/
					 });
					
					
				} else {
					layer.msg('请选择一条数据', {
						msg : [ '#BB0000' ],
						time : 1500
					});
				}
			}
		});
	//新增
	EL.addReportMenu.click(function() {
		var rows = EL.reportMenuGrid.treegrid("getSelections");
		if(rows.length > 1) {
			layer.msg('请选择一条数据！', {
				msg : [ '#BB0000' ],
				time : 1500
			});
		} else if(rows.length == 1){
			var row = EL.reportMenuGrid.treegrid("getSelected");
			var menuparent = row.menuparent;
			if(menuparent !=null && menuparent!=""){
				layer.msg('不能创建三级菜单！', {
					msg : [ '#BB0000' ],
					time : 1500
				});
			}else{
				url=URLS.addReportMenu+"?menuid="+row.menuid;
				//top.getDlg(url,"addReportMenuFrame","新增菜单",730,332,true);
				layer.open({
				      type: 2,
				      title: '新增',
				     // shadeClose: false,
				     // shade: false,
				      maxmin: true, //开启最大化最小化按钮
				      area: ['730px', '300px'],
				      content: url,
				      btns: 0/*,
				      btn: ['保存','关闭']*/	
			  });
			}
		} else {
			url=URLS.addReportMenu;
			//top.getDlg(url,"addReportMenuFrame","新增菜单",730,332,true);
			layer.open({
			      type: 2,
			      title: '新增',
			     // shadeClose: false,
			     // shade: false,
			      maxmin: true, //开启最大化最小化按钮
			      area: ['730px', '300px'],
			      content: url,
			 });
		}
	})
});