<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.rate_n_rev.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>

<%-- <%
Set<Rate_n_revVO> rate_n_revSet = (Set<Rate_n_revVO>) request.getAttribute("rate_n_revVO");//EmpServlet.java(Concroller), 存入req的empVO物件
%> --%>


<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />

<html>
<style type="text/css">
#list {
    padding-top: 10px;
    border: 0px;
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
<head>
<title>留言 - listOfRate_n_rev.jsp</title>
</head>
<body bgcolor='white'>
<jsp:useBean id="tagSvc" scope="page" class="com.tag.model.TagService" />
 <div id="list" class="container-fluid">
 	<div class="row header-btn">
  		<div class="col-xs-12 col-sm-6">
			<ul class="tag-list">
			<c:forEach var="store_tagVO" items="${store_tagVO}">
			
				<li><a class="btn btn-custo" href="<%=request.getContextPath()%>/store/store_tag.do?tag_id=${store_tagVO.tag_id}&action=getStoresBYTAG">${tagSvc.getOneTag(store_tagVO.tag_id).tag_content}</a></li>
		
			</c:forEach>		
			
			</ul>
		</div>
	</div>
</div>



</body>
</html>
