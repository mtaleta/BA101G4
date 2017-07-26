package com.activity.controller;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.participant.model.ParticipantService;
import com.participant.model.ParticipantVO;
import com.rept_activ.model.Rept_activService;
import com.rept_activ.model.Rept_activVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;
import com.tools.Pic;
import org.apache.catalina.Store;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class ActivityServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣
		
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		// res.setContentType("application/x-www-form-urlencoded");

		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("MEMBER");
		String mem_id;
		
		// json測試
		if ("getJson".equals(req.getParameter("action"))) {
			String store_id = req.getParameter("store_id");
			StoreService storeSvc = new StoreService();
			System.out.println("到");
			Set<StoreVO> Set = storeSvc.getStoresByStore_id(store_id);
			req.setAttribute("storeVO", Set);

			System.out.println("u.有到這?");
			JSONArray array = new JSONArray();
			for (StoreVO usb : Set) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("name", usb.getStore_name());
//					obj.put("img", usb.getStore_img());
//					obj.put("tel", usb.getStore_tel());
					obj.put("lon", usb.getLongitude());
					obj.put("lat", usb.getLatitude());
				} catch (Exception e) {
				}
				array.add(obj);
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		}

		// 會員舉辦的活動
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				if (memberVO != null) {
					mem_id = memberVO.getMem_id();
					System.out.println("走這邊?" + mem_id);
				} else {
					mem_id = (String) req.getAttribute("mem_id");
					System.out.println("還是這邊" + mem_id);
				}

				// if(req.getParameter("mem_id").trim() !=null){
				// mem_id = req.getParameter("mem_id").trim();
				// }else{
				// mem_id = memberVO.getMem_id();
				// }
				/*************************** 2.開始查詢資料 *****************************************/
				ActivityService activitySvc = new ActivityService();
				List<ActivityVO> list = activitySvc.get_activity_run(mem_id);
				if (list.size() == 0) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/activity/activityErrorAdmin.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("有到這?");
				req.setAttribute("list", list); // 資料庫取出的activityVO物件,存入req
				for (ActivityVO li : list) {
					System.out.println(li.getActiv_id());
				}
				String url = "/frontend/activity/adminActivity.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交adminActivity.jsp活動管理頁面

				System.out.println("走完ㄇ");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/activityError.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		// 來自activityList查詢活動詳細資訊走getOne_For_Activity
		// //店家查詢活動資訊走getOne_For_Store
		// 會員參加活動列表會走listActivitys_Activity_B
		if ("getOne_For_Activity".equals(action) || "listActivitys_Activity_B".equals(action)
				|| "getOne_For_Store".equals(action)) {

			List<String> errorMsgsActiv = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgsActiv", errorMsgsActiv);

			// try {
			/***************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			String acvitid = req.getParameter("activ_id");
			if (acvitid == null || (acvitid.trim()).length() == 0) {
				errorMsgsActiv.add("請選擇活動");
			}

			/*************************** 2.開始查詢資料 *****************************************/
			ActivityService activitySvc = new ActivityService();
			ActivityVO activityVO = activitySvc.getOneActivityOne(acvitid);
			if (activityVO == null) {
				errorMsgsActiv.add("查無資料");
			}

			String activ_id = req.getParameter("activ_id").trim();
			/***************************
			 * //錯誤處理:判別是否第二次參與活動，只有點選activityDetail的參加活動才會走進來
			 *************/
			if ("listActivitys_Activity_B".equals(action)) {
				if (memberVO != null) {
					mem_id = memberVO.getMem_id();
				} else {
					mem_id = req.getParameter("mem_id").trim();
				}
				ParticipantService participantSvc = new ParticipantService();
				String participant = null;
				if (participantSvc.getOneParticipant(activ_id) == null) {
					participant = "123";
				} else {
					participant = participantSvc.getOneParticipant(activ_id).getMem_id();
					errorMsgsActiv.add("活動只能報名一ㄘ喔");
				}

				System.out.println(participant + "11111111111111");

				// Send the use back to the form, if there were errors
				if (!errorMsgsActiv.isEmpty()) {
					// 重複參加活動會跑這邊
					req.setAttribute("activityVO", activityVO); // 資料庫取出的rept_activVOVO物件,存入req
					String url = "/frontend/activity/activityDetail.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);// 重複檢舉,轉送回原頁面
					failureView.forward(req, res);
					return;
				}

			}

			/***************************
			 * 3.查詢完成,準備轉交(Send the Success view)
			 *************/
			req.setAttribute("activityVO", activityVO); // 資料庫取出的activityVO物件,存入req
			String url = null;
			if ("getOne_For_Activity".equals(action))
				url = "/frontend/activity/activityDetail.jsp";
			else if ("getOne_For_Store".equals(action))
				url = "/frontend/activity/activityStoreDetail.jsp";
			else if ("listActivitys_Activity_B".equals(action))

				url = "/frontend/activity/participant.jsp";

			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交activityDetail.jsp或activityStoreDetail.jsp
			successView.forward(req, res);
			return;
			// 成功轉交 activity/listAllActivity.jsp

			/*************************** 其他可能的錯誤處理 *************************************/
			// } catch (Exception e) {
			// errorMsgsActiv.add("無法取得資料:" + e.getMessage());
			// RequestDispatcher failureView =
			// req.getRequestDispatcher("/select_page.jsp");
			// failureView.forward(req, res);
			// }
		}

		// 搜尋活動
		if ("Search_For_Activ_name".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String activ_name = req.getParameter("activ_name");

				/*************************** 2.開始查詢資料 *****************************************/
				ActivityService activitySvc = new ActivityService();
				String activ_names = activ_name.toUpperCase();
				List<ActivityVO> list = activitySvc.getSearch(activ_names);

				if (list.isEmpty()) {
					errorMsgs.add("查無資料");
				}
				System.out.println(list + "ss");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/activityError.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				for (ActivityVO l : list) {
					System.out.println(l.getActiv_name());
					System.out.println(l.getActiv_id() + ">>");
				}
				System.out.println(list.size() + "yoooo");

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("Search_Activity", list); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/activity/activitySearch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/activityError.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		// 更新活動內容
		if ("update".equals(action)) { // 來自updateActivity.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			System.out.println("有近來?");
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String activ_id = req.getParameter("activ_id").trim();
				if (req.getParameter("mem_id").trim() != null) {
					mem_id = req.getParameter("mem_id").trim();
				} else {
					mem_id = memberVO.getMem_id();
				}
				String store_id = req.getParameter("store_id").trim();
				String activ_name = req.getParameter("activ_name").trim();
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (activ_name == null || activ_name.trim().isEmpty()) {
					errorMsgs.put("activ_name", "請輸入活動名稱");
				} else if (!activ_name.trim().matches(enameReg)) {
					errorMsgs.put("activ_name", "只能是中、英文字母、數字和_ , 且長度必需在2到50字之間");
				}

				System.out.println(store_id + "yoo");
				java.sql.Timestamp activ_starttime = null;

				try {
					String a = req.getParameter("activ_starttime");
					System.out.println(a + "??>>>>?");
					a.substring(0, 16);
					System.out.println(a + ">>>>?");
					activ_starttime = java.sql.Timestamp.valueOf(a);
					System.out.println(activ_starttime);
				} catch (IllegalArgumentException e) {
					String a = String.valueOf(System.currentTimeMillis());
					System.out.println(a + "??>>>>?");
					activ_starttime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.put("starttime", "請輸入日期!");
				}

				java.sql.Timestamp activ_endtime = null;
				try {
					String endtime = req.getParameter("activ_endtime");
					endtime.substring(0, 16);
					activ_endtime = java.sql.Timestamp.valueOf(endtime);
				} catch (IllegalArgumentException e) {
					activ_endtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.put("endtime", "請輸入日期!");
				}

				java.sql.Timestamp activ_expire = null;
				try {
					String expire = req.getParameter("activ_expire");
					expire.substring(0, 16);
					activ_expire = java.sql.Timestamp.valueOf(expire);
				} catch (IllegalArgumentException e) {
					activ_expire = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.put("expire", "請輸入日期!");
				}

				ActivityService activSvc = new ActivityService();

				ActivityVO activityVO = activSvc.getOneActivityOne(activ_id);
				Part part = req.getPart("prod_img");
				byte[] activ_img = null;

				// 圖片未修改就存回
				if (part.getSize() == 0) {
					activ_img = activityVO.getActiv_img();
				} else {
					activ_img = Pic.getPictureByteArray(part);
				}

				String activ_summary = req.getParameter("activ_summary").trim();
				if (activ_summary == null || activ_summary.trim().isEmpty()) {
					errorMsgs.put("activ_summary", "請填入簡介");
				} else if (activ_summary.length() > 150) {
					errorMsgs.put("activ_summary", "長度突破限制了呢");
				}

				String activ_intro = req.getParameter("activ_intro").trim();
				if (activ_intro == null || activ_intro.trim().isEmpty()) {
					errorMsgs.put("activ_intro", "請填入介紹");
				}

				Integer activ_num = new Integer(req.getParameter("activ_num").trim());
				if (activ_num == null) {
					errorMsgs.put("activ_num", "請輸入人數");
				} else if (activ_num >= 100) {
					errorMsgs.put("activ_num", "人數突破限制了呢");
				}
				Integer activ_store_cfm = new Integer(req.getParameter("activ_store_cfm").trim());

				System.out.println("有到這?");
				activityVO.setActiv_id(activ_id);
				activityVO.setMem_id(mem_id);
				activityVO.setStore_id(store_id);
				activityVO.setActiv_name(activ_name);
				activityVO.setActiv_starttime(activ_starttime);
				activityVO.setActiv_endtime(activ_endtime);
				activityVO.setActiv_expire(activ_expire);
				activityVO.setActiv_img(activ_img);
				activityVO.setActiv_summary(activ_summary);
				activityVO.setActiv_intro(activ_intro);
				activityVO.setActiv_num(activ_num);
				activityVO.setActiv_store_cfm(activ_store_cfm);
				System.out.println("這而呢?");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("activityVO", activityVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/updateActivity.jsp");
					failureView.forward(req, res);
					System.out.println("怎麼就到這?");
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				System.out.println("有到這嗎");
				ActivityService activitySvc = new ActivityService();
				activityVO = activitySvc.updateActivity(activ_id, mem_id, store_id, activ_name, activ_starttime,
						activ_endtime, activ_expire, activ_img, activ_summary, activ_intro, activ_num, activ_store_cfm);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("累了");
				req.setAttribute("activityVO", activityVO); // 資料庫update成功後,存入req
				String url = "/frontend/activity/activityList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後回到activityList頁面
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:" + e.getMessage(), null);
				return;
			}
		}

		// 活動狀態及檢舉狀態更新
		if ("update_cfm".equals(action)) { // 來自update_activity_input.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			System.out.println("有近來?");
			// try {
			/***************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			System.out.println("???");
			String activ_id = req.getParameter("activ_id").trim();
			System.out.println("???" + activ_id);
			if (req.getParameter("mem_id").trim() != null) {
				mem_id = req.getParameter("mem_id").trim();
			} else {
				mem_id = memberVO.getMem_id();
			}

			System.out.println("有到這??" + mem_id);
			Integer activ_store_cfm = new Integer(req.getParameter("activ_store_cfm").trim());
			ActivityService activSvc = new ActivityService();
			ActivityVO activityVO = activSvc.getOneActivityOne(activ_id);
			activityVO.setActiv_id(activ_id);
			activityVO.setActiv_store_cfm(activ_store_cfm);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("activityVO", activityVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/updateActivity.jsp");
				failureView.forward(req, res);
				return;
			}
			/*************************** 2.開始修改資料 *****************************************/
			System.out.println("有到這嗎");
			ActivityService activitySvc = new ActivityService();
			activityVO = activitySvc.update_cfm(activ_id, activ_store_cfm);

			if (requestURL.equals("/backend/rept_activ/rept_activ.jsp")) {
				String repo_rsn = req.getParameter("repo_rsn").trim();
				Integer repo_rev = new Integer(req.getParameter("repo_rev").trim());
				System.out.println("我想回家");
				Rept_activVO rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(activ_id);
				rept_activVO.setMem_id(mem_id);
				rept_activVO.setRepo_rsn(repo_rsn);
				rept_activVO.setRepo_rev(repo_rev);
				Rept_activService rept_activSvc = new Rept_activService();
				rept_activSvc.updateRept_activ(activ_id, mem_id, repo_rsn, repo_rev);
			}

			/***************************
			 * 3.修改完成,準備轉交(Send the Success view)
			 *************/
			System.out.println("累了");
			req.setAttribute("activityVO", activityVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後轉交
			successView.forward(req, res);
			return;
			/*************************** 其他可能的錯誤處理 *************************************/
			// } catch (Exception e) {
			// errorMsgs.put("修改資料失敗:" + e.getMessage(), null);
			// }
		}

		if ("insert".equals(action)) { // 來自addActivity.jsp的請求,新增活動

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			/***********************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 *************************/
			String activ_id = req.getParameter("activ_id").trim();
			if (memberVO != null) {
				mem_id = memberVO.getMem_id();
			} else {
				mem_id = req.getParameter("mem_id").trim();
			}
			String store_id = req.getParameter("store_id").trim();
			String activ_name = req.getParameter("activ_name").trim();
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (activ_name == null || activ_name.trim().isEmpty()) {
				errorMsgs.put("activ_name", "請輸入活動名稱");
			} else if (!activ_name.trim().matches(enameReg)) {
				errorMsgs.put("activ_name", "只能是中、英文字母、數字和_ , 且長度必需在2到50字之間");
			}

			java.sql.Timestamp activ_starttime = null;
			try {
				String a = req.getParameter("activ_starttime");
				a = a + ":00";
				activ_starttime = java.sql.Timestamp.valueOf(a);

			} catch (IllegalArgumentException e) {
				String a = String.valueOf(System.currentTimeMillis());
				activ_starttime = new java.sql.Timestamp(System.currentTimeMillis());
				errorMsgs.put("starttime", "請輸入日期!");

			}

			java.sql.Timestamp activ_endtime = null;
			try {
				String endtime = req.getParameter("activ_endtime");
				endtime = endtime + ":00";

				activ_endtime = java.sql.Timestamp.valueOf(endtime);
			} catch (IllegalArgumentException e) {
				activ_endtime = new java.sql.Timestamp(System.currentTimeMillis());
				errorMsgs.put("endtime", "請輸入日期!");

			}

			java.sql.Timestamp activ_expire = null;
			try {
				String expire = req.getParameter("activ_expire");
				expire = expire + ":00";
				activ_expire = java.sql.Timestamp.valueOf(expire);

			} catch (IllegalArgumentException e) {
				activ_expire = new java.sql.Timestamp(System.currentTimeMillis());
				errorMsgs.put("expire", "請輸入日期!");
			}

			Part part = req.getPart("prod_img");
			byte[] activ_img = Pic.getPictureByteArray(part);

			String activ_summary = req.getParameter("activ_summary").trim();
			if (activ_summary == null || activ_summary.trim().isEmpty()) {
				errorMsgs.put("activ_summary", "請填入簡介");
			} else if (activ_summary.length() >= 150) {
				errorMsgs.put("activ_summary", "長度突破限制了呢");
			}
			String activ_intro = req.getParameter("activ_intro").trim();
			if (activ_intro == null || activ_intro.trim().isEmpty()) {
				errorMsgs.put("activ_intro", "請填入介紹");
			}

			Integer activ_num = new Integer(req.getParameter("activ_num").trim());
			if (activ_num == null) {
				errorMsgs.put("activ_num", "請輸入人數");
			} else if (activ_num >= 100) {
				errorMsgs.put("activ_num", "人數突破限制了呢");
			}

			Integer activ_store_cfm = new Integer(req.getParameter("activ_store_cfm").trim());

			ActivityVO activityVO = new ActivityVO();
			activityVO.setActiv_id(activ_id);
			activityVO.setMem_id(mem_id);
			activityVO.setStore_id(store_id);
			activityVO.setActiv_name(activ_name);
			activityVO.setActiv_starttime(activ_starttime);
			activityVO.setActiv_endtime(activ_endtime);
			activityVO.setActiv_expire(activ_expire);
			activityVO.setActiv_img(activ_img);
			activityVO.setActiv_summary(activ_summary);
			activityVO.setActiv_intro(activ_intro);
			activityVO.setActiv_num(activ_num);
			activityVO.setActiv_store_cfm(activ_store_cfm);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("activityVO", activityVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/addActivity.jsp");
				failureView.forward(req, res);
				return;
			}

			try {
				/*************************** 2.開始新增資料 ***************************************/
				ActivityService activitySvc = new ActivityService();
				activityVO = activitySvc.addActivity(activ_id, mem_id, store_id, activ_name, activ_starttime,
						activ_endtime, activ_expire, activ_img, activ_summary, activ_intro, activ_num, activ_store_cfm);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/frontend/activity/activityAddEnd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.put(e.getMessage(), activ_intro);
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/activity/addActivity.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自adminActivity.jsp 會員變更活動內容

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String activ_id = req.getParameter("activ_id");
				/*************************** 2.開始查詢資料 ****************************************/
				ActivityService activitySvc = new ActivityService();
				ActivityVO activityVO = activitySvc.getOneActivityOne(activ_id);
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("activityVO", activityVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/activity/updateActivity.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				return;
			}
		}
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣
	}
}
