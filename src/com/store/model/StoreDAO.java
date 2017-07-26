package com.store.model;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.activity.model.ActivityVO;
import com.fav_store.model.Fav_storeVO;
import com.news.model.NewsVO;
import com.orderlist.model.OrderlistVO;
import com.photo_store.model.Photo_storeVO;
import com.product.model.ProductVO;
import com.rate_n_rev.model.Rate_n_revVO;
import com.reply.model.ReplyVO;
import com.rept_store.model.Rept_storeVO;
import com.spndcoffee.model.SpndcoffeeVO;
import com.spndcoffeelist.model.SpndcoffeelistVO;
import com.store_tag.model.Store_tagVO;

public class StoreDAO implements StoreDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO STORE (STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE) VALUES ('STORE' || LPAD(to_char(STORE_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE  FROM STORE WHERE STORE_PASS=1 ORDER BY STORE_ID DESC";
	private static final String GET_REVIEW_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE  FROM STORE WHERE STORE_PASS IS NULL AND STORE_ACCT IS NOT NULL ORDER BY STORE_ID DESC";
	private static final String GET_ONE_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE  FROM STORE WHERE STORE_ID = ?";
	private static final String DELETE_STORE = "DELETE FROM STORE WHERE STORE_ID = ?";
	private static final String UPDATE = "UPDATE STORE SET STORE_ACCT=?, STORE_PWD=?, STORE_NAME=?, STORE_TEL=?, STORE_ADD=?, STORE_EMAIL=?, LONGITUDE=?, LATITUDE=?, STORE_POINTS=?, STORE_CPSE=?, MIN_ORDER=?, IS_MIN_ORDER=?, IS_WIFI=?, IS_PLUG=?, IS_SINGLE_ORGN=?, IS_DESSERT=?, IS_MEAL=?, IS_TIME_LIMIT=?, MON_ISOPEN=?, MON_OPEN=?, MON_CLOSE=?, TUE_ISOPEN=?, TUE_OPEN=?, TUE_CLOSE=?, WED_ISOPEN=?, WED_OPEN=?, WED_CLOSE=?, THU_ISOPEN=?, THU_OPEN=?, THU_CLOSE=?, FRI_ISOPEN=?, FRI_OPEN=?, FRI_CLOSE=?, SAT_ISOPEN=?, SAT_OPEN=?, SAT_CLOSE=?, SUN_ISOPEN=?, SUN_OPEN=?, SUN_CLOSE=?,STORE_IMG=?,STORE_PASS=?,STORE_AUTHENTICATION=?, STORE_VALIDATECODE=? WHERE STORE_ID = ?";
	private static final String Login_Store = "SELECT * FROM STORE WHERE STORE_ACCT= ? AND STORE_PWD = ?";
	private static final String updatePass = "UPDATE STORE SET STORE_PASS = ? WHERE STORE_ID = ?";
	private static final String GET_ONE_BY_ACCOUNT_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE FROM STORE WHERE STORE_ACCT = ?";
	private static final String STORE_AUTHENTICATION = "UPDATE STORE SET STORE_AUTHENTICATION=? WHERE STORE_ACCT = ?";
	private static final String STORE_FORGET_PASSWORD="UPDATE STORE SET STORE_PWD=? WHERE STORE_ACCT=?";
	
	private static final String GET_Spndcoffees_ByStore_id_STMT = "SELECT SPND_ID,STORE_ID,SPND_NAME,SPND_PROD,to_char(SPND_ENDDATE,'yyyy-mm-dd') SPND_ENDDATE,SPND_AMT,SPND_IMG FROM SPNDCOFFEE WHERE STORE_ID = ? ORDER BY SPND_ID";
	private static final String GET_Spndcoffeelists_ByStore_id_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE STORE_ID = ? ORDER BY LIST_ID";
	private static final String GET_Rate_n_revs_ByStore_id_STMT = "SELECT RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE FROM RATE_N_REV WHERE STORE_ID = ? ORDER BY RNR_ID";
	private static final String GET_Newss_ByStore_id_STMT = "SELECT NEWS_ID,STORE_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_IMG,NEWS_DATE,NEWS_CLASS,NEWS_TOP,NEWS_PASS FROM NEWS WHERE STORE_ID = ? ORDER BY NEWS_ID";
	private static final String GET_Store_tags_ByStore_id_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG WHERE STORE_ID = ? ORDER BY STORE_ID,TAG_ID";
	private static final String GET_Products_ByStore_id_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE STORE_ID = ? ORDER BY PROD_ID";
	private static final String GET_Orderlists_ByStore_id_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE STORE_ID = ? ORDER BY ORD_ID";
	private static final String GET_Replys_ByStore_id_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY WHERE STORE_ID = ? ORDER BY REPLY_ID";
	private static final String GET_Activitys_ByStore_id_STMT = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM FROM ACTIVITY WHERE STORE_ID = ? ORDER BY ACTIV_ID";
	private static final String GET_Fav_stores_ByStore_id_STMT = "SELECT MEM_ID,STORE_ID FROM FAV_STORE WHERE STORE_ID = ? ORDER BY MEM_ID,STORE_ID";
	private static final String GET_Photo_stores_ByStore_id_STMT = "SELECT PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC FROM PHOTO_STORE WHERE STORE_ID = ? ORDER BY PHOTO_ID";
	private static final String GET_Rept_stores_ByStore_id_STMT = "SELECT STORE_ID,MEM_ID,REPT_RSN,REPT_REV FROM REPT_STORE WHERE STORE_ID = ? ORDER BY STORE_ID,MEM_ID";

	private static final String DELETE_SPNDCOFFEEs = "DELETE FROM SPNDCOFFEE WHERE STORE_ID = ?";
	private static final String DELETE_SPNDCOFFEELISTs = "DELETE FROM SPNDCOFFEELIST WHERE STORE_ID = ?";
	private static final String DELETE_RATE_N_REVs = "DELETE FROM RATE_N_REV WHERE STORE_ID = ?";
	private static final String DELETE_NEWSs = "DELETE FROM NEWS WHERE STORE_ID = ?";
	private static final String DELETE_STORE_TAGs = "DELETE FROM STORE_TAG WHERE STORE_ID = ?";
	private static final String DELETE_PRODUCTs = "DELETE FROM PRODUCT WHERE STORE_ID = ?";
	private static final String DELETE_ORDERLISTs = "DELETE FROM ORDERLIST WHERE STORE_ID = ?";
	private static final String DELETE_REPLYs = "DELETE FROM REPLY WHERE STORE_ID = ?";
	private static final String DELETE_ACTIVITYs = "DELETE FROM ACTIVITY WHERE STORE_ID = ?";
	private static final String DELETE_FAV_STOREs = "DELETE FROM FAV_STORE WHERE STORE_ID = ?";
	private static final String DELETE_PHOTO_STOREs = "DELETE FROM PHOTO_STORE WHERE STORE_ID = ?";
	private static final String DELETE_REPT_STOREs = "DELETE FROM REPT_STORE WHERE STORE_ID = ?";
	
	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	private static final String GET_STORES_BYSTOREADD_STMT = "SELECT * FROM STORE WHERE UPPER(STORE_ADD) LIKE ? OR UPPER(STORE_NAME) LIKE ?";
	private static final String GET_STORES_BYSTORE_ID_STMT = "SELECT * FROM STORE WHERE STORE_id = ?";
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	private static final String GET_Spndcoffees_ByStore_id_DESC_STMT = "SELECT SPND_ID,STORE_ID,SPND_NAME,SPND_PROD,to_char(SPND_ENDDATE,'yyyy-mm-dd') SPND_ENDDATE,SPND_AMT,SPND_IMG FROM SPNDCOFFEE WHERE STORE_ID = ? ORDER BY SPND_ID DESC";
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪

	// new STMT
	// 訂單完成時撥款
	private static final String UPDATE_POINTS = "UPDATE STORE SET STORE_POINTS=? WHERE STORE_ID = ?";
	private static final String GET_Products_ByStore_id_DESC_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE STORE_ID = ? ORDER BY PROD_ID DESC";
	private static final String GET_Orderlists_ByStore_id_DESC_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE STORE_ID = ? ORDER BY ORD_ID DESC";
	// 依取貨方式查詢店家訂單
	private static final String GET_Orderlists_ByStore_id_And_Ord_pick_DESC_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE STORE_ID = ? AND ORD_PICK = ? ORDER BY ORD_ID DESC";
	// new STMT

	/* Start : Adding by Sylvie */
	private static final String GET_Store_ByAreaOrName = "select * from store where upper(store_add) like ? or upper(store_name) like ? ORDER BY STORE_ID DESC";	
	private static final String GET_TakeawayProducts_ByStore_id_STMT = "SELECT * FROM PRODUCT WHERE STORE_ID = ? and prod_category=2 and prod_launch=1 ORDER BY PROD_ID";
	private static final String GET_TakeawayProducts_ByStore_id_and_Cate_id_STMT = "SELECT * FROM PRODUCT WHERE STORE_ID = ? and cate_id=? and prod_category=2 and prod_launch=1 ORDER BY PROD_ID";
//	private static final String GET_TakeawayOrderlistList_ByStore_id_STMT = "SELECT * FROM ORDERLIST WHERE STORE_ID = ? and not ord_pick = 1 ORDER BY ORD_ID";
	private static final String GET_Products_ByStore_id_and_category_DESC_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE STORE_ID = ? and prod_category = ? ORDER BY PROD_ID DESC";
	private static final String GET_orderlistList_ByStore_id_and_Ord_pick_DESC_STMT = "SELECT * FROM ORDERLIST WHERE STORE_ID = ? and ord_pick in(?,?) ORDER BY ORD_ID DESC";

	/*  1st : Adding by Sylvie */	
	@Override
	public Set<StoreVO> findStoreByAreaOrName(String store_area_or_name) {
		Set<StoreVO> storeSet = new LinkedHashSet<StoreVO>();
		
		StoreVO storeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Store_ByAreaOrName);

			pstmt.setString(1, "%" + store_area_or_name + "%");
			pstmt.setString(2, "%" + store_area_or_name + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getInt("store_pass"));
				storeVO.setStore_authentication(rs.getInt("store_authentication")); //Adding
				storeVO.setStore_validateCode(rs.getString("store_validatecode")); //Adding
				
				storeSet.add(storeVO);
				
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return storeSet;
	}
	
	/* 2nd : Adding by Sylvie */
	@Override
	public List<ProductVO> getTakeawayProductsByStore_id(String store_id){
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TakeawayProducts_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				productVO.setProd_desc(readerToString(rs.getCharacterStream("prod_desc"))); //Adding
				list.add(productVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	/* 3rd : Adding by Sylvie */
	@Override
	public Set<ProductVO> getTakeawayProductsByStore_idAndCate_id(String store_id, String cate_id){
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TakeawayProducts_ByStore_id_and_Cate_id_STMT);
			pstmt.setString(1, store_id);
			pstmt.setString(2, cate_id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				productVO.setProd_desc(readerToString(rs.getCharacterStream("prod_desc"))); //Adding
				set.add(productVO); // Store the row in the vector
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	
//	/* 4th : Adding by Sylvie */
//	@Override
//	public List<OrderlistVO> getOrderlistListByStore_id(String store_id){
//		List<OrderlistVO> set = new ArrayList<OrderlistVO>();
//		OrderlistVO orderlistVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_TakeawayOrderlistList_ByStore_id_STMT);
//			pstmt.setString(1, store_id);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				orderlistVO = new OrderlistVO();
//				orderlistVO.setOrd_id(rs.getString("ord_id"));
//				orderlistVO.setMem_id(rs.getString("mem_id"));
//				orderlistVO.setStore_id(rs.getString("store_id"));
//				orderlistVO.setOrd_total(rs.getInt("ord_total"));
//				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
//				orderlistVO.setOrd_add(rs.getString("ord_add"));
//				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
//				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
//				orderlistVO.setScore_seller(rs.getInt("score_seller"));
//
//				set.add(orderlistVO); // Store the row in the vector
//			}
//
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return set;
//	}
	/* 4th : Adding by Sylvie */
	@Override
	public List<OrderlistVO> getOrderlistListByStore_idandOrd_pickDESC(String store_id, Integer ord_pick, Integer ord_pick2){
		List<OrderlistVO> set = new ArrayList<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_orderlistList_ByStore_id_and_Ord_pick_DESC_STMT);
			pstmt.setString(1, store_id);
			pstmt.setInt(2, ord_pick);
			pstmt.setInt(3, ord_pick2);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				
				set.add(orderlistVO); // Store the row in the vector
			}
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	
	// Adding by sylvie(4 version exists)
	@Override
	public List<ProductVO> getProductListByStore_id(String store_id) {
		List<ProductVO> set = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Products_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				productVO.setProd_desc(readerToString(rs.getCharacterStream("prod_desc"))); //Adding
				set.add(productVO); // Store the row in the vector
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	// Adding by sylvie(4 version exists)
	@Override
	public List<ProductVO> getProductListByStore_idAndCateDESC(String store_id, Integer prod_category) {
		List<ProductVO> set = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_Products_ByStore_id_STMT);
			pstmt = con.prepareStatement(GET_Products_ByStore_id_and_category_DESC_STMT);
			pstmt.setString(1, store_id);
			pstmt.setInt(2, prod_category);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				productVO.setProd_desc(readerToString(rs.getCharacterStream("prod_desc"))); //Adding
				set.add(productVO); // Store the row in the vector
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	
	// new method
	// 訂單完成時撥款
	@Override
	public void updatePoints(StoreVO storeVO, Connection con) {
		
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(UPDATE_POINTS);

			pstmt.setInt(1, storeVO.getStore_points());
			pstmt.setString(2, storeVO.getStore_id());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.println("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}
	// new method

	// new method
	@Override
	public Set<ProductVO> getProductsByStore_idDesc(String store_id){
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Products_ByStore_id_DESC_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				productVO.setProd_desc(readerToString(rs.getCharacterStream("prod_desc")));
				set.add(productVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	// new method

	// new method
	@Override
	public Set<OrderlistVO> getOrderlistsByStore_idDesc(String store_id){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByStore_id_DESC_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				set.add(orderlistVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	// new method
	
	// new method
	// 依取貨方式查詢店家訂單
	@Override
	public Set<OrderlistVO> getOrderlistsByStore_idAndOrd_pickDesc(String store_id, Integer ord_pick){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByStore_id_And_Ord_pick_DESC_STMT);
			pstmt.setString(1, store_id);
			pstmt.setInt(2, ord_pick);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				set.add(orderlistVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	// new method

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	@Override
	public Set<StoreVO> getStoresByStore_add_or_name(String store_add_or_name) {

		Set<StoreVO> storeSet = new LinkedHashSet<StoreVO>();
		StoreVO storeVO =  null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORES_BYSTOREADD_STMT);
			
			pstmt.setString(1, "%"+store_add_or_name+"%");
			pstmt.setString(2, "%"+store_add_or_name+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getInt("store_pass"));
				storeSet.add(storeVO);
				
			}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return storeSet ;

	}

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	@Override
	public Set<StoreVO> getStoresByStore_id(String store_id) {

		Set<StoreVO> storeSet = new LinkedHashSet<StoreVO>();
		StoreVO storeVO =  null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORES_BYSTORE_ID_STMT);
			
			pstmt.setString(1, store_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getInt("store_pass"));
				storeSet.add(storeVO);
				
			}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}			
		return storeSet ;
	}
	
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	@Override
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_idDesc(String store_id){
		Set<SpndcoffeeVO> set = new LinkedHashSet<SpndcoffeeVO>();
		SpndcoffeeVO spndcoffeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Spndcoffees_ByStore_id_DESC_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeeVO = new SpndcoffeeVO();
				spndcoffeeVO.setSpnd_id(rs.getString("spnd_id"));
				spndcoffeeVO.setStore_id(rs.getString("store_id"));
				spndcoffeeVO.setSpnd_name(rs.getString("spnd_name"));
				spndcoffeeVO.setSpnd_prod(rs.getString("spnd_prod"));
				spndcoffeeVO.setSpnd_enddate(rs.getDate("spnd_enddate"));
				spndcoffeeVO.setSpnd_amt(rs.getInt("spnd_amt"));
				spndcoffeeVO.setSpnd_img(rs.getBytes("spnd_img"));
				set.add(spndcoffeeVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	
	@Override
	public StoreVO insert(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT, new String[] { "STORE_ID" });

			pstmt.setString(1, storeVO.getStore_acct());
			pstmt.setString(2, storeVO.getStore_pwd());
			pstmt.setString(3, storeVO.getStore_name());
			pstmt.setString(4, storeVO.getStore_tel());
			pstmt.setString(5, storeVO.getStore_add());
			pstmt.setString(6, storeVO.getStore_email());
			pstmt.setDouble(7, storeVO.getLongitude());
			pstmt.setDouble(8, storeVO.getLatitude());
			pstmt.setInt(9, storeVO.getStore_points());
			pstmt.setString(10, storeVO.getStore_cpse());
			pstmt.setInt(11, storeVO.getMin_order());
			pstmt.setInt(12, storeVO.getIs_min_order());
			pstmt.setInt(13, storeVO.getIs_wifi());
			pstmt.setInt(14, storeVO.getIs_plug());
			pstmt.setInt(15, storeVO.getIs_single_orgn());
			pstmt.setInt(16, storeVO.getIs_dessert());
			pstmt.setInt(17, storeVO.getIs_meal());
			pstmt.setInt(18, storeVO.getIs_time_limit());
			pstmt.setInt(19, storeVO.getMon_isopen());
			pstmt.setTimestamp(20, storeVO.getMon_open());
			pstmt.setTimestamp(21, storeVO.getMon_close());
			pstmt.setInt(22, storeVO.getTue_isopen());
			pstmt.setTimestamp(23, storeVO.getTue_open());
			pstmt.setTimestamp(24, storeVO.getTue_close());
			pstmt.setInt(25, storeVO.getWed_isopen());
			pstmt.setTimestamp(26, storeVO.getWed_open());
			pstmt.setTimestamp(27, storeVO.getWed_close());
			pstmt.setInt(28, storeVO.getThu_isopen());
			pstmt.setTimestamp(29, storeVO.getThu_open());
			pstmt.setTimestamp(30, storeVO.getThu_close());
			pstmt.setInt(31, storeVO.getFri_isopen());
			pstmt.setTimestamp(32, storeVO.getFri_open());
			pstmt.setTimestamp(33, storeVO.getFri_close());
			pstmt.setInt(34, storeVO.getSat_isopen());
			pstmt.setTimestamp(35, storeVO.getSat_open());
			pstmt.setTimestamp(36, storeVO.getSat_close());
			pstmt.setInt(37, storeVO.getSun_isopen());
			pstmt.setTimestamp(38, storeVO.getSun_open());
			pstmt.setTimestamp(39, storeVO.getSun_close());

			if (storeVO.getStore_img() == null) {
				pstmt.setBinaryStream(40, this.getClass().getResourceAsStream("/store01.jpg"));
			} else {
				pstmt.setBytes(40, storeVO.getStore_img());
			}
			pstmt.setObject(41, storeVO.getStore_pass());
			
			pstmt.setObject(42, storeVO.getStore_authentication());
			pstmt.setString(43, storeVO.getStore_validateCode());
			
			

			int rows = pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			if (rows > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					String store_id = rs.getString(1);
					return this.findByPrimaryKey(store_id);
				}
			}
			return null;
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, storeVO.getStore_acct());
			pstmt.setString(2, storeVO.getStore_pwd());
			pstmt.setString(3, storeVO.getStore_name());
			pstmt.setString(4, storeVO.getStore_tel());
			pstmt.setString(5, storeVO.getStore_add());
			pstmt.setString(6, storeVO.getStore_email());
			pstmt.setDouble(7, storeVO.getLongitude());
			pstmt.setDouble(8, storeVO.getLatitude());
			pstmt.setInt(9, storeVO.getStore_points());
			pstmt.setString(10, storeVO.getStore_cpse());
			pstmt.setInt(11, storeVO.getMin_order());
			pstmt.setInt(12, storeVO.getIs_min_order());
			pstmt.setInt(13, storeVO.getIs_wifi());
			pstmt.setInt(14, storeVO.getIs_plug());
			pstmt.setInt(15, storeVO.getIs_single_orgn());
			pstmt.setInt(16, storeVO.getIs_dessert());
			pstmt.setInt(17, storeVO.getIs_meal());
			pstmt.setInt(18, storeVO.getIs_time_limit());
			pstmt.setInt(19, storeVO.getMon_isopen());
			pstmt.setTimestamp(20, storeVO.getMon_open());
			pstmt.setTimestamp(21, storeVO.getMon_close());
			pstmt.setInt(22, storeVO.getTue_isopen());
			pstmt.setTimestamp(23, storeVO.getTue_open());
			pstmt.setTimestamp(24, storeVO.getTue_close());
			pstmt.setInt(25, storeVO.getWed_isopen());
			pstmt.setTimestamp(26, storeVO.getWed_open());
			pstmt.setTimestamp(27, storeVO.getWed_close());
			pstmt.setInt(28, storeVO.getThu_isopen());
			pstmt.setTimestamp(29, storeVO.getThu_open());
			pstmt.setTimestamp(30, storeVO.getThu_close());
			pstmt.setInt(31, storeVO.getFri_isopen());
			pstmt.setTimestamp(32, storeVO.getFri_open());
			pstmt.setTimestamp(33, storeVO.getFri_close());
			pstmt.setInt(34, storeVO.getSat_isopen());
			pstmt.setTimestamp(35, storeVO.getSat_open());
			pstmt.setTimestamp(36, storeVO.getSat_close());
			pstmt.setInt(37, storeVO.getSun_isopen());
			pstmt.setTimestamp(38, storeVO.getSun_open());
			pstmt.setTimestamp(39, storeVO.getSun_close());

			if (storeVO.getStore_img() == null) {
				pstmt.setBinaryStream(40, this.getClass().getResourceAsStream("/store01.jpg"));
			} else {
				pstmt.setBytes(40, storeVO.getStore_img());
			}
			if (storeVO.getStore_pass() == null) {
				pstmt.setObject(41, storeVO.getStore_pass());
			} else {
				pstmt.setInt(41, storeVO.getStore_pass());
			}
			
			if (storeVO.getStore_authentication() == null) {
				pstmt.setObject(42, storeVO.getStore_authentication());
			} else {
				pstmt.setInt(42, storeVO.getStore_authentication());
			}
	
			pstmt.setString(43, storeVO.getStore_validateCode());
			
			pstmt.setString(44, storeVO.getStore_id());

			int rows = pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String store_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEEs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEELISTs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_RATE_N_REVs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_NEWSs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_STORE_TAGs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PRODUCTs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDERLISTs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPLYs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ACTIVITYs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_FAV_STOREs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PHOTO_STOREs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_STOREs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_STORE);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public StoreVO findByPrimaryKey(String store_id) {
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, store_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));

				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				if (rs.getInt("mon_isopen") != 0) {
					storeVO.setMon_open(rs.getTimestamp("mon_open"));
					storeVO.setMon_close(rs.getTimestamp("mon_close"));
				} else {
					storeVO.setMon_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setMon_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				if (rs.getInt("tue_isopen") != 0) {
					storeVO.setTue_open(rs.getTimestamp("tue_open"));
					storeVO.setTue_close(rs.getTimestamp("tue_close"));
				} else {
					storeVO.setTue_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setTue_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				if (rs.getInt("wed_isopen") != 0) {
					storeVO.setWed_open(rs.getTimestamp("wed_open"));
					storeVO.setWed_close(rs.getTimestamp("wed_close"));
				} else {
					storeVO.setWed_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setWed_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				if (rs.getInt("thu_isopen") != 0) {
					storeVO.setThu_open(rs.getTimestamp("thu_open"));
					storeVO.setThu_close(rs.getTimestamp("thu_close"));
				} else {
					storeVO.setThu_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setThu_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				if (rs.getInt("fri_isopen") != 0) {
					storeVO.setFri_open(rs.getTimestamp("fri_open"));
					storeVO.setFri_close(rs.getTimestamp("fri_close"));
				} else {
					storeVO.setFri_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setFri_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				if (rs.getInt("sat_isopen") != 0) {
					storeVO.setSat_open(rs.getTimestamp("sat_open"));
					storeVO.setSat_close(rs.getTimestamp("sat_close"));
				} else {
					storeVO.setSat_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setSat_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				if (rs.getInt("sun_isopen") != 0) {
					storeVO.setSun_open(rs.getTimestamp("sun_open"));
					storeVO.setSun_close(rs.getTimestamp("sun_close"));
				} else {
					storeVO.setSun_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setSun_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getObject("store_pass") != null ? rs.getInt("store_pass") : null);
				storeVO.setStore_authentication(rs.getObject("store_authentication")!=null ? rs.getInt("store_authentication") : null);
				storeVO.setStore_validateCode(rs.getString("store_validateCode"));
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll() {
		List<StoreVO> list = new ArrayList<StoreVO>();
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getObject("store_pass") != null ? rs.getInt("store_pass") : null);
				storeVO.setStore_authentication(rs.getObject("store_authentication")!= null ? rs.getInt("store_authentication") : null);
				list.add(storeVO); // Store the row in the list
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<StoreVO> getAllREVIEW() {
		List<StoreVO> list = new ArrayList<StoreVO>();
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REVIEW_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getObject("store_pass") != null ? rs.getInt("store_pass") : null);
				storeVO.setStore_authentication(rs.getObject("store_authentication")!= null ? rs.getInt("store_authentication") : null);
				list.add(storeVO); // Store the row in the list
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}


	@Override
	public StoreVO Login_Store(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StoreVO storeVO_login = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(Login_Store);
			pstmt.setString(1, storeVO.getStore_acct());
			pstmt.setString(2, storeVO.getStore_pwd());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO_login = new StoreVO();
				storeVO_login.setStore_id(rs.getString("store_id"));
				storeVO_login.setStore_acct(rs.getString("store_acct"));
				storeVO_login.setStore_pwd(rs.getString("store_pwd"));
				storeVO_login.setStore_name(rs.getString("store_name"));
				storeVO_login.setStore_tel(rs.getString("store_tel"));
				storeVO_login.setStore_add(rs.getString("store_add"));
				storeVO_login.setStore_email(rs.getString("store_email"));
				storeVO_login.setLongitude(rs.getDouble("longitude"));
				storeVO_login.setLatitude(rs.getDouble("latitude"));
				storeVO_login.setStore_points(rs.getInt("store_points"));
				storeVO_login.setStore_cpse(rs.getString("store_cpse"));
				storeVO_login.setMin_order(rs.getInt("min_order"));
				storeVO_login.setIs_min_order(rs.getInt("is_min_order"));
				storeVO_login.setIs_wifi(rs.getInt("is_wifi"));
				storeVO_login.setIs_plug(rs.getInt("is_plug"));
				storeVO_login.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO_login.setIs_dessert(rs.getInt("is_dessert"));
				storeVO_login.setIs_meal(rs.getInt("is_meal"));
				storeVO_login.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO_login.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO_login.setMon_open(rs.getTimestamp("mon_open"));
				storeVO_login.setMon_close(rs.getTimestamp("mon_close"));
				storeVO_login.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO_login.setTue_open(rs.getTimestamp("tue_open"));
				storeVO_login.setTue_close(rs.getTimestamp("tue_close"));
				storeVO_login.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO_login.setWed_open(rs.getTimestamp("wed_open"));
				storeVO_login.setWed_close(rs.getTimestamp("wed_close"));
				storeVO_login.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO_login.setThu_open(rs.getTimestamp("thu_open"));
				storeVO_login.setThu_close(rs.getTimestamp("thu_close"));
				storeVO_login.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO_login.setFri_open(rs.getTimestamp("fri_open"));
				storeVO_login.setFri_close(rs.getTimestamp("fri_close"));
				storeVO_login.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO_login.setSat_open(rs.getTimestamp("sat_open"));
				storeVO_login.setSat_close(rs.getTimestamp("sat_close"));
				storeVO_login.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO_login.setSun_open(rs.getTimestamp("sun_open"));
				storeVO_login.setSun_close(rs.getTimestamp("sun_close"));
				storeVO_login.setStore_img(rs.getBytes("store_img"));
				storeVO_login.setStore_pass(rs.getObject("store_pass") != null ? rs.getInt("store_pass") : null);
				storeVO_login.setStore_authentication(rs.getObject("store_authentication") != null ? rs.getInt("store_authentication") : null);
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return storeVO_login;

	}

	@Override
	public void updatePass(String[] store_id, Integer pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(updatePass);
			for (String id : store_id) {
				pstmt.setInt(1, pass);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public StoreVO findByAccount(String store_acct) {
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ACCOUNT_STMT);
			pstmt.setString(1, store_acct);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));

				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				if (rs.getInt("mon_isopen") != 0) {
					storeVO.setMon_open(rs.getTimestamp("mon_open"));
					storeVO.setMon_close(rs.getTimestamp("mon_close"));
				} else {
					storeVO.setMon_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setMon_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				if (rs.getInt("tue_isopen") != 0) {
					storeVO.setTue_open(rs.getTimestamp("tue_open"));
					storeVO.setTue_close(rs.getTimestamp("tue_close"));
				} else {
					storeVO.setTue_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setTue_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				if (rs.getInt("wed_isopen") != 0) {
					storeVO.setWed_open(rs.getTimestamp("wed_open"));
					storeVO.setWed_close(rs.getTimestamp("wed_close"));
				} else {
					storeVO.setWed_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setWed_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				if (rs.getInt("thu_isopen") != 0) {
					storeVO.setThu_open(rs.getTimestamp("thu_open"));
					storeVO.setThu_close(rs.getTimestamp("thu_close"));
				} else {
					storeVO.setThu_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setThu_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				if (rs.getInt("fri_isopen") != 0) {
					storeVO.setFri_open(rs.getTimestamp("fri_open"));
					storeVO.setFri_close(rs.getTimestamp("fri_close"));
				} else {
					storeVO.setFri_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setFri_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				if (rs.getInt("sat_isopen") != 0) {
					storeVO.setSat_open(rs.getTimestamp("sat_open"));
					storeVO.setSat_close(rs.getTimestamp("sat_close"));
				} else {
					storeVO.setSat_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setSat_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				if (rs.getInt("sun_isopen") != 0) {
					storeVO.setSun_open(rs.getTimestamp("sun_open"));
					storeVO.setSun_close(rs.getTimestamp("sun_close"));
				} else {
					storeVO.setSun_open(Timestamp.valueOf("2017-06-27 00:00:00"));
					storeVO.setSun_close(Timestamp.valueOf("2017-06-27 00:00:00"));
				}

				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getObject("store_pass") != null ? rs.getInt("store_pass") : null);
				storeVO.setStore_authentication(rs.getObject("store_authentication") != null ? rs.getInt("store_authentication") : null);
				storeVO.setStore_validateCode(rs.getString("store_validateCode"));
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return storeVO;
	}

	@Override
	public void Store_AUTHENTICATION(String store_acct, Integer store_authentication) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(STORE_AUTHENTICATION);
			pstmt.setInt(1, store_authentication);
			pstmt.setString(2, store_acct);
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void Store_Forget_Password(String store_acct, String store_pwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(STORE_FORGET_PASSWORD);
			pstmt.setString(1, store_pwd);
			pstmt.setString(2, store_acct);
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}		
	}
	
	@Override
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_id(String store_id){
		Set<SpndcoffeeVO> set = new LinkedHashSet<SpndcoffeeVO>();
		SpndcoffeeVO spndcoffeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Spndcoffees_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeeVO = new SpndcoffeeVO();
				spndcoffeeVO.setSpnd_id(rs.getString("spnd_id"));
				spndcoffeeVO.setStore_id(rs.getString("store_id"));
				spndcoffeeVO.setSpnd_name(rs.getString("spnd_name"));
				spndcoffeeVO.setSpnd_prod(rs.getString("spnd_prod"));
				spndcoffeeVO.setSpnd_enddate(rs.getDate("spnd_enddate"));
				spndcoffeeVO.setSpnd_amt(rs.getInt("spnd_amt"));
				spndcoffeeVO.setSpnd_img(rs.getBytes("spnd_img"));
				set.add(spndcoffeeVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByStore_id(String store_id){
		Set<SpndcoffeelistVO> set = new LinkedHashSet<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Spndcoffeelists_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeelistVO = new SpndcoffeelistVO();
				spndcoffeelistVO.setList_id(rs.getString("list_id"));
				spndcoffeelistVO.setSpnd_id(rs.getString("spnd_id"));
				spndcoffeelistVO.setMem_id(rs.getString("mem_id"));
				spndcoffeelistVO.setSpnd_prod(rs.getString("spnd_prod"));
				spndcoffeelistVO.setStore_id(rs.getString("store_id"));
				spndcoffeelistVO.setList_amt(rs.getInt("list_amt"));
				spndcoffeelistVO.setList_left(rs.getInt("list_left"));
				spndcoffeelistVO.setList_date(rs.getTimestamp("list_date"));
				set.add(spndcoffeelistVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Rate_n_revVO> getRate_n_revsByStore_id(String store_id){
		Set<Rate_n_revVO> set = new LinkedHashSet<Rate_n_revVO>();
		Rate_n_revVO rate_n_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rate_n_revs_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rate_n_revVO = new Rate_n_revVO();
				rate_n_revVO.setRnr_id(rs.getString("rnr_id"));
				rate_n_revVO.setMem_id(rs.getString("mem_id"));
				rate_n_revVO.setStore_id(rs.getString("store_id"));
				rate_n_revVO.setRnr_rate(rs.getInt("rnr_rate"));
				rate_n_revVO.setRnr_rev(readerToString(rs.getCharacterStream("rnr_rev")));
				rate_n_revVO.setRnr_date(rs.getTimestamp("rnr_date"));
				set.add(rate_n_revVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<NewsVO> getNewssByStore_id(String store_id){
		Set<NewsVO> set = new LinkedHashSet<NewsVO>();
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Newss_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setStore_id(rs.getString("store_id"));
				newsVO.setNews_title(rs.getString("news_title"));
				newsVO.setNews_content(readerToString(rs.getCharacterStream("news_content")));
				newsVO.setNews_img(rs.getBytes("news_img"));
				newsVO.setNews_date(rs.getTimestamp("news_date"));
				newsVO.setNews_class(rs.getString("news_class"));
				newsVO.setNews_top(rs.getInt("news_top"));
				newsVO.setNews_pass(rs.getInt("news_pass"));
				set.add(newsVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Store_tagVO> getStore_tagsByStore_id(String store_id){
		Set<Store_tagVO> set = new LinkedHashSet<Store_tagVO>();
		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Store_tags_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
				set.add(store_tagVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<ProductVO> getProductsByStore_id(String store_id){
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Products_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				productVO.setProd_desc(readerToString(rs.getCharacterStream("prod_desc")));
				set.add(productVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<OrderlistVO> getOrderlistsByStore_id(String store_id){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				set.add(orderlistVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<ReplyVO> getReplysByStore_id(String store_id){
		Set<ReplyVO> set = new LinkedHashSet<ReplyVO>();
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Replys_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO = new ReplyVO();
				replyVO.setReply_id(rs.getString("reply_id"));
				replyVO.setMsg_id(rs.getString("msg_id"));
				replyVO.setMem_id(rs.getString("mem_id"));
				replyVO.setStore_id(rs.getString("store_id"));
				replyVO.setReply_content(readerToString(rs.getCharacterStream("reply_content")));
				replyVO.setReply_date(rs.getTimestamp("reply_date"));
				set.add(replyVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<ActivityVO> getActivitysByStore_id(String store_id){
		Set<ActivityVO> set = new LinkedHashSet<ActivityVO>();
		ActivityVO activityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Activitys_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				activityVO = new ActivityVO();
				activityVO.setActiv_id(rs.getString("activ_id"));
				activityVO.setMem_id(rs.getString("mem_id"));
				activityVO.setStore_id(rs.getString("store_id"));
				activityVO.setActiv_name(rs.getString("activ_name"));
				activityVO.setActiv_starttime(rs.getTimestamp("activ_starttime"));
				activityVO.setActiv_endtime(rs.getTimestamp("activ_endtime"));
				activityVO.setActiv_expire(rs.getTimestamp("activ_expire"));
				activityVO.setActiv_img(rs.getBytes("activ_img"));
				activityVO.setActiv_summary(rs.getString("activ_summary"));
				activityVO.setActiv_intro(readerToString(rs.getCharacterStream("activ_intro")));
				activityVO.setActiv_num(rs.getInt("activ_num"));
				activityVO.setActiv_store_cfm(rs.getInt("activ_store_cfm"));
				set.add(activityVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Fav_storeVO> getFav_storesByStore_id(String store_id){
		Set<Fav_storeVO> set = new LinkedHashSet<Fav_storeVO>();
		Fav_storeVO fav_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Fav_stores_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fav_storeVO = new Fav_storeVO();
				fav_storeVO.setMem_id(rs.getString("mem_id"));
				fav_storeVO.setStore_id(rs.getString("store_id"));
				set.add(fav_storeVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Photo_storeVO> getPhoto_storesByStore_id(String store_id){
		Set<Photo_storeVO> set = new LinkedHashSet<Photo_storeVO>();
		Photo_storeVO photo_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Photo_stores_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				photo_storeVO = new Photo_storeVO();
				photo_storeVO.setPhoto_id(rs.getString("photo_id"));
				photo_storeVO.setPhoto(rs.getBytes("photo"));
				photo_storeVO.setStore_id(rs.getString("store_id"));
				photo_storeVO.setMem_id(rs.getString("mem_id"));
				photo_storeVO.setPhoto_desc(readerToString(rs.getCharacterStream("photo_desc")));
				set.add(photo_storeVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Rept_storeVO> getRept_storesByStore_id(String store_id){
		Set<Rept_storeVO> set = new LinkedHashSet<Rept_storeVO>();
		Rept_storeVO rept_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rept_stores_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(rs.getString("store_id"));
				rept_storeVO.setMem_id(rs.getString("mem_id"));
				rept_storeVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				rept_storeVO.setRept_rev(rs.getInt("rept_rev"));
				set.add(rept_storeVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	public Reader stringToReader(String text) {
		if(text != null){
			return new StringReader(text);
		}
		else{
			return null;
		}
	}

	public String readerToString(Reader reader) {
		if(reader != null){
			int i;
			StringBuilder sb = new StringBuilder();
			try {
				while ((i = reader.read()) != -1) {
					sb.append((char)i);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
		else{
			return null;
		}
	}

}