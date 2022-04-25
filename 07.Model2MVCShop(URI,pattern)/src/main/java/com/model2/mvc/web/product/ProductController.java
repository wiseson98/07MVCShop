package com.model2.mvc.web.product;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Resource(name="uploadPath")
	String uploadPath;
		
	/// Constructor
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
		   
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct() throws Exception{
		
		System.out.println("/product/addProduct : GET");		
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product,  MultipartHttpServletRequest mtpReq) throws Exception{
		
		System.out.println("/product/addProduct : POST ");
		
		List<MultipartFile> fileList = mtpReq.getFiles("file");
		
		String fileName = getFile(fileList);	
		System.out.println("[ 최종 fileName ] =>" + fileName);
		if(fileName != null) {
			product.setFileName(fileName);
		}
		
		productService.addProduct(product);
				
		return "forward:/product/addProduct.jsp";
	}
	
	public String getFile(List<MultipartFile> fileList) throws Exception{
				
		System.out.println("fileList size : " + fileList.size());
		
		String fileName = "";		
		
		if(fileList.size() <= 1) {
			
			fileName = fileList.get(0).getOriginalFilename().equals("") ? null : System.currentTimeMillis() + fileList.get(0).getOriginalFilename();
			
		}else {
			
			for(MultipartFile mf : fileList) {
				
				System.out.println("파일 이름 : " + mf.getOriginalFilename());
					
				String saveName = System.currentTimeMillis() + mf.getOriginalFilename();
					
				System.out.println("saveName : " + saveName);
					
				FileCopyUtils.copy(mf.getBytes(), new File(uploadPath, saveName));
					
				fileName += saveName + ",";
				
			}
			
			fileName = fileName.substring(0, (fileName.length()-1));
			System.out.println("fileName = " + fileName);
		}		
		
		return fileName;
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/updateProductView");
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product ) throws Exception{
		
		System.out.println("/updateProduct");
		
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo=" + product.getProdNo();
	}
	
	@RequestMapping(value = "/getProduct", method = RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("/getProduct");
		
		Product product = productService.getProduct(prodNo);
		
		System.out.println("product.getFileName : " + product.getFileName());
		
//		List<String> fileNames = new ArrayList<String>();		
				
		model.addAttribute("product", product);
		
		if(product.getFileName() != null) {
			String[] fileNames = null;
			if(product.getFileName().indexOf(",") > 0) {
				fileNames = product.getFileName().split(",");				
			}else {
				fileNames[0] = product.getFileName();
			}
			model.addAttribute("fileNames", fileNames);
		}		
		
		String cookieValue = null;
		Cookie cookie = null;
		
		cookieValue = Integer.toString(prodNo);
		
		if(request.getCookies() != null) {
			for(Cookie c : request.getCookies()) {
				if(c.getName().equals("history")) {
					cookieValue = URLDecoder.decode(c.getValue(), "euc-kr");
					cookieValue += "," + prodNo;
					System.out.println("CookieValue(history 존재): " + cookieValue);
				}
			}
		}
		System.out.println("CookieValue: " + cookieValue);
		cookie = new Cookie("history", URLEncoder.encode(cookieValue, "euc-kr"));
		response.addCookie(cookie);
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping(value = "/listProduct")
	public String listProduct(@ModelAttribute("search") Search search, @RequestParam(value = "menu", defaultValue = "search") String menu, Model model) throws Exception{
		
		System.out.println("/listProduct");
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}		

}//end of class