<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%
	StoreDAO dao = new StoreDAO();
	List<StoreVO> list = dao.getAll();
	pageContext.setAttribute("list", list);
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
               		 ���@���a�|�����
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
                                        <h3 class="box-title">���a�|�����</h3>
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
									<th>���a�s��</th>
									<th>�b��</th>
									<th>���W</th>
									<th>�q��</th>
									<th>�a�}</th>
									<th>Email</th>
									<th>�I��</th>
									<th>��~�n�O��</th>
									<th>�j�Y�K</th>
									<th>�O�_�q�L�f��</th>
									<th>�ק�</th>
									<th>�R��</th>
								</tr>
								<%@ include file="/pages/page1.file"%>
								<c:forEach var="storeVO" items="${list}" begin="<%=pageIndex%>"
									end="<%=pageIndex+rowsPerPage-1%>">
									<tr align='center' valign='middle'}>
										<td>${storeVO.store_id}</td>
										<td>${storeVO.store_acct}</td>
										<td>${storeVO.store_name}</td>
										<td>${storeVO.store_tel}</td>
										<td>${storeVO.store_add}</td>
										<td>${storeVO.store_email}</td>
										<td>${storeVO.store_points}</td>
										<td>${storeVO.store_cpse}</td>
										<td><img
											src="<%=request.getContextPath()%>/images?action=store&id=${storeVO.store_id}" /></td>
										<td>${storeVO.store_pass== null?'���f��':storeVO.store_pass == 1?'�f�ֳq�L':'�f�֨S�q�L'}</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/backend/store/store.do">
												<input type="submit" value="�ק�"> <input type="hidden"
													name="store_id" value="${storeVO.store_id}"> <input
													type="hidden" name="action" value="getOne_For_Update">
											</FORM>
										</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/backend/store/store.do">
												<input type="submit" value="�R��"> <input type="hidden"
													name="store_id" value="${storeVO.store_id}"> <input
													type="hidden" name="action" value="delete">
											</FORM>
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