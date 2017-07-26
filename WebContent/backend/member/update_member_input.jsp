<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
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
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do" name="form1" enctype="multipart/form-data">
					<table border="0">
						<tr>
							<td>會員編號:<font color=red><b>*</b></font></td>
							<td><%=memberVO.getMem_id()%></td>
						</tr>
						<tr>
							<td>帳號:</td>
							<td><input type="TEXT" name="mem_acct" size="45" value="<%=memberVO.getMem_acct()%>" /></td>
						</tr>
						<tr>
							<input type="hidden" name="mem_pwd" size="45" value="<%=memberVO.getMem_pwd()%>" />
						</tr>
						<tr>
							<td>姓名:</td>
							<td><input type="TEXT" name="mem_name" size="45" value="<%=memberVO.getMem_name()%>" /></td>
						</tr>
						<tr>
							<td>電話:</td>
							<td><input type="TEXT" name="mem_tel" size="45" value="<%=memberVO.getMem_tel()%>" /></td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><input type="TEXT" name="mem_email" size="45" value="<%=memberVO.getMem_email()%>" /></td>
						</tr>
						<tr>
							<td>地址:</td>
							<td><input type="TEXT" name="mem_add" size="45" value="<%=memberVO.getMem_add()%>" /></td>
						</tr>
							<tr>
							<td>點數:</td>
							<td><input type="TEXT" name="mem_points" size="45" value="<%=memberVO.getMem_points()%>" /></td>
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
					<input type="hidden" name="action" value="update">
					<input type="hidden" name="mem_id" value="<%=memberVO.getMem_id()%>">
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