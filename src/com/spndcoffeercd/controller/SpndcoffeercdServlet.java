package com.spndcoffeercd.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;


import com.spndcoffeercd.model.*;
import com.tools.Pic;

@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SpndcoffeercdServlet extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("insert".equals(action)) { // 來自addSpndcoffee.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				/*取杯明細編號*/
				
				String rcd_id = req.getParameter("rcd_id").trim();
				if(rcd_id.length() == 0){ /*空字串*/
					errorMsgs.add("請填入取杯明細編號");
				}
				
				/*寄杯紀錄編號*/
				String list_id = req.getParameter("list_id").trim();
				if("".equals(list_id)){
					errorMsgs.add("請填入寄杯紀錄編號");
				}
				
				/*單次領取杯數*/
				Integer single_amt = null;
				
				try {
					single_amt = new Integer(req.getParameter("single_amt").trim());
				} catch (NumberFormatException e) {
					single_amt = 0;
					errorMsgs.add("領取杯數請填整數.");
				}
				
				/*取杯時間*/
				java.sql.Timestamp rcd_date = null;
				try {
					rcd_date = java.sql.Timestamp.valueOf(req.getParameter("rcd_date").trim());
				} catch (IllegalArgumentException e) {
					rcd_date = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入取杯時間!");
				}
				
			
				
				SpndcoffeercdVO spndcoffeercdVO = new SpndcoffeercdVO();
				spndcoffeercdVO.setRcd_id(rcd_id);
				spndcoffeercdVO.setList_id(list_id);
				spndcoffeercdVO.setSingle_amt(single_amt);
				spndcoffeercdVO.setRcd_date(rcd_date);
			
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("spndcoffeercdVO", spndcoffeercdVO); // 含有輸入格式錯誤的spndcoffeeVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/spndcoffee/addSpndcoffee.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				SpndcoffeercdService spndcoffeercdSvc = new SpndcoffeercdService();
				spndcoffeercdVO =  spndcoffeercdSvc.addSpndcoffeercd(rcd_id, list_id, single_amt, rcd_date);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/spndcoffeercd/listAllSpndCoffeeRcd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllSpndCoffeeRcd.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/spndcoffeercd/addSpndcoffeercd.jsp");
				failureView.forward(req, res);
				return;
			}
		}

        
        if ("getOne_For_Display".equals(action)) { // 來自spndcoffeercd_select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String rcd_id = req.getParameter("rcd_id");
				if (rcd_id == null || (rcd_id.trim()).length() == 0) {
					errorMsgs.add("請輸入取杯明細編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/spndcoffeercd/spndcoffeercd_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String list_id = req.getParameter("list_id");
				if (list_id == null || (list_id.trim()).length() == 0) {
					errorMsgs.add("請輸入寄杯紀錄編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/spndcoffeercd/spndcoffeercd_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				
				Integer single_amt = null;
				try {
					single_amt = new Integer(single_amt);
				} catch (Exception e) {
					errorMsgs.add("單次領取杯數不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/spndcoffeercd/spndcoffeercd_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				SpndcoffeercdService spndcoffeercdSvc = new SpndcoffeercdService();
				SpndcoffeercdVO spndcoffeercdVO = spndcoffeercdSvc.getOneSpndcoffeercd(rcd_id);
				if (rcd_id == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/spndcoffeercd/spndcoffeercd_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("spndcoffeercdVO", spndcoffeercdVO); // 資料庫取出的spndcoffeercdVO物件,存入req
				String url = "/spndcoffeercd/listOneSpndcoffeeRcd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneSpndcoffeeRcd.jsp
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
		}
        
        if ("getSpndcoffeercdByRcd_id".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String rcd_id = req.getParameter("rcd_id");

				/*************************** 2.開始查詢資料 ****************************************/
				SpndcoffeercdService spndcoffeercdSvc = new SpndcoffeercdService();
				SpndcoffeercdVO spndcoffeercdVO = spndcoffeercdSvc.getOneSpndcoffeercd(rcd_id); //?

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("spndcoffeercdVO", spndcoffeercdVO);    // 資料庫取出的set物件,存入request, 名字自己取

				//String url = null;
				//if ("listRcd_ByList_id".equals(action))
				//	url = "/spndcoffeelist/listRcd_ByList_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
				        
				RequestDispatcher successView = req.getRequestDispatcher("/frontend/spndcoffeercd/listRcd_ByRcd_id.jsp");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		

    }
    
    
}