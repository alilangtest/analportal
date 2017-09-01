<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@tag import="byit.core.util.convert.ConvertUtil"%>
<%@tag import="byit.osdp.base.security.UserHolder"%>
<%@attribute name="not" type="java.lang.String" required="false"%>
<%--[权限标签，如果用户认证通过(已经登录)，则显示body内容]--%>
<%
	boolean not = ConvertUtil.toBoolean((String)jspContext.getAttribute("not"),Boolean.FALSE);
	if (UserHolder.isAuthc() != not) {
%>
<jsp:doBody />
<%
	}
%>