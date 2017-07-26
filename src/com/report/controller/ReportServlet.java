package com.report.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.report.model.ReportService;
import com.report.model.ReportVO;
import com.rept_store.model.Rept_storeService;
import com.rept_store.model.Rept_storeVO;


public class ReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String memid = req.getParameter("mem_id");
			
				
				if (memid == null || (memid.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/report/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_id = null;
				try {
					mem_id = new String(memid);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/report/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ReportService reportSvc = new ReportService();
				ReportVO reportVO = reportSvc.getOneReport(mem_id);
				if (reportVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/report/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reportVO", reportVO); // 資料庫取出的empVO物件,存入req
				String url = "/report/listOneReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/report/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String mem_id = req.getParameter("mem_id");
				
				/***************************2.開始查詢資料****************************************/
				ReportService reportSvc = new ReportService();
				ReportVO reportVO = reportSvc.getOneReport(mem_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("reportVO", reportVO);         // 資料庫取出的empVO物件,存入req
				String url = "/report/update_report_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/report/listAllReport.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		System.out.println("有收到?");
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_id = req.getParameter("mem_id").trim();
				String rnr_id = req.getParameter("rnr_id").trim();
				Integer rept_verf = new Integer(req.getParameter("rept_verf").trim());
				String rept_rsn = req.getParameter("rept_rsn").trim();				

				ReportVO reportVO = new ReportVO();
				reportVO.setMem_id(mem_id);
				reportVO.setRnr_id(rnr_id);
				reportVO.setRept_verf(rept_verf);
				reportVO.setRept_rsn(rept_rsn);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reportVO", reportVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/report/update_report_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ReportService reportSvc = new ReportService();
				reportVO = reportSvc.updateReport(mem_id, rnr_id, rept_verf, rept_rsn);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reportVO", reportVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/report/listOneReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/report/update_report_input.jsp");
				failureView.forward(req, res);
				return;
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_id = req.getParameter("mem_id").trim();
				String rnr_id = req.getParameter("rnr_id").trim();
				Integer rept_verf = new Integer(req.getParameter("rept_verf").trim());
				String rept_rsn = req.getParameter("rept_rsn").trim();	

				ReportVO reportVO = new ReportVO();
				reportVO.setMem_id(mem_id);
				reportVO.setRnr_id(rnr_id);
				reportVO.setRept_verf(rept_verf);
				reportVO.setRept_rsn(rept_rsn);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reportVO", reportVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/report/addReport.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ReportService reportSvc = new ReportService();
				reportVO = reportSvc.addReport(mem_id, rnr_id, rept_verf, rept_rsn);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/report/addReport.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
//		
//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				String mem_id = req.getParameter("mem_id");
//				String rnr_id = req.getParameter("rnr_id");
//				
//				/***************************2.開始刪除資料***************************************/
//				ReportService reportSvc = new ReportService();
//				reportSvc.deleteReport(mem_id,rnr_id);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/report/listAllReport.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/report/listAllReport.jsp");
//				failureView.forward(req, res);
//			}
//		}
        
        // 祈竣 祈竣 祈竣
     	// 祈竣 祈竣 祈竣
	}
}
