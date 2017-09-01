<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/auth_permission/auth_permission_edit.js"></script>
<style type="text/css">
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
		<div class='add_box' style='margin-top:30px;'>
			<div class="add_box_in pt-page-scaleUpCenter">
				<ul class='add_box_in_ul'>
					<li class="add_box_in_uli">
						<span>名称: <span id="redSpan">*</span></span>
						<input name="name" type="text" maxlength="20">
					</li>
					<li class="add_box_in_uli">
						<span>类别: </span>
						<select id="type" name="type" class="form-control m-bot15 form-control_new" style="color:#333;">
							<option value="1" selected="selected">模块</option>
							<option value="2">菜单</option>
							<!-- <option value="3">按钮</option> -->
						</select>
					</li>
					<li class="add_box_in_uli">
						<span>上级: </span>
						<input id="parentName" name="parentName" type="text" class="x-icon-menu-" style="color:#ccc;" readonly="readonly">
						<input id="parentId" name="parentId" type="hidden">
					</li>
					<li class="add_box_in_uli">
						<span>排序: </span>
						<input id="ordinal" name="ordinal" type="text" maxlength="8">
					</li>
					<li class="add_box_in_uli">
						<span>图标: </span>
						<input name="iconCls" type="text">
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>编码: <span id="redSpan">*</span></span>
						<input name="code" type="text" maxlength="50" style='width:465px;'>
					</li>
					
					<li class="add_box_in_uli add_box_in_textarea">
						<span>路径: </span>
						<input name="path" type="text" maxlength="1000" style='width:465px;'>
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