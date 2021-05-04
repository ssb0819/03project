package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public UpdateTranCodeByProdAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("UpdateTranCodeByProdAction.java execute() ����");
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));		
		purchase.setTranCode(request.getParameter("tranCode"));
		
		System.out.println("purchaseVO : "+purchase);
		
		service.updateTranCode(purchase);
		
		System.out.println("UpdateTranCodeByProdAction.java execute() �Ϸ�");
		
		return "forward:/listSale.do?menu=manage";
	}

}
