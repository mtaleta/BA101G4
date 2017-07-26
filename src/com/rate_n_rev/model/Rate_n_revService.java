package com.rate_n_rev.model;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;



public class Rate_n_revService {



	
	private Rate_n_revDAO_interface dao;
	
	public Rate_n_revService(){
		dao = new Rate_n_revDAO();
	}
	
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public Rate_n_revVO addRate_n_rev(String mem_id,String store_id,Integer rnr_rate,String rnr_rev,Timestamp rnr_date){
		Rate_n_revVO rate_n_revVO = new Rate_n_revVO();
		
		rate_n_revVO.setMem_id(mem_id);
		rate_n_revVO.setStore_id(store_id);
		rate_n_revVO.setRnr_rate(rnr_rate);
		rate_n_revVO.setRnr_rev(rnr_rev);
		rate_n_revVO.setRnr_date(rnr_date);
		dao.insert(rate_n_revVO);
		
		return rate_n_revVO;
	}

	public Set<Rate_n_revVO> getAllRate_n_rev_ByStore_id(String store_id) throws IOException{
		return dao.getAllRate_n_rev_ByStore_id(store_id);
	}
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均

	
//	public StoreVO addstore(String tag_content){
//	StoreVO storeVO = new StoreVO();
//	
//	storeVO
//	dao.insert(StoreVO);
//	
//	return storeVO;
//}
//
//public TagVO updateTag(String tag_id,String tag_content){
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

//public StoreVO getOneStore(String store_id){
//	
//	return dao.findByPrimaryKey(store_id);
//	
//}

//public List<TagVO> getAll(){
//	return dao.getAll();
//}


}
