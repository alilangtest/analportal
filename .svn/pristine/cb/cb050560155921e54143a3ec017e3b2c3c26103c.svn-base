<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_org/auth_org_edit.js"></script>
<style type="text/css">
.btn-shadow.btn-success,.btn-shadow.btn-default{
	box-shadow:0 0 #9c9c9c;
}
#redSpan{
	color:red;
	width: 0px;
	min-width: 0px;
}
</style>
</head>
<body>
	<form id="form">
		<input id="id" name="id" type="hidden">
		<div class='add_box'>
			<div class="add_box_in pt-page-scaleUpCenter">
				<ul class='add_box_in_ul'>
					<li class="add_box_in_uli">
						<span>名称: <span id="redSpan">*</span></span>
						<input name="name" type="text" maxlength="50">
					</li>
					<li class="add_box_in_uli">
						<span>上级机构: </span>
						<input id="parentName" name="parentName" type="text" class="x-icon-house" readonly="readonly">
						<input id="parentId" name="parentId" type="hidden">
					</li>
					<li class="add_box_in_uli">
						<span>编码: <span id="redSpan">*</span></span>
						<input name="code" type="text" maxlength="50">
					</li>
					<li class="add_box_in_uli">
						<span>电话: </span>
						<input name="phone" type="text" maxlength="20">
					</li>
					<li class="add_box_in_uli">
						<span>传真: </span>
						<input name="fax" type="text" maxlength="20">
					</li>
					<li class="add_box_in_uli">
						<span>所在地址: </span>
						<input name="address" type="text" maxlength="200">
					</li>
					<li class="add_box_in_uli">
						<span>排序: </span>
						<input id="ordinal" name="ordinal" type="text" maxlength="8">
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<em>备注: </em> 
						<textarea name="remark" class="form-control" id="remark" style=';height:80px;' maxlength="400"></textarea>
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