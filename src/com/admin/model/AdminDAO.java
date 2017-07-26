package com.admin.model;

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

import com.auth.model.AuthDAO;
import com.auth.model.AuthVO;
import com.tools.DatabaseUtils;

public class AdminDAO implements AdminDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ADMIN (ADMIN_ID,ADMIN_ACCT,ADMIN_PWD,ADMIN_NAME,ADMIN_EMAIL,ADMIN_EMPLOYED,ADMIN_IMG) VALUES ('ADMIN' || LPAD(to_char(ADMIN_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ADMIN_ID,ADMIN_ACCT,ADMIN_PWD,ADMIN_NAME,ADMIN_EMAIL,ADMIN_EMPLOYED,ADMIN_IMG FROM ADMIN ORDER BY ADMIN_ID";
	private static final String GET_ONE_STMT = "SELECT ADMIN_ID,ADMIN_ACCT,ADMIN_PWD,ADMIN_NAME,ADMIN_EMAIL,ADMIN_EMPLOYED,ADMIN_IMG FROM ADMIN WHERE ADMIN_ID = ?";
	private static final String GET_Auths_ByAdmin_id_STMT = "SELECT ADMIN_ID,FEATURE_ID FROM AUTH WHERE ADMIN_ID = ? ORDER BY ADMIN_ID";

	private static final String DELETE_AUTHs = "DELETE FROM AUTH WHERE ADMIN_ID = ?";
	private static final String DELETE_ADMIN = "DELETE FROM ADMIN WHERE ADMIN_ID = ?";
	private static final String UPDATE = "UPDATE ADMIN SET ADMIN_ACCT=?, ADMIN_PWD=?, ADMIN_NAME=?, ADMIN_EMAIL=?, ADMIN_EMPLOYED=?, ADMIN_IMG=? WHERE ADMIN_ID = ?";
	private static final String Login_Admin = "SELECT * FROM ADMIN WHERE ADMIN_ACCT= ? AND ADMIN_PWD = ? AND ADMIN_EMPLOYED=0";
	private static final String AUTH_INSERT_STMT = "INSERT INTO AUTH (ADMIN_ID,FEATURE_ID) VALUES (?, ?)";
	private static final String ADMIN_FORGET_PASSWORD = "UPDATE ADMIN SET ADMIN_PWD=? WHERE ADMIN_ACCT=?";

	@Override
	public AdminVO insert(AdminVO adminVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT, new String[] { "ADMIN_ID" });

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());

			if (adminVO.getAdmin_img() == null) {
				pstmt.setBinaryStream(6, this.getClass().getResourceAsStream("/admin01.jpg"));
			} else {
				pstmt.setBytes(6, adminVO.getAdmin_img());
			}

			int rows = pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			if (rows > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					String admin_id = rs.getString(1);
					return this.findByPrimaryKey(admin_id);
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
	public void update(AdminVO adminVO, String[] feature) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());
			if (adminVO.getAdmin_img() == null) {
				pstmt.setBinaryStream(6, this.getClass().getResourceAsStream("/admin01.jpg"));
			} else {
				pstmt.setBytes(6, adminVO.getAdmin_img());
			}

			pstmt.setString(7, adminVO.getAdmin_id());

			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);

			AuthDAO authDao = new AuthDAO();
			List<AuthVO> auths = authDao.findByPrimaryKey(adminVO.getAdmin_id());
			if (auths == null && feature == null) {
				// not thing...
			} else if (auths != null && feature == null) {
				for (AuthVO vo : auths) {
					authDao.delete(vo.getAdmin_id(), vo.getFeature_id());
				}
			} else if (auths == null && feature != null) {
				for (String f : feature) {
					AuthVO authVO = new AuthVO();
					authVO.setAdmin_id(adminVO.getAdmin_id());
					authVO.setFeature_id(f);
					authDao.insert(authVO);
				}
			} else {
				for (AuthVO vo : auths) {
					authDao.delete(vo.getAdmin_id(), vo.getFeature_id());
				}
				for (String f : feature) {
					AuthVO authVO = new AuthVO();
					authVO.setAdmin_id(adminVO.getAdmin_id());
					authVO.setFeature_id(f);
					authDao.insert(authVO);
				}
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
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
	public void delete(String admin_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_AUTHs);

			pstmt.setString(1, admin_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ADMIN);

			pstmt.setString(1, admin_id);

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
	public AdminVO findByPrimaryKey(String admin_id) {

		AdminVO adminVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, admin_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdmin_id(rs.getString("admin_id"));
				adminVO.setAdmin_acct(rs.getString("admin_acct"));
				adminVO.setAdmin_pwd(rs.getString("admin_pwd"));
				adminVO.setAdmin_name(rs.getString("admin_name"));
				adminVO.setAdmin_email(rs.getString("admin_email"));
				adminVO.setAdmin_employed(rs.getInt("admin_employed"));
				adminVO.setAdmin_img(rs.getBytes("admin_img"));
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
		return adminVO;
	}

	@Override
	public List<AdminVO> getAll() {

		List<AdminVO> list = new ArrayList<AdminVO>();
		AdminVO adminVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdmin_id(rs.getString("admin_id"));
				adminVO.setAdmin_acct(rs.getString("admin_acct"));
				adminVO.setAdmin_pwd(rs.getString("admin_pwd"));
				adminVO.setAdmin_name(rs.getString("admin_name"));
				adminVO.setAdmin_email(rs.getString("admin_email"));
				adminVO.setAdmin_employed(rs.getInt("admin_employed"));
				adminVO.setAdmin_img(rs.getBytes("admin_img"));

				list.add(adminVO); // Store the row in the list
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
	public Set<AuthVO> getAuthsByAdmin_id(String admin_id) {
		try {
			return DatabaseUtils.querySet(ds, GET_Auths_ByAdmin_id_STMT, AuthVO.class, admin_id);
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}
	}

	@Override
	public AdminVO login_Admin(AdminVO adminVO) {
		try {
			AdminVO vo = DatabaseUtils.queryOne(ds, Login_Admin, AdminVO.class, adminVO.getAdmin_acct(),
					adminVO.getAdmin_pwd());
			if (vo != null) {
				Set<AuthVO> authTypeSet = this.getAuthsByAdmin_id(vo.getAdmin_id());
				vo.setAuths(new ArrayList<AuthVO>(authTypeSet));
			}
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
			new RuntimeException("A database error occured. " + e.getMessage());
			return null;
		}
	}

	@Override
	public AdminVO insert(AdminVO adminVO, String[] feature) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT, new String[] { "ADMIN_ID" });

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());

			if (adminVO.getAdmin_img() == null) {
				pstmt.setBinaryStream(6, this.getClass().getResourceAsStream("/admin01.jpg"));
			} else {
				pstmt.setBytes(6, adminVO.getAdmin_img());
			}

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					String admin_id = rs.getString(1);
					if (feature != null) {
						for (String f : feature) {
							pstmt = con.prepareStatement(AUTH_INSERT_STMT);
							pstmt.setString(1, admin_id);
							pstmt.setString(2, f);
							pstmt.executeUpdate();
						}
					}
					con.commit();
					return this.findByPrimaryKey(admin_id);
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
	public void Admin_Forget_Password(String admin_acct, String admin_pwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(ADMIN_FORGET_PASSWORD);
			pstmt.setString(1, admin_pwd);
			pstmt.setString(2, admin_acct);
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

}