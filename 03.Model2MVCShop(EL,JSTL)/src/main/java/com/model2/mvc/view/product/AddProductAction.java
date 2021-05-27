package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	public AddProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		if(FileUpload.isMultipartContent(request)) {
			//String temDir = "D://workspace03(Project&analysis)\\9941.1.Model2MVCShop(ins)"
							//+ "\\WebContent\\images\\uploadFiles\\";
			//String temDir = "C://workspace\\03.Model2MVCShop(ins)\\src\\main\\webapp\\images\\uploadFiles\\";
			String temDir = "C:\\Users\\USER\\git\\03project\\03.Model2MVCShop(EL,JSTL)\\src\\main\\webapp\\images\\uploadFiles\\";
			//String temDir = "../images/uploadFiles/";
			//String temDir = ".";
			//String temDir2 = "uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024*1024*100); //100MB
			fileUpload.setSizeThreshold(1024*100); //100k������ �ѹ��� �޸𸮿� ����
			
			if(request.getContentLength()<fileUpload.getSizeMax()) {
				Product product = new Product();
				
				StringTokenizer token = null;
				
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();
				for(int i=0; i<Size ; i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"),"-");
							String manuDate = token.nextToken()+token.nextToken()+token.nextToken();
							product.setManuDate(manuDate);						
						}
						else if(fileItem.getFieldName().equals("prodName")) 
							product.setProdName(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("prodDetail")) 
							product.setProdDetail(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("price")) 
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					}else {
						System.out.print("���� : "+fileItem.getFieldName()+"="+fileItem.getName());
						System.out.print("("+fileItem.getSize()+" byte)<br>");
						if(fileItem.getSize()>0) {
							int idx = fileItem.getName().lastIndexOf("\\");
							if(idx==-1) {
								idx=fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx+1);
							product.setFileName(fileName);
							System.out.println(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							}catch(IOException e){
								System.out.println(e);								
							}
						}else {
							product.setFileName("../../images/empty.GIF");
						}
					}
						
				}//for
				
				ProductServiceImpl service = new ProductServiceImpl();
				service.addProduct(product);
				
				request.setAttribute("product", product);
			}else {
				int overSize = (request.getContentLength()/1000000);
				System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮�� "+overSize+"MB�Դϴ�.');");
				System.out.println("history.back();</script>");
			}			
		}else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�.");
		}
		
		
		return "forward:/product/addProduct.jsp";
	}

}
