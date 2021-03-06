package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action {

	public GetPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		PurchaseService service=new PurchaseServiceImpl();
		Purchase purchase = null;
		
		if(request.getParameter("tranNo")!=null) {
			int tranNo=Integer.parseInt(request.getParameter("tranNo"));
			purchase = service.getPurchase(tranNo);
		}
		if(request.getParameter("prodNo")!=null) {
			int prodNo=Integer.parseInt(request.getParameter("prodNo"));
			purchase = service.getPurchase2(prodNo);
		}
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";		
	}

}
