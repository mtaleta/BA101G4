<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
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
		// var inputs = document.getElementsByTagName('input');
		
		document.form1.mem_acct.value="member02";
		document.form1.mem_pwd.value="member02";
		document.form1.mem_name.value="member02";
		document.form1.mem_tel.value="0123111111";
		document.form1.mem_email.value="ba101g4@gmail.com";
		document.form1.mem_add.value="桃園市中壢區中大路300號";
		}
	</script>
</head>
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
                    <li><a href="${pageContext.request.contextPath}/frontend/member/login_Member.jsp">點此登入&nbsp;&nbsp;</a></li>
                </ul>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
    <%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>
		<br>
	<div class="wrap login_wrap" style="margin-top:-100px;">
		<div class="content">		
		<div class="logo"></div>		
		<div class="login_box">				
			<div class="login_form">
				<div style="text-align:center;">    
					<ul class="tab-group">
						<li class="tab active" style="font-family:'FontAwesome';"><a href="${pageContext.request.contextPath}/frontend/member/addMember.jsp">一般會員註冊</a></li>
						<li class="tab" ><a href="${pageContext.request.contextPath}/frontend/store/addStore.jsp" class="store"  >店家註冊</a></li>
					 </ul>
					</div>
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/member/member.do" name="form1" enctype="multipart/form-data" onsubmit="return checkdata();">
							<div class="form_text_ipt">
								<input type="TEXT" name="mem_acct" size="45" value="<%= (memberVO==null)? "" : memberVO.getMem_acct()%>" placeholder="會員帳號" required="required"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="password" name="mem_pwd" size="45"	value="<%= (memberVO==null)? "" : memberVO.getMem_pwd()%>" placeholder="會員密碼" required="required"/>
							</div>
							
							<div class="form_text_ipt">
								<input type="TEXT" name="mem_name" size="45" value="<%= (memberVO==null)? "" : memberVO.getMem_name()%>" placeholder="會員名稱" required="required"/>	
							</div>
														
							<div class="form_text_ipt">
							<input type="TEXT" name="mem_tel" size="45"	value="<%= (memberVO==null)? "" : memberVO.getMem_tel()%>" placeholder="會員電話" maxlength="10" required="required"/>
							</div>
														
							<div class="form_text_ipt">
							<input type="TEXT" name="mem_email" size="45" value="<%= (memberVO==null)? "" : memberVO.getMem_email()%>" placeholder="會員E-mail" required="required"/>
							</div>
													
							<div class="form_text_ipt">
							<input type="TEXT" name="mem_add" size="45" value="<%= (memberVO==null)? "" : memberVO.getMem_add()%>" placeholder="會員地址" /> 
							</div>

							<input type="hidden" name="mem_points" size="45" value="<%= (memberVO==null)? "0" : memberVO.getMem_points()%>" />
							
							
							<div class="form_text_ipt">
							<input type="file" name="upfile1" size="10">
							</div>
								
							<div class="form_btn">
							<input type="hidden" name="action" value="insert">
								<input type="submit" value="送出新增">
							</div>
							
							</FORM>
							<div class="form_reg_btn">
								<span>已有帳號?  </span><a href="${pageContext.request.contextPath}/frontend/member/login_Member.jsp">馬上登入</a>
							</div>
							
							<input type="button" onclick="show()" style="background-color:pink;">
						<div style="height:30px;"></div>
					</div>
				</div>
			</div>
		</div>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

</body>

</html>
