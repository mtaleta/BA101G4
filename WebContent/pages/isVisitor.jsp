<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="isMember" value="false" />
<c:set var="isStore" value="false" />
<c:set var="isVisitor" value="false" />
<c:set var="displayName" value="" />
<c:choose>
	<c:when test="${sessionScope.MEMBER!=null}">
		<c:set var="isMember" value="true" />
		<c:set var="displayName"
			value="${sessionScope.MEMBER.mem_name}&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" />
	</c:when>
	<c:when test="${sessionScope.STORE!=null}">
		<c:set var="isStore" value="true" />
		<c:set var="displayName"
			value="${sessionScope.STORE.store_name}&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" />
	</c:when>
	<c:otherwise>
		<c:set var="isVisitor" value="true" />
		<c:set var="displayName" value="CoffeePuzzle 您好" />
	</c:otherwise>
</c:choose>