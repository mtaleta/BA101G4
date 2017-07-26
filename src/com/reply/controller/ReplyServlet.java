package com.reply.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;
import com.msg.model.MsgService;
import com.msg.model.MsgVO;
import com.reply.model.ReplyService;
import com.reply.model.ReplyVO;
import com.store.model.StoreVO;


public class ReplyServlet extends HttpServlet {
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

		if ("getOne_For_Insert".equals(action)) { // 來自select_page.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			//String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
			
			try {
				/***************************1.接收請求參數****************************************/
				String msg_id = req.getParameter("msg_id");
				if(msg_id.isEmpty()){
					errorMsgs.add("請輸入留言編號");
				}
				
				/***************************2.開始查詢資料****************************************/
				MsgService msgSvc = new MsgService();
				MsgVO msgVO = msgSvc.getOneMsg(msg_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("msgVO", msgVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/reply/addReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交addMsg.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		} // if ("getOne_For_Insert".equals(action)
		

		if ("insert".equals(action)) { // 來自addReply.jsp的請求

			MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
			String mem_id = null;
			if(memberVO != null){
				mem_id = memberVO.getMem_id();
			}
			
			StoreVO storeVO = (StoreVO)session.getAttribute("STORE");
			String store_id = null;
			if(storeVO != null){
				store_id = storeVO.getStore_id();
			}

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String msg_id = req.getParameter("msg_id").trim();

			MsgService msgSvc = new MsgService();
			MsgVO msgVO = msgSvc.getOneMsg(msg_id);
			
			String url = "/frontend/product/product.do?action=getOne_For_Display&prod_id=" + msgVO.getProd_id();

			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String reply_content = req.getParameter("reply_content").trim();
				if (reply_content.isEmpty()) {
					errorMsgs.add("請輸入回覆內容.");
				}

				Timestamp reply_date = new Timestamp(System.currentTimeMillis());

				ReplyVO replyVO = new ReplyVO();
				if (mem_id != null) {
					replyVO.setMem_id(mem_id);
				}
				else if (store_id != null) {
					replyVO.setStore_id(store_id);
				}
				replyVO.setMsg_id(msg_id);
				replyVO.setReply_content(reply_content);
				replyVO.setReply_date(reply_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("replyVO", replyVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ReplyService replySvc = new ReplyService();
				replyVO = replySvc.addReply(msg_id, mem_id, store_id, reply_content, reply_date);

				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProduct.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // if ("insert".equals(action))
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
	}

}
