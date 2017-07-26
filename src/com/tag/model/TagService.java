package com.tag.model;

import java.util.List;
import java.util.Set;



public class TagService {

	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	
	private TagDAO_interface dao;
	
	public TagService(){
		dao = new TagDAO();
	}

	public TagVO addTag(String tag_content){
		TagVO tagVO = new TagVO();
		
		tagVO.setTag_content(tag_content);
		dao.insert(tagVO);
		
		return tagVO;
	}

	public TagVO getTag_id_BY_Tag_content(String tag_content){
		
		return dao.getTag_id_BY_Tag_content(tag_content);
		
	}
	
	public TagVO getOneTag(String tag_id){
		
		return dao.findByPrimaryKey(tag_id);
		
	}

	public List<TagVO> getAll(){
		return dao.getAll();
	}
	
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	
//	public Set<StoreVO> getStoresByStore_add(String store_add){
//		return dao.getStoresByStore_add(store_add);
//	}
	
//	public TagVO updateTag(String tag_id,String tag_content){
//	TagVO tagVO = new TagVO();
//	
//	tagVO.setTag_id(tag_id);
//	tagVO.setTag_content(tag_content);
//	dao.update(tagVO);
//	
//	return tagVO;
//	
//}
//
//public void deleteTag(String tag_id){
//	dao.delete(tag_id);
//}


}
