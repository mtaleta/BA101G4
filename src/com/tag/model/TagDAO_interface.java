package com.tag.model;

import java.util.List;
import java.util.Set;
import com.store_tag.model.Store_tagVO;

public interface TagDAO_interface {

	public void insert(TagVO tagVO);
	public void update(TagVO tagVO);
	public void delete(String tag_id);
	public TagVO findByPrimaryKey(String tag_id);
	public List<TagVO> getAll();
	public Set<Store_tagVO> getStore_tagsByTag_id(String tag_id);

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public TagVO getTag_id_BY_Tag_content(String tag_content);
}