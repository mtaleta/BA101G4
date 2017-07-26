package com.category.model;

import java.io.Serializable;

public class CategoryVO implements Serializable {

	private String cate_id;
	private String cate_name;
	private Integer prod_category;

	public CategoryVO(){}

	public CategoryVO(String cate_id, String cate_name, Integer prod_category){
		this.cate_id = cate_id;
		this.cate_name = cate_name;
		this.prod_category = prod_category;
	}

	public String getCate_id() {
		return cate_id;
	}

	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public Integer getProd_category() {
		return prod_category;
	}

	public void setProd_category(Integer prod_category) {
		this.prod_category = prod_category;
	}

}