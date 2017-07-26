package com.advertisment.model;

import java.util.List;

public interface AdvertismentDAO_interface {

	public void insert(AdvertismentVO advertismentVO);
	public void update(AdvertismentVO advertismentVO);
	public void delete(String ad_id);
	public AdvertismentVO findByPrimaryKey(String ad_id);
	public List<AdvertismentVO> getAll();
	
	
	public List<AdvertismentVO> getAllup();
	public void updateUP(String ad_id);
	public void updateDOWN(String ad_id);
}