<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.spndcoffee.model.*"%>
<%@ page import="com.store.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<%
	SpndcoffeeVO spndcoffeeVO = (SpndcoffeeVO) request.getAttribute("spndcoffeeVO");
%>

<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>發起寄杯活動</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
    <style type="text/css">
    /**{
            outline: 1px solid #F00;
            }*/
    html {
        height: 100%;
    }
    
    body {
        min-height: 100%;
        display: grid;
        grid-template-rows: 1fr auto;
        font-size:14px;
        line-height:1.428571429;
        color:#757575;
        -webkit-font-smoothing:antialiased;
    }
    
    .footer {
        grid-row-start: 2;
        grid-row-end: 3;
        background-color: #333;
        color: #FFF;
        line-height: 1.428571429;
    }
    
    .footer:before {
        content: "";
        display: block;
        height: 4px;
        /*background: #0088d2;*/
        margin-bottom: 16px;
    }
    .titleGroup{
        line-height:60px;
        height:60px;
        font-size:36px;
        
    }
    .form-controls{
        padding:6px 12px;
        border:1px solid #ccc;
        border-radius:4px;
        line-height:60px;
        height:60px;
        font-size:36px;
        width:100%;
    }
    img {
        width: 100%;
    }
    
    .textsizeb {
        font-weight: bold;
    }
    
    .btn-cofe {
        background: #fff;
        /* Old browsers */
        background: -moz-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* FF3.6+ */
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #7f5f42), color-stop(50%, #7f5f42), color-stop(100%, #7f5f42));
        /*   Chrome,Safari4+ */
        background: -webkit-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* Chrome10+,Safari5.1+ */
        background: -o-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* Opera 11.10+ */
        background: -ms-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* IE10+ */
        background: linear-gradient(to bottom, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* W3C */
        filter: progid: DXImageTransform.Microsoft.gradient( startColorstr='#7f5f42', endColorstr='#7f5f42', GradientType=0);
        /* IE6-9 */
        color: #fff;
    }
    
    .btn-cofe:hover {
        background: #603813;
        /*旧版浏览器*/
        color: #fff;
    }
    /*活動照片*/
    .figure{
        background:#ededed;
        color:#bdbdbd;
        height:530px;
        margin:15px 0 0;
        position:relative;
        text-align:center;
    }
    .image-text{
        position: absolute;
        top:152px;
        left:8px;
        width:100%;
        font-size:30px;
        line-height:36px;
        letter-spacing:20px;
        color:#bdbdbd;
    }
    .image-text-size{
        font-size:144px;
        position:relative;
        top:68px;
        line-height:440px;
    }
   
   
    .uploadimg {
	line-height: 45px;
	height: 45px;
	background: rgba(0, 0, 0, .5);
	color: #fff;
	width: 100%;
	position: absolute;
	z-index: 1;
	left: 0;
	bottom: 0;
}

.uploadimg  input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
    filter: alpha(opacity=0);
    cursor: pointer;
    height:45px;
}
.uploadimg:hover {
	background: rgba(0, 0, 0, .7);
	color: #fff;
	text-decoration: none;
	border-color: #ccc;
}

.file {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}


   
    /*報名表單*/
    .background-gray .eventInfo{
        /*padding:30px 0 0;*/
/*        margin:-10px 0 0;*/
        background-color:#f5f5f5;
        box-sizing:border-box;
        -webkit-font-smoothing:antialiased;
        text-center:none;
    }
    label{
        display:inline-block;
        margin-bottom:5px;
        font-weight:700;
    }
    .error-input{
        border:1px solid #c00;
        box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 6px #d59392;
    }
    .form-input{
        width:100%;
        height:34px;
        padding:6px 12px;
        border:1px solid #ccc;
        border-radius:4px;
        font-size:14px;
        width:100%;
        background-color:#fff;
        transition:border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    }
    .textarea{
        height:auto;
    }
    .lineCaption{
        text-align:center;
        color:#999;
        font-size:18px;

        margin-top:20px;
        margin-bottom:20px;
        height: 1px;
        border-top: 1px solid #ddd;
    }
    .lineCaption span{
    position: relative;
    top: -8px;
    background: #fff;
    padding: 0 20px;
}
    </style>
</head>

