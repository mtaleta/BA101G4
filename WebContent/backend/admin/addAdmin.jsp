<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admin.model.*"%>
<%
AdminVO adminVO = (AdminVO) request.getAttribute("adminVO");
%>
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
<script>
		function show() {
		// var inputs = document.getElementsByTagName('input');
		
		document.form1.admin_acct.value="admin06";
		document.form1.admin_name.value="admin06";
		document.form1.admin_email.value="ba101g4@gmail.com";

		}
	</script>
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		 <section class="content-header">
                <h1>
               		 新增後端管理員
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
                    <input type="button" onclick="show()" style="background-color:pink; width:5px;height:5px;">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/admin/admin.do" name="form1" enctype="multipart/form-data">
				<table border="0">

					<tr>
						<td>管理員帳號:</td>
						<td><input type="TEXT" name="admin_acct" size="45" 
							value="<%= (adminVO==null)? "" : adminVO.getAdmin_acct()%>" /></td>
					</tr>
					<tr>
						<td>姓名:</td>
						<td><input type="TEXT" name="admin_name" size="45"
							value="<%= (adminVO==null)? "" : adminVO.getAdmin_name()%>" /></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td><input type="email" name="admin_email" size="45"
							value="<%= (adminVO==null)? "" : adminVO.getAdmin_email()%>" /></td>
					
					<tr>
						<td>在職:</td>
						<td><input type="radio" name="admin_employed" value="0" ${adminVO.admin_employed==null?'checked':(adminVO.admin_employed==0?'checked':'')}>在職
							<input type="radio" name="admin_employed" value="1" ${adminVO.admin_employed==null?'':(adminVO.admin_employed==1?'checked':'')}>離職</td>
					</tr>

					<tr>
						<td>上傳照片:</td>
						<td>  	 
								<input type="file" name="upfile1">
							  <br/>
						</td>
					</tr>
					<tr>
						<td>給予權限:</td>
						<td>  	 
							  <input type="checkbox" name="feature" value="FEATURE00000001"> 維護後端管理員資料<br>
							  <input type="checkbox" name="feature" value="FEATURE00000002" > 維護一般會員資料<br>
							  <input type="checkbox" name="feature" value="FEATURE00000003" > 維護店家資料<br>
							  <input type="checkbox" name="feature" value="FEATURE00000004" > 審核店家上線<br>
							  <input type="checkbox" name="feature" value="FEATURE00000005" > 審核最新消息<br>
							  <input type="checkbox" name="feature" value="FEATURE00000006" > 審核廣告<br>
							  <input type="checkbox" name="feature" value="FEATURE00000011" > 審核檢舉留言<br>  
						</td>
					</tr>
				</table>
				<br>
				<input type="hidden" name="action" value="insert">
				<input type="submit" value="送出新增">
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