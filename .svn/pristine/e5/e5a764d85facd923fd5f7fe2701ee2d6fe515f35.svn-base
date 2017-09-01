var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.load = _ctx + '/auth_role/load.do';
URLS.save = _ctx + '/auth_role/update.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.id = GV.search.id;

	EL.window = $(window);
	EL.form = $('#form');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');

	//
	if ($.isEmpty(GV.id)) {
		EL.form.vals({});
	} else {
		var params = {};
		params.id = GV.id;
		$.mask();
		$.post(URLS.load, params, function(result) {
			$.unmask();
			EL.form.vals(result);
		});
	}

	EL.confirm.on('click', function() {
		var params = EL.form.vals();
		params.name = $.trim(params.name);
		params.remark = $.trim(params.remark);

		var errors = {};

		if ($.isEmpty(params.name)) {
			errors.name = '角色名称不能为空';
		}
		if (!$.isEmptyObject(errors)) {
			EL.form.markInvalid(errors);
			return;
		}
		$.post(URLS.save, params, function(result) {
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