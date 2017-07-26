package com.orderlist.model;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.member.model.MemberVO;
import com.orderdetail.model.OrderdetailVO;
import com.product.model.ProductVO;
import com.store.model.StoreVO;

public interface OrderlistDAO_interface {

	public void insert(OrderlistVO orderlistVO);
	public void update(OrderlistVO orderlistVO);
	public void delete(String ord_id);
	public OrderlistVO findByPrimaryKey(String ord_id);
	public List<OrderlistVO> getAll();
	public Set<OrderdetailVO> getOrderdetailsByOrd_id(String ord_id);

	// new method
	// 訂單完成&撥款
	public void updateWithStore(OrderlistVO orderlistVO, StoreVO storeVO);
	// 下單&扣款&扣庫存量
	public void insertWithDetailAndCharge(OrderlistVO orderlistVO, Vector<OrderdetailVO> cartList, Vector<ProductVO> productList, MemberVO memberVO);
	// new method
	
	// Adding by Sylvie
	public OrderlistVO insertWithDetail(OrderlistVO orderlistVO, List<OrderdetailVO> list);

}