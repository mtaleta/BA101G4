package com.rate_n_rev.model;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import com.report.model.ReportVO;

public interface Rate_n_revDAO_interface {

	public void insert(Rate_n_revVO rate_n_revVO);
	public void update(Rate_n_revVO rate_n_revVO);
	public void delete(String rnr_id);
	public Rate_n_revVO findByPrimaryKey(String rnr_id);
	public List<Rate_n_revVO> getAll();
	public Set<ReportVO> getReportsByRnr_id(String rnr_id);

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public Set<Rate_n_revVO> getAllRate_n_rev_ByStore_id(String store_id);
}