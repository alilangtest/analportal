<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder"%>
<%@include file="../_include/_taglib.jsp"%>
<%
	String from = request.getParameter("from");
	String useid=UserHolder.getId();
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/easyui/1.4.5/themes/bootstrap/easyui.css" />
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript"
	src="${ctx}/assets/_osdp/js/tableau/tableauServer.js"></script>

<style type="text/css">
	body, html {
		overflow: auto;height:100%;
	}
	
	.panel-body {
		border: none;
	}
	
	.datagrid-header-row, .datagrid-row {
		height:40px;
	}
	
</style>
</head>
<body class='table_css'>
	<!-- 查询区 -->
	<section id="main-content">
		<!-- BEGIN WRAPPER  -->
		<section class="wrapper site-min-height">
			<section class="panel">
				<header class="panel-heading">
					<span class="label label-primary new_label">tableu数据库配置</span> <span
						class="tools pull-right"></span>
				</header>

				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group text-right float-right" style=''>
								<div class='float-rightd'>
									<input id="postgreuname1" name="postgreuname1" onchange="searchs()" type="text"
										class="form-control" placeholder="用户姓名">
								</div>
								<button id="searchs" onclick="searchs()"
									class="btn btn-info green">搜索</button>
								<button id="empty" onclick="empty()" class="btn btn-default green">
									取消</button>
							</div>
							<div class="btn-group">
								<button id="toAdd" onclick="toAdd()"
									class="btn green btn_gray new_add">
									<i class="fa fa-plus"></i>&nbsp;新建 
								</button>
								<button id="toRemoves" onclick="toRemoves()"
									class="btn green btn_gray new_del">
									<i class="fa fa-minus-circle"></i>&nbsp;删除 
								</button>
								<button id="toUpdate" onclick="toUpdate()"
									class="btn green btn_gray new_add">
									<i class="fa  fa-edit"></i>&nbsp;修改 
								</button>
								<button id="toView" onclick="toView()"
									class="btn green btn_gray new_view">
									<i class="fa fa-eye"></i>&nbsp;查看 
								</button>
							</div>
						</div>
					</div>
				</div>
			</section>
		</section>
	</section>

	<!-- <div>
		<input id="postgreuname1" name="postgreuname1" type="text" placeholder="登录名称"/> &nbsp;
		<a id="searchs" href="#" onclick="searchs()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	</div> -->
	<!-- 列表区 -->
	<div class='table_cell'
		style='width: 100%; height: 500px; overflow-y: auto;'>
		<table id="oGrid"></table>
	</div>

	<!-- <table id="oGrid"></table> -->
	<script>
		$('.table_cell').height($('body').height()-130);
	</script>
</body>
</html>