<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 response.setHeader("_SESSION_TIMEOUT", "Y");
%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/login/login.js"></script>
<style type="text/css">
.xiao{
	cursor: pointer;
    width: 234px;
    height: 36px;
    line-height:36px;
    margin-top: 60px;
    padding: 0;
    /* border-radius: 6px; */
    background: #2b89d6;
   /*  -moz-border-radius: 6px;
    -webkit-border-radius: 6px;   
    border: 1px solid #2b89d6;
    -moz-box-shadow: 0 15px 30px 0 rgba(255,255,255,.25) inset, 0 2px 7px 0 rgba(0,0,0,.2);
    -webkit-box-shadow: 0 15px 30px 0 rgba(255,255,255,.25) inset, 0 2px 7px 0 rgba(0,0,0,.2);
    box-shadow: 0 15px 30px 0 rgba(255,255,255,.25) inset, 0 2px 7px 0 rgba(0,0,0,.2); */
    font-family: 'PT Sans', Helvetica, Arial, sans-serif;
    font-size: 14px;
    font-weight: 700;
    color: #fff;
    /* text-shadow: 0 1px 2px rgba(0,0,0,.1);
    -o-transition: all .2s;
    -moz-transition: all .2s;
    -webkit-transition: all .2s;
    -ms-transition: all .2s; */
}
</style>


<!-- CSS -->
<!-- <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'> -->
<link rel="stylesheet" href="${ctx}/assets/assets/css/reset.css">
<link rel="stylesheet" href="${ctx}/assets/assets/css/supersized.css">
<link rel="stylesheet" href="${ctx}/assets/assets/css/style.css">
</head>
<body>
<div class="content-body">
		<div class="login-box">
			<h1>数据分析平台</h1>
			<form action="">
				 <div class="user-box"><i class="user-icon-left"></i><input id="username" onkeydown="BindEnter()" type="text" class="pufa_ipt user-icon" placeholder="用户名"/></div>
				 <div class="user-box"><i class="pwd-icon-left"></i><input id="password" onkeydown="BindEnter()" type="password" class="pufa_ipt pwd-icon" placeholder="密码" /></div>
				 <a href="#" id="submit" ><div class="xiao">登&nbsp;录</div></a>
			</form>
		</div>
<div class="footer-text">恒丰银行移动金融部 All Rights Reserved</div>		
</div>		
</body>
</html>
