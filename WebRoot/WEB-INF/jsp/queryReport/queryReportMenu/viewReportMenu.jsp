<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%><%
String from = request.getParameter("from");
String useid=UserHolder.getId();
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>
<script type="text/javascript">
 var userid="<%=useid%>";
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="po">
		<div class='add_box'>
			<div class="add_box_in pt-page-scaleUpCenter">
				<ul class='add_box_in_ul'>
					<li class="add_box_in_uli">
						<span>菜单编号：</span>
						<input id="menuid" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli">
						<span>菜单名称：</span>
						<input id="menuname" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli">
						<span>上级菜单：</span>
						<input id="menuParentName" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli">
						<span>排序号：</span>
						<input id="ordinal" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>菜单描述：</span>
						<!-- <input id="menudesp" readonly="readonly"/> -->
						<strong name="menudesp" class="form-control" id="menudesp" style='height:150px;'  readonly="readonly"></strong>
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>备　　注：</span>
						<!-- <input id="rem" readonly="readonly"> -->
						<strong name="rem" class="form-control" id="rem" style='height:150px;'  readonly="readonly"></strong>
					</li>
				</ul>
			</div>
		</div>
		<!-- <table align="center" class="detailtable">
			<tr>
			    <th width="20%" align="right"><span>菜单编号：</span></th>
				<td width="31%"><span id="menuid"></span></td>					
				<th width="25%" align="right"><span>菜单名称：</span></th>
				<td width="31%"><span id="menuname"></span></td>
			</tr>
			<tr>
				<th width="10%" align="right"><span>上级菜单：</span></th>
				<td colspan="3"><span id="menuParentName"></span></td>
				
			</tr>
			<tr>
			    <th width="10%" align="right"><span>菜单描述：</span></th>
				<td colspan="3"><span id="menudesp"></span></td>
			</tr>
			<tr>
			    <th width="10%" align="right"><span>备　　注：</span></th>
				<td colspan="3"><span id="rem"></span></td>
			</tr>
		</table> -->
	</div>
</body>
</html>