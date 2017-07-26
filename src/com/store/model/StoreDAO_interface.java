package com.store.model;

import java.sql.Connection;
import java.util.List;
import java.util.Set;
import com.spndcoffee.model.SpndcoffeeVO;
import com.spndcoffeelist.model.SpndcoffeelistVO;
import com.rate_n_rev.model.Rate_n_revVO;
import com.news.model.NewsVO;
import com.store_tag.model.Store_tagVO;
import com.product.model.ProductVO;
import com.orderlist.model.OrderlistVO;
import com.reply.model.ReplyVO;
import com.activity.model.ActivityVO;
import com.fav_store.model.Fav_storeVO;
import com.member.model.MemberVO;
import com.photo_store.model.Photo_storeVO;
import com.rept_store.model.Rept_storeVO;

public interface StoreDAO_interface {

	public StoreVO insert(StoreVO storeVO);
	public void update(StoreVO storeVO);
	public void delete(String store_id);
	public StoreVO findByPrimaryKey(String store_id) ;
	public List<StoreVO> getAll() ;
	public List<StoreVO> getAllREVIEW() ;
	public StoreVO Login_Store(StoreVO storeVO);
	public void updatePass(String[] store_id, Integer pass);
	public StoreVO findByAccount(String store_acct) ;
	public void Store_AUTHENTICATION(String store_acct, Integer store_authentication);
	public void Store_Forget_Password(String store_acct, String store_pwd);
	
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_id(String store_id);
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByStore_id(String store_id);
	public Set<Rate_n_revVO> getRate_n_revsByStore_id(String store_id);
	public Set<NewsVO> getNewssByStore_id(String store_id);
	public Set<Store_tagVO> getStore_tagsByStore_id(String store_id);
	public Set<ProductVO> getProductsByStore_id(String store_id);
	public Set<OrderlistVO> getOrderlistsByStore_id(String store_id);
	public Set<ReplyVO> getReplysByStore_id(String store_id);
	public Set<ActivityVO> getActivitysByStore_id(String store_id);
	public Set<Fav_storeVO> getFav_storesByStore_id(String store_id);
	public Set<Photo_storeVO> getPhoto_storesByStore_id(String store_id);
	public Set<Rept_storeVO> getRept_storesByStore_id(String store_id);
	
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public Set<StoreVO> getStoresByStore_add_or_name(String store_add_or_name);
	public Set<StoreVO> getStoresByStore_id(String store_id);
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_idDesc(String store_id);
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	
	// new method
	// 訂單完成時撥款
	public void updatePoints(StoreVO storeVO, Connection con);
	public Set<ProductVO> getProductsByStore_idDesc(String store_id);
	public Set<OrderlistVO> getOrderlistsByStore_idDesc(String store_id);
	// 依取貨方式查詢店家訂單
	public Set<OrderlistVO> getOrderlistsByStore_idAndOrd_pickDesc(String store_id, Integer ord_pick);
	// new method
	
	// Adding by Sylvie
	public Set<StoreVO> findStoreByAreaOrName(String store_area_or_name);
	public List<ProductVO> getTakeawayProductsByStore_id(String store_id);
	public Set<ProductVO> getTakeawayProductsByStore_idAndCate_id(String store_id, String cate_id);
	public List<ProductVO> getProductListByStore_idAndCateDESC(String store_id, Integer prod_category);
//	public List<OrderlistVO> getOrderlistListByStore_id(String store_id);
	public List<ProductVO> getProductListByStore_id(String store_id);
	public List<OrderlistVO> getOrderlistListByStore_idandOrd_pickDESC(String store_id, Integer ord_pick, Integer ord_pick2);
}