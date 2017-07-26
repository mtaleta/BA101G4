package com.report.model;

import java.io.IOException;
import java.util.List;

public interface ReportDAO_interface {

	public void insert(ReportVO reportVO);
	public void update(ReportVO reportVO);
	public void delete(String mem_id, String rnr_id);
	public ReportVO findByPrimaryKey(String mem_id, String rnr_id);
	public List<ReportVO> getAll();

	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	ReportVO findByPrimaryKey(String mem_id);
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	
}