package com.rept_activ.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.member.model.MemberVO;
import com.rept_activ.model.Rept_activService;
import com.rept_activ.model.Rept_activVO;
import com.store.model.StoreVO;

public class Rept_activServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
		String mem_id;

		
		
		
		
	    // 來自select_page.jsp的請求                                  // 來自 dept/listAllDept.jsp的請求
			if ("listAllRept_activs_ByActivity_A".equals(action) || "listAllRept_activs_ByActivity_B".equals(action)) {

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.接收請求參數 ****************************************/
					String activid = req.getParameter("activ_id");

					/*************************** 2.開始查詢資料 ****************************************/
					Rept_activService rept_activSvc = new Rept_activService();
					Set<ActivityVO> set = rept_activSvc.getActivitysByActivId(activid);

					/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
					req.setAttribute("listAllRept_activs_ByActivity", set);    // 資料庫取出的set物件,存入request
					System.out.println("*************************明彥快回來");
					String url = null;
					if ("listAllRept_activs_ByActivity_A".equals(action))
						url = "/activity/listAllActivity2.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
					else if ("listAllRept_activs_ByActivity_B".equals(action))
						url = "/activity/listAllActivity.jsp";              // 成功轉交 dept/listAllDept.jsp

					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					return;
					/*************************** 其他可能的錯誤處理 ***********************************/
				} catch (Exception e) {
					throw new ServletException(e);
				}
			}
		
		
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String activ_id_search = req.getParameter("activ_id");
				System.out.println(activ_id_search);
				
				if (activ_id_search == null || (activ_id_search.trim()).length() == 0) {
					errorMsgs.add("請輸入活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/rept_activ/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				String activ_id__search = null;
				try {
					activ_id__search = new String(activ_id_search);
				} catch (Exception e) {
					errorMsgs.add("活動編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/rept_activ/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Rept_activService rept_activSvc = new Rept_activService();
				Rept_activVO rept_activVO = rept_activSvc.getOneRept_activ(activ_id_search);
				System.out.println("彥彥yoooo3"+activ_id_search);
				if (rept_activVO == null) {
					System.out.println("明彥不要躲");
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/rept_activ/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("rept_activVO", rept_activVO); // 資料庫取出的rept_activVOVO物件,存入req
				String url = "/rept_activ/listOneRept_activ.jsp";
				System.out.println("明彥出來喔");
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneRept_activ.jsp
				System.out.println("明彥出來喔~~~~~~~~~~~~~~~~");
				successView.forward(req, res);
				System.out.println("明彥出來喔~~喔喔喔喔喔");
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/rept_activ/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
        	List<String> errorMsgs = new LinkedList<String>();			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			System.out.println("彥彥出來");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			if (memberVO != null) { 
				System.out.println("走這?");
				mem_id = memberVO.getMem_id();
			} else {
				mem_id = req.getParameter("mem_id").trim();
			}
			
			String activ_id = req.getParameter("activ_id").trim();
			Rept_activService rept_activSvc = new Rept_activService();
			String rept = null;
			if (rept_activSvc.getOneRept_activ(activ_id) == null) {
				rept = "123";
			} else {
				rept = rept_activSvc.getOneRept_activ(activ_id).getMem_id();
			}
			System.out.println(rept + "11111111111111");
			if (mem_id.equals(rept)) {
				errorMsgs.add("檢舉只能檢一ㄘ喔");
				System.out.println("有近來嗎?");
			}
			String repo_rsn = req.getParameter("repo_rsn").trim();
			Integer repo_rev = new Integer(req.getParameter("repo_rev").trim());
			System.out.println("躲是躲不掉的" + activ_id);

			Rept_activVO rept_activVO = new Rept_activVO(activ_id, mem_id, repo_rsn, repo_rev);
			rept_activVO.setActiv_id(activ_id);
			rept_activVO.setMem_id(mem_id);
			rept_activVO.setRepo_rsn(repo_rsn);
			rept_activVO.setRepo_rev(repo_rev);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				// 重複檢舉會跑這邊
				ActivityService activitySvc = new ActivityService();
				ActivityVO activityVO =activitySvc.getOneActivityOne(activ_id);
				req.setAttribute("activityVO", activityVO); // 資料庫取出的rept_activVOVO物件,存入req
				String url = requestURL;
				RequestDispatcher failureView = req.getRequestDispatcher(url);//重複檢舉,轉送回原頁面
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
//			Rept_activService rept_activSvc = new Rept_activService();
			rept_activVO = rept_activSvc.addRept_activ(activ_id, mem_id, repo_rsn, repo_rev);
			
			ActivityService activitySvc = new ActivityService();
			ActivityVO activityVO =activitySvc.getOneActivityOne(activ_id);
			/***************************
			 * 3.新增完成,準備轉交(Send the Success view)
			 ***********/
			req.setAttribute("activityVO", activityVO); // 資料庫取出的rept_activVOVO物件,存入req

			String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			return;
			/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			String url = requestURL;
			RequestDispatcher failureView = req.getRequestDispatcher(url);//重複檢舉,轉送回原頁面
			failureView.forward(req, res);
			return;
        	}
		}
		
		
        if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求
        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				String activid = req.getParameter("activ_id");
				String memid = req.getParameter("mem_id");
				System.out.println(activid);
				System.out.println(memid);
				/***************************2.開始刪除資料***************************************/
				Rept_activService rept_activSvc = new Rept_activService();
				Rept_activVO rept_activVO = rept_activSvc.getOneRept_activ(activid);
				rept_activSvc.deleteRept_activ(activid,memid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				String url = "/backend/rept_activ/rept_activ.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				return;
			}
		}
    	if ("update".equals(action)) { // 來自update_activity_input.jsp的請求

    		List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			System.out.println("有近來?");
//			 try {
			/***************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			String activid = req.getParameter("activ_id").trim();
			String memid = req.getParameter("mem_id").trim();
			String repo_rsn = req.getParameter("repo_rsn").trim();
			Integer repo_rev = new Integer(req.getParameter("repo_rev").trim());
			
			
			
			Rept_activVO rept_activVO = new Rept_activVO();
			rept_activVO.setActiv_id(activid);
			rept_activVO.setMem_id(memid);
			rept_activVO.setRepo_rsn(repo_rsn);
			rept_activVO.setRepo_rev(repo_rev);
			
			System.out.println(activid);
			System.out.println(memid);
			System.out.println(repo_rsn);
			System.out.println(repo_rev);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("rept_activVO", rept_activVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/backend/rept_activ.jsp");
				failureView.forward(req, res);
				return;
			}
			/*************************** 2.開始修改資料 *****************************************/
			
			Rept_activService rept_activSvc = new Rept_activService();
			rept_activVO = rept_activSvc.updateRept_activ(activid, memid, repo_rsn, repo_rev);
			
			/***************************
			 * 3.修改完成,準備轉交(Send the Success view)
			 *************/
			System.out.println("有到這?");

			req.setAttribute("rept_activVO", rept_activVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			return;
			/*************************** 其他可能的錯誤處理 *************************************/
//			 } catch (Exception e) {
//				 errorMsgs.add("修改資料失敗:"+e.getMessage());
//					RequestDispatcher failureView = req
//							.getRequestDispatcher(requestURL);
//					failureView.forward(req, res);
//			 }
		}

		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣
	}
}
