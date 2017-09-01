﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/bootstrap/js/jquery.dcjqaccordion.2.7.js"></script>
<script type="text/javascript" src="${ctx}/assets/bootstrap/js/common-scripts.js"></script>
<script type="text/javascript" src="${ctx}/assets/_osdp/admin/admin_index.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/easyui_change1.css" />
<link href="${ctx}/assets/bootstrap/css/style.css" rel="stylesheet">
<link href="${ctx}/assets/bootstrap/css/style-responsive.css" rel="stylesheet">
<style>
	html,body{height:100%; overflow: hidden;}
</style>
</head>
<body>
	<!-- BEGIN SECTION -->
	<section id="container" class="">
		<!-- BEGIN HEADER -->
		<%-- <header class="header white-bg" style="display: none;">
			<!-- SIDEBAR TOGGLE BUTTON  END-->
			<a href="index3.html" class="logo" style='margin-left: 6px;'>
				<!-- olive<span>admin</span> --> <img src="${ctx}/assets/bootstrap/img/logo@3x.png" alt="" style='width: 120px; height: auto;'>
			</a>
			<!-- END HEADER NAV -->
			<!-- START USER LOGIN DROPDOWN  -->
			<div class="top-nav ">
				<ul class="nav pull-right top-menu">
					<li id="password" class="dropdown dropdownli" style='' title="修改密码"><a class='fa fa-lock' style='margin-top:13px;'> </a></li>
					<li class="dropdown dropdownli" style='' title="退出"><a href="${ctx}/logout.html" class='fa fa-sign-in' style='margin-top:13px;'> </a></li>
				</ul>
			</div>
			<!-- END USER LOGIN DROPDOWN  -->
		</header> --%>
		<!-- END HEADER -->
		<!-- BEGIN SIDEBAR -->
		<aside>
			<div id="sidebar" class="nav-collapse">
				<ul class="sidebar-menu" id="nav-accordion" style="margin-top:55px;">
					<%-- <li class="sub-menu">
						<a href="javascript:void(0);" class="active"> <i class="fa fa-th"></i> 
							<span>系统管理</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/auth_org/auth_org_main.html" target="oFrame">机构管理</a></li>
							<li><a href="${ctx}/auth_user/auth_user_main.html" target="oFrame">用户管理eeeee</a></li>
							<li><a href="${ctx}/auth_role/auth_role_main.html" target="oFrame">角色管理</a></li>
							<li><a href="${ctx}/auth_permission/auth_permission_main.html" target="oFrame">菜单管理</a></li>
							<!-- <li><a href="dynamic_table.html">系统菜单管理</a></li> -->
							<li><a href="${ctx}/logss/logmain.html" target="oFrame">日志管理</a></li>
							<li><a href="${ctx}/orderAbtainController/showMainView">数据快查管理</a></li>
							<li><a href="${ctx}/selfHelpDataController/showSelfHelfData">自助取数</a></li>
							<li><a href="${ctx}/dataFileEntityController/showMainView">数据下载</a></li>
						</ul>
					</li>
					
					
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>报表管理</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/reportMenu/reportMenu.html" target="oFrame">报表菜单维护</a></li>
				            <li><a href="${ctx}/report/report.html" target="oFrame">报表维护</a></li>
				            <li><a href="${ctx}/report/reportRole.html" target="oFrame">报表授权</a></li>			
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>tableau服务配置</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/tableauserver/tableauServer.html" target="oFrame">tableau数据库配置</a></li>
           	 				<li><a href="${ctx}/tableauuser/tableauUser.html" target="oFrame">tableau权限配置</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>数据答疑管理</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/dqQusnfo/queryAdminQuestion.html" target="oFrame">答疑管理</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>数据智库管理</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/dbDoc/queryDbDoc.html" target="oFrame">智库管理</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>数据指标管理</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/dataIndex/dataIndex.html" target="oFrame">数据指标</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>数据权限配置</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/orderAbtainController/showCheckData" target="oFrame">数据权限配置</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>数据补录管理</span>
						</a>
						<ul class="sub">
							<li><a href="${ctx}/data_supplement/queryDS.html" target="oFrame">数据补录配置</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a href="javascript:void(0);" > <i class="fa fa-th"></i> 
							<span>数据订阅管理</span>
						</a>
						<ul class="sub">
							<li><a href="javascript:void(0);">数据订阅配置</a></li>
						</ul>
					</li>
					
					
					<li class="sub-menu">
						<a href="javascript:;"> 
							<i class="fa fa-sitemap"></i> 
							<span>三级菜单</span>
						</a>
						<ul class="sub">
							<li>
								<a href="javascript:;"></a>
							</li>
							<li class="sub-menu"><a href="javascript:void(0);"><span class="label label-primary">1</span></a>
								<ul class="sub">
									<li><a href="javascript:;"></a></li>
									<li class="sub-menu"><a href="javascript:;"><span class="label label-primary">3</span></a>
										<ul class="sub">
											<li><a href="javascript:;"></a>1</li>
											<li><a href="javascript:;"></a>2</li>
											<li><a href="javascript:;"></a>3</li>
										</ul></li>
								</ul>
							</li>
						</ul>
					</li> --%>
				</ul>
			</div>
		</aside>
		<!-- END SIDEBAR -->

		<section id="main-content">
			<section class="wrapper site-min-height" id='site-min-height' style='min-height:500px; margin-top:44px;'>
				<iframe id="oFrame" name="oFrame" src="about:blank" style="width:100%;height:100%;border:none;padding:0px 0px;overflow:hidden;"></iframe>
			</section>
		</section>

		<%-- <%@include file="../_include/_footer.jsp"%> --%>

	</section>

	<script type="text/javascript">
	
		//$(window)
		$(document.body).on('click','.sub-menu ul.sub li',function(){
			$(this).addClass('active').siblings('li').removeClass('active');
			$(this).parents('.sub-menu').siblings('.sub-menu').find('li').removeClass('active');
		});
		var iframes_h = $('body').height();
		/* $('#oFrame').height(iframes_h); */
		$('#site-min-height').height(iframes_h- 60);
		$('.box-collect').click(function() {
			layer.open({
				type : 2,
				title : '收藏列表',
				fix : false,
				maxmin : true,
				shadeClose : true,
				area : [ '1000px', '530px' ],
				content : 'collection_list.html',
				end : function() {
					layer.tips('Hi', '#about', {
						tips : 1
					})
				}
			});
		});
	</script>
</body>
</html>


