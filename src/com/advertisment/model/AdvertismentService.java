package com.advertisment.model;

import java.sql.Timestamp;
import java.util.List;



public class AdvertismentService {
private AdvertismentDAO_interface dao;
	
	public AdvertismentService(){
		dao = new AdvertismentDAO();
	}
	
	public List<AdvertismentVO> getAll(){
		return dao.getAll();
	}
	
	public AdvertismentVO getAdvertismentContentByAD_ID(String ad_id){
		return dao.findByPrimaryKey(ad_id);
	}
	
	public void updateUP(String ad_id){
		dao.updateUP(ad_id);
	}
	
	public void updateDOWN(String ad_id){
		
		dao.updateDOWN(ad_id);
	}
	
	public List<AdvertismentVO> getAllup(){
		return dao.getAllup();
	}
	
	public AdvertismentVO addAdvertisment(String ad_title,String ad_content,byte[] ad_img,Timestamp ad_date,Integer ad_status){
		AdvertismentVO advertismentVO =  new AdvertismentVO();
		
		advertismentVO.setAd_title(ad_title);
		advertismentVO.setAd_content(ad_content);
		advertismentVO.setAd_img(ad_img);
		advertismentVO.setAd_date(ad_date);
		advertismentVO.setAd_status(ad_status);
		dao.insert(advertismentVO);
		
		return advertismentVO;
	}
	
}
