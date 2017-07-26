package com.feature.model;

import java.util.List;

public class FeatureService {

	private FeatureDAO_interface dao;

	public FeatureService() {
		dao = new FeatureDAO();
	}

	public FeatureVO addFeature(String feature_name) {

		FeatureVO featureVO = new FeatureVO();
		featureVO.setFeature_name(feature_name);;
		
		dao.insert(featureVO);

		return featureVO;
	}

	public FeatureVO updateFeature(String feature_id, String feature_name) {

		FeatureVO featureVO = new FeatureVO();
		featureVO.setFeature_id(feature_id);;
		featureVO.setFeature_name(feature_name);;

		dao.update(featureVO);
		return this.getOneFeature(feature_id);
	}

	public void deleteFeature(String feature_id) {
		dao.delete(feature_id);
	}

	public FeatureVO getOneFeature(String feature_id) {
		return dao.findByPrimaryKey(feature_id);
	}

	public List<FeatureVO> getAll() {
		return dao.getAll();
	}
}
