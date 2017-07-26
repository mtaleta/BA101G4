<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.feature.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	FeatureDAO dao = new FeatureDAO();
    List<FeatureVO> list = dao.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有後端功能資料 - listAllFeature.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有後端功能資料 - listAllFeaure.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/feature/select_page.jsp"><img src="feature_img/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>後端功能編號</th>
		<th>功能名稱</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="featureVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle'}>
			<td>${featureVO.feature_id}</td>
			<td>${featureVO.feature_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do">
			     <input type="submit" value="修改">
			     <input type="hidden" name="feature_id" value="${featureVO.feature_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="feature_id" value="${featureVO.feature_id}">
			    <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</body>
</html>
