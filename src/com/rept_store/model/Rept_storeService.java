package com.rept_store.model;

import java.io.IOException;
import java.util.List;

public class Rept_storeService {

	private Rept_storeDAO_interface dao;

	public Rept_storeService() {
		dao = new Rept_storeDAO();
	}

	public Rept_storeVO addRept_store(String store_id, String mem_id, String rept_rsn, Integer rept_rev) {

		Rept_storeVO rept_storeVO = new Rept_storeVO();

		rept_storeVO.setStore_id(store_id);
		rept_storeVO.setMem_id(mem_id);
		rept_storeVO.setRept_rsn(rept_rsn);
		rept_storeVO.setRept_rev(rept_rev);
		
		dao.insert(rept_storeVO);

		return rept_storeVO;
	}

	public Rept_storeVO updateRept_store(String store_id, String mem_id, String rept_rsn, Integer rept_rev) {

		Rept_storeVO rept_storeVO = new Rept_storeVO();

		rept_storeVO.setStore_id(store_id);
		rept_storeVO.setMem_id(mem_id);
		rept_storeVO.setRept_rsn(rept_rsn);
		rept_storeVO.setRept_rev(rept_rev);
		
		dao.update(rept_storeVO);

		return rept_storeVO;
	}

	public void deleteRept_store(String store_id, String mem_id) {
		dao.delete(store_id, mem_id);
	}

	public Rept_storeVO getOneRept_store(String store_id, String mem_id) throws IOException {
		return dao.findByPrimaryKey(store_id, mem_id);
	}
	public Rept_storeVO getOneRept_store(String store_id) throws IOException {
		return dao.findByPrimaryKeyOne(store_id);
	}

	public List<Rept_storeVO> getAll() throws IOException {
		return dao.getAll();
	}
}
