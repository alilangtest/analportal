<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../_include/_taglib.jsp"%><%
/* String reportName = request.getParameter("reportName");
System.out.print("=========================");
System.out.print(reportName);
String functionClass = new String(request.getParameter("functionClass").getBytes("ISO-8859-1"),"utf-8");
System.out.print(functionClass);
String reoprtClass = new String(request.getParameter("reoprtClass").getBytes("ISO-8859-1"),"utf-8");
String reportSubclass = new String(request.getParameter("reportSubclass").getBytes("ISO-8859-1"),"utf-8"); */
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>数据分析</title>
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/common.css">
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/animations.css">
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/byit.css">
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/newuAdowncss/css.css">
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/layui.css">
	<script type="text/javascript">;var _ctx = '${ctx}',EL=EL||{},FN=FN||{},GV=GV||{},URLS=URLS||{};</script>
	<script src='${ctx}/assets/bootstrap/js/jquery-1.11.1.min.js'></script>
	<script src="${ctx}/assets/layer/layer.js"></script>
	<script src="${ctx}/assets/bootstrap/js/layui.all.js"></script>
	<style type="text/css">
	.btn-success{
		background-color: #78CD51;
	    border-color: #78CD51;
	    color: #FFFFFF;
	}
	.btn{
		display: inline-block;
	    margin-bottom: 0;
	    font-weight: 400;
	    text-align: center;
	    vertical-align: middle;
	    cursor: pointer;
	    background-image: none;
	    border: 1px solid transparent;
	    white-space: nowrap;
	    padding: 6px 12px;
	    font-size: 14px;
	    line-height: 1.428571429;
	    border-radius: 4px;
	    -webkit-user-select: none;
    }
	</style>
	<script type="text/javascript">
		$(function (){
			
		});
		
	</script>
</head>
<body>
<div id = "parentDiv" class = "div">
	<input type="hidden" value="${reportName }" id="reportName">
	<input type="hidden" value="${functionClass }" id="functionClass">
	<input type="hidden" value="${reoprtClass }" id="reoprtClass">
	<input type="hidden" value="${reportSubclass }" id="reportSubclass">
	<div id = "div3" style="width:100%; border: solid 1px black;" class = "div">
		<div style="background-color:#fff; margin-top: 1.5%;margin-left:10px;margin-right:10px;border-radius:10px;padding:10px;">
		<h3 class="zhibiao-title">指标</h3>
			<div style="height: 70%;">
				<table class="layui-table" lay-even="" lay-skin="row">
				  <colgroup>
				    <col width="150">
				    <col width="150">
				    <col width="200">
				    <col>
				    <col>
				    <col>
				    <col>
				    <col>
				  </colgroup>
				  <thead>
				    <tr>
				      <th>序号</th>
				      <th>报表分类</th>
				      <th>报表名称</th>
				      <th>指标名称</th>
				      <th>数据元名称</th>
				      <th>含义描述</th>
				      <th>计算公式</th>
				    </tr> 
				  </thead>
				  <tbody id="indexTbody">
				  </tbody>
				</table>
			</div>
			<div style="height: 10%; text-align: right;">
				<div id="demo7"></div>
			</div>
		</div>
		<div style="background-color:#fff;margin-top: 1.5%; margin-left:10px;margin-right:10px;border-radius:10px;padding:10px;">
			<h3 class="zhibiao-title">维度</h3>
			<div style="height: 70%;">
				<table class="layui-table" lay-even="" lay-skin="row">
				  <colgroup>
				    <col width="150">
				    <col width="150">
				    <col width="200">
				    <col>
				    <col>
				    <col>
				  </colgroup>
				  <thead>
				    <tr>
				      <th>序号</th>
				      <th>报表分类</th>
				      <th>报表名称</th>
				      <th>维度名称</th>
				      <th>数据元名称</th>
				      <th>含义描述</th>
				    </tr> 
				  </thead>
				  <tbody id="weiduTbody">
				  </tbody>
			</table>
			</div>
			<div style="text-align: right;">
				<div id="demo8"></div>
			</div>
		</div>
	</div>
</div>
</body>
<script src="${ctx}/assets/_osdp/js/dataIndex/dataIndexOpenShow.js"></script>
    <script type="text/javascript">
    	layui.use(['laypage', 'layer'], function(){
    		  var laypage = layui.laypage
    		  ,layer = layui.layer;
    		  laypage({
    		    cont: 'demo7'
    		    ,pages: 2
    		    ,skip: true
    		    ,jump: function(obj){
    		    	//indexPage(obj.curr);
       		     // document.getElementById('biuuu_city_list').innerHTML = render(data, obj.curr);
       		    }
    		  });
    		  //调用分页
    		  laypage({
    		    cont: 'demo8'
    		    ,pages: 2 //得到总页数
    		    ,skip: true
    		    ,jump: function(obj){
    		    	//weiduPage(obj.curr)
    		     // document.getElementById('biuuu_city_list').innerHTML = render(data, obj.curr);
    		    }
    		  });
    		});
    </script>
</html>