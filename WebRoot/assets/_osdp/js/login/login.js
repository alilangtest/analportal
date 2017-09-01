var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.login = _ctx + '/login.html';// 登录
URLS.portal = _ctx + '/admin/index.html';// 首页
URLS.doLogin = _ctx + '/login.do';// 登录
//
$(function() {
	
	// 防止登录页被嵌套到iframe
	if (window.location.href != window.top.location.href) {
		var allowEmbedded = $.decodeUrlParams()['allowEmbedded'];
		if (allowEmbedded !== false) {
			try {
				window.top.location.href = URLS.login;
			} catch (e) {
			}
		}
	}

	EL.username = $('#username');
	EL.password = $('#password');
	EL.submit = $('#submit');

	// 获得焦点，清除提示文字
	EL.username.focus(function() {
		if (EL.username.attr('placeholder')) {
			EL.username.attr('placeholder', '');
		}
	});
	EL.password.focus(function() {
		if (EL.password.attr('placeholder')) {
			EL.password.attr('placeholder', '');
		}
	});

	// 失去焦点，显示提示文字
	EL.username.blur(function() {
		if ($.trim(EL.username.val()) == '') {
			EL.username.val('');
			EL.username.attr('placeholder', '请输入账号');
		}
	});
	EL.password.blur(function() {
		if (EL.password.val() == '') {
			EL.password.attr('placeholder', '请输入密码');
		}
	});

	// 登录
	EL.submit.on('click', function() {
		var params = {};
		params.username = EL.username.val();
		params.password = EL.password.val();

		if (params.username == '') {
			layer.tips('请输入账号!', '#username', {
				time : 1500
			});
			return;
		}

		if (params.password == '') {
			layer.tips('请输入密码!', '#password', {
				time : 1500
			});
			return;
		}

		$.post(URLS.doLogin, params, function(map) {
//			var err = $.err(response)
			if (map.success) {
//				$.mask();
				location.href = URLS.login;
			} else {
				layer.msg(map.msg, {
					msg : [ '#BB0000' ],
					shift : 5,
					time : 1500
				});
			}
		});
	});
});
function BindEnter() {
	if (event.keyCode == 13) {
		var params = {};
		params.username = EL.username.val();
		params.password = EL.password.val();

		if (params.username == '') {
			layer.tips('请输入账号!', '#username', {
				time : 1500
			});
			return;
		}

		if (params.password == '') {
			layer.tips('请输入密码!', '#password', {
				time : 1500
			});
			return;
		}

		$.post(URLS.doLogin, params, function(map) {
//			var err = $.err(response)
			if (map.success) {
//				$.mask();
				location.href = URLS.login;
			} else {
				layer.msg(map.msg, {
					msg : [ '#BB0000' ],
					shift : 5,
					time : 1500
				});
			}
		});
		/*$.post(URLS.doLogin, params, function(response) {
			var err = $.err(response)
			if (err) {
				layer.msg(err.message, {
					msg : [ '#BB0000' ],
					shift : 5,
					time : 1500
				});
			} else {
				$.mask();
				location.href = URLS.index;
			}
		});*/
	}
}