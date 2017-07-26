package com.product.model;

import java.util.List;
import java.util.Set;

import com.msg.model.MsgVO;

public class ProductService {

	private ProductDAO_interface dao;
	
	public ProductService() {
		dao = new ProductDAO();
	}
	
	// 此區塊與晴帆insert()相同
	// 此區塊與晴帆insert()相同
	public ProductVO addProduct(String store_id, String prod_name, String cate_id, Integer prod_price,
			Integer prod_category, byte[] prod_img, Integer prod_amt, Integer prod_launch, String prod_desc) {

		ProductVO productVO = new ProductVO();

		productVO.setStore_id(store_id);
		productVO.setProd_name(prod_name);
		productVO.setCate_id(cate_id);
		productVO.setProd_price(prod_price);
		productVO.setProd_category(prod_category);
		productVO.setProd_img(prod_img);
		productVO.setProd_amt(prod_amt);
		productVO.setProd_launch(prod_launch);
		productVO.setProd_desc(prod_desc);
		dao.insert(productVO);

		return productVO;
	}
	// 此區塊與晴帆insert()相同
	// 此區塊與晴帆insert()相同
	
	// 此區塊與晴帆update()相同
	// 此區塊與晴帆update()相同
	public ProductVO updateProduct(String prod_id, String store_id, String prod_name, String cate_id, Integer prod_price,
			Integer prod_category, byte[] prod_img, Integer prod_amt, Integer prod_launch, String prod_desc){
		
		ProductVO productVO = new ProductVO();

		productVO.setProd_id(prod_id);
		productVO.setStore_id(store_id);
		productVO.setProd_name(prod_name);
		productVO.setCate_id(cate_id);
		productVO.setProd_price(prod_price);
		productVO.setProd_category(prod_category);
		productVO.setProd_img(prod_img);
		productVO.setProd_amt(prod_amt);
		productVO.setProd_launch(prod_launch);
		productVO.setProd_desc(prod_desc);
		dao.update(productVO);

		return productVO;
	}
	// 此區塊與晴帆update()相同
	// 此區塊與晴帆update()相同
	
	// 此區塊與晴帆findByPrimaryKey()相同
	// 此區塊與晴帆findByPrimaryKey()相同
	public ProductVO getOneProduct(String prod_id) {
		return dao.findByPrimaryKey(prod_id);
	}
	// 此區塊與晴帆findByPrimaryKey()相同
	// 此區塊與晴帆findByPrimaryKey()相同
	
	public List<ProductVO> getAll(){
		return dao.getAll();
	}
	
	public Set<MsgVO> getMsgsByProd_id(String prod_id) {
		return dao.getMsgsByProd_id(prod_id);
	}
	
	// new method
	// 單一種購物類型的上架商品
	public Set<ProductVO> launchedByProd_categoryDesc(Integer prod_category) {
		return dao.launchedByProd_categoryDesc(prod_category);
	}
	
	// new method
	// 搜尋單一購物類型的商品名稱
	public List<ProductVO> searchLaunchedByProd_categoryAndProd_name(Integer prod_category, String[] search){
		return dao.searchLaunchedByProd_categoryAndProd_name(prod_category, search);
	}
	
	// Adding by Sylvie
    public ProductVO insert(String store_id, String prod_name, String cate_id, Integer prod_price, Integer prod_category, byte[] prod_img, Integer prod_amt, Integer prod_launch, String prod_desc) {
        ProductVO productVO = new ProductVO();
        productVO.setStore_id(store_id);
        productVO.setProd_name(prod_name);
        productVO.setCate_id(cate_id);
        productVO.setProd_price(prod_price);
        productVO.setProd_category(prod_category);
        productVO.setProd_img(prod_img);
        productVO.setProd_amt(prod_amt);
        productVO.setProd_launch(prod_launch);
        productVO.setProd_desc(prod_desc);       
        dao.insert(productVO);
        return productVO;
    }

	// Adding by Sylvie
    public ProductVO update(String prod_id, String store_id, String prod_name, String cate_id, Integer prod_price, Integer prod_category, byte[] prod_img, Integer prod_amt, Integer prod_launch, String prod_desc) {
        ProductVO productVO = new ProductVO();
        productVO.setProd_id(prod_id);
        productVO.setStore_id(store_id);
        productVO.setProd_name(prod_name);
        productVO.setCate_id(cate_id);
        productVO.setProd_price(prod_price);
        productVO.setProd_category(prod_category);
        productVO.setProd_img(prod_img);
        productVO.setProd_amt(prod_amt);
        productVO.setProd_launch(prod_launch);
        productVO.setProd_desc(prod_desc);
        dao.update(productVO);
        return productVO;
    }

    // Adding by Sylvie
    public ProductVO findByPrimaryKey(String prod_id) {
        return dao.findByPrimaryKey(prod_id);
    }

    // Adding by Sylvie
	public ProductVO update(ProductVO productVO) {
		dao.update(productVO);
		return productVO;		
	}
}
