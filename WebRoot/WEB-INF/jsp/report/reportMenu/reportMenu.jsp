<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%><%
String from = request.getParameter("from");
String useid=UserHolder.getId();
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/1.4.5/themes/bootstrap/easyui.css" />
	
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>
<script type="text/javascript">
  var userid="<%=useid%>";
</script>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/report/reportMenu/reportMenu.js"></script>
<style type="text/css">
body, html {
		overflow: auto;height:100%;
	}
	
	.panel-body {
		border: none;
		overflow:hidden;
	}
	
	.datagrid-header-row, .datagrid-row {
		height:40px;
	}
</style>
<script type="text/javascript">
</script>
</head>
<body class='table_css' style='overflow-y:auto;'>
	<!-- <div id="userquery">
		<form id="testform">
		<table align="center" class="querytable" id="queryDiv">
			<tr>
				
			</tr>
		</table>
		</form>
	</div>	 -->	
	<!-----------------------------------------------------------------------  -->
	
	<!-- 查询区 -->
	<section id="main-content">
		<section class="wrapper site-min-height">
			<section class="panel">
				<header class="panel-heading">
					<span class="label label-primary new_label">报表菜单维护</span> <span
						class="tools pull-right"></span>
				</header>

				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group">
								<button id="addReportMenu" 
									class="btn green btn_gray new_add">
									<i class="fa fa-plus"></i>&nbsp;新增根菜单 
								</button>
								<!-- <button id="deleteReportMenu" 
									class="btn green btn_gray new_del">
									<i class="fa fa-minus-circle"></i>&nbsp;删除 
								</button>
								<button id="updateReportMenu"
									class="btn green btn_gray new_add">
									<i class="fa  fa-edit"></i>&nbsp;修改 
								</button>
								<button id="viewReportMenu"
									class="btn green btn_gray new_view">
									<i class="fa fa-eye"></i>&nbsp;查看 
								</button>
								<button id="relateRole"
									class="btn green btn_gray new_add">
									 <i class="fa fa-fire"></i>&nbsp;授权
								</button> -->
							</div>
						</div>
					</div>
				</div>
			</section>
		</section>
	</section>
	
	<!-----------------------------------------------------------------------  -->
	<div id="grid-wrap" style="height: 82%;">
		<table id="reportMenuGrid"></table>
	</div>
	<!-- <div id="toolbar"  style='height:auto;background:#fff;width:99.3%;border: 1px solid #e6e6e6;border-bottom: 3px solid #e6e5e5;margin:5px 0.5%;float:left;'>
		<div class='toolbar_left' style='width:45%;float:left;'>
			<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" id="addReportMenu"><span class='spanAbc'><i iconCls="icon-add" class='icon-add1'></i></span><span key="add" class="paltform-i18n paltform-i17rt">新增</span></a>
			<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" id="updateReportMenu"><span class='spanAbc'><i  iconCls="icon-edit" class='icon-edit1'></i></span><span key="edit" class="paltform-i18n paltform-i17rt">修改</span></a>
			<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" id="deleteReportMenu"><span class='spanAbc'><i iconCls="icon-remove" class='icon-remove1'></i></span><span key="delete" class="paltform-i18n paltform-i17rt">删除</span></a>
			<a href="#"  class="toolbar_lefta toolbar_leftb" plain="true" id="viewReportMenu"><span class='spanAbc'><i iconCls="icon-view" class='icon-view1'></i></span><span key="view" class="paltform-i18n paltform-i17rt">查看</span></a>
			<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" id="relateRole"><span class='spanAbc'><i iconCls="icon-view" class='icon-js1'></i></span><span key="view" class="paltform-i18n paltform-i17rt">授权</span></a>
		</div>
	</div> -->
	
</body>
</html>