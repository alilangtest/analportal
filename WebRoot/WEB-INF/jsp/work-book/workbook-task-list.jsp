<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../_include/_taglib.jsp"%><%
String from = request.getParameter("from");
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
	
	<link rel="stylesheet" href="${ctx}/assets/workBook/css/dataindexshow.css">
	<link rel="stylesheet" href="${ctx}/assets/workBook/css/dataindexshowskin.css" id="layout-skin" />
	<link rel="stylesheet" href="${ctx}/assets/workBook/css/reportshensuomenu.css" />
	<link rel="stylesheet" href="${ctx}/assets/iconfont/1.0.2/iconfont.css" id="layout-skin" />
	<link rel="stylesheet" href="${ctx}/assets/workBook/select2/4.0.0/css/select2.min.css" />
	
	<script type="text/javascript">;var _ctx = '${ctx}',EL=EL||{},FN=FN||{},GV=GV||{},URLS=URLS||{};</script>
	<script src='${ctx}/assets/bootstrap/js/jquery-1.11.1.min.js'></script>
	<%-- <script src="${ctx}/assets/jquery/1.11.1/jquery.min.js"></script> --%>
	<script src="${ctx}/assets/jquery/patch/jquery-patch-1244.js"></script>
	<script src="${ctx}/assets/layer/layer.js"></script>
	<script src="${ctx}/assets/bootstrap/js/layui.all.js"></script>
	<script src="${ctx}/assets/workBook/select2/4.0.0/js/select2.min.js"></script>
	<style type="text/css">
	.btn-info{
		background-color: #58c9f3;
    	border-color: #58c9f3;
	    color: #FFFFFF;
	}
	.btn-info:hover{	    
	    background-color: #53bee6;
    	border-color: #53BEE6;
	}
	.btn-infos{
		background-color: #f5c86e;
	}
	.btn-infos:hover {
		background-color: #f5c056;
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
	    padding: 6px 18px;
	    font-size: 14px;
	    line-height: 1.428571429;
	    border-radius: 4px;
	    -webkit-user-select: none;
    }
    #reportNames li{
		text-align:center;
		font-size:14px;
		line-height: 30px;
	}
	.side-menu .menu-item a:hover{
			text-decoration: none;
			color:#428bca;
		}
	.reportNameA{
		color : #36a4ff;
	}
	.reportAs{
		color:#667fa0;
	}
	</style>
	<script type="text/javascript">
		$(function (){
			URLS.findSites = _ctx + '/work-book/findAll.do';
			URLS.pagedQuery = _ctx + '/work-book/pagedQuery.do';
			URLS.pagedQueryCount = _ctx + '/work-book/pagedQueryCount.do';
			URLS.post = _ctx + '/work-book/workbooktaskPostPage.html';
			URLS.findBySiteId = _ctx + "/work-book/findBySiteId.do";
			var this_height = $('body,html').height();
			$('.div').css("height", this_height - 30);
			//查询所有站点信息并绑定到站点下拉框
			$.post(URLS.findSites).done(function(records) {
				var html = '';
				$.each(records, function(i, record) {
					html += '<option value="' + record.id + '" >' + record.name + '</option>';
				});
				$("#site").html(html).select2('val', records == null ? '' : records[0].id);
			});
			//设置站点下拉框改变事件
			$("#site").select2({
				placeholder : '站点名称',
				minimumResultsForSearch : 0
			}).change(function(e){
				$.post(URLS.findBySiteId,{siteId:$("#site").val()}).done(function(records) {
					//拼接项目下拉框信息
					var html = '<option></option>';

					$.each(records, function(i, record) {
						html += '<option value="' + record.id + '" >' + record.name + '</option>';
					});
					//赋值，并调用下拉框改变事件
					$("#project").html(html);
					$("#project").change();
				});
			});
			//加载项目下拉框，并绑定change事件
			$("#project").select2({
				placeholder : '项目名称',
				minimumResultsForSearch : 0,
				allowClear : true
			}).change(function(e) {
				queryCount();
			});
			//监听工作薄名称输入框是否输入Enter键
			$("#reportName").on("keyup",function(event){
		    	if((event.keyCode || event.which)==13){
		    		queryCount();
		    	}
			});
		});
		//根据页面条件获取分页总条数
		function queryCount(){
			//计算分页开始条数
			pageParam.start =  0 * pageParam.limit;
			pageParam.siteId = $("#site").val();
			pageParam.projectId =  $("#project").val();
			pageParam.reportName =  $("#reportName").val();
			//点击菜单加载报表信息
			$.post(URLS.pagedQueryCount, pageParam, function(result) {
				for(var key in result){
					if(key == "count"){
						pageParam.count = result[key];
						//计算分页总页数
						pageParam.totlePage = pageParam.count % pageParam.limit == 0 ? pageParam.count / pageParam.limit : (pageParam.count / pageParam.limit) + 1;
						// 重新初始化分页信息
						initPage();
					}else{
						var dataTbody = document.getElementById("dataTbody");
						dataTbody.innerHTML = "";
						for(var k in result[key]){
							var tr = document.createElement("tr");
							var td0 = document.createElement("td");
							td0.innerText = result[key][k].id;
							tr.appendChild(td0);
							var td1 = document.createElement("td");
							td1.innerText = result[key][k].name;
							tr.appendChild(td1);
							var td2 = document.createElement("td");
							td2.innerText = result[key][k].projectName;
							tr.appendChild(td2);
							var td3 = document.createElement("td");
							td3.innerText = result[key][k].ownerName;
							tr.appendChild(td3);
							var td4 = document.createElement("td");
							if(result[key][k].dispatchState == "0"){
								td4.innerText = "停止";
							}else if(result[key][k].dispatchState == "1"){
								td4.innerText = "执行";
							}else{
								td4.innerText = "~";
							}
							tr.appendChild(td4);
							var td5 = document.createElement("td");
							if(result[key][k].taskState == "0"){
								td5.innerText = "未更新";
							}else if(result[key][k].taskState == "1"){
								td5.innerText = "已更新";
							}else{
								td5.innerText = "~";
							}
							tr.appendChild(td5);
							var td6 = document.createElement("td");
							if(result[key][k].refreshState == "0"){
								td6.innerText = "未完成";
							}else if(result[key][k].refreshState == "1"){
								td6.innerText = "已完成";
							}else if(result[key][k].refreshState == "2"){
								td6.innerText = "刷新中";
							}else{
								td6.innerText = "~";
							}
							tr.appendChild(td6);
							var td8 = document.createElement("td");
							td8.innerText = result[key][k].refreshTime;
							tr.appendChild(td8);
							var td7 = document.createElement("td");
							var a = document.createElement("a");
							a.innerText = "计划任务";
							a.href = "#";
							a.className = "reportAs";
							a.setAttribute("id",result[key][k].id);
							a.setAttribute("siteId",result[key][k].siteId);
							a.setAttribute("refreshFreq",result[key][k].refreshFreq);
							a.setAttribute("name",result[key][k].name);
							a.setAttribute("refreshTime",result[key][k].refreshTime);
							a.setAttribute("dispatchState",result[key][k].dispatchState);
							a.onclick = function () {
								openHtml(this);
								//reportInfo(this, this.getAttribute("functionClass"), this.getAttribute("reoprtClass"), this.getAttribute("reportSubclass"), this.getAttribute("reportName"));
							};
							td7.appendChild(a);
							tr.appendChild(td7);
							dataTbody.appendChild(tr);
						}
					}
				}
			});
		}
		//加载分页界面
		function initPage(){
			layui.use(['laypage', 'layer'], function(){
				  var laypage = layui.laypage
				  ,layer = layui.layer;
				  laypage({
				    cont: 'demo7'
				    ,pages: pageParam.totlePage
				    ,skip: true
				    ,jump: function(obj){
				    	pagedQuery(obj.curr);
		   		    }
				  });
			});
		}
		var pageParam = new Object();
		pageParam.start = 0;
		pageParam.count = 0;
		pageParam.limit = 12;
		pageParam.totlePage = 1;
		pageParam.currPage = 0;
		pageParam.siteId = "";
		pageParam.projectId = "";
		pageParam.reportName = "";
		function pagedQuery(currPage){
			pageParam.currPage = currPage;
			//计算分页开始条数
			pageParam.start =  (currPage - 1) * pageParam.limit;
			pageParam.siteId = $("#site").val();
			pageParam.projectId =  $("#project").val();
			pageParam.reportName =  $("#reportName").val();
			//点击菜单加载报表信息
			$.post(URLS.pagedQuery, pageParam, function(result) {
				var dataTbody = document.getElementById("dataTbody");
				dataTbody.innerHTML = "";
				for(var k in result){
					var tr = document.createElement("tr");
					var td0 = document.createElement("td");
					td0.innerText = result[k].id;
					tr.appendChild(td0);
					var td1 = document.createElement("td");
					td1.innerText = result[k].name;
					tr.appendChild(td1);
					var td2 = document.createElement("td");
					td2.innerText = result[k].projectName;
					tr.appendChild(td2);
					var td3 = document.createElement("td");
					td3.innerText = result[k].ownerName;
					tr.appendChild(td3);
					var td4 = document.createElement("td");
					if(result[k].dispatchState == "0"){
						td4.innerText = "停止";
					}else if(result[k].dispatchState == "1"){
						td4.innerText = "执行";
					}else{
						td4.innerText = "~";
					}
					tr.appendChild(td4);
					var td5 = document.createElement("td");
					if(result[k].taskState == "0"){
						td5.innerText = "未更新";
					}else if(result[k].taskState == "1"){
						td5.innerText = "已更新";
					}else{
						td5.innerText = "~";
					}
					tr.appendChild(td5);
					var td6 = document.createElement("td");
					if(result[k].refreshState == "0"){
						td6.innerText = "未完成";
					}else if(result[k].refreshState == "1"){
						td6.innerText = "已完成";
					}else if(result[k].refreshState == "2"){
						td6.innerText = "刷新中";
					}else{
						td6.innerText = "~";
					}
					tr.appendChild(td6);
					var td8 = document.createElement("td");
					td8.innerText = result[k].refreshTime;
					tr.appendChild(td8);
					var td7 = document.createElement("td");
					var a = document.createElement("a");
					a.innerText = "计划任务";
					a.href = "#";
					a.className = "reportAs";
					a.setAttribute("id",result[k].id);
					a.setAttribute("siteId",result[k].siteId);
					a.setAttribute("refreshFreq",result[k].refreshFreq);
					a.setAttribute("name",result[k].name);
					a.setAttribute("refreshTime",result[k].refreshTime);
					a.setAttribute("dispatchState",result[k].dispatchState);
					a.onclick = function () {
						openHtml(this);
						//reportInfo(this, this.getAttribute("functionClass"), this.getAttribute("reoprtClass"), this.getAttribute("reportSubclass"), this.getAttribute("reportName"));
					};
					td7.appendChild(a);
					tr.appendChild(td7);
					dataTbody.appendChild(tr);
				}
			});
		}
		//计划任务页面跳转方法
		function openHtml(obj){
			layer.open({
			      type: 2,
			      title: '计划任务',
			      maxmin: false, //开启最大化最小化按钮
			      area: ['550px', '360px'],
			      content: URLS.post,
			      btnAlign: 'c',
			      success: function(layero, index){
			    	  //回传参数到计划任务页面
			    	    var body = layer.getChildFrame('body', index);
			    	    body.contents().find("#id").val(obj.getAttribute("id"));
			    	    body.contents().find("#siteId").val(obj.getAttribute("siteId"));
			    	    body.contents().find("#workbookName").val(obj.getAttribute("name"));
			    	    body.contents().find("#refreshFreq").val(obj.getAttribute("refreshFreq"));
			    	    body.contents().find("#refreshTime").val(obj.getAttribute("refreshTime"));
			    	    body.contents().find("#dispatchState").val(obj.getAttribute("dispatchState"));
			    	    var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
		                var ids = iframeWin.init();//调用子页面的方法，得到子页面返回的ids
			    	  }
			 });
		}
		function clearInfo(){
			$("#reportName").val("");
			$("#project").select2("val", "");
			//docuement.getElementById("project").selectedIndex = -1;
			//$("#project").val("");
		}
	</script>
