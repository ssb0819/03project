package com.model2.mvc.view.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;


public class LogoutAction extends Action {

	@Override
	public String execute(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		
		//�ֱ� �� ��ǰ��� ����
		Cookie[] cookies = request.getCookies();		
		if (cookies!=null && cookies.length > 0) {
			
			for (Cookie cookie : cookies) {
				
				if (cookie.getName() != null && cookie.getName().startsWith("history")) {
					
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		//���� ����
		session.invalidate();
		
		return "redirect:/index.jsp";
	}
}