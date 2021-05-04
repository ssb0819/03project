package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class AddPurchaseAction extends Action {

	public AddPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("AddPurchaseAction.java execute()시작");
		
		Purchase purchase = new Purchase();
		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");
		System.out.println("tranCode 공백 확인 : "+purchase.getTranCode().replace(' ', '!'));
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		purchase.setPurchaseProd(product);
		
		HttpSession session = request.getSession();
		purchase.setBuyer((User)session.getAttribute("user"));
			
		PurchaseService pServ = new PurchaseServiceImpl();
		pServ.addPurchase(purchase);
		request.setAttribute("purchase", purchase);
		
		System.out.println("addPurchase() 완료");
		return "forward:/purchase/addPurchase.jsp";
	}

}
