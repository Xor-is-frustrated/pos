package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController  
public class ListController {   
            
	@Autowired       
	private BrandService brandService;  
	 
	@ApiOperation(value = "Gets list of all categories")  
	@RequestMapping(path = "/api/list/category", method = RequestMethod.GET)
	public List<String> getAllCategories() {
		List<BrandPojo> list = brandService.getCategories();
		return ControllerUtil.convertCategoryList(list); 
	}
	 
	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(path = "/api/list/brands", method = RequestMethod.GET)
	public List<String> getAllBrands() {
		List<BrandPojo> list = brandService.getBrands();
		return ControllerUtil.convertBrandList(list);
	} 
	 
	
	
	
	
}
