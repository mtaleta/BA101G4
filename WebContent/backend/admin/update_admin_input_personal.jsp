<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.auth.model.AuthVO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admin.model.*"%>
<%
	AdminVO adminVO = (AdminVO) request.getSession().getAttribute("ADMIN"); //AdminServlet.java (Concroller), 存入req的adminVO物件 (包括幫忙取出的adminVO, 也包括輸入資料錯誤時的adminVO物件)
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
               		 維護後端管理員資料
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
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do" name="form1" enctype="multipart/form-data">
					<table border="0">
						<tr>
							<td>管理員編號:<font color=red><b>*</b></font></td>
							<td><%=adminVO.getAdmin_id()%></td>
						</tr>
						<tr>
							<td>管理員帳號:</td>
							<td><%=adminVO.getAdmin_acct()%></td>
						</tr>	
					
						<tr>
							<td>姓名:</td>
							<td><input type="TEXT" name="admin_name" size="45"	value="<%=adminVO.getAdmin_name()%>" /></td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><input type="TEXT" name="admin_email" size="45" value="<%=adminVO.getAdmin_email()%>" /></td>
						</tr>
						</tr>
						<tr>
							<td>上傳照片:</td>
					     	<td>  	 
							    <input type="file" name="upfile1">
							<br/>
							</td>
						</tr>
					</table>
					<br>
					<input type="hidden" name="action" value="update_admin_personal">
					<input type="hidden" name="admin_id" value="<%=adminVO.getAdmin_id()%>">
					<input type="submit" value="送出修改">
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