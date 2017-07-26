package com.store_tag.model;

import java.util.List;
import java.util.Set;

public interface Store_tagDAO_interface {

	public void insert(Store_tagVO store_tagVO);
	public void update(Store_tagVO store_tagVO);
	public void delete(String store_id, String tag_id);
	public Store_tagVO findByPrimaryKey(String store_id, String tag_id);
	public List<Store_tagVO> getAll();

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public Set<Store_tagVO> getStoresIdByTag_id(String tag_id);
	public Set<Store_tagVO> getTagsIdByStore_id(String store_id);
	
}