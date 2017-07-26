<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />
<jsp:useBean id="tagVO" scope="page" class="com.tag.model.TagVO" />
<jsp:useBean id="storetagDAO" scope="page" class="com.store_tag.model.Store_tagDAO" />

<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />


<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
#list {
    padding-top: 10px;
    border: 1px #ccc solid;
    border-radius: 10px;
    /*background-color: #F0E68C;*/
}
	
.tag-list {
    padding-left: 0;
    list-style: none;
}

.tag-list li {
    float: none;
}

.header-btn .btn {
    width: 100%;
    margin-bottom: 10px;
}

.btn-custo {
  color: #fff;
  background-color: #aaa;
  /*border-color: #fff;*/
}

.btn-custo:hover {
  color: #aaa;
  background-color: #fff;
  border-color: #aaa;
}
.tag-list li{
	float:left;
	margin-right: 10px;
}
</style>
<head><title>IBM Store: Home</title></head>
<body bgcolor='white'>

<a href='<%=request.getContextPath()%>/backend/select_page2.jsp'>回後端</a><br>
<a href='<%=request.getContextPath()%>/select_page3.jsp'>回前端(店家會員)</a>

<p>This is the Home page for BA101G4 Store: Home</p>

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
  <li><a href='frontend/store/listAllStore.jsp'>List</a> all Stores. </li> <br>
  
  <li><a href='frontend/fav_store/listOfFavStore.jsp'> 我的最愛</a> </li> <br>
  
  
  <li>
    <FORM METHOD="post" ACTION="frontend/store/store.do" >
        <b>輸入店家編號 (如STORE00000001):</b>
        <input type="text" name="store_id">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
    <FORM METHOD="post" ACTION="frontend/store/store.do" >
        <b>輸入路名or店名(如  中央路or 咖啡):</b>
        <input type="text" name="store_add_or_name">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getStores_BY_Store_ADD_OR_NAME">
    </FORM>
  </li>
  
  <jsp:useBean id="tagSvc" scope="page" class="com.tag.model.TagService" />
  
 <div id="list" class="container-fluid">
 	<div class="row header-btn">
  		<div class="col-xs-12 col-sm-6">
			<ul class="tag-list">
			<c:forEach var="tagVO" items="${tagSvc.all}" >
			
				<li><a class="btn btn-custo" href="<%=request.getContextPath()%>/store/store_tag.do?tag_id=${tagVO.tag_id}&action=getStoresBYTAG">${tagVO.tag_content}</a></li>
		
			</c:forEach>
			
			</ul>
		</div>
	</div>
</div>

  <jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
   

  
</ul>


<h3>新增店家</h3>

<ul>
  <li><a href='addEmp.jsp'>Add</a> a new Store.</li>
</ul>

<img src="<%=request.getContextPath()%>/images?action=advertisment&id=AD00000001" height="100">

<p></p>

<table border='1' cellpadding='5' cellspacing='0' width='800' align='center'>
	<tr bgcolor='#c4c099' align='center' valign='middle' height='20'>
		<td>
		<h3>最新消息- </h3>
		
		</td>
	</tr>
</table>
<jsp:useBean id="newsSvc" scope="page" class="com.news.model.NewsService" />
	<table border='1' bordercolor='#774f34' width='800' align='center'>
		<tr>
			<th>標題</th>
			<th>日期</th>
		</tr>
		
		
		<c:forEach var="newsVO" items="${newsSvc.allPassNews}">
			<tr align='center' valign='middle'>
				<td><a href="<%=request.getContextPath()%>/news/news.do?news_id=${newsVO.news_id}&action=getFrontNewsContent">${newsVO.news_title}</a></td>
				<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${newsVO.news_date}"/></td>
			</tr>
		</c:forEach>
		
		
	</table>
</body>

</html>
