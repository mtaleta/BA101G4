<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
					<li><a href='${pageContext.request.contextPath}/frontend/member/listOneMember.jsp'>查詢會員資料</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/member/update_member_input.jsp'>修改會員資料</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/member/change_Member_Password.jsp'>修改密碼</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/orderlist/shoppingcart.jsp'>購物車</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/orderlist/order_mem.jsp'>查看訂單</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/member/member.do?action=getOne_For_Mem_id'>一般會員看到自己參與的活動</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/activity/activity.do?action=getOne_For_Display'>一般會員舉辦的活動</a></li>
					<li><a href='${pageContext.request.contextPath}/frontend/member/topUp.jsp'>儲值點數</a></li>
					<li></li>