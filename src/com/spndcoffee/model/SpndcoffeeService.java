package com.spndcoffee.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.spndcoffeelist.model.SpndcoffeelistVO;


public class SpndcoffeeService {
	private SpndcoffeeDAO_interface dao;

	public SpndcoffeeService() {
		dao = new SpndcoffeeDAO();
	}

	public SpndcoffeeVO addSpndcoffee(String store_id, String spnd_name, String spnd_prod, Date spnd_enddate,
			Integer spnd_amt, byte[] spnd_img) {

		SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();

		spndcoffeeVO.setStore_id(store_id);
		spndcoffeeVO.setSpnd_name(spnd_name);
		spndcoffeeVO.setSpnd_prod(spnd_prod);
		spndcoffeeVO.setSpnd_enddate(spnd_enddate); //bugrerrr
		spndcoffeeVO.setSpnd_amt(spnd_amt);
		spndcoffeeVO.setSpnd_img(spnd_img);

		dao.insert(spndcoffeeVO);

		return spndcoffeeVO;
	}

	public SpndcoffeeVO updateSpndcoffee(String spnd_id, String store_id, String spnd_name, String spnd_prod, Date spnd_enddate,
			Integer spnd_amt, byte[] spnd_img) {

		SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();

		spndcoffeeVO.setSpnd_id(spnd_id);
		spndcoffeeVO.setStore_id(store_id);
		spndcoffeeVO.setSpnd_name(spnd_name);
		spndcoffeeVO.setSpnd_prod(spnd_prod);
		spndcoffeeVO.setSpnd_enddate(spnd_enddate);
		spndcoffeeVO.setSpnd_amt(spnd_amt);
		spndcoffeeVO.setSpnd_img(spnd_img);
		
		dao.update(spndcoffeeVO);

		return spndcoffeeVO;
	}

	public void deleteSpndcoffee(String spnd_id) {
		dao.delete(spnd_id);
	}

	public SpndcoffeeVO getOneSpndcoffee(String spnd_id) {
		return dao.findByPrimaryKey(spnd_id);
	}

	public List<SpndcoffeeVO> getAll() {
		return dao.getAll();
	}
	
	public Set<SpndcoffeelistVO> getSpndcoffeelistsBySpnd_id(String spnd_id) {
		return dao.getSpndcoffeelistsBySpnd_id(spnd_id);
	}
	
	// new
	public List<SpndcoffeeVO> getAllWithinTheDeadline() {
		return dao.getAllWithinTheDeadline();
	}
	// new
}
