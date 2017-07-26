package com.rept_activ.model;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.activity.model.ActivityVO;

public class Rept_activService {

	private Rept_activDAO_interface dao;

	public Rept_activService() {
		dao = new Rept_activDAO();
	}
	
	
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	
	public Rept_activVO addRept_activ(String activ_id, String mem_id, String repo_rsn, Integer repo_rev) {

		Rept_activVO rept_activVO = new Rept_activVO(activ_id, mem_id,repo_rsn,repo_rev);

		rept_activVO.setActiv_id(activ_id);
		rept_activVO.setMem_id(mem_id);
		rept_activVO.setRepo_rsn(repo_rsn);
		rept_activVO.setRepo_rev(repo_rev);
		
		dao.insert(rept_activVO);

		return rept_activVO;
	}


	public void deleteRept_activ(String activ_id, String mem_id) {
		dao.delete(activ_id, mem_id);
	}

	public Rept_activVO getOneRept_activ(String activ_id) throws IOException {
		return dao.findByPrimaryKey(activ_id);
	}
	public List<Rept_activVO> getAll() throws IOException {
		return dao.getAll();
	}

	public Set<ActivityVO> getActivitysByActivId(String activ_id) throws IOException {
		return dao.getActivitysByActivId(activ_id);
	}
	
	public Rept_activVO updateRept_activ(String activ_id, String mem_id, String repo_rsn, Integer repo_rev) {
		
		Rept_activVO rept_activVO = new Rept_activVO();

		rept_activVO.setActiv_id(activ_id);
		rept_activVO.setMem_id(mem_id);
		rept_activVO.setRepo_rsn(repo_rsn);
		rept_activVO.setRepo_rev(repo_rev);
		
		dao.update(rept_activVO);

		return rept_activVO;
	}
}
