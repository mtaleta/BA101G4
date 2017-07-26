package com.orderlist.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import com.member.model.MemberDAO;
import com.member.model.MemberVO;
import com.orderdetail.model.OrderdetailDAO;
import com.orderdetail.model.OrderdetailVO;
import com.product.model.ProductDAO;
import com.product.model.ProductVO;
import com.store.model.StoreDAO;
import com.store.model.StoreVO;

public class OrderlistDAO implements OrderlistDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ORDERLIST (ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER) VALUES ('ORD' || LPAD(to_char(ORD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST ORDER BY ORD_ID";
	private static final String GET_ONE_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String GET_Orderdetails_ByOrd_id_STMT = "SELECT ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT FROM ORDERDETAIL WHERE ORD_ID = ? ORDER BY ORD_ID,PROD_ID";

	private static final String DELETE_ORDERDETAILs = "DELETE FROM ORDERDETAIL WHERE ORD_ID = ?";
	private static final String DELETE_ORDERLIST = "DELETE FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String UPDATE = "UPDATE ORDERLIST SET MEM_ID=?, STORE_ID=?, ORD_TOTAL=?, ORD_PICK=?, ORD_ADD=?, ORD_SHIPPING=?, ORD_TIME=?, SCORE_SELLER=? WHERE ORD_ID = ?";

	// Adding by Sylvie
	@Override
	public OrderlistVO insertWithDetail(OrderlistVO orderlistVO, List<OrderdetailVO> list) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
			
		try {
			con = ds.getConnection();
		
			con.setAutoCommit(false);
			
			int[] cols = {1};
				
			pstmt = con.prepareStatement(INSERT_STMT, cols);
				
			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			pstmt.setInt(8, orderlistVO.getScore_seller());
				
			pstmt.executeUpdate();
				
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			String key = rs.getString(1);
				
			OrderdetailDAO orderdetailDAO = new OrderdetailDAO();
				
			for (OrderdetailVO orderdetailVO : list) {
				orderdetailVO.setOrd_id(new String(key));
				orderdetailDAO.insertWithCon(orderdetailVO,con);
			}
				
			con.commit();
			con.setAutoCommit(true);
	
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return orderlistVO;
	}

	// new method
	// 訂單完成&撥款
	@Override
	public void updateWithStore(OrderlistVO orderlistVO, StoreVO storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			// setInt -> setObject
			pstmt.setObject(8, orderlistVO.getScore_seller());
			// setInt -> setObject
			pstmt.setString(9, orderlistVO.getOrd_id());

			pstmt.executeUpdate();

			
			StoreDAO storeDAO = new StoreDAO();
			storeDAO.updatePoints(storeVO, con);
			
			
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	// new method

	// new method
	// 下單&扣款&扣庫存量
	@Override
	public void insertWithDetailAndCharge(OrderlistVO orderlistVO, Vector<OrderdetailVO> cartList, Vector<ProductVO> productList, MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		String[] cols = {"ORD_ID"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			pstmt.setObject(8, orderlistVO.getScore_seller());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys(); // getGeneratedKeys(): 取得綁定的欄位，回傳ResultSet
			if (rs.next()) { // next(): 讀取下一筆資料，回傳boolean值，沒讀取到資料時，回傳false
				orderlistVO.setOrd_id(rs.getString(1)); // 此處傳入index，取得欄位1的值，也就是綁定的欄位EMPNO的值
			}

			OrderdetailDAO orderdetailDAO = new OrderdetailDAO();
			for(OrderdetailVO orderdetailVO : cartList){
				orderdetailVO.setOrd_id(orderlistVO.getOrd_id());
				orderdetailDAO.insertByAutoGeneratedKey(orderdetailVO, con);
			}
			
			ProductDAO productDAO = new ProductDAO();
			for(ProductVO productVO : productList){
				productDAO.updateAmt(productVO, con);
			}
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.updateForCharge(memberVO, con);
			
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

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
	// new method

	// 此方法有修改細節
	// setInt -> setObject
	@Override
	public void update(OrderlistVO orderlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			// setInt -> setObject
			pstmt.setObject(8, orderlistVO.getScore_seller());
			// setInt -> setObject
			pstmt.setString(9, orderlistVO.getOrd_id());

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
	// setInt -> setObject
	// 此方法有修改細節
	
	
	@Override
	public void insert(OrderlistVO orderlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			pstmt.setInt(8, orderlistVO.getScore_seller());

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
	public void delete(String ord_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_ORDERDETAILs);

			pstmt.setString(1, ord_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDERLIST);

			pstmt.setString(1, ord_id);

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
	public OrderlistVO findByPrimaryKey(String ord_id) {

		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ord_id);

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
		return orderlistVO;
	}

	@Override
	public List<OrderlistVO> getAll() {

		List<OrderlistVO> list = new ArrayList<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

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
				list.add(orderlistVO); // Store the row in the list
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
	public Set<OrderdetailVO> getOrderdetailsByOrd_id(String ord_id){
		Set<OrderdetailVO> set = new LinkedHashSet<OrderdetailVO>();
		OrderdetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderdetails_ByOrd_id_STMT);
			pstmt.setString(1, ord_id);

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


}