package com.store_tag.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Store_tagJNDIDAO implements Store_tagDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO STORE_TAG (STORE_ID,TAG_ID,TAG_NUM) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG ORDER BY STORE_ID,TAG_ID";
	private static final String GET_ONE_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG WHERE STORE_ID = ? AND TAG_ID = ?";
	private static final String DELETE_STORE_TAG = "DELETE FROM STORE_TAG WHERE STORE_ID = ? AND TAG_ID = ?";
	private static final String UPDATE = "UPDATE STORE_TAG SET TAG_NUM=? WHERE STORE_ID = ? AND TAG_ID = ?";

	
	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	private static final String GET_STORESID_BY_TAGID = "SELECT STORE_ID FROM STORE_TAG WHERE TAG_ID = ?";
	private static final String GET_TAGSID_BY_STOREID = "SELECT TAG_ID FROM STORE_TAG WHERE STORE_ID = ?";
	
	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	@Override
	public Set<Store_tagVO> getStoresIdByTag_id(String tag_id) {


		// TODO Auto-generated method stub
		Set<Store_tagVO> store_tagSet = new LinkedHashSet<Store_tagVO>();
		Store_tagVO store_tagVO =  null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORESID_BY_TAGID);
			
			pstmt.setString(1, tag_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
//				store_tagVO.setTag_id(rs.getString("tag_id"));
//				store_tagVO.setTag_num(rs.getInt("tag_num"));
				
				store_tagSet.add(store_tagVO);
				
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

		return store_tagSet ;

	}

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	@Override
	public Set<Store_tagVO> getTagsIdByStore_id(String store_id) {


		Set<Store_tagVO> store_tagSet = new LinkedHashSet<Store_tagVO>();
		Store_tagVO store_tagVO =  null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TAGSID_BY_STOREID);
			
			pstmt.setString(1, store_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
				
				store_tagSet.add(store_tagVO);
				
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
		
		
		return store_tagSet ;

	}


	@Override
	public void insert(Store_tagVO store_tagVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, store_tagVO.getStore_id());
			pstmt.setString(2, store_tagVO.getTag_id());
			pstmt.setInt(3, store_tagVO.getTag_num());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
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
	public void update(Store_tagVO store_tagVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, store_tagVO.getTag_num());
			pstmt.setString(2, store_tagVO.getStore_id());
			pstmt.setString(3, store_tagVO.getTag_id());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
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
	public void delete(String store_id, String tag_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STORE_TAG);

			pstmt.setString(1, store_id);
			pstmt.setString(2, tag_id);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
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
	public Store_tagVO findByPrimaryKey(String store_id, String tag_id) {

		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, store_id);
			pstmt.setString(2, tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
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
		return store_tagVO;
	}

	@Override
	public List<Store_tagVO> getAll() {

		List<Store_tagVO> list = new ArrayList<Store_tagVO>();
		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
				list.add(store_tagVO); // Store the row in the list
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


}