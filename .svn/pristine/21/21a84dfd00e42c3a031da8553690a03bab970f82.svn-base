<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../_include/_taglib.jsp"%>
<%
	String from = request.getParameter("from");
    String type = request.getParameter("type");
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	 String username=UserHolder.getUsername();	
	 String loginid=UserHolder.getId();
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<title>数据分析平台</title>
<script type="text/javascript">
	var _ctx = '${ctx}'
</script>
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/common.css">
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/animations.css">
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/bootstrap.min.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/bootstrap-reset.css" />
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/byit.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/plugin/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/layer/1.9.3/skin/layer.css" />
<script src="${ctx}/assets/jquery/1.11.1/jquery.min.js"></script>
<script src="${ctx}/assets/layer/1.9.3/layer.js"></script>
<script src="${ctx}/assets/bootstrap/js/bootstrap.min.js"></script>

<style>
body, html {
	overflow-x: initial;
	background: #f0f0f0;
}

.iframes {
	float: left;
	margin-top: 120px;
	background-color: #fff;
	width: 100%;
}

.iframes iframe {
	float: left;
	width: 100%;
}
</style>
<script type="text/javascript"
	src="${ctx}/assets/bootstrap/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src='${ctx}/js/aladdin/dataAnalysis/dataanalysis.js'></script>
       <script type="text/javascript">
        var username='<%=username%>';
        var type='<%=type%>';
        var loginid='<%=loginid%>';
      /*   var text='<i class="fa fa-th-large"></i>数据分析';
        var url=_ctx+"/dataAnalysis/TableauDataAnalysis.html"; */
        $(function() {
        	
        	 $('#dengluSucess').html(username);
        	 if(type=='zhiku'){
        		 url=_ctx+"/dbDoc/zhiKu.html";
        		 text='<i class="fa fa-align-right"></i>数据智库';
        	 }else if(type=='dataanalysis'){
        		 url=_ctx+"/dataAnalysis/TableauDataAnalysis.html";
        		 text='<i class="fa fa-th-large"></i>数据分析';        		 
        	 }else if(type=='datasearch'){
        		 url=_ctx+"/selfHelpDataController/showSelfHelfData";
        		 text='<i class="fa fa-indent"></i>数据查询';        		 
        	 }else if(type=='dataindex'){
        		 url=_ctx+"/dataQuota/toDataIndexPage.html";
        		 text='<i class="fa fa-hospital-o"></i>数据指标';        		 
        	 }else if(type=='databulu'){
        		 url=_ctx+"/dataCollection/queryDataCollection.html";
        		 text='<i class="fa fa-bar-chart-o"></i>数据补录';        		 
        	 }
        	 $('#erji_cdan').html(text);
        	 $('#new_iframes').attr('src',url);
        });        
    </script> 	
