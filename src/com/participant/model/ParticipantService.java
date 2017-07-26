package com.participant.model;

import java.util.List;
import java.util.Set;

public class ParticipantService {

	
	// 以下做新增修改
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	
	private ParticipantDAO_interface dao;

	public ParticipantService() {
		dao = new ParticipantDAO();
	}

	public ParticipantVO addParticipant(String activ_id, String mem_id) {

		ParticipantVO participantVO = new ParticipantVO(activ_id, mem_id);

		participantVO.setActiv_id(activ_id);
		participantVO.setMem_id(mem_id);
		dao.insert(participantVO);

		return participantVO;
	}

//	public ParticipantVO updateParticipant(String activ_id, String mem_id) {
//
//		 ParticipantVO participantVO = new  ParticipantVO();
//	}

	public void deleteParticipant(String activ_id, String mem_id) {
		dao.delete(activ_id, mem_id);
	}

	public ParticipantVO getOneParticipant(String activ_id, String mem_id) {
		return dao.findByPrimaryKey(activ_id, mem_id);
	}
	public ParticipantVO getOneParticipant(String activ_id) {
		return dao.findByPrimaryKey(activ_id);
	}
	public Set<ParticipantVO> getAll() {
		return dao.getAll();
	}


}
