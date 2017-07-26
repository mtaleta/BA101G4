package com.category.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.product.model.ProductVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		if("findCateByProd_cate".equals(action)) {
			
			Integer prod_category = new Integer(req.getParameter("prod_category"));
			
			CategoryService categorySvc = new CategoryService();	
			List<CategoryVO> categoryVOList = categorySvc.findCateByProd_cate(prod_category);
			
			JSONArray array = new JSONArray();
			
			for(CategoryVO categoryVO : categoryVOList){
				JSONObject obj = new JSONObject();
				try{
					obj.put("cate_id", categoryVO.getCate_id());
					obj.put("cate_name", categoryVO.getCate_name());
					obj.put("prod_category", categoryVO.getProd_category());

				}catch(Exception e){}
				array.add(obj);
			}
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
		// 此區塊與晴帆"findCateByProd_cate".equals(action)相同
		// 此區塊與晴帆"findCateByProd_cate".equals(action)相同
		if ("getCateByAjax".equals(action)) {
			JSONArray array = new JSONArray();

			/*************************** 1.接收請求參數 ****************************************/
			Integer prod_category = new Integer(req.getParameter("prod_category").trim());

			/*************************** 開始查詢資料 *****************************************/
			CategoryService cateSvc = new CategoryService();
			Set<CategoryVO> set = cateSvc.getByProd_category(prod_category);

			for(CategoryVO categoryVO : set){
				JSONObject obj = new JSONObject();
				try{
					obj.put("cate_id", categoryVO.getCate_id());
					obj.put("cate_name", categoryVO.getCate_name());
					obj.put("prod_category", categoryVO.getProd_category());
				}catch(Exception e){}
				array.add(obj);
			}
			
			/*************************** 開始修改資料 *****************************************/
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		} // if("getCateByAjax".equals(action))
		// 此區塊與晴帆"findCateByProd_cate".equals(action)相同
		// 此區塊與晴帆"findCateByProd_cate".equals(action)相同
		
		// 新增商品類型
		// 新增商品類型
		if ("insert".equals(action)) { // 來自addProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String cate_name = req.getParameter("cate_name").trim();
				if (cate_name.isEmpty()) {
					errorMsgs.add("請輸入商品類型名稱.");
				}
				
				Integer prod_category;
				try {
					prod_category = new Integer(req.getParameter("prod_category").trim());
				} catch (Exception e) {
					prod_category = 1;
					errorMsgs.add("請選擇購物類型.");
				}
				
				CategoryService cateSvc = new CategoryService();
				if(cateSvc.getOneByCate_nameAndProd_category(cate_name, prod_category) != null){
					errorMsgs.add("已有此商品類型.");
				}

				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setCate_name(cate_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("categoryVO", categoryVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/category/addCategory.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				categoryVO = cateSvc.addCategory(cate_name, prod_category);

				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/category/addCategory.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProduct.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/category/addCategory.jsp");
				failureView.forward(req, res);
				return;
			}
		} // if ("insert".equals(action))
		// 新增商品類型
		// 新增商品類型
		
		// 查詢單一種商品類型的全部商品
		// 查詢單一種商品類型的全部商品
		if ("listLaunchedProductsByCate_idDesc".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String cate_id = req.getParameter("cate_id");
				
				if (cate_id.isEmpty()) {
					RequestDispatcher successView = req.getRequestDispatcher("/frontend/product/shop.jsp");
					successView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 ****************************************/
				CategoryService cateSvc = new CategoryService();
				Set<ProductVO> set = cateSvc.getLaunchedProductsByCate_idDesc(cate_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listLaunchedProductsByCate_idDesc", set);    // 資料庫取出的set物件,存入request

				RequestDispatcher successView = req.getRequestDispatcher("/frontend/category/shop.jsp");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} // if ("listLaunchedProductsByCate_idDesc".equals(action))
		// 查詢單一種商品類型的全部商品
		// 查詢單一種商品類型的全部商品
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
	}
}
