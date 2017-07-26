package com.participant.model;

import java.util.List;
import java.util.Set;

public interface ParticipantDAO_interface {

	public void insert(ParticipantVO participantVO);
	public void delete(String activ_id, String mem_id);
	public ParticipantVO findByPrimaryKey(String activ_id, String mem_id);
	
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	/*
	public List<ParticipantVO> getAll();
	*/
	
	// 以下做新增修改
	public Set<ParticipantVO> getAll() ;
	ParticipantVO findByPrimaryKey(String activ_id);
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
}