<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.admin.model.*"%>
<%
	AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");//AdminServlet.java (Concroller), 存入req的adminVO物件 (包括幫忙取出的adminVO, 也包括輸入資料錯誤時的adminVO物件)
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
               		 維護一般會員資料
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
                                        <h3 class="box-title">一般會員資料</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body ">
                        <table class="table-ext table table-hover">
                            <tbody>
							<tr>	
								<th>管理員編號</th>
								<th>帳號</th>
								<th>密碼</th>
								<th>姓名</th>
								<th>Email</th>
								<th>在職</th>
								<th>大頭貼</th>
								<th>修改</th>
							</tr>
							<tr align='center' valign='middle'>
								<td><%=adminVO.getAdmin_id()%></td>
								<td><%=adminVO.getAdmin_acct()%></td>
								<td><%=adminVO.getAdmin_pwd()%></td>
								<td><%=adminVO.getAdmin_name()%></td>
								<td><%=adminVO.getAdmin_email()%></td>
								<td><input type="radio" name="admin_employed" value="0" ${adminVO.admin_employed==null?'checked':(adminVO.admin_employed==0?'checked':'')} disabled>在職
									<input type="radio" name="admin_employed" value="1" ${adminVO.admin_employed==null?'':(adminVO.admin_employed==1?'checked':'')} disabled>離職
								</td>

								<td><img src="<%=request.getContextPath()%>/images?action=admin&id=${adminVO.admin_id}" width="230px" height="230px"/></td>
								<td>
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do">
										 <input type="submit" value="修改">
										 <input type="hidden" name="admin_id" value="${adminVO.admin_id}">
										 <input type="hidden" name="action"	value="getOne_For_Update">
									</FORM>
								</td>
							</tr>
						</tbody>
                        </table>
                    </div>

                    <!-- end split buttons box -->
				</div>
			</div>
			<!-- Your Page Content Here -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>