</head>
<body style='width: 100%; height: 100%;' class='index_css'>
	<input type="hidden" name="Rpath" id="Rpath" value="<%=basePath%>">
	<div data-spm="2" class='nav_header'>
		<div class="ali-common-header">
			<div class="ali-common-header-inner common-header-clearfix">
				<!-- 运营专区 -->
				<div class="activity item pull-left">
					<div class="flash-wrap" id="J-ali-activity-img">
						<a href="${ctx}/index.html"> <img
							src="${ctx}/assets/bootstrap/images/logo_whi.png"
							style="width: 120px; margin-top: 17px; height: auto;"></a>
					</div>
				</div>
				<!-- 导航菜单 -->
				<ul class="menu item pull-left pull-left_ul">
					<li id="DATAANALYSIS" class="top-menu-item"><span class="menu-hd"> <a
							href="${ctx}/dataAnalysis/TableauDataAnalysis.html"
							target="new_iframes" id='data_fexi'>数据分析</a>
					</span></li>
					<li id="DATACOLLECTION" class="top-menu-item"><span class="menu-hd"> 数据补录 </span>
						<ol class="nav_header_ol" style=''>
							<li class="nav_header_oli"><a id="shujubulu"
								href="${ctx}/dataCollection/queryDataCollection.html"
								target="new_iframes">数据补录</a></li>
							<li class="nav_header_oli"><a id="bululishi"
								href="${ctx}/dataCollection/data_bulu_lishi.html"
								target="new_iframes">补录历史</a></li>
							<li class="nav_header_oli"><a id="bulushenpi"
								href="${ctx}/dataCollection/data_bulu_shenpi.html"
								target="new_iframes">补录审批</a></li>
							<li class="nav_header_oli"><a id="piliangshangchuan" 
								href="${ctx}/dataCollection/data_bulu_plsc.html"
								target="new_iframes">批量上传</a></li>
							<li class="nav_header_oli"><a id="shangchuanshenpi" 
								href="${ctx}/dataCollection/data_bulu_plsp.html"
								target="new_iframes">上传审批</a></li>
						</ol></li>
					<li id="DATAQUERY" class="top-menu-item"><span class="menu-hd"> <a id="shujuchaxun"
							href="${ctx}/selfHelpDataController/showSelfHelfData"
							target="new_iframes">数据查询</a>
					</span></li>
					<li id="DATAINDICATORS" class="top-menu-item"><span class="menu-hd"> <a id="shujuzhibiao"
							href="${ctx}/dataQuota/toDataIndexPage.html" target="new_iframes">数据指标</a>
					</span></li>
					<li id="DATATHINK-TANK" class="top-menu-item"><span class="menu-hd"> <a id="shujuzhiku"
							href="${ctx}/dbDoc/zhiKu.html" target="new_iframes">数据智库</a>
					</span></li>
					<li id="RANKINGLIST" class="top-menu-item"><span class="menu-hd"> <a id="paihangbang"
							href="${ctx}/dataAnalysis/DataAnalysisInfo.html"
							target="new_iframes">排行榜</a>
					</span></li>
					<li id="PLATFORMFUNCTION" class="top-menu-item"><span class="menu-hd"> 平台功能 </span>
						<ol class="nav_header_ol" style=''>
							<li class="nav_header_oli">
								<a id="shujudingyue" href="${ctx}/dataPush/queryDataPush.html" target="new_iframes">数据订阅</a>
							</li>
							<li class="nav_header_oli"><a id="shujudayi"
								href="${ctx}/dqQusnfo/queryQuestion.html" target="new_iframes">数据答疑</a>
							</li>
							<li class="nav_header_oli"><a id="shujuxiazai"
								href="${ctx}/dataFileEntityController/showMainView"
								target="new_iframes">数据下载</a></li>
							<li class="nav_header_oli"><a id="rizhifenxi"
								href="${ctx}/logAnalysis/logAnalysis.html" target="new_iframes">日志分析</a>
							</li>
							<%-- 	<li class="nav_header_oli">
						<a href="${ctx}/logss/logmain.html" target="new_iframes">日志查询</a>
					</li> --%>
						</ol></li>
					<li id="PLATFORMMANAGEMRNT" class="top-menu-item"><span class="menu-hd"> <a
							href="${ctx}/admin/index2.html">平台管理</a>
					</span></li>
					<li id="PERSONCENTER" class="top-menu-item"><span class="menu-hd"> <a id="personCenter"
							href="${ctx}/personCenter/personCenter.html" target="new_iframes">个人中心</a>
					</span></li>
				</ul>
				<div class="nav_right">
				<_:isAuthc>
					 <a href="javascript:;" id='collections'>收藏</a>
	                 <a href="${ctx}/logout.html" id='logout' style='margin-top: 17px;float: left;line-height: 20px;height: 17px;'>退出</a>
				</_:isAuthc>
				<span class='dengluSucess' id="dengluSucess" ></span> <!--  -->
				</div>
			</div>
		</div>
		<!-- 二级菜单 -->
		<div class='erji_caidan'>
			<div id="erji_cdan" class='erji_cdan'>
				
			</div>
		</div>
		<!-- 二级菜单 结束 -->
	</div>
	<!-- nav over -->
	<div class="iframes">
		<iframe frameborder="0" id='new_iframes' name="new_iframes" ></iframe>
	</div>
	<!-- login -->
	<div class="login_div" style='display: none;' id='login_erd'>
		<div class="login_divin pt-page-scaleUpCenter">
			<div class="colose" id='colose'>
				<img src="${ctx}/assets/bootstrap/images/close_fa.png" alt="">
			</div>
			<h2 class='qingdelu'>请登录</h2>
			<div class="login_input">
				<li class='login_input_li'><input type="text"
					placeholder='请输入帐号'></li>
				<li class='login_input_li'><input type="paassword"
					placeholder='请输入密码'></li>
				<li class='login_input_li'><span></span></li>
				<li class='login_input_li'>
					<button>登录</button>
				</li>
			</div>
		</div>
	</div>
	<footer
		style='background-color: #fbfbfb; color: #999; border-top: 1px solid #eee;'>
		恒丰银行移动金融部 All Rights Reserved
		 </footer>
	<script type="text/javascript" src='${ctx}/assets/bootstrap/js/index.js'></script>
	
	<script>
		var this_wdith = $('body,html').width();
		var this_height = $('body,html').height();
		$('.iframes iframe').height(this_height - 171);
		$('#erji_cdan').html('<i class="fa fa-th-large"></i>数据分析');
		$('#data_fexi').click(function(){
			$('#erji_cdan').html('<i class="fa fa-th-large"></i>数据分析');
		}); 
		$("#shujubulu").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-bar-chart-o"></i>数据补录');
		});
		$("#bululishi").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-list-ul"></i>补录历史');
		});
		$("#bulushenpi").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-th"></i>补录审批');
		});
		$("#piliangshangchuan").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-align-right"></i>批量上传');
		});
		$("#shangchuanshenpi").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-list-alt"></i>上传审批');
		});
		$("#shujuchaxun").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-indent"></i>数据查询');
		});
		$("#shujuzhibiao").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-hospital-o"></i>数据指标');
		});
		$("#shujuzhiku").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-align-right"></i>数据智库');
		});
		$("#paihangbang").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-list-ol"></i>排行榜');
		});
		$("#shujudingyue").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-user"></i>数据订阅');
		});
		$("#shujudayi").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-signal"></i>数据答疑');
		});
		$("#shujuxiazai").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-sitemap"></i>数据下载');
		});
		$("#rizhifenxi").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-list-ul"></i>日志分析');
		});
		$("#personCenter").click(function(){ 
			$('#erji_cdan').html('<i class="fa fa-list-ul"></i>个人中心');
		});
		
	</script>
</body>
</html>
