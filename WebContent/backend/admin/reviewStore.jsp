<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>

<%
	StoreDAO dao = new StoreDAO();
	List<StoreVO> list = dao.getAllREVIEW();
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
			<h1>會員管理</h1>
		</section>
		<!-- Main content -->
		<section class="content">
			<div class="col-sm-12 col-xs-12">
				<div class="box box-primary">
					<div class="box-header">
						<div class="table-header-tools">
							<div class="col-sm-2 col-xs-2">
								<div class="box-header">
									<h3 class="box-title">店家會員審核</h3>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body ">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/backend/store/store.do"
						name="form1" enctype="multipart/form-data">
						<table class="table-ext table table-hover">
							<tbody>
								<tr>
									<!-- <th class="ie8">啟用前方會有勾勾
                                        <label for="selectAllorCaner"></label>
                                        <input type="checkbox" id="selectAllorCaner">
                                    </th> -->
									<th>審核狀態</th>
									<th>店家編號</th>
									<th>帳號</th>
									<th>店名</th>
									<th>電話</th>
									<th>地址</th>
									<th>營業登記證</th>
									<th>審核店家狀態</th>
								</tr>
								<tr>
									<!-- <td class="ie8">啟用前方會有勾勾
                                        <label for="selector-checkbox_1"></label>
                                        <input type="checkbox" id="selector-checkbox_1" class="ck_active" value="">
                                    </td> -->
									<c:forEach var="storeVO" items="${list}">
										<c:if test="${storeVO.store_pass==null}">
											<tr align='center' valign='middle'}>
												<td><input type="checkbox" name="store_id"
													value="${storeVO.store_id}"></td>
												<td>${storeVO.store_id}</td>
												<td>${storeVO.store_acct}</td>
												<td>${storeVO.store_name}</td>
												<td>${storeVO.store_tel}</td>
												<td>${storeVO.store_add}</td>
												<td>${storeVO.store_cpse}</td>
												<td>${storeVO.store_pass== null?'未審核':storeVO.store_pass == 1?'審核通過':'審核沒通過'}</td>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</tr>
						</table>
						<input type="hidden" name="action" value="updatePass"> <input
							type="submit" name="pass_store" value="審核通過店家">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="submit" name="pass_store" value="審核不通過店家">
					</FORM>
				</div>
				<!-- end split buttons box -->
			</div>
		</section>
	</div>
</body>
</html>