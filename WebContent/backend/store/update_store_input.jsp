<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.store.model.*"%>
<%
	StoreVO storeVO = (StoreVO) request.getAttribute("storeVO");
%>
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
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
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		<section class="content-header">
                <h1>
               		 維護店家資料
            	</h1>
            </section>
            <!-- Main content -->
            <section class="content">
                <div class="col-sm-12 col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <div class="table-header-tools">
                                <div class="col-sm-2 col-xs-2">
                                    <div class="box-header">
                                        <h3 class="box-title">店家資料</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-header -->
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/store/store.do" name="form1" enctype="multipart/form-data">
					<table border="0">
						<tr>
							<td>店家帳號:</td>
							<td><%= storeVO.getStore_acct()%></td>
						</tr>
						<tr>
							<td>店名:</td>
							<td><input type="TEXT" name="store_name" size="45"
								value="<%= storeVO.getStore_name()%>" /></td>
						</tr>
						<tr>
							<td>電話:</td>
							<td><input type="TEXT" name="store_tel" size="45"
								value="<%= storeVO.getStore_tel()%>" /></td>
						</tr>
						<tr>
							<td>地址:</td>
							<td><input type="TEXT" name="store_add" size="45"
								value="<%= storeVO.getStore_add()%>" /></td>
						
						<tr>
							<td>Email:</td>
							<td><input type="TEXT" name="store_email" size="45"
								value="<%= storeVO.getStore_email()%>" /></td>
						</tr>
						<tr>
							<td>營業登記證:</td>
							<td><input type="TEXT" name="store_cpse" size="45" 
								value="<%= storeVO.getStore_cpse()%>" /></td>
						</tr>
						<tr>
							<td>外送數量門檻:</td>
							<td><input type="TEXT" name="min_order" size="45" 
								value="<%= storeVO.getMin_order()%>" /></td>
						</tr>
						<tr>
							<td>有無低消</td>
							<td><input type="radio" name="is_min_order" value="0" ${storeVO.is_min_order==null?'checked':(storeVO.is_min_order==0?'checked':'')}>無
								<input type="radio" name="is_min_order" value="1" ${storeVO.is_min_order==null?'':(storeVO.is_min_order==1?'checked':'')}>有
							</td>
							<td>有無WIFI:</td>
							<td><input type="radio" name="is_wifi" value="0" ${storeVO.is_wifi==null?'checked':(storeVO.is_wifi==0?'checked':'')}>無
								<input type="radio" name="is_wifi" value="1" ${storeVO.is_wifi==null?'':(storeVO.is_wifi==1?'checked':'')}>有
							</td>
						</tr>
						<tr>
							<td>有無插座:</td>
							<td><input type="radio" name="is_plug" value="0" ${storeVO.is_plug==null?'checked':(storeVO.is_plug==0?'checked':'')}>無
													<input type="radio" name="is_plug" value="1" ${storeVO.is_plug==null?'':(storeVO.is_plug==1?'checked':'')}>有
							</td>
							<td>有賣單品:</td>
							<td><input type="radio" name="is_single_orgn" value="0" ${storeVO.is_single_orgn==null?'checked':(storeVO.is_single_orgn==0?'checked':'')}>無
													<input type="radio" name="is_single_orgn" value="1" ${storeVO.is_single_orgn==null?'':(storeVO.is_single_orgn==1?'checked':'')}>有
							</td>
						</tr>
						<tr>
							<td>有賣甜點:</td>
							<td><input type="radio" name="is_dessert" value="0" ${storeVO.is_dessert==null?'checked':(storeVO.is_dessert==0?'checked':'')}>無
								<input type="radio" name="is_dessert" value="1" ${storeVO.is_dessert==null?'':(storeVO.is_dessert==1?'checked':'')}>有						
							</td>
							<td>有賣正餐:</td>
							<td><input type="radio" name="is_meal" value="0" ${storeVO.is_meal==null?'checked':(storeVO.is_meal==0?'checked':'')}>無
								<input type="radio" name="is_meal" value="1" ${storeVO.is_meal==null?'':(storeVO.is_meal==1?'checked':'')}>有						
							</td>
						</tr>
						<tr>
							<td>有無限時:</td>
							<td><input type="radio" name="is_time_limit" value="0" ${storeVO.is_time_limit==null?'checked':(storeVO.is_time_limit==0?'checked':'')}>無
								<input type="radio" name="is_time_limit" value="1" ${storeVO.is_time_limit==null?'':(storeVO.is_time_limit==1?'checked':'')}>有	
							</td>
						<tr>
							<td>一有無營業:</td>
							<td><input type="radio" name="mon_isopen" value="0" ${storeVO.mon_isopen==null?'checked':(storeVO.mon_isopen==0?'checked':'')}>無
													<input type="radio" name="mon_isopen" value="1" ${storeVO.mon_isopen==null?'':(storeVO.mon_isopen==1?'checked':'')}>有
							</td>
						</tr>
						<tr>
							<td>一營業時間起:</td>
							<td><input type="TEXT" name="mon_open" size="45" 
								value="<%=storeVO.getMon_open().toString().substring(11,16)%>" /></td>
							<td>一營業時間訖:</td>
							<td><input type="TEXT" name="mon_close" size="45" 
								value="<%= storeVO.getMon_close().toString().substring(11,16)%>" /></td>
						</tr>
							<tr>
							<td>二有無營業:</td>
							<td><input type="radio" name="tue_isopen" value="0" ${storeVO.tue_isopen==null?'checked':(storeVO.tue_isopen==0?'checked':'')}>無
								<input type="radio" name="tue_isopen" value="1" ${storeVO.tue_isopen==null?'':(storeVO.tue_isopen==1?'checked':'')}>有						
							</td>
						</tr>
							<tr>
							<td>二營業時間起:</td>
							<td><input type="TEXT" name="tue_open" size="45" 
								value="<%= storeVO.getTue_open().toString().substring(11,16)%>" /></td>
							<td>二營業時間訖:</td>
							<td><input type="TEXT" name="tue_close" size="45" 
								value="<%= storeVO.getTue_close().toString().substring(11,16)%>" /></td>
						</tr>
							<tr>
							<td>三有無營業:</td>
							<td><input type="radio" name="wed_isopen" value="0" ${storeVO.wed_isopen==null?'checked':(storeVO.wed_isopen==0?'checked':'')}>無
								<input type="radio" name="wed_isopen" value="1" ${storeVO.wed_isopen==null?'':(storeVO.wed_isopen==1?'checked':'')}>有					
							</td>
						</tr>
							<tr>
							<td>三營業時間起:</td>
							<td><input type="TEXT" name="wed_open" size="45" 
								value="<%= storeVO.getWed_open().toString().substring(11,16)%>" /></td>
							<td>三營業時間訖:</td>
							<td><input type="TEXT" name="wed_close" size="45" 
								value="<%= storeVO.getWed_close().toString().substring(11,16)%>" /></td>
						</tr>
							<tr>
							<td>四有無營業:</td>
							<td><input type="radio" name="thu_isopen" value="0" ${storeVO.thu_isopen==null?'checked':(storeVO.thu_isopen==0?'checked':'')}>無
								<input type="radio" name="thu_isopen" value="1" ${storeVO.thu_isopen==null?'':(storeVO.thu_isopen==1?'checked':'')}>有												
							</td>
						</tr>
							<tr>
							<td>四營業時間起:</td>
							<td><input type="TEXT" name="thu_open" size="45" 
								value="<%= storeVO.getThu_open().toString().substring(11,16)%>" /></td>
							<td>四營業時間訖:</td>
							<td><input type="TEXT" name="thu_close" size="45" 
								value="<%= storeVO.getThu_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<td>五有無營業:</td>
							<td><input type="radio" name="fri_isopen" value="0" ${storeVO.fri_isopen==null?'checked':(storeVO.fri_isopen==0?'checked':'')}>無
								<input type="radio" name="fri_isopen" value="1" ${storeVO.fri_isopen==null?'':(storeVO.fri_isopen==1?'checked':'')}>有
							</td>
						</tr>
							<tr>
							<td>五營業時間起:</td>
							<td><input type="TEXT" name="fri_open" size="45" 
								value="<%= storeVO.getFri_open().toString().substring(11,16)%>" /></td>
							<td>五營業時間訖:</td>
							<td><input type="TEXT" name="fri_close" size="45" 
								value="<%= storeVO.getFri_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<td>六有無營業:</td>
							<td><input type="radio" name="sat_isopen" value="0" ${storeVO.sat_isopen==null?'checked':(storeVO.sat_isopen==0?'checked':'')}>無
								<input type="radio" name="sat_isopen" value="1" ${storeVO.sat_isopen==null?'':(storeVO.sat_isopen==1?'checked':'')}>有												
							</td>
						</tr>
						<tr>
							<td>六營業時間起:</td>
							<td><input type="TEXT" name="sat_open" size="45" 
								value="<%= storeVO.getSat_open().toString().substring(11,16)%>" /></td>
							<td>六營業時間訖:</td>
							<td><input type="TEXT" name="sat_close" size="45" 
								value="<%= storeVO.getSat_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<td>日有無營業:</td>
							<td><input type="radio" name="sun_isopen" value="0" ${storeVO.sun_isopen==null?'checked':(storeVO.sun_isopen==0?'checked':'')}>無
								<input type="radio" name="sun_isopen" value="1" ${storeVO.sun_isopen==null?'':(storeVO.sun_isopen==1?'checked':'')}>有												
							</td>
						</tr>
						<tr>
							<td>日營業時間起:</td>
							<td><input type="TEXT" name="sun_open" size="45" 
								value="<%=storeVO.getSun_open().toString().substring(11,16)%>" /></td>
							<td>日營業時間訖:</td>
							<td><input type="TEXT" name="sun_close" size="45" 
								value="<%= storeVO.getSun_close().toString().substring(11,16)%>" /></td>
						</tr>
						<tr>
							<td>上傳照片:</td>
					     	<td>  	 
							    <input type="file" name="upfile1">
							<br/>
							</td>
						</tr>
					</table>
					<br>
					<input type="hidden" name="store_id" value="<%= storeVO.getStore_id()%>">
					<input type="hidden" name="action" value="update">
					<input type="submit" value="送出修改">
					</FORM>

				<!-- end split buttons box -->
				</div>
			</div>
			<!-- Your Page Content Here -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>