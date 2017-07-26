<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>


<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreDAO" />
<jsp:useBean id="photo_storeSvc" scope="page" class="com.photo_store.model.Photo_storeDAO" />

<html>
<style type="text/css">
	.showbox {
	width: 1000px;
	height: 460px;
	border: 2px solid #CCC;
	vertical-align: middle;
}
	
	.photoimgs {
	margin-top: 10px;
	width: 680px;
	overflow: hidden;
}
	.photoimgs a {
	margin-right: 10px;
}
	.photoimgs a img {
	width: 140px;
	height: 92px;
	border: 2px solid #CCC;
	vertical-align: middle;
}
    
</style>
<head>
<title>���a�Ӥ�- PhotoStore.jsp</title>
</head>
<body bgcolor='white'>


		<div class="showbox"><img src="<%=request.getContextPath()%>/images?action=store&id=${storeVO.store_id}" id="show-image" height="460" width="1000"></div>

		<div class="photoimgs" align="left">
			<c:forEach var="photo_storeVO" items="${photo_storeSvc.getStorePicture(storeVO.store_id)}">
			
					<a href="<%=request.getContextPath()%>/images?action=photo_store&id=${photo_storeVO.photo_id}" target="_blank"><img src="<%=request.getContextPath()%>/images?action=photo_store&id=${photo_storeVO.photo_id}" ></a>
			
			</c:forEach>
		</div>

</body>
</html>
<script src="https://code.jquery.com/jquery.js"></script>
<script>
$(function(){
	// �Ψ���ܤj�Ϥ���
	var $showImage = $('#show-image');
 
	// ��ƹ����� .abgne-block-20120106 �����Y�@�ӶW�s����
	$('.photoimgs a').mouseover(function(){
		// �� #show-image �� src �令�Q���쪺�W�s������m
		$showImage.attr('src', $(this).attr('href'));
	}).click(function(){
		// �p�G�W�s���Q�I����, **false�����s���ʧ@
		return true;
	});
});
</script>

