<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page import="java.util.Map" %>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		 <section class="content-header">
                <h1>
               		  �s�i�C��
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
                                        <h3 class="box-title">�s�i�����޲z</h3>
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
                                    <th>�s�i�s��</th>
									<th>���D</th>
									<th>���</th>
									<th>���A</th>
									<th>���e</th>
									<th>�ާ@</th>
                                </tr>
                                <c:forEach var="advertismentList" items="${advertismentList}" >
								<tr align='center' valign='middle'>
									<td>${advertismentList.ad_id}</td>
									<td>${advertismentList.ad_title}</td>
									<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${advertismentList.ad_date}"/></td>
									<td>
										<c:if test="${advertismentList.ad_status eq 1}">
											�W�[��
										</c:if>
										<c:if test="${advertismentList.ad_status eq 0}">
											�U�[��
										</c:if>
									</td>
									
									<td>
									  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/advertisment/advertisment.do">
										<input type="submit" value="�d��"> 
										<input type="hidden" name="advertisment_id" value="${advertismentList.ad_id}">
										<input type="hidden" name="action" value="getBackAdvertismentContent">
									</td></FORM>
									
									<td>
										<c:if test="${advertismentList.ad_status eq 1}">
											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/advertisment/advertisment.do">
												<input type="submit" value="�U�["> 
												<input type="hidden" name="advertisment_id" value="${advertismentList.ad_id}">
												<input type="hidden" name="action" value="UpdateDOWN">
											</FORM>
										</c:if>
										<c:if test="${advertismentList.ad_status eq 0}">
											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/advertisment/advertisment.do">
												<input type="submit" value="�W�["> 
												<input type="hidden" name="advertisment_id" value="${advertismentList.ad_id}">
												<input type="hidden" name="action" value="UpdateUP">
											</FORM>
										</c:if>
									</td>
										
									
								</c:forEach>
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