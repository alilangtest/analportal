var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.orgAsyncTree = _ctx + '/auth_org/asyncTree.do';

$(function() {

	EL.window = $(window);
	EL.tree = $('#tree');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');

	//机构
	EL.tree.tree({
		url : URLS.orgAsyncTree,
		loadFilter : function(node) {
			return $.map(node, function(node) {
				return {
					id : node.id,
					text : node.name,
					state : 'closed',
					attributes : node
				}
			});
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