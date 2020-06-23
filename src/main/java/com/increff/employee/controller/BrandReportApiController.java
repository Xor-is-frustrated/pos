package com.increff.employee.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.BrandReport;
import com.increff.employee.model.ReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandReportApiController {

	@Autowired
	private BrandService brandService;

	@ApiOperation(value = "filters brand report")
	@RequestMapping(path = "/api/brandreport", method = RequestMethod.POST)
	public List<BrandReport> add(@RequestBody ReportForm form) throws ApiException, ParseException {

		// converting Date to local date format
		LocalDate d1 = ControllerUtil.getLocalDate(form.getStartdate());
		LocalDate d2 = ControllerUtil.getLocalDate(form.getEnddate());

		List<String> category = form.getCategory();
		List<String> brand = form.getBrand();

		// retrieving all distinct brands
		List<BrandPojo> list = brandService.getBrands();

		List<BrandReport> sales = new ArrayList<BrandReport>();
		for (BrandPojo p : list) {
			String brand_name = p.getBrand();

			// selected brand is not in the choosen list
			if (!brand.contains(brand_name)) {
				continue;
			}
			// retrieving all brand items of the selected brand
			List<BrandPojo> pojo = brandService.getDistinctBrands(brand_name);

			BrandReport report = new BrandReport();
			report.setBrand(brand_name);
			
			// calculates revenue of selected brands and categories in selected time frame
			report.setRevenue(ControllerUtil.getRevenue(pojo, category, d1, d2));
			sales.add(report);

		}
		return sales;
	}

}
