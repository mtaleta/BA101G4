package com.product.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import com.orderdetail.model.OrderdetailVO;
import com.tools.SpecialQuery;
import com.msg.model.MsgVO;

public class ProductJDBCDAO implements ProductDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO PRODUCT (PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC) VALUES ('PROD' || LPAD(to_char(PROD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT ORDER BY PROD_ID";
	private static final String GET_ONE_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE PROD_ID = ?";
	private static final String GET_Orderdetails_ByProd_id_STMT = "SELECT ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT FROM ORDERDETAIL WHERE PROD_ID = ? ORDER BY ORD_ID,PROD_ID";
	private static final String GET_Msgs_ByProd_id_STMT = "SELECT MSG_ID,MEM_ID,PROD_ID,MSG_CONTENT,MSG_DATE FROM MSG WHERE PROD_ID = ? ORDER BY MSG_ID";

	private static final String DELETE_ORDERDETAILs = "DELETE FROM ORDERDETAIL WHERE PROD_ID = ?";
	private static final String DELETE_MSGs = "DELETE FROM MSG WHERE PROD_ID = ?";
	private static final String DELETE_PRODUCT = "DELETE FROM PRODUCT WHERE PROD_ID = ?";
	private static final String UPDATE = "UPDATE PRODUCT SET STORE_ID=?, PROD_NAME=?, CATE_ID=?, PROD_PRICE=?, PROD_CATEGORY=?, PROD_IMG=?, PROD_AMT=?, PROD_LAUNCH=?, PROD_DESC=? WHERE PROD_ID = ?";

	// new STMT
	// 下單時扣庫存量
	private static final String UPDATE_AMT = "UPDATE PRODUCT SET PROD_AMT=? WHERE PROD_ID = ?";
	// 單一種購物類型的上架商品
	private static final String Launched_ByProd_category_DESC_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH,PROD_DESC FROM PRODUCT WHERE PROD_LAUNCH = 1 AND PROD_CATEGORY = ? ORDER BY PROD_ID DESC";
	// new STMT
	
	// new method
	// 搜尋單一購物類型的商品名稱
	@Override
	public List<ProductVO> searchLaunchedByProd_categoryAndProd_name(Integer prod_category, String[] search) {

		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from product where prod_launch = 1 and prod_category = ?" + SpecialQuery.multipleProd_name(search) + " order by prod_id desc";
			pstmt = con.prepareStatement(finalSQL);

			pstmt.setInt(1, prod_category);
			
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
				list.add(productVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
	// new method
	
	// new method
	// 下單時扣庫存量
	@Override
	public void updateAmt(ProductVO productVO, Connection con){
		
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(UPDATE_AMT);

			pstmt.setInt(1, productVO.getProd_amt());
			pstmt.setString(2, productVO.getProd_id());

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
	// 單一種購物類型的上架商品
	@Override
	public Set<ProductVO> launchedByProd_categoryDesc(Integer prod_category) {

		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Launched_ByProd_category_DESC_STMT);

			pstmt.setInt(1, prod_category);

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
				set.add(productVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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


	@Override
	public void insert(ProductVO productVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productVO.getStore_id());
			pstmt.setString(2, productVO.getProd_name());
			pstmt.setString(3, productVO.getCate_id());
			pstmt.setInt(4, productVO.getProd_price());
			pstmt.setInt(5, productVO.getProd_category());
			pstmt.setBytes(6, productVO.getProd_img());
			pstmt.setInt(7, productVO.getProd_amt());
			pstmt.setInt(8, productVO.getProd_launch());
			pstmt.setCharacterStream(9, stringToReader(productVO.getProd_desc()));

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
	public void update(ProductVO productVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productVO.getStore_id());
			pstmt.setString(2, productVO.getProd_name());
			pstmt.setString(3, productVO.getCate_id());
			pstmt.setInt(4, productVO.getProd_price());
			pstmt.setInt(5, productVO.getProd_category());
			pstmt.setBytes(6, productVO.getProd_img());
			pstmt.setInt(7, productVO.getProd_amt());
			pstmt.setInt(8, productVO.getProd_launch());
			pstmt.setCharacterStream(9, stringToReader(productVO.getProd_desc()));
			pstmt.setString(10, productVO.getProd_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
	public void delete(String prod_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_ORDERDETAILs);

			pstmt.setString(1, prod_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_MSGs);

			pstmt.setString(1, prod_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PRODUCT);

			pstmt.setString(1, prod_id);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
	public ProductVO findByPrimaryKey(String prod_id) {

		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, prod_id);

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
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
		return productVO;
	}

	@Override
	public List<ProductVO> getAll() {

		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

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
				list.add(productVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
	public Set<OrderdetailVO> getOrderdetailsByProd_id(String prod_id){
		Set<OrderdetailVO> set = new LinkedHashSet<OrderdetailVO>();
		OrderdetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Orderdetails_ByProd_id_STMT);
			pstmt.setString(1, prod_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderdetailVO = new OrderdetailVO();
				orderdetailVO.setOrd_id(rs.getString("ord_id"));
				orderdetailVO.setProd_id(rs.getString("prod_id"));
				orderdetailVO.setProd_name(rs.getString("prod_name"));
				orderdetailVO.setProd_price(rs.getInt("prod_price"));
				orderdetailVO.setDetail_amt(rs.getInt("detail_amt"));
				set.add(orderdetailVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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
	public Set<MsgVO> getMsgsByProd_id(String prod_id){
		Set<MsgVO> set = new LinkedHashSet<MsgVO>();
		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Msgs_ByProd_id_STMT);
			pstmt.setString(1, prod_id);

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

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
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

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public static void main(String[] args) throws IOException {

		ProductJDBCDAO dao = new ProductJDBCDAO();

/*
		// insert()
		ProductVO productVO = new ProductVO();
		productVO.setProd_id("A");
		productVO.setStore_id("A");
		productVO.setProd_name("A");
		productVO.setCate_id("A");
		productVO.setProd_price(1);
		productVO.setProd_category(1);
		productVO.setProd_img(getPictureByteArray("D:/temp/tomcat.gif"));
		productVO.setProd_amt(1);
		productVO.setProd_launch(1);
		productVO.setProd_desc("A");
		dao.insert(productVO);

		// update()
		ProductVO productVO = new ProductVO();
		productVO.setProd_id("A");
		productVO.setStore_id("A");
		productVO.setProd_name("A");
		productVO.setCate_id("A");
		productVO.setProd_price(1);
		productVO.setProd_category(1);
		productVO.setProd_img(getPictureByteArray("D:/temp/tomcat.gif"));
		productVO.setProd_amt(1);
		productVO.setProd_launch(1);
		productVO.setProd_desc("A");
		dao.update(productVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		ProductVO productVO = dao.findByPrimaryKey("A");
		System.out.print(productVO.getProd_id() + ", ");
		System.out.print(productVO.getStore_id() + ", ");
		System.out.print(productVO.getProd_name() + ", ");
		System.out.print(productVO.getCate_id() + ", ");
		System.out.print(productVO.getProd_price() + ", ");
		System.out.print(productVO.getProd_category() + ", ");
		System.out.print(productVO.getProd_img() + ", ");
		System.out.print(productVO.getProd_amt() + ", ");
		System.out.print(productVO.getProd_launch() + ", ");
		System.out.print(productVO.getProd_desc() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<ProductVO> list = dao.getAll();
		for (ProductVO aProductVO : list) {
			System.out.print(aProductVO.getProd_id() + ", ");
			System.out.print(aProductVO.getStore_id() + ", ");
			System.out.print(aProductVO.getProd_name() + ", ");
			System.out.print(aProductVO.getCate_id() + ", ");
			System.out.print(aProductVO.getProd_price() + ", ");
			System.out.print(aProductVO.getProd_category() + ", ");
			System.out.print(aProductVO.getProd_img() + ", ");
			System.out.print(aProductVO.getProd_amt() + ", ");
			System.out.print(aProductVO.getProd_launch() + ", ");
			System.out.print(aProductVO.getProd_desc() + ", ");
			System.out.println();
		}

		Set<OrderdetailVO> set = dao.getOrderdetailsByProd_id("A");
		for (OrderdetailVO aOrderdetail : set) {
			System.out.print(aOrderdetail.getOrd_id() + ", ");
			System.out.print(aOrderdetail.getProd_id() + ", ");
			System.out.print(aOrderdetail.getProd_name() + ", ");
			System.out.print(aOrderdetail.getProd_price() + ", ");
			System.out.print(aOrderdetail.getDetail_amt() + ", ");
			System.out.println();
		}

		Set<MsgVO> set = dao.getMsgsByProd_id("A");
		for (MsgVO aMsg : set) {
			System.out.print(aMsg.getMsg_id() + ", ");
			System.out.print(aMsg.getMem_id() + ", ");
			System.out.print(aMsg.getProd_id() + ", ");
			System.out.print(aMsg.getMsg_content() + ", ");
			System.out.print(aMsg.getMsg_date() + ", ");
			System.out.println();
		}

*/
	}


}