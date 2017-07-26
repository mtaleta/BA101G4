package com.report.model;

import java.io.IOException;
import java.util.List;

import com.rept_store.model.Rept_storeVO;

public class ReportService {

	private ReportDAO_interface dao;

	public ReportService() {
		dao = new ReportDAO();
	}

	public ReportVO addReport(String mem_id, String rnr_id, Integer rept_verf, String rept_rsn) {

		ReportVO reportVO = new ReportVO(mem_id, rnr_id, rept_verf, rept_rsn);

		reportVO.setMem_id(mem_id);
		reportVO.setRnr_id(rnr_id);
		reportVO.setRept_verf(rept_verf);
		reportVO.setRept_rsn(rept_rsn);
		
		dao.insert(reportVO);

		return reportVO;
	}

	public ReportVO updateReport(String mem_id, String rnr_id, Integer rept_verf, String rept_rsn) {

		ReportVO reportVO = new ReportVO();

		reportVO.setMem_id(mem_id);
		reportVO.setRnr_id(rnr_id);
		reportVO.setRept_verf(rept_verf);
		reportVO.setRept_rsn(rept_rsn);
		
		dao.update(reportVO);

		return reportVO;
	}

	public void deleteReport(String mem_id, String rnr_id) {
		dao.delete(mem_id, rnr_id);
	}

	public ReportVO getOneReport(String mem_id){
		return dao.findByPrimaryKey(mem_id, mem_id);
	}
	public List<ReportVO> getAll(){
		return dao.getAll();
	}


}
