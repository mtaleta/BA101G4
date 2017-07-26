package com.feature.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.feature.model.*;
import com.tools.HttpUtils;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class FeatureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("feature_id");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入後端功能表編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					HttpUtils.forward("/feature/select_page.jsp",req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				FeatureService featureSvc = new FeatureService();
				System.out.println(str);
				FeatureVO featureVO = featureSvc.getOneFeature(str);

				if (featureVO == null) {
					System.out.println(featureVO);
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					HttpUtils.forward("/feature/select_page.jsp",req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("featureVO", featureVO); // 資料庫取出的featureVO物件,存入req
				HttpUtils.forward("/feature/listOneFeature.jsp",req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				HttpUtils.forward("/feature/select_page.jsp",req, res);
				return;// 程式中斷
			}
		}
		
		if ("getAll".equals(action)) {
			/*************************** 開始查詢資料 ****************************************/
			FeatureDAO dao = new FeatureDAO();
			List<FeatureVO> list = dao.getAll();

			/***************************
			 * 查詢完成,準備轉交(Send the Success view)
			 *************/
			HttpSession session = req.getSession();
			session.setAttribute("list", list); // 資料庫取出的list物件,存入session
			// Send the Success view
			HttpUtils.forward("/feature/listAllFeature.jsp",req, res);
			return;
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllFeature.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String feature_id = new String(req.getParameter("feature_id"));

				/*************************** 2.開始查詢資料 ****************************************/
				FeatureService featureSvc = new FeatureService();
				FeatureVO featureVO = featureSvc.getOneFeature(feature_id);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("featureVO", featureVO); // 資料庫取出的feature物件,存入req
				HttpUtils.forward("/feature/update_feature_input.jsp",req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				HttpUtils.forward("/feature/listAllFeature.jsp",req, res);
				return;// 程式中斷
			}
		}

		if ("update".equals(action)) { // 來自update_feature_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String feature_id = req.getParameter("feature_id").trim();
				String feature_name = req.getParameter("feature_name").trim();
				

				FeatureVO featureVO = new FeatureVO();
				featureVO.setFeature_id(feature_id);
				featureVO.setFeature_name(feature_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featureVO", featureVO); // 含有輸入格式錯誤的featureVO物件,也存入req
					HttpUtils.forward("/feature/update_feature_input.jsp",req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				FeatureService featureSvc = new FeatureService();
				featureVO = featureSvc.updateFeature( feature_id,  feature_name);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("featureVO", featureVO); // 資料庫update成功後,正確的的featureVO物件,存入req
				HttpUtils.forward("/feature/listOneFeature.jsp",req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				HttpUtils.forward("/feature/update_feature_input.jsp",req, res);
				return;// 程式中斷
			}
		}

		if ("insert".equals(action)) { // 來自addFeature.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String feature_name = req.getParameter("feature_name").trim();


				FeatureVO featureVO = new FeatureVO();
				featureVO.setFeature_name(feature_name);
		// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featureVO", featureVO); // 含有輸入格式錯誤的featureVO物件,也存入req
					HttpUtils.forward("/feature/addFeature.jsp",req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				FeatureService featureSvc = new FeatureService();
				featureVO = featureSvc.addFeature(feature_name);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				HttpUtils.forward("/feature/listAllFeature.jsp",req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				HttpUtils.forward("/feature/addFeature.jsp",req, res);
				return;// 程式中斷
			}
		}

		if ("delete".equals(action)) { // 來自listAllFeature.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String feature_id = new String(req.getParameter("feature_id"));

				/*************************** 2.開始刪除資料 ***************************************/
				FeatureService featureSvc = new FeatureService();
				featureSvc.deleteFeature(feature_id);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				HttpUtils.forward("/feature/listAllFeature.jsp",req, res);
				return;// 程式中斷

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				HttpUtils.forward("/feature/listAllFeature.jsp",req, res);
				return;// 程式中斷
			}
		}
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}
