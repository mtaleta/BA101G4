<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.feature.model.*"%>
<%
FeatureVO featureVO = (FeatureVO) request.getAttribute("featureVO");
%>
<html>
<head>
<title>��ݥ\���� - listOneFeature.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='600'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>��ݥ\���� - listOneFeature.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/feature/select_page.jsp"><img src="${pageContext.request.contextPath}/img/feature_img/back1.gif" width="100" height="32" border="0">�^����</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='600'>
	<tr>
		
		<th>��ݥ\��s��</th>
		<th>�\��W��</th>
	
	</tr>
	<tr align='center' valign='middle'>
		<td><%=featureVO.getFeature_id()%></td>
		<td><%=featureVO.getFeature_name()%></td>
	</tr>
</table>

</body>
</html>
