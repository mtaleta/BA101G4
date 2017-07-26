package com.photo_store.model;

import java.util.List;

public interface Photo_storeDAO_interface {

	public void insert(Photo_storeVO photo_storeVO);
	public void update(Photo_storeVO photo_storeVO);
	public void delete(String photo_id);
	public Photo_storeVO findByPrimaryKey(String photo_id);
	public List<Photo_storeVO> getAll();
	
	// 晨均
	public List<Photo_storeVO> getStorePicture(String store_id);
	
	// Adding by Sylvie
	public List<Photo_storeVO> getPhotoByStore_id(String store_id);
}