<body>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container">
        	<%@ include file="/pages/navbarHeader.jsp" %>
            <!-- 手機隱藏選單區 -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <!-- 左選單 -->
                <ul class="nav navbar-nav">
					<li><a href="<%=request.getContextPath()%>/frontend/news/listOfAllNewsByStore.jsp" >最新消息</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/store/findStore.jsp">搜尋店家</a></li>
					<li class="active"><a href="<%=request.getContextPath()%>/frontend/spndcoffee/listAllSpndCoffee.jsp">寄杯</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/product/shop.jsp">購物</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/activity/activityList.jsp">活動</a></li>
				</ul>
                <!-- 右選單 -->
                <%@ include file="/pages/navbarRight.jsp" %>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
    <!-- nav end -->
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-sm-6 col-sm-offset-3">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="請輸入關鍵字">
                    <span class="input-group-btn">
                            <button class="btn btn-cofe" type="button">搜尋</button>
                        </span>
                </div>
            </div>
            
            
           <FORM METHOD="post" ACTION="<%= request.getContextPath() %>/frontend/spndcoffee/spndcoffee.do" name="form1" enctype="multipart/form-data">


            <div class="col-xs-12 col-sm-12">
                <ol class="breadcrumb">
                    <li>寄杯</li>
                    <li class="active">發起寄杯活動</li>
                    
                </ol>
            </div>
            <div class="col-xs-12 col-sm-12">
               
            </div>
            <div class="col-xs-12 col-sm-12">
                <form>
                    <div class="form-horizontal">
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-12">
                                    <input type="text" name="spnd_name" id="titleGroup" placeholder="請輸入寄杯活動名稱*" class="form-controls"value="<%= (spndcoffeeVO==null)? "大家一起來寄杯" : spndcoffeeVO.getSpnd_name()%>">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
                    <div class="col-xs-12 col-sm-3">
               
                <p class="text-right">
<!--                     <a href="activityAdded.html" class="btn btn-cofe"></a> -->
                    		<input type="hidden" name="action" value="insert">
							<input type="submit" value="儲存並發布">
                </p>
         
            </div>
            <div class="col-xs-12 col-sm-12">
                <figure class="figure"  style="width: 100%;">
                    
                        
                        <img src="<%= request.getContextPath() %>/img/spndcoffee11.jpg">
                        
                   
                  <a href="javascript:;" class="uploadimg a-upload">
						<input type="file" name="prod_img" id="upfile1"
						onchange="file_change()">上傳圖片
					</a>	
                </figure>
            </div> 
            
            
            <div class="background-gray eventInfo">


                <div class="container">
                    <div class="row">
                        <div class="col-xs-12 col-sm-6">
                            <div class="form-group">
                                
                                
                                   
                                    
                                
                            </div>
                            <div class="form-group">
                                <label for="aa" class="col-xs-12 col-sm-4 control-label" style="float: right;">
								
                                    <i class="glyphicon glyphicon-time"></i> 結束時間*
                                </label>

                                 <label for="aa" class="col-xs-12 col-sm-4 control-label" style="float: right;"> 
									寄杯杯數*
                                </label>
								
								<label for="aa" class="col-xs-12 col-sm-4 control-label" style="float: right;"> 
									寄杯商品*
                                </label>
                               
                               

                           	 	
                                <div class="row" >
                                    <div class="col-xs-12 col-sm-4" style="float: right;">
										<%java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());%>
										<input type="date" name="spnd_enddate"	id="aa" class="form-input error-input" value="<%=(spndcoffeeVO == null) ? date_SQL : spndcoffeeVO.getSpnd_enddate()%>">
              
                                    </div>
                                    <div class="col-xs-12 col-sm-4" style="float: right;">
                                        <input type="text" name="spnd_amt" id="aa" placeholder="輸入杯數" class="form-input error-input" value="<%= (spndcoffeeVO==null)? "100" : spndcoffeeVO.getSpnd_amt()%>">
                                    </div>
									<div class="col-xs-12 col-sm-4" style="float: right;">
                                        <input type="text" name="spnd_prod" id="aa" placeholder="輸入寄杯商品" class="form-input error-input" value="<%= (spndcoffeeVO==null)? "大杯摩卡" : spndcoffeeVO.getSpnd_prod()%>">
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="col-xs-12 col-sm-6">
                            <div class="form-group">
                                
                        
                                 
                            </div>
                            <div class="form-group">
                                <label for="aa" class="col-xs-12 col-sm-3 control-label" style="width: 100%">
                                    <i class="glyphicon glyphicon-map-marker"></i> 寄杯活動摘要*
                                </label>
                                <br>
                                <div class="row" >
                                    <div class="col-xs-12 col-sm-12" style="float: right;">
                                        <textarea placeholder="寫下不能錯過這場活動的理由，將會顯示在活動介紹頁中，最多可輸入150個中文字。" class="form-input error-input"></textarea>
                                    </div>
                                </div> 
                            </div>
                        </div>
                    </div>
                    <h3 class="lineCaption">
                            
                    </h3>
                </div>
            </div> 
        
        


		</FORM>
		</div>
        <footer class="footer text-center">
            <div class="col-xs-12 col-sm-3">
                常見問題
            </div>
            <div class="col-xs-12 col-sm-3">
                功能介紹
            </div>
            <div class="col-xs-12 col-sm-3">
                關於我們
            </div>
            <div class="col-xs-12 col-sm-3">
                客服中心
            </div>
            <center style="margin-top: 10px;">BA101第四組</center>
        </footer>
    </div>
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
