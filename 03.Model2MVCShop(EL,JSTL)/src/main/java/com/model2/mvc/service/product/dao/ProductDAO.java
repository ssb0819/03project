package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}

	
	public Product findProduct(int prodNo) throws Exception {
		
		System.out.println("findProduct() 호출 / PROD_NO : " +prodNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT prod_no, prod_name, prod_detail, price, image_file, reg_date, manufacture_day"
					+ " FROM product WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product productVO = null;
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));			
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
			productVO.setManuDate(rs.getString("manufacture_day"));
		}
		
		rs.close();
		stmt.close();
		con.close();
		
		System.out.println(productVO);

		return productVO;
	}

	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT prod_no, prod_name, prod_detail, price, image_file, reg_date,"
					+ " manufacture_day FROM product ";
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
		System.out.println("로우의 수:" + total);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", new Integer(total));

		sql=this.makeCurrentPageSql(sql, search);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		List<Product> list = new ArrayList<Product>();		
		Product product = null;
		
		PurchaseService pServ = new PurchaseServiceImpl();
		
		while(rs.next()) {
				product = new Product();
				product.setProdNo(rs.getInt("prod_no"));
				product.setProdName(rs.getString("prod_name"));
				product.setProdDetail(rs.getString("prod_detail"));
				product.setManuDate(rs.getString("manufacture_day"));
				product.setPrice(rs.getInt("price"));
				product.setFileName(rs.getString("image_file"));
				product.setRegDate(rs.getDate("reg_date"));
				
				if(pServ.getPurchase2(product.getProdNo())!=null) {
					product.setProTranCode("재고없음");
				}else {
					product.setProTranCode("판매중");
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

	
	public void insertProduct(Product productVO) throws Exception {
		
		System.out.println("insertProduct() 시작 ");
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES (seq_product_prod_no.NEXTVAL,?,?,?,?,?,SYSDATE)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();

		
		stmt.close();
		con.close();
		
		System.out.println("INSERT 완료");
	}

	
	public void updateProduct(Product productVO) throws Exception {
		
		System.out.println("updateProduct() 호출 "+productVO);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product SET prod_name=?,prod_detail=?,manufacture_day=?,price=?,image_file=? WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
		System.out.println("UPDATE 완료");
	}
	
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		stmt.close();
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
