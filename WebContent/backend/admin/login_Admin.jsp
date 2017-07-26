<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admin.model.*"%>
<%
AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");
%>
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				後臺管理
				<small>主頁</small>
			</h1>
		</section>
		<!-- Main content -->
		<section class="content">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h2>後端管理者登入</h2>
				</div>
				<div class="panel-body">
					<pre>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do">
					<table border="0">
						<tr>
							<td>帳號:</td>
							<td><input type="TEXT" name="admin_acct" size="20" VALUE="" /></td>
						</tr>
						<tr>
							<td>密碼:</td>
							<td><input type="password" name="admin_pwd" size="20"  VALUE=""/></td>
						</tr>
						
						</table>
						<input type="hidden" name="action" value="Login_Admin">
						<input type="submit" value="登入">
					</FORM>
					</pre>
				</div>
									
			</div>
			<!-- Your Page Content Here -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>