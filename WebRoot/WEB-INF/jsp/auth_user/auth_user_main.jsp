<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/style.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_user/auth_user_main.js"></script>
<style type="text/css">
body, html {
	height: 100%;
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
.btn-group>.btn:last-child:not(:first-child), .btn-group>.dropdown-toggle:not(:first-child) {
	    border-radius: 4px;
	    margin-right: 10px;
	}
</style>
</head>
<body class='table_css table_css2 table_css_tree'>
	<div style="width:20%;float: left;height: 100%;overflow-y: auto;background-color: #fff;">
		<aside class="sm-side" style="width: 100%;">
			<ul id="tree" style='margin-top: 20px; width: 90%; margin-left: 5%;'>
			</ul>
		</aside>
	</div>
	<div class="mail-box" style="width:80%;float: right;overflow-y: auto;">
		<aside class="lg-side" style="width: 100%;">
			<div class="inbox-body">
				<div class="btn-group">
					<button id="add" class="btn green btn_gray new_add">
						<i class="fa fa-plus"></i> 新建
					</button>
				</div>
				<div class="btn-group text-right float-right" style=''>
					<div class='float-rightd'>
						<input id="keyword" type="text" class="form-control" placeholder="用户账户">
					</div>
					<button id="search" class="btn btn-info green">搜索</button>
					<button id="searchAll" class="btn btn-info green">全局搜索</button>
					<button id="empty" class="btn btn-default green">清空</button>
				</div>
			</div>
			<div id="grid-wrap" class='table_table'>
				<table id="grid" style='width: 100%;'></table>
			</div>
		</aside>
	</div>
</body>
</html>