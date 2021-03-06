package com.member.model;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.msg.model.MsgVO;
import com.orderlist.model.OrderlistVO;
import com.participant.model.ParticipantVO;
import com.photo_store.model.Photo_storeVO;
import com.rate_n_rev.model.Rate_n_revVO;
import com.reply.model.ReplyVO;
import com.report.model.ReportVO;
import com.rept_activ.model.Rept_activVO;
import com.rept_store.model.Rept_storeVO;
import com.spndcoffeelist.model.SpndcoffeelistVO;

public class MemberDAO implements MemberDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MEMBER (MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG,MEM_AUTHENTICATION,MEM_VALIDATECODE) VALUES ('MEM' || LPAD(to_char(MEM_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG,MEM_AUTHENTICATION,MEM_VALIDATECODE FROM MEMBER ORDER BY MEM_ID DESC";
	private static final String GET_ONE_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG,MEM_AUTHENTICATION,MEM_VALIDATECODE FROM MEMBER WHERE MEM_ID = ?";
	private static final String DELETE_MEMBER = "DELETE FROM MEMBER WHERE MEM_ID = ?";
	private static final String UPDATE = "UPDATE MEMBER SET MEM_ACCT=?,MEM_PWD=?, MEM_NAME=?, MEM_TEL=?, MEM_EMAIL=?, MEM_ADD=?, MEM_POINTS=?, MEM_IMG=? ,MEM_AUTHENTICATION=?, MEM_VALIDATECODE=? WHERE MEM_ID = ?";
	private static final String Login_Member = "SELECT * FROM MEMBER WHERE MEM_ACCT= ? AND MEM_PWD = ?";
	private static final String GET_ONE_BY_ACCOUNT_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG,MEM_AUTHENTICATION,MEM_VALIDATECODE FROM MEMBER WHERE MEM_ACCT = ?";
	private static final String MEM_AUTHENTICATION = "UPDATE MEMBER SET MEM_AUTHENTICATION=? WHERE MEM_ACCT = ?";
	private static final String MEM_FORGET_PASSWORD = "UPDATE MEMBER SET MEM_PWD=? WHERE MEM_ACCT=?";
	
	private static final String GET_Spndcoffeelists_ByMem_id_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE MEM_ID = ? ORDER BY LIST_ID";
	private static final String GET_Rate_n_revs_ByMem_id_STMT = "SELECT RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE FROM RATE_N_REV WHERE MEM_ID = ? ORDER BY RNR_ID";
	private static final String GET_Reports_ByMem_id_STMT = "SELECT MEM_ID,RNR_ID,REPT_VERF,REPT_RSN FROM REPORT WHERE MEM_ID = ? ORDER BY MEM_ID,RNR_ID";
	private static final String GET_Orderlists_ByMem_id_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE MEM_ID = ? ORDER BY ORD_ID";
	private static final String GET_Msgs_ByMem_id_STMT = "SELECT MSG_ID,MEM_ID,PROD_ID,MSG_CONTENT,MSG_DATE FROM MSG WHERE MEM_ID = ? ORDER BY MSG_ID";
	private static final String GET_Replys_ByMem_id_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY WHERE MEM_ID = ? ORDER BY REPLY_ID";
	private static final String GET_Activitys_ByMem_id_STMT = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM FROM ACTIVITY WHERE MEM_ID = ? ORDER BY ACTIV_ID";
	private static final String GET_Participants_ByMem_id_STMT = "SELECT ACTIV_ID,MEM_ID FROM PARTICIPANT WHERE MEM_ID = ? ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_Rept_activs_ByMem_id_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV WHERE MEM_ID = ? ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_Fav_stores_ByMem_id_STMT = "SELECT MEM_ID,STORE_ID FROM FAV_STORE WHERE MEM_ID = ? ORDER BY MEM_ID,STORE_ID";
	private static final String GET_Photo_stores_ByMem_id_STMT = "SELECT PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC FROM PHOTO_STORE WHERE MEM_ID = ? ORDER BY PHOTO_ID";
	private static final String GET_Rept_stores_ByMem_id_STMT = "SELECT STORE_ID,MEM_ID,REPT_RSN,REPT_REV FROM REPT_STORE WHERE MEM_ID = ? ORDER BY STORE_ID,MEM_ID";

	private static final String DELETE_SPNDCOFFEELISTs = "DELETE FROM SPNDCOFFEELIST WHERE MEM_ID = ?";
	private static final String DELETE_RATE_N_REVs = "DELETE FROM RATE_N_REV WHERE MEM_ID = ?";
	private static final String DELETE_REPORTs = "DELETE FROM REPORT WHERE MEM_ID = ?";
	private static final String DELETE_ORDERLISTs = "DELETE FROM ORDERLIST WHERE MEM_ID = ?";
	private static final String DELETE_MSGs = "DELETE FROM MSG WHERE MEM_ID = ?";
	private static final String DELETE_REPLYs = "DELETE FROM REPLY WHERE MEM_ID = ?";
	private static final String DELETE_ACTIVITYs = "DELETE FROM ACTIVITY WHERE MEM_ID = ?";
	private static final String DELETE_PARTICIPANTs = "DELETE FROM PARTICIPANT WHERE MEM_ID = ?";
	private static final String DELETE_REPT_ACTIVs = "DELETE FROM REPT_ACTIV WHERE MEM_ID = ?";
	private static final String DELETE_FAV_STOREs = "DELETE FROM FAV_STORE WHERE MEM_ID = ?";
	private static final String DELETE_PHOTO_STOREs = "DELETE FROM PHOTO_STORE WHERE MEM_ID = ?";
	private static final String DELETE_REPT_STOREs = "DELETE FROM REPT_STORE WHERE MEM_ID = ?";
	
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	private static final String GET_Spndcoffeelists_ByMem_id_DESC_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE MEM_ID = ? ORDER BY LIST_ID DESC";
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪

	// new STMT
	private static final String UPDATE_POINTS = "UPDATE MEMBER SET MEM_POINTS=? WHERE MEM_ID = ?";
	private static final String GET_Orderlists_ByMem_id_DESC_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE MEM_ID = ? ORDER BY ORD_ID DESC";
	private static final String GET_Orderlists_ByMem_id_And_Ord_pick_DESC_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE MEM_ID = ? AND ORD_PICK = ? ORDER BY ORD_ID DESC";
	// new STMT

	// Adding by Sylvie
//	private static final String GET_OrderlistList_ByMem_id_STMT = "SELECT * FROM ORDERLIST WHERE MEM_ID = ? and not ord_pick = 1 ORDER BY ORD_ID";
	private static final String GET_OrderlistList_ByMem_id_and_Ord_pick_DESC_STMT = "SELECT * FROM ORDERLIST WHERE MEM_ID = ? and ord_pick in (?,?) ORDER BY ORD_ID DESC";

//	// Adding by Sylvie
//	@Override
//	public List<OrderlistVO> getOrderlistListByMem_id(String mem_id){
//		List<OrderlistVO> list = new ArrayList<OrderlistVO>();
//		OrderlistVO orderlistVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_OrderlistList_ByMem_id_STMT);
//			pstmt.setString(1, mem_id);
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
//				list.add(orderlistVO); // Store the row in the vector
//			}
//
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
//		return list;
//	}

	// Adding by Sylvie
	@Override
	public List<OrderlistVO> getOrderlistListByMem_idAndOrd_pickDESC(String mem_id, Integer ord_pick, Integer ord_pick2){
		List<OrderlistVO> list = new ArrayList<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_OrderlistList_ByMem_id_and_Ord_pick_DESC_STMT);
			pstmt.setString(1, mem_id);
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

				list.add(orderlistVO); // Store the row in the vector
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
		return list;
	}

	// new method
	// 下單時扣款
	@Override
	public void updateForCharge(MemberVO memberVO, Connection con){
		
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(UPDATE_POINTS);

			pstmt.setInt(1, memberVO.getMem_points());
			pstmt.setString(2, memberVO.getMem_id());

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
	// 會員全部訂單
	@Override
	public Set<OrderlistVO> getOrderlistsByMem_idDesc(String mem_id){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByMem_id_DESC_STMT);
			pstmt.setString(1, mem_id);

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
	// 會員單一種取貨方式的訂單
	@Override
	public Set<OrderlistVO> getOrderlistsByMem_idAndOrd_pickDesc(String mem_id, Integer ord_pick){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByMem_id_And_Ord_pick_DESC_STMT);
			pstmt.setString(1, mem_id);
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
	
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	@Override
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByMem_idDesc(String mem_id){
		Set<SpndcoffeelistVO> set = new LinkedHashSet<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Spndcoffeelists_ByMem_id_DESC_STMT);
			pstmt.setString(1, mem_id);

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
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪

	public MemberVO insert(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT, new String[] { "MEM_ID" });

			pstmt.setString(1, memberVO.getMem_acct());
			pstmt.setString(2, memberVO.getMem_pwd());
			pstmt.setString(3, memberVO.getMem_name());
			pstmt.setString(4, memberVO.getMem_tel());
			pstmt.setString(5, memberVO.getMem_email());
			pstmt.setString(6, memberVO.getMem_add());
			pstmt.setInt(7, memberVO.getMem_points());
			if (memberVO.getMem_img() == null) {
				pstmt.setBinaryStream(8, this.getClass().getResourceAsStream("/mem01.jpg"));
			} else {
				pstmt.setBytes(8, memberVO.getMem_img());
			}
			pstmt.setObject(9, memberVO.getMem_authentication());
			pstmt.setString(10, memberVO.getMem_validateCode());

			int rows = pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			if (rows > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					String mem_id = rs.getString(1);
					return this.findByPrimaryKey(mem_id);
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
	public void update(MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, memberVO.getMem_acct());
			pstmt.setString(2, memberVO.getMem_pwd());
			pstmt.setString(3, memberVO.getMem_name());
			pstmt.setString(4, memberVO.getMem_tel());
			pstmt.setString(5, memberVO.getMem_email());
			pstmt.setString(6, memberVO.getMem_add());
			pstmt.setInt(7, memberVO.getMem_points());

			if (memberVO.getMem_img() == null) {
				pstmt.setBinaryStream(8, this.getClass().getResourceAsStream("/mem01.jpg"));
			} else {
				pstmt.setBytes(8, memberVO.getMem_img());
			}

			if (memberVO.getMem_authentication() == null) {
				pstmt.setObject(9, memberVO.getMem_authentication());
			} else {
				pstmt.setInt(9, memberVO.getMem_authentication());
			}
			pstmt.setString(10, memberVO.getMem_validateCode());
			pstmt.setString(11, memberVO.getMem_id());

			pstmt.executeUpdate();
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
	public void delete(String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEELISTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_RATE_N_REVs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPORTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDERLISTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_MSGs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPLYs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ACTIVITYs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PARTICIPANTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_ACTIVs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_FAV_STOREs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PHOTO_STOREs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_STOREs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_MEMBER);

			pstmt.setString(1, mem_id);

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
	public MemberVO findByPrimaryKey(String mem_id) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getString("mem_id"));
				memberVO.setMem_acct(rs.getString("mem_acct"));
				memberVO.setMem_pwd(rs.getString("mem_pwd"));
				memberVO.setMem_name(rs.getString("mem_name"));
				memberVO.setMem_tel(rs.getString("mem_tel"));
				memberVO.setMem_email(rs.getString("mem_email"));
				memberVO.setMem_add(rs.getString("mem_add"));
				memberVO.setMem_points(rs.getInt("mem_points"));
				memberVO.setMem_img(rs.getBytes("mem_img"));
				memberVO.setMem_authentication(
						rs.getObject("mem_authentication") != null ? rs.getInt("mem_authentication") : null);
				memberVO.setMem_validateCode(rs.getString("mem_validateCode"));
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
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {

		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getString("mem_id"));
				memberVO.setMem_acct(rs.getString("mem_acct"));
				memberVO.setMem_pwd(rs.getString("mem_pwd"));
				memberVO.setMem_name(rs.getString("mem_name"));
				memberVO.setMem_tel(rs.getString("mem_tel"));
				memberVO.setMem_email(rs.getString("mem_email"));
				memberVO.setMem_add(rs.getString("mem_add"));
				memberVO.setMem_points(rs.getInt("mem_points"));
				memberVO.setMem_img(rs.getBytes("mem_img"));

				list.add(memberVO); // Store the row in the list
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
	public MemberVO login_Member(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO memberVO_login = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(Login_Member);
			pstmt.setString(1, memberVO.getMem_acct());
			pstmt.setString(2, memberVO.getMem_pwd());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO_login = new MemberVO();
				memberVO_login.setMem_id(rs.getString("mem_id"));
				memberVO_login.setMem_acct(rs.getString("mem_acct"));
				memberVO_login.setMem_pwd(rs.getString("mem_pwd"));
				memberVO_login.setMem_name(rs.getString("mem_name"));
				memberVO_login.setMem_tel(rs.getString("mem_tel"));
				memberVO_login.setMem_email(rs.getString("mem_email"));
				memberVO_login.setMem_add(rs.getString("mem_add"));
				memberVO_login.setMem_points(rs.getInt("mem_points"));
				memberVO_login.setMem_img(rs.getBytes("mem_img"));
				memberVO_login.setMem_authentication(
						rs.getObject("mem_authentication") != null ? rs.getInt("mem_authentication") : null);

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
		return memberVO_login;
	}

	@Override
	public MemberVO findByAccount(String mem_acct) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ACCOUNT_STMT);
			pstmt.setString(1, mem_acct);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getString("mem_id"));
				memberVO.setMem_acct(rs.getString("mem_acct"));
				memberVO.setMem_pwd(rs.getString("mem_pwd"));
				memberVO.setMem_name(rs.getString("mem_name"));
				memberVO.setMem_tel(rs.getString("mem_tel"));
				memberVO.setMem_email(rs.getString("mem_email"));
				memberVO.setMem_add(rs.getString("mem_add"));
				memberVO.setMem_points(rs.getInt("mem_points"));
				memberVO.setMem_img(rs.getBytes("mem_img"));
				memberVO.setMem_authentication(
						rs.getObject("mem_authentication") != null ? rs.getInt("mem_authentication") : null);
				memberVO.setMem_validateCode(rs.getString("mem_validateCode"));
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
		return memberVO;
	}

	@Override
	public void MEM_AUTHENTICATION(String mem_acct, Integer mem_authentication) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(MEM_AUTHENTICATION);
			pstmt.setInt(1, mem_authentication);
			pstmt.setString(2, mem_acct);

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
	public void Mem_Forget_Password(String mem_acct, String mem_pwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(MEM_FORGET_PASSWORD);
			pstmt.setString(1, mem_pwd);
			pstmt.setString(2, mem_acct);
			pstmt.executeUpdate();
			con.commit();
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
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByMem_id(String mem_id){
		Set<SpndcoffeelistVO> set = new LinkedHashSet<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Spndcoffeelists_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<Rate_n_revVO> getRate_n_revsByMem_id(String mem_id){
		Set<Rate_n_revVO> set = new LinkedHashSet<Rate_n_revVO>();
		Rate_n_revVO rate_n_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rate_n_revs_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<ReportVO> getReportsByMem_id(String mem_id){
		Set<ReportVO> set = new LinkedHashSet<ReportVO>();
		ReportVO reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Reports_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportVO = new ReportVO();
				reportVO.setMem_id(rs.getString("mem_id"));
				reportVO.setRnr_id(rs.getString("rnr_id"));
				reportVO.setRept_verf(rs.getInt("rept_verf"));
				reportVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				set.add(reportVO); // Store the row in the vector
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
	public Set<OrderlistVO> getOrderlistsByMem_id(String mem_id){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<MsgVO> getMsgsByMem_id(String mem_id){
		Set<MsgVO> set = new LinkedHashSet<MsgVO>();
		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Msgs_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				msgVO = new MsgVO();
				msgVO.setMsg_id(rs.getString("msg_id"));
				msgVO.setMem_id(rs.getString("mem_id"));
				msgVO.setProd_id(rs.getString("prod_id"));
				msgVO.setMsg_content(readerToString(rs.getCharacterStream("msg_content")));
				msgVO.setMsg_date(rs.getTimestamp("msg_date"));
				set.add(msgVO); // Store the row in the vector
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
	public Set<ReplyVO> getReplysByMem_id(String mem_id){
		Set<ReplyVO> set = new LinkedHashSet<ReplyVO>();
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Replys_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<ActivityVO> getActivitysByMem_id(String mem_id){
		Set<ActivityVO> set = new LinkedHashSet<ActivityVO>();
		ActivityVO activityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Activitys_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<ParticipantVO> getParticipantsByMem_id(String mem_id){
		Set<ParticipantVO> set = new LinkedHashSet<ParticipantVO>();
		ParticipantVO participantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Participants_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				participantVO = new ParticipantVO();
				participantVO.setActiv_id(rs.getString("activ_id"));
				participantVO.setMem_id(rs.getString("mem_id"));
				set.add(participantVO); // Store the row in the vector
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
	public Set<Rept_activVO> getRept_activsByMem_id(String mem_id){
		Set<Rept_activVO> set = new LinkedHashSet<Rept_activVO>();
		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rept_activs_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
				set.add(rept_activVO); // Store the row in the vector
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
	public Set<Fav_storeVO> getFav_storesByMem_id(String mem_id){
		Set<Fav_storeVO> set = new LinkedHashSet<Fav_storeVO>();
		Fav_storeVO fav_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Fav_stores_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<Photo_storeVO> getPhoto_storesByMem_id(String mem_id){
		Set<Photo_storeVO> set = new LinkedHashSet<Photo_storeVO>();
		Photo_storeVO photo_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Photo_stores_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
	public Set<Rept_storeVO> getRept_storesByMem_id(String mem_id){
		Set<Rept_storeVO> set = new LinkedHashSet<Rept_storeVO>();
		Rept_storeVO rept_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rept_stores_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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