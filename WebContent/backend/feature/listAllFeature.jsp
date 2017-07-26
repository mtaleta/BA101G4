<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.feature.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	FeatureDAO dao = new FeatureDAO();
    List<FeatureVO> list = dao.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>�Ҧ���ݥ\���� - listAllFeature.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�Ҧ���ݥ\���� - listAllFeaure.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/feature/select_page.jsp"><img src="feature_img/back1.gif" width="100" height="32" border="0">�^����</a>
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
		<th>��ݥ\��s��</th>
		<th>�\��W��</th>
		<th>�ק�</th>
		<th>�R��</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="featureVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle'}>
			<td>${featureVO.feature_id}</td>
			<td>${featureVO.feature_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="feature_id" value="${featureVO.feature_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="feature_id" value="${featureVO.feature_id}">
			    <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</body>
</html>
