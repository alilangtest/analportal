<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/style.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_role/auth_role_main.js"></script>
<style type="text/css">
body, html {
	height: 100%;
	background:#fff;
}
.btn-group>.btn:last-child:not(:first-child), .btn-group>.dropdown-toggle:not(:first-child) {
    border-radius: 4px;
    margin-right: 10px;
}
</style>
<script type="text/javascript">

</script>
</head>
<body class='table_css table_css2'>
	<div class="inbox-body">
		<div class="btn-group">
			<button id="add" class="btn green btn_gray new_add">
				<i class="fa fa-plus"></i>&nbsp;新建 
			</button>
		</div>
		<div class="btn-group text-right float-right" style=''>
			<div class='float-rightd'>
				<input id="keyword" type="text" class="form-control" placeholder="角色名称">
			</div>
			<button id="search" class="btn btn-info green">搜索</button>
			<button id="empty" class="btn btn-default green">清空</button>
		</div>
	</div>
	<div id="grid-wrap" class='table_table'>
		<table id="grid" style='width: 100%;'></table>
	</div>
</body>
</html>