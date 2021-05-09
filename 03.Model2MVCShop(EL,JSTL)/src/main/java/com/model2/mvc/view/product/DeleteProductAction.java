package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class DeleteProductAction extends Action {

	public DeleteProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("DeleteProductAction.java 시작 / prodNo : "+request.getParameter("prodNo"));
		
		//DB에서 상품정보 삭제
		ProductService service = new ProductServiceImpl();
		service.deleteProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		System.out.println("DeleteProductAction.java 완료");
		
		return "redirect:/listSale.do?menu=manage";
	}

}
