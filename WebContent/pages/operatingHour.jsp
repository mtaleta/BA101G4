<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<c:if test = "${dayOfWeek==2}">
			<c:set var="dy" value="星期一" />
        	<c:set var="open" value="${storeVO.mon_open}" />
        	<c:set var="close" value="${storeVO.mon_close}" />
      	</c:if>
		<c:if test = "${dayOfWeek==3}">
			<c:set var="dy" value="星期二" />
        	<c:set var="open" value="${storeVO.tue_open}" />
        	<c:set var="close" value="${storeVO.tue_close}" />
      	</c:if>
		<c:if test = "${dayOfWeek==4}">
			<c:set var="dy" value="星期三" />
        	<c:set var="open" value="${storeVO.wed_open}" />
        	<c:set var="close" value="${storeVO.wed_close}" />
      	</c:if>
		<c:if test = "${dayOfWeek==5}">
			<c:set var="dy" value="星期四" />
        	<c:set var="open" value="${storeVO.thu_open}" />
        	<c:set var="close" value="${storeVO.thu_close}" />
      	</c:if>
		<c:if test = "${dayOfWeek==6}">
			<c:set var="dy" value="星期五" />
        	<c:set var="open" value="${storeVO.fri_open}" />
        	<c:set var="close" value="${storeVO.fri_close}" />
      	</c:if>
		<c:if test = "${dayOfWeek==7}">
			<c:set var="dy" value="星期六" />
        	<c:set var="open" value="${storeVO.sat_open}" />
        	<c:set var="close" value="${storeVO.sat_close}" />
      	</c:if>
		<c:if test = "${dayOfWeek==1}">
			<c:set var="dy" value="星期日" />
        	<c:set var="open" value="${storeVO.sun_open}" />
        	<c:set var="close" value="${storeVO.sun_close}" />
      	</c:if>
      	<c:if test="${not empty open}">
      		<c:set var="abc" value="${operatingTime.checkOperatingTime(open,close)}"/>
      	</c:if>		