</head>
<body>
<div id="parentDiv" class="div">
	<div id ="div3" class="layout-main rightcon">
		<div class="header-top-search">
			<em class="local-sub"><i class="local-icon"></i><span>任务调度</span></em>
			<b class="search-right">
				站点：<select id="site" style="width: 210px;" class="iptname"></select>
				<select id="project" style="width: 210px;" class="iptname"></select>
				<input class="iptname" style="width: 210px;" id="reportName" name="reportName" type="text" placeholder=" 工作簿名称" />
				<button id="search" onclick="queryCount();" class="btn btn-info green">搜索</button>
				<button id="search" onclick="clearInfo();" class="btn btn-infos green" style="color:#fff;">清空</button>
				<!-- <input type="text" class="iptname" placeholder="报表名称" id="vagueReportName" onkeydown="reportNameOrIndexNamekeyDown(event, this)">
				<input type="text" class="iptname" placeholder="指标名称" id="indexName" onkeydown="reportNameOrIndexNamekeyDown(event, this)">
				<button id="search" onclick="queryIndexName();" class="btn btn-info green">搜索</button>
				<button id="search" onclick="clearInfo();" class="btn btn-infos green" style="color:#fff;">清空</button> -->
				<!-- <button id="empty" onclick="clearInfo();" class="btn btn-default">清空</button> -->
			</b>
		</div>
		<div class="zhibiao-tab">
		<h3 class="zhibiao-title">调度任务</h3>
			<div class="tabbox">
				<table class="layui-table" lay-even="" lay-skin="row">
				  <colgroup>
				    <col width="60">
				    <col>
				    <col>
				    <col>
				    <col width="120">
				    <col width="100">
				    <col width="100">
				    <col width="150">
				    <col width="100">
				  </colgroup>
				  <thead>
				    <tr>
				      <th>ID</th>
				      <th>工作薄名称</th>
				      <th>所属项目</th>
				      <th>所有者</th>
				      <th>调度状态</th>
				      <th>ETL状态</th>
				      <th>刷新状态</th>
				      <th>计划刷新时间</th>
				      <th>操作</th>
				    </tr> 
				  </thead>
				  <tbody id="dataTbody">
				  </tbody>
				</table>
			</div>
			<div class="pagetation">
				<div id="demo7"></div>
			</div>
		</div>
	</div>
</div>
</html>