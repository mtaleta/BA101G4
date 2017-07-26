<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tools.HttpUtils"%>
<%
	HttpUtils.clearSessionAdmin(request);
	HttpUtils.forward("/backend/index.jsp", request, response);
	return;
%>