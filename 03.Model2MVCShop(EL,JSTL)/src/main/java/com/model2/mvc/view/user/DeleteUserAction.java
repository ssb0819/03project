package com.model2.mvc.view.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class DeleteUserAction extends Action {

	public DeleteUserAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("DeleteUserAction.java ����");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println("���ǿ��� user���� userId : "+user.getUserId());
		
		//DB���� ȸ������ ����
		UserService service = new UserServiceImpl();
		service.deleteUser(user.getUserId());
		
		//�ֱ� �� ��ǰ ��� ����
		Cookie[] cookies = request.getCookies();		
		if (cookies!=null && cookies.length > 0) {
			
			for (Cookie cookie : cookies) {
				
				if (cookie.getName() != null && cookie.getName().startsWith("history")) {
					
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		//���ǻ���
		session.invalidate();
		
		System.out.println("DeleteUserAction.java �Ϸ�");
		
		return "redirect:/user/deleteUserView.jsp";
	}

}
