package com.tag.controller;

import java.io.IOException;
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
import com.tag.model.TagService;
import com.tag.model.TagVO;

public class TagServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		HttpSession session = req.getSession();
		
		MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
		
		String mem_id = memberVO.getMem_id();
		
		if ("insertStore_tag".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("store_id");
				String tag_content =null;
				
				tag_content = req.getParameter("tag_content");
				if(tag_content == ""){
					errorMsgs.add("請填標籤內容");
				}
				
				TagVO tagVO = new TagVO();
				tagVO.setTag_content(tag_content);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tagVO", tagVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/store_tag/addStore_tag.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料*****************************************/
				TagService tagSvc = new TagService();
				tagVO = tagSvc.addTag(tag_content);
				
				TagVO tag_id  = tagSvc.getTag_id_BY_Tag_content(tag_content);
				String tag_idstr = tag_id.getTag_id();
				
				Store_tagService store_tagSvc = new Store_tagService();
				Store_tagVO store_tagVO= new Store_tagVO();
				store_tagVO = store_tagSvc.addstore_tag(str, tag_idstr, 1);
				
				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(str);
				
				
				Rate_n_revService rate_n_revSvc = new Rate_n_revService();
				Set<Rate_n_revVO> rate_n_revSet = rate_n_revSvc.getAllRate_n_rev_ByStore_id(str);
				
				
				Set<Store_tagVO> store_tagVOSet = storeSvc.getStore_tagsByStore_id(str);
				
				Fav_storeService fav_storeSvc = new Fav_storeService();
				Fav_storeVO fav_storeVOcheck = fav_storeSvc.findByPrimaryKey(mem_id, str);
				
				
				req.setAttribute("storeVO", storeVO);
				req.setAttribute("rate_n_revVO", rate_n_revSet);// 資料庫取出的storeVO物件,存入req
				req.setAttribute("store_tagVO", store_tagVOSet);
				req.setAttribute("fav_storeVOcheck", fav_storeVOcheck);
				
				
				String url = "/frontend/memberstore/listOneStore123.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
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
