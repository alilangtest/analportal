var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.load = _ctx + '/auth_role/load.do';
URLS.save = _ctx + '/auth_role/save.do';
URLS.getById = _ctx + '/auth_role/getById.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.id = GV.search.id;
	EL.window = $(window);
	EL.form = $('#form');
	EL.confirm = $('#confirm');
	EL.cancel = $('#cancel');
	EL.id = $('#id');
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
	
	EL.id.on('blur',function(){
		var params = EL.form.vals();
		params.id = $.trim(params.id);
		alert("params.id=="+params.id);
		if (!$.isEmpty(params.id)) {
			$.ajax({
				url:URLS.getById,
				type:"post",
				data:{
					id:params.id
				},
				dataType:"text",
				success:function(o){
					if(o=="1"){
						errors.id = '角色ID重复';
					}
				}
			})
		}
		
	});

	EL.confirm.on('click', function() {
		var params = EL.form.vals();
		params.id = $.trim(params.id);
		params.name = $.trim(params.name);
		params.remark = $.trim(params.remark);

		var errors = {};
		
		var reg=new RegExp("^[0-9a-zA-Z]*$");
		if (!reg.test(params.id)) {
			errors.id = '角色ID只能为字母数字';
//			alert("params.id==="+params.id);
		}
		
		if ($.isEmpty(params.id)) {
			errors.id = '角色ID不能为空';
		}else{
			$.ajax({
				url:URLS.getById,
				type:"post",
				data:{
					id:params.id
				},
				async:false,
				dataType:"text",
				success:function(o){
					if(o==1){
						errors.id = '角色ID重复';
					}
				}
			})
		}
		
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