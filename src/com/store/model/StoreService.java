package com.store.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.activity.model.ActivityVO;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.orderlist.model.OrderlistVO;
import com.product.model.ProductVO;
import com.spndcoffee.model.SpndcoffeeVO;
import com.store_tag.model.Store_tagVO;

public class StoreService {

	private StoreDAO_interface dao;

	public StoreService() {
		dao = new StoreDAO();
	}

	public List<Double> getLatLng(String address) {
		List<Double> location = new ArrayList<Double>();
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
		GeocodeResponse geocodeResponse = geocoder.geocode(geocoderRequest);

		List<GeocoderResult> results = geocodeResponse.getResults();
		if (results != null && results.size() > 0) {
			location.add(Double.parseDouble(results.get(0).getGeometry().getLocation().getLat().toString()));
			location.add(Double.parseDouble(results.get(0).getGeometry().getLocation().getLng().toString()));
		} else {
			location.add(0.0);
			location.add(0.0);
		}
		return location;
	}

	public StoreVO addStore(String store_acct, String store_pwd, String store_name, String store_tel, String store_add,
			String store_email, Integer store_points, String store_cpse, Integer min_order, Integer is_min_order,
			Integer is_wifi, Integer is_plug, Integer is_single_orgn, Integer is_dessert, Integer is_meal,
			Integer is_time_limit, Integer mon_isopen, Timestamp mon_open, Timestamp mon_close, Integer tue_isopen,
			Timestamp tue_open, Timestamp tue_close, Integer wed_isopen, Timestamp wed_open, Timestamp wed_close,
			Integer thu_isopen, Timestamp thu_open, Timestamp thu_close, Integer fri_isopen, Timestamp fri_open,
			Timestamp fri_close, Integer sat_isopen, Timestamp sat_open, Timestamp sat_close, Integer sun_isopen,
			Timestamp sun_open, Timestamp sun_close, byte[] store_img, String store_validateCode) {

		List<Double> location = this.getLatLng(store_add);

		StoreVO storeVO = new StoreVO();
		storeVO.setStore_acct(store_acct);
		storeVO.setStore_pwd(store_pwd);
		storeVO.setStore_name(store_name);
		storeVO.setStore_tel(store_tel);
		storeVO.setStore_add(store_add);
		storeVO.setStore_email(store_email);
		storeVO.setLongitude(location.get(1));
		storeVO.setLatitude(location.get(0));
		storeVO.setStore_points(store_points);
		storeVO.setStore_cpse(store_cpse);
		storeVO.setMin_order(min_order);
		storeVO.setIs_min_order(is_min_order);
		storeVO.setIs_wifi(is_wifi);
		storeVO.setIs_plug(is_plug);
		storeVO.setIs_single_orgn(is_single_orgn);
		storeVO.setIs_dessert(is_dessert);
		storeVO.setIs_plug(is_plug);
		storeVO.setIs_single_orgn(is_single_orgn);
		storeVO.setIs_dessert(is_dessert);
		storeVO.setIs_meal(is_meal);
		storeVO.setIs_time_limit(is_time_limit);
		storeVO.setMon_isopen(mon_isopen);
		storeVO.setMon_open(mon_open);
		storeVO.setMon_close(mon_close);
		storeVO.setTue_isopen(tue_isopen);
		storeVO.setTue_open(tue_open);
		storeVO.setTue_close(tue_close);
		storeVO.setWed_isopen(wed_isopen);
		storeVO.setWed_open(wed_open);
		storeVO.setWed_close(wed_close);
		storeVO.setThu_isopen(thu_isopen);
		storeVO.setThu_open(thu_open);
		storeVO.setThu_close(thu_close);
		storeVO.setFri_isopen(fri_isopen);
		storeVO.setFri_open(fri_open);
		storeVO.setFri_close(fri_close);
		storeVO.setSat_isopen(sat_isopen);
		storeVO.setSat_open(sat_open);
		storeVO.setSat_close(sat_close);
		storeVO.setSun_isopen(sun_isopen);
		storeVO.setSun_open(sun_open);
		storeVO.setSun_close(sun_close);
		storeVO.setStore_img(store_img);
		storeVO.setStore_validateCode(store_validateCode);
		return dao.insert(storeVO);
	}

