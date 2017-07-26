<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rept_activ.model.*"%>
<%
	Rept_activService rept_activSvc = new Rept_activService();
	List<Rept_activVO> list = rept_activSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="activitySvc" scope="page"
	class="com.activity.model.ActivityService" />
<jsp:useBean id="memberSvc" scope="page"
	class="com.member.model.MemberService" />
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		<section class="content">
			<div class="col-sm-12 col-xs-12">
				<div class="box box-primary">
					<div class="box-header">
						<div class="table-header-tools">
							<div class="col-sm-2 col-xs-2">
								<div class="box-header">
									<h3 class="box-title">活動檢舉管理</h3>
								</div>
							</div>
							<div class="col-sm-10 col-xs-10">
								<!-- Single button -->
								<div class="input-group tools tools-normal">
									<input type="text" name="table_search"
										class="form-control pull-right" placeholder="Search">
									<div class="input-group-btn">
										<button type="submit" class="btn btn-default">
											<i class="fa fa-search"></i>
										</button>
									</div>
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
								<th style="text-align: center;">活動編號-名稱</th>
								<th style="text-align: center;">類型</th>
								<th style="text-align: center;">會員編號-名稱</th>
								<th style="text-align: center;">檢舉原因</th>
								<th style="text-align: center;">審核狀態</th>
								<th style="text-align: center;">檢舉頁面</th>
								<th style="text-align: center;">操作</th>
							</tr>
							<tr>
								<!-- <td class="ie8">啟用前方會有勾勾
                                        <label for="selector-checkbox_1"></label>
                                        <input type="checkbox" id="selector-checkbox_1" class="ck_active" value="">
                                    </td> -->
								<%-- 								<%@ include file="../pages/page1.file"%> --%>
								<c:forEach var="rept_activVO" items="${list}">
									<c:if test="${rept_activVO.repo_rev eq '0'}">
										<tr align='center' valign='middle'
											${(rept_activVO.activ_id==param.activ_id) ? 'bgcolor=#CCCCFF':''}>
											<!--將修改的那一筆加入對比色而已-->
											<td><c:forEach var="activityVO"
													items="${activitySvc.all}">
													<c:if test="${rept_activVO.activ_id==activityVO.activ_id}">
	                  			  ${activityVO.activ_id}【${activityVO.activ_name}】 </c:if>
												</c:forEach></td>
											<td><span class="label label-warning">活動檢舉</span></td>

											<td><c:forEach var="memberVO" items="${memberSvc.all}">
													<c:if test="${rept_activVO.mem_id==memberVO.mem_id}">${memberVO.mem_id}【${memberVO.mem_name}】 </c:if>
												</c:forEach></td>
											<td>${rept_activVO.repo_rsn}</td>
											<td><span class="label label-warning">待審核</span></td>
											<td><a
												href="<%=request.getContextPath()%>/frontend/activity/activity.do?action=getOne_For_Activity&activ_id=${rept_activVO.activ_id}">詳細資訊</a></td>
											<td>
												<div class="btn-group">
													<a href="#" class="btn btn-flat btn-default" role="button"
														data-toggle="modal" data-target="#rept-modal"> <i
														class="fa fa-edit" title="檢舉處理" ata-toggle="tooltip"
														data-placement="top"></i>
													</a>
												</div>
												<c:forEach var="activityVO"
													items="${activitySvc.all}">
													<c:if test="${rept_activVO.activ_id==activityVO.activ_id}">
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do"
													name="form1" enctype="multipart/form-data">
													<input type="submit" value="刪除活動"> 
													<input type="hidden" name="activ_id" value="${activityVO.activ_id}"> 
													<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"> 
													<input type="hidden" name="action" value="update_cfm"> 
													<input type="hidden" name="mem_id" value="${activityVO.mem_id}">
													<input type="hidden" name="store_id" value="${activityVO.store_id}"> 
													<input type="hidden" name="activ_store_cfm" value="3">
													<input type="hidden" name="repo_rsn" value="${rept_activVO.repo_rsn}"> 
													<input type="hidden" name="repo_rev" value="3"> 
												</FORM>
														</c:if>
														</c:forEach>
											</td>
										</tr>



										<!-- 檢舉燈箱-->
										<div class="modal fade in" id="rept-modal" tabindex="-1"
											role="dialog" aria-labelledby="myModalLabel"
											aria-hidden="true" style="display: none;">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header" align="center">
														<a class="modal-close" aria-hidden="true"
															data-dismiss="modal">×</a>
														<h4 class="modal-title">檢舉此活動</h4>
													</div>
													<div class="modal-body">
														<p class="text-center">請協助提供您檢舉此活動的原因</p>
														<h4 class="text-center event-report-subtitle">檢舉原因</h4>
														<FORM METHOD="post"
															ACTION="<%=request.getContextPath()%>/frontend/rept_activ/rept_activ.do"
															name="form1">
															<div class="radio-box">
																<input type="radio" name="repo_rev" value="1"
																	id="report1"> <label for="report1">不處理</label>
															</div>
															<div class="radio-box">
																<input type="radio" name="repo_rev" value="2"
																	id="report2"> <label for="report2">刪除留言</label>
															</div>
															<input type="submit" value="審核完成"
																class="btn btn-cofe btn-block"> 
																<input type="hidden" name="action" value="update"> 
																<input type="hidden" name="activ_id" value="${rept_activVO.activ_id}"> 
																<input type="hidden" name="mem_id" value="${rept_activVO.mem_id}"> 
																<input 	type="hidden" name="repo_rsn" value="${rept_activVO.repo_rsn}"> 
																<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
														</FORM>
													</div>
												</div>
											</div>
										</div>
										</div>
										<!-- 檢舉燈箱 -->


									</c:if>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- end split buttons box -->
			</div>
			<!-- /.box-body -->
			<div class="box-footer">
				<div class="row">
					<div
						class="col-md-6 col-md-offset-4 col-sm-6 col-sm-offset-4 col-xs-6 col-xs-offset-4"
						style="text-align: center;">
					<%--<%@ include file="../pages/page2.file"%> --%>
					</div>
				</div>
			</div>
			<!--/.box-footer--> 
		</section>
		<!-- /.content -->
	</div>
</body>
</html>