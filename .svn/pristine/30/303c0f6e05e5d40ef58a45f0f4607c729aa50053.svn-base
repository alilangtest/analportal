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

<script type="text/javascript" src="${ctx}/assets/_osdp/js/report/report/getReport.js"></script>
<style type="text/css">
	body,html{width:100%;height:100%;}
</style>
<script type="text/javascript">
	var menuid="<%=menuid%>";
</script>
</head>
<body class='table_css'>
		<div id="staffPanel" region="center">
			<div style="padding-top: 10px;padding-bottom: 5px">
				  <span>&nbsp&nbsp&nbsp&nbsp站点 : </span><input id="cc" name="dept" value="" style="line-height: 30px; height: 30px;width: 165px ">  
		    </div>
		</div>
		
	     <div id="reportDiv" >
	          <table id="reportTable_"  toolbar="#toolbar"></table>
	    </div>
	     
         <div id="buttondiv" >
 			<tr align="center">
				<td>
   					<div class="button_succ" >
						<button id="savebutton" type="button" class="btn btn-shadow btn-info" onclick="getReportFromTableau()">获取</button>
						<button  type="button" class="btn btn-shadow btn-default" onclick="closePanel()">取消</button>
					</div>
			    </td>
 			</tr>
     	 </div>	
</body>
</html>