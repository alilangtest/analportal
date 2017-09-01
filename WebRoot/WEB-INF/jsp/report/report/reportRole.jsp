<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%>
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
  <%-- var userid="<%=useid%>"; --%>
</script>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/report/report/reportRole.js"></script>
<style type="text/css">
	.panel-header-noborder{background:none;border-bottom:1px dashed #ddd;padding-bottom:10px;}
</style>
</head>
<body class='table_css table_css_tree'>
	<div id="cc" class="easyui-layout" fit="true">
		<div id="left" region="west" split="false" style="width: 400px;">
			<div id="reportDiv" class="easyui-panel" title="报表" border="false" style="padding:16px;overflow:auto;">
				<ul id="tt" class="easyui-tree right_tree_sty"></ul> 
				<input type="hidden" id="menuid_"  class="easyui-validatebox input_bg"/>
				<input type="hidden" id="reportid_"  class="easyui-validatebox input_bg"/>
			</div>
		</div>
		<div id="right" region="center" split="false" style="width: 300px;border-left: none;border-right: none;">
			<div id="roleTreeDiv" class="easyui-panel" title="角色" border="false" style="padding:16px;overflow: auto;">
				<table class="querytable" id="queryDiv"  >
					<tr>
						<!-- <td><span class="rolespn_sty">角色名称：</span></td>
					    <td><input type="text" id="rolename" onkeypress="queryRole(event)" class="easyui-validatebox input_bg iptstyreport" /></td>
						<td style='display:inline-block;'><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryRoleButton()">查询</a></td> -->
						<div class="panel-body" style='border:none;'>
							<div class="adv-table editable-table ">
								<div class="clearfix">
									<div class="btn-group text-right" style="float:left;">
										<div class="float-rightd" style='float:left;margin-right:10px;'>
											<input type="text" id="rolename" name="rolename" onkeypress="queryRole(event)" class="form-control" placeholder='角色名称'/>
										</div>
										<button id="searchs" onclick="queryRoleButton()" class="btn btn-info green">查询</button>
									</div>
								</div>
							</div>
						</div>
					</tr>
				</table>
				<ul id="ttt" class="easyui-tree"></ul> 
			</div>
			<div id="bottom" region="south" split="false"   style="width: 100px; height: 40px;margin:0 auto;margin-top:-43px;">
				<table class="button_area_absolute" align="center">
					<tr>
						<td>
							<button id="savebutton1" type="button" class="btn btn-shadow btn-info" style="margin-top:12px;" onclick="saveRoleReport()">保存</button>
						</td>
	  				</tr>
				</table>	 
			</div>
		</div>
		
	</div>
</body>
</html>