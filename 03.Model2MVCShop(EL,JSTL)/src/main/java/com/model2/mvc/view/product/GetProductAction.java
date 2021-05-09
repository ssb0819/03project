package com.model2.mvc.view.product;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public GetProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		Cookie cookie = new Cookie("history"+prodNo, URLEncoder.encode(product.getProdName()));
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		System.out.println("history=prodNo 쿠키 저장완료 : "+cookie);		
		
		return "forward:/product/getProduct.jsp";
	}

}
