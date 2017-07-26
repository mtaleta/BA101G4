package com.spndcoffee.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.store.model.*;

import com.spndcoffee.model.*;
import com.spndcoffeelist.model.SpndcoffeelistService;
import com.spndcoffeelist.model.SpndcoffeelistVO;
import com.spndcoffeercd.model.SpndcoffeercdVO;
import com.tools.Pic;

@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SpndcoffeeServlet extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        HttpSession session = req.getSession();
        
        if ("insert".equals(action)) { // 來自addSpndcoffee.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				/*店家編號*/
				String store_id = ((StoreVO)session.getAttribute("STORE")).getStore_id();

				
				/*寄杯活動名稱*/
				String spnd_name = req.getParameter("spnd_name").trim();
				if("".equals(spnd_name)){
					errorMsgs.add("請填入寄杯活動名稱");
				}
				
				/*寄杯商品*/
				String spnd_prod =req.getParameter("spnd_prod").trim();
				if(spnd_prod.isEmpty()){
					errorMsgs.add("請填入寄杯商品");
				}
				
				/*活動結束日期*/
				java.sql.Date spnd_enddate = null;
				try {
					spnd_enddate = java.sql.Date.valueOf(req.getParameter("spnd_enddate").trim());
				} catch (IllegalArgumentException e) {
					spnd_enddate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				/*新增圖片*/
				Part part =req.getPart("spnd_img");
				byte[] spnd_img = Pic.getPictureByteArray(part);

				
				
				/*店家寄杯總杯數*/
				Integer spnd_amt = null;
				try {
					spnd_amt = new Integer(req.getParameter("spnd_amt").trim());
				} catch (NumberFormatException e) {
					spnd_amt = 0;
					errorMsgs.add("總杯數請填整數.");
				}
				

				SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();
				spndcoffeeVO.setStore_id(store_id);
				spndcoffeeVO.setSpnd_name(spnd_name);
				spndcoffeeVO.setSpnd_prod(spnd_prod);
				spndcoffeeVO.setSpnd_enddate(spnd_enddate);
				spndcoffeeVO.setSpnd_amt(spnd_amt);
				spndcoffeeVO.setSpnd_img(spnd_img);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("spndcoffeeVO", spndcoffeeVO); // 含有輸入格式錯誤的spndcoffeeVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/spndcoffee/addSpndcoffee.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				SpndcoffeeService spndcoffeeSvc = new SpndcoffeeService();
				spndcoffeeVO =  spndcoffeeSvc.addSpndcoffee(store_id, spnd_name, spnd_prod, spnd_enddate, spnd_amt, spnd_img);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/store/listSpndCoffeeByStore_id.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/spndcoffee/addSpndcoffee.jsp");
				failureView.forward(req, res);
				return;
			}
		}

    
    
	    if ("getOneSpndcoffee_For_Display".equals(action)) { // 來自spndcoffee_select_page.jsp的請求
	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String spnd_id = req.getParameter("spnd_id");
				if (spnd_id == null || (spnd_id.trim()).length() == 0) {
					errorMsgs.add("請輸入寄杯活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/spndcoffee/spndcoffee_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
	
				
				/***************************2.開始查詢資料*****************************************/
				SpndcoffeeService spndcoffeeSvc = new SpndcoffeeService();
				SpndcoffeeVO spndcoffeeVO = spndcoffeeSvc.getOneSpndcoffee(spnd_id);
				if (spndcoffeeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/spndcoffee/spndcoffee_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("spndcoffeeVO", spndcoffeeVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/spndcoffee/listOneSpndcoffee.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/spndcoffee/spndcoffee_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}
	    
	    
	    if ("getSpndcoffeeListsBySpnd_id".equals(action)) {
	
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String spnd_id = req.getParameter("spnd_id");

				/*************************** 2.開始查詢資料 ****************************************/
				SpndcoffeeService spndcoffeeSvc = new SpndcoffeeService();
				Set<SpndcoffeelistVO> set = spndcoffeeSvc.getSpndcoffeelistsBySpnd_id(spnd_id); //?

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("getSpndcoffeelistsBySpnd_id", set);    // 資料庫取出的set物件,存入request, 名字自己取
	
				//String url = null;
				//if ("listRcd_ByList_id".equals(action))
				//	url = "/spndcoffeelist/listRcd_ByList_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
  
				RequestDispatcher successView = req.getRequestDispatcher("/frontend/spndcoffee/listList_BySpnd_id.jsp");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
    
	    if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數****************************************/
				String spnd_id = req.getParameter("spnd_id");
				
				/***************************2.開始查詢資料****************************************/
				SpndcoffeeService spndcoffeeSvc = new SpndcoffeeService();
				SpndcoffeeVO spndcoffeeVO = spndcoffeeSvc.getOneSpndcoffee(spnd_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("spndcoffeeVO", spndcoffeeVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/spndcoffee/updateSpndcoffee.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/store/listSpndCoffeeByStore_id.jsp");
				failureView.forward(req, res);
				return;
			}
		}
	    
	    if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String spnd_id = req.getParameter("spnd_id").trim();
				String store_id = ((StoreVO)session.getAttribute("STORE")).getStore_id();
				String spnd_prod = req.getParameter("spnd_prod").trim();
				String spnd_name = req.getParameter("spnd_name").trim();
			

				Part part = req.getPart("spnd_img");

				byte[] spnd_img;

				SpndcoffeeService spndcoffeeSvc = new SpndcoffeeService();

				if(Pic.noFileSelected(part)){
					// 沒有選擇圖片
					SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();
					spndcoffeeVO = spndcoffeeSvc.getOneSpndcoffee(spnd_id);
					spnd_img = spndcoffeeVO.getSpnd_img();
				} else {
					// 有選擇圖片
					spnd_img = Pic.getPictureByteArray(part);
				}

				java.sql.Date spnd_enddate = null;
				try {
					spnd_enddate = java.sql.Date.valueOf(req.getParameter("spnd_enddate").trim());
				} catch (IllegalArgumentException e) {
					spnd_enddate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer spnd_amt = 0;
				try {
					spnd_amt = new Integer(req.getParameter("spnd_amt").trim());
				} catch (NumberFormatException e) {
				
					errorMsgs.add("店家寄杯總杯數請填整數.");
				}

				SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();
				spndcoffeeVO.setSpnd_id(spnd_id);
				spndcoffeeVO.setStore_id(store_id);
				spndcoffeeVO.setSpnd_name(spnd_name);
				spndcoffeeVO.setSpnd_prod(spnd_prod);
				spndcoffeeVO.setSpnd_enddate(spnd_enddate);
				spndcoffeeVO.setSpnd_amt(spnd_amt);
				spndcoffeeVO.setSpnd_img(spnd_img);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("spndcoffeeVO", spndcoffeeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/spndcoffee/updateSpndcoffee.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}

				/***************************2.開始修改資料*****************************************/
				spndcoffeeVO = spndcoffeeSvc.updateSpndcoffee(spnd_id, store_id, spnd_name, spnd_prod, spnd_enddate, spnd_amt, spnd_img);

				/***************************3.修改完成,準備轉交(Send the Success view)*************/				

				RequestDispatcher successView = req.getRequestDispatcher("/frontend/store/listSpndCoffeeByStore_id.jsp");   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/spndcoffee/updateSpndcoffee.jsp");
				failureView.forward(req, res);
				return;
			}
		}
	    
    
    }
    
}