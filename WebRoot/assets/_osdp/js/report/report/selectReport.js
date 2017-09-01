
	var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
	URLS.reportPage=_ctx + '/report/reportPage.do';
	URLS.reportSetMenuid=_ctx + '/report/reportSetMenuid.do';


	//=================================================

	/*var menuid='';*/
	$(function(){
	   /* menuid=$("#menuid1").text();*/
		$('#reportDiv').height(document.documentElement.clientHeight-$('#staffPanel').height()-40);
		$("#reportTable_").datagrid({
			nowrap : true,
			striped : true,
			border:false,
			fit:true,
			queryParams : {
				filters : __.encode({
					'menuid' :menuid,
					'reportname':'',
					'flag' : 'set'
				})
			},
			checkOnSelect:true,
			selectOnCheck:true,
			singleSelect:false,
			pagination:true,
			rownumbers:false,
			pageSize:20,
			pageList: [20,50,100],
			autoRowHeight: false,
			fitColumns:true,
			idField : 'reportId',
			columns:[[{field:'ck',checkbox:true},
					{field:'reportId',title:'报表编号',width:80},
					{field:'projectname',title:'项目名称',width:100},
					{field:'workbookname',title:'工作簿名称',width:130},
					{field:'reportname',title:'报表名称',width:200},
					{field:'reporttype',hidden:true},
					{field:'publisper',hidden:true},
					{field:'reportdescription',hidden:true},
					{field:'desp',hidden:true},
					{field:'publishdate',title:'发布日期',width:100,
						formatter : function(value, row) {
							var publishdate = '';
							if(""!=row.publishdate&&null!=row.publishdate){
								publishdate = row.publishdate.substring(0,10);
								return publishdate;
							}else{
								return row.publishdate;
							}
						}	
					}
					//,
					//{field:'url',title:'URL',width:100}
				]],
		 	url:URLS.reportPage,
			onLoadSuccess:function(data){
				$(this).datagrid('doCellTip', { 'max-width': '400px', 'delay': 500 });
		    	$('#reportTable_').datagrid('clearSelections'); //clear selected options
		    	$('#buttondiv').css('position', 'relative');
		    	$('#buttondiv').css('z-index', 999);
		    	$('#buttondiv').css('top', 0);
		    	$('#reportTable').datagrid('clearSelections'); //clear selected options
		    }
		});
	});

	//为菜单挂载报表
	function reportSetMenuid(){
		var index = layer.load(1);
		var rows = $('#reportTable_').datagrid('getChecked');
		if (rows.length > 0){
			var reportids="";
			//var reportpic="";
			for(var i=0;i<rows.length;i++){
				reportids += ","+rows[i].reportId;
				//reportpic = rows[i].REPORTPIC;
			}
			if(reportids.length > 0){
				reportids = reportids.substring(1);	
			}
			var postUrl = URLS.reportSetMenuid;
			$.post(postUrl,{"menuid":menuid,"reportids":reportids,"rd":Math.random()},function(result){
				layer.close(index);
				if (result.result){
					layer.msg(result.msg,{icon:1},function(){
		                //parent.location.reload(); // 父页面刷新
		                parent.reloadData();
		                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		                parent.layer.close(index);
		            });
					
				} else {
					layer.msg(result.msg, {
						msg : [ '#BB0000' ],
						time : 2000
					});
				}
			},'json');
		 }else{
			layer.close(index);
			layer.msg("请选择报表", {
				msg : [ '#BB0000' ],
				time : 2000
			});
		}
	}

	//查询
	function queryReport(){
		$("#reportTable_").datagrid('clearSelections'); // clear
		var reportname = $('#reportname').val();
		
		$("#reportTable_").datagrid('reload', {
			filters : __.encode({
				'menuid' : menuid,
				'reportname' : reportname,
				'flag':'set'
			})
		});
	}


	//回车查询           
	function queryReportForEnter1(e){
	 	var keyCode = null;
	    if(e.which){
	        keyCode = e.which;
	    }else if(e.keyCode){
	        keyCode = e.keyCode;
	    } 
	    if(keyCode == 13) 
	    {
			$("#reportTable_").datagrid('clearSelections'); // clear
			var reportname =$('#reportname').val();
			$("#reportTable").datagrid('reload', {
				filters : __.encode({
					'menuid' : menuid,
					'reportname' : reportname,
					'falg':'set'
				})
			});
	       return false;
	    }
	    return true;
	}

	function closePanel(){
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
	        parent.layer.close(index);
	}

	function clearQuery(){
		$('#reportname').val("");
		$("#reportTable_").datagrid('load');
	}
