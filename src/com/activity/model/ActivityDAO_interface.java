package com.activity.model;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import com.participant.model.ParticipantVO;
import com.rept_activ.model.Rept_activVO;

public interface ActivityDAO_interface {

	public void insert(ActivityVO activityVO);
	public void update(ActivityVO activityVO);
	public void delete(String activ_id);
	//這個活動有多少參加者
	public Set<ParticipantVO> getParticipantsByActiv_id(String activ_id);
	//活動清單上架中的活動
	public List<ActivityVO> getAll() throws IOException ;
	public Set<Rept_activVO> getRept_activsByActiv_id(String activ_id);
	
	
	//	以下新增
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	public ActivityVO getfindByPrimaryKey(String activ_id) throws IOException;
	//活動搜尋
	public List<ActivityVO> getSearch(String activ_name) throws IOException;
	//會員舉辦的活動狀態
	public List<ActivityVO> get_activity_run(String mem_id) throws IOException ;
	
	//更新會員狀態
	public void update_cfm(ActivityVO activityVO);
}