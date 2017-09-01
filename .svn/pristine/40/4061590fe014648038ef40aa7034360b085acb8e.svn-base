var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.menuTree=_ctx + '/report/menuTree.do';
URLS.reportPage=_ctx + '/report/reportPage.do';
URLS.viewReport=_ctx + '/report/viewReport.html';
URLS.selectReport=_ctx + '/report/selectReport.html';
URLS.updateReport=_ctx + '/report/updateReport.html';
URLS.getReport=_ctx + '/report/getReport.html';
URLS.findIsReportMenu=_ctx + '/report/findIsReportMenu.do';
URLS.deleteReport=_ctx + '/report/deleteReport.do';
URLS.cutReport=_ctx + '/report/cutReport.do';
URLS.reportById=_ctx + '/report/reportById.do';
URLS.findMountState=_ctx + '/report/findMountState.do';

EL.userid=userid;


//-------------------------------------------------------------------
//定义全局变量
	var menuid='';
	$(function(){
		// 加载问题列表
		__.mask();
		
		$('#menuTreeDiv').height(document.documentElement.clientHeight-90);
		$('#menuTree').tree({  
			//获得角色树地址
		    url :URLS.menuTree,
		    onClick : function(node){
		    	//清空查询框
		    	$('#reportname').val('');
		    	//点击节点时得到角色编号
		    	var nodeChildren=$('#menuTree').tree('getChildren',node.target);
		    	if(nodeChildren.length==0){
			    	menuid=node.id;
				    $("#reportTable").datagrid('reload', {
						filters : __.encode({
							'menuid' : menuid,
							'reportname' : '',
							'flag':''
						})
					});
				    
		    	}else{
		    		return;
		    	}
		    }
		}); 
		
		$("#reportTable").datagrid({
			height:document.documentElement.clientHeight-$('#queryDiv').height()-2,
			pagination:true,
			striped : true,
			fitColumns:true,
			pageSize:20,
			//rownumbers:true,
			queryParams : {
				filters : __.encode({
					'menuid' : '',
					'reportname' : '',
					'flag':''
				})
			},
			checkOnSelect:true,
			selectOnCheck:true,
			singleSelect:false,
			pageList: [20,50,100],
			columns:[[{field:'ck',checkbox:true},
				{field:'reportId',title:'报表编号',width:80},
				{field:'projectname',title:'项目名称',width:130},
				{field:'workbookname',title:'工作簿名称',width:130},
				{field:'reportname',title:'报表名称',width:200},
				{field:'reporttype',hidden:true},
				{field:'publisper',hidden:true},//制作人
				{field:'reportdescription',hidden:true},
				{field:'desp',hidden:true},
				{field:'datescreen',hidden:true},//报表有效期截止时间  
				{field:'publishdate',title:'发布日期',width:120,
					formatter : function(value, row) {
						var publishdate = '';
						if(""!=row.publishdate&&null!=row.publishdate){
							publishdate = row.publishdate.substring(0,10);
							return publishdate;
						}else{
							return row.publishdate;
						}
					}	
				},
				{field:'mountstate',title:'挂载状态',width:100,formatter : function(value, row) {
					var html = '';
					if(row.mountstate == "0"){
						html = '未挂载';
					}else if(row.mountstate == "1"){
						html = '已挂载';
					}
					return html;
				}},
				{
					field : '_operation',
					title : '操作',
					fixed : true,
					width : 180,
					halign : 'center',
					align : 'center',
					formatter : function(value, row) {
						var html = '';
						html += '<div data-record="' + $.encodeHTML($.encode(row)) + '">';
						html += ' <a data-invoke="edit"  href=\"javascript:void(0)\" class="x-link" onclick="editReport('+row.reportId+')">编辑</a>';
						html += ' <a data-invoke="delete"  href=\"javascript:void(0)\" class="x-link" onclick="removeReport('+row.reportId+')">删除</a>';
						html += ' <a data-invoke="view"  href=\"javascript:void(0)\" class="x-link" onclick="viewReport('+row.reportId+')">查看</a>';
						html += '</div>';
						return html;
					}
				} 
			]],
		 	url:URLS.reportPage,
			onLoadSuccess:function(data){
				__.unmask();
		    	$('#reportTable').datagrid('clearSelections'); //clear selected options
		    }
		});
		$('#reportDiv').height(document.documentElement.clientHeight-$('#queryDiv').height()-2);
	});
	
