var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.selectParentPage = _ctx + '/auth_org/auth_org_parent_select.html';
URLS.load = _ctx + '/auth_org/load.do';
URLS.save = _ctx + '/auth_org/save.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.id = GV.search.id;
	GV.parentId = GV.search.parentId;
	GV.parentName = GV.search.parentName;

	EL.window = $(window);
	EL.form = $('#form');

	EL.parentName = $('#parentName');
	EL.parentId = $('#parentId');
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
			EL.form.vals(result);
		});
	}

	EL.parentName.on('click', function() {
		$.dlg.open({
			title : '选择上级部门',
			area : [ '470px', '500px' ],
			content : URLS.selectParentPage + '?' + $.encodeUrlParams({
				id : GV.id
			}),
			end : function(result) {
				if (result) {
					EL.parentId.val(result.id);
					EL.parentName.val(result.namePath || result.name);
				}
			}
		});
	});

	EL.confirm.on('click', function() {
		var params = EL.form.vals();
		params.name = $.trim(params.name);
		params.remark = $.trim(params.remark);
		params.code = $.trim(params.code);
		params.ordinal = $.trim(params.ordinal);
		params.phone = $.trim(params.phone);
		params.fax = $.trim(params.fax);
		
		var errors = {};
		var faxRegx =  /^(\d{3,4}-)?\d{7,8}$/;
		if(!$.isEmpty(params.phone)){
			if(!faxRegx.test(params.phone)){
				errors.phone = '固定电话格式错误';
			}
		}
		if(!$.isEmpty(params.fax)){
			if(!faxRegx.test(params.fax)){
				errors.fax = '传真格式错误';
			}
		}
		if ($.isEmpty(params.name)) {
			errors.name = '机构名称不能为空';
		}
		if ($.isEmpty(params.code)) {
			errors.code = '编码不能为空';
		}
		var ordinalRegx =  /^[0-9]*$/;
		if(!$.isEmpty(params.ordinal)){
			if(!ordinalRegx.test(params.ordinal)){
				errors.ordinal = '排序必须为数字！';
			}
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