var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.load = _ctx + '/auth_permission/load.do';
URLS.save = _ctx + '/auth_permission/save.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.id = GV.search.id;
	GV.parentId = GV.search.parentId;
	GV.parentName = GV.search.parentName;

	EL.window = $(window);
	EL.form = $('#form');

	EL.parentName = $('#parentName');
	EL.parentId = $('#parentId');
	EL.type = $('#type');
	EL.ordinal = $('#ordinal');

	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');

	//
	if ($.isEmpty(GV.id)) {
		EL.form.vals({
			parentId : GV.parentId || '',
			parentName : GV.parentName || ''
		});
	} else {
		var params = {};
		params.id = GV.id;
		$.mask();
		$.post(URLS.load, params, function(result) {
			$.unmask();
			GV.parentId = result.parentId;
			EL.form.vals(result);
		});
	}

	if (GV.parentId === '0') {
		EL.type.find('option').each(function() {
			this.value !== '1' && $(this).remove();
		});
	}

	EL.confirm.on('click', function() {
		var params = EL.form.vals();
		params.name = $.trim(params.name);
		params.code = $.trim(params.code);
		params.remark = $.trim(params.remark);

		var errors = {};

		if ($.isEmpty(params.name)) {
			errors.name = '功能名称不能为空';
		}
		if ($.isEmpty(params.code)) {
			errors.code = '功能编码不能为空';
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