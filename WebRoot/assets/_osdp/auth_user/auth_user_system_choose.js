var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.findSystemAll = _ctx + '/auth_user/findSystemAll.do';
URLS.findSystemIdByUserId = _ctx + '/auth_user/findUserSystem.do';
URLS.updateUserSystem = _ctx + '/auth_user/updateUserSystem.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.userId = GV.search.id;

	EL.window = $(window);
	EL.grid = $('#grid');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');

	EL.grid.datagrid({
		url : URLS.pagedQuery,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'clientName',
			title : '系统名称',
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

	FN.refresh = function() {
		$.mask();
		$.post(URLS.findSystemAll, function(records) {
			EL.grid.datagrid('loadData', records);
			var params = {};
			params.id = GV.userId;
			$.post(URLS.findSystemIdByUserId, params, function(roldIds) {
				$.unmask();
				EL.grid.datagrid('checkById', roldIds);
			});
		});
	};
	FN.refresh();

	EL.confirm.on('click', function() {
		var records = EL.grid.datagrid('getChecked');
		var params = {
			userId : GV.userId,
			roleIds : $.map(records, function(record) {
				return record.id;
			})
		};
		$.mask();
		$.post(URLS.updateUserSystem, params, function(result) {
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