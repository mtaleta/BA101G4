<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.store.model.*"%>
<%
	StoreVO storeVO = (StoreVO) request.getAttribute("storeVO");
%>

<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="HandheldFriendly" content="true" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<link href="https://cdn.jotfor.ms/static/formCss.css?3.3.612" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="https://cdn.jotfor.ms/css/styles/nova.css?3.3.612" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addsignin.css" type="text/css">
		<title>SignIn</title>
		
		<script>
		function show() {
		document.form1.store_acct.value="store22";
		document.form1.store_pwd.value="store22";
		document.form1.store_name.value="store22";
		document.form1.store_tel.value="0123456";
		document.form1.store_add.value="桃園市中壢區中大路300號";
		document.form1.store_email.value="ba101g4@gmail.com";
		document.form1.store_cpse.value="12345678";
		document.form1.min_order.value="0";

		}
	</script>
</head>
<div class="body"></div>
	<nav class="navbar navbar-default" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="float-left" href="index.html"><img src="${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png"></a>
                <a class="navbar-brand" href="index.html">CoffeePuzzle</a>
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
                    <li><a href="${pageContext.request.contextPath}/frontend/store/login_Store.jsp">點此登入&nbsp;&nbsp;</a></li>
                </ul>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
<br>
	<div class="wrap login_wrap" style="margin-top:-100px;">
		<div class="content">		
		<div class="logo"></div>		
		<div class="login_box">				
			<div class="login_form">
				<div style="text-align:center;">    
					<ul class="tab-group">
						<li class="tab"><a href="${pageContext.request.contextPath}/frontend/member/addMember.jsp">一般會員註冊</a></li>
						<li class="tab active" style="font-family:'FontAwesome';" ><a href="${pageContext.request.contextPath}/frontend/store/addStore.jsp" class="store"  >店家註冊</a></li>
					 </ul>
					</div>
					<input type="button" onclick="show()" style="background-color:pink;">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/store/store.do" name="form1" enctype="multipart/form-data">
							<div style="height: 400px;overflow: auto;">
							<div class="form_text_ipt">
								<input type="TEXT" name="store_acct" size="45" value="<%=(storeVO == null) ? "" : storeVO.getStore_acct()%>" placeholder="店家帳號"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="password" name="store_pwd" size="45" value="<%=(storeVO == null) ? "" : storeVO.getStore_pwd()%>" placeholder="密碼"/>
							</div>
							<div class="form_text_ipt">
								<input type="TEXT" name="store_name" size="45"	value="<%=(storeVO == null) ? "" : storeVO.getStore_name()%>" placeholder="店名"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="store_tel" size="45" value="<%=(storeVO == null) ? "" : storeVO.getStore_tel()%>" placeholder="電話"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="store_add" size="45" value="<%=(storeVO == null) ? "" : storeVO.getStore_add()%>" placeholder="地址"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="store_email" size="45"	value="<%=(storeVO == null) ? "@gmail.com" : storeVO.getStore_email()%>" placeholder="E-mail"/>
							</div>
							
								<input type="hidden" name="store_points" size="45" value="<%=(storeVO == null) ? "0" : storeVO.getStore_points()%>" />
							
							<div class="form_text_ipt">
								<input type="TEXT" name="store_cpse" size="45" value="<%=(storeVO == null) ? "" : storeVO.getStore_cpse()%>" placeholder="營業登記證"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="min_order" size="45" value="<%=(storeVO == null) ? "" : storeVO.getMin_order()%>" placeholder="外送數量門檻"/>
							</div>
							
							<div class="form_check_ipt">
								有無低消
								<input type="radio" name="is_min_order" value="0" ${storeVO.is_min_order==null?'checked':(storeVO.is_min_order==0?'checked':'')}>無
								<input type="radio" name="is_min_order" value="1" ${storeVO.is_min_order==null?'':(storeVO.is_min_order==1?'checked':'')}>有
							</div>						
							
							<div class="form_check_ipt">
								有無WIFI
								<input type="radio" name="is_wifi" value="0" ${storeVO.is_wifi==null?'checked':(storeVO.is_wifi==0?'checked':'')}>無
								<input type="radio" name="is_wifi" value="1" ${storeVO.is_wifi==null?'':(storeVO.is_wifi==1?'checked':'')}>有
							</div>
													
							<div class="form_check_ipt">
								有無插座
								<input type="radio" name="is_plug" value="0" ${storeVO.is_plug==null?'checked':(storeVO.is_plug==0?'checked':'')}>無
								<input type="radio" name="is_plug" value="1" ${storeVO.is_plug==null?'':(storeVO.is_plug==1?'checked':'')}>有
							</div>
							
							<div class="form_check_ipt">
								有無賣單品
								<input type="radio" name="is_single_orgn" value="0" ${storeVO.is_single_orgn==null?'checked':(storeVO.is_single_orgn==0?'checked':'')}>無
								<input type="radio" name="is_single_orgn" value="1" ${storeVO.is_single_orgn==null?'':(storeVO.is_single_orgn==1?'checked':'')}>有
							</div>
							
							<div class="form_check_ipt">
								有賣甜點
								<input type="radio" name="is_dessert" value="0" ${storeVO.is_dessert==null?'checked':(storeVO.is_dessert==0?'checked':'')}>無
								<input type="radio" name="is_dessert" value="1" ${storeVO.is_dessert==null?'':(storeVO.is_dessert==1?'checked':'')}>有						
							</div>
							
							
							<div class="form_check_ipt">
								有賣正餐
								<input type="radio" name="is_meal" value="0" ${storeVO.is_meal==null?'checked':(storeVO.is_meal==0?'checked':'')}>無
								<input type="radio" name="is_meal" value="1" ${storeVO.is_meal==null?'':(storeVO.is_meal==1?'checked':'')}>有						
							</div>
							
							
							<div class="form_check_ipt">
								有無限時
								<input type="radio" name="is_time_limit" value="0" ${storeVO.is_time_limit==null?'checked':(storeVO.is_time_limit==0?'checked':'')}>無
								<input type="radio" name="is_time_limit" value="1" ${storeVO.is_time_limit==null?'':(storeVO.is_time_limit==1?'checked':'')}>有						
							</div>
							
							<div class="form_check_ipt">
								一有無營業
								<input type="radio" name="mon_isopen" value="0" ${storeVO.mon_isopen==null?'checked':(storeVO.mon_isopen==0?'checked':'')}>無
								<input type="radio" name="mon_isopen" value="1" ${storeVO.mon_isopen==null?'':(storeVO.mon_isopen==1?'checked':'')}>有						
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="mon_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getMon_open()%>" placeholder="一營業時間起"/>
							</div>
							
							
							<div class="form_text_ipt">
								<input type="TEXT" name="mon_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getMon_close()%>" placeholder="一營業時間訖"/>
							</div>
							
							
							<div class="form_check_ipt">
								二有無營業
								<input type="radio" name="tue_isopen" value="0" ${storeVO.tue_isopen==null?'checked':(storeVO.tue_isopen==0?'checked':'')}>無
								<input type="radio" name="tue_isopen" value="1" ${storeVO.tue_isopen==null?'':(storeVO.tue_isopen==1?'checked':'')}>有						
							</div>
																			
							<div class="form_text_ipt">
								<input type="TEXT" name="tue_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getTue_open()%>" placeholder="二營業時間起"/>
							</div>
																				
							<div class="form_text_ipt">
								<input type="TEXT" name="tue_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getTue_close()%>" placeholder="二營業時間訖點"/>
							</div>
																			
							<div class="form_check_ipt">
								三有無營業
								<input type="radio" name="wed_isopen" value="0" ${storeVO.wed_isopen==null?'checked':(storeVO.wed_isopen==0?'checked':'')}>無
								<input type="radio" name="wed_isopen" value="1" ${storeVO.wed_isopen==null?'':(storeVO.wed_isopen==1?'checked':'')}>有						
							</div>
																					
							<div class="form_text_ipt">
								<input type="TEXT" name="wed_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getWed_open()%>" placeholder="三營業時間起"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="wed_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getWed_close()%>" placeholder="三營業時間訖"/>
							</div>
												
							<div class="form_check_ipt">
								四有無營業
								<input type="radio" name="thu_isopen" value="0" ${storeVO.thu_isopen==null?'checked':(storeVO.thu_isopen==0?'checked':'')}>無
								<input type="radio" name="thu_isopen" value="1" ${storeVO.thu_isopen==null?'':(storeVO.thu_isopen==1?'checked':'')}>有												
							</div>					
														
							<div class="form_text_ipt">
								<input type="TEXT" name="thu_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getThu_open()%>" placeholder="四營業時間起"/>
							</div>
														
							<div class="form_text_ipt">
								<input type="TEXT" name="thu_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getThu_close()%>" placeholder="四營業時間訖"/>
							</div>							
														
							<div class="form_check_ipt">
								五有無營業
								<input type="radio" name="fri_isopen" value="0" ${storeVO.fri_isopen==null?'checked':(storeVO.fri_isopen==0?'checked':'')}>無
								<input type="radio" name="fri_isopen" value="1" ${storeVO.fri_isopen==null?'':(storeVO.fri_isopen==1?'checked':'')}>有												
							</div>
													
							<div class="form_text_ipt">
								<input type="TEXT" name="fri_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getFri_open()%>" placeholder="五營業時間起"/>
							</div>
													
							<div class="form_text_ipt">
								<input type="TEXT" name="fri_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getFri_close()%>" placeholder="五營業時間訖"/>
							</div>
																				
							<div class="form_check_ipt">
								六無營業業
								<input type="radio" name="sat_isopen" value="0" ${storeVO.sat_isopen==null?'checked':(storeVO.sat_isopen==0?'checked':'')}>無
								<input type="radio" name="sat_isopen" value="1" ${storeVO.sat_isopen==null?'':(storeVO.sat_isopen==1?'checked':'')}>有												
							</div>
							
							
							<div class="form_text_ipt">
								<input type="TEXT" name="sat_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getSat_open()%>" placeholder="六營業時間起"/>
							</div>
							
														
							<div class="form_text_ipt">
								<input type="TEXT" name="sat_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getSat_close()%>" placeholder="六營業時間訖"/>
							</div>
							
														
							<div class="form_check_ipt">
								日有無營業
								<input type="radio" name="sun_isopen" value="0" ${storeVO.sun_isopen==null?'checked':(storeVO.sun_isopen==0?'checked':'')}>無
								<input type="radio" name="sun_isopen" value="1" ${storeVO.sun_isopen==null?'':(storeVO.sun_isopen==1?'checked':'')}>有												
							</div>
																	
							<div class="form_text_ipt">
								<input type="TEXT" name="sun_open" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getSun_open()%>" placeholder="日營業時間起"/>
							</div>
																					
							<div class="form_text_ipt">
								<input type="TEXT" name="sun_close" size="45" value="<%=(storeVO == null) ? "00:00" : storeVO.getSun_close()%>" placeholder="日營業時間訖"/>
							</div>
													
							<div class="form_text_ipt">
								<input type="file" name="upfile1" size="10">
							</div>
																			
							<div class="form_btn">
								<input type="hidden" name="action" value="insert">
								<input type="submit" value="送出新增">
							</div>
							</div>
						</FORM>
							<div class="form_reg_btn">
								<span>已有帳號?  </span><a href="${pageContext.request.contextPath}/frontend/store/login_Store.jsp">馬上登入</a>
							</div>
							
						<div style="height:30px;"></div>
					</div>
				</div>
			</div>
		</div>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

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
