<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.admin.model.*"%>
<%
    AdminDAO dao = new AdminDAO();
    List<AdminVO> list = dao.getAll();
    pageContext.setAttribute("list",list);
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
									<th>刪除</th>
                                </tr>
                                <%@ include file="/pages/page1.file" %>
                                <c:forEach var="adminVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	                                <tr>
	                                   <!-- <td class="ie8">啟用前方會有勾勾
	                                        <label for="selector-checkbox_1"></label>
	                                        <input type="checkbox" id="selector-checkbox_1" class="ck_active" value="">
	                                    </td> -->
	                                    <td>${adminVO.admin_id}</td>
										<td>${adminVO.admin_acct}</td>
										<td>${adminVO.admin_pwd}</td>
										<td>${adminVO.admin_name}</td>
										<td>${adminVO.admin_email}</td>
										<td>${adminVO.admin_employed}</td>
										<td><img src="<%=request.getContextPath()%>/images?action=admin&id=${adminVO.admin_id}" /></td>
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do">
											 <input type="submit" value="修改">
											 <input type="hidden" name="admin_id" value="${adminVO.admin_id}">
											 <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
										</td>
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do">
											<input type="submit" value="刪除">
											<input type="hidden" name="admin_id" value="${adminVO.admin_id}">
											<input type="hidden" name="action" value="delete"></FORM>
										</td>
	                                </tr>
                            	</c:forEach>
                             </tbody>
                        </table>
                    </div>
                    <%@ include file="/pages/page2.file" %>
                    <!-- end split buttons box -->
				</div>
			</div>
			<!-- Your Page Content Here -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>