var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.findRoleAll = _ctx + '/auth_role/findAll.do';
URLS.findRoldIdByUserId = _ctx + '/auth_user/findUserRold.do';
URLS.updateUserRole = _ctx + '/auth_user/updateUserRole.do';
URLS.findRoleType = _ctx + '/auth_role/findRoleType.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.userId = GV.search.id;

	EL.window = $(window);
	EL.grid = $('#grid');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');
	EL.type = $('#type');
	EL.grid.datagrid({
		url : URLS.pagedQuery,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'name',
			title : '角色',
			width : 150,
			fixed : true,
			align : 'center'
		}, {
			field : 'remark',
			title : '备注',
			width : 100,
			halign : 'center'
		} ] ]
	});
	$.post(URLS.findRoleType,null,function(records) {
		var html = '';
		$.each(records, function(i, record) {
			html += '<option value="' + record.codeid + '" >' + record.codename + '</option>';
		});
		$("#type").html(html);
		var select = document.getElementById("type");
		select.options[0].selected = true;
		FN.refresh();
	});
	var arr = [];
	var types;
	EL.type.change(function(){
		//获取当前页面选中参数值
		var records = EL.grid.datagrid('getChecked');
		var params = {
			userId : GV.userId,
			roleIds : $.map(records, function(record) {
				return record.id;
			})
		};
		//如果数组中有，更新则为true，新增则为false
		var flg = "false";
		for(var i = 0; i < arr.length; i++){
			var obj = arr[i];
			if(obj.type == types){
				obj.ids = params.roleIds;
				arr[i] = obj;
				flg = "true";
			}
		}
		//如果数组没值，获取flg标识为新增时，往数组中放值
		if(arr.length == 0 || flg == "false"){
			var obj = new Object();
			obj.type = types;
			obj.ids = params.roleIds;
			arr.push(obj);
		}
		FN.refresh();
	});
	FN.refresh = function() {
		$.mask();
		types = $("#type").val();
		$.post(URLS.findRoleAll,{type:types}, function(records) {
			EL.grid.datagrid('loadData', records);
			var params = {};
			params.id = GV.userId;
			//定义标识当前选择类型是否已存在于数组
			var flg = "false";
			for(var i = 0; i < arr.length; i++){
				var obj = arr[i];
				//存在于数组，则取出上次选中值进行默认选中操作
				if(obj.type == types){
					flg = "true";
					$.unmask();
					EL.grid.datagrid('checkById', obj.ids);
				}
			}
			//如果数组中没有，则去后台查询当前分类下用户已分配的角色进行默认选中
			if(flg == "false"){
				$.post(URLS.findRoldIdByUserId, params, function(roldIds) {
					$.unmask();
					EL.grid.datagrid('checkById', roldIds);
				});
			}
		});
	};

	EL.confirm.on('click', function() {
		var records = EL.grid.datagrid('getChecked');
		var ids = [];
		for(var i = 0; i < arr.length; i++){
			var obj = arr[i];
			//循环除当前分类之外的其他分类已选中的值
			if(obj.type != $("#type").val()){
				//循环其他分类已选中的值进行拼接
				for(var s in obj.ids){
					ids.push(obj.ids[s]);
				}
			}
		}
		//获取当前页面中选中的值
		for(var i in records){
			ids.push(records[i].id);
		}
		var params = {
			userId : GV.userId,
			roleIds : ids
				/* $.map(records, function(record) {
				return record.id;
			}) */
		};
		$.mask();
		$.post(URLS.updateUserRole, params, function(result) {
			$.unmask();
			var err = $.err(result);
			if (err) {
				$.dlg.msg(err.message);
			} else {
				$.dlg.close();
			}
		});
	});

	EL.cancel.on('click', function() {
		$.dlg.close();
	});

});