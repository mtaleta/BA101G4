<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tag.model.*"%>
<%@ page import="com.store.model.*"%>
<% 
TagVO tagVO = (TagVO) request.getAttribute("TagVO");
String store_id = request.getParameter("store_id");

 %>

<html>
<head>
<title>���ҷs�W - addStore_tag.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#fcf2c4' align='center' valign='middle' height='20'>
		<td>
		<h3>���ҷs�W - addStore_tag.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/select_page.jsp">�^����</a>
	    </td>
	</tr>
</table>

<h3>���Ҥ��e:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/tag/tag.do" name="form1">
<table border="0">

	
	
	<tr>
		<td>���Ҥ��e:</td>
		<td><input type="TEXT" name="tag_content" size="45"
			value="<%= (tagVO==null)? "" :tagVO.getTag_content()%>" /></td>
	</tr>
	
	<%-- <tr>
		<%java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());%>
		
	</tr> --%>
	
	


</table>
<br>
<input type="hidden" name="store_id" value="<%=store_id %>">

<input type="hidden" name="action" value="insertStore_tag">
<input type="submit" value="�e�X�s�W">
</FORM>
</body>

</html>
