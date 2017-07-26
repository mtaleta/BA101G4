<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.advertisment.model.*"%>

<% 
AdvertismentVO advertismentVO = (AdvertismentVO) request.getAttribute("advertismentVO");
 %>
 <html>
<head>
<jsp:include page="/backend/resource.jsp" />
<script src="js/jquery.min.1.11.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

function file_change() {
	//file = document.getElementById('upfile1').files[0];
	file = document.getElementsByName("ad_img");

	//if (file) {
	if (file[0]) {
		fileReader = new FileReader();
		fileReader.onload = openfile;
		//fileReader.readAsDataURL(file);
		fileReader.readAsDataURL(file[0].files[0]);
	}
}
function openfile(event) {
	document.getElementById('image').src = event.target.result;
	result;
}
	
</script>
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		 <section class="content-header">
                <h1>
               		  �s�i�s�W
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
                                        <h3 class="box-title">�s�i�s�W</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body ">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/advertisment/advertisment.do" name="form1" enctype="multipart/form-data">
                        <table class="table-ext table table-hover">
                            <tbody>
							<tr>
								<td></td>
								<td><img id="image" ></td>
							</tr>
							
							<tr>
								<td>�W�ǹϤ�</td>
								<td><input type="file" name="ad_img" onchange="file_change()"></td>
							</tr>
							
							<tr>
								<td>���D:</td>
								<td><input type="TEXT" name="ad_title" size="45" 
									value="<%=(advertismentVO==null)? "" : advertismentVO.getAd_title()%>" /></td>
							</tr>
							
							<tr>
								<td>�s�i����:</td>
								<td><textarea class="ckeditor" cols="80" id="content" name="ad_content" rows="12"
												value="<%=(advertismentVO==null)? "" : advertismentVO.getAd_content()%>"></textarea></td>
							</tr>
								
							<%-- <tr>
								<%java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());%>
								
							</tr> --%>
							
							<!-- <tr><textarea class="ckeditor" cols="80" id="content" name=content rows="12"></textarea></tr> -->
                             </tbody>
                        </table>
						<input type="hidden" name="action" value="insertAdvertisment">
						<input type="submit" value="�e�X�s�W">
						</FORM>
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