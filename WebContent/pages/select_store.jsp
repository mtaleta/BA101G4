<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
					<li><a href='${pageContext.request.contextPath}/frontend/store/listOneStore.jsp'>查看店家資料</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/store/update_store_input.jsp'>修改店家資料</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/store/change_Store_Password.jsp'>修改密碼</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/news/addNews.jsp'>新增最新消息</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/store/listOfAllNewsByStore.jsp'>最新消息狀態</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/orderlist/good_manage.jsp'>商品管理</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/orderlist/order_owner.jsp'>訂單管理</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/store/store.do?action=getActivity_For_Store_id'>店家會員確認活動</a></li>
					<li></li>