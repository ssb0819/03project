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
		
		System.out.println("DeleteUserAction.java 시작");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println("세션에서 user꺼냄 userId : "+user.getUserId());
		
		//DB에서 회원정보 삭제
		UserService service = new UserServiceImpl();
		service.deleteUser(user.getUserId());
		
		//최근 본 상품 목록 삭제
		Cookie[] cookies = request.getCookies();		
		if (cookies!=null && cookies.length > 0) {
			
			for (Cookie cookie : cookies) {
				
				if (cookie.getName() != null && cookie.getName().startsWith("history")) {
					
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		//세션삭제
		session.invalidate();
		
		System.out.println("DeleteUserAction.java 완료");
		
		return "redirect:/user/deleteUserView.jsp";
	}

}
