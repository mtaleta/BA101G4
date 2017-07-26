package com.rept_store.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


import com.rept_store.model.Rept_storeService;
import com.rept_store.model.Rept_storeVO;

public class Rept_storeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣

		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("store_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���a�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/rept_store/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String store_id = null;
				try {
					store_id = str;
				} catch (Exception e) {
					errorMsgs.add("�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/rept_store/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				Rept_storeService rept_storeSvc = new Rept_storeService();
				Rept_storeVO rept_storeVO = rept_storeSvc.getOneRept_store(store_id);
				if (rept_storeVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/rept_store/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("rept_storeVO", rept_storeVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/rept_store/listOneRept_store.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/rept_store/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String storeid = req.getParameter("store_id");
				
				/***************************2.�}�l�d�߸��****************************************/
				Rept_storeService rept_storeSvc = new Rept_storeService();
				Rept_storeVO rept_storeVO = rept_storeSvc.getOneRept_store(storeid);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("rept_storeVO", rept_storeVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/rept_store/update_rept_store_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);
				return;
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/rept_store/listAllRept_store.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String store_id = req.getParameter("store_id").trim();
				String mem_id = req.getParameter("mem_id").trim();
				String rept_rsn = req.getParameter("rept_rsn").trim();				
				Integer rept_rev = new Integer(req.getParameter("rept_rev").trim());
				

				

				Rept_storeVO rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(store_id);
				rept_storeVO.setMem_id(mem_id);
				rept_storeVO.setRept_rsn(rept_rsn);
				rept_storeVO.setRept_rev(rept_rev);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rept_storeVO", rept_storeVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/rept_store/update_rept_store_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				Rept_storeService rept_storeSvc = new Rept_storeService();
				rept_storeVO = rept_storeSvc.updateRept_store(store_id, mem_id, rept_rsn, rept_rev);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("rept_storeVO", rept_storeVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/rept_store/listOneRept_store.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/rept_store/update_rept_store_input.jsp");
				failureView.forward(req, res);
				return;
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String store_id = req.getParameter("store_id").trim();
				String mem_id = req.getParameter("mem_id").trim();
				String rept_rsn = req.getParameter("rept_rsn").trim();	
				Integer rept_rev = new Integer(req.getParameter("rept_rev").trim());

				Rept_storeVO rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(store_id);
				rept_storeVO.setMem_id(mem_id);
				rept_storeVO.setRept_rsn(rept_rsn);
				rept_storeVO.setRept_rev(rept_rev);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rept_storeVO", rept_storeVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/rept_store/addRept_store.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				Rept_storeService rept_storeSvc = new Rept_storeService();
				rept_storeVO = rept_storeSvc.addRept_store(store_id, mem_id, rept_rsn, rept_rev);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/rept_store/listAllRept_store.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				return;
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/rept_store/addRept_store.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
//		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.�����ШD�Ѽ�***************************************/
//				String storeid = req.getParameter("store_id");
//				
//				/***************************2.�}�l�R�����***************************************/
//				Rept_storeService rept_storeSvc = new Rept_storeService();
//				Rept_storeVO rept_storeVO = rept_storeSvc.getOneRept_store(storeid);
//				rept_storeSvc.deleteRept_store(storeid);
//				
//				
//				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
//				DeptService deptSvc = new DeptService();
//				if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
//					req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno())); // ��Ʈw���X��list����,�s�Jrequest
//				
//				if(requestURL.equals("/emp/listEmps_ByCompositeQuery.jsp")){
//					HttpSession session = req.getSession();
//					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
//					List<EmpVO> list  = empSvc.getAll(map);
//					req.setAttribute("listEmps_ByCompositeQuery",list); //  �ƦX�d��, ��Ʈw���X��list����,�s�Jrequest
//				}
//				
//				String url = requestURL;
//				RequestDispatcher successView = req.getRequestDispatcher(url); // �R�����\��,���^�e�X�R�����ӷ�����
//				successView.forward(req, res);
//				
//				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�R����ƥ���:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/listAllEmp.jsp");
//				failureView.forward(req, res);
//			}
//		}

		// 祈竣 祈竣 祈竣
		// 祈竣 祈竣 祈竣
	}
}
