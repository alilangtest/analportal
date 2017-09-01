var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.editPage = _ctx + '/auth_org/auth_org_edit.html';
URLS.loadTree = _ctx + '/auth_org/loadTree.do';
URLS.load = _ctx + '/auth_org/load.do';
URLS.save = _ctx + '/auth_org/save.do';
URLS.remove = _ctx + '/auth_org/remove.do';
URLS.forceRefreshIndexes = _ctx + '/auth_org/forceRefreshIndexes.do';

$(function() {

	EL.window = $(window);
	EL.add = $('#add');
	EL.refresh = $('#refresh');
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
			field : 'code',
			title : '编码',
			fixed : true,
			width : 100,
			hidden : false
		}, {
			field : 'name',
			title : '名称',
			width : 100
		}, {
			field : 'phone',
			title : '电话',
			fixed : true,
			width : 100
		}, {
			field : 'address',
			title : '地址',
			width : 100
		}, {
			field : 'remark',
			title : '备注',
			width : 120
		}, {
			field : 'ordinal',
			title : '排序号',
			fixed : true,
			width : 50
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
		} ] ],
		loadFilter : function(nodes, parentId) {
			var map = function(nodes) {
				return $.map(nodes, function(node) {
					node.children = map(node.children);
					return node;
				}).sort(function(d1, d2) {
					var //
					ordinal1 = d1.ordinal == null ? Number.MAX_VALUE : d1.ordinal, //
					ordinal2 = d2.ordinal == null ? Number.MAX_VALUE : d2.ordinal, //
					name1 = d1.name || ' ', //
					name2 = d2.name || ' ';
					return ordinal1 < ordinal2 ? -1 : ordinal1 > ordinal2 ? 1 : name1.localeCompare(name2);
				});
			};
			return map(nodes);
		}
	});

	FN.refresh = function() {
		$.mask();
		$.post(URLS.loadTree, function(nodes) {
			$.unmask();
			var err = $.err(nodes);
			if (err) {
				$.dlg.msg(err.message);
				return;
			}
			EL.treegrid.treegrid('loadData', nodes)
		});
	};
	FN.refresh();

	EL.refresh.on('click', function() {
		FN.refresh();
	});

	EL.add.on('click', function() {
		$.dlg.open({
			title : '新建',
			area : [ '700px', '420px' ],
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
				area : [ '700px', '420px' ],
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