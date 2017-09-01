var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.updatePassword = _ctx + '/show/updatePassword.do';

$(function() {

	EL.window = $(window);
	EL.form = $('#form');
	EL.confirm = $('#confirm');

	EL.confirm.on('click', function() {
		var params = EL.form.vals();
		
		//密码长度校验
		var password = params.newPasswordConfirm;
		var len = strlen(password);
		if(len<6){
			layer.alert("密码长度不能少于6位！");
		}else if(password == "111111"){
			layer.confirm('本次修改密码跟默认密码一样，下次登录还会提醒修改密码，是否修改？', {
				  area : [ '330px', '150px' ],
				  btn: ['确认','取消'] //按钮
				}, function(){
					//确认
					commit(params);
				}, function(){
					//取消
			});
		}else{
			commit(params);
		}
		
	});
	function commit(params){
		var errors = {};

		if ($.isEmpty(params.newPasswordConfirm)) {
			errors.newPasswordConfirm = '密码不能为空';
		} else if (params.newPassword != params.newPasswordConfirm) {
			errors.newPasswordConfirm = '两次密码输入不一致';
		}

		if (!$.isEmptyObject(errors)) {
			EL.form.markInvalid(errors);
			return;
		}

		$.post(URLS.updatePassword, params, function(result) {
			var err = $.err(result);
			if (err) {
				layer.msg(err.message, {
					msg : [ '#BB0000' ],
					shift : 5,
					time : 1500
				});
			} else {
				$.dlg.close();
			}
		});
	}
	
	function strlen(str) {
		var len = 0;
		for (var i = 0; i < str.length; i++) {
			var c = str.charCodeAt(i);
			//单字节加1 
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				len++;
			} else {
				len += 2;
			}
		}
		return len;
	}
});