package com.rept_store.model;

import java.io.IOException;
import java.util.List;

public interface Rept_storeDAO_interface {

	public void insert(Rept_storeVO rept_storeVO);
	public void update(Rept_storeVO rept_storeVO);
	public void delete(String store_id, String mem_id);
	public Rept_storeVO findByPrimaryKey(String store_id, String mem_id);
	public List<Rept_storeVO> getAll();
	
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	public Rept_storeVO findByPrimaryKeyOne(String store_id);
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣

}