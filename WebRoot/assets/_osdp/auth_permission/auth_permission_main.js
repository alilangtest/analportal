var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.editPage = _ctx + '/auth_permission/auth_permission_edit.html';
URLS.loadTree = _ctx + '/auth_permission/loadTree.do';
URLS.save = _ctx + '/auth_permission/save.do';
URLS.remove = _ctx + '/auth_permission/remove.do';
URLS.forceRefreshIndexes = _ctx + '/auth_permission/forceRefreshIndexes.do';

$(function() {

	EL.window = $(window);
	EL.add = $('#add');
	//EL.refresh = $('#refresh');
	EL.refreshIndexes = $('#refreshIndexes');

	EL.gridWrap = $('#grid-wrap');
	EL.treegrid = $('#treegrid');

	EL.treegrid.treegrid({
		fitColumns : true,
		fit : true,
		idField : 'id',
		treeField : 'name',
		//rownumbers : true,
		columns : [ [ {
			field : 'name',
			title : '名称',
			halign : 'center',
			width : 100
		}, {
			field : 'type',
			title : '类型',
			halign : 'center',
			width : 50,
			fixed : true,
			formatter : function(value, row) {
				if (value == 0) {
					return '系统';
				} else if (value == 1) {
					return '模块';
				} else if (value == 2) {
					return '菜单';
				} else if (value == 3) {
					return '按钮';
				} else {
					return '功能';
				}
			}
		}, {
			field : 'code',
			title : '编码',
			halign : 'center',
			fixed : true,
			width : 100,
			hidden : false
		}, {
			field : 'path',
			title : '路径',
			halign : 'center',
			width : 100
		}, {
			field : 'ordinal',
			title : '排序号',
			halign : 'center',
			width : 50,
			fixed : true
		}, {
			field : 'remark',
			title : '备注',
			halign : 'center',
			width : 100
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
				html += ' <a data-invoke="childran" class="x-link">创建下级</a>';
				if ($.isEmpty(row.children)) {
					html += ' <a data-invoke="remove" class="x-link">删除</a>';
				} else {
					html += ' <a class="x-link x-disable">删除</a>';
				}
				html += '</div>';
				return html;
			}
		} ] ]
	});

	FN.refresh = function() {
		$.mask();
		$.post(URLS.loadTree, function(nodes) {
			$.unmask();
			var err = $.err(nodes);
			if (err) {
				layer.msg('刷新失败！', {
					msg : [ '#BB0000' ],
					shift : 5,
					time : 1500
				});
				return;
			}
			EL.treegrid.treegrid('loadData', nodes)
		});
	}

	FN.refresh();

	/*EL.refresh.on('click', function() {
		FN.refresh();
	});*/

	EL.add.on('click', function() {
		$.dlg.open({
			title : '新建模块',
			area : [ '700px', '500px' ],
			content : URLS.editPage + '?' + $.encodeUrlParams({
				parentId : '0',
				parentName : '/'
			}),
			end : function() {
				FN.refresh();
			}
		});
	});

	EL.refreshIndexes.on('click', function() {
		$.post(URLS.forceRefreshIndexes, function(result) {
			var err = $.err(result);
			if (err) {
				$.dlg.msg(err.message);
			}
			FN.refresh();
		});
	});

	EL.gridWrap.on('click', '[data-invoke]', function(e) {
		e.stopPropagation();
		var el = $(this), //
		invoke = el.attr('data-invoke'), //
		record = $.decode(el.parent().attr('data-record')), //
		id = record.id, name = record.name, namePath = record.namePath;

		if ('edit' === invoke) {
			$.dlg.open({
				title : '编辑',
				area : [ '700px', '500px' ],
				content : URLS.editPage + '?' + $.encodeUrlParams({
					id : id
				}),
				end : function() {
					FN.refresh();
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
					FN.refresh();
					dlg.close();
				});
			});
		} else if ('childran' === invoke) {
			$.dlg.open({
				title : '新建',
				area : [ '700px', '500px' ],
				content : URLS.editPage + '?' + $.encodeUrlParams({
					parentId : id,
					parentName : namePath || name
				}),
				end : function() {
					FN.refresh();
				}
			});
		}
	});

	$(window).resize(function() {
		EL.gridWrap.height($(window).height() - 100);
	}).trigger('resize')
});