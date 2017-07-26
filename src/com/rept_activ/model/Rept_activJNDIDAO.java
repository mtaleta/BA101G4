package com.rept_activ.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.activity.model.ActivityVO;

public class Rept_activJNDIDAO implements Rept_activDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO REPT_ACTIV (ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_ONE_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV WHERE ACTIV_ID = ? AND MEM_ID = ?";
	private static final String DELETE_REPT_ACTIV = "DELETE FROM REPT_ACTIV WHERE ACTIV_ID = ? AND MEM_ID = ?";
	private static final String UPDATE = "UPDATE REPT_ACTIV SET REPO_RSN=?, REPO_REV=? WHERE ACTIV_ID = ? AND MEM_ID = ?";

	// 以下新增
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	private static final String getActivitysByActivId = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME FROM ACTIVITY WHERE ACTIV_ID = ? order by ACTIV_STARTTIME desc";
	private static final String GET_ONEactiv_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV WHERE ACTIV_ID = ? ";
	
	
	// 以下新增
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	@Override
	public Set<ActivityVO> getActivitysByActivId(String activ_id) {
		
		Set<ActivityVO> set = new LinkedHashSet<ActivityVO>();
		ActivityVO activityVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(getActivitysByActivId);

			pstmt.setString(1, activ_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				activityVO = new ActivityVO();
				activityVO.setActiv_id(rs.getString("activ_id"));
				activityVO.setMem_id(rs.getString("mem_id"));
				activityVO.setStore_id(rs.getString("store_id"));
				activityVO.setActiv_name(rs.getString("activ_name"));
				activityVO.setActiv_starttime(rs.getTimestamp("activ_starttime"));
				activityVO.setActiv_endtime(rs.getTimestamp("activ_endtime"));
//				activityVO.setActiv_expire(rs.getTimestamp("activ_expire"));
//				activityVO.setActiv_summary(rs.getString("activ_summary"));
//				activityVO.setActiv_intro(rs.getString("activ_intro"));
				
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

	// 以下新增
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	@Override
	public Rept_activVO findByPrimaryKey(String activ_id){

		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONEactiv_STMT);

			pstmt.setString(1, activ_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
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
		return rept_activVO;
	}

	
	@Override
	public void insert(Rept_activVO rept_activVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, rept_activVO.getActiv_id());
			pstmt.setString(2, rept_activVO.getMem_id());
			pstmt.setCharacterStream(3, stringToReader(rept_activVO.getRepo_rsn()));
			pstmt.setInt(4, rept_activVO.getRepo_rev());

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
	public void update(Rept_activVO rept_activVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setCharacterStream(1, stringToReader(rept_activVO.getRepo_rsn()));
			pstmt.setInt(2, rept_activVO.getRepo_rev());
			pstmt.setString(3, rept_activVO.getActiv_id());
			pstmt.setString(4, rept_activVO.getMem_id());

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
	public void delete(String activ_id, String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_REPT_ACTIV);

			pstmt.setString(1, activ_id);
			pstmt.setString(2, mem_id);

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
	public Rept_activVO findByPrimaryKey(String activ_id, String mem_id) {

		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, activ_id);
			pstmt.setString(2, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
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
		return rept_activVO;
	}

	@Override
	public List<Rept_activVO> getAll() {

		List<Rept_activVO> list = new ArrayList<Rept_activVO>();
		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
				list.add(rept_activVO); // Store the row in the list
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