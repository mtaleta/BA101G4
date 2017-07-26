package com.photo_store.model;

import java.util.List;

public class Photo_storeService {
private Photo_storeDAO_interface dao;
	
	public Photo_storeService(){
		dao = new Photo_storeDAO();
	}
	
	// 晨均
	public List<Photo_storeVO> getStorePicture(String store_id){
		return dao.getStorePicture(store_id);
	}
	
	// Adding by Sylvie
    public Photo_storeVO insert(String mem_id,byte[] photo, String store_id, String photo_desc) {
    	Photo_storeVO photo_storeVO = new Photo_storeVO();
    	photo_storeVO.setMem_id(mem_id);
    	photo_storeVO.setPhoto(photo);
    	photo_storeVO.setStore_id(store_id);
    	photo_storeVO.setPhoto_desc(photo_desc);
        dao.insert(photo_storeVO);
        return photo_storeVO;
    }
	
    // Adding by Sylvie
	public List<Photo_storeVO> getPhotoByStore_id(String store_id) {
		return dao.getPhotoByStore_id(store_id);
	}
}
