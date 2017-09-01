var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.loadTree = _ctx + '/auth_permission/loadTree.do';
URLS.updateRolePermissions = _ctx + '/auth_role/updateRolePermissions.do';
URLS.findPermissionId = _ctx + '/auth_role/findPermissionId.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.roleId = GV.search.id;

	EL.window = $(window);
	EL.tree = $('#tree');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');

	//机构
	EL.tree.tree({
		checkbox : true,
		loadFilter : function(nodes) {
			var map = function(nodes) {
				return $.map(nodes, function(node) {
					return {
						id : node.id,
						text : node.name,
						state : 'open',
						attributes : node,
						children : map(node.children || [])
					}
				});
			};
			return map(nodes);
		},
		onBeforeCollapse : function() {
			return false;
		}
	});

	FN.refresh = function() {
		$.mask();
		$.post(URLS.loadTree,function(nodes) {
			EL.tree.tree('loadData', nodes);
			var params = {};
			params.id = GV.roleId;
			$.post(URLS.findPermissionId, params, function(permissionIds) {
				$.unmask();
				EL.tree.tree('uncheckAll');
				$.each(permissionIds, function(i, permissionId) {
					EL.tree.tree('checkById', permissionId);
				})
			});
		});
	};
	FN.refresh();

	EL.confirm.on('click', function() {
		var nodes = EL.tree.tree('getChecked');
		var params = {
			roleId : GV.roleId,
			permissionIds : $.map(nodes, function(node) {
				return node.id;
			})
		};

		$.mask();
		$.post(URLS.updateRolePermissions, params, function(result) {
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