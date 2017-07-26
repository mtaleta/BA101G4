package com.spndcoffeercd.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.spndcoffeercd.model.*;


public class SpndcoffeercdService {
	private SpndcoffeercdDAO_interface dao;

	public SpndcoffeercdService() {
		dao = new SpndcoffeercdDAO();
	}

	public SpndcoffeercdVO addSpndcoffeercd(String rcd_id, String list_id, Integer single_amt, Timestamp rcd_date) {

		SpndcoffeercdVO spndcoffeercdVO = new SpndcoffeercdVO();

		spndcoffeercdVO.setList_id(list_id);
		spndcoffeercdVO.setSingle_amt(single_amt);
		spndcoffeercdVO.setRcd_date((Timestamp) rcd_date);
		
		dao.insert(spndcoffeercdVO);

		return spndcoffeercdVO;
	}

	public SpndcoffeercdVO updateSpndcoffeercd(String list_id, Integer single_amt, Timestamp rcd_date) {

		SpndcoffeercdVO spndcoffeercdVO = new SpndcoffeercdVO();

		spndcoffeercdVO.setList_id(list_id);
		spndcoffeercdVO.setSingle_amt(single_amt);
		spndcoffeercdVO.setRcd_date((Timestamp) rcd_date);
		
		dao.update(spndcoffeercdVO);

		return spndcoffeercdVO;
	}

	public void deleteSpndcoffeercd(String rcd_id) {
		dao.delete(rcd_id);
	}

	public SpndcoffeercdVO getOneSpndcoffeercd(String rcd_id) {
		return dao.findByPrimaryKey(rcd_id);
	}

	public List<SpndcoffeercdVO> getAll() {
		return dao.getAll();
	}

	
	

}
