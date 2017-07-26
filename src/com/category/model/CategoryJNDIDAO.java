package com.category.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.Set;
import com.product.model.ProductVO;

public class CategoryJNDIDAO implements CategoryDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO CATEGORY (CATE_ID,CATE_NAME,PROD_CATEGORY) VALUES ('CATE' || LPAD(to_char(CATE_ID_SQ.NEXTVAL), 8, '0'), ?, ?)";
	private static final String GET_ALL_STMT = "SELECT CATE_ID,CATE_NAME,PROD_CATEGORY FROM CATEGORY ORDER BY CATE_ID";
	private static final String GET_ONE_STMT = "SELECT CATE_ID,CATE_NAME,PROD_CATEGORY FROM CATEGORY WHERE CATE_ID = ?";
	private static final String GET_Products_ByCate_id_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE CATE_ID = ? ORDER BY PROD_ID";

	private static final String DELETE_PRODUCTs = "DELETE FROM PRODUCT WHERE CATE_ID = ?";
	private static final String DELETE_CATEGORY = "DELETE FROM CATEGORY WHERE CATE_ID = ?";
	private static final String UPDATE = "UPDATE CATEGORY SET CATE_NAME=?, PROD_CATEGORY=? WHERE CATE_ID = ?";

	// new STMT
	// 查詢單一種商品類型的全部商品
	private static final String GET_Launched_Products_ByCate_id_DESC_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE PROD_LAUNCH = 1 AND CATE_ID = ? ORDER BY PROD_ID DESC";
	private static final String GET_ByCate_name_And_Prod_category_STMT = "SELECT CATE_ID,CATE_NAME,PROD_CATEGORY FROM CATEGORY WHERE CATE_NAME = ? AND PROD_CATEGORY = ?";
	// 此區塊與晴帆findCateByProd_cate相同，但有ORDER BY CATE_ID
	private static final String GET_ByProd_category_STMT = "SELECT CATE_ID,CATE_NAME,PROD_CATEGORY FROM CATEGORY WHERE PROD_CATEGORY = ? ORDER BY CATE_ID";
	// 此區塊與晴帆findCateByProd_cate相同，但有ORDER BY CATE_ID
	// new STMT
	
	// Adding by Sylvie
	private static final String findCateByProd_cate = "select * from category where prod_category = ?";
	
	// Adding by Sylvie
	@Override
	public List<CategoryVO> findCateByProd_cate(Integer prod_category) {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		CategoryVO categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
				
			pstmt = con.prepareStatement(findCateByProd_cate);
			pstmt.setInt(1, prod_category);		
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCate_id(rs.getString("cate_id"));
				categoryVO.setCate_name(rs.getString("cate_name"));
				categoryVO.setProd_category(rs.getInt("prod_category"));
				list.add(categoryVO);
			}
			
			pstmt.executeUpdate();
			
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
		return list;
	}

	// new method
	// 查詢單一種商品類型的全部商品
	@Override
	public Set<ProductVO> getLaunchedProductsByCate_idDesc(String cate_id) {
		
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Launched_Products_ByCate_id_DESC_STMT);
			
			pstmt.setString(1, cate_id);

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
	public CategoryVO getOneByCate_nameAndProd_category(String cate_name, Integer prod_category) {

		CategoryVO categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ByCate_name_And_Prod_category_STMT);

			pstmt.setString(1, cate_name);
			pstmt.setInt(2, prod_category);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCate_id(rs.getString("cate_id"));
				categoryVO.setCate_name(rs.getString("cate_name"));
				categoryVO.setProd_category(rs.getInt("prod_category"));
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
		return categoryVO;
	}
	// new method
	
	// new method
	// 此區塊與晴帆findCateByProd_cate()相同
	// 此區塊與晴帆findCateByProd_cate()相同
	@Override
	public Set<CategoryVO> getByProd_category(Integer prod_category) {

		Set<CategoryVO> set = new LinkedHashSet<CategoryVO>();
		CategoryVO categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ByProd_category_STMT);

			pstmt.setInt(1, prod_category);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCate_id(rs.getString("cate_id"));
				categoryVO.setCate_name(rs.getString("cate_name"));
				categoryVO.setProd_category(rs.getInt("prod_category"));
				set.add(categoryVO);
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
	// 此區塊與晴帆findCateByProd_cate()相同
	// 此區塊與晴帆findCateByProd_cate()相同
	// new method
	
	@Override
	public void insert(CategoryVO categoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, categoryVO.getCate_name());
			pstmt.setInt(2, categoryVO.getProd_category());

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
	public void update(CategoryVO categoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, categoryVO.getCate_name());
			pstmt.setInt(2, categoryVO.getProd_category());
			pstmt.setString(3, categoryVO.getCate_id());

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
	public void delete(String cate_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_PRODUCTs);

			pstmt.setString(1, cate_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_CATEGORY);

			pstmt.setString(1, cate_id);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "+ excep.getMessage());
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
	public CategoryVO findByPrimaryKey(String cate_id) {

		CategoryVO categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cate_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCate_id(rs.getString("cate_id"));
				categoryVO.setCate_name(rs.getString("cate_name"));
				categoryVO.setProd_category(rs.getInt("prod_category"));
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
		return categoryVO;
	}

	@Override
	public List<CategoryVO> getAll() {

		List<CategoryVO> list = new ArrayList<CategoryVO>();
		CategoryVO categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCate_id(rs.getString("cate_id"));
				categoryVO.setCate_name(rs.getString("cate_name"));
				categoryVO.setProd_category(rs.getInt("prod_category"));
				list.add(categoryVO); // Store the row in the list
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
	public Set<ProductVO> getProductsByCate_id(String cate_id){
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Products_ByCate_id_STMT);
			pstmt.setString(1, cate_id);

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