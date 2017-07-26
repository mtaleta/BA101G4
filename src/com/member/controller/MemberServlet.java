
package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.activity.model.ActivityVO;
import com.member.model.MemberDAO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.participant.model.ParticipantVO;
import com.tools.HttpUtils;
import com.tools.JavamailSender;
import com.tools.MD5Util;
import com.tools.Pic;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String mem_id = null;
				boolean isMemberLogin = false;
				if (HttpUtils.isFrontend(req) && req.getSession().getAttribute("MEMBER") != null) {
					// 代表是店家會員登入使用
					MemberVO memberFromSession = (MemberVO) req.getSession().getAttribute("MEMBER");
					mem_id = memberFromSession.getMem_id();
					isMemberLogin = true;
				} else if (HttpUtils.isBackend(req)) {
					// 代表是管理者
					mem_id = req.getParameter("mem_id").trim();
				} else {
					throw new Exception("查無會員資料!!!");
				}
				if (!errorMsgs.isEmpty()) {
					HttpUtils.smartForward("/member/select_page.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(mem_id);

				if (memberVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					HttpUtils.smartForward("/member/listOneMember.jsp", req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				if (isMemberLogin) {
					req.getSession().setAttribute("MEMBER", memberVO);
				} else {
					req.setAttribute("memberVO", memberVO);
				}
				HttpUtils.smartForward("/member/listOneMember.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				HttpUtils.smartForward("/index.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isBackend(req) && "getAll".equals(action)) {
			/*************************** 開始查詢資料 ****************************************/
			MemberDAO dao = new MemberDAO();
			List<MemberVO> list = dao.getAll();

			/***************************
			 * 查詢完成,準備轉交(Send the Success view)
			 *************/
			HttpSession session = req.getSession();
			session.setAttribute("list", list); // 資料庫取出的list物件,存入session
			// Send the Success view
			HttpUtils.smartForward("/member/listAllMember.jsp", req, res);
			return;
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllMember.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String mem_id = null;
				boolean isMemberLogin = false;
				if (HttpUtils.isFrontend(req) && req.getSession().getAttribute("MEMBER") != null) {
					// 代表是店家會員登入使用
					MemberVO memberFromSession = (MemberVO) req.getSession().getAttribute("MEMBER");
					mem_id = memberFromSession.getMem_id();
					isMemberLogin = true;
					System.out.println();
				} else if (HttpUtils.isBackend(req)) {
					// 代表是管理者
					mem_id = req.getParameter("mem_id").trim();
				} else {
					throw new Exception("查無會員資料!!!");
				}
				/*************************** 2.開始查詢資料 ****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(mem_id);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				if (isMemberLogin) {
					req.getSession().setAttribute("MEMBER", memberVO);
				} else {
					req.setAttribute("memberVO", memberVO);
				}
				if ("getOne_For_Update".equals(action))
					HttpUtils.smartForward("/member/update_member_input.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				e.printStackTrace();
				HttpUtils.smartForward("/member/listOneMember.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("update".equals(action)) { // 來自update_member_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			MemberVO memberVO = new MemberVO();
			MemberService memberSvc = new MemberService();
			try {
				String mem_id = null;
				boolean isMemberLogin = false;
				if (HttpUtils.isFrontend(req) && req.getSession().getAttribute("MEMBER") != null) {
					// 代表是店家會員登入使用
					MemberVO memberFromSession = (MemberVO) req.getSession().getAttribute("MEMBER");
					mem_id = memberFromSession.getMem_id();
					isMemberLogin = true;
				} else if (HttpUtils.isBackend(req)) {
					// 代表是管理者
					mem_id = req.getParameter("mem_id").trim();
				} else {
					throw new Exception("查無會員資料!!!");
				}
				String mem_name = req.getParameter("mem_name").trim();
				String mem_tel = req.getParameter("mem_tel").trim();
				String mem_email = req.getParameter("mem_email").trim();
				String mem_add = HttpUtils.getParameter(req, "mem_add");
				Integer mem_points = new Integer(req.getParameter("mem_points").trim());

				memberVO.setMem_id(mem_id);
				memberVO.setMem_name(mem_name);
				memberVO.setMem_tel(mem_tel);
				memberVO.setMem_email(mem_email);
				memberVO.setMem_add(mem_add);
				memberVO.setMem_points(mem_points);
				Part part = req.getPart("upfile1");
				if (!Pic.noFileSelected(part)) {
					memberVO.setMem_img(Pic.getPictureByteArray(part));
				} else {
					memberVO.setMem_img(memberSvc.getOneMember(mem_id).getMem_img());
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的MemberVO物件,也存入req
					HttpUtils.smartForward("/member/update_member_input.jsp", req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/

				memberVO = memberSvc.updateMember(mem_id, mem_name, mem_tel, mem_email, mem_add, mem_points,
						memberVO.getMem_img());

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				if (isMemberLogin) {
					req.getSession().setAttribute("MEMBER", memberVO);
				} else {
					req.setAttribute("memberVO", memberVO);
				}
				HttpUtils.smartForward("/member/listOneMember.jsp", req, res);
				return;// 程式中斷
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/member/update_member_input.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isFrontend(req) && "insert".equals(action)) { // 來自addMember.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				HttpUtils.getParameter(req, "mem_add");
				String mem_acct = req.getParameter("mem_acct").trim();
				String mem_pwd = req.getParameter("mem_pwd").trim();
				String mem_name = req.getParameter("mem_name").trim();
				String mem_tel = req.getParameter("mem_tel").trim();
				String mem_email = req.getParameter("mem_email").trim();
				String mem_add = HttpUtils.getParameter(req, "mem_add");
				Integer mem_points = new Integer(req.getParameter("mem_points").trim());
				MemberService memberSvc = new MemberService();

				MemberVO memberVO = new MemberVO();
				MemberVO memberVO1 = memberSvc.getOneMemberByAccount(mem_acct);

				if (memberVO1 == null) {
					memberVO.setMem_acct(mem_acct);
					memberVO.setMem_pwd(mem_pwd);
					memberVO.setMem_name(mem_name);
					memberVO.setMem_tel(mem_tel);
					memberVO.setMem_email(mem_email);
					memberVO.setMem_add(mem_add);
					memberVO.setMem_points(mem_points);
					Part part = req.getPart("upfile1");
					if (!Pic.noFileSelected(part)) {
						memberVO.setMem_img(Pic.getPictureByteArray(part));
					}
					memberVO.setMem_validateCode(MD5Util.encode2hex(mem_email));
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的MemberVO物件,也存入req
						HttpUtils.smartForward("/member/addMember.jsp", req, res);
						return;
					}
					/*************************** 2.開始新增資料 ***************************************/
					memberVO = memberSvc.addMember(mem_acct, mem_pwd, mem_name, mem_tel, mem_email, mem_add, mem_points,
							memberVO.getMem_img(), memberVO.getMem_validateCode());
					/***************************
					 * 3.新增完成,準備轉交(Send the Success view)
					   ***********/
					String mem_context = new String("請點選以下驗證連結~~!!");
					mem_context = mem_context + String.valueOf(
							"<a href='http://localhost:8081/" + req.getContextPath() + "/frontend/member/member.do?action=MEM_AUTHENTICATION&mail="
									+ memberVO.getMem_email() + "&validateCode=" + memberVO.getMem_validateCode()
									+ "&account=" + memberVO.getMem_acct() + "'>點我點我</a>");
					JavamailSender.send(mem_acct, mem_email, mem_context);
					HttpUtils.smartForward("/member/member_unauths.jsp", req, res);
					return;// 程式中斷
				} else {
					HttpUtils.smartForward("/member/login-alreadyExist.jsp", req, res);
					return;
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				HttpUtils.smartForward("/member/addMember.jsp", req, res);
			}
			return;// 程式中斷
		}

		if (HttpUtils.isBackend(req) && "delete".equals(action)) { // 來自listAllMember.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String mem_id = new String(req.getParameter("mem_id"));

				/*************************** 2.開始刪除資料 ***************************************/
				MemberService memberSvc = new MemberService();
				memberSvc.deleteMember(mem_id);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				HttpUtils.smartForward("/member/listAllMember.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/member/listAllMember.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("Login_Member".equals(action)) {

			String mem_acct = req.getParameter("mem_acct");
			String mem_pwd = req.getParameter("mem_pwd");

			MemberService memberSvc = new MemberService();
			MemberVO memberVO_login = memberSvc.Login_Member(mem_acct, mem_pwd);
			if (memberVO_login != null) {
				if (memberVO_login != null && (memberVO_login.getMem_authentication() != null
						&& memberVO_login.getMem_authentication() == 1)) {
					HttpUtils.clearSessionMember(req);
					HttpSession session = req.getSession();
					// 已通過認證會員
					HttpUtils.registerSessionMember(req, memberVO_login);
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location"); // *工作2: 看看有無來源網頁
						res.sendRedirect(location);
						return;
					} else {
						HttpUtils.smartForward("/index.jsp", req, res);
						return;// 程式中斷
					}
				} else if (memberVO_login != null && (memberVO_login.getMem_authentication() == null
						|| memberVO_login.getMem_authentication() != 1)) {
					HttpUtils.smartForward("/member/member_unauths.jsp", req, res);
					return;// 程式中斷
				}
			} else {
				HttpUtils.smartForward("/member/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("MEM_AUTHENTICATION".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				System.out.println("sssssssssssssss");
				String mem_acct = req.getParameter("account");
				String mem_email = req.getParameter("mail");
				String mem_validateCode = req.getParameter("validateCode");

				MemberService memberSvc = new MemberService();

				MemberVO memberVO = memberSvc.getOneMemberByAccount(mem_acct);
				if (memberVO.getMem_email().equals(mem_email)
						&& memberVO.getMem_validateCode().equals(mem_validateCode)) {
					// 驗證成功

					/*************************** 2.開始修改資料 *****************************************/
					int mem_authentication = 1;
					memberVO.setMem_authentication(mem_authentication);
					memberSvc.mem_Authentication(mem_acct, mem_authentication);
					System.out.println(memberVO.getMem_authentication());
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					HttpUtils.smartForward("/member/login_Member.jsp", req, res);
					return;// 程式中斷
				} else {
					// 驗證失敗
					HttpUtils.smartForward("/member/login_errorAuth.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/member/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}
		if ("MEM_FORGET_PASSWORD".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String mem_acct = req.getParameter("mem_acct");
				String mem_email = req.getParameter("mem_email");

				MemberService memberSvc = new MemberService();

				MemberVO memberVO = memberSvc.getOneMemberByAccount(mem_acct);

				if (memberVO.getMem_email().equals(mem_email) && memberVO.getMem_acct().equals(mem_acct)) {
					// 驗證成功

					/*************************** 2.開始修改資料 *****************************************/
					String mem_pwd = UUID.randomUUID().toString().substring(0, 8);
					memberVO.setMem_pwd(mem_pwd);
					memberSvc.mem_Forget_Password(mem_acct, mem_pwd);
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					req.setAttribute("memberVO", memberVO);
					req.getSession().setAttribute("MEMBER", memberVO);

					String mem_context = new String("請複製以下密碼進行登入~~!!");
					mem_context = mem_context + String.valueOf("<br>" + "新密碼為: " + mem_pwd);
					JavamailSender.send(mem_acct, mem_email, mem_context);

					HttpUtils.smartForward("/member/login_Member.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/member/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}
		if ("UPDATE_MEMBER_PASSWORD".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				MemberVO memberFromSession = (MemberVO) req.getSession().getAttribute("MEMBER");
				String mem_acct = memberFromSession.getMem_acct();
				String newPassword = req.getParameter("newPassword").trim();
				String newPassword2 = req.getParameter("newPassword2").trim();

				if (newPassword == null || "".equals(newPassword)) {
					errorMsgs.add("新密碼不能為空！");
				}

				if (newPassword2 == null || "".equals(newPassword2)) {
					errorMsgs.add("確認新密碼不能為空！");
				}

				if (!newPassword.equals(newPassword2)) {
					errorMsgs.add("兩次輸入的密碼不一致");
				}
				if (newPassword.equals(newPassword2)) {

					MemberService memberSvc = new MemberService();
					MemberVO memberVO = new MemberVO();
					memberVO.setMem_acct(mem_acct);
					memberVO.setMem_pwd(newPassword);
					memberSvc.mem_Forget_Password(mem_acct, newPassword);
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					req.setAttribute("memberVO", memberVO);
					req.getSession().setAttribute("MEMBER", memberVO);
					HttpUtils.clearSessionMember(req);
					req.setAttribute("url", HttpUtils.getSmartUrl("/member/login_Store.jsp", req));
					HttpUtils.smartForward("/member/login_Member.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/member/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}

		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		// 會員舉辦多少活動
		if ("getActivity_For_Mem_id".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO) session.getAttribute("MEMBER");
				String mem_id = memberVO.getMem_id();
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memberSvc = new MemberService();
				Set<ActivityVO> set = memberSvc.getActivitysByMem_id(mem_id);
				if (set == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("activityVO", set); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/activity/adminActivity.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		// 會員查詢活動資料
		if ("getOne_For_Mem_id".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO) session.getAttribute("MEMBER");
				String mem_id = memberVO.getMem_id();
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				// String str = req.getParameter("mem_id");
				// if (str == null || (str.trim()).length() == 0) {
				// errorMsgs.add("請輸入會員編號");
				// }
				// // Send the use back to the form, if there were errors
				// if (!errorMsgs.isEmpty()) {
				// RequestDispatcher failureView =
				// req.getRequestDispatcher("/member/select_page.jsp");
				// failureView.forward(req, res);
				// return;//程式中斷
				// }

				// 7
				try {
					// mem_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("AAA");
				MemberService memberSvc = new MemberService();
				System.out.println("BBB");
				Set<ParticipantVO> set = memberSvc.getParticipantsByMem_id(mem_id);
				System.out.println("CCC");
				if (set == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/**************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("participantVO", set); // 資料庫取出的empVO物件,存入req
				System.out.println("DDDDDDDDD");
				String url = "/frontend/activity/myActivity.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				System.out.println("EEEEEEEEEEEEEEEE");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 儲值點數
		if ("topUp".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("MEMBER");
			
			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String[] card_no = req.getParameterValues("card_no");
				for(String no : card_no){
					if(no.trim().length() != 4){
						errorMsgs.add("卡號錯誤.");
						break;
					}
				}
				
				String card_month = req.getParameter("card_month").trim();
				try{
					Integer month = new Integer(card_month);
					if(card_month.length() != 2){
						errorMsgs.add("到期日格式錯誤.");
					}
					else if(month > 12 || month < 1){
						errorMsgs.add("到期日錯誤.");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("到期日格式錯誤.");
				}
				
				String card_year = req.getParameter("card_year").trim();
				try{
					Integer year = new Integer(card_year);
					if(card_year.length() != 2){
						errorMsgs.add("到期日格式錯誤.");
					}
					else if(year < 0){
						errorMsgs.add("到期日錯誤.");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("到期日格式錯誤.");
				}

				String card_check = req.getParameter("card_check").trim();
				try{
					new Integer(card_check);
					if(card_check.length() != 3){
						errorMsgs.add("驗證碼錯誤.");
					}
				} catch (NumberFormatException e) {
					errorMsgs.add("驗證碼格式錯誤.");
				}

				Integer mem_points = null;
				try{
					mem_points = new Integer(req.getParameter("mem_points"));
				} catch(NumberFormatException e) {
					mem_points = 1000;
					errorMsgs.add("儲值金額請填數字.");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/member/topUp.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 開始查詢資料 ****************************************/
				String mem_id = memberVO.getMem_id();
				
				MemberService memSvc = new MemberService();
				memberVO = memSvc.getOneMember(mem_id);

				String mem_acct = memberVO.getMem_acct();
				String mem_pwd = memberVO.getMem_pwd();
				String mem_name = memberVO.getMem_name();
				String mem_tel =  memberVO.getMem_tel();
				String mem_email = memberVO.getMem_email();
				String mem_add = memberVO.getMem_add();
				mem_points = memberVO.getMem_points() + mem_points;
				byte[] mem_img = memberVO.getMem_img();
				Integer mem_authentication = memberVO.getMem_authentication();
				String mem_validatecode = memberVO.getMem_validateCode();

				/*************************** 開始修改資料 *****************************************/
				memberVO = memSvc.updateMember(mem_id, mem_acct, mem_pwd, mem_name, mem_tel, mem_email, mem_add, mem_points, mem_img, mem_authentication, mem_validatecode);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/

				RequestDispatcher successView = req.getRequestDispatcher(requestURL);
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/member/topUp.jsp");
				failureView.forward(req, res);
				return;
			}
		} // if("topUp".equals(action))
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		// 強禾 強禾 強禾
		
		HttpUtils.smartForward("/index.jsp", req, res);
		return;
	}
}
