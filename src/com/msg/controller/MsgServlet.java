package com.msg.controller;

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
import javax.servlet.http.Part;

import com.member.model.MemberVO;
import com.msg.model.MsgService;
import com.msg.model.MsgVO;


public class MsgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		HttpSession session = req.getSession();
		
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String msg_id = req.getParameter("msg_id");
				if (msg_id == null || (msg_id.trim()).length() == 0) {
					errorMsgs.add("請輸入留言編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MsgService msgSvc = new MsgService();
				MsgVO msgVO = msgSvc.getOneMsg(msg_id);
				if (msgVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("msgVO", msgVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/msg/listOneMsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
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
		} // if ("getOne_For_Display".equals(action))
		
		if ("listReplys_ByMsg_id".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String msg_id = req.getParameter("msg_id");

				/*************************** 2.開始查詢資料 ****************************************/
				MsgService msgSvc = new MsgService();
				MsgVO msgVO = msgSvc.getOneMsg(msg_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("msgVO", msgVO);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listReplys_ByMsg_id".equals(action))
					url = "/frontend/msg/listReplys_ByMsg_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
//				else if ("listEmps_ByDeptno_B".equals(action))
//					url = "/dept/listAllDept.jsp";              // 成功轉交 dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} // if ("listReplys_ByMsg_id".equals(action))
		
		if ("getOne_For_Insert".equals(action)) { // 來自select_page.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			//String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
			
			try {
				/***************************1.接收請求參數****************************************/
				String prod_id = req.getParameter("prod_id");
				if(prod_id.isEmpty()){
					errorMsgs.add("請輸入商品編號");
				}
				
				/***************************3.準備轉交(Send the Success view)************/
				String url = "/frontend/msg/addMsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交addMsg.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		} // if ("getOne_For_Insert".equals(action)
		

		if ("insert".equals(action)) { // 來自addMsg.jsp的請求
			
			MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
			if(memberVO == null){
				System.out.println("NULLLLLLLLLLLLLLLLLLLLL");
			}
			String mem_id = memberVO.getMem_id();

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = "/frontend/product/product.do?action=getOne_For_Display";
			
			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String prod_id = req.getParameter("prod_id").trim();

				String msg_content = req.getParameter("msg_content").trim();
				if (msg_content.isEmpty()) {
					errorMsgs.add("請輸入留言內容.");
				}

				Timestamp msg_date = new Timestamp(System.currentTimeMillis());

				MsgVO msgVO = new MsgVO();
				msgVO.setMem_id(mem_id);
				msgVO.setProd_id(prod_id);
				msgVO.setMsg_content(msg_content);
				msgVO.setMsg_date(msg_date);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("msgVO", msgVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				MsgService msgSvc = new MsgService();
				msgVO = msgSvc.addMsg(mem_id, prod_id, msg_content, msg_date);

				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/
				RequestDispatcher successView = req.getRequestDispatcher(requestURL); // 新增成功後轉交listAllProduct.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				return;
			}
		} // if ("insert".equals(action))
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		
	}

}
