package com.store.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
import com.fav_store.model.Fav_storeService;
import com.fav_store.model.Fav_storeVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.photo_store.model.Photo_storeService;
import com.photo_store.model.Photo_storeVO;
import com.product.model.ProductVO;
import com.rate_n_rev.model.Rate_n_revService;
import com.rate_n_rev.model.Rate_n_revVO;
import com.store.model.StoreDAO;
import com.store.model.StoreService;
import com.store.model.StoreVO;
import com.store_tag.model.Store_tagVO;
import com.tools.HttpUtils;
import com.tools.JavamailSender;
import com.tools.MD5Util;
import com.tools.Pic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自all select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String store_id = null;
				boolean isStoreLogin = false;
				if (HttpUtils.isFrontend(req) && req.getSession().getAttribute("STORE") != null) {
					// 代表是店家會員登入使用
					StoreVO storeFromSession = (StoreVO) req.getSession().getAttribute("STORE");
					store_id = storeFromSession.getStore_id();
					isStoreLogin = true;
					System.out.println();
				} else if (HttpUtils.isBackend(req)) {
					// 代表是管理者
					store_id = req.getParameter("store_id").trim();
				} else {
					throw new Exception("查無店家資料!!!");
				}
				if (!errorMsgs.isEmpty()) {
					HttpUtils.smartForward("/store/select_page.jsp", req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_id);

				if (!errorMsgs.isEmpty()) {
					HttpUtils.smartForward("/store/select_page.jsp", req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				if (isStoreLogin) {
					req.getSession().setAttribute("STORE", storeVO);
				} else {
					req.setAttribute("storeVO", storeVO);
				}
				HttpUtils.smartForward("/store/listOneStore.jsp", req, res);
				return;// 程式中斷
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				HttpUtils.smartForward("/store/select_page.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isBackend(req) && "getAll".equals(action)) {
			/*************************** 開始查詢資料 ****************************************/
			StoreDAO dao = new StoreDAO();
			List<StoreVO> list = dao.getAll();
			/***************************
			 * 查詢完成,準備轉交(Send the Success view)
			 *************/
			HttpSession session = req.getSession();
			session.setAttribute("list", list); // 資料庫取出的list物件,存入session
			// Send the Success view
			HttpUtils.smartForward("/store/listAllStore.jsp", req, res);
			return;
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllStore.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/

				String store_id = null;
				boolean isStoreLogin = false;
				if (HttpUtils.isFrontend(req) && req.getSession().getAttribute("STORE") != null) {
					// 代表是店家會員登入使用
					StoreVO storeVOFromSession = (StoreVO) req.getSession().getAttribute("STORE");
					store_id = storeVOFromSession.getStore_id();
					isStoreLogin = true;
				} else if (HttpUtils.isBackend(req)) {
					// 代表是管理者
					store_id = req.getParameter("store_id").trim();
				} else {
					throw new Exception("查無店家資料!!!");
				}

				/*************************** 2.開始查詢資料 ****************************************/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_id);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				if (isStoreLogin) {
					req.getSession().setAttribute("STORE", storeVO);
				} else {
					req.setAttribute("storeVO", storeVO);
				}
				HttpUtils.smartForward("/store/update_store_input.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				e.printStackTrace();
				HttpUtils.smartForward("/store/listAllStore.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("update".equals(action)) { // 來自update_store_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			StoreVO storeVO = new StoreVO();
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String store_id = null;
				boolean isStoreLogin = false;
				if (HttpUtils.isFrontend(req) && req.getSession().getAttribute("STORE") != null) {
					// 代表是店家會員登入使用
					StoreVO storeFromSession = (StoreVO) req.getSession().getAttribute("STORE");
					store_id = storeFromSession.getStore_id();
					isStoreLogin = true;
				} else if (HttpUtils.isBackend(req)) {
					// 代表是管理者
					store_id = req.getParameter("store_id").trim();
				} else {
					throw new Exception("查無店家資料!!!");
				}
				String store_acct = req.getParameter("store_acct");
				String store_name = req.getParameter("store_name").trim();
				String store_tel = req.getParameter("store_tel").trim();
				String store_add = req.getParameter("store_add").trim();
				String store_email = req.getParameter("store_email").trim();
				String store_cpse = req.getParameter("store_cpse").trim();
				Integer min_order = new Integer(req.getParameter("min_order").trim());
				Integer is_min_order = new Integer(req.getParameter("is_min_order").trim());
				Integer is_wifi = new Integer(req.getParameter("is_wifi").trim());
				Integer is_plug = new Integer(req.getParameter("is_plug").trim());
				Integer is_single_orgn = new Integer(req.getParameter("is_single_orgn").trim());
				Integer is_dessert = new Integer(req.getParameter("is_dessert").trim());
				Integer is_meal = new Integer(req.getParameter("is_meal").trim());
				Integer is_time_limit = new Integer(req.getParameter("is_time_limit").trim());
				Integer mon_isopen = new Integer(req.getParameter("mon_isopen").trim());
				String mon_open = req.getParameter("mon_open").trim();
				String mon_close = req.getParameter("mon_close").trim();
				Integer tue_isopen = new Integer(req.getParameter("tue_isopen").trim());
				String tue_open = req.getParameter("tue_open").trim();
				String tue_close = req.getParameter("tue_close").trim();
				Integer wed_isopen = new Integer(req.getParameter("wed_isopen").trim());
				String wed_open = req.getParameter("wed_open").trim();
				String wed_close = req.getParameter("wed_close").trim();
				Integer thu_isopen = new Integer(req.getParameter("thu_isopen").trim());
				String thu_open = req.getParameter("thu_open").trim();
				String thu_close = req.getParameter("thu_close").trim();
				Integer fri_isopen = new Integer(req.getParameter("fri_isopen").trim());
				String fri_open = req.getParameter("fri_open").trim();
				String fri_close = req.getParameter("fri_close").trim();
				Integer sat_isopen = new Integer(req.getParameter("sat_isopen").trim());
				String sat_open = req.getParameter("sat_open").trim();
				String sat_close = req.getParameter("sat_close").trim();
				Integer sun_isopen = new Integer(req.getParameter("sun_isopen").trim());
				String sun_open = req.getParameter("sun_open").trim();
				String sun_close = req.getParameter("sun_close").trim();

				StoreService storeSvc = new StoreService();
				List<Double> location = storeSvc.getLatLng(store_add);
				storeVO.setStore_id(store_id);
				storeVO.setStore_acct(store_acct);
				storeVO.setStore_name(store_name);
				storeVO.setStore_tel(store_tel);
				storeVO.setStore_add(store_add);
				storeVO.setStore_email(store_email);
				storeVO.setLongitude(location.get(0));
				storeVO.setLatitude(location.get(1));
				storeVO.setStore_cpse(store_cpse);
				storeVO.setMin_order(min_order);
				storeVO.setIs_min_order(is_min_order);
				storeVO.setIs_wifi(is_wifi);
				storeVO.setIs_plug(is_plug);
				storeVO.setIs_single_orgn(is_single_orgn);
				storeVO.setIs_dessert(is_dessert);
				storeVO.setIs_meal(is_meal);
				storeVO.setIs_time_limit(is_time_limit);
				storeVO.setMon_isopen(mon_isopen);
				storeVO.setMon_open(Timestamp.valueOf("2017-06-21 " + mon_open + ":00"));
				storeVO.setMon_close(Timestamp.valueOf("2017-06-21 " + mon_close + ":00"));
				storeVO.setTue_isopen(tue_isopen);
				storeVO.setTue_open(Timestamp.valueOf("2017-06-21 " + tue_open + ":00"));
				storeVO.setTue_close(Timestamp.valueOf("2017-06-21 " + tue_close + ":00"));
				storeVO.setWed_isopen(wed_isopen);
				storeVO.setWed_open(Timestamp.valueOf("2017-06-21 " + wed_open + ":00"));
				storeVO.setWed_close(Timestamp.valueOf("2017-06-21 " + wed_close + ":00"));
				storeVO.setThu_isopen(thu_isopen);
				storeVO.setThu_open(Timestamp.valueOf("2017-06-21 " + thu_open + ":00"));
				storeVO.setThu_close(Timestamp.valueOf("2017-06-21 " + thu_close + ":00"));
				storeVO.setFri_isopen(fri_isopen);
				storeVO.setFri_open(Timestamp.valueOf("2017-06-21 " + fri_open + ":00"));
				storeVO.setFri_close(Timestamp.valueOf("2017-06-21 " + fri_close + ":00"));
				storeVO.setSat_isopen(sat_isopen);
				storeVO.setSat_open(Timestamp.valueOf("2017-06-21 " + sat_open + ":00"));
				storeVO.setSat_close(Timestamp.valueOf("2017-06-21 " + sat_close + ":00"));
				storeVO.setSun_isopen(sun_isopen);
				storeVO.setSun_open(Timestamp.valueOf("2017-06-21 " + sun_open + ":00"));
				storeVO.setSun_close(Timestamp.valueOf("2017-06-21 " + sun_close + ":00")); // 直接這樣放的話，會出錯，一定要在處理

				Part part = req.getPart("upfile1");
				if (!Pic.noFileSelected(part)) {
					storeVO.setStore_img(Pic.getPictureByteArray(part));
				} else {
					storeVO.setStore_img(storeSvc.getOneStore(store_id).getStore_img());
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的storeVO物件,也存入req
					HttpUtils.smartForward("/store/update_store_input.jsp", req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				storeVO = storeSvc.updateStore(store_id, store_acct, store_name, store_tel, store_add, store_email,
						store_cpse, min_order, is_min_order, is_wifi, is_plug, is_single_orgn, is_dessert, is_meal,
						is_time_limit, mon_isopen, storeVO.getMon_open(), storeVO.getMon_close(), tue_isopen,
						storeVO.getTue_open(), storeVO.getTue_close(), wed_isopen, storeVO.getWed_open(),
						storeVO.getWed_close(), thu_isopen, storeVO.getThu_open(), storeVO.getThu_close(), fri_isopen,
						storeVO.getFri_open(), storeVO.getFri_close(), sat_isopen, storeVO.getSat_open(),
						storeVO.getSat_close(), sun_isopen, storeVO.getSun_open(), storeVO.getSun_close(),
						storeVO.getStore_img());

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				if (isStoreLogin) {
					HttpUtils.clearSessionMember(req);
					HttpUtils.registerSessionStore(req, storeVO);
				} else {
					req.setAttribute("storeVO", storeVO);
				}
				// 資料庫update成功後,正確的的storeVO物件,存入req
				HttpUtils.smartForward("/store/listOneStore.jsp", req, res);
				return;// 程式中斷
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				req.setAttribute("storeVO", storeVO);
				HttpUtils.smartForward("/store/update_store_input.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isFrontend(req) && "insert".equals(action)) { // 來自addStore.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String store_acct = req.getParameter("store_acct").trim();
				String store_pwd = req.getParameter("store_pwd").trim();
				String store_name = req.getParameter("store_name").trim();
				String store_tel = req.getParameter("store_tel").trim();
				String store_add = HttpUtils.getParameter(req, "store_add");
				String store_email = req.getParameter("store_email").trim();
				Integer store_points = new Integer(req.getParameter("store_points").trim());
				String store_cpse = req.getParameter("store_cpse").trim();
				Integer min_order = new Integer(req.getParameter("min_order").trim());
				Integer is_min_order = new Integer(req.getParameter("is_min_order").trim());
				Integer is_wifi = new Integer(req.getParameter("is_wifi").trim());
				Integer is_plug = new Integer(req.getParameter("is_plug").trim());
				Integer is_single_orgn = new Integer(req.getParameter("is_single_orgn").trim());
				Integer is_dessert = new Integer(req.getParameter("is_dessert").trim());
				Integer is_meal = new Integer(req.getParameter("is_meal").trim());
				Integer is_time_limit = new Integer(req.getParameter("is_time_limit").trim());
				Integer mon_isopen = new Integer(req.getParameter("mon_isopen").trim());
				String mon_open = req.getParameter("mon_open").trim();
				String mon_close = req.getParameter("mon_close").trim();
				Integer tue_isopen = new Integer(req.getParameter("tue_isopen").trim());
				String tue_open = req.getParameter("tue_open").trim();
				String tue_close = req.getParameter("tue_close").trim();
				Integer wed_isopen = new Integer(req.getParameter("wed_isopen").trim());
				String wed_open = req.getParameter("wed_open").trim();
				String wed_close = req.getParameter("wed_close").trim();
				Integer thu_isopen = new Integer(req.getParameter("thu_isopen").trim());
				String thu_open = req.getParameter("thu_open").trim();
				String thu_close = req.getParameter("thu_close").trim();
				Integer fri_isopen = new Integer(req.getParameter("fri_isopen").trim());
				String fri_open = req.getParameter("fri_open").trim();
				String fri_close = req.getParameter("fri_close").trim();
				Integer sat_isopen = new Integer(req.getParameter("sat_isopen").trim());
				String sat_open = req.getParameter("sat_open").trim();
				String sat_close = req.getParameter("sat_close").trim();
				Integer sun_isopen = new Integer(req.getParameter("sun_isopen").trim());
				String sun_open = req.getParameter("sun_open").trim();
				String sun_close = req.getParameter("sun_close").trim();

				StoreService storeSvc = new StoreService();
				StoreVO storeVO = new StoreVO();
				StoreVO storeVO1 = storeSvc.getOneStoreByAccount(store_acct);
				if (storeVO1 == null) {
					storeVO.setStore_acct(store_acct);
					storeVO.setStore_pwd(store_pwd);
					storeVO.setStore_name(store_name);
					storeVO.setStore_tel(store_tel);
					storeVO.setStore_add(store_add);
					storeVO.setStore_email(store_email);
					storeVO.setStore_points(store_points);
					storeVO.setStore_cpse(store_cpse);
					storeVO.setMin_order(min_order);
					storeVO.setIs_min_order(is_min_order);
					storeVO.setIs_wifi(is_wifi);
					storeVO.setIs_plug(is_plug);
					storeVO.setIs_single_orgn(is_single_orgn);
					storeVO.setIs_dessert(is_dessert);
					storeVO.setIs_plug(is_plug);
					storeVO.setIs_single_orgn(is_single_orgn);
					storeVO.setIs_dessert(is_dessert);
					storeVO.setIs_meal(is_meal);
					storeVO.setIs_time_limit(is_time_limit);
					storeVO.setMon_isopen(mon_isopen);
					storeVO.setMon_open(Timestamp.valueOf("2017-06-21 " + mon_open + ":00"));
					storeVO.setMon_close(Timestamp.valueOf("2017-06-21 " + mon_close + ":00"));
					storeVO.setTue_isopen(tue_isopen);
					storeVO.setTue_open(Timestamp.valueOf("2017-06-21 " + tue_open + ":00"));
					storeVO.setTue_close(Timestamp.valueOf("2017-06-21 " + tue_close + ":00"));
					storeVO.setWed_isopen(wed_isopen);
					storeVO.setWed_open(Timestamp.valueOf("2017-06-21 " + wed_open + ":00"));
					storeVO.setWed_close(Timestamp.valueOf("2017-06-21 " + wed_close + ":00"));
					storeVO.setThu_isopen(thu_isopen);
					storeVO.setThu_open(Timestamp.valueOf("2017-06-21 " + thu_open + ":00"));
					storeVO.setThu_close(Timestamp.valueOf("2017-06-21 " + thu_close + ":00"));
					storeVO.setFri_isopen(fri_isopen);
					storeVO.setFri_open(Timestamp.valueOf("2017-06-21 " + fri_open + ":00"));
					storeVO.setFri_close(Timestamp.valueOf("2017-06-21 " + fri_close + ":00"));
					storeVO.setSat_isopen(sat_isopen);
					storeVO.setSat_open(Timestamp.valueOf("2017-06-21 " + sat_open + ":00"));
					storeVO.setSat_close(Timestamp.valueOf("2017-06-21 " + sat_close + ":00"));
					storeVO.setSun_isopen(sun_isopen);
					storeVO.setSun_open(Timestamp.valueOf("2017-06-21 " + sun_open + ":00"));
					storeVO.setSun_close(Timestamp.valueOf("2017-06-21 " + sun_close + ":00")); // 直接這樣放的話，會出錯，一定要在處理
					Part part = req.getPart("upfile1");
					if (!Pic.noFileSelected(part)) {
						storeVO.setStore_img(Pic.getPictureByteArray(part));
					}
					storeVO.setStore_validateCode(MD5Util.encode2hex(store_email));
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的storeVO物件,也存入req
						HttpUtils.smartForward("/store/addStore.jsp", req, res);
						return;
					}

					/*************************** 2.開始新增資料 ***************************************/
					storeVO = storeSvc.addStore(store_acct, store_pwd, store_name, store_tel, store_add, store_email,
							store_points, store_cpse, min_order, is_min_order, is_wifi, is_plug, is_single_orgn,
							is_dessert, is_meal, is_time_limit, mon_isopen, storeVO.getMon_open(),
							storeVO.getMon_close(), tue_isopen, storeVO.getTue_open(), storeVO.getTue_close(),
							wed_isopen, storeVO.getWed_open(), storeVO.getWed_close(), thu_isopen,
							storeVO.getThu_open(), storeVO.getThu_close(), fri_isopen, storeVO.getFri_open(),
							storeVO.getFri_close(), sat_isopen, storeVO.getSat_open(), storeVO.getSat_close(),
							sun_isopen, storeVO.getSun_open(), storeVO.getSun_close(), storeVO.getStore_img(),
							storeVO.getStore_validateCode());

					/***************************
					 * 3.新增完成,準備轉交(Send the Success view)
					 ***********/
					HttpUtils.smartForward("/store/login-store_under_review.jsp", req, res);
					return;// 程式中斷
				} else {
					HttpUtils.smartForward("/store/login-alreadyExist.jsp", req, res);
					return;// 程式中斷
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				HttpUtils.smartForward("/store/addStore.jsp", req, res);
				return;// 程式中斷
			}
		}

		if (HttpUtils.isBackend(req) && "delete".equals(action)) { // 來自listAllStore.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String store_id = new String(req.getParameter("store_id"));

				/*************************** 2.開始刪除資料 ***************************************/
				StoreService storeSvc = new StoreService();
				storeSvc.deleteStore(store_id);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				HttpUtils.smartForward("/store/listAllStore.jsp", req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/store/listAllStore.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("Login_Store".equals(action)) {

			String store_acct = req.getParameter("store_acct");
			String store_pwd = req.getParameter("store_pwd");

			StoreService storeSvc = new StoreService();
			StoreVO storeVO_login = storeSvc.Login_Store(store_acct, store_pwd);
			if (storeVO_login != null) {
				if ((storeVO_login.getStore_pass() != null && storeVO_login.getStore_pass().intValue() == 1)
						&& (storeVO_login.getStore_authentication().intValue() == 1
								&& storeVO_login.getStore_authentication() != null)) {
					// 已通過認證店家
					HttpSession session = req.getSession();
					req.getSession().setAttribute("STORE", storeVO_login);
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location"); // *工作2: 看看有無來源網頁
						res.sendRedirect(location);
						return;
					} else {
						HttpUtils.smartForward("/index.jsp", req, res);
						return;// 程式中斷
					}
				} else if ((storeVO_login.getStore_pass() != null && storeVO_login.getStore_pass().intValue() == 1)
						&& storeVO_login.getStore_authentication() == null) {
					// 已通過認證店家 但未點選驗證信
					HttpUtils.smartForward("/store/login-storeAuthEmail_Check.jsp", req, res);
					return;// 程式中斷
				} else if ((storeVO_login.getStore_pass() != null && storeVO_login.getStore_pass().intValue() == 0)
						&& storeVO_login.getStore_authentication() == null) {
					// 未通過認證店家
					HttpUtils.smartForward("/store/login-reject_storePass.jsp", req, res);
					return;// 程式中斷
				} else if (storeVO_login.getStore_pass() == null && storeVO_login.getStore_authentication() == null) {
					// 未通過認證店家
					HttpUtils.smartForward("/store/login-store_under_review.jsp", req, res);
					return;// 程式中斷
				}
			} else {
				HttpUtils.smartForward("/store/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("updatePass".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			StoreVO storeVO = new StoreVO();

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String[] store_id = req.getParameterValues("store_id");
				String pass = req.getParameter("pass_store");

				Integer store_pass = null;
				if ("審核通過店家".equals(pass)) {
					store_pass = 1;
				} else if ("審核不通過店家".equals(pass)) {
					store_pass = 0;
				}

				StoreService storeSvc = new StoreService();

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的storeVO物件,也存入req
					HttpUtils.smartForward("/store/update_store_input.jsp", req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				storeSvc.updatePass(store_id, store_pass);

				for (String stores_id : store_id) {
					storeVO = storeSvc.getOneStore(stores_id);
					if (store_pass == 1) {
						String store_context = "親愛的CoffeePuzzle 新會員 " + storeVO.getStore_acct() + "您好" + "請點選以下驗證連結~~!!"
								+ "<a href='http://localhost:8081/" + req.getContextPath()
								+ "/frontend/store/store.do?action=STORE_AUTHENTICATION&mail="
								+ storeVO.getStore_email() + "&validateCode=" + storeVO.getStore_validateCode()
								+ "&account=" + storeVO.getStore_acct() + "'>點我點我</a>";
						JavamailSender.send(storeVO.getStore_acct(), storeVO.getStore_email(), store_context);
					} else {
						String store_context = storeVO.getStore_acct() + "您好" + "  此信件為CoffeePuzzle 店家未通過審核信件!!";
						JavamailSender.send(storeVO.getStore_acct(), storeVO.getStore_email(), store_context);

					}
				}
				HttpUtils.smartForward("/admin/reviewStore.jsp", req, res);
				return;// 程式中斷
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/admin/reviewStore.jsp", req, res);
				return;// 程式中斷
			}
		}

		if ("STORE_AUTHENTICATION".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String store_acct = req.getParameter("account");
				String store_email = req.getParameter("mail");
				String store_validateCode = req.getParameter("validateCode");

				StoreService storerSvc = new StoreService();

				StoreVO storeVO = storerSvc.getOneStoreByAccount(store_acct);
				if (storeVO.getStore_email().equals(store_email)
						&& storeVO.getStore_validateCode().equals(store_validateCode)) {
					// 驗證成功

					/*************************** 2.開始修改資料 *****************************************/
					int store_authentication = 1;
					storeVO.setStore_authentication(store_authentication);
					storerSvc.store_Authentication(store_acct, store_authentication);
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					HttpUtils.smartForward("/store/login_Store.jsp", req, res);
					return;// 程式中斷
				} else {
					// 驗證失敗
					HttpUtils.smartForward("/store/login_errorAuth.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/store/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}
		if ("STORE_FORGET_PASSWORD".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String store_acct = req.getParameter("store_acct");
				String store_email = req.getParameter("store_email");

				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStoreByAccount(store_acct);

				if (storeVO.getStore_email().equals(store_email) && storeVO.getStore_acct().equals(store_acct)) {
					// 驗證成功

					/*************************** 2.開始修改資料 *****************************************/
					String store_pwd = UUID.randomUUID().toString().substring(0, 8);
					storeVO.setStore_pwd(store_pwd);
					storeSvc.store_Forget_Password(store_acct, store_pwd);
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					req.setAttribute("storeVO", storeVO);
					req.getSession().setAttribute("STORE", storeVO);

					String store_context = new String("請複製以下密碼進行登入~~!!");
					store_context = store_context + String.valueOf("<br>" + "新密碼為: " + store_pwd);
					JavamailSender.send(store_acct, store_email, store_context);

					HttpUtils.smartForward("/store/login_Store.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/store/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}
		if ("UPDATE_STORE_PASSWORD".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				StoreVO storeFromSession = (StoreVO) req.getSession().getAttribute("STORE");
				String store_acct = storeFromSession.getStore_acct();
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

					StoreService storeSvc = new StoreService();
					StoreVO storeVO = new StoreVO();
					storeVO.setStore_acct(store_acct);
					storeVO.setStore_pwd(newPassword);
					storeSvc.store_Forget_Password(store_acct, newPassword);
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 *************/
					req.getSession().setAttribute("STORE", storeVO);
					HttpUtils.clearSessionStore(req);
					req.setAttribute("url", HttpUtils.getSmartUrl("/member/login_Store.jsp", req));
					HttpUtils.smartForward("/store/login_Store.jsp", req, res);
					return;// 程式中斷
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.smartForward("/store/login-error.jsp", req, res);
				return;// 程式中斷
			}
		}

		// 晨均 晨均 晨均
		// 晨均 晨均 晨均
		if ("getOne_For_Display123".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("store_id");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入店家編號");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(str);
				if (storeVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("132132");

				/****************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("storeVO", storeVO); // 資料庫取出的storeVO物件,存入req
				String url = "/frontend/memberstore/listOfStore123.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneStore.jsp
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("getStores_BY_Store_ADD_OR_NAME".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/****************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("store_add_or_name").toUpperCase();
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入地址OR路名OR店名");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();

				Set<StoreVO> storeADDNAMESet = storeSvc.getStoresByStore_add_or_name(str);
				System.out.println("storeADDNAMESet" + storeADDNAMESet);

				if (storeADDNAMESet.isEmpty()) {
					errorMsgs.add("查無資料");
				}
				// if(!errorMsgs.isEmpty()){
				// RequestDispatcher failureView =
				// req.getRequestDispatcher("/store/select_page.jsp");
				// failureView.forward(req, res);
				// return;
				// }

				/****************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("storeADDNAMESet", storeADDNAMESet); // 資料庫取出的storeVO物件,存入req
				String url = "/frontend/memberstore/listOfStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneStore.jsp
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("Store_ByStore_id".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO) session.getAttribute("MEMBER");
				String mem_id = memberVO.getMem_id();
				/****************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("store_id");

				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(str);

				Rate_n_revService rate_n_revSvc = new Rate_n_revService();
				Set<Rate_n_revVO> rate_n_revVO = rate_n_revSvc.getAllRate_n_rev_ByStore_id(str);

				// Store_tagService store_tagSvc = new Store_tagService();
				// Set<Store_tagVO> store_tagVO =
				// store_tagSvc.getTagContentByStore_id(str);

				Set<Store_tagVO> store_tagVO = storeSvc.getStore_tagsByStore_id(str);

				Fav_storeService fav_storeSvc = new Fav_storeService();
				Fav_storeVO fav_storeVOcheck = fav_storeSvc.findByPrimaryKey(mem_id, str);
				/****************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("storeVO", storeVO);
				req.setAttribute("rate_n_revVO", rate_n_revVO);// 資料庫取出的storeVO物件,存入req
				req.setAttribute("store_tagVO", store_tagVO);
				req.setAttribute("fav_storeVOcheck", fav_storeVOcheck);

				String url = "/frontend/memberstore/listOneStore123.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		// 晨均 晨均 晨均
		// 晨均 晨均 晨均

		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		// 會員參與舉辦多少活動
		if ("getActivity_For_Store_id".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				HttpSession session = req.getSession();
				StoreVO storeVO = (StoreVO) session.getAttribute("STORE");

				String store_id = storeVO.getStore_id();
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/store/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();
				Set<ActivityVO> set = storeSvc.getActivitysByStore_id(store_id);
				if (set == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/store/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("activityVO", set); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/activity/storeActivity.jsp";
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
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		if("findStoreByAreaOrName".equals(action)) {
			String area = req.getParameter("area_or_name");
			Set<StoreVO> storeList = new LinkedHashSet<StoreVO>();
			
			StoreService storeSvc = new StoreService();
			for(StoreVO storeVO : storeSvc.findStoreByAreaOrName(area.toUpperCase())) {
				storeList.add(storeVO);
			}
				
			req.setAttribute("findStoreByAreaOrName", storeList);

 			String url = "/frontend/store/findStore.jsp";
		
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;

		}
		if("findByPrimaryKey".equals(action)) {
			String store_id = req.getParameter("store_id");
			
			StoreService storeSvc = new StoreService();
			Photo_storeService photo_storeSvc = new Photo_storeService();
			StoreVO storeVO = storeSvc.findByPrimaryKey(store_id);
			List<Photo_storeVO> photoStoreList = photo_storeSvc.getPhotoByStore_id(store_id);
			List<String> encodedList = new ArrayList<String>();
//			if(photoStoreList.size() > 1) {
				for(Photo_storeVO photoStoreVO : photoStoreList) {
					encodedList.add(Base64.getEncoder().encodeToString(photoStoreVO.getPhoto()));
				}
				
//			}
			
			JSONArray array = new JSONArray();
			
			JSONObject obj = new JSONObject();
			try{
				obj.put("store_id", storeVO.getStore_id());
				obj.put("store_name", storeVO.getStore_name());
				obj.put("store_img", Base64.getEncoder().encodeToString(storeVO.getStore_img()));
				obj.put("store_tel", storeVO.getStore_tel());
				obj.put("store_add", storeVO.getStore_add());
				obj.put("store_email", storeVO.getStore_email());
				obj.put("min_order", storeVO.getMin_order());
				obj.put("is_min_order", storeVO.getIs_min_order());
				obj.put("is_wifi", storeVO.getIs_wifi());
				obj.put("is_plug", storeVO.getIs_plug());
				obj.put("is_single_orgn", storeVO.getIs_single_orgn());
				obj.put("is_dessert", storeVO.getIs_dessert());
				obj.put("is_meal", storeVO.getIs_meal());
				obj.put("is_time_limit", storeVO.getIs_time_limit());
				obj.put("sun_isopen", storeVO.getSun_isopen());
				obj.put("sun_open", storeVO.getSun_open());
				obj.put("sun_close", storeVO.getSun_close());
				obj.put("mon_isopen", storeVO.getMon_isopen());
				obj.put("mon_open", storeVO.getMon_open());
				obj.put("mon_close", storeVO.getMon_close());
				obj.put("tue_isopen", storeVO.getTue_isopen());
				obj.put("tue_open", storeVO.getTue_open());
				obj.put("tue_close", storeVO.getTue_close());
				obj.put("wed_isopen", storeVO.getWed_isopen());
				obj.put("wed_open", storeVO.getWed_open());
				obj.put("wed_close", storeVO.getWed_close());
				obj.put("thu_isopen", storeVO.getThu_isopen());
				obj.put("thu_open", storeVO.getThu_open());
				obj.put("thu_close", storeVO.getThu_close());
				obj.put("fri_isopen", storeVO.getFri_isopen());
				obj.put("fri_open", storeVO.getFri_open());
				obj.put("fri_close", storeVO.getFri_close());
				obj.put("sat_isopen", storeVO.getSat_isopen());
				obj.put("sat_open", storeVO.getSat_open());
				obj.put("sat_close", storeVO.getSat_close());
				obj.put("photo_store", encodedList);

			}catch(Exception e){}
			array.add(obj);
		
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		}
		
		// only for takeaway products, shopping page cannot be queried by store_id 
		if("getTakeawayProductsByStore_id".equals(action)) {
			String store_id = req.getParameter("store_id");
//			   new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			
			StoreService storeSvc = new StoreService();
			List<ProductVO> productSet = storeSvc.getTakeawayProductsByStore_id(store_id);
			StoreVO storeVO = storeSvc.findByPrimaryKey(store_id);		
			
			Map<String, List<ProductVO>> proMap = new TreeMap<String, List<ProductVO>>();			
			
			List<ProductVO> prodTypeList = new  ArrayList<ProductVO>();
			proMap.put("CATE00000000", productSet);
			for(ProductVO productVO : productSet){
				String cateId = productVO.getCate_id();					
				prodTypeList = proMap.get(cateId)!= null?proMap.get(cateId):new ArrayList<ProductVO>();						
				prodTypeList.add(productVO);
				proMap.put(cateId, prodTypeList);			
			}
									
			req.setAttribute("proMap", proMap);
			req.setAttribute("findByPrimaryKey", storeVO);
			
			String url = "/frontend/product/takeaway_product_v2.jsp";
			
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
			
		}
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		
		
		HttpUtils.smartForward("/index.jsp", req, res);
		return;
	}

}
