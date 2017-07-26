package com.orderlist.model;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.member.model.MemberVO;
import com.orderdetail.model.OrderdetailVO;
import com.product.model.ProductVO;
import com.store.model.StoreVO;

public class OrderlistService {

	private OrderlistDAO_interface dao;
	
	public OrderlistService() {
		dao = new OrderlistDAO();
	}
	
	public OrderlistVO addOrderlist(String mem_id, String store_id, Integer ord_total, Integer ord_pick,
			String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_seller) {

		OrderlistVO orderlistVO = new OrderlistVO();
		orderlistVO.setMem_id(mem_id);
		orderlistVO.setStore_id(store_id);
		orderlistVO.setOrd_total(ord_total);
		orderlistVO.setOrd_pick(ord_pick);
		orderlistVO.setOrd_add(ord_add);
		orderlistVO.setOrd_shipping(ord_shipping);
		orderlistVO.setOrd_time(ord_time);
		orderlistVO.setScore_seller(score_seller);
		dao.insert(orderlistVO);
		
		return orderlistVO;
	}

	// 此區塊與晴帆update()相同
	// 此區塊與晴帆update()相同
	public OrderlistVO updateOrderlist(String ord_id, String mem_id, String store_id, Integer ord_total,
			Integer ord_pick, String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_seller){
		
		OrderlistVO orderlistVO = new OrderlistVO();
		
		orderlistVO.setOrd_id(ord_id);
		orderlistVO.setMem_id(mem_id);
		orderlistVO.setStore_id(store_id);
		orderlistVO.setOrd_total(ord_total);
		orderlistVO.setOrd_pick(ord_pick);
		orderlistVO.setOrd_add(ord_add);
		orderlistVO.setOrd_shipping(ord_shipping);
		orderlistVO.setOrd_time(ord_time);
		orderlistVO.setScore_seller(score_seller);
		dao.update(orderlistVO);
		
		return orderlistVO;
	}
	// 此區塊與晴帆update()相同
	// 此區塊與晴帆update()相同
	
	// 此區塊與晴帆findByPrimaryKey()相同
	// 此區塊與晴帆findByPrimaryKey()相同
	public OrderlistVO getOneOrderlist(String ord_id) {
		return dao.findByPrimaryKey(ord_id);
	}
	// 此區塊與晴帆findByPrimaryKey()相同
	// 此區塊與晴帆findByPrimaryKey()相同
	
	public List<OrderlistVO> getAll(){
		return dao.getAll();
	}

	public Set<OrderdetailVO> getOrderdetailsByOrd_id(String ord_id){
		return dao.getOrderdetailsByOrd_id(ord_id);
	}
	
	// new method
	// 下單&扣款&扣庫存量
	public OrderlistVO addOrderWithDetailAndCharge(String mem_id, String store_id, Integer ord_total, Integer ord_pick,
			String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_seller, Vector<OrderdetailVO> cartList,
			Vector<ProductVO> productList, MemberVO memberVO) {

		OrderlistVO orderlistVO = new OrderlistVO();
		orderlistVO.setMem_id(mem_id);
		orderlistVO.setStore_id(store_id);
		orderlistVO.setOrd_total(ord_total);
		orderlistVO.setOrd_pick(ord_pick);
		orderlistVO.setOrd_add(ord_add);
		orderlistVO.setOrd_shipping(ord_shipping);
		orderlistVO.setOrd_time(ord_time);
		orderlistVO.setScore_seller(score_seller);
		dao.insertWithDetailAndCharge(orderlistVO, cartList, productList, memberVO);

		return orderlistVO;
	}

	// new method
	// 訂單完成&撥款
	public OrderlistVO updateWithStore(String ord_id, String mem_id, String store_id, Integer ord_total,
			Integer ord_pick, String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_seller, StoreVO storeVO){
		
		OrderlistVO orderlistVO = new OrderlistVO();
		
		orderlistVO.setOrd_id(ord_id);
		orderlistVO.setMem_id(mem_id);
		orderlistVO.setStore_id(store_id);
		orderlistVO.setOrd_total(ord_total);
		orderlistVO.setOrd_pick(ord_pick);
		orderlistVO.setOrd_add(ord_add);
		orderlistVO.setOrd_shipping(ord_shipping);
		orderlistVO.setOrd_time(ord_time);
		orderlistVO.setScore_seller(score_seller);
		dao.updateWithStore(orderlistVO, storeVO);
		
		return orderlistVO;
	}
	
	// Adding by Sylvie
    public OrderlistVO update(String ord_id, String mem_id, String store_id, Integer ord_total, Integer ord_pick, String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_seller) {
        OrderlistVO orderlistVO = new OrderlistVO();
        orderlistVO.setOrd_id(ord_id);
        orderlistVO.setMem_id(mem_id);
        orderlistVO.setStore_id(store_id);
        orderlistVO.setOrd_total(ord_total);
        orderlistVO.setOrd_pick(ord_pick);
        orderlistVO.setOrd_add(ord_add);
        orderlistVO.setOrd_shipping(ord_shipping);
        orderlistVO.setOrd_time(ord_time);
        orderlistVO.setScore_seller(score_seller);

        dao.update(orderlistVO);
        return orderlistVO;
    }
	
    // Adding by Sylvie
    public OrderlistVO findByPrimaryKey(String ord_id) {
        return dao.findByPrimaryKey(ord_id);
    }
	
    // Adding by Sylvie
    public OrderlistVO insertWithDetail(String mem_id, String store_id, Integer ord_total, Integer ord_pick, String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_seller, List<OrderdetailVO> list) {
        OrderlistVO orderlistVO = new OrderlistVO();
        orderlistVO.setMem_id(mem_id);
        orderlistVO.setStore_id(store_id);
        orderlistVO.setOrd_total(ord_total);
        orderlistVO.setOrd_pick(ord_pick);
        orderlistVO.setOrd_add(ord_add);
        orderlistVO.setOrd_shipping(ord_shipping);
        orderlistVO.setOrd_time(ord_time);
        orderlistVO.setScore_seller(score_seller);

        dao.insertWithDetail(orderlistVO, list);
        return orderlistVO;
    }
}
