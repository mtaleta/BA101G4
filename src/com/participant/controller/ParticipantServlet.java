package com.participant.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.activity.model.ActivityService;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.participant.model.*;
import com.participant.model.ParticipantService;
import com.participant.model.ParticipantVO;
import com.tools.MailService;

public class ParticipantServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
		String mem_id = memberVO.getMem_id();
//		String mem_id = (String)session.getAttribute("mem_id");
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String activ = req.getParameter("activ_id");
				
				if (activ == null || (activ.trim()).length() == 0) {
					errorMsgs.add("請輸入活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/participant/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String activ_id = null;
				try {
					activ_id = new String(activ);
				} catch (Exception e) {
					errorMsgs.add("活動編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/participant/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				
				/***************************2.開始查詢資料*****************************************/
				ParticipantService participantSvc = new ParticipantService();
				ParticipantVO participantVO = participantSvc.getOneParticipant(activ_id);
				if (participantVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/participant/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("participantVO", participantVO); // 資料庫取出的empVO物件,存入req
				String url = "/participant/listOneParticipant.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/participant/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}		
		
		 if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
					System.out.println("明彥不要躲");
				try {
					/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
					String activ_id = req.getParameter("activ_id").trim();
//					String mem_id = req.getParameter("mem_id").trim();
					System.out.println("明彥快出來了"+activ_id+mem_id);
					ParticipantVO participantVO = new ParticipantVO(activ_id, mem_id);
					participantVO.setActiv_id(activ_id);
					participantVO.setMem_id(mem_id);
					

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("participantVO", participantVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/participant/addParticipant.jsp");
						failureView.forward(req, res);
						return;
					}
					/***************************2.開始新增資料***************************************/
					ParticipantService participantSvc = new ParticipantService();
					participantVO = participantSvc.addParticipant(activ_id, mem_id);
					
					/***************************2.5傳送mail***************************************/
					
					
					

					MailService mailService = new MailService();
					MemberService memberService = new MemberService();
//					memberService.getOneMember(mem_id).getMem_email();
					ActivityService activityService = new ActivityService();
					
					String to = memberService.getOneMember(mem_id).getMem_email();
			      
					String subject = "活動報名成功通知";
				      
					String ch_name = memberService.getOneMember(mem_id).getMem_name();
					String ch_activ_name = activityService.getOneActivityOne(activ_id).getActiv_name();
					Timestamp ch_activstarttime = activityService.getOneActivityOne(activ_id).getActiv_starttime();
					
					String messageText = "Hello! " + ch_name + "\n" + "您所報名的活動：" + ch_activ_name + "\n" + "將於：" + ch_activstarttime +"開始，請務必記得前往參與活動。"; 
				       
					mailService.sendMail(to, subject, messageText);
					
					
					
					
					
					/***************************3.新增完成,準備轉交(Send the Success view)***********/
					String url = "/frontend/activity/activityJoin.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
					successView.forward(req, res);				
					return;
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/participant/addParticipant.jsp");
					failureView.forward(req, res);
					return;
				}
			}
			
			
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				String requestURL = req.getParameter("requestURL"); 
		
				try {
					/***************************1.接收請求參數***************************************/
					String activ_id = req.getParameter("activ_id");
//					String mem_id = req.getParameter("mem_id");
					System.out.println("彥彥是這邊嗎?" + activ_id);
					
					/***************************2.開始刪除資料***************************************/
					ParticipantService participantSvc = new ParticipantService();
					ParticipantVO participantVO = participantSvc.getOneParticipant(activ_id, mem_id);
					participantSvc.deleteParticipant(activ_id,mem_id);
					
					/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
					req.setAttribute("ParticipantVO", participantVO);
					String url = requestURL;
					RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
					successView.forward(req, res);
					return;
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/participant/listAllParticipant.jsp");
					failureView.forward(req, res);
					return;
				}
		}
	}
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
}
