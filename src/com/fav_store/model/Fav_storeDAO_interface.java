package com.fav_store.model;

import java.util.List;
import java.util.Set;

public interface Fav_storeDAO_interface {

	public void insert(Fav_storeVO fav_storeVO);
	public void delete(String mem_id, String store_id);
	public Fav_storeVO findByPrimaryKey(String mem_id, String store_id);
	public List<Fav_storeVO> getAll();

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public Set<Fav_storeVO> getAllFavStoreByMem_id(String mem_id);
}