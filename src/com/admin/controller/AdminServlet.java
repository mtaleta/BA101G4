package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.admin.model.AdminDAO;
import com.admin.model.AdminService;
import com.admin.model.AdminVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.store.model.StoreVO;
import com.tools.HttpUtils;
import com.tools.JavamailSender;
import com.tools.Pic;

import oracle.net.ano.AuthenticationService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		String action = req.getParameter("action");

		if (HttpUtils.isBackend(req) && "getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String admin_id = null;
				boolean isAdminLogin = false;
				if (HttpUtils.isBackend(req) && req.getSession().getAttribute("ADMIN") != null) {
					AdminVO storeFromSession = (AdminVO) req.getSession().getAttribute("ADMIN");
					admin_id = storeFromSession.getAdmin_id();
					isAdminLogin = true;
				} else if (HttpUtils.isBackend(req)) {
					admin_id = req.getParameter("admin_id").trim();
				} else {
					throw new Exception("查無管理者資料!!!");
				}

				if (!errorMsgs.isEmpty()) {
					HttpUtils.smartForward("/admin/listOneAdmin.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(admin_id);

				if (adminVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					HttpUtils.smartForward("/admin/listOneAdmin.jsp", req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				if (isAdminLogin) {
					req.getSession().setAttribute("ADMIN", adminVO);
				} else {
					req.setAttribute("adminVO", adminVO);
				}

				HttpUtils.smartForward("/admin/listOneAdmin.jsp", req, res);
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
			AdminDAO dao = new AdminDAO();
			List<AdminVO> list = dao.getAll();

			/***************************
			 * 查詢完成,準備轉交(Send the Success view)
			 *************/
			HttpSession session = req.getSession();
			session.setAttribute("list", list); // 資料庫取出的list物件,存入session
			// Send the Success view
			HttpUtils.smartForward("/admin/listAllAdmin.jsp", req, res);
			return;
		}

		if (HttpUtils.isBackend(req) && "getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String admin_id = null;
				 if (HttpUtils.isBackend(req)) {
					admin_id = req.getParameter("admin_id").trim();
				} else {
					throw new Exception("查無管理者資料!!!");
				}

				/*************************** 2.開始查詢資料 ****************************************/
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(admin_id);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/		
					req.setAttribute("adminVO", adminVO);
					HttpUtils.smartForward("/admin/update_admin_input.jsp", req, res);
					return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());

				HttpUtils.smartForward("/admin/listOneAdmin.jsp", req, res);
				return;// 程式中斷
			}
		}
		
		if (HttpUtils.isBackend(req) && "getOne_For_Update_personal".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String admin_id = null;
				if (HttpUtils.isBackend(req) && req.getSession().getAttribute("ADMIN") != null) {
					AdminVO storeFromSession = (AdminVO) req.getSession().getAttribute("ADMIN");
					admin_id = storeFromSession.getAdmin_id();
				} else if (HttpUtils.isBackend(req)) {
					admin_id = req.getParameter("admin_id").trim();
				} else {
					throw new Exception("查無管理者資料!!!");
				}

				/*************************** 2.開始查詢資料 ****************************************/
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(admin_id);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
					req.getSession().setAttribute("ADMIN", adminVO);
					HttpUtils.smartForward("/admin/update_admin_input_personal.jsp", req, res);
					return;// 程式中斷					
				

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());

				HttpUtils.smartForward("/admin/listOneAdmin_personal.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isBackend(req) && "update".equals(action)) { // 來自update_admin_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			AdminService adminSvc = new AdminService();

			try {
				String admin_id = null;
				if (HttpUtils.isBackend(req)) {
					admin_id = req.getParameter("admin_id").trim();
				} else {
					throw new Exception("查無管理者資料!!!");
				}
				String admin_acct = req.getParameter("admin_acct").trim();
				String admin_name = req.getParameter("admin_name").trim();
				String admin_email = req.getParameter("admin_email").trim();
				Integer admin_employed = new Integer(req.getParameter("admin_employed").trim());
				String[] feature = req.getParameterValues("feature");

				AdminVO adminVO = new AdminVO();
				adminVO.setAdmin_id(admin_id);
				adminVO.setAdmin_acct(admin_acct);
				adminVO.setAdmin_name(admin_name);
				adminVO.setAdmin_email(admin_email);
				adminVO.setAdmin_employed(admin_employed);

				Part part = req.getPart("upfile1");
				if (!Pic.noFileSelected(part)) {
					adminVO.setAdmin_img(Pic.getPictureByteArray(part));
				} else {
					adminVO.setAdmin_img(adminSvc.getOneAdmin(admin_id).getAdmin_img());
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminVO", adminVO); // 含有輸入格式錯誤的adminVO物件,也存入req
					HttpUtils.smartForward("/admin/update_admin_input.jsp", req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/

				adminVO = adminSvc.updateAdmin(admin_id, admin_acct, admin_name, admin_email, admin_employed,
						adminVO.getAdmin_img(), feature);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("adminVO", adminVO);
				HttpUtils.smartForward("/admin/listOneAdmin.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/admin/update_admin_input.jsp", req, res);
				return;// 程式中斷
			}
		}
		
		if (HttpUtils.isBackend(req) && "update_admin_personal".equals(action)) { // 來自update_admin_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			AdminService adminSvc = new AdminService();

			try {
				String admin_id = null;
				boolean isAdminLogin = false;
				if (HttpUtils.isBackend(req) && req.getSession().getAttribute("ADMIN") != null) {
					AdminVO storeFromSession = (AdminVO) req.getSession().getAttribute("ADMIN");
					admin_id = storeFromSession.getAdmin_id();
					isAdminLogin = true;
				} else if (HttpUtils.isBackend(req)) {
					admin_id = req.getParameter("admin_id").trim();
				} else {
					throw new Exception("查無管理者資料!!!");
				}
				String admin_name = req.getParameter("admin_name").trim();
				String admin_email = req.getParameter("admin_email").trim();

				AdminVO adminVO = new AdminVO();
				adminVO.setAdmin_id(admin_id);
				adminVO.setAdmin_name(admin_name);
				adminVO.setAdmin_email(admin_email);

				Part part = req.getPart("upfile1");
				if (!Pic.noFileSelected(part)) {
					adminVO.setAdmin_img(Pic.getPictureByteArray(part));
				} else {
					adminVO.setAdmin_img(adminSvc.getOneAdmin(admin_id).getAdmin_img());
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminVO", adminVO); // 含有輸入格式錯誤的adminVO物件,也存入req
					HttpUtils.smartForward("/admin/update_admin_input_personal.jsp", req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/

				adminVO = adminSvc.update_admin_personal(admin_id, admin_name, admin_email, adminVO.getAdmin_img());

				if (isAdminLogin) {
					req.getSession().setAttribute("ADMIN", adminVO);
				} else {
					req.setAttribute("adminVO", adminVO);
				}
				HttpUtils.smartForward("/admin/listOneAdmin_personal.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/admin/update_admin_input.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isBackend(req) && "insert".equals(action)) { // 來自addAdmin.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String admin_acct = req.getParameter("admin_acct").trim();
				String admin_pwd = UUID.randomUUID().toString().substring(0, 8);
				String admin_name = req.getParameter("admin_name").trim();
				String admin_email = req.getParameter("admin_email").trim();
				Integer admin_employed = new Integer(req.getParameter("admin_employed").trim());
				String[] feature = req.getParameterValues("feature");

				AdminVO adminVO = new AdminVO();
				adminVO.setAdmin_acct(admin_acct);
				adminVO.setAdmin_pwd(admin_pwd);
				adminVO.setAdmin_name(admin_name);
				adminVO.setAdmin_email(admin_email);
				adminVO.setAdmin_employed(admin_employed);

				Part part = req.getPart("upfile1");
				if (!Pic.noFileSelected(part)) {
					adminVO.setAdmin_img(Pic.getPictureByteArray(part));
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminVO", adminVO); // 含有輸入格式錯誤的adminVO物件,也存入req
					HttpUtils.smartForward("/admin/addAdmin.jsp", req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				AdminService adminSvc = new AdminService();
				adminVO = adminSvc.addAdmin(admin_acct, admin_pwd, admin_name, admin_email, admin_employed,
						adminVO.getAdmin_img(), feature);
				
				String admin_context =null;
				admin_context = "新進的後端管理員同仁  "+adminVO.getAdmin_name() +"您好<br>"
						+"請輸入以下帳號及密碼:<br>"+ "帳號:" + adminVO.getAdmin_acct()+"<br>密碼: "+adminVO.getAdmin_pwd() ;
				JavamailSender.send(admin_acct, admin_email, admin_context);
				

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				HttpUtils.smartForward("/admin/listAllAdmin.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				HttpUtils.smartForward("/admin/addAdmin.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isBackend(req) && "delete".equals(action)) { // 來自listAllAdmin.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String admin_id = new String(req.getParameter("admin_id"));

				/*************************** 2.開始刪除資料 ***************************************/
				AdminService adminSvc = new AdminService();
				adminSvc.deleteAdmin(admin_id);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				HttpUtils.smartForward("/admin/listAllAdmin.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/admin/listAllAdmin.jsp", req, res);
				return;// 程式中斷
			}
		}
		if (HttpUtils.isBackend(req) && "Login_Admin".equals(action)) {

			String admin_acct = req.getParameter("admin_acct");
			String admin_pwd = req.getParameter("admin_pwd");
			AdminVO adminVO = new AdminVO();
			adminVO.setAdmin_acct(admin_acct);
			adminVO.setAdmin_pwd(admin_pwd);

			AdminService adminSvc = new AdminService();
			AdminVO adminVO_login = adminSvc.login_Admin(admin_acct, admin_pwd);

			if (adminVO_login != null) {
				HttpUtils.clearSessionAdmin(req);
				HttpSession session = req.getSession();
				req.getSession().setAttribute("ADMIN", adminVO_login);
				HttpUtils.registerSessionAdmin(req, adminVO_login);
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁
					res.sendRedirect(location);
					return;
				} else {
					req.setAttribute("url", HttpUtils.getSmartUrl("/index.jsp", req));
					HttpUtils.smartForward("/admin/redirect.jsp", req, res);
					return;// 程式中斷
				}
			} else {
				HttpUtils.smartForward("/admin/login-error.jsp", req, res);
				return;// 程式中斷
			}

		}
		if (HttpUtils.isBackend(req) && "UPDATE_ADMIN_PASSWORD".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				
				AdminVO adminFromSession = (AdminVO) req.getSession().getAttribute("ADMIN");
				String admin_acct = adminFromSession.getAdmin_acct();
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

					AdminService adminSvc = new AdminService();
					AdminVO adminVO = new AdminVO();
					adminVO.setAdmin_acct(admin_acct);
					adminVO.setAdmin_pwd(newPassword);
					adminSvc.Admin_Forget_Password(admin_acct, newPassword);
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					req.setAttribute("adminVO", adminVO);
					req.getSession().setAttribute("ADMIN", adminVO);
					HttpUtils.clearSessionAdmin(req);
					req.setAttribute("url", HttpUtils.getSmartUrl("/index.jsp", req));
					HttpUtils.smartForward("/admin/redirect.jsp", req, res);
					
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/admin/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}
		HttpUtils.smartForward("/index.jsp", req, res);
		return;
	}
}
