package com.category.model;

import java.util.List;
import java.util.Set;
import com.product.model.ProductVO;

public interface CategoryDAO_interface {

	public void insert(CategoryVO categoryVO);
	public void update(CategoryVO categoryVO);
	public void delete(String cate_id);
	public CategoryVO findByPrimaryKey(String cate_id);
	public List<CategoryVO> getAll();
	public Set<ProductVO> getProductsByCate_id(String cate_id);

	// new method
	// 查詢單一種商品類型的全部商品
	public Set<ProductVO> getLaunchedProductsByCate_idDesc(String cate_id);
	public CategoryVO getOneByCate_nameAndProd_category(String cate_name, Integer prod_category);
	// 此區塊與晴帆findCateByProd_cate()相同
	public Set<CategoryVO> getByProd_category(Integer prod_category);
	// 此區塊與晴帆findCateByProd_cate()相同
	// new method
	
	// Adding by Sylvie
	List<CategoryVO> findCateByProd_cate(Integer prod_category);
}