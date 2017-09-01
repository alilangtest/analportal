<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@tag import="byit.core.util.convert.ConvertUtil"%>
<%@tag import="byit.osdp.base.security.UserHolder"%>
<%@attribute name="code" type="java.lang.String" required="false"%>
<%@attribute name="not" type="java.lang.String" required="false"%>
<%--[权限标签，如果用户有参数code的权限，则显示body内容]--%>
<%
	String value = (String)jspContext.getAttribute("code");
	boolean not = ConvertUtil.toBoolean((String)jspContext.getAttribute("not"),Boolean.TRUE);
	if (UserHolder.hasPermCode(code) != not) {
%>
<jsp:doBody />
<%
	}
%>