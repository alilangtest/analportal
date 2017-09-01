<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%>
<%
String useid=UserHolder.getId();
String menuid=request.getParameter("menuid");
String parentid=request.getParameter("parentid");
%>

<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>
<script type="text/javascript">
  var userid="<%=useid%>";
  var menuid="<%=menuid%>";
  var parentid="<%=parentid%>";
</script>

<script type="text/javascript" src="${ctx}/assets/_osdp/js/queryReport/queryReportMenu/relateRole.js"></script>
<style type="text/css">
	.panel-header-noborder{background:none;border:none;font-size:13px;color:#666;text-align:center;}
	#authRole{
		border: 1px solid #e2e2e4;
		box-shadow: none;
		color: #333;
		height: 34px;
		width: 142px;
	    padding: 6px 12px;
	    font-size: 14px;
	    line-height: 1.428571429;
	    background-color: #fff;
	    background-image: none;
	    border-radius: 4px;
	    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	}
</style>
<script type="text/javascript">
function searchRole(){
	var keyword = $.trim($("#authRole").val());
	GV.searchAll = false;
	$("#leftrole").datagrid('load', {
		roleName : keyword
	});
}
</script>
</head>
<body class='table_css'>
	<div style="margin-left:30px;position: absolute;height: 39px;top: 4px;width: 100%;z-index: 1;">
		角色列表
		<input id="authRole" type="text" placeholder="角色名称">
		<button type="button"  id="savebutton" class="btn btn-primary" style='margin-right:15px;'  onclick="searchRole()">
                		 搜索
           </button>
        <span style="margin-right: 251px;float: right; margin-top: 10px;">已分配角色列表</span>
	</div>
	 <div id="cc" class="easyui-layout" fit="true">
			<div  id="left" region="west" split="false" style="width:350px; height: 90%; padding: 25px 15px 0px;">
				<table id="leftrole"></table>
			</div>
		
			<div id="right" region="center" align="center">
				<div>
					<div id="menuid" hidden="true">${menuid}</div>
					<div id="parentid"  hidden="true">${parentid}</div>
				</div>
				<div style="padding-top: 200px;">
					<!-- <a href="#" id="savebutton" class="easyui-linkbutton" onclick="rightRole()"><span>右移  >></span></a>

					<a href="#" class="easyui-linkbutton" onclick="leftRole()"><span><< 左移</span></a> -->
					<button type="button"  id="savebutton" class="btn btn-primary"  onclick="rightRole()">
	                   	 添加→
	                 </button>
	                 <br/>
					 <br/>
	                 <button type="button" class="btn btn-primary" onclick="leftRole()">
                    	←删除
                  	</button>
				</div>
			</div>
			<div id="east" region="east"  style="width: 350px; height: 90%; padding: 25px 15px 0px;">
				<table id="rightrole"></table>
			</div>
			<div id="south" region="south" align="center" style='line-height:47px;padding:0;'>
				<table class="button_area_absolute" align="center">
       				<tr align="center">
       					 <td>
       					    
       						<button type="button"  id="savebutton" class="btn btn-info" style='margin-right:15px;'  onclick="saveRole()">
		                   		 保存
		                 	</button>
							<button type="button" class="btn btn-default" style='margin-right:15px;' onclick="closeEmployee()">
		                    	取消
		                  	</button>
       					 </td>
       				</tr>
     			</table>
			</div>
			
		</div>
</body>
</html>