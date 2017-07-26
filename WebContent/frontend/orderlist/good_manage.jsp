<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ include file="/pages/isVisitor.jsp" %>

<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:useBean id="base" class="com.sylvie.picture.BaseChanger" />
<jsp:useBean id="categorySvc" scope="page" class="com.category.model.CategoryService" />
<jsp:useBean id="storeSvc" scope="page"	class="com.store.model.StoreService" />

<c:set value="${STORE.store_id}" var="store_id" />						

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" href="${context}/css/normal.css">
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
				<li><a href="<%=request.getContextPath()%>/frontend/spndcoffee/listAllSpndCoffee.jsp">寄杯</a></li>
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
			<div class="col-xs-12 col-sm-8 col-sm-offset-2">

				
				<div class="my-3 row text-center"></div>

			</div>
			<div class="col-xs-12">
				<hr>
			</div>
		</div>
		<!-- row end -->
	</div>
	<!-- container end -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-8 col-lg-offset-2">
				<div class="clearfix">
				<div class="pull-left">
				<ul id="myTab" class="nav nav-tabs">
				     <li class=""><a href="#takeaway" data-toggle="tab">外帶外送</a></li>
				     <li class=""><a href="#shopping" data-toggle="tab">購物商品</a></li>				     				          
				</ul></div>
					<button id="addNewProd" class="btn btn-sec btn-sm pull-right" type="submit"><i class="glyphicon glyphicon-plus"></i> 新增商品</button>			
				</div>
				<div class="tab-content clearfix">
					<%/* 1st PANEL */ %>
					<div id="takeaway" class="tab-pane">
						<div class="mainDiv">
						<div id="myItems" >
						<c:set value="${storeSvc.getProductListByStore_idAndCateDESC(store_id, 2)}" var="product2VOs" scope="page"/>
						<display:table id="product2VO" name="pageScope.product2VOs"
							class="table" pagesize="8" excludedParams="d-3697406-p prod_amt prod_desc prod_category prod_price action cate_id prod_img prod_name prod_id"
							cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/orderlist/good_manage.jsp" export="false"
							partialList="true" size="${product2VOs.size()}">
							<jsp:setProperty name="base" property="imgByte"	value="${product2VO.prod_img}" />
							<c:set value="${product2VO.cate_id}" var="cat_id" />
							<c:set value="${categorySvc.findByPrimaryKey(cat_id)}" var="categoryVO" />
							<display:column title="圖片" class="col-lg-2">
								<span id="tr_${product2VO.prod_id}"><img class="img pull-left" src="data:image/jpeg;base64, ${base.baseStr}" height="120"></span>
							</display:column>
							<display:column title="商品名稱" class="col-lg-2 .name">
								<span><c:out value="${product2VO.prod_name}" /></span>
							</display:column>
							<display:column title="商品描述" class="col-lg-3">
								<span><c:out value="${product2VO.prod_desc}" /></span>
							</display:column>		
							<display:column title="商品類別" class="col-lg-1">
								<span><c:out value="${categoryVO.cate_name}" /></span>
							</display:column>		
							<display:column title="單價" class="col-lg-1">
								<span><c:out value="${product2VO.prod_price}" /></span>
							</display:column>		
							<display:column title="上下架" class="col-lg-1">
								<span><button id="${product2VO.prod_id}" class="btn ${product2VO.prod_launch==1?'btn-sub':'btn-normal'} btn-sm switchLauch" type="submit">
											<i class="glyphicon ${product2VO.prod_launch==1?'glyphicon-arrow-down':'glyphicon-arrow-up'}"></i><c:out value="${product2VO.prod_launch==1?' 下架':' 上架'}" />
										</button>
										<input type='hidden' value='${product2VO.prod_launch}'>
								</span>
							</display:column>		
							<display:column title="修改" class="col-lg-1">
								<span><button id="A_${product2VO.prod_id}" class="btn btn-sec btn-sm editProd" type="submit">
											<i class="glyphicon glyphicon-pencil"></i>
										</button>
								</span>
							</display:column>		
						</display:table>	
							</div>	
						</div>
					</div>
					

					<%/* 2nd PANEL */ %>
					<div id="shopping" class="tab-pane">
						<div class="mainDiv">
						<div id="myItems">
							<c:set value="${storeSvc.getProductListByStore_idAndCateDESC(store_id,1)}" var="product1VOs" scope="page"/>
							<display:table id="productVO" name="pageScope.product1VOs"
							class="table" pagesize="8" excludedParams="d-2703386-p prod_amt prod_desc prod_category prod_price action cate_id prod_img prod_name prod_id"
							cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/orderlist/good_manage.jsp" export="false"
							partialList="true" size="${product1VOs.size()}">
							<jsp:setProperty name="base" property="imgByte"	value="${productVO.prod_img}" />
							<c:set value="${productVO.cate_id}" var="cat_id" />
							<c:set value="${categorySvc.findByPrimaryKey(cat_id)}" var="categoryVO" />
							<display:column title="圖片" class="col-lg-2">
								<span id="tr_${productVO.prod_id}"><img class="img pull-left" src="data:image/jpeg;base64, ${base.baseStr}" height="120"></span>
							</display:column>
							<display:column title="商品名稱" class="col-lg-2 .name">
								<span><c:out value="${productVO.prod_name}" /></span>
							</display:column>
							<display:column title="商品描述" class="col-lg-3">
								<span><c:out value="${productVO.prod_desc}" /></span>
							</display:column>		
							<display:column title="商品類別" class="col-lg-1">
								<span><c:out value="${categoryVO.cate_name}" /></span>
							</display:column>		
							<display:column title="單價" class="col-lg-1">
								<span><c:out value="${productVO.prod_price}" /></span>
							</display:column>		
							<display:column title="庫存" class="col-lg-1">
								<span><c:out value="${productVO.prod_amt}" /></span>
							</display:column>		
							<display:column title="上下架" class="col-lg-1">
								<span><button id="${productVO.prod_id}" class="btn ${productVO.prod_launch==1?'btn-sub':'btn-normal'} btn-sm switchLauch" type="submit">
											<i class="glyphicon ${productVO.prod_launch==1?'glyphicon-arrow-down':'glyphicon-arrow-up'}"></i><c:out value="${productVO.prod_launch==1?' 下架':' 上架'}" />
										</button>
										<input type='hidden' value='${productVO.prod_launch}'>
								</span>
							</display:column>		
							<display:column title="修改" class="col-lg-1">
								<span><button id="A_${productVO.prod_id}" class="btn btn-sec btn-sm editProd" type="submit">
											<i class="glyphicon glyphicon-pencil"></i>
										</button>
								</span>
							</display:column>		
						</display:table>
							</div>
						</div>
					</div><%/* end panel */%>
				</div>
			</div><%/* end column */%>
		</div>
		<!-- row end -->
	</div>
	<!-- container-fluid end -->


	<div id="light" class="white_content">
		<form id="divForm" method="post" action="${context}/frontend/product/product.do">
			<div class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label">商品名稱</label>
					<div class="col-sm-9">
						<input id="prodName" type="text" class="form-control" name="prod_name">
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">商品類型</label>
					<div class="col-sm-9">
						<select id="selectProdType" class="" name="prod_category">
							<option value="">請選擇</option>
							<option value="1">購物商品</option>
							<option value="2">外帶外送</option>
						</select> <select id="selectCateType" class="" name="cate_id">
							<option value="">請選擇</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">單價</label>
					<div class="col-sm-9">
						<input id="prodPrice" type="text" class="form-control" name="prod_price">
					</div>
				</div>

				<div id="storage" class="form-group">
					<label class="col-sm-2 control-label">庫存</label>
					<div class="col-sm-9">
						<input id="prodStorage" type="text" class="form-control" name="prod_amt">
					</div>
				</div>
				
				<div id="" class="form-group">
					<label class="col-sm-2 control-label">商品描述</label>
					<div class="col-sm-9">
						<textarea class="form-control" rows="5" id="comment" name="prod_desc"></textarea>
					</div>
				</div>
				
				<div class="form-group" id="dropZone">
					<label class="col-sm-2 control-label">商品圖片</label>
					<div class="col-sm-9">
						<p style="border: 1px solid #ccc; width: 100%; height: 250px; border-radius: 5px">
							<img id="image" style="height: 250px"> <input id="imgg"
								type="hidden" name="prod_img" value="">
						</p>
					</div>
				</div>
				<div id="errorBox">
				</div>
			</div>

			<p class="text-center">
				<button id="updBtn" type="submit" class="btn btn-normal">
					<i class="glyphicon glyphicon-ok-sign"></i> 確認
				</button>
				<input id="switchInput" type="hidden" name="action" value="insert">
				<input id="prodId" type="hidden" name="prod_id" value="">
				<a href="#" id="closeBtn" class="btn btn-sub"><i class="glyphicon glyphicon-remove-sign"></i> 取消</a>
			</p>
		</form>
	</div>
	<div id="fade" class="black_overlay"></div>


	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
			
			function openMask() {
				$("#light").show();
				$("#fade").show();
			}
			function closeMask() {
				$("#light").hide();
				$("#fade").hide();
			}
			
			function showErrorMsg() {
				var errorMsg = "<div style='color:red'>請修正以下錯誤:</div>" +
								"<ul>" +
								<c:forEach var="message" items="${errorMsgs}">
								"<li>${message}</li>" +
								</c:forEach>
								"</ul>";								
				$('#errorBox').html(errorMsg);							
			}
			
			function ifErrorExist() {
				<c:if test="${not empty errorMsgs}">
					$("#addNewProd").click();				
					$('#prodName').val('<c:out value="${param.prod_name}" />');
					$('#prodPrice').val('<c:out value="${param.prod_price}" />');
					$('#prodStorage').val('<c:out value="${param.prod_amt}" />');
					$('#selectProdType').val('<c:out value="${param.prod_category}" />');
					$('#selectCateType').val('<c:out value="${param.cate_id}" />');
					$('#divForm textarea').val('<c:out value="${param.prod_desc}" />');
					$('#divForm img').attr('src','');
					<c:if test="${not empty param.prod_img}">
						$('#divForm img').attr('src', 'data:image/jpeg;base64,<c:out value="${param.prod_img}" />');
					</c:if>
					$('#imgg').val('<c:out value="${param.prod_img}" />');
					showErrorMsg();
				</c:if>
			}
			
			function ifSecondTab() {
				var attr = $(location).attr('search');
				var secondPage = "d-3697406-p";
				
				if (attr.indexOf(secondPage) != -1){	
					$("#takeaway").removeClass("fade active in");
					$("#shopping").addClass("fade active in");
					$("#myTab li:nth-child(1)").removeClass("active");
					$("#myTab li:nth-child(2)").addClass("active");
				}else {
					$("#shopping").removeClass("fade active in");
					$("#takeaway").addClass("fade active in");
					$("#myTab li:nth-child(2)").removeClass("active");
					$("#myTab li:nth-child(1)").addClass("active");
				}
			
			}

		$(document).ready(function() {

			$('.viewDetail').click(function() {
				var btnObj = $(this);
				var detailDiv = btnObj.attr('data-target');
				$.ajax({type : "POST",
						url : "${context}/frontend/orderdetail/orderdetail.do",
						data : {"action" : "findProductsByOrdId",
								"ord_id" : $(this).closest('tr').find('td:nth-child(1) span').attr('id').substring(3),
								"ord_add" : $(this).closest('tr').find('.ord_add').val()
								},
						dataType : "json",
						success : function(data) {
									selectCreate(data, detailDiv, btnObj);
									btnObj.off('click');
								},
						error : function() {alert("error")}
						})
			})

			$("#addNewProd").click(function() {
				
				console.log('off click事件');
				$('#updBtn').off('click');
				openMask();

				$('#updBtn').attr('type', 'submit');
				
				$('#switchInput').attr('value', 'insert');
				$('#prodId').attr('value', '');

				$('#prodName').val('');
				$('#selectProdType').val('');
				$('#selectCateType').val('');
				$('#prodPrice').val('');
				$('#prodStorage').val('');
				$('textarea').val('');
				$('#image').attr('src', null);
				$('#imgg').val('');
				
			})
			
			ifErrorExist();
			ifSecondTab();
			
			$("#closeBtn").click(function() {
				closeMask();
				$('#errorBox').empty();
			})

			document.getElementById('dropZone').ondragover = dragOver;
			document.getElementById('dropZone').ondrop = dropped;
			

			$('#selectProdType').change(function() {
				$.ajax({
						type : "POST",
						url : "${context}/frontend/category/category.do",
						data : {"action" : "findCateByProd_cate",
								"prod_category" : $(this).val()
								},
						dataType : "json",
						async : false,
						success : function(data) {
								selectCate(data, 'selectCateType');
						},
						error : function() {
								alert("error")
						}
				})
				$(this).val() == 2?$('#storage').css('display', 'none'):$('#storage').css('display', 'block');								
			})

			$('.switchLauch').click(function() {
				var currStatus = $(this).parent().find('input').val();
				console.log("old : " + currStatus);
				var switchStatus = currStatus == 1? 0 : 1;
				var bt = $(this).attr('id');
				console.log(bt);
				$.ajax({type : "POST",
						url : "${context}/frontend/product/product.do",
						data : {"action" : "update",
								"prod_launch" : switchStatus,
								"prod_id" : $(this).attr('id')
								},
						dataType : "json",
						success : function(data) {
									if(data[0].prod_launch == 1) {
										$('#' + bt).html("<i class='glyphicon glyphicon-arrow-down'></i> 下架");
										$('#' + bt).removeClass("btn-normal").addClass("btn-sub");
									} else {
										$('#' + bt).html("<i class='glyphicon glyphicon-arrow-up'></i> 上架");
										$('#' + bt).removeClass("btn-sub").addClass("btn-normal");
									}
									$('#' + bt).next().attr('value',data[0].prod_launch);
								},
						error : function() {alert("error")}
				})
			})

			$('.editProd').click(function(){
				openMask();								
				var prodId = $(this).attr('id').substring(2);
				console.log(prodId);
				$.ajax({type : "POST",
						url : "${context}/frontend/product/product.do",
						data : {"action" : "findByPrimaryKey",
								"prod_id" : $(this).attr('id').substring(2)
								},
						dataType : "json",
						success : function(data){$('#updBtn').attr('type','button');
									$('#switchInput').attr('value','update');
									$('#prodId').attr('value',prodId);
									$('#prodName').val(data[0].prod_name);
									$('#selectProdType').val(data[0].prod_category).change();
									$('#selectCateType').val(data[0].cate_id);	
									$('#prodPrice').val(data[0].prod_price);
									data[0].prod_category=='1'?$('#prodStorage').val(data[0].prod_amt):$('#prodStorage').val('');
									$('textarea').val(data[0].prod_desc);
									$('#image').attr('src','data:image/jpeg;base64,'+ data[0].prod_img);
									$('#imgg').attr('value',data[0].prod_img);
									console.log('on click事件');
									$('#updBtn').on('click',{prodId: prodId},update);
								},
						error : function() {alert("error")}
					})
					console.log('off click事件');
					$('#updBtn').off('click');
			})

	});
		
		function update(event) {
			var prodId = event.data.prodId;
			console.log('update ' + prodId);
			$.ajax({type : "POST",
				url : "${context}/frontend/product/product.do",
				data : $("#divForm").serialize(),
				dataType : "json",
				success : function(data) {
							console.log('after success : off click事件');
							$('#updBtn').off('click');
							var tr = $('span[id=tr_'+ prodId+ ']').closest('tr');
							tr.find('td:nth-child(1) img').attr('src','data:image/jpeg;base64,'+ data[0].prod_img);
							tr.find('td:nth-child(2) span').text(data[0].prod_name);
							tr.closest('tr').find('td:nth-child(3) span').text(data[0].prod_desc);
							tr.closest('tr').find('td:nth-child(4) span').text(data[0].cate_name);
							tr.closest('tr').find('td:nth-child(5) span').text(data[0].prod_price);
							closeMask();
						},
				error : function() {alert("error")}		
			})
		}
		

		function selectCreate(oJson, target, thisObj) {
			var ordId = target.substring(7);
			$(target).empty();

			$(target).html("<table class='table'>"
							+ "<thead><tr><th>商品名稱</th><th>單價</th><th>數量</th><th>小計</th></tr></thead>"
							+ "<tbody id='detailBody" + ordId + "'></tbody>"
							+ "</table>");

			var i = 0;
			$.each(oJson, function() {
				$('#detailBody'+ ordId).append(
						 "<tr><td>" + oJson[i].prod_name + "</td><td>"
									+ oJson[i].prod_price + "</td><td>"
									+ oJson[i].detail_amt + "</td><td>"
									+ oJson[i].detail_amt * oJson[i].prod_price
									+ "</td></tr>");

					if(i==oJson.length-1&&oJson[i].ord_add!="") {						
						$('#detailBody'+ ordId).append("<tr><td colspan='6'><i class='glyphicon glyphicon-map-marker'></i> 外送地址："+ oJson[i].ord_add + "</td></tr>");
					}

				i++;
			});
		}

		function selectCate(oJson, selectCateType) {
			$('#' + selectCateType).empty();
			var i = 0;
			$.each(oJson, function() {
				$('#' + selectCateType).append("<option value='"+oJson[i].cate_id+"'>"+ oJson[i].cate_name + "</option>");
				i++;
			});
		}

		function dragOver(e) {
			e.preventDefault();
		}
		function dropped(e) {
			e.preventDefault();

			var file = e.dataTransfer.files[0];

			var readFile = new FileReader();
			readFile.readAsDataURL(file);
			readFile.addEventListener('load', function() {
				var image = document.getElementById('image');
				image.src = this.result;
				$('#image').parent().find('input').attr('value',
						this.result.substring(23));
			}, false);

		}
    
</script>
</body>
</html>