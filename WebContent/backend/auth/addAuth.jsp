<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admin.model.*"%>
<%
AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");
%>

<html>
<head>
<title>�޲z����Ʒs�W - addAdmin.jsp</title></head>


<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
			<div style="font-size: 20px;font-weight: 900;">�޲z����Ʒs�W - addAdmin.jsp</div>
			  <a href="select_page.jsp"><img src="${pageContext.request.contextPath}/img/mem_img/logo.png" width="100" height="100"><div>�^����</div></a>
		</td>
	</tr>
</table>

<h3>��ƭ��u:</h3>
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

<FORM METHOD="post" ACTION="admin.do" name="form1" enctype="multipart/form-data">
<table border="0">

<li>
     <FORM METHOD="post" ACTION="admin.do" >
       <b>��ܺ޲z���s��:</b>
       <select size="1" name="admin_id">
         <c:forEach var="adminVO" items="${dao.all}" > 
          <option value="${adminVO.admin_id}">${adminVO.admin_id}
         </c:forEach>   
       </select>

    </FORM>
  </li>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>

</html>
