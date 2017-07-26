<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.feature.model.*"%>
<%
FeatureVO featureVO = (FeatureVO) request.getAttribute("featureVO");
%>
<html>
<head>
<title>後端功能表修改 - update_feature_input.jsp</title></head>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>後端功能表修改 - update_feature_input.jsp</h3>
		<a href="select_page.jsp"><img src="${pageContext.request.contextPath}/img/feature_img/back1.gif" width="100" height="32" border="0">回首頁</a></td>
	</tr>
</table>

<h3>資料修改:</h3>
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
		<td>後端功能編號:<font color=red><b>*</b></font></td>
		<td><%=featureVO.getFeature_id()%></td>
	</tr>
	<tr>
		<td>後端功能名稱:</td>
		<td><input type="TEXT" name="feature_name" size="45" value="<%=featureVO.getFeature_name()%>" /></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="feature_id" value="<%=featureVO.getFeature_id()%>">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
