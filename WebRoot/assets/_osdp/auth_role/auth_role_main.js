var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.editPage = _ctx + '/auth_role/auth_role_edit.html';
URLS.addPage = _ctx + '/auth_role/auth_role_add.html';
URLS.permissionChoosePage = _ctx + '/auth_role/auth_role_permission_choose.html';
URLS.userChoosePage = _ctx + '/auth_role/auth_role_user_choose.html';
URLS.pagedQuery = _ctx + '/auth_role/pagedQuery.do';
URLS.load = _ctx + '/auth_role/load.do';
URLS.save = _ctx + '/auth_role/save.do';
URLS.remove = _ctx + '/auth_role/remove.do';

$(function() {

	EL.window = $(window);
	EL.add = $('#add');
	EL.keyword = $('#keyword');
	EL.search = $('#search');
	EL.empty = $('#empty');
	EL.tree = $('tree');
	EL.gridWrap = $('#grid-wrap')
	EL.grid = $('#grid');

	EL.grid.datagrid({
		url : URLS.pagedQuery,
		pagination : true,
		columns : [ [ {
			field : 'name',
			title : '角色',
			width : 150,
			fixed : true,
			align : 'center'
		},{
			field : 'typeStr',
			title : '角色类型',
			width : 100,
			fixed : true,
			align : 'center'
		},{
			field : 'remark',
			title : '备注',
			width : 100,
			halign : 'center'
		}, {
			field : '_operation',
			title : '操作',
			fixed : true,
			width : 200,
			halign : 'center',
			align : 'center',
			formatter : function(value, row) {
				var html = '';
				html += '<div data-record="' + $.encodeHTML($.encode(row)) + '">';
				html += ' <a data-invoke="edit" class="x-link">编辑</a>';
				if(row.type == "1"){
					html += ' <a data-invoke="permission" class="x-link">授权</a>';
				}else{
					html += ' <a class="x-link x-disable">授权</a>';
				}
				html += ' <a data-invoke="remove" class="x-link">删除</a>';
				html += ' <a data-invoke="user" class="x-link">关联用户</a>';
				html += '</div>';
				return html;
			}
		} ] ]
	});

	EL.add.on('click', function() {
		$.dlg.open({
			title : '新增',
			area : [ '470px', '420px' ],
			content : URLS.addPage,
			end : function() {
				EL.grid.datagrid('reload');
			}

		});
	});

	//	
	EL.gridWrap.on('click', '[data-invoke]', function(e) {
		e.stopPropagation();
		var el = $(this), //
		invoke = el.attr('data-invoke'), //
		record = $.decode(el.parent().attr('data-record')), //
		id = record.id;

		if ('edit' === invoke) {
			$.dlg.open({
				title : '编辑',
				area : [ '600px', '370px' ],
				content : URLS.editPage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					EL.grid.datagrid('reload');
				}
			});
		} else if ('remove' === invoke) {
			$.dlg.confirm('是否确认删除?', function(dlg) {
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
		} else if ('permission' === invoke) {
			$.dlg.open({
				title : '授权',
				area : [ '400px', '500px' ],
				content : URLS.permissionChoosePage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					EL.grid.datagrid('reload');
				}
			});
		} else if ('user' === invoke) {
			$.dlg.open({
				title : '关联用户',
				area : [ '500px', '500px' ],
				content : URLS.userChoosePage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					EL.grid.datagrid('reload');
				}
			});
		}
	});

	EL.search.on('click', function() {
		var keyword = $.trim(EL.keyword.val());
		EL.grid.datagrid('load', {
			name : keyword
		});
	});

	EL.empty.on('click', function() {
		EL.keyword.val('');
		EL.grid.datagrid('load', {});
	});

	$(window).resize(function() {
		$('#grid-wrap').height($(window).height() - 100);
	}).trigger('resize')
});