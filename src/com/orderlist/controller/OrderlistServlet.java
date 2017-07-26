package com.orderlist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.orderdetail.model.OrderdetailVO;
import com.orderlist.model.OrderlistService;
import com.orderlist.model.OrderlistVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;
import com.sylvie.sendmsg.Send;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class OrderlistServlet
 */
public class OrderlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		HttpSession session = req.getSession();
		
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
		
		// only for takeaway product : business logic difference & score_seller = -1
		if("insertTakeaway".equals(action)) {
			String mem_id = memberVO.getMem_id();
			String store_id = req.getParameter("store_id");
			Integer ord_pick = new Integer(req.getParameter("orderType"));
			String ord_add = ord_pick==3?req.getParameter("mem_add"):null;
			Integer ord_total = new Integer(req.getParameter("ord_total"));
			Map<String, String[]> product = req.getParameterMap();
			
			List<OrderdetailVO> orderdetailVOList = new ArrayList<OrderdetailVO>();
			
			for(Entry<String, String[]> entry : product.entrySet()) {
				OrderdetailVO orderdetailVO = new OrderdetailVO();
				if(entry.getValue().length>=4) {
					orderdetailVO.setProd_id(entry.getValue()[0]);
					orderdetailVO.setProd_name(entry.getValue()[1]);
					orderdetailVO.setProd_price(new Integer(entry.getValue()[3]));
					orderdetailVO.setDetail_amt(new Integer(entry.getValue()[2]));
					orderdetailVOList.add(orderdetailVO);
				}
			}
			
			OrderlistService orderlistSvc = new OrderlistService();
			orderlistSvc.insertWithDetail(mem_id, store_id, ord_total, ord_pick, ord_add, 1, new Timestamp(System.currentTimeMillis()), -1, orderdetailVOList);
			
			String url = req.getContextPath() + "/frontend/orderlist/order_mem.jsp";
			
			res.sendRedirect(url);
			return;
		}
		
		if("update".equals(action)) {
			String ord_id = req.getParameter("ord_id");
			
			OrderlistService orderlistSvc = new OrderlistService();
			OrderlistVO orderlistVO = orderlistSvc.findByPrimaryKey(ord_id);
			
			Integer ord_shipping = new Integer(req.getParameter("ord_shipping"));
			
			OrderlistVO ordlistVO;
			if(ord_shipping==5&&orderlistVO.getOrd_pick()==1) {
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.findByPrimaryKey(orderlistVO.getStore_id());
				storeVO.setStore_points(storeVO.getStore_points() + orderlistVO.getOrd_total());

				ordlistVO = orderlistSvc.updateWithStore(ord_id, orderlistVO.getMem_id(), orderlistVO.getStore_id(), orderlistVO.getOrd_total(), orderlistVO.getOrd_pick(), orderlistVO.getOrd_add(), ord_shipping, orderlistVO.getOrd_time(), orderlistVO.getScore_seller(), storeVO);
			} else {
				ordlistVO = orderlistSvc.update(ord_id, orderlistVO.getMem_id(), orderlistVO.getStore_id(), orderlistVO.getOrd_total(), orderlistVO.getOrd_pick(), orderlistVO.getOrd_add(), ord_shipping, orderlistVO.getOrd_time(), orderlistVO.getScore_seller());
			}
			
			if(ord_shipping==4&&(ordlistVO.getOrd_pick()==2||ordlistVO.getOrd_pick()==1)) {
				MemberService memberSvc = new MemberService();
				MemberVO memVO = memberSvc.findByPrimaryKey(ordlistVO.getMem_id());
				Send se = new Send();
				String[] tel ={memVO.getMem_tel()};
				String takeawayMsg = "親愛的" + memVO.getMem_name() + "您好！\n您的餐點已經完成，請至本店取餐，謝謝！";
				String deliveryMsg = "親愛的" + memVO.getMem_name() + "您好！\n您的貨品已經寄出，謝謝！";
				String message = ordlistVO.getOrd_pick()==2? takeawayMsg:deliveryMsg;
				se.sendMessage(tel , message);				
			}
			
			JSONArray array = new JSONArray();
						
				JSONObject obj = new JSONObject();
				try{
					obj.put("ord_id", ordlistVO.getOrd_id());
					obj.put("mem_id", ordlistVO.getMem_id());
					obj.put("ord_shipping", ordlistVO.getOrd_shipping());
					obj.put("ord_pick", ordlistVO.getOrd_pick());
					
				}catch(Exception e){}
				array.add(obj);
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		}
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 變更訂單狀態
		if ("shippingByAjax".equals(action)) {

			/*************************** 1.接收請求參數 ****************************************/
			String ord_id = req.getParameter("ord_id");
			Integer ord_shipping = new Integer(req.getParameter("ord_shipping"));

			/*************************** 開始查詢資料 ****************************************/
			OrderlistService ordSvc = new OrderlistService();
			OrderlistVO orderlistVO = ordSvc.getOneOrderlist(ord_id);

			String mem_id = orderlistVO.getMem_id();
			String store_id = orderlistVO.getStore_id();
			Integer ord_total = orderlistVO.getOrd_total();
			Integer ord_pick = orderlistVO.getOrd_pick();
			String ord_add = orderlistVO.getOrd_add();
			Timestamp ord_time = orderlistVO.getOrd_time();
			Integer score_seller = null;

			/*************************** 開始修改資料 *****************************************/
			if (ord_shipping != 5) {
				orderlistVO = ordSvc.updateOrderlist(ord_id, mem_id, store_id, ord_total, ord_pick, ord_add, ord_shipping, ord_time, score_seller);
			} else { // ord_shipping == 5
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_id);
				storeVO.setStore_points(storeVO.getStore_points() + ord_total);

				orderlistVO = ordSvc.updateWithStore(ord_id, mem_id, store_id, ord_total, ord_pick, ord_add, ord_shipping, ord_time, score_seller, storeVO);
			}
			return;
		} // if("shippingByAjax".equals(action))

		// 商品加入購物車
		if ("addCartByAjax".equals(action)) {

			JSONArray array = new JSONArray();
			JSONObject obj = new JSONObject();
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();

			boolean hasError = false;
			

			/*************************** 1.接收請求參數 ****************************************/
			String store_id = req.getParameter("store_id");
			String prod_id = req.getParameter("prod_id");

			ProductService prodSvc = new ProductService();
			ProductVO productVO = prodSvc.getOneProduct(prod_id);

			Integer prod_amt = productVO.getProd_amt();
			String prod_name = productVO.getProd_name();
			Integer prod_price = productVO.getProd_price();

			Integer detail_amt = null;
			try {
				detail_amt = new Integer(req.getParameter("detail_amt").trim());
			} catch (NumberFormatException e) {
				detail_amt = 1;
				hasError = true;
				obj.put("msg", "數量請填整數!!!");
			}
			if (detail_amt <= 0) {
				detail_amt = 1;
				hasError = true;
				obj.put("msg", "數量必須大於0!!!");
			} else if (detail_amt > prod_amt) {
				detail_amt = prod_amt;
				hasError = true;
				obj.put("msg", "庫存量不足!!!");
			}

			// Send the use back to the form, if there were errors
			if (hasError) {
				obj.put("type", "error");
				array.add(obj);

				out.write(array.toString());
				out.flush();
				out.close();
				return;
			}

			OrderdetailVO orderdetailVO = new OrderdetailVO();
			orderdetailVO.setProd_id(prod_id);
			orderdetailVO.setProd_name(prod_name);
			orderdetailVO.setProd_price(prod_price);
			orderdetailVO.setDetail_amt(detail_amt);

			Hashtable<String, Vector<OrderdetailVO>> shoppingcart = (Hashtable<String, Vector<OrderdetailVO>>)session.getAttribute("shoppingcart");
			Vector<OrderdetailVO> cartList = null;

			if (shoppingcart == null) {
				cartList = new Vector<OrderdetailVO>();
				cartList.add(orderdetailVO);

				shoppingcart = new Hashtable<String, Vector<OrderdetailVO>>();
				shoppingcart.put(store_id, cartList);

				session.setAttribute("shoppingcart", shoppingcart);
			} else {
				cartList = shoppingcart.get(store_id);

				if (cartList == null) {
					cartList = new Vector<OrderdetailVO>();
					cartList.add(orderdetailVO);

					shoppingcart.put(store_id, cartList);
				}
				else {
					boolean hasThisProduct = false;

					for (OrderdetailVO detail : cartList) {
						if (detail.getProd_id().equals(orderdetailVO.getProd_id())) {
							hasThisProduct = true;
							detail_amt = detail.getDetail_amt() + detail_amt;

							if (detail_amt > prod_amt) {
								detail_amt = prod_amt;

								obj.put("type", "error");
								obj.put("msg", "庫存量不足!!!");
								array.add(obj);

								out.write(array.toString());
								out.flush();
								out.close();
								return;
							}
							else {
								detail.setDetail_amt(detail_amt);
							}
							break;
						}
					}
					if (!hasThisProduct) {
						cartList.add(orderdetailVO);
					}
				}
			}
			obj.put("type", "success");
			obj.put("msg", "已將" + req.getParameter("detail_amt").trim() + "件商品加入購物車");
			array.add(obj);
			
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		} // if ("addCart".equals(action))

		// 移除購物車商品
		if("removeProductByAjax".equals(action)){
			
			String store_id = req.getParameter("store_id");
			String prod_id = req.getParameter("prod_id");
			Hashtable<String, Vector<OrderdetailVO>> shoppingcart = (Hashtable<String, Vector<OrderdetailVO>>)session.getAttribute("shoppingcart");
			Vector<OrderdetailVO> cartList = shoppingcart.get(store_id);
			for(OrderdetailVO detail : cartList){
				if(detail.getProd_id().equals(prod_id)){
					cartList.remove(detail);
					break;
				}
			}
			if(cartList.size() == 0){
				shoppingcart.remove(store_id);
			}
			return;
		} // if("removeProduct".equals(action))
		
		// 確認會員點數
		if ("checkPointsByAjax".equals(action)) { // 來自addProduct.jsp的請求

			JSONArray array = new JSONArray();
			
			Integer ord_total = new Integer(req.getParameter("ord_total"));
			
			String mem_id = memberVO.getMem_id();
			
			MemberService memSvc = new MemberService();
			memberVO = memSvc.getOneMember(mem_id);
			Integer mem_points = memberVO.getMem_points();

			JSONObject obj = new JSONObject();
			if(ord_total > mem_points){
				try{
					obj.put("type", "error");
					obj.put("points", ord_total - mem_points);
				}catch(Exception e){}
			}
			else{
				obj.put("type", "success");
			}
			array.add(obj);
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		} // if ("checkPointsByAjax".equals(action))
		
		// 從購物車頁面前往結帳頁面
		if ("getOne_For_Insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;
			
			try {
				String store_id = req.getParameter("store_id");
				String[] prod_ids = req.getParameterValues("prod_id");
				String[] detail_amts = req.getParameterValues("detail_amt");

				Hashtable<String, Vector<OrderdetailVO>> shoppingcart = (Hashtable<String, Vector<OrderdetailVO>>)session.getAttribute("shoppingcart");
				Vector<OrderdetailVO> cartList = shoppingcart.get(store_id);
				
				Integer ord_total = new Integer(0);
				for(OrderdetailVO detail : cartList){
					for(int i = 0; i < prod_ids.length; i++){
						if(detail.getProd_id().equals(prod_ids[i])){
							
							ProductService prodSvc = new ProductService();
							ProductVO productVO = prodSvc.getOneProduct(prod_ids[i]);
							Integer prod_amt = productVO.getProd_amt();
							
							Integer detail_amt = null;
							
							try {
								detail_amt = new Integer(detail_amts[i]);
							} catch (NumberFormatException e) {
								detail_amt = 1;
								errorMsgs.add("數量請填整數.");
							}
							if(detail_amt <= 0){
								detail_amt = 1;
								errorMsgs.add("數量必須大於0.");
							}
							else if(detail_amt > prod_amt){
								detail_amt = prod_amt;
								errorMsgs.add("庫存量不足.");
							}
							
							// Send the use back to the form, if there were errors
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req.getRequestDispatcher(url);
								failureView.forward(req, res);
								return;
							}
							
							detail.setDetail_amt(detail_amt);
							ord_total = ord_total + detail_amt * detail.getProd_price();
							break;
						}
					}

				}
				req.setAttribute("ord_total", ord_total);
				url = "/frontend/orderlist/checkout.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				throw new ServletException(e);
			}

		} // if ("getOne_For_Insert".equals(action))
		
		// 新增訂單
		if ("insert".equals(action)) { // 來自addProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = null;
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_id = memberVO.getMem_id();
				
				String store_id = req.getParameter("store_id");
				
				Hashtable<String, Vector<OrderdetailVO>> shoppingcart = (Hashtable<String, Vector<OrderdetailVO>>)session.getAttribute("shoppingcart");
				Vector<OrderdetailVO> cartList = shoppingcart.get(store_id);

				Integer ord_total = new Integer(req.getParameter("ord_total"));

				Integer ord_pick = new Integer(req.getParameter("ord_pick"));
				
				String ord_add = req.getParameter("ord_add").trim();
				if(ord_add.isEmpty()){
					ord_add = memberVO.getMem_add();
					errorMsgs.add("請輸入收件地址.");
				}

				Integer ord_shipping = new Integer(1);
				
				Timestamp ord_time = new Timestamp(System.currentTimeMillis());
				
				Integer score_seller = null;

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				MemberService memSvc = new MemberService();
				memberVO = memSvc.getOneMember(mem_id);

				Integer mem_points = memberVO.getMem_points();

				if(ord_total > mem_points){
					errorMsgs.add("餘額不足，尚差" + (ord_total - mem_points) + "元!!!");
					url = "/frontend/member/topUp.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				
				// 扣除庫存量
				Vector<ProductVO> productList = new Vector<ProductVO>();
				ProductService prodSvc = new ProductService();
				for(OrderdetailVO orderdetailVO : cartList){
					ProductVO productVO = prodSvc.getOneProduct(orderdetailVO.getProd_id());
					productVO.setProd_amt(productVO.getProd_amt() - orderdetailVO.getDetail_amt());
					
					productList.add(productVO);
				}
				
				// 扣款
				memberVO.setMem_points(mem_points - ord_total);
				
				OrderlistVO orderlistVO = new OrderlistVO();
				orderlistVO.setMem_id(mem_id);
				orderlistVO.setStore_id(store_id);
				orderlistVO.setOrd_total(ord_total);
				orderlistVO.setOrd_pick(ord_pick);
				orderlistVO.setOrd_add(ord_add);
				orderlistVO.setOrd_shipping(ord_shipping);
				orderlistVO.setOrd_time(ord_time);
				orderlistVO.setScore_seller(score_seller);
				
				/***************************2.開始新增資料***************************************/
				// 新增訂單
				OrderlistService ordSvc = new OrderlistService();
				orderlistVO = ordSvc.addOrderWithDetailAndCharge(mem_id, store_id, ord_total, ord_pick, ord_add, ord_shipping, ord_time, score_seller, cartList, productList, memberVO);

				shoppingcart.remove(store_id);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				url = "/frontend/orderlist/order_mem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProduct.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				return;
			}
		} // if ("insert".equals(action))
		
		// 查詢單筆訂單資訊
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ord_id = req.getParameter("ord_id");
				if (ord_id == null || (ord_id.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				OrderlistService ordSvc = new OrderlistService();
				OrderlistVO orderlistVO = ordSvc.getOneOrderlist(ord_id);
				if (orderlistVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("orderlistVO", orderlistVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/orderlist/listOneOrderlist.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		} // if ("getOne_For_Display".equals(action))
		
		// 查詢單筆訂單的訂單明細
		if ("listOrderdetailsByOrd_id".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
//				String prod_id = req.getParameter("prod_id");

				/*************************** 2.開始查詢資料 ****************************************/
//				ProductService prodSvc = new ProductService();
//				Set<MsgVO> set = prodSvc.getMsgsByProd_id(prod_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
//				req.setAttribute("listMsgs_ByProd_id", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listOrderdetailsByOrd_id".equals(action))
					url = "/frontend/orderlist/listOrderdetailsByOrd_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
//				else if ("listEmps_ByDeptno_B".equals(action))
//					url = "/dept/listAllDept.jsp";              // 成功轉交 dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} // if ("listOrderdetailsByOrd_id".equals(action))
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
	}

}
