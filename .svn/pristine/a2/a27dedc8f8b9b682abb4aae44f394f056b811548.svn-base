var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.tableauProjectPage=_ctx + '/queryReport/tableauProjectPage.do'; //获取tableau报表项目列表
URLS.getReportFromTableau=_ctx + '/queryReport/getReportFromTableau.do';//获取报表
URLS.tableauSitePage=_ctx + '/report/tableauSitePage.do'; //获取tableau报表站点列表

//=================================================
$(function(){
	$('#reportDiv').height(document.documentElement.clientHeight-$('#staffPanel').height()-40);
	$("#reportTable_").datagrid({
		nowrap : true,
		striped : true,
		border:false,
		fit:true,
		pagination:false,
		rownumbers:false,
		checkOnSelect:true,
		selectOnCheck:true,
		singleSelect: false,
		autoRowHeight: false,
		fitColumns:true,
		idField : 'id',
		columns:[[{field:'ck',checkbox:true},
				{field:'id',title:'项目id',hidden:true},
				{field:'site_id',title:'站点id',hidden:true},
				{field:'site_name',title:'站点名称',width:150},
				{field:'name',title:'项目名称',width:150}
			]],
	 	url:URLS.tableauProjectPage
	});
	
	
	$('#cc').combobox({    
	    url:URLS.tableauSitePage,    
	    valueField:'site_id',    
	    textField:'site_name',
	    editable:false,
    	onSelect: function(rec){  
            var url = URLS.tableauProjectPage+"?site_id="+rec.site_id;    
            $("#reportTable_").datagrid('reload', url);    
        }
	});
	
});


//获取报表
function getReportFromTableau(){
	
	var rows = $('#reportTable_').datagrid('getChecked');
	
	if (rows.length > 0){
		var ids="";
		for(var i=0;i<rows.length;i++){
			ids += ","+rows[i].id;
		}
		if(ids.length > 0){
			ids = ids.substring(1);	
		}
		
		showProgress("正在获取报表", '');
		var postUrl = URLS.getReportFromTableau;
		$.post(postUrl,{"ids":ids},function(result){
			hideProgress();
			if (result.success){
				layer.msg("获取报表成功",{icon:1},function(){
	                parent.location.reload(); // 父页面刷新
	                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	                parent.layer.close(index);
	            });
			} else {
				layer.msg('获取报表失败', {
					msg : [ '#BB0000' ],
					time : 1500
				});
			}
		},'json');
		
	}else{
		layer.msg("请选择报表的所属项目和站点", {
			msg : [ '#BB0000' ],
			time : 2000
		});
	}
	//----------------------
	
}


//关闭
function closePanel(){
	 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
        parent.layer.close(index);
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

