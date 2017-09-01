/**
 * ${modelDesc}主页面js
 */
(function($){
	$.rolemain = {
		init : function(){
			//新增
			$("#addBtn").click(function(){
				$("#dialog_div").dialog({    
				    title: "新增", 
				    collapsible : false,//是否显示可折叠按钮
				    minimizable : false,//定义是否显示最小化按钮。
				    maximizable : false,//定义是否显示最大化按钮。
				    resizable : false,//定义是否可以改变对话框窗口大小。
				    toolbar : null,//设置对话框窗口顶部工具栏
				    width: 600,    
				    height: 250,    
				    closed: false,    
				    cache: false,    
				    href: getRootPath() + "/${param_model}Controller/edit",    
				    modal: true,
				    buttons : [{
				    	text : "确定",
				    	iconCls : "icon-ok",
				    	handler : function(){
				    		$.rolemain.save();
				    		$("#dialog_div").dialog("close");
				    		$("#role_datagrid").datagrid("reload");
				    	}
				    },{
				    	text : "取消",
				    	iconCls : "icon-cancel",
				    	handler : function(){
				    		$("#dialog_div").dialog("close");
				    	}
				    }]
				});   
			});
			//修改
			$("#editBtn").click(function(){
				var _selected = $("#role_datagrid").datagrid("getSelected");
				if(!_selected || !_selected.id) {
					easyShow("请选择！");
					return false;
				}
				$("#dialog_div").dialog({    
				    title: "修改", 
				    collapsible : false,//是否显示可折叠按钮
				    minimizable : false,//定义是否显示最小化按钮。
				    maximizable : false,//定义是否显示最大化按钮。
				    resizable : false,//定义是否可以改变对话框窗口大小。
				    toolbar : null,//设置对话框窗口顶部工具栏
				    width: 600,    
				    height: 250,    
				    closed: false,    
				    cache: false,    
				    href: getRootPath() + "/${param_model}Controller/edit?id=" + _selected.id,    
				    modal: true,
				    buttons : [{
				    	text : "确定",
				    	iconCls : "icon-ok",
				    	handler : function(){
				    		$.rolemain.save();
				    		$("#dialog_div").dialog("close");
				    		$("#role_datagrid").datagrid("reload");
				    	}
				    },{
				    	text : "取消",
				    	iconCls : "icon-cancel",
				    	handler : function(){
				    		$("#dialog_div").dialog("close");
				    	}
				    }]
				});
			});
			//删除
			$("#removeBtn").click(function(){
				var _checked = $("#role_datagrid").datagrid("getChecked");
				if(!_checked){
					eashShow("请选择！");
					return false;
				}
				var _array = new Array();
				$.each(_checked, function(index, item){
					_array.push(item.id);
				});
				$.ajax({
					url : getRootPath() + "/${param_model}Controller/deleteByKeys",
					async : false,
					type : "POST",
					dataType : "json",
					data : {
						ids : _array.join(",")
					},
					success : function(data){
						if(data && data.success){
							$("#role_datagrid").datagrid("reload");
							easyShow(data.msg);
						}
					}
				});
			});
			//初始化${modelDesc}列表
			$("#role_datagrid").datagrid({
				url : getRootPath() + "/${param_model}Controller/initMainView",
				rownumbers : true,
				singleSelect : true,
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,
				pagePosition : "bottom",
				pageNumber : 1,
				pageSize : 10,
				pageList : [10,20,30,40,50],
				queryParams : {}
			});
			var _pager = $("#role_datagrid").datagrid("getPager");
			_pager.pagination({
				beforePageText : "页",
				afterPageText : "of {pages}",
				displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
			});
		},
		//保存${modelDesc}信息
		save : function(){
			$.ajax({
				url : getRootPath() + "/${param_model}Controller/save",
				async : false,
				type : "POST",
				dataType : "json",
				data : $("form:first").serialize(),
				success : function(data){
					if(data && data.success){
						easyShow(data.msg);
					}
				}
			});
		}
	};
})(jQuery);
$(document).ready(function(){
	$.rolemain.init();
	//让easyui-layout自适应浏览器高度
	autoLayoutHeight();
});