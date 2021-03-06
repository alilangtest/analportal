<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%>
<%
String menuparent=request.getParameter("menuid");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>

<script type="text/javascript" src="${ctx}/assets/_osdp/js/report/reportMenu/addReportMenu.js"></script>
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
<script type="text/javascript">
	var menuparent="<%=menuparent%>";
</script>
<script type="text/javascript">
</script>
</head>
<body>

<!--  ------------------------------------->

<form id="addReportMenuForm" method="post">
	<input type="hidden" id="menuparent" name="menuparent" >
	<div class="add_box" style='' id='add_box'>
		<div class="add_box_in pt-page-scaleUpCenter">
			<!-- <h4>新增</h4> -->
			<ul class="add_box_in_ul" style="width:100%;">
				<li class="add_box_in_uli"><span>菜单编号: <span id="redSpan">*</span></span> 
					<input  type="text" id="menuid" name='menuid'  onblur="aaa()"    placeholder="必须填写数字（不能为空）" >
						<div id="menuid_" style="color: red"></div>
				</li>
				<li class="add_box_in_uli"><span>菜单名称: <span id="redSpan">*</span></span> 
					<input type="text" id="menuname" name="menuname"   placeholder="不能为空" >
					<div id="menuname_" style="color: red"></div>
				</li>
				<li class="add_box_in_uli"><span>菜单描述: </span> 
					<input type="text" id="menudesp" name="menudesp"  >
				</li>
				<li class="add_box_in_uli"><span>备　　注: </span> 
					<input type="text" id="rem" name="rem"  >
				</li>
				<li class="add_box_in_uli"><span>排序号: <span id="redSpan">*</span></span> 
					<input type="text" id="ordinal" name="ordinal">
					<div id="ordinal_" style="color: red"></div>
				</li>
			</ul>
			<div class="button_succ">
				<button id="savebutton" disabled="disabled" type="button" class="btn btn-shadow btn-info">确定</button>
				<button id="closebutton"  type="button" class="btn btn-shadow btn-default">取消</button>
			</div>
		</div>
	</div>
</form>

<!--  -------------------------------------------->

</body>
</html>