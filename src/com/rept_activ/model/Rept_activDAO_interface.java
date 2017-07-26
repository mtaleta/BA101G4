package com.rept_activ.model;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.activity.model.ActivityVO;

public interface Rept_activDAO_interface {

	public void insert(Rept_activVO rept_activVO);
	public void update(Rept_activVO rept_activVO);
	public void delete(String activ_id, String mem_id);
	public Rept_activVO findByPrimaryKey(String activ_id, String mem_id);
	public List<Rept_activVO> getAll();

	
	// 以下改過
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	public Rept_activVO findByPrimaryKey(String activ_id);
	public Set<ActivityVO> getActivitysByActivId(String activ_id);
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
}