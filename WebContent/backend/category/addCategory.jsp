<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.category.model.*"%>
<%
CategoryVO categoryVO = (CategoryVO) request.getAttribute("categoryVO");
%>

<jsp:useBean id="cateSvc" scope="page" class="com.category.model.CategoryService" />

<html>
<head>
<title>商品類型新增 - addCategory.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1300'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>商品類型新增 - addCategory.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/select_page.jsp"><img src="<%=request.getContextPath()%>/img/tomcat.gif" width="100" height="100" border="1">回首頁</a>
	    </td>
	</tr>
</table>

<h3>商品類型:</h3>
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

<table border='1' bordercolor='#CCCCFF' width='1300'>
	<tr>
		<td>商品類型編號</td>
		<td>商品類型名稱</td>
		<td>購物類型</td>
	</tr>
	<c:forEach var="cateVO" items="${cateSvc.all}">
		<tr>
			<td>${cateVO.cate_id}</td>
			<td>${cateVO.cate_name}</td>
			<td>
				<c:if test="${cateVO.prod_category == 1}">購物</c:if>
				<c:if test="${cateVO.prod_category == 2}">外帶外送</c:if>
			</td>
		</tr>
	</c:forEach>
</table>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/category/category.do" name="form1">
<table border="0">
	<tr>
		<td>商品類型名稱:</td>
		<td>
			<input type="TEXT" name="cate_name" size="45"
				value="<%= (categoryVO==null)? "咖啡" : categoryVO.getCate_name()%>" />
		</td>
	</tr>
	<tr>
		<td>購物類型:</td>
		<td>
		    <input type="RADIO" name="prod_category" id="shop" value="1">
		    <label for="shop">購物</label><br>
		    <input type="RADIO" name="prod_category" id="take" value="2">
		    <label for="take">外帶外送</label>
		</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>
