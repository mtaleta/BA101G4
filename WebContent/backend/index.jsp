<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.auth.model.AuthVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.admin.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	AdminVO adminVO = (AdminVO) request.getSession().getAttribute("ADMIN");
	if (adminVO != null && adminVO.getAuths() != null) {
		boolean isMemberManger = false;
		boolean isReportManger = false;
		boolean isAuthManager = true;
		Set<String> auths = new HashSet<String>();
		for (AuthVO auth : adminVO.getAuths()) {
			auths.add(auth.getFeature_id());
			if ("FEATURE00000002".equals(auth.getFeature_id()) || "FEATURE00000003".equals(auth.getFeature_id())
					|| "FEATURE00000004".equals(auth.getFeature_id())) {
				isMemberManger = true;
			} else if ("FEATURE00000008".equals(auth.getFeature_id())) {
				isReportManger = true;
			}
		}
		pageContext.setAttribute("isMemberManger", isMemberManger);
		pageContext.setAttribute("isReportManger", isReportManger);
		pageContext.setAttribute("isAuthManager", isAuthManager);
		pageContext.setAttribute("auths", auths.toString());
	}
%>
<c:set var="isAdmin" value="false" />
<c:set var="isVisitor" value="false" />
<c:set var="displayName" value="" />
<c:choose>
	<c:when test="${sessionScope.ADMIN!=null}">
		<c:set var="isAdmin" value="true" />
		<c:set var="displayName"
			value="${sessionScope.ADMIN.admin_name}&nbsp;&nbsp;您好" />
	</c:when>
	<c:otherwise>
		<c:set var="isVisitor" value="true" />
		<c:set var="displayName" value="CoffeePuzzle 您好" />
	</c:otherwise>
</c:choose>
<html>
<head>
<title>後端首頁</title>
<jsp:include page="/backend/resource.jsp" />
<style type="text/css">
#right {
	height: 70%;
	width: 100%;
}

