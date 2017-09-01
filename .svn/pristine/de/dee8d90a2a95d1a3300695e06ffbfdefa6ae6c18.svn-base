var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.relateNotRoleList=_ctx + '/reportMenu/relateNotRoleList.do';
URLS.relateInRoleList=_ctx + '/reportMenu/relateInRoleList.do';
URLS.staffRelateRole=_ctx + '/reportMenu/staffRelateRole.do';
URLS.parentstaffRelateRole=_ctx + '/reportMenu/parentstaffRelateRole.do';


var leftRoleArray = new Array ();//删除集合
var rightRoleArray = new Array ();//添加集合
/*var menuid="";
var parentid="";
*/
$(function() {
	//------------------------------------------------------
	//menuid=$("#menuid").text();
	//parentid=$("#parentid").text();
	__.mask();
	$("#leftrole").datagrid({
		fitColumns:true,
		singleSelect:false,
		fit:true,
		striped : true,
		nowrap : true,
		striped : true,
		border:false,
		checkOnSelect:true,
		selectOnCheck:true,
		pagination:true,
		pageSize:20,
		pageList: [10,20,30],
		autoRowHeight: false,
		idField:"ID",
		url:URLS.relateNotRoleList,
		columns:[[{field:'ck',checkbox:true},    
		          {field:'ID',title:'角色编号',width:100},
				  {field:'NAME',title:'角色名称',width:100}		  
			]],
			onLoadSuccess:function(data){
	   			__.unmask();
	   			
	   	    }
		});
	
	$("#rightrole").datagrid({
		fitColumns:true,
		singleSelect:false,
		fit:true,
		striped : true,
		nowrap : true,
		striped : true,
		checkOnSelect:true,
		selectOnCheck:true,
		border:false,
		pagination:true,
		pageSize:20,
		pageList: [10,20,30],
		autoRowHeight: false,
		idField:"ID",
		queryParams : {
			filters : __.encode({
				'menuid' : menuid
			})
		},
		columns:[[{field:'ck',checkbox:true},    
		      {field:'ID',title:'角色编号',width:100},
		      {field:'NAME',title:'角色名称',width:100},			  
			]],
		 	url:URLS.relateInRoleList
		});
})
	
	function leftRole(){
		var rows = $('#rightrole').datagrid('getSelections');
		if (rows.length > 0){
			for(var i=rows.length-1;i>=0;i--){
//				if(leftRoleArray.indexOf(rows[i].ID) == -1){//判断    删除集合 是否存在元素
//					leftRoleArray.push(rows[i].ID);			//没有则添加
//				}
				if(rightRoleArray.indexOf(rows[i].ID) != -1){//判断    添加集合 是否存在元素
					rightRoleArray.splice(rightRoleArray.indexOf(rows[i].ID),1);//有则删除添加集合中的元素
				}else{
					leftRoleArray.push(rows[i].ID);			//没有则添加到删除集合
				}
				var row=rows[i];
				$("#rightrole").datagrid('deleteRow',$("#rightrole").datagrid('getRowIndex',row));
			} 
		}
		$('#rightrole').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked",false);
	}

	function rightRole(){
		var rows = $('#leftrole').datagrid('getSelections');
		var rightrows = "";
		var rightArray = new Array (); //  用于存已分配的角色
		var errArray = new Array ();   //  用于存重复的角色
		if (rows.length > 0){
			rightrows = $('#rightrole').datagrid('getRows');
			for(var j=rightrows.length-1;j>=0;j--){
				rightArray.push(rightrows[j].ID);
			}
			for(var i=rows.length-1;i>=0;i--){
				if(rightArray.indexOf(rows[i].ID) == -1){
					
//					if(rightRoleArray.indexOf(rows[i].ID) == -1){
//						rightRoleArray.push(rows[i].ID);
//					}
					if(leftRoleArray.indexOf(rows[i].ID) != -1){//判断    删除集合 是否存在元素
						leftRoleArray.splice(leftRoleArray.indexOf(rows[i].ID),1);//有则删除   删除集合 中的元素
					}else{
						rightRoleArray.push(rows[i].ID);     // 没有则添加 到添加集合
					}
					
					var row=rows[i];
					$("#rightrole").datagrid('appendRow',row);
				}else{
					errArray.push(rows[i].NAME);
				}		
			} 
		}
		if(errArray!=""){
			layer.msg(errArray+"已存在", {
				msg : [ '#BB0000' ],
				time : 2000
			});
		}
		$('#leftrole').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked",false);
	}
	
	function saveRole(){
		var leftRoleIds = "";
		var rightRoleIds = "";
		for(var i=0;i<leftRoleArray.length;i++){
			if(i<leftRoleArray.length-1){
				leftRoleIds += leftRoleArray[i]+",";
			} else{
				leftRoleIds += leftRoleArray[i];
			}
		}
		for(var i=0;i<rightRoleArray.length;i++){
			if(i<rightRoleArray.length-1){
				rightRoleIds += rightRoleArray[i]+",";
			} else{
				rightRoleIds += rightRoleArray[i];
			}
		}
		var postUrl = URLS.staffRelateRole;
		var parentpostUrl = URLS.parentstaffRelateRole;
		if(parentid!=""){
			 $.post(parentpostUrl,{"leftRoleIds":leftRoleIds,"rightRoleIds":rightRoleIds,"menuid":parentid,"rd":Math.random()},function(result){},'json');
		}
		
		$.post(postUrl,{"leftRoleIds":leftRoleIds,"rightRoleIds":rightRoleIds,"menuid":menuid,"rd":Math.random()},function(result){
			if (result.success){
				layer.msg(result.msg,{icon:1},function(){
	                parent.location.reload(); // 父页面刷新
	                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	                parent.layer.close(index);
	            });
				
			} else {
				layer.msg(result.msg, {
					msg : [ '#BB0000' ],
					time : 2000
				});
				return;
			}
		},'json');
	}

	function closeEmployee(){
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
	        parent.layer.close(index);
	}	
	
