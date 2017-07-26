package com.product.model;

import java.sql.Connection;
import java.util.List;
import java.util.Set;
import com.orderdetail.model.OrderdetailVO;
import com.msg.model.MsgVO;

public interface ProductDAO_interface {

	public void insert(ProductVO productVO);
	public void update(ProductVO productVO);
	public void delete(String prod_id);
	public ProductVO findByPrimaryKey(String prod_id);
	public List<ProductVO> getAll();
	public Set<OrderdetailVO> getOrderdetailsByProd_id(String prod_id);
	public Set<MsgVO> getMsgsByProd_id(String prod_id);

	// new method
	// 搜尋單一購物類型的商品名稱
	public List<ProductVO> searchLaunchedByProd_categoryAndProd_name(Integer prod_category, String[] search);
	// 下單時扣庫存量
	public void updateAmt(ProductVO productVO, Connection con);
	// 單一種購物類型的上架商品
	public Set<ProductVO> launchedByProd_categoryDesc(Integer prod_category);
	// new method
}