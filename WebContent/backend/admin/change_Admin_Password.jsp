<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admin.model.*"%>
<%
AdminVO adminVO = (AdminVO)  request.getSession().getAttribute("ADMIN");
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
               		 修改管理員密碼
            	</h1>
            </section>
            <!-- Main content -->
            <section class="content">
                <div class="col-sm-12 col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <div class="table-header-tools">
                                <div class="col-sm-2 col-xs-2">
                                    <div class="box-header">
                                        <h3 class="box-title">管理員資料</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-header -->
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do">
					<table border="0">
						<tr>
							<td>後端管理員帳號：</td>
							<td><%= adminVO.getAdmin_acct()%></td>
						</tr>
						<tr>
							<td>請輸入 欲修改新密碼：</td>
							<td><input type="password" name="newPassword" size="20" VALUE="" /></td>
						</tr>
						<tr>
							<td>請再輸入一次 新密碼：</td>
							<td><input type="password" name="newPassword2" size="20" VALUE="" /></td>
						</tr>		
					</table>
						
						<input type="hidden" name="action" value="UPDATE_ADMIN_PASSWORD">
						<input type="submit" value="更新密碼">
				</FORM>

				<!-- end split buttons box -->
				</div>
			</div>
			<!-- Your Page Content Here -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>