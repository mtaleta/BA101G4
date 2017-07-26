<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>CoffeePuzzle Store</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
   <td>
   		<div style="font-size: 20px;font-weight: 900;">CoffeePuzzle___Store_backend</div>
    	<a href="${pageContext.request.contextPath}/backend/admin/select_page.jsp"><img src="${pageContext.request.contextPath}/img/store_img//logo.png" width="100" height="100" title="CoffeePuzzle"></a>
  </td>
  </tr>
</table>

<ul>
	<li><a href='${pageContext.request.contextPath}/backend/store/listAllStore.jsp'>List</a> all Stores. </li> <br>
  
  <jsp:useBean id="dao" scope="page" class="com.store.model.StoreDAO" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/store/store.do" >
       <b>選擇店家姓名:</b>
       <select size="1" name="store_id">
         <c:forEach var="storeVO" items="${dao.all}" > 
          <option value="${storeVO.store_id}">${storeVO.store_name}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
</ul>

</body>

</html>
