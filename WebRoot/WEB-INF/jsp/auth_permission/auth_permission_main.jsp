<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/style.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_permission/auth_permission_main.js"></script>
<style type="text/css">
body, html {
	height: 100%;
	background:#fff;
}

.user-head {
	background-color: #fff;
}

.inbox-head {
	background-color: #1d8b7e;
}

.mail-box .sm-side .user-head {
	background-color: rgba(29, 139, 126, 0.87);
}

.user-head .user-name {
	margin-top: 8px;
}

.inbox-head h3 {
	font-size: 18px;
	margin-top: 6px;
}

.user-head .user-name h5 a {
	font-size: 16px;
}

ul.labels-info li h4 {
	background-color: rgba(29, 139, 126, 0.87);
}

.btn.btn_gray {
	margin-right: 2px;
}

.inbox-head .sr-btn {
	background-color: rgba(255, 255, 255, 0.2);
}
</style>
</head>
<body class='table_css table_css2'>
	<div class="inbox-body">
		<div class="btn-group">
			<button id="add" class="btn green btn_gray new_add">
				<i class="fa fa-plus"></i>&nbsp;新建根菜单 
			</button>
			<!-- <button id="refresh" class="btn green btn_gray new_add">
				刷新<i class="fa fa-refresh"></i>
			</button> -->
			<!-- <button id="refreshIndexes" class="btn green btn_gray new_add">
				<i class="fa fa-certificate"></i>&nbsp;刷新索引 
			</button> -->
		</div>
		<div class="btn-group text-right float-right" style=''></div>
	</div>
	<div id="grid-wrap" class='table_table'>
		<table id="treegrid" style='width: 100%;'></table>
	</div>
</body>
</html>