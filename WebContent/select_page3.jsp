<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />
<jsp:useBean id="tagVO" scope="page" class="com.tag.model.TagVO" />
<jsp:useBean id="storetagDAO" scope="page" class="com.store_tag.model.Store_tagDAO" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />

<% session.setAttribute("STORE", storeSvc.getOneStore("STORE00000032")); %>


<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">

</style>
<head><title>IBM ���a: Home</title></head>
<body bgcolor='white'>

<a href='<%=request.getContextPath()%>/select_page.jsp'>�^�e��(�@��|��)</a>

<p>This is the Home page for BA101G4 Store: Home</p>

<h3>��Ƭd��:</h3>
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

<ul>
  <li><a href='<%=request.getContextPath()%>/news/news.do?action=getAllNewsBYStore_id'>�̷s�����޲z</a> </li> <br>
  
  
</ul>

<p></p>

<%if (request.getAttribute("newsList")!=null){%>
       <jsp:include page="/frontend/store/listOfAllNewsByStore.jsp" />
<%} %>


<div >
	<h3><a href="<%=request.getContextPath()%>/frontend/news/addNews.jsp">�s�W�̷s����</a></h3>
</div> 

</body>

</html>
