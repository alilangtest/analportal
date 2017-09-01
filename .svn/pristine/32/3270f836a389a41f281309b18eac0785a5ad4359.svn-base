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
<script type="text/javascript" src="${ctx}/assets/_osdp/js/tableau/tableauServerUpdate.js"></script>
</head>
<body>

<form id="savePcTableauConfig" method="post">
	<div class="add_box" style='' id='add_box'>
		<div class="add_box_in pt-page-scaleUpCenter">
			<h4>修改</h4>
			<ul class="add_box_in_ul">
				<li><input id ="serverid" name="serverid" type="hidden"/></li>
				<li class="add_box_in_uli"><span>tableau服务器IP: </span> 
					<input id="tableauserverip" name="tableauserverip" type="text" placeholder="请填写tableau服务器IP">
				</li>
				<li class="add_box_in_uli"><span>postgreURL: </span> 
					<input id="postgreurl" name="postgreurl" type="text" placeholder="请填写postgreurl">
				</li>
				<li class="add_box_in_uli"><span>postgre用户名: </span> 
					<input id="postgreuname" name="postgreuname" type="text" placeholder="请填写postgre用户名">
				</li>
				<li class="add_box_in_uli"><span>postgre密码: </span> 
					<input id="postgrepwd" name="postgrepwd" type="text" placeholder="请填写postgre密码">
				</li>
			</ul>
			<div class="button_succ">
				<button id="savebutton" onclick="saveTableauConfig()" type="button" class="btn btn-shadow btn-info">
					确定</button>
				<button onclick="closeTableauConfig()" type="button" class="btn btn-shadow btn-default">
					取消</button>
			</div>
		</div>
	</div>
</form>


	
	<!-- <div>
		<form id="savePcTableauConfig" method="post">
			<table align="center">
				<tr>
					<td>
						<input id ="serverid" name="serverid" type="hidden"/>
						<span style='width: 100%;'>tableau服务器IP：</span>
					</td>
					<td>
						<input id="tableauserverip" name="tableauserverip" />
					</td>
					<td><span style='width: 100%;'>postgreURL：</span></td>
					<td>
						<input id="postgreurl" name="postgreurl" />
					</td>
				</tr>
				<tr>
					<td><span style='width: 100%;'>postgre用户名：</span></td>
					<td>
						<input id="postgreuname" name="postgreuname" />
					</td>
					<td class='newtrtr'><span style='width: 100%;'>postgre密码：</span></td>
					<td>
						<input id="postgrepwd" name="postgrepwd" />
					</td>
				</tr>
				<tr align="center">
					<td>
						<a href="#" id="savebutton" onclick="saveTableauConfig()"><span>保存</span></a> 
						<a href="#" onclick="closeTableauConfig()"><span>取消</span></a>
					</td>
				</tr>
			</table>
		</form>
	</div> -->

</body>
</html>