//挂载报表
function selectReports(){
	if(menuid=='' || menuid==null){
		layer.msg('请选择需要挂载报表的菜单', {
			msg : [ '#BB0000' ],
			time : 1500
		});
		return;
	}
	url=URLS.selectReport+"?menuid="+menuid;
//	top.getDlg(url,"selectReportiframe","挂载报表",700,600,true);
	layer.open({
	      type: 2,
	      title: '挂载报表',
	      //shadeClose: false,
	      //shade: false,
	      maxmin: true, //开启最大化最小化按钮
	      area: ['700px', '450px'],
	      content: url,
	      btns: 0
	      
	 });
}
	
//卸载报表
function cutReports(){
	if(menuid=='' || menuid==null){
		layer.msg('请选择需要卸载报表的菜单', {
			msg : [ '#BB0000' ],
			time : 1500
		});
		return;
	}
	var rows = $('#reportTable').datagrid('getChecked');
	
	/*if(rows.length > 1){
		layer.msg('请选择 1 条要卸载的报表', {
			msg : [ '#BB0000' ],
			time : 1500
		});
		return;
	}*/
	if (rows.length > 0){
		var ids="";
		for(var i=0;i<rows.length;i++){
			ids += rows[i].reportId+",";
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length-1);
		}
		layer.confirm("确认卸载 "+rows.length+" 条报表？", function(r){
		//$.messager.confirm("卸载报表","确认卸载 "+rows.length+" 条报表？",function(r){
			if (r){
				var postUrl = URLS.cutReport;
				$.post(postUrl,{"ids":ids, "menuid" : menuid, "rd":Math.random()},function(result){
					if (result.success){
						layer.msg(result.msg, {
							msg : [ '#BB0000' ],
							time : 2000
						});
						$('#reportTable').datagrid('reload');	// reload the user data
						$('#reportTable').datagrid('clearSelections'); //clear selected options
					} else {
						layer.msg(result.msg, {
							msg : [ '#BB0000' ],
							time : 2000
						});
					}
				},'json');
			}
		});
	}else{
		layer.msg('请选择一条数据', {
			msg : [ '#BB0000' ],
			time : 1500
		});
	}
}
	
//编辑
function editReport(reportid){

		url=URLS.updateReport+"?reportid=" + reportid
		layer.open({
		      type: 2,
		      title: '编辑',
		     // shadeClose: false,
		     // shade: false,
		      maxmin: true, //开启最大化最小化按钮
		      area: ['750px', '290px'],
		      content: url,
		      btns: 0
		
		 });

}
	
//查看详情
function viewReport(reportid){

	$.ajax({
		url: URLS.reportById,
		type:'POST',
		async:false,
		data:{
			"reportid":reportid
		},
		success: function(result){
//			var reportid = result.reportid;
			var reportname = result.reportname;
			var reporttype = result.reporttype;
			var isopen = result.isopen;
			var url = result.url;
			var publisper = result.publisper;
			var reportdescription = result.reportdescription;
			var desp = result.desp;
		 //-------------------------------------   
		    layer.open({
			      type: 2,
			      title: '查看',
			      //shadeClose: false,
			     // shade: false,
			      maxmin: true, //开启最大化最小化按钮
			      area: ['750px', '400px'],
			      content: URLS.viewReport,
			      btns: 1,
			      btn: ['关闭'],
			      success: function(layero, index){
			    	  var body = layer.getChildFrame('body', index);
			    	    body.contents().find("#reportid").val(reportid);
			    	    body.contents().find("#reportname").val(reportname);
			    	    body.contents().find("#reporttype").val(reporttype);
			    	    body.contents().find("#isopen").val(isopen);
			    	    body.contents().find("#url").text(url);	
			    	    body.contents().find("#publisper").val(publisper);
			    	    body.contents().find("#reportdescription").text(reportdescription);
			    	    body.contents().find("#desp").text(desp);
			      }
			 });
		    
		  //---------------------------------------  
		}
	});

}
	
