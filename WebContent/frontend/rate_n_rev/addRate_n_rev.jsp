<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rate_n_rev.model.*"%>
<% 
Rate_n_revVO rate_n_revVO = (Rate_n_revVO) request.getAttribute("rate_n_revVO");
String store_id = request.getParameter("store_id");

 %>

<html>
<head>
<title>�d���s�W - addRate_n_rev.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#fcf2c4' align='center' valign='middle' height='20'>
		<td>
		<h3>�d���s�W - addRate_n_rev.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/select_page.jsp">�^����</a>
	    </td>
	</tr>
</table>

<h3>�����P�P�Q:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/rate_n_rev/rate_n_rev.do" name="form1">
<table border="0">

	
	<tr>
		<td>����:</td>
		<td><input type="TEXT" name="rate" size="45" 
			value="<%= (rate_n_revVO==null)? 5 : rate_n_revVO.getRnr_rate()%>" /></td>
	</tr>
	<tr>
		<td>�P�Q:</td>
		<td><input type="TEXT" name="rev" size="45"
			value="<%= (rate_n_revVO==null)? "good" :rate_n_revVO.getRnr_rev()%>" /></td>
	</tr>
	
	<%-- <tr>
		<%java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());%>
		
	</tr> --%>
	
	<!-- <tr><textarea class="ckeditor" cols="80" id="content" name=content rows="12"></textarea></tr> -->

	<jsp:useBean id="rate_n_revSvc" scope="page" class="com.rate_n_rev.model.Rate_n_revService" />
	

</table>
<br>
<input type="hidden" name="store_id" value="<%=store_id %>">

<input type="hidden" name="action" value="insertRate_n_rev">
<input type="submit" value="�e�X�s�W">
</FORM>
</body>

</html>
<script src="js/jquery.min.1.11.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>


