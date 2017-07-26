package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store.model.StoreVO;
import com.tools.Pic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ProductServlet extends HttpServlet {
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
		StoreVO storeVO = (StoreVO)session.getAttribute("STORE");
		
		if("insert".equals(action)) {
			String store_id = storeVO.getStore_id();
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String prod_name = req.getParameter("prod_name").trim();
				
				if (prod_name == null || (prod_name.trim()).length() == 0) {
					errorMsgs.put("prod_name","請輸入商品名稱");
				}
				
				String cate_id = req.getParameter("cate_id").trim();
				if (cate_id == null || (cate_id.trim()).length() == 0) {
					errorMsgs.put("cate_id","請選擇商品類別");
				}
				
				Integer prod_price = null;				
				try {
					prod_price = new Integer(req.getParameter("prod_price").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("prod_price","商品價格格式不符");
				}
				
				Integer prod_category = null;
				Integer prod_amt = null;
				if(req.getParameter("prod_category")==null||req.getParameter("prod_category").trim().length()==0) {
					errorMsgs.put("prod_category","請選擇商品購物類型");
				} else {
					prod_category = new Integer(req.getParameter("prod_category").trim());					
					if (prod_category == 2) {
						prod_amt = -1;
					} else {
						try {
							prod_amt = new Integer(req.getParameter("prod_amt").trim());
						} catch (NumberFormatException e) {
							errorMsgs.put("prod_amt","商品庫存格式不符");
						}					
					}
				}
				
				String prod_img = req.getParameter("prod_img");
				byte[] decoded = null;
				if (prod_img == null || (prod_img.trim()).length() == 0) {
					errorMsgs.put("prod_img","請上傳商品圖片");
				} else {					
					decoded = Base64.getDecoder().decode(prod_img);
				}
				
				String prod_desc = req.getParameter("prod_desc").trim();
				
				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/order_owner.jsp");
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/good_manage.jsp");
					failureView.forward(req, res);
					return;
				}
				
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.insert(store_id, prod_name, cate_id, prod_price, prod_category, decoded, prod_amt, 0, prod_desc);
							
//				String url = req.getContextPath() + "/frontend/orderlist/order_owner.jsp";
				String url = req.getContextPath() + "/frontend/orderlist/good_manage.jsp";
				
				res.sendRedirect(url);
				return;
				
			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/order_owner.jsp");
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/good_manage.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		if("update".equals(action)) {
			String store_id = storeVO.getStore_id();
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String prod_id = req.getParameter("prod_id");
							
				ProductService productSvc = new ProductService();
				CategoryService categorySvc = new CategoryService();
				ProductVO productVO = productSvc.findByPrimaryKey(prod_id);
				
				String prod_name = req.getParameter("prod_name");
				if (prod_name == null) {
					prod_name = productVO.getProd_name();
				}
				if((prod_name.trim()).length() == 0){
					errorMsgs.put("prod_name","請輸入商品名稱");					
				}
				
				
				String cate_id = req.getParameter("cate_id");
				if (cate_id == null) {
					cate_id = productVO.getCate_id();
				}
				if((cate_id.trim()).length() == 0){
					errorMsgs.put("cate_id","請選擇商品類別");					
				} 
								

				Integer prod_price = null;				
				if (req.getParameter("prod_price") == null) {
					prod_price = productVO.getProd_price();
				} else{
					try {
						prod_price = new Integer(req.getParameter("prod_price").trim());
					} catch (NumberFormatException e) {
						errorMsgs.put("prod_price","商品價格格式不符");
					}										
				}
				
				Integer prod_category = null;
				if(req.getParameter("prod_category")==null) {
					prod_category = productVO.getProd_category();
				}else{
					try {
						prod_category = new Integer(req.getParameter("prod_category").trim());
					} catch (NumberFormatException e) {
						errorMsgs.put("prod_category","請選擇商品購物類型");
					}	
				}
						
				byte[] decoded;
				if(req.getParameter("prod_img")!=null){
					String prod_img = req.getParameter("prod_img");
					decoded = Base64.getDecoder().decode(prod_img);
				} else {
					decoded = productVO.getProd_img();
				}
								
				Integer prod_launch = req.getParameter("prod_launch")!=null?new Integer(req.getParameter("prod_launch")):productVO.getProd_launch();
				
				Integer prod_amt = null;
				if(prod_category == 2&& (req.getParameter("prod_amt")==null||req.getParameter("prod_amt").trim()=="")) {
					prod_amt=-1;
				} else if (prod_category == 1&&req.getParameter("prod_amt")==null||req.getParameter("prod_amt").trim()=="") {
					prod_amt = productVO.getProd_amt();
				}else{
					try {
						prod_amt = new Integer(req.getParameter("prod_amt"));
					} catch (NumberFormatException e) {
						errorMsgs.put("prod_amt","商品庫存格式不符");
					}	
				}
				
				String prod_desc = req.getParameter("prod_desc")!=null?req.getParameter("prod_desc"):productVO.getProd_desc();
				
				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/order_owner.jsp");
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/good_manage.jsp");
					failureView.forward(req, res);
					return;
				}
				
				ProductVO prodVO = productSvc.update(prod_id, store_id, prod_name, cate_id, prod_price, prod_category, decoded, prod_amt, prod_launch, prod_desc);
				
				
				JSONArray array = new JSONArray();
							
					JSONObject obj = new JSONObject();
					try{
						obj.put("prod_id", prodVO.getProd_id());
						obj.put("store_id", prodVO.getStore_id());
						obj.put("prod_name", prodVO.getProd_name());
						obj.put("cate_id", prodVO.getCate_id());
						obj.put("cate_name", categorySvc.findByPrimaryKey(prodVO.getCate_id()).getCate_name());
						obj.put("prod_desc", prodVO.getProd_desc());
						obj.put("prod_price", prodVO.getProd_price());
						obj.put("prod_category", prodVO.getProd_category());
						obj.put("prod_img", Base64.getEncoder().encodeToString(prodVO.getProd_img()));
						obj.put("prod_amt", prodVO.getProd_amt());
						obj.put("prod_launch", prodVO.getProd_launch());
					}catch(Exception e){}
					array.add(obj);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(array.toString());
				out.flush();
				out.close();
				return;
			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/order_owner.jsp");
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/orderlist/good_manage.jsp");
				failureView.forward(req, res);
				return;
			}
			
		}
		
		if("findByPrimaryKey".equals(action)) {
			String prod_id = req.getParameter("prod_id");
						
			ProductService productSvc = new ProductService();
			CategoryService categorySvc = new CategoryService();
			ProductVO productVO = productSvc.findByPrimaryKey(prod_id);
			String encoded = Base64.getEncoder().encodeToString(productVO.getProd_img());
			CategoryVO categoryVO = categorySvc.findByPrimaryKey(productVO.getCate_id());
			JSONArray array = new JSONArray();			
			JSONObject obj = new JSONObject();
			try{
				obj.put("prod_id", productVO.getProd_id());
				obj.put("store_id", productVO.getStore_id());
				obj.put("prod_name", productVO.getProd_name());
				obj.put("cate_id", productVO.getCate_id());
				obj.put("prod_price", productVO.getProd_price());
				obj.put("prod_category", productVO.getProd_category());
				obj.put("prod_img", encoded);
				obj.put("prod_amt", productVO.getProd_amt());
				obj.put("prod_launch", productVO.getProd_launch());
				obj.put("prod_desc", productVO.getProd_desc());
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
		// 商品上下架
		if ("launchedByAjax".equals(action)) {

			String store_id = storeVO.getStore_id();

			/*************************** 1.接收請求參數 ****************************************/
			String prod_id = req.getParameter("prod_id");

			/*************************** 開始查詢資料 *****************************************/
			ProductService prodSvc = new ProductService();
			ProductVO productVO = prodSvc.getOneProduct(prod_id);

			String prod_name = productVO.getProd_name();
			String cate_id = productVO.getCate_id();
			Integer prod_price = productVO.getProd_price();
			Integer prod_category = productVO.getProd_category();
			byte[] prod_img = productVO.getProd_img();
			Integer prod_amt = productVO.getProd_amt();

			Integer prod_launch = productVO.getProd_launch();
			prod_launch = (prod_launch == 1) ? 0 : 1;
			
			String prod_desc = productVO.getProd_desc();

			/*************************** 開始修改資料 *****************************************/
			productVO = prodSvc.updateProduct(prod_id, store_id, prod_name, cate_id, prod_price, prod_category, prod_img, prod_amt, prod_launch, prod_desc);
			return;
		} // if("launchedByAjax".equals(action))

		// 搜尋單一購物類型的商品名稱
		if ("searchLaunchedByProd_nameAndProd_category".equals(action)) {
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String[] search = (String[])session.getAttribute("searchLaunchedByProd_name");
				Integer prod_category = (Integer)session.getAttribute("searchLaunchedByProd_category");

				if (req.getParameter("whichPage") == null){
					String prod_name = req.getParameter("prod_name");
					String upper = prod_name.toUpperCase();

					search = upper.split(" ");
					prod_category = new Integer(req.getParameter("prod_category"));
					
					session.setAttribute("searchLaunchedByProd_name", search);
					session.setAttribute("searchLaunchedByProd_category", prod_category);
				}

				/*************************** 2.開始查詢資料 ****************************************/
				ProductService prodSvc = new ProductService();
				List<ProductVO> list = prodSvc.searchLaunchedByProd_categoryAndProd_name(prod_category, search);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("searchLaunchedByProd_nameAndProd_category", list); // 資料庫取出的productVO物件,存入req
				String url = "/frontend/product/searchProducts.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/product/shop.jsp");
				failureView.forward(req, res);
				return;
			}

		} // if ("searchLaunchedByProd_nameAndProd_category".equals(action))
		
		// 跳往商品修改頁面
		if ("getOne_For_Update".equals(action)) { // 來自listAllProduct.jsp 或 /dept/listEmps_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或 【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String prod_id = req.getParameter("prod_id");

				/*************************** 2.開始查詢資料 ****************************************/
				ProductService prodSvc = new ProductService();
				ProductVO productVO = prodSvc.getOneProduct(prod_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/frontend/product/update_product_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				return;
			}
		} // if ("getOne_For_Update".equals(action))

		// 商品資訊修改
		if ("update".equals(action)) { // 來自update_product_input.jsp的請求

			String store_id = storeVO.getStore_id();

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或 【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String prod_id = req.getParameter("prod_id").trim();

				String prod_name = req.getParameter("prod_name").trim();
				if (prod_name.isEmpty()) {
					errorMsgs.add("請輸入商品名稱.");
				}

				String cate_id = req.getParameter("cate_id").trim();

				Integer prod_price;
				try {
					prod_price = new Integer(req.getParameter("prod_price").trim());
				} catch (NumberFormatException e) {
					prod_price = 1000;
					errorMsgs.add("單價請填整數.");
				}

				Integer prod_category;
				try {
					prod_category = new Integer(req.getParameter("prod_category").trim());
				} catch (Exception e) {
					prod_category = 1;
					errorMsgs.add("請選擇購物類型.");
				}

				String img_base64 = req.getParameter("prod_img").trim();
				if(img_base64.isEmpty()){
					errorMsgs.add("請選擇圖片.");
				}
				else{
					img_base64 = img_base64.substring(img_base64.indexOf(";base64,") + 8);
				}
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] prod_img = decoder.decode(img_base64);
				
				/*
				Part part = req.getPart("prod_img");
				byte[] prod_img = Pic.getPictureByteArray(part);
				if (Pic.noFileSelected(part)) {
					ProductService prodSvc = new ProductService();
					prod_img = prodSvc.getOneProduct(prod_id).getProd_img();
				}
				*/

				Integer prod_amt;
				if(prod_category == 2) {
					prod_amt = -1;
				}
				else {
					try {
						prod_amt = new Integer(req.getParameter("prod_amt").trim());
					} catch (NumberFormatException e) {
						prod_amt = 100;
						errorMsgs.add("庫存請填整數.");
					}
					if (prod_amt <= 0) {
						prod_amt = 100;
						errorMsgs.add("庫存必須大於0.");
					}
				}
				
				Integer prod_launch = new Integer(req.getParameter("prod_launch").trim());

				String prod_desc = req.getParameter("prod_desc").trim();
				
				ProductVO productVO = new ProductVO();
				productVO.setProd_id(prod_id);
				productVO.setStore_id(store_id);
				productVO.setProd_name(prod_name);
				productVO.setCate_id(cate_id);
				productVO.setProd_price(prod_price);
				productVO.setProd_category(prod_category);
				productVO.setProd_img(prod_img);
				productVO.setProd_amt(prod_amt);
				productVO.setProd_launch(prod_launch);
				productVO.setProd_desc(prod_desc);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ProductService prodSvc = new ProductService();
				productVO = prodSvc.updateProduct(prod_id, store_id, prod_name, cate_id, prod_price, prod_category, prod_img, prod_amt, prod_launch, prod_desc);

				/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
				String url = null;
				if ("/frontend/product/product.jsp".equals(requestURL)) {
					url = "/frontend/product/product.do?action=getOne_For_Display";
				} else if ("/frontend/product/listAllProduct.jsp".equals(requestURL) || "/frontend/store/listProductsByStore_id.jsp".equals(requestURL)) {
					url = requestURL;
				}
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/product/update_product_input.jsp");
				failureView.forward(req, res);
				return;
			}
		} // if ("update".equals(action))

		// 新增商品
		if ("insert".equals(action)) { // 來自addProduct.jsp的請求

			String store_id = storeVO.getStore_id();

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String prod_name = req.getParameter("prod_name").trim();
				if (prod_name.isEmpty()) {
					errorMsgs.add("請輸入商品名稱.");
				}

				String cate_id = req.getParameter("cate_id").trim();
				if (cate_id.isEmpty()) {
					errorMsgs.add("請選擇商品類型.");
				}

				Integer prod_price;
				try {
					String str_prod_price = req.getParameter("prod_price").trim();
					if(str_prod_price.isEmpty()){
						prod_price = 1000;
						errorMsgs.add("請輸入單價.");
					}
					else{
						prod_price = new Integer(str_prod_price);
					}
				} catch (NumberFormatException e) {
					prod_price = 1000;
					errorMsgs.add("單價請填整數.");
				}
				if (prod_price <= 0) {
					prod_price = 1000;
					errorMsgs.add("單價必須大於0.");
				}

				Integer prod_category;
				try {
					prod_category = new Integer(req.getParameter("prod_category").trim());
				} catch (Exception e) {
					prod_category = 0;
					errorMsgs.add("請選擇購物類型.");
				}

				String img_base64 = req.getParameter("prod_img").trim();
				if(img_base64.isEmpty()){
					errorMsgs.add("請選擇圖片.");
				}
				else{
					img_base64 = img_base64.substring(img_base64.indexOf(";base64,") + 8);
				}
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] prod_img = decoder.decode(img_base64);
				
				/*
				Part part = req.getPart("prod_img");
				byte[] prod_img = Pic.getPictureByteArray(part);
				if (Pic.noFileSelected(part)) {
					prod_img = null;
					errorMsgs.add("請選擇圖片.");
				}
				*/

				Integer prod_amt;
				if(prod_category == 2) {
					prod_amt = -1;
				}
				else {
					try {
						prod_amt = new Integer(req.getParameter("prod_amt").trim());
					} catch (NumberFormatException e) {
						prod_amt = 100;
						errorMsgs.add("庫存請填整數.");
					}
					if (prod_amt <= 0) {
						prod_amt = 100;
						errorMsgs.add("庫存必須大於0.");
					}
				}

				Integer prod_launch = new Integer(0);

				String prod_desc = req.getParameter("prod_desc").trim();
				
				ProductVO productVO = new ProductVO();
				productVO.setStore_id(store_id);
				productVO.setProd_name(prod_name);
				productVO.setCate_id(cate_id);
				productVO.setProd_price(prod_price);
				productVO.setProd_category(prod_category);
				productVO.setProd_img(prod_img);
				productVO.setProd_amt(prod_amt);
				productVO.setProd_launch(prod_launch);
				productVO.setProd_desc(prod_desc);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/product/addProduct.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ProductService prodSvc = new ProductService();
				productVO = prodSvc.addProduct(store_id, prod_name, cate_id, prod_price, prod_category, prod_img, prod_amt, prod_launch, prod_desc);

				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/store/listProductsByStore_id.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProduct.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/product/addProduct.jsp");
				failureView.forward(req, res);
				return;
			}
		} // if ("insert".equals(action))

		// 查詢單一商品資訊
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = null;
			boolean hasReqError;
			if(req.getAttribute("errorMsgs") != null){
				errorMsgs = (List<String>)req.getAttribute("errorMsgs");
				hasReqError = true;
			}
			else{
				errorMsgs = new LinkedList<String>();
				hasReqError = false;
			}

			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String prod_id = req.getParameter("prod_id");
				if (prod_id == null || (prod_id.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty() && !hasReqError) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService prodSvc = new ProductService();
				ProductVO productVO = prodSvc.getOneProduct(prod_id);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty() && !hasReqError) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/product/product.jsp";
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

		// 查詢單一商品的留言
		if ("listMsgs_ByProd_id".equals(action)) { // 來自select_page.jsp的請求

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
				if ("listMsgs_ByProd_id".equals(action))
					url = "/frontend/product/listMsgs_ByProd_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
//				else if ("listEmps_ByDeptno_B".equals(action))
//					url = "/dept/listAllDept.jsp";              // 成功轉交 dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} // if ("listMsgs_ByProd_id".equals(action))
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
	}

}
