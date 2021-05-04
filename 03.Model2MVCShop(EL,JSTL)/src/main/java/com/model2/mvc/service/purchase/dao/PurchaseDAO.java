package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {

	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		System.out.println("findPurchase() ȣ�� / tranNo : " +tranNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction t, product p, users u "
				+ " WHERE t.prod_no = p.prod_no AND"
				+ " t.buyer_id = u.user_id AND t.tran_no = ? ORDER BY tran_no";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = new Purchase();
		Product Product = new Product();
		User userVO = new User();
		
		while (rs.next()) {
			
			userVO.setUserId(rs.getString("user_id"));
			userVO.setUserName(rs.getString("user_name"));
			userVO.setPassword(rs.getString("password"));
			userVO.setRole(rs.getString("role"));
			userVO.setSsn(rs.getString("ssn"));
			userVO.setPhone(rs.getString("cell_phone"));
			userVO.setAddr(rs.getString("addr"));
			userVO.setEmail(rs.getString("email"));
			userVO.setRegDate(rs.getDate("reg_date"));
			purchase.setBuyer(userVO);
			
			Product.setProdNo(rs.getInt("prod_no"));
			Product.setProdName(rs.getString("prod_name"));
			Product.setProdDetail(rs.getString("prod_detail"));			
			Product.setPrice(rs.getInt("price"));
			Product.setFileName(rs.getString("image_file"));
			Product.setRegDate(rs.getDate("reg_date"));
			Product.setManuDate(rs.getString("manufacture_day"));
			purchase.setPurchaseProd(Product);
			
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setDivyAddr(rs.getString("dlvy_addr"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setOrderDate(rs.getDate("order_date"));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			System.out.println("payment ���� Ȯ�� : "+purchase.getPaymentOption().replace(' ', '!'));
		}
		
		stmt.close();
		con.close();
		
		System.out.println("findPurchase() �Ϸ� / purchaseVO : "+purchase);

		return purchase;
	}
	
	public Purchase findPurchase2(int prodNo) throws Exception{
		
		System.out.println("findPurchase2() ȣ�� / prodNo : " +prodNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		
		while (rs.next()) {
			
			purchase = new Purchase();
			
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setDivyAddr(rs.getString("dlvy_addr"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setOrderDate(rs.getDate("order_date"));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));	
		}
		
		stmt.close();
		con.close();
		
		System.out.println("findPurchase2() �Ϸ� / purchaseVO : "+purchase);

		return purchase;
	}
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		System.out.println("getPurchaseList() ȣ�� : "+buyerId);
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT tran_no, buyer_id, receiver_name, receiver_phone, tran_status_code"
					+ " FROM transaction WHERE buyer_id = '";	
		
		sql += buyerId + "'";
		System.out.println("getPurchaseList ù��° sql : "+sql);		

		int total = this.getTotalCount(sql);
		System.out.println("�ο��� ��:" + total);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", new Integer(total));

		sql = this.makeCurrentPageSql(sql, search);
		
		PreparedStatement stmt = con.prepareStatement(sql);		
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchase = null;
		UserService service = new UserServiceImpl();		
		ArrayList<Purchase> list = new ArrayList<Purchase>();		
		
		while(rs.next()) {
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setBuyer(service.getUser(rs.getString("buyer_id")));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			System.out.println("tranCode ���� Ȯ�� : "+purchase.getTranCode().replace(' ', '!'));
			System.out.println("purchaseVO ���� �Ϸ� : "+purchase);
	
			list.add(purchase);			
		}
					
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
		rs.close();
		stmt.close();
		con.close();
			
		return map;
	}
	
	public Map<String, Object> getSaleList(Search search) throws Exception {
		
		System.out.println("getSaleList() ����");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword()
						+ "%'";
			}
		}
		sql += " ORDER BY prod_no";
		
		System.out.println("SQL : "+sql);

		int total = this.getTotalCount(sql);
		System.out.println("�ο��� ��:" + total);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", new Integer(total));

		sql=this.makeCurrentPageSql(sql, search);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		List<Product> list = new ArrayList<Product>();		
		Product product = null;
		
		while(rs.next()) {
				product = new Product();
				product.setProdNo(rs.getInt("prod_no"));
				product.setProdName(rs.getString("prod_name"));
				product.setProdDetail(rs.getString("prod_detail"));
				product.setManuDate(rs.getString("manufacture_day"));
				product.setPrice(rs.getInt("price"));
				product.setFileName(rs.getString("image_file"));
				product.setRegDate(rs.getDate("reg_date"));
				if(this.findPurchase2(product.getProdNo())!=null) {
					product.setProTranCode(this.findPurchase2(product.getProdNo()).getTranCode());
				}
				list.add(product);
				System.out.println("list.add -> product : "+product);
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		rs.close();
		stmt.close();
		con.close();
			
		return map;

	}
	
	public void insertPurchase(Purchase purchase) throws Exception {	
		
		System.out.println("insertPurchase() ȣ�� : "+purchase);
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.NEXTVAL, ?,?,?,?,?,?,?,?,SYSDATE,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		System.out.println("tranCode ���� Ȯ�� : "+purchase.getTranCode().replace(' ', '!'));
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
		System.out.println("INSERT �Ϸ�");
		
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {	
		
		System.out.println("updatePurchase() ȣ�� : "+purchase);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?,"
				       + " dlvy_addr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
		System.out.println("UPDATE �Ϸ�");
		
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		
		System.out.println("updateTranCodeByProd() ȣ�� : "+purchase);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET tran_status_code=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());

		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
		System.out.println("UPDATE �Ϸ�");
		
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	

	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}	


}