body {
	overflow: hidden;
}
</style>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<!-- Main Header -->
		<header class="main-header">
			<!-- Logo -->
			<a href="${pageContext.request.contextPath}/backend/index.jsp"
				class="logo"> <!-- mini logo for sidebar mini 50x50 pixels --> <span
				class="logo-mini"><img
					src="${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png"
					alt="CoffeePuzzle"></span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg">CoffeePuzzle</span>
			</a>
			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- User Account Menu -->
						<li class="dropdown user user-menu">
							<!-- Menu Toggle Button --> <a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <!-- The user image in the navbar-->
								<c:if test="${isAdmin}">
									<img
										src="<%=request.getContextPath()%>/images?action=admin&id=${sessionScope.ADMIN.admin_id}"
										class="user-image" alt="User Image">
									<!-- hidden-xs hides the username on small devices so only the image appears. -->
								</c:if> <span class="hidden-xs">${displayName}</span>
						</a> <c:if test="${isAdmin}">
								<ul class="dropdown-menu">
									<!-- The user image in the menu -->
									<li class="user-header"><img
										src="<%=request.getContextPath()%>/images?action=admin&id=${sessionScope.ADMIN.admin_id}"
										class="img-circle" alt="User Image">
										<p>CoffeezPuzzle~後端管理中心~</p></li>
									<!-- Menu Footer-->
									<li class="user-footer">
										<div class="pull-left">
											<a
												href="${pageContext.request.contextPath}/backend/admin/listOneAdmin_personal.jsp"
												class="btn btn-default btn-flat" target="right">個人訊息</a>
										</div>
										<div class="pull-right">
											<a
												href="${pageContext.request.contextPath}/backend/admin/logout_Admin.jsp"
												class="btn btn-default btn-flat">退出</a>
										</div>
									</li>
								</ul>
							</c:if>
						</li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar Menu -->
				<ul class="sidebar-menu">
					<c:if test="${fn:contains(auths, 'FEATURE00000005')}">
						<li class="treeview"><a href="#"> <i class="fa fa-group"></i>
								<span>最新消息</span> <i class="fa fa-angle-left pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<li><a href="<%=request.getContextPath()%>/news/news.do?action=getAllNews" target="right"><i
										class="fa fa-circle-o"></i>審核最新消息</a></li>
							</ul></li>
					</c:if>
					<c:if test="${fn:contains(auths, 'FEATURE00000006')}">
						<li class="treeview"><a href="#"> <i
								class="fa fa-comments-o"></i> <span>廣告管理</span> <i
								class="fa fa-angle-left pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<li><a href="<%=request.getContextPath()%>/backend/advertisment/advertisment.do?action=getAllAdvertisment" target="right"><i
										class="fa fa-circle-o"></i>審核廣告</a></li>
							</ul>
							<ul class="treeview-menu">
								<li><a href="${pageContext.request.contextPath}/backend/advertisment/addAdvertisment.jsp" target="right"><i
										class="fa fa-circle-o"></i>新增廣告</a></li>
							</ul>
						</li>
					</c:if>
					<c:if test="${isMemberManger}">
						<li class="treeview"><a href="#"> <i class="fa fa-cubes"></i>
								<span>會員管理</span> <i class="fa fa-angle-left pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<c:if test="${fn:contains(auths, 'FEATURE00000002')}">
									<li><a
										href="${pageContext.request.contextPath}/backend/admin/reviewStore.jsp"
										target="right"><i class="fa fa-circle-o"></i>審核店家上線</a></li>
								</c:if>
								<c:if test="${fn:contains(auths, 'FEATURE00000003')}">
									<li><a
										href="${pageContext.request.contextPath}/backend/member/listAllMember.jsp"
										target="right"><i class="fa fa-circle-o"></i>維護一般會員資料</a></li>
								</c:if>
								<c:if test="${fn:contains(auths, 'FEATURE00000004')}">
									<li><a
										href="${pageContext.request.contextPath}/backend/store/listAllStore.jsp"
										target="right"><i class="fa fa-circle-o"></i>維護店家資料</a></li>
								</c:if>
							</ul></li>
					</c:if>
					<c:if test="${isAuthManager }">
						<li class="treeview"><a href="#"> <i class="fa fa-laptop"></i>
								<span>員工資料及權限管理</span> <i class="fa fa-angle-left pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<c:if test="${fn:contains(auths, 'FEATURE00000001')}">
									<li><a
										href="${pageContext.request.contextPath}/backend/admin/listAllAdmin.jsp"
										target="right"><i class="fa fa-circle-o"></i>員工資料維護</a></li>
									<li><a
										href="${pageContext.request.contextPath}/backend/admin/addAdmin.jsp"
										target="right"><i class="fa fa-circle-o"></i>新增後端管理員</a></li>
								</c:if>
								<li><a
									href="${pageContext.request.contextPath}/backend/admin/change_Admin_Password.jsp"
									target="right"><i class="fa fa-circle-o"></i>修改密碼</a></li>
							</ul></li>
					</c:if>
					<c:if test="${isReportManger}">
						<li class="treeview"><a href="#"> <i
								class="fa fa-lightbulb-o"></i> <span>檢舉管理</span> <i
								class="fa fa-angle-left pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<c:if test="${fn:contains(auths, 'FEATURE00000008')}">
									<li><a href="${pageContext.request.contextPath}/backend/rept_activ/rept_activ.jsp" target="right"><i
											class="fa fa-circle-o"></i>審核檢舉活動</a></li>
								</c:if>
							</ul></li>
					</c:if>
				</ul>
				<!-- /.sidebar-menu -->
			</section>
			<!-- /.sidebar -->
		</aside>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<iframe id="right" name="right"
				src="${pageContext.request.contextPath}/backend/admin/select_page.jsp"
				frameborder="0"></iframe>
		</div>
		<!-- /.content-wrapper -->
		<!-- Main Footer -->
		<!-- /.control-sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->
</body>
</html>
