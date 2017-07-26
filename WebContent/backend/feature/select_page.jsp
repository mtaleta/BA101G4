<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>CoffeePuzzle Feature</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
   <td>
   		<div style="font-size: 20px;font-weight: 900;">CoffeePuzzle___Feature</div>
    	<a href="select_page.jsp"><img src="${pageContext.request.contextPath}/img/admin_img/logo.png" width="100" height="100" title="CoffeePuzzle"></a>
  </td>
  </tr>
</table>
<h3>資料查詢:</h3>
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
	<li><a href='${pageContext.request.contextPath}/backend/feature/listAllFeature.jsp'>List</a> all Features. </li> <br>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do" >
        <b>輸入後端功能表編號: (如FEATURE00000001):</b>
        <input type="text" name="feature_id">
        <input type="submit" value="送出"><font color=blue>(資料格式驗證  by Controller ).</font> 
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do" name="form1">
        <b>輸入後端功能表編號: (如FEATURE00000001):</b>
        <input type="text" name="feature_id">
        <input type="button" value="送出" onclick="fun1()"><font color=blue>(資料格式驗證  by Java Script).</font> 
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="dao" scope="page" class="com.feature.model.FeatureDAO" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do" >
       <b>選擇後端功能表編號:</b>
       <select size="1" name="feature_id">
         <c:forEach var="featureVO" items="${dao.all}" > 
          <option value="${featureVO.feature_id}">${featureVO.feature_id}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/feature/feature.do" >
       <b>選擇後端功能表名稱:</b>
       <select size="1" name="feature_id">
         <c:forEach var="featureVO" items="${dao.all}" > 
          <option value="${featureVO.feature_id}">${featureVO.feature_name}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
</ul>

<h3>後端功能表管理</h3>

<ul>
  <li><a href='${pageContext.request.contextPath}/backend/feature/addFeature.jsp'>Add</a> a new Feature.</li>
</ul>

<script>    
   function fun1(){
      with(document.form1){
         if (feature_id.value=="") 
             alert("請輸入後端功能表編號!");
        // else if (isNaN(admin_id.value)) 
           //  alert("員工編號格式不正確!");
      //   else if (admin_id.value < "admin00000000" || admin_id.value > 7999) 
         //    alert("請填寫介於7001和7999之間的數量!");
         else
             submit();
      }
   }
</script>

</body>

</html>
