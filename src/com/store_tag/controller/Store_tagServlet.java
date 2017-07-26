package com.store_tag.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.model.StoreService;
import com.store.model.StoreVO;
import com.store_tag.model.Store_tagDAO;
import com.store_tag.model.Store_tagService;
import com.store_tag.model.Store_tagVO;
import com.tag.model.TagService;
import com.tag.model.TagVO;

public class Store_tagServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		if ("getStoresBYTAG".equals(action)) {

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("tag_id");

				/*************************** 2.開始查詢資料  *****************************************/
				Store_tagService store_tagSvc = new Store_tagService();
				StoreService storeSvc = new StoreService();
				
				Set<StoreVO> storeSet = new LinkedHashSet<StoreVO>();

				Set<Store_tagVO> store_tagSet = store_tagSvc.getStoresIdByTag_id(str);

				for (Store_tagVO store_tagVO : store_tagSet) {
					storeSet.add(storeSvc.getOneStore(store_tagVO.getStore_id()));
				}

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("findStoreByAreaOrName", storeSet);// 資料庫取出的storeVO物件,存入req
				
				String url = "/frontend/store/findStore.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交

				successView.forward(req, res);

				return;

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
	}

}
