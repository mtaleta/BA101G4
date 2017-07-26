<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.category.model.*"%>
<%
CategoryVO categoryVO = (CategoryVO) request.getAttribute("categoryVO");
%>

<jsp:useBean id="cateSvc" scope="page" class="com.category.model.CategoryService" />

<html>
<head>
<title>�ӫ~�����s�W - addCategory.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1300'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�ӫ~�����s�W - addCategory.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/select_page.jsp"><img src="<%=request.getContextPath()%>/img/tomcat.gif" width="100" height="100" border="1">�^����</a>
	    </td>
	</tr>
</table>

<h3>�ӫ~����:</h3>
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<table border='1' bordercolor='#CCCCFF' width='1300'>
	<tr>
		<td>�ӫ~�����s��</td>
		<td>�ӫ~�����W��</td>
		<td>�ʪ�����</td>
	</tr>
	<c:forEach var="cateVO" items="${cateSvc.all}">
		<tr>
			<td>${cateVO.cate_id}</td>
			<td>${cateVO.cate_name}</td>
			<td>
				<c:if test="${cateVO.prod_category == 1}">�ʪ�</c:if>
				<c:if test="${cateVO.prod_category == 2}">�~�a�~�e</c:if>
			</td>
		</tr>
	</c:forEach>
</table>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/category/category.do" name="form1">
<table border="0">
	<tr>
		<td>�ӫ~�����W��:</td>
		<td>
			<input type="TEXT" name="cate_name" size="45"
				value="<%= (categoryVO==null)? "�@��" : categoryVO.getCate_name()%>" />
		</td>
	</tr>
	<tr>
		<td>�ʪ�����:</td>
		<td>
		    <input type="RADIO" name="prod_category" id="shop" value="1">
		    <label for="shop">�ʪ�</label><br>
		    <input type="RADIO" name="prod_category" id="take" value="2">
		    <label for="take">�~�a�~�e</label>
		</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>

</html>
