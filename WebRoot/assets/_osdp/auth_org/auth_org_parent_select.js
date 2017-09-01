var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.orgAsyncTree = _ctx + '/auth_org/asyncTree.do';

$(function() {

	GV.ORG_ROOT_ID = '0';
	GV.search = $.decodeUrlParams();
	GV.id = GV.search.id;

	EL.window = $(window);
	EL.tree = $('#tree');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');

	//机构
	EL.tree.tree({
		url : URLS.orgAsyncTree,
		loadFilter : function(nodes, parentDom) {
			var map = function(nodes) {
				return $.map($.grep(nodes, function(node) {
					return node.id !== GV.id && !(!!node.idPath && node.idPath.indexOf(GV.id) !== -1);
				}), function(node) {
					node = {
						id : node.id,
						text : node.name,
						state : node.id === GV.ORG_ROOT_ID ? 'open' : 'closed',
						attributes : node,
						children : map(node.children || [])
					}
					return node;
				});
			};

			if (parentDom == null) {
				nodes = [ {
					id : '0',
					name : '顶层机构',
					namePath : '/',
					iconCls : 'icon-house',
					children : nodes
				} ]
			}
			return map(nodes);
		},
		onBeforeCollapse : function(node) {
			return node.id !== GV.ORG_ROOT_ID;
		}
	});

	EL.confirm.on('click', function() {
		var selected = EL.tree.tree('getSelected');
		if (selected == null) {
			$.dlg.msg('请选择一个机构');
			return;
		}
		$.dlg.close(selected.attributes);
	});

	EL.cancel.on('click', function() {
		$.dlg.close();
	});

});