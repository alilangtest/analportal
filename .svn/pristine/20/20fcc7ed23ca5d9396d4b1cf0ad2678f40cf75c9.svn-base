var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.editPage = _ctx + '/auth_user/auth_user_edit.html';
URLS.roleChoosePage = _ctx + '/auth_user/auth_user_role_choose.html';
URLS.systemChoosePage = _ctx + '/auth_user/auth_user_system_choose.html';
URLS.pagedQuery = _ctx + '/auth_user/pagedQuery.do';
URLS.load = _ctx + '/auth_user/load.do';
URLS.save = _ctx + '/auth_user/save.do';
URLS.remove = _ctx + '/auth_user/remove.do';
URLS.enable = _ctx + '/auth_user/enable.do';
URLS.disable = _ctx + '/auth_user/disable.do';
URLS.resetPasswordById = _ctx + '/auth_user/resetPasswordById.do';
URLS.orgAsyncTree = _ctx + '/auth_org/asyncTree.do';

$(function() {

	EL.window = $(window);
	EL.add = $('#add');
	EL.keyword = $('#keyword');
	EL.search = $('#search');
	EL.searchAll = $('#searchAll');
	EL.empty = $('#empty');
	EL.tree = $('#tree');
	EL.gridWrap = $('#grid-wrap')
	EL.grid = $('#grid');

	GV.orgId = '';
	GV.searchAll = true;

	//机构
	EL.tree.tree({
		url : URLS.orgAsyncTree,
		loadFilter : function(node) {
			return $.map(node, function(node) {
				return {
					id : node.id,
					text : node.name,
					state : 'closed'
				}
			});
		},
		onClick : function(node) {
			var orgId = node.id;
			if (GV.orgId != orgId) {
				GV.orgId = orgId;
				EL.search.trigger('click');
			}
		}
	});

	EL.grid.datagrid({
		url : URLS.pagedQuery,
		pagination : true,
		columns : [ [ {
			field : 'USERNAME',
			title : '账号',
			fixed : true,
			width : 100,
			halign : 'center'
		}, {
			field : 'NAME',
			title : '姓名',
			fixed : true,
			width : 100,
			halign : 'center'
		}, {
			field : 'SEX',
			title : '性别',
			fixed : true,
			width : 40,
			halign : 'center',
			align : 'center'
		}, {
			field : 'ORGNAME',
			title : '所属机构',
			width : 150,
			halign : 'center',
			align : 'center'
//			formatter : function(value, row) {
//				return $.encodeHTML(row.orgNamePath || row.orgName || '');
//			}
		}, {
			field : 'ENABLED',
			title : '状态',
			fixed : true,
			width : 50,
			halign : 'center',
			align : 'center',
			formatter : function(value, row) {
				return value==1 ? '启用' : '禁用';
			}
		}, {
			field : '_operation',
			title : '操作',
			fixed : true,
			width : 300,
			halign : 'center',
			align : 'center',
			formatter : function(value, row) {
				var html = '';
				html += '<div data-record="' + $.encodeHTML($.encode(row)) + '">';
				html += ' <a data-invoke="edit" class="x-link">编辑</a>';
				if (row.ENABLED=="1") {
					html += ' <a data-invoke="disable" class="x-link">禁用</a>';
				} else {
					html += ' <a data-invoke="enable" class="x-link">启用</a>';
				}
				html += ' <a data-invoke="role" class="x-link">分配角色</a>';
				html += ' <a data-invoke="resetpwd" class="x-link">重置密码</a>';
				html += ' <a data-invoke="remove" class="x-link">删除</a>';
				html += ' <a data-invoke="system" class="x-link">分配系统</a>';
				html += '</div>';
				return html;
			}
		} ] ]
	});

	EL.search.on('click', function() {
		var keyword = $.trim(EL.keyword.val());
		GV.searchAll = false;
		EL.grid.datagrid('load', {
			orgId : GV.orgId,
			username : keyword
		});
	});
	EL.searchAll.on('click', function() {
		var keyword = $.trim(EL.keyword.val());
		GV.searchAll = true;
		EL.grid.datagrid('load', {
			username : keyword
		});
	});

	EL.empty.on('click', function() {
		EL.keyword.val('');
		if (GV.searchAll) {
			EL.searchAll.trigger('click');
		} else {
			EL.search.trigger('click');
		}
	});

	EL.add.on('click', function() {
		$.dlg.open({
			title : '新增',
			area : [ '750px', '500px' ],
			content : URLS.editPage,
			end : function() {
				EL.grid.datagrid('reload');
			}

		});
	});

	EL.gridWrap.on('click', '[data-invoke]', function(e) {
		e.stopPropagation();
		e.preventDefault();
		var el = $(this), //
		invoke = el.attr('data-invoke'), //
		record = $.decode(el.parent().attr('data-record')), //
		id = record.ID;

		if ('edit' === invoke) {
			$.dlg.open({
				title : '编辑',
				area : [ '750px', '500px' ],
				content : URLS.editPage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					EL.grid.datagrid('reload');
				}
			});
		} else if ('remove' === invoke) {
			$.dlg.confirm('是否确认删除(操作无法恢复)?', function(dlg) {
				var params = {};
				params.id = id;
				$.post(URLS.remove, params, function(result) {
					var err = $.err(result);
					if (err) {
						$.dlg.msg(err.message);
					}
					EL.grid.datagrid('reload');
					dlg.close();
				});
			});
		} else if ('enable' === invoke) {
			var params = {};
			params.id = id;
			$.post(URLS.enable, params, function(result) {
				var err = $.err(result);
				if (err) {
					$.dlg.msg(err.message);
				}
				EL.grid.datagrid('reload');
				dlg.close();
			});
			URLS.disable = _ctx + '/auth_user/disable.do';
		} else if ('disable' === invoke) {
			var params = {};
			params.id = id;
			$.post(URLS.disable, params, function(result) {
				var err = $.err(result);
				if (err) {
					$.dlg.msg(err.message);
				}
				EL.grid.datagrid('reload');
				dlg.close();
			});
		} else if ('role' === invoke) {
			$.dlg.open({
				title : '关联角色',
				area : [ '700px', '500px' ],
				content : URLS.roleChoosePage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					EL.grid.datagrid('reload');
				}
			});
		} else if ('resetpwd' === invoke) {
			$.dlg.confirm('是否重置该用户密码为初始默认密码?', function(dlg) {
				var params = {};
				params.id = id;
				$.post(URLS.resetPasswordById, params, function(result) {
					var err = $.err(result);
					if (err) {
						$.dlg.msg(err.message);
					}
					EL.grid.datagrid('reload');
					dlg.close();
				});
			});
		}else if ('system' === invoke) {
			$.dlg.open({
				title : '关联系统',
				area : [ '500px', '500px' ],
				content : URLS.systemChoosePage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					EL.grid.datagrid('reload');
				}
			});
		}
	});

	$(window).resize(function() {
		EL.gridWrap.height($(window).height() - 100);
	}).trigger('resize')
});