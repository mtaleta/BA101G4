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
StoreVO storeVO = (StoreVO) request.getAttribute("storeVO");//EmpServlet.java(Concroller), �s�Jreq��empVO����

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
<title>���a�C�� - listOfStore.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#c4c099' align='center' valign='middle' height='20'>
		<td>
		<h3>���a�C�� - listOfFavStore.jsp</h3>
		<a href="<%=request.getContextPath()%>/select_page.jsp">�^����</a>
		</td>
	</tr>
</table>

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

<table border='1' bordercolor='#774f34' width='1600'>
	<tr>
		<th>���a�s��</th>
		<th>���a�m�W</th>
		<th>�a�}</th>
		<th>�j�Y�K</th>
		<th>�d�߸ԲӸ��</th>
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
			    <input type="submit" value="�e�X�d��"> 
			    <input type="hidden" name="store_id" value="${fav_storeSet.store_id}">
			    <input type="hidden" name="action" value="Store_ByStore_id">
			</td></FORM>
				
			
        </c:forEach>
        
       
        
	</tr>
	
	
</table>



</body>
</html>
