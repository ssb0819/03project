package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class DeletePurchaseAction extends Action {

	public DeletePurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("DeletePurchaseAction.java ���� / tranNo : "+request.getParameter("tranNo"));
		
		//DB���� �������� ����
		PurchaseService service = new PurchaseServiceImpl();
		service.deletePurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		System.out.println("DeletePurchaseAction.java �Ϸ�");
		
		return "redirect:/purchase/deletePurchaseView.jsp";
	}

}
