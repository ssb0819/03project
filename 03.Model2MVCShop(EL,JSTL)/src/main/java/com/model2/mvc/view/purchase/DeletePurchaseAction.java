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
		
		System.out.println("DeletePurchaseAction.java 시작 / tranNo : "+request.getParameter("tranNo"));
		
		//DB에서 구매정보 삭제
		PurchaseService service = new PurchaseServiceImpl();
		service.deletePurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		System.out.println("DeletePurchaseAction.java 완료");
		
		return "redirect:/purchase/deletePurchaseView.jsp";
	}

}
