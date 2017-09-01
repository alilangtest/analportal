﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/bootstrap-reset.css" />
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/byit.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/plugin/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/layer/1.9.3/skin/layer.css" />
	
<script type="text/javascript"
		src='${ctx}/assets/bootstrap/js/index.js'></script>
<script type="text/javascript" src="${ctx}/assets/bootstrap/js/jquery.dcjqaccordion.2.7.js"></script>

<script src="${ctx}/assets/json2/json2.js"></script>
<script src="${ctx}/assets/jquery/1.11.1/jquery.min.js"></script>
<script src="${ctx}/assets/jquery/patch/jquery-patch-1244.js"></script>
<script src="${ctx}/assets/easyui/1.4.5/jquery.easyui.min.js"></script>
<script src="${ctx}/assets/easyui/1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx}/assets/easyui/patch/easyui-patch.js"></script>

<script src="${ctx}/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/assets/bootstrap/plugin/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/assets/bootstrap/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>

<script src="${ctx}/assets/layer/1.9.3/layer.js"></script>
<script src="${ctx}/assets/laydate/1.1/laydate.js"></script>
<style>
body, html {
	overflow-y: auto;
	background: #f0f0f0;
}

.iframes {
	float: left;
	margin-top: 16px;
	background-color: #fff;
	height:100%;
	width: 100%;
}

.iframes iframe {
	height:100%;
	float: left;
	width: 100%;
	overflow:auto;
}
.nav_right a.update-lock{
	width:19px;
	height:19px;
	display:inline-block;
	position:absolute;
	top:2px;
	right:60px;
}
.nav_right a .fa-lock{
	font-size:19px;
}
.nav_right a:hover .fa-lock:before{
	color:#3aa3ff;
}
</style>

       <script type="text/javascript">
        var username='<%=username%>';
        var type='<%=type%>';
        var loginid='<%=loginid%>';
        
        $(function() {
        	$.getJSON(_ctx + "/show/load.do", {},
        			function(objJson){
				var ul = document.getElementById("show");
  				for ( var j = 0; j < objJson.length; j++) {
  					var name = objJson[j].name;
  					var path = objJson[j].path;
  					
						var li = document.createElement("li");
						var span = document.createElement("span");
						var a = document.createElement("a");
						if(j == 0){
							li.id = "liselect";
							a1 = a;
							a.className = "select";
						}
						li.className = "top-menu-item";
						li.onclick = function(){
							changeStyle(this);
						}
						span.className = "menu-hd";
						a.href = _ctx + "/" + path;
						a.target = "new_iframes";
						a.innerHTML = name;
						span.appendChild(a);
						li.appendChild(span);
						ul.appendChild(li);
  				}
  				a1.click();
          	});
        	
        	/* $.getJSON(_ctx + "/show/checkPassword.do", {},
        			function(objJson){
        		var flag = objJson;
        		var checkPassword = _ctx + '/show/checkPasswordHtml.html';
        		if(flag){
        			$.dlg.open({
        				title : '修改密码',
        				area : [ '430px', '200px' ],
        				closeBtn: 0,
        				content : checkPassword
        			});
        		}
        	}); */
        });
        function changeStyle(obj){
        	$(".top-menu-item").each(function (){
        		$(this).attr("id","");
        		$(this).find("a").removeClass();
        	});
        	$(obj).find("a").addClass("select");
        	$(obj).attr("id","liselect");
        }
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
						<div>
							<img src="${ctx}/assets/bootstrap/images/logo_whi.png" style="width: 140px; margin-top: 13px;margin-left: 30px; height: 34px;">
						</div>
					</div>
				</div>
				<!-- 导航菜单 -->
				<ul id="show" class="menu item pull-left pull-left_ul">
				</ul>
				<div class="nav_right">
				<_:isAuthc>
					 <a href="javascript:;" class="shoucang" id='collections' style="display: none;">收藏</a>
	                 <a href="${ctx}/logout.html" id='logout' title="退出" class="header-right-img"></a>
				</_:isAuthc>
				<!-- <b class="touxiang"></b> -->
				<span id="dengluSucess"><%=username %></span> <!--  -->
				<a class="update-lock" id="password" title="修改密码" href="#"><i class='fa fa-lock'></i></a>
				</div>
			</div>
		</div>
		<!-- 二级菜单 -->
		<div class='erji_caidan' style="display: none;">
			<div id="erji_cdan" class='erji_cdan'>
				
			</div>
		</div>
		<!-- 二级菜单 结束 -->
	</div>
	<!-- nav over -->
	<div class="iframes">
		<iframe frameborder="0" id='new_iframes' name="new_iframes" src=""></iframe>
	</div>
	<footer style='background-color: #fbfbfb; color: #999; border-top: 1px solid #eee;'>
		恒丰银行移动金融部 All Rights Reserved</footer>
	<script>
		var this_wdith = $('body,html').width();
		var this_height = $('body,html').height();
		var editPasswordPage = _ctx + '/auth_user/auth_user_password.html';
		var password = $('#password');
		
		password.on('click', function() {
			$.dlg.open({
				title : '修改密码',
				area : [ '430px', '280px' ],
				content : editPasswordPage
			});
		});
		$('.iframes iframe').height(this_height - 48);
	</script>
</body>
</html>
