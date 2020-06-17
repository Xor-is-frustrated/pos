package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.InventoryReport;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController

public class ReportApiController {

	@Autowired
	private BrandService brandservice;

	@ApiOperation(value = "Gets inventory report")
	@RequestMapping(path = "/api/inventoryreport", method = RequestMethod.GET)
	public List<InventoryReport> getAll() {
		List<BrandPojo> list = brandservice.getAll();
		return convert1(list);
	}

	private static List<InventoryReport> convert1(List<BrandPojo> list) {
		List<InventoryReport> list2 = new ArrayList<InventoryReport>();
		for (BrandPojo pojo : list) {
			InventoryReport inv = new InventoryReport();
			inv.setBrand(pojo.getBrand());
			inv.setCategory(pojo.getCategory());
			int quantity = getQuantity(pojo.getProduct());
			inv.setQuantity(quantity);
			double revenue = getRevenue(pojo.getProduct());
			inv.setRevenue(revenue);
			list2.add(inv);
		}
		return list2;
	}

	private static int getQuantity(Set<ProductPojo> p) {
		int quantity = 0;
		for (ProductPojo pojo : p) {
			if (pojo.getQuantity() != null) {
				quantity = quantity + pojo.getQuantity().getQuantity();
			}
		}
		return quantity;
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

	public static List<BrandData> convert(List<BrandPojo> list) {
		List<BrandData> list2 = new ArrayList<BrandData>();
		for (BrandPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	private static BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		d.setId(p.getId());

		Set<ProductPojo> pojoset = p.getProduct();
		Set<String> s = new HashSet<String>();
		Iterator<ProductPojo> value = pojoset.iterator();
		while (value.hasNext()) {
			ProductPojo test = (ProductPojo) value.next();
			s.add(test.getProduct());
		}
		d.setProduct(s);

		return d;
	}

}
