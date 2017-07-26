<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.member.model.*"%>
<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO"); 
%>
<html>
<head>
<jsp:include page="/backend/resource.jsp" />
</head>
<body>
	<div class="wrapper" style="background-color: #FFF;">
		<!-- Content Header (Page header) -->
		<section class="content-header">
                <h1>
               		 維護一般會員資料
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
                                        <h3 class="box-title">一般會員資料</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body ">
                        <table class="table-ext table table-hover">
                            <tbody>
							<tr>	
								<th>會員編號</th>
								<th>帳號</th>
								<th>姓名</th>
								<th>電話</th>
								<th>Email</th>
								<th>地址</th>
								<th>點數</th>
								<th>大頭貼</th>
								<th>修改</th>
								<th>刪除</th>
							</tr>
							<tr align='center' valign='middle'>
								<td><%=memberVO.getMem_id()%></td>
								<td><%=memberVO.getMem_acct()%></td>
								<td><%=memberVO.getMem_name()%></td>
								<td><%=memberVO.getMem_tel()%></td>
								<td><%=memberVO.getMem_email()%></td>
								<td><%=memberVO.getMem_add()%></td>
								<td><%=memberVO.getMem_points()%></td>		
								<td><img src="<%=request.getContextPath()%>/images?action=member&id=${memberVO.mem_id}" width="230px" height="230px"/></td>
								<td>
									  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
									     <input type="submit" value="修改">
									     <input type="hidden" name="mem_id" value="${memberVO.mem_id}">
									     <input type="hidden" name="action"	value="getOne_For_Update">
									   </FORM>
								</td>
								<td>
									  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
									    <input type="submit" value="刪除">
									    <input type="hidden" name="mem_id" value="${memberVO.mem_id}">
									    <input type="hidden" name="action" value="delete">
									  </FORM>
								</td>
							</tr>
						</tbody>
                        </table>
                    </div>

                    <!-- end split buttons box -->
				</div>
			</div>
			<!-- Your Page Content Here -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>