package com.increff.employee.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.InventoryReport;
import com.increff.employee.model.ReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController

public class ReportApiController {

	@Autowired
	private BrandService brandservice;

	@ApiOperation(value = "filters inventory report")
	@RequestMapping(path = "/api/inventoryreport", method = RequestMethod.POST)
	public List<InventoryReport> add(@RequestBody ReportForm form) throws ApiException, ParseException {
		
		//converting Date to local date format
		LocalDate d1 = ControllerUtil.getLocalDate(form.getStartdate());
		LocalDate d2 =  ControllerUtil.getLocalDate(form.getEnddate());

		List<BrandPojo> list = brandservice.getAll();
		List<String> category = form.getCategory();
		List<String> brand = form.getBrand();
		
		//generating inventory report with brand, category and date filters
		return ControllerUtil.convertToInventory(list, category, brand, d1, d2);

	}

	
}
