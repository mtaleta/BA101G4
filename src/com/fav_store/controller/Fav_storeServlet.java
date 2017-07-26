package com.fav_store.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fav_store.model.Fav_storeService;
import com.fav_store.model.Fav_storeVO;
import com.member.model.MemberVO;
import com.rate_n_rev.model.Rate_n_revService;
import com.rate_n_rev.model.Rate_n_revVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;
import com.store_tag.model.Store_tagService;
import com.store_tag.model.Store_tagVO;

public class Fav_storeServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
		
		String mem_id = memberVO.getMem_id();
		
		
		if ("insert_Favstore".equals(action)) { // 來自addRate_n_rev.jsp的請求  
			

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String store_id = req.getParameter("store_id");
				
				/***************************2.開始新增資料***************************************/
				Fav_storeService fav_storeSvc = new Fav_storeService();
				
				Fav_storeVO  fav_storeVO = fav_storeSvc.addFav_store(mem_id, store_id);
				
				Fav_storeVO fav_storeVOcheck = fav_storeSvc.findByPrimaryKey(mem_id, store_id);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_id);
				
				Rate_n_revService rate_n_revSvc = new Rate_n_revService();
				Set<Rate_n_revVO> rate_n_revSet = rate_n_revSvc.getAllRate_n_rev_ByStore_id(store_id);
				
				Store_tagService store_tagSvc = new Store_tagService();
				Set<Store_tagVO> store_tagVO = storeSvc.getStore_tagsByStore_id(store_id);
				
				
				
				req.setAttribute("storeVO", storeVO);
				req.setAttribute("rate_n_revVO", rate_n_revSet);// 資料庫取出的storeVO物件,存入req
				req.setAttribute("store_tagVO", store_tagVO);
				req.setAttribute("fav_storeVOcheck", fav_storeVOcheck);
				String url = "/frontend/memberstore/listOneStore123.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("delete_Favstore".equals(action)) { // 來自addRate_n_rev.jsp的請求  
			

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String store_id = req.getParameter("store_id");
				
				/***************************2.開始新增資料***************************************/
				Fav_storeService fav_storeSvc = new Fav_storeService();
				
				fav_storeSvc.deleteFav_store(mem_id, store_id);
				
				Fav_storeVO fav_storeVOcheck = fav_storeSvc.findByPrimaryKey(mem_id, store_id);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_id);
				
				Rate_n_revService rate_n_revSvc = new Rate_n_revService();
				Set<Rate_n_revVO> rate_n_revSet = rate_n_revSvc.getAllRate_n_rev_ByStore_id(store_id);
				
				Store_tagService store_tagSvc = new Store_tagService();
				Set<Store_tagVO> store_tagVO = storeSvc.getStore_tagsByStore_id(store_id);
				
				
				
				req.setAttribute("storeVO", storeVO);
				req.setAttribute("rate_n_revVO", rate_n_revSet);// 資料庫取出的storeVO物件,存入req
				req.setAttribute("store_tagVO", store_tagVO);
				req.setAttribute("fav_storeVOcheck", fav_storeVOcheck);
				String url = "/frontend/memberstore/listOneStore123.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		
		if ("listOfFavStore".equals(action)) { // 來自addRate_n_rev.jsp的請求  

			Fav_storeService fav_storeSvc = new Fav_storeService();
			Set<Fav_storeVO> fav_storeSet = fav_storeSvc.getAllFavStoreByMem_id(mem_id);

			Set<StoreVO> storeList = new LinkedHashSet<StoreVO>();
			StoreService storeSvc = new StoreService();
			for(Fav_storeVO fav_storeVO : fav_storeSet) {
				storeList.add(storeSvc.getOneStore(fav_storeVO.getStore_id()));
			}
			
			req.setAttribute("findStoreByAreaOrName", storeList);

	 		String url = "/frontend/store/findStore.jsp";

			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
	}
}
