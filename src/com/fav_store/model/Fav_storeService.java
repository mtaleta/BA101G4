package com.fav_store.model;

import java.util.List;
import java.util.Set;

public class Fav_storeService {
	private Fav_storeDAO_interface dao;
	
	public Fav_storeService(){
		dao = new Fav_storeDAO();
	}
	
	
	public Fav_storeVO addFav_store(String mem_id,String store_id){//create BY 趙帥
		Fav_storeVO fav_storeVO =new Fav_storeVO();
		
		fav_storeVO.setMem_id(mem_id);
		fav_storeVO.setStore_id(store_id);
		dao.insert(fav_storeVO);
		
		return fav_storeVO;
	}
	
	
	public void deleteFav_store(String mem_id,String store_id){//create BY 趙帥
		dao.delete(mem_id, store_id);
	}
	
	public Fav_storeVO findByPrimaryKey(String mem_id, String store_id){//create BY 趙帥
		return dao.findByPrimaryKey(mem_id, store_id);
	}
	
	public List<Fav_storeVO> getAll(){//create BY 趙帥
		return dao.getAll();
	}
	
	public Set<Fav_storeVO> getAllFavStoreByMem_id(String mem_id){//create BY 趙帥
		return dao.getAllFavStoreByMem_id(mem_id);
	}
	
	
}
