<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_user/auth_user_role_choose.js"></script>
<style type="text/css">
body, html {
	height: 100%;
}
</style>
<script type="text/javascript">

</script>
</head>
<body class='table_css table_css2'>
	<form id="form">
		<span style="margin-left:15px; margin-top: 7px; float: left;">角色类型:</span>
		<select style="width:200px;margin-left:85px;margin-top: 3px;" id="type" class="form-control m-bot15 form-control_new" name="type">
			
		</select>
		<div style="height:370px" id="div1">
			<table id="grid"> </table>
		</div>
		<div class="button_succ">
			<button id="confirm" type="button" class="btn btn-shadow btn-info">确定</button>
			<button id="cancel" type="button" class="btn btn-shadow btn-default">取消</button>
		</div>
	</form> 
</body>
</html>