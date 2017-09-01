<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%>
<%
String menuid=request.getParameter("menuid");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>

<script type="text/javascript" src="${ctx}/assets/_osdp/js/report/report/selectReport.js"></script>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/queryReport/queryReport/kz.js"></script>
<style type="text/css">
	body,html{width:100%;height:100%;}
</style>
<script type="text/javascript">
var menuid="<%=menuid%>";
</script>
</head>
<body class='table_css'>
	<div id="staffPanel" region="center">
	            <%-- <div id="menuid1" hidden>${menuid}</div> --%>
				<table align="center" class="querytable" id="queryDiv" style='width:100%;'>
					<tr></tr>
					<div class="panel-body">
						<div class="adv-table editable-table ">
							<div class="clearfix">
								<div class="btn-group text-right float-right" style="">
									<div class="float-rightd">
										<input type="text" id="reportname" name="reportname" class="form-control" placeholder="报表名称">
									</div>
									<button onclick="queryReport()" class="btn btn-shadow btn-info">查询</button>
									<button onclick="clearQuery()" class="btn btn-shadow btn-info">
										清空</button>
								</div>
							</div>
						</div>
					</div>
				</table>
				
		 </div>
		 <div id="reportDiv">
	          <table id="reportTable_"  toolbar="#toolbar"></table>
	     </div>
         <div id="buttondiv" style="">
            <table class="button_area_absolute" style='width:100%;' align="center">
      				<tr align="center">
      					 <td>
      					    <!-- <a href="#" id="savebutton" class="easyui-linkbutton" iconCls="icon-ok" onclick="reportSetMenuid()"><span>保存</span></a>
						    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closePanel()"><span>取消</span></a> -->
      					 	<div class="button_succ">
								<button id="savebutton" type="button" class="btn btn-shadow btn-info" onclick="reportSetMenuid()">保存</button>
								<button  type="button" class="btn btn-shadow btn-default" onclick="closePanel()">取消</button>
							</div>
      					 </td>
      				</tr>
      		</table>
     	 </div>	
</body>
</html>