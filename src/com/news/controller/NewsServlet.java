package com.news.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

import com.fav_store.model.Fav_storeService;
import com.fav_store.model.Fav_storeVO;
import com.member.model.MemberVO;
import com.news.model.NewsService;
import com.news.model.NewsVO;
import com.rate_n_rev.model.Rate_n_revService;
import com.rate_n_rev.model.Rate_n_revVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;
import com.store_tag.model.Store_tagService;
import com.store_tag.model.Store_tagVO;
import com.tools.Pic;

@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class NewsServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		HttpSession session = req.getSession();
			
		StoreVO storeVO = (StoreVO)session.getAttribute("STORE");

		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		
		if ("insertNews".equals(action)) { // 來自addRate_n_rev.jsp的請求  
			String session_store_id = storeVO.getStore_id();
			List<String> errorMsgs = new LinkedList<String>();
			
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String news_title = null;
				String news_content = null;
				String news_class = null;
				byte[] news_img= null;
				String store_id = req.getParameter("store_id");
				
				try {
					news_title = req.getParameter("news_title").trim();
					if(news_title == ""){
						errorMsgs.add("請填標題");
					}
				} catch (NumberFormatException e) {
					
					errorMsgs.add("123");
				}
				
				
				try {
					news_content = req.getParameter("news_content").trim();
					if(news_content == ""){
						errorMsgs.add("請填消息內容.");
					}
				} catch (NumberFormatException e) {
					news_content = "";
					errorMsgs.add("請填消息內容.");
				}
				
				news_class = req.getParameter("news_class").trim();
				Part part = req.getPart("news_img");
				news_img = Pic.getPictureByteArray(part);
				
				Timestamp news_date=new Timestamp(System.currentTimeMillis());
				
				
				NewsVO newsVO = new NewsVO();
				
				newsVO.setNews_title(news_title);
				newsVO.setNews_content(news_content);
				newsVO.setNews_class(news_class);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/news/addNews.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(session_store_id, news_title, news_content, news_img,news_date, news_class, 0, 0);
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				
				String url = "/frontend/store/listOfAllNewsByStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add("請確實填完所有欄位!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/news/addNews.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		
		
		if ("getFrontNewsContent".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String news_id = req.getParameter("news_id");

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getNewsContentByNews_id(news_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsVO", newsVO);// 資料庫取出的storeVO物件,存入req
				
				String url = "/frontend/news/newscontent.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		if ("getBackNewsContent".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String news_id = req.getParameter("news_id");

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getNewsContentByNews_id(news_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsVO", newsVO);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/news/newscontent.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		if ("getStoreNewsContent".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String news_id = req.getParameter("news_id");

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getNewsContentByNews_id(news_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsVO", newsVO);// 資料庫取出的storeVO物件,存入req
				
				String url = "/frontend/news/newscontent.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		if ("getAllNews".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				List<NewsVO> newsList = newsSvc.getAll();

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsList", newsList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/news/listOfAllNews.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		
		
		if ("getAllNewsBYStore_id".equals(action)) {
			String session_store_id = storeVO.getStore_id();
			
			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				List<NewsVO> newsList = newsSvc.getAllNewsByStore_id(session_store_id);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsList", newsList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/select_page3.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/select_page3.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		if ("getNewsByClass".equals(action)) {
			String str = req.getParameter("news_class");
			String news_class = null;
			
			if(str.equals("XXX")){
				news_class = "咖啡知識";
			} else if(str.equals("YYY")){
				news_class = "優惠活動";
			} else if(str.equals("ZZZ")){
				news_class = "新店成立";
			}
			
			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				List<NewsVO> newsList = newsSvc.getNewsByClass(news_class);
				
			
				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsList", newsList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/frontend/news/listOfNewsClass.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		

		if ("UpdatePass".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String news_id = req.getParameter("news_id");

				/*************************** 2.開始查詢資料  *****************************************/
				NewsService newsSvc = new NewsService();
				newsSvc.updatePass(news_id);
				
				
				List<NewsVO> newsList = newsSvc.getAll();

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsList", newsList);// 資料庫取出的storeVO物件,存入req
				
				String url = "/backend/news/listOfAllNews.jsp";

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
