var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};

URLS.loadMenuTreeByPermCode = _ctx + '/auth_permission/loadMenuTreeByPermCode.do';
URLS.editPasswordPage = _ctx + '/auth_user/auth_user_password.html';

$(function() {

	EL.navAccordion = $('#nav-accordion');
	EL.password = $('#password');
	EL.oFrame = $('#oFrame');

	EL.password.on('click', function() {
		$.dlg.open({
			title : '修改密码',
			area : [ '430px', '280px' ],
			content : URLS.editPasswordPage
		});
	});

	//加载菜单树
	FN.loadMenu = function() {
		$.mask();
		var params = {};
		params.code = 'PORTAL';
		$.post(URLS.loadMenuTreeByPermCode, params, function(nodes) {
			$.unmask();
			var activePath = EL.navAccordion.html((function() {
				var html = '';
				$.each(nodes, function(i, node) {
					html += '<li class="sub-menu">';
					html += ' <a href="javascript:;" class="' + (i == 0 ? 'active ' : '') + '">';
					html += '  <i class="' + $.trim(node.iconCls) + '"></i>';
					html += '  <span>' + $.encodeHTML(node.name) + '</span>';
					html += ' </a>';
					html += ' <ul class="sub">';
					$.each(node.children || [], function(i, cnode) {
						var href = 'about:blank';
						if (cnode.path) {
							href = _ctx + $.encodeHTML(cnode.path);
						}
						html += '  <li class="' + (i === 0 ? 'active' : '') + '"><a href="' + href + '" target="oFrame">' + $.encodeHTML(cnode.name) + '</a></li>';
					});
					html += ' </ul>';
					html += '</li>';
				});
				return html;
			})()).each(function() {
				$(this).dcAccordion({
					autoExpand : true
				});
			}).find('ul.sub li.active a').attr('href') || 'about:blank';

			EL.oFrame.attr('src', activePath);
		});
	}
	FN.loadMenu();
});