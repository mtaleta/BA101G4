package com.activity.model;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.participant.model.ParticipantVO;

public class ActivityService {

	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	
	private ActivityDAO_interface dao;

	public ActivityService() {
		dao = new ActivityDAO();
	}

	public ActivityVO addActivity(String activ_id, String mem_id, String store_id,
			String activ_name, Timestamp activ_starttime, Timestamp activ_endtime, 
			Timestamp activ_expire,byte[] activ_img, String activ_summary,
			String activ_intro, Integer activ_num, Integer activ_store_cfm) {

		ActivityVO activityVO = new ActivityVO();

		activityVO.setActiv_id(activ_id);
		activityVO.setMem_id(mem_id);
		activityVO.setStore_id(store_id);
		activityVO.setActiv_name(activ_name);
		activityVO.setActiv_starttime(activ_starttime);
		activityVO.setActiv_endtime(activ_endtime);
		activityVO.setActiv_expire(activ_expire);
		activityVO.setActiv_img(activ_img);
		activityVO.setActiv_summary(activ_summary);
		activityVO.setActiv_intro(activ_intro);
		activityVO.setActiv_num(activ_num);
		activityVO.setActiv_store_cfm(activ_store_cfm);
		
		
		dao.insert(activityVO);

		return activityVO;
	}

	public ActivityVO updateActivity(String activ_id, String mem_id, String store_id,
			String activ_name, Timestamp activ_starttime, Timestamp activ_endtime, 
			Timestamp activ_expire, byte[] activ_img, String activ_summary,
			String activ_intro, Integer activ_num, Integer activ_store_cfm) {

		ActivityVO activityVO = new ActivityVO();

		activityVO.setActiv_id(activ_id);
		activityVO.setMem_id(mem_id);
		activityVO.setStore_id(store_id);
		activityVO.setActiv_name(activ_name);
		activityVO.setActiv_starttime(activ_starttime);
		activityVO.setActiv_endtime(activ_endtime);
		activityVO.setActiv_expire(activ_expire);
		activityVO.setActiv_img(activ_img);
		activityVO.setActiv_summary(activ_summary);
		activityVO.setActiv_intro(activ_intro);
		activityVO.setActiv_num(activ_num);
		activityVO.setActiv_store_cfm(activ_store_cfm);
		
		
		dao.update(activityVO);

		return activityVO;
	}

	public void deleteActivity(String activ_id) {
		dao.delete(activ_id);
	}

	public List<ActivityVO> get_activity_run(String mem_id) throws IOException {
		return dao.get_activity_run(mem_id);
	}
	
	public ActivityVO getOneActivityOne(String activ_id) throws IOException {
		return dao.getfindByPrimaryKey(activ_id);
	}

	public List<ActivityVO> getAll() throws IOException {
		return dao.getAll();
	}
	
	public Set<ParticipantVO> getParticipantsByActiv_id(String activ_id){
		return dao.getParticipantsByActiv_id(activ_id);
	}
	//查詢活動
	public List<ActivityVO> getSearch(String activ_name) throws IOException{
		return dao.getSearch(activ_name);
	}
	//更新活動狀態
	public ActivityVO update_cfm(String activ_id,Integer activ_store_cfm) {

		ActivityVO activityVO = new ActivityVO();
		activityVO.setActiv_id(activ_id);
		activityVO.setActiv_store_cfm(activ_store_cfm);
			
		dao.update_cfm(activityVO);
		return activityVO;
	}
	
}
