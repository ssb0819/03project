package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	public AddProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("AddProductAction.java execute()����");
		
		Product product = new Product();
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		
		String manuDay = "";
		for( String temp : request.getParameter("manuDate").split("-")) {
			manuDay += temp;
		}			
		product.setManuDate(manuDay);		
		
		product.setPrice( Integer.parseInt( request.getParameter("price") ) );
		product.setFileName(request.getParameter("fileName"));
		
		System.out.println("productVO ���� �Ϸ� : "+product);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(product);
		request.setAttribute("product", product);
		
		return "forward:/product/addProduct.jsp";
	}

}
