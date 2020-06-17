package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.BrandReport;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandReportApiController {

	@Autowired
	private BrandService brandservice;

	@ApiOperation(value = "Gets inventory report")
	@RequestMapping(path = "/api/branderport", method = RequestMethod.GET)
	public List<BrandReport> getAll() {
		List<BrandPojo> list = brandservice.getBrands();
		List<BrandReport> sales = new ArrayList<BrandReport>();
		for (BrandPojo p : list) {
			String brand = p.getBrand();
			List<BrandPojo> pojo = brandservice.getDistinctBrands(brand);

			BrandReport report = new BrandReport();
			report.setBrand(brand);
			report.setRevenue(getRevenue(pojo));

			sales.add(report);
		}
		return sales;
	}

	private static double getRevenue(List<BrandPojo> list) {
		double revenue = 0;
		for (BrandPojo pojo : list) {
			revenue += getRevenue(pojo.getProduct());
		}
		return revenue;
	}

	private static double getRevenue(Set<ProductPojo> p) {
		double revenue = 0;
		for (ProductPojo pojo : p) {
			revenue += getProductRevenue(pojo.getItem());
		}
		return revenue;
	}

	private static double getProductRevenue(Set<OrderItemPojo> p) {
		double revenue = 0;
		for (OrderItemPojo pojo : p) {
			revenue += pojo.getSellingPrice();
		}
		return revenue;
	}

}
