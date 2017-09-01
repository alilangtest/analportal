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
	
	<!-- 左侧菜单相关 -->
	<link rel="stylesheet" href="${ctx}/assets/css/dataindexshow.css">
	<link rel="stylesheet" href="${ctx}/assets/css/dataindexshowskin.css" id="layout-skin" />
	<link rel="stylesheet" href="${ctx}/assets/css/reportshensuomenu.css" />
	<link rel="stylesheet" href="${ctx}/assets/iconfont/1.0.2/iconfont.css" id="layout-skin" />
	<!-- 左侧菜单相关 -->
	
	<script src='${ctx}/assets/bootstrap/js/jquery-1.11.1.min.js'></script>
	<script src="${ctx}/assets/layer/layer.js"></script>
	<script type="text/javascript" src='${ctx}/js/aladdin/dataAnalysis/dataanalysis.js'></script><link><link>
	<script type="text/javascript">
	  var userid='<%=userid%>';
	</script>
	<style type="text/css">
		/**媒体查询开始**/
		@media(max-width:1280px) {
			.dianzan_list{
				width:82.5%;	
			}	
		}
		@media(min-width:1280px) and (max-width:1440px) {
			.dianzan_list{
				width:83.5%;	
			}
		}
		@media(min-width:1441px) and (max-width:1680px) {
			.dianzan_list{
				width:86%;	
			}
		}
		@media(min-width:1681px) and (max-width:1920px) {
			.dianzan_list{
				width:88%;	
			}
		}
		/**媒体查询结束**/
		
		.dianzan_con{
			background:#fff;
			margin-top:20px;
			min-height:500px;
		}
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
		.dianzan_list{
			min-height:758px;
			float:right;
			margin-top: 0;
			margin-right:10px;
			background:none;	
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
			color:#33a3ff;
		}
	</style>
</head>
<body>
<!-- <input type="hidden" name="repLab" id="repLab" value=""> --><!--报表标签  -->
<!-- <input type="hidden" name="repAtt" id="repAtt" value=""> --><!--报表属性  -->
<input type="hidden"  id="id"  value="${id}" >
<div class="div">
    <!--banner-->
    <div class="mainbody" style="margin-top:34px;" id="divVV">
        <!-- <div class="collbdy" style='min-height:0;'>
        	<div class="tiaojian">
                <ul class='tiaojian_bottom' style='' id='hide_div'>
                </ul>
            </div>
        </div> -->
   		
   		<!-- 左侧菜单 -->
        <div class="layout-side">
			<div class="left-menu div">
				<ul class="side-menu">	
				</ul>	
			</div>
		</div>
   		<!-- 右侧内容 -->
        <div class="dianzan_list"> 
        	<div class="header-top-search">
				<em class="local-sub"><i class="local-icon"></i><span>数据分析</span></em>
			</div> 
        	<div class="dianzan_con">
        		<h5 class='dianzan_list_h5'>报表名称</h5>
				<ul class="dianzan_list_ul" id="ULTable">
				</ul>
			</div>	
        </div>
        
        
    </div>
  <!--   <div class="repert-box" style='margin:0 auto;'> 
		<iframe src="" id="reportifr" style="padding: 0px;overflow: hidden;" frameborder="0" scrolling="no" width="724px" height="100%"></iframe>
	</div>	 -->
	
</div>
<!--左侧垂直导航js-->
<script type="text/javascript" src="${ctx}/assets/js/data-fenxi-sccl.js"></script>
</body>
</html>