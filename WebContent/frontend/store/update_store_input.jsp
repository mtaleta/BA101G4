<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.store.model.*"%>
<%
	StoreVO storeVO = (StoreVO) request.getSession().getAttribute("STORE");
	pageContext.setAttribute("storeVO", storeVO);
%>
<html lang="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>CoffeePuzzle</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexbar.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
	<link rel="stylesheet" type="text/css" media="all"href="${pageContext.request.contextPath}/css/default_Selectpage.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update_data.css">
</head>
<body>
<div class="body"></div>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="float-left" href="${pageContext.request.contextPath}/frontend/index.jsp"><img src="${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png"></a>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/frontend/index.jsp">CoffeePuzzle</a>
            </div>
            <!-- 手機隱藏選單區 -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <!-- 左選單 -->
                <ul class="nav navbar-nav">
					<li><a href="<%=request.getContextPath()%>/frontend/news/listOfAllNewsByStore.jsp" >最新消息</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/store/findStore.jsp">搜尋店家</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/spndcoffee/listAllSpndCoffee.jsp">寄杯</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/product/shop.jsp">購物</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/activity/activityList.jsp">活動</a></li>
				</ul>
                <!-- 右選單 -->
                <ul class="nav navbar-nav navbar-right">
                    <li class="nav-login"><%=storeVO.getStore_name()==null? "CoffeePuzzle":storeVO.getStore_name()%>&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                    <li><a href="${pageContext.request.contextPath}/frontend/store/select_page.jsp">個人設定</a></li>
                    <li class="nav-login-out"><a href="${pageContext.request.contextPath}/frontend/store/logout_Store.jsp"><%= storeVO.getStore_name()==null? "":"&nbsp;&nbsp;&nbsp;&nbsp;登出"%></a></li>
                </ul>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
		<div id="wrapper">
			<div id="page" class="container">
				<div id="content">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/store/store.do" name="form1" enctype="multipart/form-data">
					<table border="0">
					
						<tr>
							<th class="update_key">店家帳號:</th>
							<td class="updata_data"><%= storeVO.getStore_acct()%></td>
						</tr>
						<tr>
							<th>店名:</th>
							<td><input type="TEXT" name="store_name" size="45"
								value="<%= storeVO.getStore_name()%>" /></td>
						</tr>
						<tr>
							<th>電話:</th>
							<td><input type="TEXT" name="store_tel" size="45"
								value="<%= storeVO.getStore_tel()%>" /></td>
						</tr>
						<tr>
							<th>地址:</th>
							<td><input type="TEXT" name="store_add" size="45"
								value="<%= storeVO.getStore_add()%>" /></td>
						
						<tr>
							<th>Email:</th>
							<td><input type="email" name="store_email" size="45"
								value="<%= storeVO.getStore_email()%>" /></td>
						</tr>
						<tr>
							<th>營業登記證:</th>
							<td><input type="TEXT" name="store_cpse" size="45" 
								value="<%= storeVO.getStore_cpse()%>" /></td>
						</tr>
						<tr>
							<th>外送數量門檻:</th>
							<td><input type="TEXT" name="min_order" size="45" 
								value="<%= storeVO.getMin_order()%>" /></td>
						</tr>
						<tr>
							<th>外送數量門檻:</th>
							<td><input type="TEXT" name="min_order" size="45" 
								value="<%= storeVO.getMin_order()%>" /></td>
						</tr>
						<tr>
							<th>有無低消</th>
							<td><input type="radio" name="is_min_order" value="0" ${storeVO.is_min_order==null?'checked':(storeVO.is_min_order==0?'checked':'')}>無
								<input type="radio" name="is_min_order" value="1" ${storeVO.is_min_order==null?'':(storeVO.is_min_order==1?'checked':'')}>有
							</td>
							<th class="update_key">有無WIFI:</th>
							<td><input type="radio" name="is_wifi" value="0" ${storeVO.is_wifi==null?'checked':(storeVO.is_wifi==0?'checked':'')}>無
								<input type="radio" name="is_wifi" value="1" ${storeVO.is_wifi==null?'':(storeVO.is_wifi==1?'checked':'')}>有
							</td>
						</tr>
						<tr>
							<th>有無插座:</th>
							<td><input type="radio" name="is_plug" value="0" ${storeVO.is_plug==null?'checked':(storeVO.is_plug==0?'checked':'')}>無
								<input type="radio" name="is_plug" value="1" ${storeVO.is_plug==null?'':(storeVO.is_plug==1?'checked':'')}>有
							</td>
							<th>有賣單品:</th>
							<td><input type="radio" name="is_single_orgn" value="0" ${storeVO.is_single_orgn==null?'checked':(storeVO.is_single_orgn==0?'checked':'')}>無
								<input type="radio" name="is_single_orgn" value="1" ${storeVO.is_single_orgn==null?'':(storeVO.is_single_orgn==1?'checked':'')}>有
							</td>
						</tr>
						<tr>
							<th>有賣甜點:</th>
							<td><input type="radio" name="is_dessert" value="0" ${storeVO.is_dessert==null?'checked':(storeVO.is_dessert==0?'checked':'')}>無
								<input type="radio" name="is_dessert" value="1" ${storeVO.is_dessert==null?'':(storeVO.is_dessert==1?'checked':'')}>有						
							</td>
							<th>有賣正餐:</th>
							<td><input type="radio" name="is_meal" value="0" ${storeVO.is_meal==null?'checked':(storeVO.is_meal==0?'checked':'')}>無
								<input type="radio" name="is_meal" value="1" ${storeVO.is_meal==null?'':(storeVO.is_meal==1?'checked':'')}>有						
							</td>
						</tr>
						<tr>
							<th>有無限時:</th>
							<td><input type="radio" name="is_time_limit" value="0" ${storeVO.is_time_limit==null?'checked':(storeVO.is_time_limit==0?'checked':'')}>無
								<input type="radio" name="is_time_limit" value="1" ${storeVO.is_time_limit==null?'':(storeVO.is_time_limit==1?'checked':'')}>有	
							</td>
						<tr>
							<th>一有無營業:</th>
							<td><input type="radio" name="mon_isopen" value="0" ${storeVO.mon_isopen==null?'checked':(storeVO.mon_isopen==0?'checked':'')}>無
								<input type="radio" name="mon_isopen" value="1" ${storeVO.mon_isopen==null?'':(storeVO.mon_isopen==1?'checked':'')}>有
							</td>
						</tr>
						<tr>
							<th>一營業時間起:</th>
							<td><input type="TEXT" name="mon_open" size="45" 
								value="<%=storeVO.getMon_open().toString().substring(11,16)%>" /></td>
							<th>一營業時間訖:</th>
							<td><input type="TEXT" name="mon_close" size="45" 
								value="<%= storeVO.getMon_close().toString().substring(11,16)%>" /></td>
						</tr>
							<tr>
							<th>二有無營業:</th>
							<td><input type="radio" name="tue_isopen" value="0" ${storeVO.tue_isopen==null?'checked':(storeVO.tue_isopen==0?'checked':'')}>無
								<input type="radio" name="tue_isopen" value="1" ${storeVO.tue_isopen==null?'':(storeVO.tue_isopen==1?'checked':'')}>有						
							</td>
						</tr>
							<tr>
							<th>二營業時間起:</th>
							<td><input type="TEXT" name="tue_open" size="45" 
								value="<%= storeVO.getTue_open().toString().substring(11,16)%>" /></td>
							<th>二營業時間訖:</th>
							<td><input type="TEXT" name="tue_close" size="45" 
								value="<%= storeVO.getTue_close().toString().substring(11,16)%>" /></td>
						</tr>
							<tr>
							<th>三有無營業:</th>
							<td><input type="radio" name="wed_isopen" value="0" ${storeVO.wed_isopen==null?'checked':(storeVO.wed_isopen==0?'checked':'')}>無
								<input type="radio" name="wed_isopen" value="1" ${storeVO.wed_isopen==null?'':(storeVO.wed_isopen==1?'checked':'')}>有					
							</td>
						</tr>
							<tr>
							<th>三營業時間起:</th>
							<td><input type="TEXT" name="wed_open" size="45" 
								value="<%= storeVO.getWed_open().toString().substring(11,16)%>" /></td>
							<th>三營業時間訖:</th>
							<td><input type="TEXT" name="wed_close" size="45" 
								value="<%= storeVO.getWed_close().toString().substring(11,16)%>" /></td>
						</tr>
							<tr>
							<th>四有無營業:</th>
							<td><input type="radio" name="thu_isopen" value="0" ${storeVO.thu_isopen==null?'checked':(storeVO.thu_isopen==0?'checked':'')}>無
								<input type="radio" name="thu_isopen" value="1" ${storeVO.thu_isopen==null?'':(storeVO.thu_isopen==1?'checked':'')}>有												
							</td>
						</tr>
							<tr>
							<th>四營業時間起:</th>
							<td><input type="TEXT" name="thu_open" size="45" 
								value="<%= storeVO.getThu_open().toString().substring(11,16)%>" /></td>
							<th>四營業時間訖:</th>
							<td><input type="TEXT" name="thu_close" size="45" 
								value="<%= storeVO.getThu_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<th>五有無營業:</th>
							<td><input type="radio" name="fri_isopen" value="0" ${storeVO.fri_isopen==null?'checked':(storeVO.fri_isopen==0?'checked':'')}>無
								<input type="radio" name="fri_isopen" value="1" ${storeVO.fri_isopen==null?'':(storeVO.fri_isopen==1?'checked':'')}>有
							</td>
						</tr>
							<tr>
							<th>五營業時間起:</th>
							<td><input type="TEXT" name="fri_open" size="45" 
								value="<%= storeVO.getFri_open().toString().substring(11,16)%>" /></td>
							<th>五營業時間訖:</th>
							<td><input type="TEXT" name="fri_close" size="45" 
								value="<%= storeVO.getFri_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<th>六有無營業:</th>
							<td><input type="radio" name="sat_isopen" value="0" ${storeVO.sat_isopen==null?'checked':(storeVO.sat_isopen==0?'checked':'')}>無
								<input type="radio" name="sat_isopen" value="1" ${storeVO.sat_isopen==null?'':(storeVO.sat_isopen==1?'checked':'')}>有												
							</td>
						</tr>
						<tr>
							<th>六營業時間起:</th>
							<td><input type="TEXT" name="sat_open" size="45" 
								value="<%= storeVO.getSat_open().toString().substring(11,16)%>" /></td>
							<th>六營業時間訖:</th>
							<td><input type="TEXT" name="sat_close" size="45" 
								value="<%= storeVO.getSat_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<th>日有無營業:</th>
							<td><input type="radio" name="sun_isopen" value="0" ${storeVO.sun_isopen==null?'checked':(storeVO.sun_isopen==0?'checked':'')}>無
								<input type="radio" name="sun_isopen" value="1" ${storeVO.sun_isopen==null?'':(storeVO.sun_isopen==1?'checked':'')}>有												
							</td>
						</tr>
						<tr>
							<th>日營業時間起:</th>
							<td><input type="TEXT" name="sun_open" size="45" 
								value="<%=storeVO.getSun_open().toString().substring(11,16)%>" /></td>
							<th>日營業時間訖:</th>
							<td><input type="TEXT" name="sun_close" size="45" 
								value="<%= storeVO.getSun_close().toString().substring(11,16)%>" /></td>
						</tr>
	<tr>
		<th>上傳照片:</th>
     	<td>  	 
		    <input type="file" name="upfile1">
		<br/>
		</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="submit" value="送出修改"></FORM>
</div>
		<div id="sidebar">
			<div class="box2">
				<div class="title">
					<h2>店家資料專區:</h2>
				</div>
				<ul class="style2">
					<%@ include file="/pages/select_store.jsp" %>
				</ul>
			</div>
		</div>
	</div>
</div>
</body>
<link rel=stylesheet type="text/css"
	href="${pageContext.request.contextPath}/css/jquery.timepicker.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery.timepicker.min.js"></script>
<script type="text/javascript">
	$(function() {
		/*https://github.com/jonthornton/jquery-timepicker*/
		$('input[name$="_open"],input[name$="_close"]').timepicker({
			'timeFormat' : 'H:i'
		});
	});
</script>
</html>
