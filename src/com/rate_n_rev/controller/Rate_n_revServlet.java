package com.rate_n_rev.controller;

import java.io.IOException;
import java.sql.Timestamp;
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



public class Rate_n_revServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
		MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
		
		String mem_id = memberVO.getMem_id();
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		
		if ("insertRate_n_rev".equals(action)) { // 來自addRate_n_rev.jsp的請求  
			System.out.println("1231321321322132132");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer rate = null;
				String rev = null;
				String store_id = req.getParameter("store_id");
				System.out.println("mem_id = "+mem_id);
				System.out.println("store_id"+store_id);
				try {
					rate = new Integer(req.getParameter("rate").trim());
					if(rate>5 || rate<1){
						errorMsgs.add("評分只限1~5分");
					}
				} catch (NumberFormatException e) {
					rate = 1;
					errorMsgs.add("評分請填數字.");
				}
				System.out.println("rate"+rate);
				
				try {
					rev = req.getParameter("rev").trim();
					if(rev == ""){
						errorMsgs.add("請說感想.");
					}
				} catch (NumberFormatException e) {
					rev = "";
					errorMsgs.add("請說感想.");
				}
				
				Timestamp date=new Timestamp(System.currentTimeMillis());
				Rate_n_revVO rate_n_revVO = new Rate_n_revVO();
				
				
				rate_n_revVO.setRnr_rate(rate);
				rate_n_revVO.setRnr_rev(rev);
				rate_n_revVO.setMem_id(mem_id);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rate_n_revVO", rate_n_revVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/memberstore/listOneStore123.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Rate_n_revService rate_n_revSvc = new Rate_n_revService();
				rate_n_revVO = rate_n_revSvc.addRate_n_rev(mem_id, store_id, rate, rev,date);
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_id);
				
				
				
				Set<Rate_n_revVO> rate_n_revSet = rate_n_revSvc.getAllRate_n_rev_ByStore_id(store_id);
				
				Store_tagService store_tagSvc = new Store_tagService();
				Set<Store_tagVO> store_tagVO = storeSvc.getStore_tagsByStore_id(store_id);
				
				
				Fav_storeService fav_storeSvc = new Fav_storeService();
				Fav_storeVO fav_storeVOcheck = fav_storeSvc.findByPrimaryKey(mem_id, store_id);
				
				req.setAttribute("storeVO", storeVO);
				req.setAttribute("rate_n_revVO", rate_n_revSet);// 資料庫取出的storeVO物件,存入req
				req.setAttribute("store_tagVO", store_tagVO);
				req.setAttribute("store_id", store_id);
				req.setAttribute("fav_storeVOcheck", fav_storeVOcheck);
				
				String url = "/frontend/memberstore/listOneStore123.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		
	}
}
