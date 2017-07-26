package com.spndcoffeelist.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.spndcoffeercd.model.*;



public class SpndcoffeelistService {
	private SpndcoffeelistDAO_interface dao;

	public SpndcoffeelistService() {
		dao = new SpndcoffeelistDAO();
	}

	public SpndcoffeelistVO addSpndcoffeelist(String spnd_id, String mem_id, String spnd_prod, String store_id,
			Integer list_amt, Integer list_left, Date list_date) {

		SpndcoffeelistVO spndcoffeelistVO = new SpndcoffeelistVO();

		spndcoffeelistVO.setSpnd_id(spnd_id);
		spndcoffeelistVO.setMem_id(mem_id);
		spndcoffeelistVO.setSpnd_prod(spnd_prod);
		spndcoffeelistVO.setStore_id(store_id); 
		spndcoffeelistVO.setList_amt(list_amt);
		spndcoffeelistVO.setList_left(list_left);
		spndcoffeelistVO.setList_date((Timestamp) list_date);
		
		dao.insert(spndcoffeelistVO);

		return spndcoffeelistVO;
	}

	public SpndcoffeelistVO updateSpndcoffeelist(String list_id, String spnd_id, String mem_id, String spnd_prod, String store_id,
			Integer list_amt, Integer list_left, Date list_date) {

		SpndcoffeelistVO spndcoffeelistVO = new SpndcoffeelistVO();

		spndcoffeelistVO.setList_id(list_id);
		spndcoffeelistVO.setSpnd_id(spnd_id);
		spndcoffeelistVO.setMem_id(mem_id);
		spndcoffeelistVO.setSpnd_prod(spnd_prod);
		spndcoffeelistVO.setStore_id(store_id); 
		spndcoffeelistVO.setList_amt(list_amt);
		spndcoffeelistVO.setList_left(list_left);
		spndcoffeelistVO.setList_date((Timestamp) list_date);
		
		dao.update(spndcoffeelistVO);

		return spndcoffeelistVO;
	}

	public void deleteSpndcoffeelist(String list_id) {
		dao.delete(list_id);
	}

	public SpndcoffeelistVO getOneSpndcoffeelist(String list_id) {
		return dao.findByPrimaryKey(list_id);
	}
	
	

	public List<SpndcoffeelistVO> getAll() {
		return dao.getAll();
	}
	
	public Set<SpndcoffeercdVO> getSpndcoffeercdsByList_id(String list_id) {
		return dao.getSpndcoffeercdsByList_id(list_id);
	}
	
	public SpndcoffeelistVO getSpndcoffeelistsByList_id(String list_id) {
		return dao.findByPrimaryKey(list_id);
	}
	


}
