<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder"%>
<%@include file="../_include/_taglib.jsp"%>
<%
	String from = request.getParameter("from");
	String useid=UserHolder.getId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/tableau/tableauUserView.js"></script>
</head>
<body>
	
<form id="savePcTableauConfig" method="post">
	<div class="add_box" style='' id='add_box'>
		<div class="add_box_in pt-page-scaleUpCenter">
			<h4>查看</h4>
			<ul class="add_box_in_ul">
				<li class="add_box_in_uli"><span>tableau用户名: </span> 
					<input id="username" name="username" readonly="readonly">
				</li>
				<li class="add_box_in_uli"><span>tableau密码: </span> 
					<input id="password" name="password" readonly="readonly">
				</li>
				<li class="add_box_in_uli"><span>项目名称: </span> 
					<input id="projectname" name="projectname" readonly="readonly">
				</li>
				<li class="add_box_in_uli"><span>tableau服务器IP: </span> 
					<input id="tableauip" name="tableauip" readonly="readonly">
				</li>
				<li class="add_box_in_uli"><span>操作类型:</span>
					<input id="optype" name="optype" readonly="readonly" />
					<!-- <select id="optype" name="optype" class="form-control m-bot15 form-control_new"></select> -->
				</li>	
			</ul>
			
			<div class="button_succ">
				<button onclick="closeTableauConfig()" type="button" class="btn btn-shadow btn-default">
					取消</button>
			</div>
		</div>
	</div>
</form>
	
	
	
	
	<!--<div>
	 <form id="savePcTableauConfig" method="post">
			<table align="center">
				<tr>
					<td>
						<input id ="userid" name="userid" type="hidden"/>
						<span style='width: 100%;'>tableau用户名：</span>
					</td>
					<td>
						<input id="username" name="username" readonly="readonly" />
					</td>
					<td><span style='width: 100%;'>tableau密码：</span></td>
					<td>
						<input id="password" name="password" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td><span style='width: 100%;'>项目名称：</span></td>
					<td>
						<input id="projectname" name="projectname" readonly="readonly" />
					</td>
					<td class='newtrtr'><span style='width: 100%;'>tableau服务器IP：</span></td>
					<td>
						<input id="tableauip" name="tableauip" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td><span style='width: 100%;'>操作类型</span></td>
					<td>
						<input id="optype" name="optype" readonly="readonly" />
					</td>
				</tr>
				<tr align="center">
					<td>
						<a href="#" onclick="closeTableauConfig()"><span>取消</span></a>
					</td>
				</tr>
			</table>
		</form>
	</div> -->
</body>
</html>