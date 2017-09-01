<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../_include/_taglib.jsp"%><%
String from = request.getParameter("from");
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String userid=UserHolder.getId();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>" />	
	<meta charset="UTF-8">
	<title>数据分析</title>
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/common.css">
	<link href="${ctx}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/bootstrap/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link rel="stylesheet" href="${ctx}/assets/bootstrap/plugin/data-tables/DT_bootstrap.css"><!-- DATATABLE CSS -->
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/animations.css">
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/byit.css">
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/newuAdowncss/css.css">
	<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/newuAdowncss/newUser.css">
	
	<link rel="stylesheet" href="${ctx}/assets/css/reportshensuomenu.css">
	<link rel="stylesheet" href="${ctx}/assets/css/dataindexshowskin.css" id="layout-skin" />
	
	<link rel="stylesheet" href="${ctx}/assets/css/reportshensuomenu.css" />
	<link rel="stylesheet" href="${ctx}/assets/iconfont/1.0.2/iconfont.css" id="layout-skin" />
	
	<script src='${ctx}/assets/bootstrap/js/jquery-1.11.1.min.js'></script>
	<script src="${ctx}/assets/layer/layer.js"></script>
	<style type="text/css">
		.layout-side{
			width:200px;
			float:left;
		}
		.left-menu{
			width:200px;
		}
		.local-sub{
			left:0;
		}
		.dianzan_list_h5{
			width:97%;
			text-align:left;
			height:50px;
			line-height:50px;
			border-bottom: 1px dotted #f0f0f0;
			margin-left:22px;
			font-size:16px;
		}
		.side-menu .menu-item a:hover{
			text-decoration: none;
		}
		.layout-side-arrow{
			left: 200px;
			background:url(<%=request.getContextPath()%>/assets/images/report-icon.png) no-repeat;
		}
		
	</style>
	<script type="text/javascript">
		function hiddenMenu(){
			$(".layout-side-arrow").click();
		}
	</script>
</head>
<body onload="hiddenMenu()">
<div class="layout-side">
	<div class="left-menu div">
		<ul class="side-menu div">	  
		</ul>	
	</div>
</div>
<!--伸缩按钮-->
<div class="layout-side-arrow"><div class="layout-side-arrow-icon"><i class="icon-font  icon-icon-copy-copy"></i></div></div>

<div class="div">
	<input type="hidden"  id="reportid"  value="${reportid}" >
    <div class="repert-box" style='margin:0 auto;/* position: fixed; */ margin-top: 48px;'> 
		<iframe src="" id="reportifr" style="padding: 0px; overflow: auto;" frameborder="0" scrolling="yes" width="724" height="100%"></iframe>
	</div>	
</div>
   
</body>
<!--左侧垂直导航js-->
<script type="text/javascript" src="${ctx}/assets/js/report-sccl.js"></script>
<script type="text/javascript">

	$(function(){
		
		var reportid = $("#reportid").val();
		
		$.post("dataAnalysis/queryTableReport.do",{reportid:reportid},function(result){
				if (result.success==1){
					
					
			 		if(result.width){
						$('.repert-box').css('overflow','auto');
						$('#reportifr').width(result.width);
					} else {
						$('#reportifr').width($('body').width());
					}
					if(result.height){
						$('.repert-box').css('overflow','auto');
						$('#reportifr').height(result.height);
					} else {
						$('#reportifr').height(700);
					} 
					
					
					$('#reportifr').attr('src',result.url);
					document.getElementById("divVV").style.display="none";
				var tableauUser = result.tableauUser;
				
				}else if (result.error==4){
					layer.alert('提示当前service服务ip配置不正确，请管理员查看tableau权限配置中service服务ip配置是否正确');
				}else if (result.error==3){
					layer.alert('提示当前用户还未分配所属机构，他无权限访问报表！请联系管理员分配对应机构');
				}else if (result.error==2){
					layer.alert('提示获取tableau用户票证失败！请管理员查看tableau权限配置用户和密码是否正确,tableau服务器是否授权！');
				}else if (result.error==1){
					layer.alert('提示查看报表内容出错，tableau服务器未授权！请联系管理员！');
				}
				parent.$('#path').html('<span><b class="local_icon"></b><a id="theme" href="#">'+result.theme+'</a><i style="color: black;">/</i><a id="title" href="#">'+result.title+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>');
			},'json'); 
		
		
	})


</script>
</html>