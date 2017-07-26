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
<title>標籤新增 - addStore_tag.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#fcf2c4' align='center' valign='middle' height='20'>
		<td>
		<h3>標籤新增 - addStore_tag.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/select_page.jsp">回首頁</a>
	    </td>
	</tr>
</table>

<h3>標籤內容:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/tag/tag.do" name="form1">
<table border="0">

	
	
	<tr>
		<td>標籤內容:</td>
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
<input type="submit" value="送出新增">
</FORM>
</body>

</html>
