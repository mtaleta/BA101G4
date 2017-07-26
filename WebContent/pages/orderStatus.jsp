<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${orderlistVO.ord_pick == 1}">
		<c:set value="購物" var="orderType" />
	</c:when>
	<c:when test="${orderlistVO.ord_pick == 2}">
		<c:set value="外帶" var="orderType" />
	</c:when>
	<c:when test="${orderlistVO.ord_pick == 3}">
		<c:set value="外送" var="orderType" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${orderlistVO.ord_shipping == 1}">
		<c:set value="未處理" var="orderStatus" />
	</c:when>
	<c:when test="${orderlistVO.ord_shipping == 2}">
		<c:set value="不接單" var="orderStatus" />
	</c:when>
	<c:when test="${orderlistVO.ord_shipping == 3}">
		<c:set value="已接單" var="orderStatus" />
	</c:when>
	<c:when test="${orderlistVO.ord_shipping == 4}">
		<c:set value="已出貨" var="orderStatus" />
	</c:when>
	<c:otherwise>
		<c:set value="交易結束" var="orderStatus" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${orderlist2VO.ord_pick == 1}">
		<c:set value="購物" var="orderType2" />
	</c:when>
	<c:when test="${orderlist2VO.ord_pick == 2}">
		<c:set value="外帶" var="orderType2" />
	</c:when>
	<c:when test="${orderlist2VO.ord_pick == 3}">
		<c:set value="外送" var="orderType2" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${orderlist2VO.ord_shipping == 1}">
		<c:set value="未處理" var="orderStatus2" />
	</c:when>
	<c:when test="${orderlist2VO.ord_shipping == 2}">
		<c:set value="不接單" var="orderStatus2" />
	</c:when>
	<c:when test="${orderlist2VO.ord_shipping == 3}">
		<c:set value="已接單" var="orderStatus2" />
	</c:when>
	<c:when test="${orderlist2VO.ord_shipping == 4}">
		<c:set value="${orderlist2VO.ord_pick==2?'等待取餐':'出餐中'}" var="orderStatus2" />
	</c:when>
	<c:otherwise>
		<c:set value="交易結束" var="orderStatus2" />
	</c:otherwise>
</c:choose>