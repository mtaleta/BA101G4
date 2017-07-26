<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.rate_n_rev.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.LinkedHashSet"%>

<%-- <%
Set<Rate_n_revVO> rate_n_revSet = (Set<Rate_n_revVO>) request.getAttribute("rate_n_revVO");//EmpServlet.java(Concroller), 存入req的empVO物件
%> --%>
<%

Rate_n_revVO rate_n_revVO2 = (Rate_n_revVO) request.getAttribute("rate_n_revVO2");

%>

<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />
<jsp:useBean id="memberSvc" scope="page"
	class="com.member.model.MemberService" />
<html>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="${pageContext.request.contextPath}/css/star-rating.css"
	media="all" rel="stylesheet" type="text/css" />

<style type="text/css">
.glyphicon-minus-sign:before {
	display: none;
}
	.thumbnail {
    padding:0px;
}
.panel {
	position:relative;
}
.panel>.panel-heading:after,.panel>.panel-heading:before{
	position:absolute;
	top:11px;left:-16px;
	right:100%;
	width:0;
	height:0;
	display:block;
	content:" ";
	border-color:transparent;
	border-style:solid solid outset;
	pointer-events:none;
}
.panel>.panel-heading:after{
	border-width:7px;
	border-right-color:#f7f7f7;
	margin-top:1px;
	margin-left:2px;
}
.panel>.panel-heading:before{
	border-right-color:#ddd;
	border-width:8px;
}
</style>

<head>
<title>留言 - listOfRate_n_rev.jsp</title>
</head>
<body bgcolor='white'>




			<%-- <c:forEach var="rate_n_revVO" items="${rate_n_revVO}">
				<tr align='center' valign='middle'>

					<td>${memberSvc.getOneMember(rate_n_revVO.mem_id).mem_name}</td>
					<td>${rate_n_revVO.rnr_rate}</td>
					<td>${rate_n_revVO.rnr_rev}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${rate_n_revVO.rnr_date}" /></td>
			</c:forEach> --%>


		
<c:forEach var="rate_n_revVO" items="${rate_n_revVO}">
		<div class="container">
			
			<div class="row">
				<div class="col-sm-1">
					<div class="thumbnail">
						<c:if test="${empty memberSvc.getOneMember(rate_n_revVO.mem_id).mem_img}">
						<img class="img-responsive user-photo"
							src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png">
						</c:if>
						<c:if test="${not empty memberSvc.getOneMember(rate_n_revVO.mem_id).mem_img}">
						<img class="img-responsive user-photo"
							src="<%=request.getContextPath()%>/images?action=member&id=${rate_n_revVO.mem_id}">
						</c:if>
					</div>
					<!-- /thumbnail -->
				</div>
				<!-- /col-sm-1 -->

				<div class="col-sm-5">
					<div class="panel panel-default">
						<div class="panel-heading">
							<strong>${memberSvc.getOneMember(rate_n_revVO.mem_id).mem_name}</strong> <span class="text-muted">commented
								at <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${rate_n_revVO.rnr_date}" /></span>
							<span class="text-muted" style="margin-left:20px;"> with </span>
							<span style="margin-left:20px;font-size:15px;"><b> ${rate_n_revVO.rnr_rate} </b></span>
							<span> <img src="<%=request.getContextPath()%>/img/star.jpg" style="width:30px;"></span>
						</div>
						<div class="panel-body">${rate_n_revVO.rnr_rev}</div>
						<!-- /panel-body -->
					</div>
					<!-- /panel panel-default -->
				</div>
				<!-- /col-sm-5 -->

				
			</div>
			<!-- /row -->

		</div>
		<!-- /container -->
</c:forEach>
	
	<p></p>

	<%-- <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/rate_n_rev/rate_n_rev.do">
			    <input type="hidden" name="store_id" value="${rate_n_revVO.store_id}">
			    <input type="hidden" name="action"	value="getOne_For_Rate_n_rev">
			    <input type="submit" value="我要留言"> 
			 	
			    
</FORM> --%>

	<%--  <div >
	<a href="<%=request.getContextPath()%>/frontend/rate_n_rev/addRate_n_rev.jsp?store_id=${storeVO.store_id}">我要留言</a>
</div>  --%>



	<div class="event-section text-left center" style="text-align: left;">
		<a href="#" class="block negative-link text-center " role="button"
			data-toggle="modal" data-target="#login-modal"> <i
			class="glyphicon glyphicon-comment"></i>我要留言
		</a>

	</div>

	<!-- 留言燈箱-->
	<div class="modal fade in" id="login-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<a class="modal-close" aria-hidden="true" data-dismiss="modal">×</a>
					<h4 class="modal-title">店家評價</h4>
				</div>
				<div class="modal-body">
					<p class="text-center">請填寫感想及評分</p>

					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/rate_n_rev/rate_n_rev.do"
						name="form1">


						<div class="radio-box">
							<label for="report1">評分</label> <input id="input-21d" name="rate"
								value="<%=(rate_n_revVO2 == null) ? 5 : rate_n_revVO2.getRnr_rate()%>"
								type="text" class="rating" data-min=0 data-max=5 data-step=1
								data-size="sm" title="">

						</div>
						<div class="radio-box">
							<label for="report2">感想</label> <input type="TEXT" name="rev"
								size="45"
								value="<%=(rate_n_revVO2 == null) ? "good" : rate_n_revVO2.getRnr_rev()%>" />

						</div>




						<input type="submit" value="提交留言" class="btn btn-cofe btn-block">
						<input type="hidden" name="action" value="insertRate_n_rev">
						<input type="hidden" name="store_id" value="${storeVO.store_id}" />

					</FORM>
				</div>
			</div>
		</div>
	</div>
	<!-- 留言燈箱 -->
</body>
</html>
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/star-rating.js"
	type="text/javascript"></script>

