<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
				<ul class="nav navbar-nav navbar-right">
					<li><a>${displayName}</a></li>
					<c:if test="${isVisitor}">
						<li><a
							href="${pageContext.request.contextPath}/frontend/member/login_Member.jsp">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;會員登入&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
						<li><a
							href="${pageContext.request.contextPath}/frontend/store/login_Store.jsp">
								&nbsp;&nbsp;店家登入&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
					</c:if>
					<li><c:choose>
							<c:when test="${isVisitor}">
							</c:when>
							<c:when test="${isMember}">
								<a href="${pageContext.request.contextPath}/frontend/member/select_page.jsp">
									&nbsp;&nbsp;個人設定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
							<c:when test="${isStore}">
								<a href="${pageContext.request.contextPath}/frontend/store/select_page.jsp">
									&nbsp;&nbsp;個人設定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
						</c:choose></li>
					<li class="nav-login-out"><c:choose>
							<c:when test="${isVisitor}">
							</c:when>
							<c:when test="${isMember}">
								<a href="${pageContext.request.contextPath}/frontend/member/logout_Member.jsp">
									&nbsp;&nbsp;登出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
							<c:when test="${isStore}">
								<a href="${pageContext.request.contextPath}/frontend/store/logout_Store.jsp">
									&nbsp;&nbsp;登出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
						</c:choose></li>
				</ul>