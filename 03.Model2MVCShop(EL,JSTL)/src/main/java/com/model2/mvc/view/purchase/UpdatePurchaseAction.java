package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class UpdatePurchaseAction extends Action {

	public UpdatePurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("UpdatePurchaseAction.java execute() 시작");
		
		Purchase purchase = new Purchase();
		HttpSession session = request.getSession();
			
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurchase(purchase);
		
		System.out.println("UpdatePurchaseAction.java execute() 완료");
		
		return "redirect:/getPurchase.do?tranNo="+purchase.getTranNo()+"&currentPage="
					+request.getParameter("currentPage");
	}

}
