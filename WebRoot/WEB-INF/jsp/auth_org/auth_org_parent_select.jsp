<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_org/auth_org_parent_select.js"></script>
<style type="text/css">
body, html {
	height: 100%;
}
.btn-shadow.btn-info,.btn-shadow.btn-default{
	box-shadow:0px 0px #fff;
}
</style>
</head>
<body class='table_css table_css2'>
	<form id="form">
		<div style="height:350px">
			<ul id="tree" style='margin-top: 20px; width: 90%; margin-left: 5%;'>

			</ul>
		</div>
		<div class="button_succ">
			<button id="confirm" type="button" class="btn btn-shadow btn-info">确定</button>
			<button id="cancel" type="button" class="btn btn-shadow btn-default">取消</button>
		</div>
	</form> 
</body>
</html>