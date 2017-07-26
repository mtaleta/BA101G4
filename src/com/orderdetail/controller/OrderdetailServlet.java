package com.orderdetail.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orderdetail.model.OrderdetailService;
import com.orderdetail.model.OrderdetailVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/OrderdetailServlet")
public class OrderdetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OrderdetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		if("findProductsByOrdId".equals(action)) {
			
			String ord_id = req.getParameter("ord_id");
			String ord_add = req.getParameter("ord_add");
			
			OrderdetailService orderdetailSvc = new OrderdetailService();	
			Set<OrderdetailVO> orderdetailVOSet = orderdetailSvc.findProductsByOrdId(ord_id);
			
			JSONArray array = new JSONArray();
			
			for(OrderdetailVO orderdetailVO : orderdetailVOSet){
				JSONObject obj = new JSONObject();
				try{
					obj.put("ord_id", orderdetailVO.getOrd_id());
					obj.put("prod_id", orderdetailVO.getProd_id());
					obj.put("prod_name", orderdetailVO.getProd_name());
					obj.put("prod_price", orderdetailVO.getProd_price());
					obj.put("detail_amt", orderdetailVO.getDetail_amt());
					if(array.size()+1==orderdetailVOSet.size()) {
						obj.put("ord_add", ord_add);
					}
				}catch(Exception e){}
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
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
	}

}
