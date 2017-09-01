var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.queryMenuTree=_ctx + '/queryReportMenu/queryMenuTree.do';
URLS.queryLikeRoleTreeList=_ctx + '/queryReport/queryLikeRoleTreeList.do';
URLS.queryRoleMenu=_ctx + '/queryReport/queryRoleMenu.do';
URLS.saveRoleMenuReport=_ctx + '/queryReport/saveRoleMenuReport.do';

//-----------------------------------------------------------
//定义全局变量

$(function(){
	var menuid="";
	$('#roleTreeDiv').height(document.documentElement.clientHeight-100);
	$('#reportDiv').height(document.documentElement.clientHeight-100);
	//$('#userDiv').height(document.documentElement.clientHeight-73);
	$('#tt').tree({  
		//获得菜单树地址
	    url:URLS.queryMenuTree+"?random="+new Date().getTime(),
	    onClick:function(node){
	    	$("#menuid_").val("");
	    	$("#reportid_").val("");
	    	//点击节点时得到菜单编号
	        menuid=node.id;
	       /* $("#reportid_").val(menuid);
	    	alert(menuid+"==点击");*/
	        
	    	var menuparentId1=$('#tt').tree('getParent',node.target);
	    	var menuparentId2=$('#tt').tree('getParent',menuparentId1.target);
	    	var menuparentId3=$('#tt').tree('getChildren',node.target);
	    	var menuparentId="";
    	    if(menuparentId3!=null&&menuparentId3!=""&&menuparentId2!=null){
	    		menuparentId=menuparentId1.id;
	    	}else if(menuparentId3!=null&&menuparentId3!=""){
	    		return;
	    	}else{
	    		menuparentId=menuparentId2.id;
	    	}
	    	$('#ttt').tree({  
				//获得菜单树地址
			    url:URLS.queryLikeRoleTreeList+"?menuparentId="+menuparentId,
			    onLoadSuccess:function(){
			    	$.getJSON(URLS.queryRoleMenu+"?random="+new Date, {"menuid" : menuid}, function(jso) {
						var nodess = $('#ttt').tree('getChecked');//获取选择节点
						var ll = nodess.length;
						for(var i=0; i<ll; i++){
							$('#ttt').tree('uncheck', nodess[i].target);//清空已选项
						}
						var nodes = $('#ttt').tree('getChecked', 'unchecked');	// 获取未选择节点
						var l = nodes.length;
						var k = jso.length;
						for(var j=0; j<l; j++){
							for (var i = 0; i < k; i++) {
								if(nodes[j].id == jso[i]){
				            		$('#ttt').tree('check', nodes[j].target);//回显选选择节点
								}
							}
						}
					});
			    }
		    });
	    	
	    	var node1=$('#tt').tree('getParent',node.target);
	    	var node2=$('#tt').tree('getParent',node1.target);
	    	if(node==null||node==""){
	    		var menuidid="";
	    	} else{
	    		var menuidid=node2.id;
	    	}
	    	$("#menuid_").val(menuidid);
	    	$("#reportid_").val(menuid);
	    }
	}); 
	
	$('#ttt').tree({ 
		//获得角色树地址
		checkbox:true,
	    url:URLS.queryLikeRoleTreeList+"?random="+new Date().getTime(),
	    onLoadSuccess:function(){
			$('.panel-title').css('border-right', '1px solid #e6e6e6');
	    }
	}); 
});
function queryRoleButton(){
//	$("#ttt").empty();
	var rolename =$('#rolename').val();
   	name = window.encodeURI(window.encodeURI(rolename));
	    $('#ttt').tree({  
		checkbox:true,
			//获得角色树地址
		    url:URLS.queryLikeRoleTreeList+"?random="+new Date().getTime() + "&rolename="+name,
		    onLoadSuccess:function(node, data){
		    }
	    });
 
	  $('#tt').find('.tree-node-selected').removeClass('tree-node-selected');
	  $("#reportid_").val("");
}
function queryRole(e){
	var keyCode = null;
	if(e.which)
		keyCode = e.which;
	else if(e.keyCode) 
		keyCode = e.keyCode;
	if(keyCode == 13) {
       	var rolename =$('#rolename').val();
       	name = window.encodeURI(window.encodeURI(rolename));
   	    $('#ttt').tree({  
   			//获得菜单树地址
   	    	url:URLS.queryLikeRoleTreeList+"?rolename="+name,
   	    });
        return false;
	}
	return true;
}	
	
//保存菜单到角色
function saveRoleReport(){
	var menus =$("#menuid_").val();//获取菜单选项
	var reportid = $("#reportid_").val();//获取报表的id
	var idss="";//角色id的字符串
	var falm =false;
	if(reportid.length>0 && reportid.indexOf("_")>-1){
		falm = true;
	}else{
		falm = false;
		layer.msg("请选择赋予角色的报表操作类型", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		$('#tt').tree('reload');
		return;
	}
	var roles = $('#ttt').tree('getChecked');//获取角色选项
	if (roles.length > 0){
		for(var i=0;i<roles.length;i++){
			idss += roles[i].id+",";//拼接成字符串，便与传值
		}
     } 
	idss = idss.substring(0, idss.length-1);
	if(falm){
		$.post(URLS.saveRoleMenuReport,{"reportid":reportid,"roleid":idss,"menuid":menus},function(result){
			if (result.success){
				layer.msg(result.msg, {
					msg : [ '#BB0000' ],
					time : 2000
				});
			} else {
				layer.msg(result.msg, {
					msg : [ '#BB0000' ],
					time : 2000
				});
				$('#ttt').tree('reload');	// reload the org data
				$('#ttt').tree('clearSelections'); //clear selected options
				return;
			}
		},'json'); 
		
	}
}

//-------------------------------------------------------------------
