package com.spndcoffeelist.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;



import com.spndcoffee.model.*;
import com.spndcoffeelist.model.*;
import com.spndcoffeercd.model.*;

public class SpndcoffeelistServlet extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        
        if ("getSpndcoffeercdsByList_id".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/*************************** 1.接收請求參數 ****************************************/

				String list_id = req.getParameter("list_id");

				/*************************** 2.開始查詢資料 ****************************************/
				SpndcoffeelistService spndcoffeelistSvc = new SpndcoffeelistService();
				SpndcoffeelistVO spndcoffeelistVO = spndcoffeelistSvc.getOneSpndcoffeelist(list_id); //?

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("SpndcoffeelistVO", spndcoffeelistVO);    // 資料庫取出的set物件,存入request, 名字自己取

				//String url = null;
				//if ("listRcd_ByList_id".equals(action))
				//	url = "/spndcoffeelist/listRcd_ByList_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
				        
				RequestDispatcher successView = req.getRequestDispatcher("/frontend/spndcoffeelist/listOneSpndcoffeeList.jsp");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
//			} catch (Exception e) {
//				throw new ServletException(e);
//			}
		}
		
        
        if ("getOneSpndcoffeeList".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String list_id = req.getParameter("list_id");

				/*************************** 2.開始查詢資料 ****************************************/
				SpndcoffeelistService spndcoffeelistSvc = new SpndcoffeelistService();
//				Set<SpndcoffeelistVO> set = spndcoffeelistSvc.getSpndcoffeelistsByList_id(list_id); //?
				SpndcoffeelistVO spndcoffeelistVO = spndcoffeelistSvc.getOneSpndcoffeelist(list_id); //?

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("SpndcoffeelistVO", spndcoffeelistVO);    // 資料庫取出的set物件,存入request, 名字自己取  ??

				//String url = null;
				//if ("listRcd_ByList_id".equals(action))
				//	url = "/spndcoffeelist/listRcd_ByList_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
				        
				RequestDispatcher successView = req.getRequestDispatcher("/frontend/spndcoffeelist/listOneSpndcoffeeList.jsp");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
		}
        
        
        if ("getSpndcoffeercdsBySpnd_id".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String spnd_id = req.getParameter("spnd_id");

				/*************************** 2.開始查詢資料 ****************************************/
				SpndcoffeelistService spndcoffeelistSvc = new SpndcoffeelistService();
				Set<SpndcoffeercdVO> set = spndcoffeelistSvc.getSpndcoffeercdsByList_id(spnd_id); //?

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listList_BySpnd_id", set);    // 資料庫取出的set物件,存入request, 名字自己取

				//String url = null;
				//if ("listRcd_ByList_id".equals(action))
				//	url = "/spndcoffeelist/listRcd_ByList_id.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
				        
				RequestDispatcher successView = req.getRequestDispatcher("/spndcoffeelist/listList_BySpnd_id.jsp");
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
        
    }
    
    
}