<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
    StoreService storeSvc = new StoreService();
    List<StoreVO> list = storeSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>

<head>
<title>�Ҧ����a��� - listAllEmp.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ����a��� - ListAllStore.jsp</h3>
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

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>���a�s��</th>
		<th>���a�m�W</th>
		<th>�a�}</th>
		
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="storeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle'>
			<td>${storeVO.store_id}</td>
			<td>${storeVO.store_name}</td>
			<td>${storeVO.store_add}</td>
			
			
			<%-- <td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/emp/emp.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="empno" value="${empVO.empno}">
			    <input type="hidden" name="action"value="delete"></FORM>
			</td> --%>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
