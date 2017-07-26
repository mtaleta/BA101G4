<%@page import="com.tools.HttpUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	HttpUtils.clearSessionStore(request);
	HttpUtils.forward("/frontend/store/login_Store.jsp", request, response);
	return;
%>