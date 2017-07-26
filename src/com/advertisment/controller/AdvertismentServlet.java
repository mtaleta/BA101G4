package com.advertisment.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.advertisment.model.AdvertismentService;
import com.advertisment.model.AdvertismentVO;
import com.news.model.NewsService;
import com.news.model.NewsVO;
import com.store.model.StoreVO;
import com.tools.Pic;

@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AdvertismentServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		HttpSession session = req.getSession();
		
		
	
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		
		if ("insertAdvertisment".equals(action)) { // 來自addAdvertisment.jsp的請求  
			
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String ad_title = null;
				String ad_content = null;
				byte[] ad_img= null;
				
				System.out.println("XXXXXXXXXXXXX");
				try {
					ad_title = req.getParameter("ad_title").trim();
					if(ad_title == ""){
						errorMsgs.add("請填標題");
					}
				} catch (NumberFormatException e) {
					
					errorMsgs.add("123");
				}
				
				
				try {
					ad_content = req.getParameter("ad_content").trim();
					if(ad_content == ""){
						errorMsgs.add("請填廣告內容.");
					}
				} catch (NumberFormatException e) {
					ad_content = "";
					errorMsgs.add("請填廣告內容.");
				}
				
				
				Part part = req.getPart("ad_img");
				ad_img = Pic.getPictureByteArray(part);
				
				Timestamp ad_date=new Timestamp(System.currentTimeMillis());
				
				
				AdvertismentVO advertismentVO = new AdvertismentVO();
				
				advertismentVO.setAd_title(ad_title);
				advertismentVO.setAd_content(ad_content);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("advertismentVO", advertismentVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backtend/advertisment/addAdvertisment.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				AdvertismentService advertismentSvc = new AdvertismentService();
				advertismentVO = advertismentSvc.addAdvertisment(ad_title, ad_content, ad_img, ad_date, 0);
				
				List<AdvertismentVO> advertismentList = advertismentSvc.getAll();
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("advertismentList", advertismentList);
				
				String url = "/backend/advertisment/listOfAllAdvertisment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		
		
		if ("getAllAdvertisment".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/

				/*************************** 2.開始查詢資料  *****************************************/
				AdvertismentService advertismentSvc = new AdvertismentService();
				List<AdvertismentVO> advertismentList = advertismentSvc.getAll();

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("advertismentList", advertismentList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/advertisment/listOfAllAdvertisment.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
	
		
		
		if ("getAdvertismentContent".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String advertisment_id = req.getParameter("advertisment_id");

				/*************************** 2.開始查詢資料  *****************************************/
				AdvertismentService advertismentSvc = new AdvertismentService();
				AdvertismentVO advertismentVO = advertismentSvc.getAdvertismentContentByAD_ID(advertisment_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("advertismentVO", advertismentVO);// 資料庫取出的storeVO物件,存入req
				
				String url = "/frontend/advertisment/advertismentcontent.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;

			}
		}
		
		
		
		
		if ("getBackAdvertismentContent".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String advertisment_id = req.getParameter("advertisment_id");

				/*************************** 2.開始查詢資料  *****************************************/
				AdvertismentService advertismentSvc = new AdvertismentService();
				AdvertismentVO advertismentVO = advertismentSvc.getAdvertismentContentByAD_ID(advertisment_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("advertismentVO", advertismentVO);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/advertisment/advertismentcontent.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		
		if ("UpdateUP".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String advertisment_id = req.getParameter("advertisment_id");

				/*************************** 2.開始查詢資料  *****************************************/
				AdvertismentService advertismentSvc = new AdvertismentService();
				advertismentSvc.updateUP(advertisment_id);
				
				System.out.println("123456789");
				List<AdvertismentVO> advertismentList = advertismentSvc.getAll();

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("advertismentList", advertismentList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/advertisment/listOfAllAdvertisment.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		

		
		
		if ("UpdateDOWN".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String advertisment_id = req.getParameter("advertisment_id");

				/*************************** 2.開始查詢資料  *****************************************/
				AdvertismentService advertismentSvc = new AdvertismentService();
				advertismentSvc.updateDOWN(advertisment_id);
				
				
				List<AdvertismentVO> advertismentList = advertismentSvc.getAll();

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("advertismentList", advertismentList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/advertisment/listOfAllAdvertisment.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
	}

}
