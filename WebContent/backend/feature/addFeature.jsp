<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.feature.model.*"%>
<%
FeatureVO featureVO = (FeatureVO) request.getAttribute("featureVO");
%>

<html>
<head>
<title>��ݥ\����Ʒs�W - addFeature.jsp</title></head>


<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>��ݥ\��W�ٷs�W - addFeature.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/backend/feature/select_page.jsp"><img src="${pageContext.request.contextPath}/img/feature_img/tomcat.gif" width="100" height="100" border="1">�^����</a>
	    </td>
	</tr>
</table>

<h3>��ƫ�ݥ\���:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do" name="form1" enctype="multipart/form-data">
<table border="0">

	<tr>
		<td>��ݥ\��W��:</td>
		<td><input type="TEXT" name="feature_name" size="45" 
			value="<%= (featureVO==null)? "feature001" : featureVO.getFeature_name()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>

</html>
