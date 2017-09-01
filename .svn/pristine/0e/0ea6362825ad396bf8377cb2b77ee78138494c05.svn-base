<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>

<script type="text/javascript" src="${ctx}/assets/_osdp/js/report/reportMenu/updateReportMenu.js"></script>
<style type="text/css">
#redSpan{
	color:red;
	width: 0px;
	min-width: 0px;
}
.add_box_in_ul .add_box_in_uli input{
	margin-right:0px;
}
</style>
</head>
<body>
	

<form id="updateReportMenuForm" method="post">
	<input type="hidden" id="menuparent" name="menuparent" value="${menu.menuparent}">
	
	<div class="add_box" style='' id='add_box'>
		<div class="add_box_in pt-page-scaleUpCenter">
		<!-- 	<h4>修改</h4> -->
			<ul class="add_box_in_ul">
				<li class="add_box_in_uli"><span>菜单编号: <span id="redSpan">*</span></span> 
					<input  id="menuid" name='menuid'  value="${menu.menuid}" readonly="readonly">
				</li>
				<li class="add_box_in_uli"><span>菜单名称: <span id="redSpan">*</span></span> 
					<input type="text" id="menuname" name="menuname" onblur="menunameblur()" value="${menu.menuname}"  placeholder="不能为空" >
				</li>
				<li class="add_box_in_uli"><span>菜单描述: </span> 
					<input type="text" id="menudesp" name="menudesp"  value=" ${menu.menudesp}">
				</li>
				<li class="add_box_in_uli"><span>备　　注: </span> 
					<input type="text" id="rem" name="rem"  value="${menu.rem}">
				</li>
				<li class="add_box_in_uli"><span>排序号: <span id="redSpan">*</span></span> 
					<input type="text" id="ordinal" name="ordinal"  value="${menu.ordinal}">
					<input type="hidden" id="ordinal_hidden"  value="${menu.ordinal}">
					<div id="ordinal_" style="color: red"></div>
				</li> 
			</ul>
			<div class="button_succ">
				<button id="savebutton" onclick="saveUpdateReportMenu()" type="button" class="btn btn-shadow btn-info">确定</button>
				<button id="closebutton" onclick="closeReportMenu()" type="button" class="btn btn-shadow btn-default">取消</button>
			</div>
			
		</div>
	</div>
</form>

<!--  -------------------------------------------->
</body>
</html>