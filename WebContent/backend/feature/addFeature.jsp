<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.feature.model.*"%>
<%
FeatureVO featureVO = (FeatureVO) request.getAttribute("featureVO");
%>

<html>
<head>
<title>後端功能表資料新增 - addFeature.jsp</title></head>


<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>後端功能名稱新增 - addFeature.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/backend/feature/select_page.jsp"><img src="${pageContext.request.contextPath}/img/feature_img/tomcat.gif" width="100" height="100" border="1">回首頁</a>
	    </td>
	</tr>
</table>

<h3>資料後端功能表:</h3>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do" name="form1" enctype="multipart/form-data">
<table border="0">

	<tr>
		<td>後端功能名稱:</td>
		<td><input type="TEXT" name="feature_name" size="45" 
			value="<%= (featureVO==null)? "feature001" : featureVO.getFeature_name()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>
