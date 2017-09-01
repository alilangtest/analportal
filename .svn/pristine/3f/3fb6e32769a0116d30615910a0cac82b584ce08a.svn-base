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
	<div style="overflow: auto;">
		<div class='add_box'>
			<div class="add_box_in pt-page-scaleUpCenter">
				<ul class='add_box_in_ul'>
					<li class="add_box_in_uli">
						<span>报表编号：</span>
						<input id="reportid" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli">
						<span>报表名称：</span>
						<input id="reportname" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli">
						<span>报表类型：</span>
						<input id="reporttype" readonly="readonly"/>
					</li>
					<!-- <li class="add_box_in_uli">
						<span>是否显示：</span>
						<input id="isopen" readonly="readonly"/>
					</li> -->
					<li class="add_box_in_uli">
						<span>制表人：</span>
						<input id="publisper" readonly="readonly"/>
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>报表地址：</span>
						<strong name="url"  class="form-control" id="url" style='height:150px;'  readonly="readonly"></strong>
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>报表描述：</span>
						<strong  class="form-control" name="reportdescription" id="reportdescription" style='height:150px;'  readonly="readonly"></strong>
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>报表说明：</span>
						<strong name="desp"  class="form-control" id="desp" style='height:150px;'  readonly="readonly"></strong>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>