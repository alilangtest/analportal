<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_user/auth_role_user_choose.js"></script>
<style type="text/css">
body, html {
	height: 100%;
}
</style>
<script type="text/javascript">
var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {};

URLS.findRoleUserByRoleId = _ctx + '/auth_role/findRoleUserByRoleId.do';

$(function() {

	GV.search = $.decodeUrlParams();
	GV.roleId = GV.search.id;

	EL.window = $(window);
	EL.grid = $('#grid');
	EL.cancel = $('#cancel');

	EL.grid.datagrid({
		url : URLS.pagedQuery,
		columns : [ [ {
			field : 'userName',
			title : '账号',
			fixed : true,
			align : 'center'
		},{
			field : 'name',
			title : '用户名称',
			width : 150,
			fixed : true,
			align : 'center'
		}, {
			field : 'orgName',
			title : '机构',
			width : 100,
			halign : 'center'
		} ] ]
	});

	FN.refresh = function() {
		$.mask();
		var params = {};
		params.roleId = GV.roleId;
		$.post(URLS.findRoleUserByRoleId, params, function(records) {
			EL.grid.datagrid('loadData', records);
			$.unmask();
		});
	};
	FN.refresh();

	EL.cancel.on('click', function() {
		$.dlg.close();
	});

});
</script>
</head>
<body class='table_css table_css2'>
	<form id="form">
		<div style="height:380px">
			<table id="grid"> </table>
		</div>
		<div class="button_succ">
			<button id="cancel" type="button" class="btn btn-shadow btn-default">关闭</button>
		</div>
	</form> 
</body>
</html>