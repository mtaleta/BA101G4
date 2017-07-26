package com.orderdetail.model;

import java.util.Set;

public class OrderdetailService {

	private OrderdetailDAO_interface dao;
	
	public OrderdetailService() {
		dao = new OrderdetailDAO();
	}
	
	public OrderdetailVO addOrderdetail(String ord_id, String prod_id, String prod_name, Integer prod_price, Integer detail_amt){
		
		OrderdetailVO orderdetailVO = new OrderdetailVO();
		orderdetailVO.setOrd_id(ord_id);
		orderdetailVO.setProd_id(prod_id);
		orderdetailVO.setProd_name(prod_name);
		orderdetailVO.setProd_price(prod_price);
		orderdetailVO.setDetail_amt(detail_amt);
		
		dao.insert(orderdetailVO);
		
		return orderdetailVO;
	}
	
	// Adding by Sylvie
	// OrderlistDAO 內建有相同方法，getOrderdetailsByOrd_id(String ord_id)
	public Set<OrderdetailVO> findProductsByOrdId(String ord_id) {
		return dao.findProductsByOrdId(ord_id);
	}
	
}
