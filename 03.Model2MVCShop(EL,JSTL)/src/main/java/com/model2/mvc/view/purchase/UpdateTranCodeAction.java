package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	public UpdateTranCodeAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("UpdateTranCodeByProdAction.java execute() 시작");
		
		Purchase purchase = new Purchase();		
		purchase.setTranCode(request.getParameter("tranCode"));
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		
		System.out.println("tranCode : "+purchase.getTranCode());
		
		PurchaseService service = new PurchaseServiceImpl();		
		service.updateTranCode(purchase);
		
		System.out.println("UpdateTranCodeByProdAction.java execute() 완료");
		
		return "forward:/listPurchase.do";
	}

}
