package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	public ListProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("ListProductAction.java execute() 시작");
		
		Search search=new Search();
		
		int currentPage=1;
		if(request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String searchPriceMin = request.getParameter("searchPriceMin");
		String searchPriceMax = request.getParameter("searchPriceMax");
		
		try {
			if(searchPriceMin!=null && searchPriceMin.length()>0) {
				search.setSearchPriceMin(Integer.parseInt(searchPriceMin));
			}
			if(searchPriceMax!=null && searchPriceMax.length()>0) {
				search.setSearchPriceMax(Integer.parseInt(searchPriceMax));
			}
		}catch(NumberFormatException e) {
			System.out.println("////searchPrice 숫자가 아닌 값 입력됨/////");
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("////searchPrice 기타 Exception 발생////");
			e.printStackTrace();
		}

		System.out.println("search 검색어 : "+search.getSearchKeyword());
		System.out.println("search 가격 : "+search.getSearchPriceMin()+" ~ "+search.getSearchPriceMax());
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(search);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);

		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		
		System.out.println("request에 productList setting 완료");
		
		return "forward:/product/listProduct.jsp";
	}

}
