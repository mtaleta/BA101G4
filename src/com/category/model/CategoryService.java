package com.category.model;

import java.util.List;
import java.util.Set;

import com.product.model.ProductVO;

public class CategoryService {

	private CategoryDAO_interface dao;
	
	public CategoryService(){
		dao = new CategoryDAO();
	}

	// 此區塊與晴帆insert()相同
	// 此區塊與晴帆insert()相同
	public CategoryVO addCategory(String cate_name, Integer prod_category){
		
		CategoryVO categoryVO = new CategoryVO();
		categoryVO.setCate_name(cate_name);
		categoryVO.setProd_category(prod_category);
		dao.insert(categoryVO);
		
		return categoryVO; 
	}
	// 此區塊與晴帆insert()相同
	// 此區塊與晴帆insert()相同
	
    public void delete(String cate_id) {
        dao.delete(cate_id);
    }
	
	// 此區塊與晴帆findByPrimaryKey()相同
	// 此區塊與晴帆findByPrimaryKey()相同
	public CategoryVO getOneCategory(String cate_id){
		return dao.findByPrimaryKey(cate_id);
	}
	// 此區塊與晴帆findByPrimaryKey()相同
	// 此區塊與晴帆findByPrimaryKey()相同
	
	public List<CategoryVO> getAll(){
		return dao.getAll();
	}
	
	// new method
	// 查詢單一種商品類型的全部商品
	public Set<ProductVO> getLaunchedProductsByCate_idDesc(String cate_id){
		return dao.getLaunchedProductsByCate_idDesc(cate_id);
	}
	// new method
	
	// new method
	public CategoryVO getOneByCate_nameAndProd_category(String cate_name, Integer prod_category){
		return dao.getOneByCate_nameAndProd_category(cate_name, prod_category);
	}
	// new method
	
	// new method
	// 此區塊與晴帆findCateByProd_cate()相同
	// 此區塊與晴帆findCateByProd_cate()相同
	public Set<CategoryVO> getByProd_category(Integer prod_category){
		return dao.getByProd_category(prod_category);
	}
	// 此區塊與晴帆findCateByProd_cate()相同
	// 此區塊與晴帆findCateByProd_cate()相同
	// new method
	
	// Adding by Sylvie
    public CategoryVO insert(String cate_name) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setCate_name(cate_name);
        dao.insert(categoryVO);
        return categoryVO;
    }

    // Adding by Sylvie
    public CategoryVO update(String cate_id, String cate_name) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setCate_id(cate_id);
        categoryVO.setCate_name(cate_name);
        dao.update(categoryVO);
        return categoryVO;
    }
	
	// Adding by Sylvie
    public CategoryVO findByPrimaryKey(String cate_id) {
        return dao.findByPrimaryKey(cate_id);
    }
	
    // Adding by Sylvie
	public List<CategoryVO> findCateByProd_cate(int prod_category) {
		return dao.findCateByProd_cate(prod_category);
	}
	


}
