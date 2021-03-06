<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_role/auth_role_edit.js"></script>
<style type="text/css">
#redSpan{
	color:red;
	width: 0px;
	min-width: 0px;
}
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<form id="form">
		<input id="id" name="id" type="hidden">
		<div class='add_box'>
			<div class="add_box_in pt-page-scaleUpCenter">
				<ul class='add_box_in_ul'>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>角色名称: <span id="redSpan">*</span></span>
						<input name="name" type="text" placeholder="角色名称" maxlength="20">
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>角色类型: <span id="redSpan">*</span></span>
						<select class="form-control m-bot15 form-control_new" name="type">
							<c:forEach items="${list}" var="obj">
								<option value="${obj.codeid }">${obj.codename }</option>
							</c:forEach>
						</select>
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<em>备注: </em> 
						<textarea name="remark" class="form-control" id="remark" style='height:150px;' maxlength="400"></textarea>
					</li>
				</ul>
				<div class="button_succ">
					<button id="confirm" type="button" class="btn btn-shadow btn-info">确定</button>
					<button id="cancel" type="button" class="btn btn-shadow btn-default">取消</button>
				</div>
			</div>
		</div>
	</form> 
</body>
</html>