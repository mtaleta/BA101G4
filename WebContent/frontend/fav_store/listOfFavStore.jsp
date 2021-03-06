<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.fav_store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page import="java.util.Map" %>
<%
StoreVO storeVO = (StoreVO) request.getAttribute("storeVO");//EmpServlet.java(Concroller), 存入req的empVO物件

MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
String mem_id = memberVO.getMem_id();

Fav_storeService fav_storeSvc = new Fav_storeService();
Set<Fav_storeVO> fav_storeSet = fav_storeSvc.getAllFavStoreByMem_id(mem_id);
pageContext.setAttribute("fav_storeSet",fav_storeSet);
%>


<jsp:useBean id="storeSrv" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="store_tagDAO" scope="page" class="com.store_tag.model.Store_tagDAO" />
<html>
<head>
<title>店家列表 - listOfStore.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#c4c099' align='center' valign='middle' height='20'>
		<td>
		<h3>店家列表 - listOfFavStore.jsp</h3>
		<a href="<%=request.getContextPath()%>/select_page.jsp">回首頁</a>
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

<table border='1' bordercolor='#774f34' width='1600'>
	<tr>
		<th>店家編號</th>
		<th>店家姓名</th>
		<th>地址</th>
		<th>大頭貼</th>
		<th>查詢詳細資料</th>
	</tr>
	<tr align='center' valign='middle'>
	
	<%-- 	<td><%=storeVO.getStore_id() %></td>
		<td><%=storeVO.getStore_name() %></td>
		<td><%=storeVO.getStore_add() %></td> --%>
		
	<c:if test="${not empty storeVO}">
		<td>${storeVO.store_id}</td>
		<td>${storeVO.store_name}</td>
		<td>${storeVO.store_add}</td>
	</c:if>
		
		<%-- <%@ include file="page3.file"  %>  --%>
			
		<c:forEach var="fav_storeSet" items="${fav_storeSet}">
		<tr align='center' valign='middle'>
			<td>${storeSrv.getOneStore(fav_storeSet.store_id).store_id}</td>
			<td>${storeSrv.getOneStore(fav_storeSet.store_id).store_name}</td>
			<td>${storeSrv.getOneStore(fav_storeSet.store_id).store_add}</td>
			<td><img src="<%=request.getContextPath()%>/images?action=store&id=${fav_storeSet.store_id}"></td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/store/store.do">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="store_id" value="${fav_storeSet.store_id}">
			    <input type="hidden" name="action" value="Store_ByStore_id">
			</td></FORM>
				
			
        </c:forEach>
        
       
        
	</tr>
	
	
</table>



</body>
</html>
