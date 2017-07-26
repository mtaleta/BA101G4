<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ page import="com.tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>

<%
TagVO tagVO = (TagVO) request.getAttribute("TagVO");
//EmpServlet.java(Concroller), �s�Jreq��empVO����
%>


<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />
<jsp:useBean id="store_tagDAO" scope="page" class="com.store_tag.model.Store_tagDAO" />
<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<head>
<title>���a��� - listOneStore.jsp</title>
</head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#c4c099' align='center' valign='middle' height='20'>
		<td>
		<h3>���a��� - ListOneStore.jsp</h3>
		<a href="<%=request.getContextPath()%>/select_page.jsp">�^����</a>
		</td>
	</tr>
</table>

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

<table border='1' bordercolor='#774f34' width='1600'>
	<tr>
		<th>���a�s��</th>
		<th>���a�m�W</th>
		<th>�a�}</th>
		<th>�֦�����</th>
		
	</tr>
	<tr align='center' valign='middle'>
	
	<%-- 	<td><%=storeVO.getStore_id() %></td>
		<td><%=storeVO.getStore_name() %></td>
		<td><%=storeVO.getStore_add() %></td> --%>
		
	<c:if test="${not empty storeVO}">
		<td>${storeVO.store_id}</td>
		<td>${storeVO.store_name}</td>
		<td>${storeVO.store_add}</td>
		<td>
		<%if (request.getAttribute("store_tagVO")!=null){%>
       		<jsp:include page="/frontend/store_tag/listOfStore_tag.jsp" />
		<%} %>
		</td>
		<td>  
			<div class="event-section text-left center" style="text-align:center;">
					<a href="#" class="block negative-link text-center " role="button"
						data-toggle="modal" data-target="#login-modal1"> <i
						class="glyphicon glyphicon-thumbs-down"></i>�s�W����
					</a>
					
			</div>

	<!-- ���ҿO�c-->
	<div class="modal fade in" id="login-modal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<a class="modal-close" aria-hidden="true" data-dismiss="modal1">��</a>
					<h4 class="modal-title">�s�W����</h4>
				</div>
				<div class="modal-body">
					<p class="text-center">�м��Ҥ��e</p>
					
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/tag/tag.do"
						name="form1">
						<div class="radio-box">
							<label for="report1">���Ҥ��e</label>
							<input type="TEXT" name="tag_content" size="45" value="<%= (tagVO==null)? "" :tagVO.getTag_content()%>" />
							
						</div>
						
						
						<input type="submit" value="����" class="btn btn-cofe btn-block">
						<input type="hidden" name="action" value="insertStore_tag"> 
						<input type="hidden" name="store_id" value="${storeVO.store_id}" />
						
					</FORM>
				</div>
			</div>
		</div>
	</div>
	<!-- ���ҿO�c -->
			<%-- <a href="<%=request.getContextPath()%>/frontend/store_tag/addStore_tag.jsp?store_id=${storeVO.store_id}">�s�W����</a>		 --%>
		 </td>
		
		<td> 
			<c:if test="${fav_storeVOcheck.store_id eq storeVO.store_id}">
			 	 <a href="<%=request.getContextPath()%>/fav_store/fav_store.do?store_id=${storeVO.store_id}&action=delete_Favstore">
				<img src="<%=request.getContextPath()%>/img/heart_red.png" id="heart" title="��������">
			 </a> 
			 </c:if>
			 
			 <c:if test="${fav_storeVOcheck.store_id eq null}">
			 <a href="<%=request.getContextPath()%>/fav_store/fav_store.do?store_id=${storeVO.store_id}&action=insert_Favstore">
				<img src="<%=request.getContextPath()%>/img/heart_white.png" id="heart" title="�[�J����">
			 </a> 
			 </c:if>
		</td>
	</c:if>
		
        
	</tr>
	
	
</table>



<%if (request.getAttribute("storeVO")!=null){%>
       <jsp:include page="/frontend/rate_n_rev/listOfRate_n_rev.jsp" />
<%} %>

<%if (request.getAttribute("storeVO")!=null){%>
       <jsp:include page="/frontend/store/PhotoStore.jsp" />
<%} %>
</body>
</html>

<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>