<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tools.HttpUtils"%>
<%
	HttpUtils.clearSessionMember(request);
	HttpUtils.forward("/frontend/member/login_Member.jsp", request, response);
	return;
%>