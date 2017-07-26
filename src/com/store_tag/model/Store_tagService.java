package com.store_tag.model;

import java.util.HashMap;
import java.util.List;
import java.util.Set;



public class Store_tagService {


	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	
	private Store_tagDAO_interface dao;
	
	public Store_tagService(){
		dao = new Store_tagDAO();
	}

	public Store_tagVO addstore_tag(String store_id,String tag_id,Integer tag_num){
		Store_tagVO store_tagVO = new Store_tagVO();
		
		store_tagVO.setStore_id(store_id);
		store_tagVO.setTag_id(tag_id);
		store_tagVO.setTag_num(tag_num);
		dao.insert(store_tagVO);
		
		return store_tagVO;
	}

	public Set<Store_tagVO> getStoresIdByTag_id(String tag_id){
		return dao.getStoresIdByTag_id(tag_id);
	}
	
	public Set<Store_tagVO> getTagsIdByStore_id(String store_id){
		return dao.getTagsIdByStore_id(store_id);
	}
	
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	
//	
//	public TagVO updateTag(String tag_id,String tag_content){
//		TagVO tagVO = new TagVO();
//		
//		tagVO.setTag_id(tag_id);
//		tagVO.setTag_content(tag_content);
//		dao.update(tagVO);
//		
//		return tagVO;
//		
//	}
//
//	public void deleteTag(String tag_id){
//		dao.delete(tag_id);
//	}
	
//	public StoreVO getOneStore(String store_id){
//		
//		return dao.findByPrimaryKey(store_id);
//		
//	}

//	public List<TagVO> getAll(){
//		return dao.getAll();
//	}


}
