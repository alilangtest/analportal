<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@include file="../_include/_taglib.jsp"%>
<%@page import="org.apache.log4j.Logger"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>

<!-- TODO。生产环境需要使用 -->
<%
response.setHeader("_SESSION_TIMEOUT", "Y");
Logger logger=Logger.getLogger("ssoindex.jsp");
logger.debug("进入ssoindex.jsp页面");
/* 获取客户端url。循环查询是否有cookie */
List<String> list=(List)request.getAttribute("urlList");
if(list!=null){
	for(int i=0;i<list.size();i++){
		String url=list.get(i);
		logger.debug("第"+i+"个sso客户端为"+list.get(i)+",客户端地址为："+url);
		pageContext.setAttribute("ssourl", url);
%>
	<script type="text/javascript" src=${ssourl}></script>
<%
	}
}
%>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/login/ssoindex.js"></script>
</head>
<body>
</body>
</html>
