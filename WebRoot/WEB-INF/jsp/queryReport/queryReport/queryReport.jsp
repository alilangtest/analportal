<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  function reloadData(){
	//点击节点时得到角色编号
    $("#reportTable").datagrid('reload', {
		filters : __.encode({
			'menuid' : menuid,
			'reportname' : '',
			'flag':''
		})
	});
  }
</script>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/queryReport/queryReport/report.js"></script>
<style type="text/css">
	body, html {
		overflow: auto;
		height:100%;
	}
	
	.panel-body {
		border: none;
		overflow: hidden;
	}
	
	.datagrid-header-row, .datagrid-row {
		height: 34px;
	}
	.panel-header-noborder{background:none;border-bottom:1px solid #ddd;padding:12px 0;font-weight:bold;}
</style>
</head>
<body class='table_css table_css_tree'>
	<div id="cc" class="easyui-layout" fit="true">
		<div  id="left" region="west" split="false" style="width: 200px; height: 90%; border:1px solid #e6e6e6;">
			<div id="menuTreeDiv" class="easyui-panel" title="菜单" border="false" style="padding:10px;overflow:auto;">
				<ul id="menuTree" class="easyui-tree"></ul> 
			</div>
		</div>
		
		<div id="right" region="center">
			<div id="reportDiv">
			<!-- ========================= -->
			<!-- 查询区 -->
				<section id="main-content">
					<!-- BEGIN WRAPPER  -->
					<section class="wrapper site-min-height">
						<section class="panel">
							<header class="panel-heading">
								<span class="label label-primary new_label">即席报表维护</span> <span
									class="tools pull-right"></span>
							</header>
			
							<div class="panel-body">
								<div class="adv-table editable-table ">
									<div class="clearfix">
										<div class="btn-group text-right float-right" style=''>
											<div class='float-rightd'>
												<select style="width:200px;float: left;" placeholder="挂载状态" id="mountstate" class="form-control m-bot15 form-control_new" name="type">
													<option value="" selected="selected">全部</option>
													<c:forEach items="${list}" var="obj">
														<option value="${obj.codeid }">${obj.codename }</option>
													</c:forEach>
												</select>
												<input type="text" id="reportname" style="width:200px;" name="reportname" class="form-control" 
												 placeholder="报表名称" onkeypress="queryReportForEnter(event)"/>
											</div>
											<button id="searchs" onclick="queryReport()"
												class="btn btn-info green">搜索</button>
											<button id="empty" onclick="clearQueryForm()" class="btn btn-default green">
												取消</button>
										</div>
										
										
										<div class="btn-group">
										<!-- 	<button id="toUpdate" onclick="editReport()"
												class="btn green btn_gray">
												编辑 <i class="fa  fa-edit"></i>
											</button>
											<button id="toRemoves" onclick="removeReport()"
												class="btn green btn_gray new_del">
												删除 <i class="fa fa-minus-circle"></i>
											</button>
										
											<button id="toView" onclick="viewReport()"
												class="btn green btn_gray new_view">
												查看 <i class="fa fa-eye"></i>
											</button> -->
											<button id="toAdd" onclick="getReportFromTableau()"
												class="btn green btn_gray new_add">
												<i class="fa fa-plus"></i>&nbsp;获取报表
											</button>
											<button id="toAdd" onclick="selectReports()"
												class="btn green btn_gray new_add">
												<i class="fa fa-plus"></i>&nbsp;挂载报表
											</button>
											<button id="toAdd" onclick="cutReports()" title="请选择 1 条"
												class="btn green btn_gray new_add">
												<i class="fa fa-plus"></i>&nbsp;卸载报表
											</button>
										</div>
									</div>
								</div>
							</div>
						</section>
					</section>
				</section>
			
			<!-- ======================== -->
				<div class='table_cell'><table id="reportTable" class="easyui-datagrid"  idField="staffno"></table></div>
				<!------------------  -->
				<!-- <div id="toolbar" style='height:auto;background:#fff;width:99.3%;border: 1px solid #e6e6e6;border-bottom: 3px solid #e6e5e5;margin:5px 0.5%;float:left;'>
					<div class='toolbar_left' style='width:47%;float:left;'>		
						<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" onclick="editReport()"><span class='spanAbc'><i iconCls="icon-edit" class='icon-edit1'></i></span><span class='paltform-i17rt'>编辑</span></a>
						<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" onclick="removeReport()"><span class='spanAbc'><i iconCls="icon-remove" class='icon-remove1'></i></span><span class='paltform-i17rt'>删除</span></a>
						<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" onclick="viewReport()"><label><span class='spanAbc'><i iconCls="icon-view" class='icon-view1'></i></span><span class='paltform-i17rt'>查看</span></label></a>
						<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" onclick="getReportFromTableau()"><span class='spanAbc'><i  iconCls="icon-download" class='icon-xz1'></i></span><span class='paltform-i17rt'>获取报表</span></a>
						<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" onclick="selectReports()"><span class='spanAbc'><i iconCls="icon-track" class='icon-gz1'></i></span><span class='paltform-i17rt'>挂载报表</span></a>
						<a href="#" class="toolbar_lefta toolbar_leftb" plain="true" onclick="cutReports()" title="请选择 1 条"><span class='spanAbc'><i iconCls="icon-cut" class='icon-xz1'></i></span><span class='paltform-i17rt'>卸载报表</span></a>
					</div>
	              	<div class='toolbar_right'  style='width:43%;float:right;margin-top:18px;'>
	              		<table align="center" class="querytable" id="queryDiv">
							<tr>
								<td  align="right" style='width:47%;'><span >报表名称：</span><input type="text" id="reportname" name="reportname" style="width:61%; height:26px; outline:none;" class="easyui-validatebox input_bg" onkeypress="queryReportForEnter(event)"/></td>
								<td align="right" style='width:43%;'>
									<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryReport()"><label><span>查询</span></label></a>
									<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearQueryForm()"><label><span>清空</span></label></a>
								</td>
							</tr>
						</table>
	              	</div>
				</div> -->
				<!-- ---------- -->
			</div>
		</div>
	</div>
	<script>
		$('.table_cell').height($('body').height()-130);
	</script>
</body>
</html>