	public StoreVO updateStore(String store_id, String store_acct, String store_name, String store_tel,
			String store_add, String store_email, String store_cpse, Integer min_order, Integer is_min_order,
			Integer is_wifi, Integer is_plug, Integer is_single_orgn, Integer is_dessert, Integer is_meal,
			Integer is_time_limit, Integer mon_isopen, Timestamp mon_open, Timestamp mon_close, Integer tue_isopen,
			Timestamp tue_open, Timestamp tue_close, Integer wed_isopen, Timestamp wed_open, Timestamp wed_close,
			Integer thu_isopen, Timestamp thu_open, Timestamp thu_close, Integer fri_isopen, Timestamp fri_open,
			Timestamp fri_close, Integer sat_isopen, Timestamp sat_open, Timestamp sat_close, Integer sun_isopen,
			Timestamp sun_open, Timestamp sun_close, byte[] store_img) {

		List<Double> location = this.getLatLng(store_add);

		StoreVO storeVO = this.getOneStore(store_id);
		storeVO.setStore_name(store_name);
		storeVO.setStore_tel(store_tel);
		storeVO.setStore_add(store_add);
		storeVO.setStore_email(store_email);
		storeVO.setLongitude(location.get(1));
		storeVO.setLatitude(location.get(0));
		storeVO.setStore_cpse(store_cpse);
		storeVO.setMin_order(min_order);
		storeVO.setIs_min_order(is_min_order);
		storeVO.setIs_wifi(is_wifi);
		storeVO.setIs_plug(is_plug);
		storeVO.setIs_single_orgn(is_single_orgn);
		storeVO.setIs_dessert(is_dessert);
		storeVO.setIs_plug(is_plug);
		storeVO.setIs_single_orgn(is_single_orgn);
		storeVO.setIs_dessert(is_dessert);
		storeVO.setIs_meal(is_meal);
		storeVO.setIs_time_limit(is_time_limit);
		storeVO.setMon_isopen(mon_isopen);
		storeVO.setMon_open(mon_open);
		storeVO.setMon_close(mon_close);
		storeVO.setTue_isopen(tue_isopen);
		storeVO.setTue_open(tue_open);
		storeVO.setTue_close(tue_close);
		storeVO.setWed_isopen(wed_isopen);
		storeVO.setWed_open(wed_open);
		storeVO.setWed_close(wed_close);
		storeVO.setThu_isopen(thu_isopen);
		storeVO.setThu_open(thu_open);
		storeVO.setThu_close(thu_close);
		storeVO.setFri_isopen(fri_isopen);
		storeVO.setFri_open(fri_open);
		storeVO.setFri_close(fri_close);
		storeVO.setSat_isopen(sat_isopen);
		storeVO.setSat_open(sat_open);
		storeVO.setSat_close(sat_close);
		storeVO.setSun_isopen(sun_isopen);
		storeVO.setSun_open(sun_open);
		storeVO.setSun_close(sun_close);
		storeVO.setStore_id(store_id);
		storeVO.setStore_img(store_img);
		dao.update(storeVO);
		return this.getOneStore(store_id);
	}

	public void deleteStore(String store_id) {
		dao.delete(store_id);
	}

	public StoreVO getOneStore(String store_id) {
		return dao.findByPrimaryKey(store_id);
	}

	public List<StoreVO> getAll() {
		return dao.getAll();
	}

	public List<StoreVO> getAllREVIEW() {
		return dao.getAllREVIEW();
	}

	public StoreVO Login_Store(String store_acct, String store_pwd) {

		StoreVO storeVO = new StoreVO();
		storeVO.setStore_acct(store_acct);
		storeVO.setStore_pwd(store_pwd);
		return dao.Login_Store(storeVO);
	}

	public void updatePass(String[] store_id, Integer pass) {
		dao.updatePass(store_id, pass);
	}

	public StoreVO getOneStoreByAccount(String store_acct) {
		return dao.findByAccount(store_acct);
	}