//删除报表
function removeReport(reportid){

		layer.confirm("确认删除报表？", function(r){
			if (r){
				if(menuid==""){
					//====删除报表前判断该报表是否已挂载====
					var postUrl = URLS.findIsReportMenu;
					$.post(postUrl,{"reportid":reportid,"rd":Math.random()},function(result){
						//======如果查询到所选报表没有挂载，则删除成功======
						if(result.success){
							var postUrl = URLS.deleteReport;
							$.post(postUrl,{"reportid":reportid,"rd":Math.random()},function(result){
								if (result.success){
									layer.msg(result.msg, {
										msg : [ '#BB0000' ],
										time : 2000
									});
									$('#reportTable').datagrid('reload');	// reload the user data
									$('#reportTable').datagrid('clearSelections'); //clear selected options
								} else {
									layer.msg(result.msg, {
										msg : [ '#BB0000' ],
										time : 2000
									});
								}
							},'json');
						}
						//======如果查询到所选报表已挂载，则删除失败======
						else{
							layer.msg(result.msg, {
								msg : [ '#BB0000' ],
								time : 2000
							});
						}
					},'json');
				}else{
					//根据菜单查询       已挂载菜单删除
					var postUrl = URLS.deleteReport;
					$.post(postUrl,{"reportid":reportid,"menuid":menuid},function(result){
						if (result.success){
							layer.msg(result.msg, {
								msg : [ '#BB0000' ],
								time : 2000
							});
							$('#reportTable').datagrid('reload');	// reload the user data
							$('#reportTable').datagrid('clearSelections'); //clear selected options
						} else {
							layer.msg(result.msg, {
								msg : [ '#BB0000' ],
								time : 2000
							});
						}
					},'json');
				}
				
			}
		});

}
	
//回车查询
function queryReportForEnter(e){
	//刷新菜单树
	$('#menuTree').tree('reload');
	//清空菜单
	menuid='';
   var keyCode = null;
   if(e.which)
       keyCode = e.which;
   else if(e.keyCode) 
       keyCode = e.keyCode;
   if(keyCode == 13) 
   {
   	var reportname =$('#reportname').val();
   	var mountstate = $("#mountstate").val();
		$("#reportTable").datagrid('reload', {
			filters : __.encode({
				'menuid' : '',
				'reportname' : reportname,
				'flag':'',
				'mountstate' : mountstate
			})
		});
       return false;
   }
   return true;
   
}
	
//清空
function clearQueryForm(){
	$('#reportname').val('');
	$('#menuTree').tree('reload');
	menuid='';
	
	$("#reportTable").datagrid('reload', {
		filters : __.encode({
			'menuid' : '',
			'reportname' :'',
			'flag':''
		})
	});
	
}
	
//查询
function queryReport(){
	//刷新菜单树
	$('#menuTree').tree('reload');
	//清空菜单
	menuid='';
	$("#reportTable").datagrid('clearSelections'); // clear
	var reportname = $('#reportname').val();
	var mountstate = $("#mountstate").val();
	$("#reportTable").datagrid('reload', {
		filters : __.encode({
			'menuid' : '',
			'reportname' : reportname,
			'flag':'',
			'mountstate' : mountstate
		})
	});
}
	
//从tableau获取报表
function getReportFromTableau(){
	
	url=URLS.getReport;
	layer.open({
	      type: 2,
	      title: '获取报表',
	      //shadeClose: false,
	      //shade: false,
	      maxmin: true, //开启最大化最小化按钮
	      area: ['450px', '400px'],
	      content: url,
	      btns: 0
	      
	 });
}

function showProgress(msg, objid) {
	if (msg == null) {
		msg = "处理中，请稍后...";
	}
	var win = $.messager.progress({
		title:'请等待',
		text:msg
	});
}

//关闭处理中提示信息
function hideProgress() {
	$.messager.progress('close');
}
