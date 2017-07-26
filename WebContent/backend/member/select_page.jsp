<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>CoffeePuzzle MEMBER</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
    <td>
    	<div style="font-size: 20px;font-weight: 900;">CoffeePuzzle___MEMBER BACKEND</div>
    	<a href="${pageContext.request.contextPath}/backend/admin/select_page.jsp"><img src="${pageContext.request.contextPath}/img/mem_img/logo.png" width="100" height="100" title="CoffeePuzzle"></a>
    </td>
  </tr>
</table>
<h3>會員資料查詢:</h3>
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

<ul>
	<li><a href='${pageContext.request.contextPath}/backend/member/listAllMember.jsp'>List</a> all Members. </li> <br>


  
  
  <jsp:useBean id="dao" scope="page" class="com.member.model.MemberDAO" />
   
  
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do" >
       <b>選擇會員姓名:</b>
       <select size="1" name="mem_id">
         <c:forEach var="memberVO" items="${dao.all}" > 
          <option value="${memberVO.mem_id}">${memberVO.mem_name}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
</ul>

</body>

</html>
