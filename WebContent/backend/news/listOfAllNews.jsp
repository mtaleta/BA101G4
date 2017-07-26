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
               		  最新消息列表
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
                                        <h3 class="box-title">最新消息管理</h3>
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
                                    <th>發布店家</th>
									<th>標題</th>
									<th>日期</th>
									<th>狀態</th>
									<th>內容</th>
									<th>審核</th>
                                </tr>
                                <c:forEach var="newsList" items="${newsList}" >
								<tr align='center' valign='middle'>
									<td>${storeSvc.getOneStore(newsList.store_id).store_name}</td>
									<td>${newsList.news_title}</td>
									<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${newsList.news_date}"/></td>
									<td>
										<c:if test="${newsList.news_pass eq 1}">
											通過
										</c:if>
										<c:if test="${newsList.news_pass eq 0}">
											未通過
										</c:if>
									</td>
									
									<td>
									  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/news.do">
										<input type="submit" value="查看"> 
										<input type="hidden" name="news_id" value="${newsList.news_id}">
										<input type="hidden" name="action" value="getBackNewsContent">
									</td></FORM>
									
									<td>
										<c:if test="${newsList.news_pass eq 1}">
											<img src="<%=request.getContextPath()%>/img/OK.jpg" height='50'>
										</c:if>
										<c:if test="${newsList.news_pass eq 0}">
											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/news.do">
												<input type="submit" value="通過"> 
												<input type="hidden" name="news_id" value="${newsList.news_id}">
												<input type="hidden" name="action" value="UpdatePass">
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