<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />
<jsp:useBean id="store_tagDAO" scope="page" class="com.store_tag.model.Store_tagDAO" />
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		 <section class="content-header">
                <h1>
               		  消息內容
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
                                        <h3 class="box-title">消息內容</h3>
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
                                    <th>最新消息編號</th>
									<th>最新消息標題</th>
									<th>發布店家</th>
									<th>發布時間</th>
                                </tr>
                                <c:if test="${not empty newsVO}">
									<td>${newsVO.news_id}</td>
									<td>${newsVO.news_title}</td>
									<td>${storeSvc.getOneStore(newsVO.store_id).store_name}</td>
									<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${newsVO.news_date}"/></td>
									
								</c:if>
                             </tbody>
                        </table>
						<table class="table-ext table table-hover">
                            <tbody>
                               <tr>
								<th><img src="<%=request.getContextPath()%>/images?action=news&id=${newsVO.news_id}"></th>
								</tr>
								<tr>
								<c:if test="${not empty newsVO}">
									
									<td>${newsVO.news_content}</td>
								</c:if>
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