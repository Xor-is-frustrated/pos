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

import com.increff.employee.model.ReportForm;
import com.increff.employee.model.SalesReport;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.ControllerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SalesReportApiController {

	@Autowired
	private BrandService brandService;

	@ApiOperation(value = "filters sales report")
	@RequestMapping(path = "/api/salesreport", method = RequestMethod.POST)
	public List<SalesReport> add(@RequestBody ReportForm form) throws ApiException, ParseException {

		// converting Date to local date format
		LocalDate d1 = ControllerUtil.getLocalDate(form.getStartdate());
		LocalDate d2 = ControllerUtil.getLocalDate(form.getEnddate());

		List<String> category = form.getCategory();
		List<String> brand = form.getBrand();

		// retrieving all distinct categories
		List<BrandPojo> list = brandService.getCategories();
		List<SalesReport> sales = new ArrayList<SalesReport>();
		for (BrandPojo p : list) {
			String category_name = p.getCategory();

			// selected category is not in the choosen list
			if (!category.contains(category_name)) {
				continue;
			}

			// retrieving all category items of the selected category
			List<BrandPojo> pojo = brandService.getDistinctCategories(category_name);

			SalesReport report = new SalesReport();
			report.setCategory(category_name);

			// calculates revenue of selected brands and categories in selected time frame
			report.setRevenue(ControllerUtil.getInitialRevenue(pojo, brand, d1, d2));

			sales.add(report);

		}
		return sales;
	}

}