	public void store_Authentication(String store_acct, Integer store_authentication) {
		StoreVO storeVO = new StoreVO();
		storeVO.setStore_acct(store_acct);
		storeVO.setStore_authentication(store_authentication);

		dao.Store_AUTHENTICATION(store_acct, store_authentication);
	}

	public void store_Forget_Password(String store_acct, String store_pwd) {
		StoreVO storeVO = new StoreVO();
		storeVO.setStore_acct(store_acct);
		storeVO.setStore_pwd(store_pwd);

		dao.Store_Forget_Password(store_acct, store_pwd);
	}

	// 祈竣 祈竣 祈竣
	public Set<ActivityVO> getActivitysByStore_id(String store_id) {
		return dao.getActivitysByStore_id(store_id);
	}
	// 祈竣 祈竣 祈竣

	public Set<StoreVO> getStoresByStore_add_or_name(String store_add_or_name) {
		return dao.getStoresByStore_add_or_name(store_add_or_name);
	}

	public Set<StoreVO> getStoresByStore_id(String store_id) {
		return dao.getStoresByStore_id(store_id);
	}

	public Set<Store_tagVO> getStore_tagsByStore_id(String store_id) {
		return dao.getStore_tagsByStore_id(store_id);
	}
	
	// 安琪
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_idDesc(String store_id) {
		return dao.getSpndcoffeesByStore_idDesc(store_id);
	}
	// 安琪


	// new method
	public Set<ProductVO> getProductsByStore_idDesc(String store_id){
		return dao.getProductsByStore_idDesc(store_id);
	}
	
	// new method
	public Set<OrderlistVO> getOrderlistsByStore_idDesc(String store_id){
		return dao.getOrderlistsByStore_idDesc(store_id);
	}
	
	// new method
	// 依取貨方式查詢店家訂單
	public Set<OrderlistVO> getOrderlistsByStore_idAndOrd_pickDesc(String store_id, Integer ord_pick){
		return dao.getOrderlistsByStore_idAndOrd_pickDesc(store_id, ord_pick);
	}
	
	// Adding by Sylvie
    public StoreVO findByPrimaryKey(String store_id) {
        return dao.findByPrimaryKey(store_id);
    }

    // Adding by Sylvie
    public Set<StoreVO> findStoreByAreaOrName(String store_area_or_name) {
    	return dao.findStoreByAreaOrName(store_area_or_name);
    }
    
    // Adding by Sylvie (de)
	public Set<ProductVO> getProductsByStore_id(String store_id) {
		return dao.getProductsByStore_id(store_id);
	}
	
	public List<ProductVO> getProductListByStore_id(String store_id) {
		return dao.getProductListByStore_id(store_id);
	}
	
	// Adding by Sylvie
	public List<ProductVO> getProductListByStore_idAndCateDESC(String store_id, Integer prod_category) {
		return dao.getProductListByStore_idAndCateDESC(store_id, prod_category);
	}
	
	// Adding by Sylvie
	public List<ProductVO> getTakeawayProductsByStore_id(String store_id) {
		return dao.getTakeawayProductsByStore_id(store_id);
	}

	public Set<OrderlistVO> getOrderlistsByStore_id(String store_id) {
		return dao.getOrderlistsByStore_id(store_id);
	}
	
	// Adding by Sylvie
	public Set<ProductVO> getTakeawayProductsByStore_idAndCate_id(String store_id, String cate_id) {
		return dao.getTakeawayProductsByStore_idAndCate_id(store_id, cate_id);
	}
	
	// Adding by Sylvie
//	public List<OrderlistVO> getOrderlistListByStore_id(String store_id) {
//		return dao.getOrderlistListByStore_id(store_id);
//	};
	
	// Adding by Sylvie
	public List<OrderlistVO> getOrderlistListByStore_idandOrd_pickDESC(String store_id, Integer ord_pick, Integer ord_pick2) {
		return dao.getOrderlistListByStore_idandOrd_pickDESC(store_id, ord_pick, ord_pick2);
	};
	
}
